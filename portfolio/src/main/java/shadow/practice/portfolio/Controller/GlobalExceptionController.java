package shadow.practice.portfolio.Controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ControllerAdvice -> is a specialization of the @Component annotation.
 * Which handles exceptions across the application in one global handling component.
 * It works as an interceptor, which intercepts error thrown by @RequestMapping and similar Annotations.
 */

@ControllerAdvice
public class GlobalExceptionController {

    /**
     * @ExceptionHandler -> will register the given method for a given particular exception type,
     * so that @ControllerAdvice can invoke this method logic if a given exception type is thrown
     * inside the application.
     * @param exception -> takes an input param of exception class. The exception can be any type of exception,
     *                  such as NullPointerException, RunTimeException, Arithmetic Exception,
     *                  Overflow and underflow Exception, etc. so many more.
     * @return ModelAndView object.
     */

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception exception){
        ModelAndView errorPage = new ModelAndView();
        errorPage.setViewName("error");
        errorPage.addObject("errormsg", exception.getMessage());
        return errorPage;
    }
}
