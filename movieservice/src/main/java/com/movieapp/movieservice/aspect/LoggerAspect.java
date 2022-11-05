package com.movieapp.movieservice.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


/* Annotate this class with @Aspect and @Component */
@Aspect
@Component
public class LoggerAspect {

  
   private final Logger logger= LoggerFactory.getLogger(LoggerAspect.class);

   @Before(value="execution(* com.movieapp.movieservice.controller..*(..)))")
   public void beforeAllMethods(JoinPoint joinPoint) throws Throwable
   {
      joinPoint.getSignature().getName();
      logger.info("{} returned with value {}", joinPoint);
   }

   @After(value="execution(* com.movieapp.movieservice.controller..*(..)))")
   public void afterAllMethods(JoinPoint joinPoint) throws Throwable
   {
      joinPoint.getSignature().getName();
      logger.info("{} returned with value {}", joinPoint);
   }

   @AfterReturning(value="execution(* com.movieapp.movieservice.controller..*(..)))",returning = "result")
   public void afterRetAllMethods(JoinPoint joinPoint, Object result) {

      joinPoint.getSignature().getName();
      logger.info("{} returned with value {}", joinPoint,result);

   }

   @AfterThrowing(value="execution(* com.movieapp.movieservice.controller..*(..)))")
   public void AfterThAllMethods(JoinPoint joinPoint) throws Throwable
   {
      joinPoint.getSignature().getName();
      logger.info("{} returned with value {}", joinPoint);
   }

}