package shadow.practice.portfolio.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import shadow.practice.portfolio.Model.Person;
import shadow.practice.portfolio.Model.PortfolioClass;
import shadow.practice.portfolio.Repository.CoursesRepository;
import shadow.practice.portfolio.Repository.PersonRepository;
import shadow.practice.portfolio.Repository.PortfolioClassRepository;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private PortfolioClassRepository portfolioClassRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    @RequestMapping("/displayCourses")
    public ModelAndView studentDisplayCoursesPage(Model model, HttpSession session){
        Person person = (Person) session.getAttribute("loggedInPerson");
        ModelAndView modelAndView = new ModelAndView("courses_enrolled.html");
        modelAndView.addObject("person", person);
        return modelAndView;
    }
}
