package shadow.practice.portfolio.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import shadow.practice.portfolio.Model.Person;
import shadow.practice.portfolio.Model.Roles;
import shadow.practice.portfolio.Repository.PersonRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class PortfolioUserAuthProvider implements AuthenticationProvider {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String pwd = authentication.getCredentials().toString();

        Person person = personRepository.readByEmail(email);

        //@passwordEncoder.matches() -> matches the password between user end password and hashed password from DB.
        if( person != null && person.getPersonId() > 0 &&
                passwordEncoder.matches(pwd, person.getPwd()) ){
            return new UsernamePasswordAuthenticationToken(
                    person.getName(), null, getGrantedAuthorities(person.getRoles())
            );
        }else
            throw new BadCredentialsException("Invalid Exception");

//
//        if(authenticatingUserBasedOnRequirement()){
//            return new UsernamePasswordAuthenticationToken(
//                    email, pwd, new ArrayList<>()
//            );
//        }else
//            return null;
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(Roles roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + roles.getRoleName()));
        return grantedAuthorities;
    }

    /**
     * looks for the support class based on authentication.
     * @param authentication
     * @return type of authentication class supports.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
