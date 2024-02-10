package example.classes;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Univesity {
  private List<Student> students = new ArrayList<>();
   public void init(){
     students.add(new Student("Shkaev", 4, 10));
     students.add(new Student("Sidorov", 3, 9.7));
     students.add(new Student("Ivanov", 2, 7.1));
   }

   public List<Student> getStudents(){
     System.out.println("Список студентов:");

     System.out.println(students.get(3));

     System.out.println(students);
     return students;
   }
}
