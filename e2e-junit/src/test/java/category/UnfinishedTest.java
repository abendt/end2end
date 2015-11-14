package category;

import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(Ongoing.class)
public class UnfinishedTest {
    @Test
    public void unfinishedTest() {
        throw new UnsupportedOperationException();
    }
}
