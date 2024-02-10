package example;


import example.classes.Book;
import example.classes.Library;
import example.classes.SchoolLibrary;
import example.classes.UniLibrary;
import example.config.MyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Ex03 {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(MyConfig.class);

    Library library = context.getBean("libraryBean", Library.class);
    library.getBook();
    library.getMagazine();

    context.close();
  }
}
