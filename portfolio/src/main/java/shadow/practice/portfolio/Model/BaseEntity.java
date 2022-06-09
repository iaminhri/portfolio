package shadow.practice.portfolio.Model;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @Data Annotations generates getters, setters, constructors and many more stuffs such as toString
 */
@Data //Lombok Library.
@MappedSuperclass // whenever this class is extended, spring data JPA includes these properties within the DB table.
public class BaseEntity {
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
