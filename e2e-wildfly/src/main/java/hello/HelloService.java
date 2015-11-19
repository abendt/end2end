package hello;

import javax.ejb.Local;
import javax.inject.Inject;

@Local
public class HelloService {

    @Inject
    OtherService otherService;

    public String sayHelloTo(String name) {
        otherService.service();

        return "Hello " + name;
    }
}
