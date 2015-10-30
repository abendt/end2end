import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import web.PhantomJsBrowser

driver = {
    if (System.getProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY)) {
        new PhantomJsBrowser().create()
    } else {
        new FirefoxDriver()
    }
}