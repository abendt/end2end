package async;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.jayway.awaitility.Awaitility.await;

public class AsyncTest {

    AtomicBoolean value = new AtomicBoolean(false);

    @Test
    public void canUseAwaitility() {
        new Thread() {
            @Override
            public void run() {
                value.set(true);
            }
        }.start();

        await().untilTrue(value);
    }
}
