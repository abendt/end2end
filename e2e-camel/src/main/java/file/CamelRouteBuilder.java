package file;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import java.io.File;

@Startup
@ApplicationScoped
@ContextName("cdi-context")
public class CamelRouteBuilder extends RouteBuilder {

    String inDir = System.getProperty("camel.in");
    String outDir = System.getProperty("camel.out");

    @PostConstruct
    void init() {
        new File(inDir).mkdirs();
        new File(outDir).mkdirs();
    }

    @Override
    public void configure() throws Exception {

        System.out.println("IN:  " + inDir);
        System.out.println("OUT: " + outDir);

        from("file:" + inDir + "?runLoggingLevel=DEBUG")
        .to("log:e2e.camel?level=DEBUG")
                .to("file:"+outDir);
    }

}
