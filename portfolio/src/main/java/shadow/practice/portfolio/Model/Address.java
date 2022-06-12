package shadow.practice.portfolio.Model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Entity
public class Address extends BaseEntity{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator( name = "native", strategy = "native")
    private int addressId;

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
