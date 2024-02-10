package example;


import example.classes.Library;
import example.config.MyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Ex05 {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(MyConfig.class);

    Library library = context.getBean("libraryBean", Library.class);
    library.getBook();
    library.returnBook();
    library.addBook();
    library.getMagazine();

    context.close();
  }
}
