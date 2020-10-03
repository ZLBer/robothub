import edu.hust.robothub.hubservice.HubServiceApplication;
import edu.hust.robothub.hubservice.clients.RestTemplateClient;
import edu.hust.robothub.hubservice.model.Organization;
import edu.hust.robothub.hubservice.services.DiscoveryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


@SpringBootTest(classes = HubServiceApplication.class )
@RunWith(SpringRunner.class)
public class ServiceTest {
    @Autowired
    @Qualifier("internalRestTemplate")
    RestTemplate restTemplate;
    @Autowired
    @Qualifier("externalRestTemplate")
    RestTemplate restTemplate2;
    @Test
    public void testRestTemplate() throws Exception {

        System.out.println((restTemplate==null)+"   "+(restTemplate==restTemplate2)+" "+(restTemplate2==null));
        ResponseEntity<String> restExchange2 =
                restTemplate2.exchange(
                        "https://service-5uv48fmw-1253402865.gz.apigw.tencentcs.com/release/start-test-1601278513",
                        HttpMethod.GET,
                        null, String.class);
        System.out.println(restExchange2.getBody());
        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(
                        "http://zuulservice/api/exampleservice/v1/organizations/{organizationId}",
                        HttpMethod.GET,
                        null, Organization.class, "123");

        System.out.println(restExchange.getBody());
    }


    @Autowired
    DiscoveryService discoveryService;

    @Test
    public  void testGetEurekaServices(){
      discoveryService.getEurekaServices().forEach(System.out::println);
    }


    @Autowired
    RestTemplateClient<String> RestClient;

    @Test
    public void testInvokeRemote() throws Exception {

       String url= "https://service-5uv48fmw-1253402865.gz.apigw.tencentcs.com/release/start-test-1601278513";
        URI uri=new URI(url);
        HttpMethod httpMethod=HttpMethod.GET;
        RequestEntity requestEntity=new RequestEntity(httpMethod,uri);

       String s= RestClient.invokeRemote(requestEntity,false,String.class);
        System.out.println(s);
    }
}
