package example.classes;

import example.aspects.LogExecutionTime;
import org.springframework.stereotype.Component;

@Component("libraryBean")
public class Library {
  public void getBook(){
    System.out.println("Мы берём книгу");
  }
  public void getBook(Book book){
    System.out.println("Мы берём книгу " + book.getName());
  }
  public void getMagazine(){
    System.out.println("Мы берём журнал");
  }
  public void addBook(){
    System.out.println("Мы добавляем книгу");
  }
  public void addBook(String personName, Book book){
    System.out.println(String.format("%s добавил книгу %s", personName, book.getName()));
  }

  public void addMagazin(){
    System.out.println("Мы добавляем журнал");
  }
  public void returnBook(){
    System.out.println("Мы возвращаем книгу");
  }

  public String returnBookEx11(){
    System.out.println("returnBookEx11: Мы возвращаем книгу");
    return "Война и мир";
  }

  public void returnMagazin(){
    System.out.println("Мы возвращаем журнал");
  }

  @LogExecutionTime
  public void work() throws InterruptedException {
    Thread.sleep(2000);
  }
}
