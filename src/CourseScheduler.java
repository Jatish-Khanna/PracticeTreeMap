import java.util.*;

public class CourseScheduler {

  // TreeMap to store courses with their start time as the key and end time as the value
  private final TreeMap<Integer, Integer> courseSchedule = new TreeMap<>();

  // Method to add a course to the system
  public boolean addCourse(int startTime, int endTime) {
    // Check if the new course overlaps with existing courses
    Map.Entry<Integer, Integer> floorEntry = courseSchedule.floorEntry(startTime);
    Map.Entry<Integer, Integer> ceilingEntry = courseSchedule.ceilingEntry(startTime);

    // Check for overlap with the course before or after
    if (floorEntry != null && floorEntry.getValue() > startTime) {
      return false; // Overlap with an existing course
    }

    if (ceilingEntry != null && ceilingEntry.getKey() < endTime) {
      return false; // Overlap with an existing course
    }

    // No overlap, so add the course
    courseSchedule.put(startTime, endTime);
    return true; // Successfully added
  }

  // Method to get all courses within a specified time range
  public List<Map.Entry<Integer, Integer>> getCoursesInRange(int startTime, int endTime) {
    return new ArrayList<>(courseSchedule.subMap(startTime, true, endTime, true).entrySet());
  }

  // Method to check if a student can be enrolled in a course
  public boolean canEnroll(int startTime, int endTime) {
    return addCourse(startTime, endTime);
  }

  public static void main(String[] args) {
    // Create the course scheduler system
    CourseScheduler scheduler = new CourseScheduler();

    // Add some courses
    System.out.println(scheduler.addCourse(9, 11)); // true
    System.out.println(scheduler.addCourse(13, 15)); // true
    System.out.println(scheduler.addCourse(10, 12)); // false (overlaps with 9-11)
    System.out.println(scheduler.addCourse(11, 13)); // true
    System.out.println(scheduler.addCourse(14, 16)); // true

    // Get all courses between 9 and 15
    List<Map.Entry<Integer, Integer>> coursesInRange = scheduler.getCoursesInRange(9, 15);
    System.out.println("Courses between 9 and 15:");
    for (Map.Entry<Integer, Integer> course : coursesInRange) {
      System.out.println("Start: " + course.getKey() + ", End: " + course.getValue());
    }

    // Check if a new course can be enrolled
    System.out.println(scheduler.canEnroll(12, 14)); // true (no overlap)
    System.out.println(scheduler.canEnroll(14, 15)); // false (overlaps with 13-15)
  }
}
