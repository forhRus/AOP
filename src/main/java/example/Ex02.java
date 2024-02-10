package example;


import example.classes.Library;
import example.classes.SchoolLibrary;
import example.classes.UniLibrary;
import example.config.MyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Ex02 {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(MyConfig.class);

    UniLibrary uniLibrary = context.getBean("uniLibrary",
            UniLibrary.class);
    uniLibrary.getBook();
    uniLibrary.returnBook();
    uniLibrary.getMagazine();

    SchoolLibrary schoolLibrary = context.getBean("schoolLibrary",
            SchoolLibrary.class);
    schoolLibrary.getBook();

    context.close();
  }
}
