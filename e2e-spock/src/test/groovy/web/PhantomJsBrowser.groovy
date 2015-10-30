package web

import groovy.util.logging.Slf4j
import org.openqa.selenium.WebDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities

@Slf4j
class PhantomJsBrowser {

    def WebDriver create() {
        def sCaps = new DesiredCapabilities()

        sCaps.setJavascriptEnabled(true)
        sCaps.setCapability(CapabilityType.TAKES_SCREENSHOT, true)
        sCaps.setBrowserName("phantomjs")
        sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, ['--webdriver-loglevel=INFO', "--ssl-protocol=tlsv1", '--ignore-ssl-errors=yes'])

        def driver = new PhantomJSDriver(sCaps)

        driver.executePhantomJS(enableConsoleLogging())
        driver.executePhantomJS(logResourceErrors())
        driver.executePhantomJS(logJavaScriptErrors())

        driver
    }

    /**
     * JavaScript console Ausgaben loggen
     * http://phantomjs.org/api/webpage/handler/on-console-message.html
     */
    private String enableConsoleLogging() {
        """ var page = this;
            var system = require('system');

            page.onConsoleMessage = function(msg) {
                system.stderr.writeLine( 'PhantomJS console: ' + msg );
            };"""
    }

    /**
     * Ressourcen Fehler loggen
     * http://phantomjs.org/api/webpage/handler/on-resource-error.html
     */
    private String logResourceErrors() {
        """ var page = this;
            page.onResourceError = function(resourceError) {
                console.log('PhantomJS ERROR: Unable to load resource (#' + resourceError.id + ' URL:' + resourceError.url + ')');
                console.log('PhantomJS ERROR: Error code: ' + resourceError.errorCode + '. Description: ' + resourceError.errorString);
            };"""
    }

    /**
     * JavaScript Fehler loggen
     * http://phantomjs.org/api/webpage/handler/on-error.html
     */
    private String logJavaScriptErrors() {
        """ var page=this;
            page.onError = function (msg, trace) {
                console.log(msg);
                trace.forEach(function(item) {
                    console.log('  ', item.file, ':', item.line);
                });
            };"""
    }
}
