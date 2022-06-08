package shadow.practice.portfolio.Aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

@Slf4j
@Aspect
@Component
public class LoggerAspect {
    @Around("execution(* shadow.practice.portfolio..*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info(joinPoint.getSignature().toString() + " method execution start");

        Instant start = Instant.now();
        // proceeding with web application methods - all
        // And returning an object.
        Object returnObj = joinPoint.proceed();

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        log.info("Time took to execute " + joinPoint.getSignature().toString() + " method is " + timeElapsed);
        log.info(joinPoint.getSignature().toString() + "method execution ends");

        return returnObj;
    }

    @AfterThrowing(value = "execution(* shadow.practice.portfolio..*.*(..))", throwing = "ex")
    public void logExecption(JoinPoint joinPoint, Exception ex){
        log.error(joinPoint.getSignature() + " An Exception Occur Due To: " + ex.getMessage());
    }
}
