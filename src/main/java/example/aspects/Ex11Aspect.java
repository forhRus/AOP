package example.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Ex11Aspect {

  // ex11
  @Around("execution(public String returnBookEx11())")
  public Object aroundReturnBookLoggingAdvice(ProceedingJoinPoint proceedingJoinPoint)  {
    System.out.println("aroundReturnBookLoggingAdvice: в библиотеку пытаются вернуть книгу");
    long time = 0;
    try {

      long start = System.currentTimeMillis();
      Object targetMethodResult = proceedingJoinPoint.proceed();
      long end = System.currentTimeMillis();
      time = end - start;
    } catch (Throwable e){
      System.out.println("Поймали " + e);
    }

    System.out.println("aroundReturnBookLoggingAdvice: в библиотеку успешно вернули книгу");
    System.out.printf("returnBookEx11() - выполнил работу за %d милисекунд\n", time);

    return "А вернули, не пойми что";
  }
}
