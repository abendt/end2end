package rest;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)

public class RestTest {

    @ArquillianResource
    private URL baseURL;

    @Deployment(testable = false)
    public static WebArchive createTestArchive() {
        File war = Maven.resolver()
                .resolve("org.wildfly.quickstarts:wildfly-helloworld-rs:war:9.0.0-SNAPSHOT")
                .withoutTransitivity()
                .asSingleFile();

        return ShrinkWrap.createFromZipFile(WebArchive.class, war);
    }

    @Test
    public void canAccessWar() throws Exception {
        String content = Resources.toString(jsonUrl(), Charsets.UTF_8);

        assertTrue(content.contains("Hello World!"));
    }

    private URL jsonUrl() {
        return baseURL;
    }
}
