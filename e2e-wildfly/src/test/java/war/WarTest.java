package war;

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
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)

public class WarTest {

    @ArquillianResource
    private URL baseURL;

    @Deployment(testable = false)
    public static WebArchive createTestArchive() {

        File war = Maven.resolver()
                .resolve("org.wildfly.quickstarts:wildfly-helloworld:war:9.0.0-SNAPSHOT")
                .withoutTransitivity()
                .asSingleFile();

        WebArchive archive = ShrinkWrap.createFromZipFile(WebArchive.class, war);

        System.out.println(archive.toString(true));

        return archive;
    }

    @Test
    public void canAccessWar() throws Exception {
        String content = Resources.toString(new URL(baseURL, "HelloWorld"), Charsets.UTF_8);

        assertThat(content).contains("Hello World");
    }
}
