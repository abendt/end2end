package file;

import com.google.common.base.Charsets;
import com.google.common.collect.Iterables;
import com.google.common.io.Files;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.jayway.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
public class CamelTest {

    static File inFolder = new File(projectFolder(), "in-folder");
    static File outFolder = new File(projectFolder(), "output-folder");

    @Deployment(testable = false)
    public static JavaArchive createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(CamelRouteBuilder.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void fileIsCopiedFromInFolderToOut() throws Exception {
        String name = givenFileWithContent("World");

        File expectedFile = new File(outFolder, name);

        await().until(() -> {
            return expectedFile.exists();
        });

        assertThat(expectedFile).hasContent("Hello World!");
    }

    private String givenFileWithContent(String content) throws IOException {
        String name = System.currentTimeMillis() + "-file.txt";
        File file = new File(inFolder, name);
        Files.write(content, file, Charsets.UTF_8);

        return name;
    }

    private static File projectFolder() {
        List<String> classpathElements = Arrays.asList(System.getProperty("java.class.path").split(File.pathSeparator));

        String classesFolder = Iterables.find(classpathElements, input -> new File(input).isDirectory() && new File(input).getParentFile().getName().equals("target"));

        return new File(classesFolder).getParentFile();
    }
}
