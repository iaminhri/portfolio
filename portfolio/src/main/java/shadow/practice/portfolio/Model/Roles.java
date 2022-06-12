package shadow.practice.portfolio.Model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity // this annotation links JPA and java class by matching Database columns names.
public class Roles extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int roleId;

    private String roleName;
}
