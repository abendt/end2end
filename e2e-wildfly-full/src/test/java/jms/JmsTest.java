package jms;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.jayway.awaitility.Awaitility.await;

@RunWith(Arquillian.class)
public class JmsTest {

    @Deployment
    public static JavaArchive createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(MessageService.class)
                .addPackages(true, "com.jayway.awaitility")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    MessageService messageService;

    @Inject
    @JMSConnectionFactory("java:/ConnectionFactory")
    private JMSContext context;

    @Resource(lookup = "java:/jms/queue/DemoQueue")
    private Queue queue;

    private final List<String> receivedMessages = Collections.synchronizedList(new ArrayList());

    @Before
    public void monitorJmsQueue() {
        JMSConsumer consumer = context.createConsumer(queue);

        consumer.setMessageListener(message -> {
            TextMessage tm = (TextMessage) message;

            try {
                String text = tm.getText();
                System.out.println(text);
                receivedMessages.add(text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testIt() throws Exception {
        messageService.sendHello("World");

        await().until(() -> receivedMessages.contains("Hello World"));
    }
}
