package shadow.practice.portfolio.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import shadow.practice.portfolio.Model.Person;
import shadow.practice.portfolio.Model.PortfolioClass;
import shadow.practice.portfolio.Repository.PersonRepository;
import shadow.practice.portfolio.Repository.PortfolioClassRepository;

import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    PortfolioClassRepository portfolioClassRepository;

    @Autowired
    PersonRepository personRepository;

    @RequestMapping("/displayClasses")
    public ModelAndView displayClassPage(Model model){
        List<PortfolioClass> portfolioClasses = portfolioClassRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("portfolioClasses", portfolioClasses);
        modelAndView.addObject("portfolioClass", new PortfolioClass());
        return modelAndView;
    }

    @PostMapping("/addNewClass")
    public ModelAndView displayNewClass(Model model, @ModelAttribute("portfolioClass") PortfolioClass portfolioClass){
        portfolioClassRepository.save(portfolioClass);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(Model model, @RequestParam int id){
        Optional<PortfolioClass> portfolioClass = portfolioClassRepository.findById(id);
        for(Person person : portfolioClass.get().getPersons()){
            person.setPortfolioClass(null);
            personRepository.save(person);
        }
        portfolioClassRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession session,
                                            @RequestParam(value = "error", required = false) String error){
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("students.html");
        Optional<PortfolioClass> portfolioClass = portfolioClassRepository.findById(classId);
        modelAndView.addObject("portfolioClass", portfolioClass.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("portfolioClass", portfolioClass.get());
        if(error != null){
            errorMessage = "Invalid Email Entered";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addStudent")
    public ModelAndView addStudent(Model model, @ModelAttribute("person") Person person, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        PortfolioClass portfolioClass = (PortfolioClass) session.getAttribute("portfolioClass");
        Person personEntity = personRepository.readByEmail(person.getEmail());

        if(personEntity == null || !(personEntity.getPersonId() > 0)){
            modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + portfolioClass.getClassId() + "&error=true");
            return modelAndView;
        }

        personEntity.setPortfolioClass(portfolioClass);
        personRepository.save(personEntity);
        portfolioClass.getPersons().add(personEntity);
        portfolioClassRepository.save(portfolioClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + portfolioClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudentOperation(Model model, @RequestParam int personId, HttpSession session){
        PortfolioClass portfolioClass = (PortfolioClass) session.getAttribute("portfolioClass");
        Optional<Person> person = personRepository.findById(personId);
        person.get().setPortfolioClass(null);
        portfolioClass.getPersons().remove(person.get());
        PortfolioClass portfolioClassSaved =  portfolioClassRepository.save(portfolioClass);
        session.setAttribute("portfolioClass", portfolioClassSaved);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId=" + portfolioClass.getClassId());
        return modelAndView;
    }
}
