package shadow.practice.portfolio.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import shadow.practice.portfolio.Model.Contact;
import shadow.practice.portfolio.Service.ContactService;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @Slf4j generates object for any System based Class. Such as Logger Class.
 */

@Slf4j
@Controller
public class ContactController {

    private final ContactService contactService;

    /**
     * get the value of contactService from Bean Context using Autowired
     */
    @Autowired
    public ContactController(ContactService contactService){
        this.contactService = contactService;
    }

    @RequestMapping("/contact")
    public String displayContactPage(Model model){
        model.addAttribute("contact", new Contact());
        return "contact.html";
    }

    /**
     * @RequestParam helps receive data from the client using get method
     * Also can be accepted the query params such form values.
     */

//    @PostMapping(value="/saveMsg")
//    @RequestMapping(value="/saveMsg", method = POST)
//    public ModelAndView saveMessage(@RequestParam String name, @RequestParam String phoneNum,
//                                    @RequestParam String email, @RequestParam String message){
//        log.info("Name: " + name);
//        log.info("Email: " + email);
//        log.info("Phone Number: "+ phoneNum);
//        log.info("Message: " + message);
//
//        return new ModelAndView("redirect:/contact");
//    }

    /**
     * @RequestMapping -> is mapped with the "/saveMsg" from html file, this way the form knows which method to execute.
     * @param contact -> Simple Contact Model Class contains, name, email, phoneNum and message.
     * @return a new ModelAndView object and redirects the user to the same page.
     * @contactService.saveMessageDetails(contact) -> stores the collected value from the form to the Contact Service Layer.
     *
     * This @saveMessage() -> accepts data from front-end process data using Controller and Service Layer.
     */
    @RequestMapping(value = "/saveMsg", method = POST)
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors){
        if(errors.hasErrors()) {
            log.error("Contact form validation failed due to: " + errors.toString());
            return "contact.html";
        }
        contactService.saveMessageDetails(contact);

//        contactService.setCounter(contactService.getCounter()+1);
//        log.info("Number of times the contact form is submitted: " + contactService.getCounter());

        return "redirect:/contact";
    }

    @RequestMapping("/displayMessages/page/{pageNum}")
    public ModelAndView displayMessages(Model model, @PathVariable(name = "pageNum") int pageNum,
                                        @RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir) {
        Page<Contact> msgPage = contactService.findMsgsWithOpenStatus(pageNum, sortField, sortDir);
        List<Contact> contactMsgs = msgPage.getContent();
        ModelAndView modelAndView = new ModelAndView("messages.html");

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", msgPage.getTotalPages());
        model.addAttribute("totalMsgs", msgPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("contactMsgs", contactMsgs);

        return modelAndView;
    }


    @RequestMapping(value="/closeMsg", method=GET)
    public String displayCloseMsg(@RequestParam int id) {
        contactService.updateMsgStatus(id);
        return "redirect:/displayMessages/page/1?sortField=name&sortDir=asc";
    }
}

//    @RequestMapping(value="/displayMessages")
//    public ModelAndView displayMessagePage(Model model){
//        List<Contact> contactMsg = contactService.findMsgsWithOpenStatus();
//        /**
//         * @ModelAndView instance object with "messages.html -> shows the messages html page with
//         * the help of ModelAndView.
//         */
//        ModelAndView modelAndView = new ModelAndView("messages.html");
//
//        modelAndView.addObject("contactMsgs", contactMsg);
//        return modelAndView;
//    }


//    @RequestMapping(value="/closeMsg", method=GET)
//    public String displayCloseMsg(@RequestParam int id,
//                                        Authentication authentication) {
//        contactService.updateMsgStatus(id, authentication.getName());
//        return "redirect:/displayMessages";
//    }