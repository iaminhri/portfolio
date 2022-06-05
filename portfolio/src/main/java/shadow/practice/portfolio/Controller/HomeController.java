package shadow.practice.portfolio.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController{
    /** Model is an interface inside Spring MVC framework, which act as a container between UI and Backend. */
    @RequestMapping(value = {"", "/", "/home"})
    public String displayHomePage() {
        return "home.html";
    }
}
