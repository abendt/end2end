package web;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.net.URL;

@RunWith(Arquillian.class)

public class WebTest {

    @ArquillianResource
    private URL baseURL;

    @Drone
    private WebDriver browser;

    @Deployment(testable = false)
    public static WebArchive createTestArchive() {

        File war = Maven.resolver()
                .resolve("org.wildfly.quickstarts:wildfly-helloworld-html5:war:9.0.0-SNAPSHOT")
                .withoutTransitivity()
                .asSingleFile();

        WebArchive archive = ShrinkWrap.createFromZipFile(WebArchive.class, war);

        return archive;
    }

    @Test
    public void canAccessPage() throws Exception {
        browser.get(baseURL.toExternalForm());

        String content = Resources.toString(baseURL, Charsets.UTF_8);

        System.out.println(content);
    }

    @Test
    public void canUsePageObject(@InitialPage HelloPage helloPage) {
        helloPage.enterName("World");

        helloPage.ensureNameIsDisplayed("World");
    }

}
