package jms;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.inject.Inject;
import javax.jms.*;

@Local
public class MessageService {

    @Inject
    @JMSConnectionFactory("java:/JmsXA")
    private JMSContext context;

    @Resource(lookup = "java:/jms/DemoQueue")
    private Queue queue;

    public void sendHello(String name) {
        try {
            TextMessage message = context.createTextMessage();
            message.setText("Hello " + name);
            context.createProducer().send(queue, message);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
