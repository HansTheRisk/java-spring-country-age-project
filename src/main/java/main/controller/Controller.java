package main.controller;

import main.resource.agify.AgifyResultResource;
import main.resource.genderize.GenderizeResultResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import main.resource.whoami.Response;
import main.resource.whoami.UserDetails;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class Controller {

    private ThreadPoolTaskExecutor taskExecutor;
    private RestTemplate template;
    private final String GENDERIZE = "https://api.genderize.io?name={name}&country_id={country_id}";
    private final String AGIFY = "https://api.agify.io?name={name}&country_id={country_id}";

    @Autowired
    public Controller(ThreadPoolTaskExecutor taskExecutor,
                      RestTemplate template) {
        this.taskExecutor = taskExecutor;
        this.template = template;
    }

    @PostMapping("/whoami")
    public ResponseEntity<Response> whoami(@Validated @RequestBody final UserDetails details) {
        try {
             CompletableFuture<GenderizeResultResource> genderReq = CompletableFuture.supplyAsync(() -> getGender(details.getFirstName(), details.getCountryCode()));
             CompletableFuture<AgifyResultResource> ageReq = CompletableFuture.supplyAsync(() -> getAge(details.getFirstName(), details.getCountryCode()));
             CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(genderReq, ageReq);

             combinedFuture.get();

            return ResponseEntity.ok(Response.builder()
                                 .firstName(details.getFirstName())
                                 .gender(genderReq.get().getGender())
                                 .countryCode(details.getCountryCode())
                                 .age(ageReq.get().getAge())
                                 .build());

        } catch (RestClientException | ExecutionException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    private GenderizeResultResource getGender(String name, String countryCode) {
        return template.getForObject(GENDERIZE,
                                     GenderizeResultResource.class,
                                     name,
                                     countryCode);
    }


    private AgifyResultResource getAge(String name, String countryCode) {
        return template.getForObject(AGIFY,
                                     AgifyResultResource.class,
                                     name,
                                     countryCode);
    }

}
