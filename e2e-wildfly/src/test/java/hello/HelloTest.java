package hello;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.*;

@RunWith(Arquillian.class)
public class HelloTest {

    @Inject
    HelloService helloService;

    @Deployment
    public static JavaArchive createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(HelloService.class)
                .addClass(OtherService.class)
                .addPackages(true, "org.assertj.core")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void canSayHello() {
        String response = helloService.sayHelloTo("World");

        assertThat(response).isEqualTo("Hello World");
    }
}
