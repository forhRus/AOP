package example.aspects;

import example.classes.Student;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
public class UniversityLoggingAspect {

  // ex09
//  @Before("execution(* getStudents())")
//  public void beforeGetStudentsLoggingAdvice(){
//    System.out.println("beforeGetStudentsLoggingAdvice: логируем получение " +
//            "списка студентов перед методом getStudents");
//  }
//
//  @AfterReturning(pointcut = "execution(* getStudents())",
//      returning = "students")
//  public void afterReturningGetStudentsLoggingAdvice(List<Student> students){
//    Student student = students.get(0);
//
//    student.setSurname("newName");
//    student.setAvgGrade(0);
//    student.setAvgGrade(0);
//
//    System.out.println("afterReturningGetStudentsLoggingAdvice: логируем получение " +
//            "списка студентов после работы метода getStudents");
//  }

  //ex10
//  @AfterThrowing("execution(* getStudents())")
//  public void AfterThrowingGetStudentsLoggingAdvice(){
//    System.out.println("AfterThrowingGetStudentsLoggingAdvice: " +
//            "логируем выброс исключения");
//  }

  @AfterThrowing(pointcut = "execution(* getStudents())",
    throwing = "exception")
  public void AfterThrowingGetStudentsLoggingAdvice(Throwable exception){
    System.out.println("AfterThrowingGetStudentsLoggingAdvice: " +
            "логируем выброс исключения " + exception);
  }

}
