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

  private Set<Item> getLeastExpensiveItem() {
    return itemsByPrice.firstEntry().getValue();
  }

  private Set<Item> getMostExpensiveItem() {
    return itemsByPrice.lastEntry().getValue();
  }

  private Map<Integer, Set<Item>> getItemsInPriceRange(int start, int end) {
    return itemsByPrice.subMap(start, true, end, true);
  }

  private Set<Item> getItemJustCheaperThan(int id) {
    if (!itemsById.containsKey(id)) {
      return Set.of();
    }
    Item item = itemsById.get(id);
    var lowerPrice = itemsByPrice.lowerEntry(item.price);
    return lowerPrice == null ? Set.of() : lowerPrice.getValue();
  }

  private Set<Item> getItemJustMoreExpensiveThan(int id) {
    if (!itemsById.containsKey(id)) {
      return Set.of();
    }
    Item item = itemsById.get(id);
    var lowerPrice = itemsByPrice.higherEntry(item.price);
    return lowerPrice == null ? Set.of() : lowerPrice.getValue();
  }

  private Integer getNextHigherPrice(int id) {
    if (!itemsById.containsKey(id)) {
      return -1;
    }
    Item item = itemsById.get(id);
    var higherPrice = itemsByPrice.higherKey(item.price);
    return higherPrice == null ? -1 : higherPrice;
  }

  private Map<Integer, Set<Item>> getAllItemsCheaperThan(int id) {
    if (!itemsById.containsKey(id)) {
      return Map.of();
    }
    Item item = itemsById.get(id);
    return itemsByPrice.headMap(item.price);
  }

  private Map<Integer, Set<Item>> getAllItemsMoreExpensiveThan(int id) {
    if (!itemsById.containsKey(id)) {
      return Map.of();
    }
    Item item = itemsById.get(id);
    return itemsByPrice.tailMap(item.price);
  }

  private Item removeItem(int id) {
    if (!itemsById.containsKey(id)) {
      return null;
    }
    Item item = itemsById.remove(id);
    itemsByPrice.get(item.price).remove(item);
    return item;
  }

  private int getItemPrice(int id) {
    Item item = itemsById.get(id);
    return item == null ? -1 : item.price;
  }

  private void addItem(int id, int price) {
    Item item = itemsById.get(id);
    if (item != null && item.price == price) {
      return;
    } else if (item != null) { // price isn't same as current item
      removeItem(id);
      item.price = price;
    } else {
      item = new Item(id, price);
    }

    itemsByPrice.computeIfAbsent(price, (val) -> new HashSet<>()).add(item);
    itemsById.put(id, item);
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
    System.out.println("Items with prices between 100 and 200 inclusive: " + store.getItemsInPriceRange(100, 200));  // Output: [1, 2]

// Get the most expensive item
    System.out.println("Fetching the most expensive item...");
    System.out.println("Most expensive item: " + store.getMostExpensiveItem());  // Output: Item ID: 2, Price: 200

// Get the least expensive item
    System.out.println("Fetching the least expensive item...");
    System.out.println("Least expensive item: " + store.getLeastExpensiveItem());  // Output: Item ID: 4, Price: 50

// Get the item just cheaper than 170
    System.out.println("Fetching the item just cheaper than 170...");
    System.out.println("Item just cheaper than 170: " + store.getItemJustCheaperThan(170));  // Output: Item ID: 1, Price: 100

// Get the item just more expensive than 170
    System.out.println("Fetching the item just more expensive than 170...");
    System.out.println("Item just more expensive than 170: " + store.getItemJustMoreExpensiveThan(170));  // Output: Item ID: 2, Price: 200

// Get the next higher price after 150
    System.out.println("Fetching the next higher price after 150...");
    System.out.println("Next higher price after 150: " + store.getNextHigherPrice(150));  // Output: 200

// Get all ite


  }


}
