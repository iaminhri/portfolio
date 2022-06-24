package shadow.practice.portfolio.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import shadow.practice.portfolio.Model.Courses;
import shadow.practice.portfolio.Model.Person;
import shadow.practice.portfolio.Model.PortfolioClass;
import shadow.practice.portfolio.Repository.CoursesRepository;
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

    @Autowired
    CoursesRepository coursesRepository;

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

    /**
     * GetMapping -> is used for viewing only, only to retrieve data from the backend.
     * @param model -> Model class parameter.
     * @param personId -> requested param from front-end
     * @param session -> existing session passed to modify data from the session.
     * @return modelAndView data modified within function.
     */
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

    @GetMapping("/displayCourses")
    public ModelAndView displayCoursesPage(Model model){
        List<Courses> courses = coursesRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("courses_secure.html");
        //fetch data from DB with the name courses and exploited this data in the front-end.
        modelAndView.addObject("courses", courses);
        /**
         * model.addAttribute -> sending a new object of the Courses class.
         * Fetches the data from the Front-end to the back-end.
         */
        model.addAttribute("course", new Courses());
        return modelAndView;
    }

    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourses(Model model, @ModelAttribute("course") Courses courses){
        ModelAndView modelAndView= new ModelAndView();
        //saved the courses details fetched using @ModelAttribute("course") from front-end / user input.
        coursesRepository.save(courses);
        modelAndView.setViewName("redirect:/admin/displayCourses");
        return modelAndView;
    }

    @GetMapping("/viewStudents")
    public ModelAndView viewStudentsPage(Model model, @RequestParam int id,
                                         @RequestParam(value = "error", required = false) String error,
                                                     HttpSession session){
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("course_students.html");
        Optional<Courses> courses = coursesRepository.findById(id);
        /**
         * @modelAndView.addObject -> gets the courses value from the DB table and display on the Front-end.
         * @model.addAttribute("person", new Person()); ->  creates a new person object
         * and sends data of newly created person data from front-end to back-end DB person Table.
         */
        modelAndView.addObject("courses", courses.get());
        model.addAttribute("person", new Person());
        session.setAttribute("courses", courses.get());
        if(error != null){
            errorMessage = "Invalid Email Entered !!!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(Model model, @ModelAttribute("person") Person person, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        Courses courses = (Courses) session.getAttribute("courses");
        Person personEntity = personRepository.readByEmail(person.getEmail());

        if(personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/viewStudents?id=" + courses.getCourseId() + "&error=true");
            return modelAndView;
        }

        personEntity.getCourses().add(courses);
        courses.getPersons().add(personEntity);
        personRepository.save(personEntity);
        //updated courses in the session.
        session.setAttribute("courses", courses);
        modelAndView.setViewName("redirect:/admin/viewStudents?id=" + courses.getCourseId());
        return modelAndView;
    }

    @GetMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentsFromCourses(Model model,  HttpSession session,
                                                  @RequestParam(value = "personId") int personId){
        //fetching existing courses data from the session.
        Courses courses = (Courses) session.getAttribute("courses");
        //findingById and storing inside person object
        Optional<Person> person = personRepository.findById(personId);
        /**
         * Unlinking person table and courses table from manyToMany relational db.
         * @removing -> courses from the person table
         * @removing -> persons from the courses table.
         * Unlink established.
         */
        person.get().getCourses().remove(courses);
        courses.getPersons().remove(person);
        // @save -> saving the updated person table in the DB.
        personRepository.save(person.get());
        //updating the session with updated value.
        session.setAttribute("courses", courses);

        //Viewing the updated page.
        ModelAndView modelAndView = new
                ModelAndView("redirect:/admin/deleteStudentFromCourse?id=" + courses.getCourseId());
        return modelAndView;
    }

}
