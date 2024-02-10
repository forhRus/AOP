package example.aspects;

import example.classes.Book;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;



@Component
@Aspect
@Order(1)
public class LoggingAspect2 {

  // ex07
//  @Before("example.aspects.MyPointcuts.allGetMethods()")
//  public void beforeAllGetLoggingAdvice(){
//    System.out.println("beforeAllGetLoggingAdvice: лог №5");
//  }

  //08
    @Before("example.aspects.MyPointcuts.allAddMethods()")
  public void beforeAllGetLoggingAdvice(JoinPoint joinPoint){

      MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

      System.out.println("methodSignature = " + methodSignature);
      System.out.println("methodSignature.getMethod() = " + methodSignature.getMethod());
      System.out.println("methodSignature.getReturnType = " + methodSignature.getReturnType());
      System.out.println("methodSignature.getName() = " + methodSignature.getName());

      if(methodSignature.getName().equals("addBook")){
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {

          if(arg instanceof Book) {
            Book newBook = (Book) arg;
            System.out.println(newBook);
          } else if (arg instanceof String){
            System.out.println("Книгу добавляет: " + arg);
          }
        }
      }
  }
}
