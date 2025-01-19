import java.util.TreeMap;

public class SimpleStockPriceManager {

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
