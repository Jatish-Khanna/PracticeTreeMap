import java.util.*;

/**
 * Problem: Managing a Store's Inventory
 * You are tasked with managing the inventory of a store where items are tracked by their
 * ID and their price. The store has a collection of items, and you need
 * to implement operations to interact with this inventory using a TreeMap.
 * <p>
 * Each item in the inventory has the following information:
 * <p>
 * Item ID: A unique identifier (integer).
 * Price: A price of the item (integer).
 * You need to implement a class called StoreInventory that can perform the following operations efficiently:
 * <p>
 * Operations:
 * Add an item: Add an item to the store’s inventory by its ID and price.
 * Remove an item: Remove an item from the inventory based on its ID.
 * Get item price: Return the price of an item by its ID.
 * Get items in price range: Given a price range, return a list of item IDs that fall within the range (inclusive of both bounds).
 * Get the most expensive item: Return the ID and price of the most expensive item.
 * Get the least expensive item: Return the ID and price of the least expensive item.
 * Get item just cheaper than a given price: Find the item with
 * the highest price that is strictly cheaper than the given price.
 * <p>
 * Get item just more expensive than a given price:
 * Find the item with the lowest price that is strictly more expensive than the given price.
 * <p>
 * Get the next higher price: Given a price, find the next higher price, if any, in the store inventory.
 * Get all items cheaper than a given price: Return all items that are cheaper than the given price.
 * Get all items more expensive than a given price: Return all items that are more expensive than the given price.
 * Constraints:
 * The number of items in the inventory can go up to 10,000.
 * All items have a unique ID and a positive integer price.
 */

public class StoreInventorySystem {

  /**
   * Summary of Time Complexities:
   * O(log N): For operations involving TreeMap search or insertion
   * (like getLeastExpensiveItem, getMostExpensiveItem, getItemJustCheaperThan, etc.).
   * <p>
   * O(log N + K): For operations that return multiple entries
   * from the TreeMap (like getItemsInPriceRange, getAllItemsCheaperThan, and getAllItemsMoreExpensiveThan).
   * <p>
   * O(1): For operations involving HashMap lookups (getItemPrice, containsKey, etc.).
   */

  static class Item {
    int id;
    int price;

    Item(int id, int price) {
      this.id = id;
      this.price = price;
    }

    public String toString() {
      return "[Id: " + id + " price: " + price + "]";
    }
  }

  private final TreeMap<Integer, Set<Item>> itemsByPrice = new TreeMap<>();
  private final Map<Integer, Item> itemsById = new HashMap<>();

  /**
   * firstEntry() on a TreeMap takes O(log N) time, where N is the number of distinct prices (keys) in the TreeMap.
   *
   * @return
   */
  private Set<Item> getLeastExpensiveItem() {
    return itemsByPrice.firstEntry().getValue();
  }

  /**
   * lastEntry() on a TreeMap is also O(log N).
   *
   * @return
   */
  private Set<Item> getMostExpensiveItem() {
    return itemsByPrice.lastEntry().getValue();
  }

  /**
   * The subMap(start, true, end, true) operation retrieves all the entries between start and end (inclusive).
   * Since TreeMap is a balanced tree structure, the time complexity for the subMap operation is O(log N + K), where:
   * O(log N) for finding the boundaries (start and end).
   * O(K) for iterating through the entries between start and end, where K is the number of entries in the range.
   * Therefore, the time complexity for this operation is O(log N + K).
   *
   * @param start
   * @param end
   * @return
   */
  private Map<Integer, Set<Item>> getItemsInPriceRange(int start, int end) {
    return itemsByPrice.subMap(start, true, end, true);
  }

  /**
   * containsKey(id) in itemsById is O(1) (since it's a HashMap).
   * get(id) in itemsById is also O(1).
   * lowerEntry(price) in TreeMap is O(log N).
   * Returning the value associated with the lower entry is a simple lookup in a Set, which is O(1).
   * Therefore, the overall complexity is O(log N).
   *
   * @param id
   * @return
   */
  private Set<Item> getItemJustCheaperThan(int id) {
    if (!itemsById.containsKey(id)) {
      return Set.of();
    }
    Item item = itemsById.get(id);
    var lowerPrice = itemsByPrice.lowerEntry(item.price);
    return lowerPrice == null ? Set.of() : lowerPrice.getValue();
  }

  /**
   * containsKey(id) and get(id) are both O(1).
   * higherEntry(price) in TreeMap is O(log N).
   * Returning the value associated with the higher entry is O(1).
   * Overall, the time complexity is O(log N).
   *
   * @param id
   * @return
   */
  private Set<Item> getItemJustMoreExpensiveThan(int id) {
    if (!itemsById.containsKey(id)) {
      return Set.of();
    }
    Item item = itemsById.get(id);
    var lowerPrice = itemsByPrice.higherEntry(item.price);
    return lowerPrice == null ? Set.of() : lowerPrice.getValue();
  }

  /**
   * containsKey(id) and get(id) are O(1).
   * higherKey(price) in TreeMap is O(log N).
   * Thus, the overall time complexity is O(log N).
   *
   * @param id
   * @return
   */
  private Integer getNextHigherPrice(int id) {
    if (!itemsById.containsKey(id)) {
      return -1;
    }
    Item item = itemsById.get(id);
    var higherPrice = itemsByPrice.higherKey(item.price);
    return higherPrice == null ? -1 : higherPrice;
  }

  /**
   * containsKey(id) and get(id) are O(1).
   * headMap(price, false) in TreeMap is O(log N + K), where:
   * O(log N) to find the boundary.
   * O(K) to iterate through the entries with prices strictly less than item.price.
   * Therefore, the overall time complexity is O(log N + K).
   *
   * @param id
   * @return
   */
  private Map<Integer, Set<Item>> getAllItemsCheaperThan(int id) {
    if (!itemsById.containsKey(id)) {
      return Map.of();
    }
    Item item = itemsById.get(id);
    return itemsByPrice.headMap(item.price, false);
  }

  /**
   * containsKey(id) and get(id) are O(1).
   * tailMap(price, false) in TreeMap is O(log N + K), where:
   * O(log N) to find the boundary.
   * O(K) to iterate through the entries with prices strictly greater than item.price.
   * Therefore, the overall time complexity is O(log N + K).
   *
   * @param id
   * @return
   */
  private Map<Integer, Set<Item>> getAllItemsMoreExpensiveThan(int id) {
    if (!itemsById.containsKey(id)) {
      return Map.of();
    }
    Item item = itemsById.get(id);
    return itemsByPrice.tailMap(item.price, false);
  }

  /**
   * containsKey(id) and get(id) in itemsById are O(1).
   * remove(id) in itemsById is O(1).
   * remove(item) from itemsByPrice is O(log N) for locating the price group in the TreeMap and O(1) for removing the item from the Set.
   * Removing the price entry if empty is O(1).
   * Therefore, the overall time complexity is O(log N).
   *
   * @param id
   * @return
   */
  private Item removeItem(int id) {
    if (!itemsById.containsKey(id)) {
      return null;
    }
    Item item = itemsById.remove(id); // O(1)
    itemsByPrice.get(item.price).remove(item); // O(1) HashSet - find the item
    if (itemsByPrice.get(item.price).isEmpty()) {
      itemsByPrice.remove(item.price); // O(log n) remove the item from TreeMap
    }
    return item;
  }

  /**
   * get(id) in itemsById is O(1).
   * Thus, the time complexity is O(1).
   *
   * @param id
   * @return
   */
  private int getItemPrice(int id) {
    Item item = itemsById.get(id);
    return item == null ? -1 : item.price;
  }

  /**
   * get(id) in itemsById is O(1).
   * removeItem(id) has a complexity of O(log N) (as it involves removing the item from TreeMap).
   * computeIfAbsent(price, ...) in TreeMap is O(log N) to locate or insert the price group.
   * put(id, item) in itemsById is O(1).
   * Overall, the complexity is O(log N).
   *
   * @param id
   * @param price
   */

  private void addItem(int id, int price) {
    Item item = itemsById.get(id); // O(1) get the item by the id
    if (item != null && item.price == price) {
      return;
    } else if (item != null) { // price isn't same as current item
      removeItem(id); // O(log n) remove from the treeMap
      item.price = price;
    } else {
      item = new Item(id, price);
    }

    itemsByPrice.computeIfAbsent(price, (val) -> new HashSet<>()).add(item); // add to treeMap log n and add to set O(1)
    itemsById.put(id, item); // O(1) add to hash map
  }

  public static void main(String[] args) {
    StoreInventorySystem store = new StoreInventorySystem();

// Adding items to the inventory
    store.addItem(1, 100);
    store.addItem(2, 200);
    store.addItem(3, 150);
    store.addItem(4, 50);

// Get price of item 2
    System.out.println("Fetching price of item 2...");
    System.out.println("Price of item 2: " + store.getItemPrice(2));  // Output: 200

// Remove item 3
    System.out.println("Removing item 3 from the inventory..." + store.removeItem(3));


// Get items with prices between 100 and 200
    System.out.println("Fetching items with prices between 100 and 200...");
    System.out.println("Items with prices between 100 and 200: " + store.getItemsInPriceRange(100, 200));  // Output: [1, 2]

// Get the most expensive item
    System.out.println("Fetching the most expensive item...");
    System.out.println("Most expensive item: " + store.getMostExpensiveItem());  // Output: Item ID: 2, Price: 200

// Get the least expensive item
    System.out.println("Fetching the least expensive item...");
    System.out.println("Least expensive item: " + store.getLeastExpensiveItem());  // Output: Item ID: 4, Price: 50

// Get the item just cheaper than 170
    System.out.println("Fetching the item just cheaper than 170...");
    System.out.println("Item just cheaper than 170: " + store.getItemJustCheaperThan(2));  // Output: Item ID: 1, Price: 100

// Get the item just more expensive than 170
    System.out.println("Fetching the item just more expensive than 170...");
    System.out.println("Item just more expensive than 170: " + store.getItemJustMoreExpensiveThan(4));  // Output: Item ID: 2, Price: 200

// Get the next higher price after 150
    System.out.println("Fetching the next higher price after 150...");
    System.out.println("Next higher price after : " + store.getNextHigherPrice(1));  // Output: 200

// Get all items cheaper than 150
    System.out.println("Fetching all items cheaper than ...");
    System.out.println("Items cheaper than : " + store.getAllItemsCheaperThan(2));  // Output: [1, 4]

// Get all items more expensive than 150
    System.out.println("Fetching all items more expensive than ...");
    System.out.println("Items more expensive than : " + store.getAllItemsMoreExpensiveThan(4));  // Output: [2]

  }


}
