package example;


import example.classes.Library;
import example.classes.Student;
import example.classes.Univesity;
import example.config.MyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Ex11 {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(MyConfig.class);

    Library library = context.getBean("libraryBean", Library.class);

    String book = library.returnBookEx11();
    System.out.println("Мы вернули " + book);

    context.close();
  }
}
