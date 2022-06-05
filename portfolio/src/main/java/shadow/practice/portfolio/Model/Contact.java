package shadow.practice.portfolio.Model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class Contact {
    @NotBlank(message = "Name Required!!!")
    @Size(min=3, message = "Name must be greater than 3 characters !!!")
    private String name;

    @NotBlank(message = "Email Required!!!")
    @Email(message = "Please provide a mail to submit the form")
    private String email;

    @NotBlank(message = "Enter A Valid Phone Number")
    @Pattern(regexp = "(^&|[0-9]{10})", message = "Mobile Number Must be 10 Digits !!!")
    private String phoneNum;

    @NotBlank(message = "Message must not be blank !")
    @Size(min = 10, message = "Message must be at least 10 characters long !")
    private String message;
}
