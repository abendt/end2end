package web;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.jboss.arquillian.graphene.Graphene.waitGui;

@Location("index.html")
public class HelloPage {

    @FindBy
    private WebElement name;

    @FindBy
    private WebElement sayHello;

    @FindBy
    private WebElement result;

    public void enterName(String name) {
        this.name.sendKeys(name);

        sayHello.click();
    }

    public void ensureNameIsDisplayed(String name) {
        waitGui().until().element(result).text().contains(name);
    }

}
