package async;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;

public class AsyncTest {

    AtomicBoolean value = new AtomicBoolean(false);

    @Test
    public void canUseAwaitility() throws Exception {
        new Thread() {
            @Override
            public void run() {
                value.set(true);
            }
        }.start();

        Thread.sleep(1000);

        assertThat(value.get()).isTrue();
    }
}
