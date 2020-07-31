package main.resource.whoami;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import main.validation.ValidCountryCode;
import main.validation.ValidName;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserDetails {

    @JsonProperty("first_name")
    @ValidName
    @NotEmpty(message = "Name cannot be empty")
    private String firstName;

    @JsonProperty("country_code")
    @ValidCountryCode
    @NotEmpty(message = "Country code cannot be empty")
    private  String countryCode;
}
