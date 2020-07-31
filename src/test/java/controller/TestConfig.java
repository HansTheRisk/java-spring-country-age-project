package controller;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfig {

    @MockBean
    public RestTemplate restTemplate;

    @MockBean
    public ThreadPoolTaskExecutor taskExecutor;

}
