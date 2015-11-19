package category;

import org.junit.Test;
import org.junit.experimental.categories.Category;

public class UnfinishedTest {
    @Test
    public void unfinishedTest() {
        throw new UnsupportedOperationException();
    }
}
