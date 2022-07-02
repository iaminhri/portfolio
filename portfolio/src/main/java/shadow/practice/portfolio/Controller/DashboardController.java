package shadow.practice.portfolio.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import shadow.practice.portfolio.Model.Person;
import shadow.practice.portfolio.Model.UserProfile;
import shadow.practice.portfolio.Repository.CoursesRepository;
import shadow.practice.portfolio.Repository.PersonRepository;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class DashboardController {

//    @Value("${portfolio.pageSize}")
//    private int defaultPageSize;
//
//    @Value("${portfolio.contact.successMsg}")
//    private String message;
//
//    @Autowired
//    private Environment environment;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session){
        Person person = personRepository.readByEmail(authentication.getName());
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());

        //checks if the person is assigned to a particular class or not. if yes it sends the data to the front-end.
        if(person.getPortfolioClass() != null && person.getPortfolioClass().getName() != null){
            model.addAttribute("enrolledClass", person.getPortfolioClass().getName());
        }
        //session created for the logged-in user with session name "loggedInPerson" within dashboard controller
        session.setAttribute("loggedInPerson", person);
//        logMessages();
//        logProperties();
        return "dashboard.html";
    }
//
//     private void logMessages(){
//        log.error("Error message from the Dashboard Page");
//        log.warn("Warning message from the Dashboard Page");
//        log.info("Info message from the Dashboard Page");
//        log.debug("Debug message from the Dashboard Page");
//        log.trace("Trace message from the Dashboard Page");

//        log.error("Default Page Size " + defaultPageSize);
//        log.error("Message: " + message);
//     }

//     private void logProperties(){
//        log.info("Default Page Size " + environment.getProperty("portfolio.pageSize"));
//        log.info("Message: " + environment.getProperty("portfolio.contact.successMsg"));
//        log.info("Message: " + environment.getProperty("JAVA_HOME"));
//     }

}
