package example;


import example.classes.Library;
import example.config.MyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Ex12 {
  public static void main(String[] args) throws InterruptedException {

    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(MyConfig.class);

    Library library = context.getBean("libraryBean", Library.class);

    library.work();

    context.close();
  }
}
