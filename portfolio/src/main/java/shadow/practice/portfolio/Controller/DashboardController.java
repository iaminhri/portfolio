package shadow.practice.portfolio.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import shadow.practice.portfolio.Model.Person;
import shadow.practice.portfolio.Model.UserProfile;
import shadow.practice.portfolio.Repository.PersonRepository;

import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session){
        Person person = personRepository.readByEmail(authentication.getName());
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        //session created for the logged-in user within dashboard controller
        session.setAttribute("loggedInPerson", person);
        return "dashboard.html";
    }
}
