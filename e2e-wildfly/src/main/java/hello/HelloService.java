package hello;

import javax.ejb.Local;

@Local
public class HelloService {
    public String sayHelloTo(String name) {
        return "Hello " + name;
    }
}
