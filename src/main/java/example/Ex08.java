package example;


import example.classes.Book;
import example.classes.Library;
import example.config.MyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Ex08 {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(MyConfig.class);

    Book book = context.getBean("bookBean", Book.class);

    Library library = context.getBean("libraryBean", Library.class);

    library.addBook("Sergey", book);
    System.out.println("------------------");
    library.addMagazin();

    context.close();
  }
}
