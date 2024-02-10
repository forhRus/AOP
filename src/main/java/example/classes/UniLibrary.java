package example.classes;

import org.springframework.stereotype.Component;

@Component
public class UniLibrary extends AbstractLibrary {

  @Override
  public void getBook() {
    System.out.println("Мы берём книгу из UniLibrary");
  }

  @Override
  public void returnBook() {
    System.out.println("Мы возвращаем книгу в UniLibrary");
  }

  @Override
  public void getMagazine() {
    System.out.println("Мы берём журнал из UniLibrary");
  }
}
