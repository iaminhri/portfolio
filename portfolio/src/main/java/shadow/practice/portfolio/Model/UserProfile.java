package shadow.practice.portfolio.Model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserProfile {

    @NotBlank(message="Name must not be blank")
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message="Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    private String email;

    @NotBlank(message = "Please fill up the address box")
    @Size(min = 5, message = "Address must be greater than 5 characters! ")
    private String address1;

    private String address2;

    @NotBlank(message = "Please provide your city name")
    @Size(min = 5, message = "City name must be greater than 5 characters !")
    private String city;

    @NotBlank(message = "Please enter your state name")
    @Size(min = 5, message = "State name must be greater than 5 characters !")
    private String state;

    @NotBlank(message = "Please provide your Zip Code.")
    @Pattern(regexp = "(^$|[0-9]{6})", message = "Zip code must be 6 digits")
    private String zip_code;
}
