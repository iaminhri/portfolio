package shadow.practice.portfolio.Model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Data Annotations generates getters, setters, constructors and many more stuffs such as toString
 */

@Data
public class BaseEntity {
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
