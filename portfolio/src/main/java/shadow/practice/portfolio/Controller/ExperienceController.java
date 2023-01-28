package shadow.practice.portfolio.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExperienceController {
    @RequestMapping(value={"/experience"})
    public String displayExperiencePage(){
        return "experience.html";
    }
}
