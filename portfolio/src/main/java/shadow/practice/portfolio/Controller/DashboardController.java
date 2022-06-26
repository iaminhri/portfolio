package shadow.practice.portfolio.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        return "dashboard.html";
    }
}
