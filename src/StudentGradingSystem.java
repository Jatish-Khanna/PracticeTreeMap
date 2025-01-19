import java.util.*;

public class StudentGradingSystem {

  static class Student {
    String name;
    int grade;

    Student(String name, int grade) {
      this.name = name;
      this.grade = grade;
    }

    public String toString() {
      return name + " " + grade;
    }
  }

  TreeMap<Integer, Set<Student>> studentsByGrade = new TreeMap<>();
  Map<String, Student> studentsByName = new HashMap<>();

  private Collection<Set<Student>> getStudentsInGradeRange(int g1, int g2) {

    var studentsInRange = studentsByGrade.subMap(g1, true, g2, true);
    return studentsInRange.values();

  }

  private Set<Student> getLowestStudent() {
    var students = studentsByGrade.firstEntry();
    return students == null ? Set.of() : students.getValue();
  }

  private Set<Student> getTopStudent() {
    var students = studentsByGrade.lastEntry();
    return students == null ? Set.of() : students.getValue();
  }

  private int getGrade(String name) {
    Student student = studentsByName.get(name);
    return student == null ? -1 : student.grade;
  }

  private void addStudent(String name, int grade) {
    Student student = studentsByName.get(name);
    if (student == null) {
      student = new Student(name, grade);
    } else if (student.grade == grade) {
      return;
    } else {
      student = removeStudent(name); // we have checked student will always exists
      student.grade = grade;
    }
    studentsByName.put(name, new Student(name, grade));
    studentsByGrade.computeIfAbsent(grade, (g) -> new HashSet<>()).add(student);

  }

  private Student removeStudent(String name) {
    if (!studentsByName.containsKey(name)) {
      return null;
    }
    Student student = studentsByName.remove(name);
    studentsByGrade.get(student.grade).remove(student);
    if (studentsByGrade.get(student.grade).isEmpty()) {
      studentsByGrade.remove(student.grade);
    }
    return student;
  }

  public static void main(String[] args) {
    StudentGradingSystem studentGrades = new StudentGradingSystem();
    studentGrades.addStudent("Alice", 95);
    studentGrades.addStudent("Bob", 89);
    studentGrades.addStudent("Charlie", 72);
    studentGrades.addStudent("Dave", 88);

    System.out.println("Alice's grade: " + studentGrades.getGrade("Alice"));
    System.out.println("Top student: " + studentGrades.getTopStudent());
    System.out.println("Lowest student: " + studentGrades.getLowestStudent());

    System.out.println("Students with grades between 80 and 95: " + studentGrades.getStudentsInGradeRange(80, 95));
  }

}
