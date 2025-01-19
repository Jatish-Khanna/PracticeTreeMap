public class StudentGradingSystem {

  public static void main(String[] args) {
    StudentGrades studentGrades = new StudentGrades();
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
