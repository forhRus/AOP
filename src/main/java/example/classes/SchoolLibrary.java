package example.classes;

import org.springframework.stereotype.Component;

@Component
public class SchoolLibrary  extends AbstractLibrary{
  @Override
  public void getBook() {
    System.out.println("Мы берём книгу из SchoolLibrary");
  }

  @Override
  public void returnBook() {
    System.out.println("Мы возвращаем книгу в SchoolLibrary");
  }

  @Override
  public void getMagazine() {
    System.out.println("Мы берём журнал из SchoolLibrary");
  }
}
