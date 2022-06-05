package shadow.practice.school.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@Slf4j /** Lombok Library */
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {

    /** In memory role defining to the users and authentication based on roles */

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //.mvcMatchers(HttpMethod.POST, "/home").permitAll();
        // -> this configures the httpMethod for a http request.
        // http.authorizeRequests().csrf().disable() // disables the csrf security.
        http.csrf().ignoringAntMatchers("/saveMsg").and()
                .authorizeRequests()
                .mvcMatchers("/dashboard").authenticated()
                .mvcMatchers("/home").permitAll()
                .mvcMatchers("/contact").permitAll()
                .mvcMatchers("/saveMsg").permitAll()
                .mvcMatchers("/services/**").authenticated()
                .mvcMatchers("/about").permitAll()
                .and().formLogin().loginPage("/login")
                .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll()
                .and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll().
                and().httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("user").password("123456").roles("USER")
                .and()
                .withUser("admin").password("iamin").roles("USER", "ADMIN")
                .and().passwordEncoder(NoOpPasswordEncoder.getInstance());
        //NoOpPasswordEncoder -> password encryption using Spring password encoder.
    }

}


//    @Override
//    protected void configure( HttpSecurity http ) throws Exception{
//        log.info("Overriding spring security configuration");
//        http.authorizeRequests().anyRequest().permitAll()
//                .and().formLogin()
//                .and().httpBasic();
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        log.info("Overriding spring security configuration for denying all");
//        http.authorizeRequests().anyRequest().denyAll()
//                .and().formLogin()
//                .and().httpBasic();
//    }