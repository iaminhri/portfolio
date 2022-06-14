package shadow.practice.portfolio.Validations;

import org.springframework.beans.BeanWrapperImpl;
import shadow.practice.portfolio.Annotations.FieldsValueMatch;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsValueMatchValidator
        implements ConstraintValidator<FieldsValueMatch, Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value,ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value)
                .getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value)
                .getPropertyValue(fieldMatch);

//        Hashed Password value Authorized when Spring DATA JPA Validation is enabled
//        if(fieldValue != null){
//            if(fieldValue.toString().startsWith("$2a")) // User password after converting into hashed password, give it a pass.
//                return true;
//            else
//                return fieldValue.equals(fieldMatchValue); // User Registration First password matching without hash code.
//        }else
//            return fieldMatchValue == null;

        if (fieldValue != null) {
            return fieldValue.equals(fieldMatchValue);
        } else {
            return fieldMatchValue == null;
        }
    }
}
