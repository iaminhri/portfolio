package shadow.practice.portfolio.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

//@Data
@Getter
@Setter
@Entity
@Table(name = "class")
public class PortfolioClass extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int classId;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message = "Name must not be less than 3 characters")
    private String name;

    //Parent Entity.
    /**
     * @mappedBy -> links between parent and child class in this case parent class is portfolioClass and
     *              child class is Person.class.
     * @fetch -> FetchType.LAZY, lazily loads the data of the java class with data instantiation.
     * @cascade -> CascadeType.Persist -> loads the both Java class with the linker and saves the data.
     * @CascadeType.PERSIST -> only does the PERSIST operation, it doesn't do any DELETE, or Any other cascade operation.
     * @targetEntity -> mentions of the child class that is mapped by parent class.
     */
    @OneToMany(mappedBy = "portfolioClass", fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST, targetEntity = Person.class)
    private Set<Person> persons; //Child Entity.
}
