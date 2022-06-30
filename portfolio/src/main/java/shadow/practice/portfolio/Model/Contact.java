package shadow.practice.portfolio.Model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
/** @Entity -> builds the relationship between POJO class and repository */
@Entity
/** explicit mentions of the database name when database name and java class name don't match */
@Table(name = "contact_msg")
@NamedQueries({
        @NamedQuery(name = "Contact.findOpenMsgs",
                    query = "SELECT c FROM Contact c WHERE c.status = :status"),
        @NamedQuery(name = "Contact.updateMsgStatus",
                    query = "UPDATE Contact c SET c.status = ?1 WHERE c.contactId = ?2")
})
public class Contact extends BaseEntity{
    /**
     * @contact_Id -> primary key column */
    @Id
    /**
     * Generating primary key column automatically by using strategy "native"
     */
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "contact_id") // Explicit mentions of column name
    private int contactId;

    @NotBlank(message = "Name Required!!!")
    @Size(min=3, message = "Name must be greater than 3 characters !!!")
    private String name;

    @NotBlank(message = "Email Required!!!")
    @Email(message = "Please provide a mail to submit the form")
    private String email;

    @NotBlank(message = "Enter A Valid Phone Number")
    @Pattern(regexp = "(^&|[0-9]{10})", message = "Mobile Number Must be 10 Digits !!!")
    private String mobileNum;

    @NotBlank(message = "Message must not be blank !")
    @Size(min = 10, message = "Message must be at least 10 characters long !")
    private String message;

    private String status;
}
