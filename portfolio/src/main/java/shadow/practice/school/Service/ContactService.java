/**
 * Business Logic Section
 */

package shadow.practice.school.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import shadow.practice.school.Model.Contact;

/**
 * @Service creates a bean context of Service
 * */

/**
 * @Slf4j generates object for any System based Class.
 * Such as Logger Class. Also Slf4j lessens the pain to instantiate an object.
 */

@Slf4j
@Service
//@RequestScope /* RequestScope creates bean for everytime a user sends HTTP request */
//@SessionScope /* SessionScope is, one user gets a single bean,
// for a new user there will be a new context bean created */
@ApplicationScope /** for application scope for every user there is only one context bean,
 * in case of counting the visitors this is useful and many more applications.
 */
public class ContactService {
    private int counter = 0;

    public ContactService(){
        System.out.println("Contact service bean using request scope is created!!!");
    }

    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = true;
        log.info(contact.toString());
        return isSaved;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
