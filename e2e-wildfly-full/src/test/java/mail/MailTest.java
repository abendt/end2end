package mail;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.assertj.core.api.JUnitSoftAssertions;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import javax.mail.Message;
import java.io.File;
import java.net.URL;

@RunWith(Arquillian.class)
public class MailTest {

    @ClassRule
    public static final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP);

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Deployment(testable = false)
    public static WebArchive createTestArchive() {
        File war = Maven.resolver()
                .resolve("org.wildfly.quickstarts:wildfly-mail:war:9.0.0-SNAPSHOT")
                .withoutTransitivity()
                .asSingleFile();

        WebArchive archive = ShrinkWrap.createFromZipFile(WebArchive.class, war);

        return archive;
    }

    @ArquillianResource
    private URL baseURL;

    @Drone
    private WebDriver browser;

    @Test
    public void canSendMail(@InitialPage MailPage page) throws Exception {
        page.sendMail("roadrunner@acme.com",
                "coyote@acme.com",
                "dinner party?",
                "may i invite you to dinner tonight?");

        greenMail.waitForIncomingEmail(1);

        Message[] messages = greenMail.getReceivedMessages();

        softly.assertThat(messages).extracting("subject").contains(("dinner party?"));
        softly.assertThat(messages).flatExtracting("from").extractingResultOf("toString").contains("coyote@acme.com");
    }
}