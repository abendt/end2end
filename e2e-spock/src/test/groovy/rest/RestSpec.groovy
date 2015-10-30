package rest

import groovy.json.JsonSlurper
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.arquillian.test.api.ArquillianResource
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.jboss.shrinkwrap.resolver.api.maven.Maven
import org.junit.runner.RunWith
import spock.lang.Specification

import javax.ws.rs.client.ClientBuilder

@RunWith(ArquillianSputnik)
class RestSpec extends Specification {

    @ArquillianResource
    private URL baseURL

    @Deployment(testable = false)
    static WebArchive createTestArchive() {
        def war = Maven.resolver()
                .resolve("org.wildfly.quickstarts:wildfly-helloworld-rs:war:9.0.0-SNAPSHOT")
                .withoutTransitivity()
                .asSingleFile()

        ShrinkWrap.createFromZipFile(WebArchive.class, war)
    }

    def "can use spock to test"() {
        given:
        def client = ClientBuilder.newClient()
        def target = client.target(jsonUrl())

        when:
        def response = target.request().get(String.class)

        then:
        def json = new JsonSlurper().parseText(response)
        json.result == 'Hello World!'
    }

    def jsonUrl() {
        new URL(baseURL, "rest/json").toURI()
    }

}
