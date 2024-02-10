package example.classes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("bookBean")
public class Book {
  @Value("Преступление и наказание")
  private String name;

  @Value("Ф.М. Достоевский")
  private String author;

  @Value("1866")
  private int yearOfPublication;

  @Override
  public String toString() {
    return "Book{" +
            "name='" + name + '\'' +
            ", author='" + author + '\'' +
            ", yearOfPublication=" + yearOfPublication +
            '}';
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getYearOfPublication() {
    return yearOfPublication;
  }

  public void setYearOfPublication(int yearOfPublication) {
    this.yearOfPublication = yearOfPublication;
  }

  public String getName() {
    return name;
  }
}
