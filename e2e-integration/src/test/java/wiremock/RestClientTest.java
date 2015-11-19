package wiremock;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

public class RestClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void canSayHelloTo() throws Exception {
        String result = new RestClient().sayHelloTo();

        assertThat(result).isEqualTo("Hello World!");
    }
}
