package shadow.practice.portfolio.Model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import shadow.practice.portfolio.Annotations.FieldsValueMatch;
import shadow.practice.portfolio.Annotations.PasswordValidator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

//@Data
@Getter
@Setter
@Entity
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "pwd",
                fieldMatch = "confirmPwd",
                message = "Passwords do not match!"
        ),
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "confirmEmail",
                message = "Email addresses do not match!"
        )
})
/**
 * Javax.Validation.Constraints used within these input fields.
 */
public class Person extends BaseEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int personId;

    @NotBlank(message="Name must not be blank")
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message="Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    private String email;

    @NotBlank(message="Confirm Email must not be blank")
    @Email(message = "Please provide a valid confirm email address" )
    @Transient // This Annotation excludes operations within DB.
    private String confirmEmail;

    @NotBlank(message="Password must not be blank")
    @Size(min=5, message="Password must be at least 5 characters long")
    @PasswordValidator
    private String pwd;

    @NotBlank(message="Confirm Password must not be blank")
    @Size(min=5, message="Confirm Password must be at least 5 characters long")
    @Transient
    private String confirmPwd;

    /**
     * $FetchType.EAGER -> Automatic loading of followed data table
     * $CascadeType.All -> loads all the Cascade type
     *                  -> PERSIST, MERGE, REFRESH, REMOVE, DETACH AND ALL.
     */
    @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Address.class)
    @JoinColumn( name = "address_id", referencedColumnName = "addressId", nullable = true)
    private Address address;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = Roles.class)
    @JoinColumn( name = "role_id", referencedColumnName = "roleId", nullable = false)
    private Roles roles;

    /**
     * @JoinColumn -> @name -> Column name of the Child Class from Class Table
     *             -> @referencedColumnName -> name of the column in Parent class of Portfolio Class.
     * @nullable   -> true.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "class_id", referencedColumnName = "classId", nullable = true)
    private PortfolioClass portfolioClass;

    /**
     * In @ManyToMany Relationship, to create an intermediate table use JoinTable;
     * @JoinTable -> requires configuration of multiple column from different Tables and creates a new intermediary table.
     * $person_courses -> Intermediary Table name;
     * @joinColumns -> configures the column within this class.
     * @inverseJoinColumns -> configures the column within courses class.
     */

    @ManyToMany(mappedBy = "persons", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "person_courses",
        joinColumns = { //details within the mentioned class.
            @JoinColumn(name = "person_id", referencedColumnName = "personId")
        },
        inverseJoinColumns = { //Details about the other joined table.
            @JoinColumn(name = "course_id", referencedColumnName = "courseId")
        }
    )
    private Set<Courses> courses = new HashSet<>();

}
