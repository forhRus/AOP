package example.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect1 {

   // ex02
  @Before("execution(public void getBook(example.classes.Book))")
  public void beforeGetBookAdvice(){
    System.out.println("beforeGetBookAdvice: попытка получить книгу");
  }


  //ex03
//  @Before("execution(* get*())") // point cut - когда должен выполниться сквозной код
//  public void beforeGetLoggingAdvice(){
//    System.out.println("beforeGetLoggingAdvice: попытка получить книгу/журнал");
//  }
//  @Before("execution(* get*())") // point cut - когда должен выполниться сквозной код
//  public void beforeGetSecurityAdvice(){
//    System.out.println("beforeGetSecurityAdvice: проверка прав на получение " +
//            "книги/журнала");
//  }

  //ex04
  // Объявление Pointcut
//  @Pointcut("execution(* get*())")
//  private void allGetMethods(){}
//  @Before("allGetMethods()") // point cut - когда должен выполниться сквозной код
//  public void beforeGetLoggingAdvice(){
//    System.out.println("beforeGetLoggingAdvice: попытка получить книгу/журнал");
//  }
//  @Before("allGetMethods()") // point cut - когда должен выполниться сквозной код
//  public void beforeGetSecurityAdvice(){
//    System.out.println("beforeGetSecurityAdvice: проверка прав на получение " +
//            "книги/журнала");
//  }

  //  ex05
//  @Pointcut("execution(* example.classes.Library.get*())")
//  private void allGetMethodsGetFromLibrary(){}
//
//  @Pointcut("execution(* example.classes.Library.return*())")
//  private void allReturnMethodsGetFromLibrary(){}
//
//  @Pointcut("allGetMethodsGetFromLibrary() " +
//          "|| allReturnMethodsGetFromLibrary()")
//  private void allGetAndReturnMethodsGetFromLibrary(){}
//
//  @Before("allGetMethodsGetFromLibrary()")
//  public void beforeGetLoggingAdvice(){
//    System.out.println("beforeGetLoggingAdvice: лог №1");
//  }
//
//  @Before("allReturnMethodsGetFromLibrary()")
//  public void beforeReturnLoggingAdvice(){
//    System.out.println("beforeReturnLoggingAdvice: лог №2");
//  }
//
//  @Before("allGetAndReturnMethodsGetFromLibrary()")
//  public void beforeGetAndReturnLoggingAdvice(){
//    System.out.println("beforeReturnLoggingAdvice: лог №3");
//  }

  // ex06
//  @Pointcut("execution(* example.classes.Library.*(..))")
//  private void allMethodsFromLibrary(){}
//
//  @Pointcut("execution(public void example.classes.Library.returnMagazin())")
//  private void returnMagazineFromLibrary(){}
//
//  @Pointcut("allMethodsFromLibrary() " +
//          "&& !returnMagazineFromLibrary())")
//  private void allMethodsExceptReturnFromLibrary(){}
//
//  @Before("allMethodsExceptReturnFromLibrary()")
//  public void beforeAllMethodsExceptReturnFromLibraryAdvice(){
//    System.out.println("beforeAllMethodsExceptReturnFromLibraryAdvice: лог №4");
//  }
}
