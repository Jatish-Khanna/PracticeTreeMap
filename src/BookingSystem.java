import java.util.TreeMap;

public class BookingSystem {

  TreeMap<Integer, Integer> bookings = new TreeMap<>();

  private boolean canBook(int start, int end) {
    var lowest = bookings.floorEntry(start);
//    var highest = bookings.lowerEntry(end);

    var highest = bookings.ceilingEntry(start);

    return !(lowest != null && lowest.getValue() > start ||
        highest != null && highest.getValue() < end);
  }

  private void addBooking(int start, int end) {
    // all the non overlap bookings
    bookings.put(start, end);
  }


  public static void main(String[] args) {
    BookingSystem system = new BookingSystem();

// Add some bookings
    system.addBooking(10, 12); // Booking from 10 to 12
    system.addBooking(13, 15); // Booking from 13 to 15
    system.addBooking(15, 18); // Booking from 15 to 18
    system.addBooking(8, 10);  // Booking from 8 to 10

// Query if a new booking overlaps
    boolean canBook = system.canBook(12, 13); // Check if new booking from 12 to 13 can be made (should return true)
    boolean canBookConflict = system.canBook(11, 14); // Check if new booking from 11 to 14 can be made (should return false)
    System.out.println("Booking : " + canBook);
    System.out.println("Booking : " + canBookConflict);

  }
}
