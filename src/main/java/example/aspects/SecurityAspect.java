package example.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(10)
public class SecurityAspect {

  // ex07
//  @Before("example.aspects.MyPointcuts.allGetMethods()")
//  public void beforeAllGetSecurityAdvice(){
//    System.out.println("beforeAllGetSecurityAdvice: лог №6");
//  }
}
