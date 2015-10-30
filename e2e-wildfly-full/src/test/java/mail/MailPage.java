package mail;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("home.jsf")
public class MailPage {

    @FindBy(id = "email:to")
    private WebElement to;

    @FindBy(id = "email:from")
    private WebElement from;

    @FindBy(id = "email:subject")
    private WebElement subject;

    @FindBy(id = "email:body")
    private WebElement body;

    @FindBy(id = "email:sendButton")
    private WebElement sendButton;

    public void sendMail(String to, String from, String subject, String body) {
        this.to.sendKeys(to);
        this.from.sendKeys(from);
        this.subject.sendKeys(subject);
        this.body.sendKeys(body);

        sendButton.click();
    }
}
