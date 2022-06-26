/**
 * Business Logic Section
 */

package shadow.practice.portfolio.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import shadow.practice.portfolio.Constants.PortfolioWebAppConstants;
import shadow.practice.portfolio.Model.Contact;
import shadow.practice.portfolio.PortfolioWebApp;
import shadow.practice.portfolio.Repository.ContactRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
//@ApplicationScope /** for application scope for every user there is only one context bean,
// * in case of counting the visitors this is useful and many more applications.
// */
public class ContactService {
//    private int counter = 0;

    @Autowired
    private ContactRepository contactRepository;

    public ContactService(){
        System.out.println("Contact service bean using request scope is created!!!");
    }

    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = false;
        /**
         * This responsibility is handed over to Spring Data JPA dependency
         * Check @AuditAwareImpl, @BaseEntity and @PortfolioWebApp
         * */
        contact.setStatus(PortfolioWebAppConstants.OPEN);
//        contact.setCreatedBy(PortfolioWebAppConstants.ANONYMOUS);
//        contact.setCreatedAt(LocalDateTime.now());

//        int result = contactRepository.saveContactMsg(contact);
        Contact savedContact = contactRepository.save(contact);

        if(savedContact != null && savedContact.getContactId() > 0)
            isSaved = true;

//        if(result > 0)
//            isSaved = true;

        return isSaved;
    }

    public Page<Contact> findMsgsWithOpenStatus(int pageNum, String sortField, String sortDir) {
        int pageSize = 5;

        Pageable pageable = PageRequest.of(pageNum-1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending() :
                        Sort.by(sortField).descending()
                );

        Page<Contact> msgPage = contactRepository.findByStatus(PortfolioWebAppConstants.OPEN, pageable);
        return msgPage;
    }

//    public boolean updateMsgStatus(int contactId, String updatedBy) {
    public boolean updateMsgStatus(int contactId) {
        boolean isUpdated = false;
//        int result = contactRepository.updateMsgStatus(contactId, PortfolioWebAppConstants.CLOSE, updatedBy);
//        if(result > 0)
//            isUpdated = true;

        Optional<Contact> contact = contactRepository.findById(contactId);
        contact.ifPresent(contact1 -> {
            /**
             * This responsibility is handed over to Spring Data JPA dependency Check @AuditAwareImpl, @BaseEntity and @PortfolioWebApp
             * */
            contact1.setStatus(PortfolioWebAppConstants.CLOSE);
//            contact1.setUpdatedAt(LocalDateTime.now());
//            contact1.setUpdatedBy(updatedBy);
        });

        Contact updatedContact = contactRepository.save(contact.get());

        if(updatedContact != null && updatedContact.getUpdatedBy() != null)
            isUpdated = true;
        return isUpdated;
    }

//
//    public List<Contact> findMsgsWithOpenStatus() {
//        // calling from Contact Repository.
////        List<Contact> contactMsgs = contactRepository.findMsgsWithStatus(PortfolioWebAppConstants.OPEN);
//        /**
//         * @findByStatus -> Abstract method from Contact Repo, which is implemented at runtime by Spring Data JPA.
//         * Additionally, property status is mentioned within model class which connects to the database using SDJPA.
//         * Such that it knows which data to fetch from the table.
//         * @return a list<Contact>.
//         * @contactMsgs holds a reference of the status List of each msg inside DB.
//         */
//        List<Contact> contactMsgs = contactRepository.findByStatus(PortfolioWebAppConstants.OPEN);
//
//        //Returning the queries from contact Repo.
//        return contactMsgs;
//    }

//    public int getCounter() {
//        return counter;
//    }
//
//    public void setCounter(int counter) {
//        this.counter = counter;
//    }
}
