package shadow.practice.portfolio.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import shadow.practice.portfolio.Model.Address;
import shadow.practice.portfolio.Model.Person;
import shadow.practice.portfolio.Model.UserProfile;
import shadow.practice.portfolio.Repository.PersonRepository;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller("ProfileControllerBean")
public class ProfileController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/displayProfile")
    public ModelAndView displayMessages(Model model, HttpSession session){
        //fetching session data from the Person repo and session created in Dashboard Controller.
        Person person = (Person) session.getAttribute("loggedInPerson");
        UserProfile profile = new UserProfile();

        //Storing data within UserProfile class.
        profile.setName(person.getName());
        profile.setMobileNumber(person.getMobileNumber());
        profile.setEmail(person.getEmail());

        if(person.getAddress() != null && person.getAddress().getAddressId() > 0){
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZip_code(person.getAddress().getZip_code());
        }

        //display profile.html with the help of ModelAndView Class.
        ModelAndView modelAndView = new ModelAndView("profile.html");
        modelAndView.addObject("profile", profile);
        return modelAndView;
    }

    @PostMapping("/updateProfile")
    public String updateProfilePage(@Valid @ModelAttribute("profile") UserProfile profile,
                          Errors errors, HttpSession session){
        if(errors.hasErrors())
            return "profile.html";

        Person person = (Person) session.getAttribute("loggedInPerson");
        //Setting the profile data by getting data from front-end form to person database.
        person.setName(profile.getName());
        person.setEmail(profile.getEmail());
        person.setMobileNumber(profile.getMobileNumber());

        if(person.getAddress() == null || !( person.getAddress().getAddressId() > 0) ){
            person.setAddress(new Address());
        }

        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setAddress2(profile.getAddress2());
        person.getAddress().setState(profile.getState());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setZip_code(profile.getZip_code());
        Person savedPerson = personRepository.save(person);

        session.setAttribute("loggedInPerson", savedPerson);

        return "redirect:/displayProfile";
    }
}
