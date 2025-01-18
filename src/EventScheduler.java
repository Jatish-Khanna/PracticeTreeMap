import java.util.*;

public class EventScheduler {

  // Event class to hold start and end times of each event
  static class Event {
    int start;
    int end;

    Event(int start, int end) {
      this.start = start;
      this.end = end;
    }

    @Override
    public String toString() {
      return "[" + start + ", " + end + "]";
    }
  }

  // List to store events
  private final List<Event> events = new ArrayList<>();

  // Method to add an event to the scheduler
  private void addEvent(int start, int end) {
    Event newEvent = new Event(start, end);
    events.add(newEvent);
  }

  // Method to find overlapping events
  private List<Event> findOverlappingEvents(int low, int high) {
    List<Event> overlappingEvents = new ArrayList<>();

    // Iterate through all events and check if they overlap with the given range [low, high]
    for (Event event : events) {
      // If the event overlaps with the given range
      if (event.start < high && event.end > low) {
        overlappingEvents.add(event);
      }
    }

    return overlappingEvents;
  }

  public static void main(String[] args) {
    // Create an instance of the EventScheduler
    EventScheduler scheduler = new EventScheduler();

    // Add events
    scheduler.addEvent(10, 15);  // Event 1: [10, 15]
    scheduler.addEvent(5, 12);   // Event 2: [5, 12]
    scheduler.addEvent(14, 20);  // Event 3: [14, 20]
    scheduler.addEvent(8, 18);   // Event 4: [8, 18]

    // Query for overlapping events with the range [6, 16]
    List<Event> overlapping = scheduler.findOverlappingEvents(6, 16);

    // Output the overlapping events
    overlapping.forEach(System.out::println);
  }
}
