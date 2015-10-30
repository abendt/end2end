package web

import geb.spock.GebSpec
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.arquillian.test.api.ArquillianResource
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.spec.WebArchive
import org.jboss.shrinkwrap.resolver.api.maven.Maven
import org.junit.runner.RunWith

@RunWith(ArquillianSputnik)
class WebSpec extends GebSpec {

    @ArquillianResource
    URL baseURL

    @Deployment(testable = false)
    static WebArchive createTestArchive() {
        def war = Maven.resolver()
                .resolve("org.wildfly.quickstarts:wildfly-helloworld-html5:war:9.0.0-SNAPSHOT")
                .withoutTransitivity()
                .asSingleFile()

        ShrinkWrap.createFromZipFile(WebArchive.class, war)
    }

    def "can use scripting style"() {
        given:
        go baseURL.toExternalForm()

        expect:
        title == 'HTML5 + REST Hello World'
    }

    def "can use page objects"() {
        given:
        go baseURL.toExternalForm()

        and:
        def page = at HelloPage

        when:
        page.enterName('World')

        then:
        page.ensureNameIsDisplayed('World')
    }

}
