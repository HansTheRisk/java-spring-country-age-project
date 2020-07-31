package main.resource.whoami;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Response {

    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty
    private int age;
    @JsonProperty
    private String gender;

}

