package shadow.practice.portfolio.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import shadow.practice.portfolio.Constants.PortfolioWebAppConstants;
import shadow.practice.portfolio.Model.Person;
import shadow.practice.portfolio.Model.Roles;
import shadow.practice.portfolio.Repository.PersonRepository;
import shadow.practice.portfolio.Repository.RolesRepository;

@Slf4j
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createNewPerson(Person person) {
        boolean isSaved = false;
        Roles roles = rolesRepository.getByRoleName(PortfolioWebAppConstants.USER_ROLE);
        person.setRoles(roles);

        //Hashing password using BCrypt Library.
        person.setPwd(passwordEncoder.encode(person.getPwd()));

        //saves information into the DB.
        person = personRepository.save(person);

        //validating person information
        if(person != null && person.getPersonId() > 0)
            isSaved = true;

        return isSaved;
    }
}
