package wiremock;

import javax.ws.rs.client.ClientBuilder;

public class RestClient {
    public String sayHelloTo() {
        return ClientBuilder.newClient().target("http://localhost:8080/helloService")
                .request().get(String.class);
    }
}
