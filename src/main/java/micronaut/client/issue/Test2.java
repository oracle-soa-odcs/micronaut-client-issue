package micronaut.client.issue;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.DefaultHttpClient;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.loadbalance.FixedLoadBalancer;

import javax.inject.Inject;
import java.lang.reflect.Field;


@Controller("/test2")
public class Test2 {
    private final RxHttpClient client;

    @Inject
    Test2(@Client("${clients.MyClient.url:`http://1.1.1.1:8080/test`}") RxHttpClient client) {
        this.client = client;
    };

    @Get("/")
    public String test() throws Exception {
        Field f = DefaultHttpClient.class.getDeclaredField("loadBalancer");
        f.setAccessible(true);
        return ((FixedLoadBalancer)f.get(client)).getUrl().toString();
    }
}