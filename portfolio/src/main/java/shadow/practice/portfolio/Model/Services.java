package shadow.practice.portfolio.Model;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;

import javax.persistence.*;

/**
 * @Data annotation generates getters, setters, toString,
 * equalsAndHashTable, RequiredArgsConstructor and AllArgsConstructor
 */
@Data
@Entity
@Table(name = "services")
public class Services extends BaseEntity{

    @Id
    private String serviceName;
    private String serviceDetails;
    @Enumerated(EnumType.STRING) //converts enum to String when executing the program and adds string value within DB.
    private Type type;

    public enum Type{
        GAMEDEV, WEBDEV
    }
}
