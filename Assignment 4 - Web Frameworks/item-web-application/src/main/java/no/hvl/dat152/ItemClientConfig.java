package no.hvl.dat152;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ItemClientConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
