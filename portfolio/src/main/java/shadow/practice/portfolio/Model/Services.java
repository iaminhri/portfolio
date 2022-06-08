package shadow.practice.portfolio.Model;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;

/**
 * @Data annotation generates getters, setters, toString,
 * equalsAndHashTable, RequiredArgsConstructor and AllArgsConstructor
 */
@Data
public class Services extends BaseEntity{

    private String serviceName;
    private String serviceDetails;
    private Type type;

    public enum Type{
        GAMEDEV, WEBDEV
    }
}
