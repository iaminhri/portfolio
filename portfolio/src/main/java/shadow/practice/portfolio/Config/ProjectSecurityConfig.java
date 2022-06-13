package shadow.practice.portfolio.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import shadow.practice.portfolio.Security.PortfolioUserAuthProvider;

@Configuration
//@EnableWebSecurity // Spring boot autoconfigures this annotation.
//@ComponentScan("shadow.practice.portfolio.Security")
@Slf4j /** Lombok Library */
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {

    /** In memory role defining to the users and authentication based on roles */

    @Autowired
    private PortfolioUserAuthProvider authProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //.mvcMatchers(HttpMethod.POST, "/home").permitAll();
        // -> this configures the httpMethod for a http request.
        // http.authorizeRequests().csrf().disable() // disables the csrf security.
//        http.csrf().ignoringAntMatchers("/saveMsg").ignoringAntMatchers("/h2-console/**").and()
        http.csrf().ignoringAntMatchers("/saveMsg")
                .ignoringAntMatchers("/public/**") // Ignoring CSRF for the directory starting with /public and afterwards.
                .and()
                .authorizeRequests()
                .mvcMatchers("/dashboard").authenticated()
                .mvcMatchers("/displayMessages").hasRole("ADMIN")
                .mvcMatchers("/home").permitAll()
                .mvcMatchers("/contact").permitAll()
                .mvcMatchers("/saveMsg").permitAll()
                .mvcMatchers("/services/**").authenticated()
                .mvcMatchers("/about").permitAll()
                .mvcMatchers("/public/**").permitAll()
                .and().formLogin().loginPage("/login")
                .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll()
                .and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll().
//                and().authorizeRequests().antMatchers("/h2-console/**").permitAll().
                and().httpBasic();

//        http.headers().frameOptions().disable();
//        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }

/**    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.inMemoryAuthentication()
//                .withUser("user").password("123456").roles("USER")
//                .and()
//                .withUser("admin").password("iamin").roles("ADMIN")
//                .and().passwordEncoder(NoOpPasswordEncoder.getInstance());
//        //NoOpPasswordEncoder -> password encryption using Spring password encoder.

//        auth.authenticationProvider(authProvider); // Spring boot Automatically executes this code.
    }*/

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