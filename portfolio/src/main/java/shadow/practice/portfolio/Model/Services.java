package shadow.practice.portfolio.Model;

import lombok.Data;

/**
 * @Data annotation generates getters, setters, toString,
 * equalsAndHashTable, RequiredArgsConstructor and AllArgsConstructor
 */
@Data
public class Services {
    private final String serviceName;
    private final String serviceDetails;
    private final Type type;

    public enum Type{
        GAMEDEV, WEBDEV
    }
}
