package example;


import example.classes.Student;
import example.classes.Univesity;
import example.config.MyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Ex09 {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(MyConfig.class);

    Univesity univesity = context.getBean("univesity", Univesity.class);
    univesity.init();

    List<Student> students = univesity.getStudents();
    System.out.println(students);

    context.close();
  }
}
