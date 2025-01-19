import java.util.TreeMap;

/**
 * Comparison of TreeMap and Sorted List with Binary Search:
 * Let’s compare the efficiency of the two approaches, specifically for insertion and query operations:
 * <p>
 * 1. TreeMap (Red-Black Tree or other balanced binary search trees)
 * Insertion: O(log N)
 * <p>
 * A TreeMap internally uses a self-balancing binary search tree (like a red-black tree).
 * When you insert an element, the tree rebalances itself to ensure that it remains balanced,
 * which ensures a logarithmic time complexity for insertion.
 * Query (Get Specific Timestamp/Price): O(log N)
 * <p>
 * The TreeMap allows efficient searching of keys in logarithmic time because of its balanced nature.
 * So querying for a specific timestamp or for a timestamp that is less than or greater
 * than a given timestamp also takes O(log N) time.
 * Deletion: O(log N)
 * <p>
 * Deleting an element is also efficient because it requires logarithmic time to find the element and then to rebalance the tree.
 * Overall Complexity for TreeMap:
 * <p>
 * Insertion: O(log N)
 * Query: O(log N)
 * Deletion: O(log N)
 * 2. Sorted List with Binary Search
 * Insertion: O(N)
 * In a sorted list (like ArrayList), when you perform an insertion,
 * you need to shift the elements in the list to maintain the sorted order.
 * While finding the correct insertion position with binary search is O(log N),
 * the actual insertion requires shifting elements, which takes O(N) in the worst case.
 * Query (Get Specific Timestamp/Price): O(log N)
 * Binary search in a sorted list allows you to find a specific timestamp or price in O(log N) time, since the list is sorted.
 * Deletion: O(N)
 * Similar to insertion, deleting an element requires finding it first
 * (which is O(log N)) and then shifting elements, resulting in O(N) for deletion.
 * Overall Complexity for Sorted List with Binary Search:
 * <p>
 * Insertion: O(N)
 * Query: O(log N)
 * Deletion: O(N)
 */
public class SimpleStockPriceManager {

  /**
   * TreeMap is more efficient for all operations, particularly insertions and deletions.
   * Insertion and deletion in a TreeMap are O(log N), which is much better than the O(N) for the same operations in a sorted list.
   * TreeMap also avoids the need for shifting elements, which can become costly when the list grows large.
   * Sorted List with Binary Search might still be efficient
   * for query operations when updates are infrequent,
   * but for dynamic applications where data is constantly being added or removed,
   * <p>
   * TreeMap provides a more efficient and scalable solution.
   */
  TreeMap<Integer, Integer> priceByTs = new TreeMap<>();

  private int getNearestEarlierStockPrice(int ts) {
    var price = priceByTs.floorEntry(ts);
    return price == null ? -1 : price.getValue();
  }

  private Integer getStockPriceAt(int ts) {
    return priceByTs.get(ts);
  }

  private void addStockPrice(int ts, int price) {
    priceByTs.put(ts, price);
  }

  public static void main(String[] args) {
    SimpleStockPriceManager system = new SimpleStockPriceManager();

    // Adding stock prices at different timestamps
    system.addStockPrice(1, 100);
    system.addStockPrice(2, 150);
    system.addStockPrice(4, 200);
    system.addStockPrice(5, 250);

    // Query 1: Get price at timestamp 2
    System.out.println("Price at timestamp 2: " + system.getStockPriceAt(2)); // Output: 150

    // Query 2: Get price at the nearest earlier time to timestamp 3
    System.out.println("Nearest price at or before timestamp 3: " + system.getNearestEarlierStockPrice(3)); // Output: 150

    // Query 2: Get price at the nearest earlier time to timestamp 4
    System.out.println("Nearest price at or before timestamp 4: " + system.getNearestEarlierStockPrice(4)); // Output: 200

    // Query 2: Get price at the nearest earlier time to timestamp 6
    System.out.println("Nearest price at or before timestamp 6: " + system.getNearestEarlierStockPrice(6)); // Output: 250
  }

}
