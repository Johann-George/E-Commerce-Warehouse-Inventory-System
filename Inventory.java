import lib.ProductValidator;
import lib.IntegerValidator;
import model.Product;
import java.util.*;

class Inventory {

  List<Product> list = new ArrayList<>();
  HashMap<Integer, Set<Integer>> adjList = new HashMap<>();

  // All the products will be added here
  public void AddProducts() {

    try {
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter the product ID:");
      int productId = IntegerValidator.getValidatedInteger(sc);
      System.out.println("Enter the price of the product:");
      int price = IntegerValidator.getValidatedInteger(sc);
      System.out.println("Enter the category of the product:");
      String category = sc.nextLine();
      System.out.println("Enter the quantity:");
      int quantity = IntegerValidator.getValidatedInteger(sc);
      Product product = new Product(productId, category, price, quantity);
      ProductValidator.isValidProduct(product);
      list.add(product);
    } catch (IllegalArgumentException e) {
      System.err.println("Error adding product: " + e.getMessage());
    }
  }

  // Product Updation will be here
  public void UpdateProducts() {

    try {
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter the product ID:");
      int productId = IntegerValidator.getValidatedInteger(sc);
      int i = 0;
      for (Product product : list) {
        if (productId == product.productId) {
          System.out.println("Enter the updated price of the product:");
          int price = IntegerValidator.getValidatedInteger(sc);
          sc.nextLine();
          System.out.println("Enter the updated category of the product:");
          String category = sc.nextLine();
          System.out.println("Enter the updated quantity of the product:");
          int quantity = IntegerValidator.getValidatedInteger(sc);
          product.price = price;
          product.category = category;
          product.quantity = quantity;
          list.set(i, product);
          break;
        }
        i++;
      }
    } catch (IllegalArgumentException e) {
      System.err.println("Error updating product: " + e.getMessage());
    }
  }

  // Product Deletion will be here
  public void DeleteProducts() {

    try {
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter the product ID:");
      int productId = IntegerValidator.getValidatedInteger(sc);
      Iterator<Product> iterator = list.iterator();
      while (iterator.hasNext()) {
        Product product = iterator.next();
        if (product.productId == productId) {
          iterator.remove();
          System.out.println("Product successfully removed!");
          break;
        }
      }
    } catch (IllegalArgumentException e) {
      System.err.println("Error deleting product:" + e.getMessage());
    }

  }

  // lookup of products using HashMap
  public void Lookup() {

    Scanner sc = new Scanner(System.in);
    HashMap<Integer, Product> ProductIdMap = new HashMap<>();
    for (Product product : list) {
      ProductIdMap.put(product.productId, product);
    }
    System.out.println("Enter the product ID of the product:");
    int productId = IntegerValidator.getValidatedInteger(sc);
    for (Map.Entry<Integer, Product> entry : ProductIdMap.entrySet()) {
      if (entry.getKey().equals(productId)) {
        System.out.println("Products found!");
        System.out
            .println("Product category:" + entry.getValue().category + "\nProduct Price:" + entry.getValue().price);
      }
    }
    System.out.println("============================");
    HashMap<String, List<Product>> CategoryMap = new HashMap<>();
    for (Product product : list) {
      CategoryMap.putIfAbsent(product.category, new ArrayList<>());
      CategoryMap.get(product.category).add(product);
    }
    sc.nextLine();
    System.out.println("Enter the product category:");
    String productCategory = sc.nextLine();
    System.out.println("The list of products in that category are:");
    for (Map.Entry<String, List<Product>> entry : CategoryMap.entrySet()) {
      if (productCategory.equals(entry.getKey())) {
        for (Product Plist : entry.getValue()) {
          System.out.println("Product ID:" + Plist.productId);
          System.out.println("Product Price:" + Plist.price);
          System.out.println("=============================");
        }
      }
    }

  }

  // Sort products by price using Heap Sort
  public void SortProductsByPrice() {

    int n = list.size();

    for (int i = n / 2 - 1; i >= 0; i--) {
      heapify(list, n, i);
    }

    for (int i = n - 1; i >= 0; i--) {
      Product temp = list.get(i);
      list.set(i, list.get(0));
      list.set(0, temp);

      heapify(list, i, 0);
    }

  }

  public void heapify(List<Product> list, int n, int i) {

    int largest = i;
    int left = 2 * i + 1;
    int right = 2 * i + 2;

    if (left < n && list.get(left).price > list.get(largest).price) {
      largest = left;
    }

    if (right < n && list.get(right).price > list.get(largest).price) {
      largest = right;
    }

    if (largest != i) {
      Product swap = list.get(largest);
      list.set(largest, list.get(i));
      list.set(i, swap);

      heapify(list, n, largest);
    }

  }

  // Product filtering by Price using Binary Search
  // Did not handle the scenario where same price products come
  public void ProductFiltering() {
    try {
      Scanner sc = new Scanner(System.in);
      System.out.println("Filter products based on price");
      System.out.println("Enter the price:");
      int ProductPrice = IntegerValidator.getValidatedInteger(sc);
      int left = 0;
      int right = list.size() - 1;
      Product product = BinarySearch(left, right, ProductPrice);
      ProductValidator.isValidProduct(product);
      System.out.println("Product Details:");
      System.out.println("Product ID:" + product.productId);
      System.out.println("Product Category:" + product.category);
    } catch (IllegalArgumentException e) {
      System.out.println("Error in product:" + e.getMessage());
    }
  }

  public Product BinarySearch(int l, int r, int price) {
    while (l <= r) {

      int mid = (l + r) / 2;
      if (list.get(mid).price == price) {
        System.out.println("Product found!");
        return list.get(mid);
      } else if (list.get(mid).price > price) {
        l = mid + 1;
      } else {
        r = mid - 1;
      }

    }
    return null;
  }

  // Low stock alerts using PriorityQueue
  public void LowStockAlert() {

    HashMap<Integer, Integer> OrderMap = new HashMap<>();
    for (Product product : list) {
      OrderMap.put(product.productId, product.quantity);
    }
    PriorityQueue<Map.Entry<Integer, Integer>> queue = new PriorityQueue<>(Map.Entry.comparingByValue());
    for (Map.Entry<Integer, Integer> entry : OrderMap.entrySet()) {
      if (entry.getValue() < 3) {
        queue.add(entry);
      }
    }
    while (!queue.isEmpty()) {
      Map.Entry<Integer, Integer> entry = queue.poll();
      System.out.println("Product " + entry.getKey() + " is low on stock!");
    }

  }

  // display product relationships
  public void DisplayProductRelationships() {

    Scanner sc = new Scanner(System.in);
    System.out.println("Display the product relationships:");
    System.out.println("Enter the product ID:");
    int productId = IntegerValidator.getValidatedInteger(sc);
    for (Map.Entry<Integer, Set<Integer>> entry : adjList.entrySet()) {
      if (entry.getKey().equals(productId)) {
        System.out.println("Related Products are :" + entry.getValue());
      }
    }

  }

  // Manage product relationships
  public void AddProductRelationships() {

    Scanner sc = new Scanner(System.in);
    int cont = 1;
    while (cont != 0) {
      System.out.println("Enter the product ID of the product:");
      int productId = IntegerValidator.getValidatedInteger(sc);
      System.out.println("Enter the product ID of the related product:");
      int RelatedProductId = sc.nextInt();
      boolean RelatedProductFound = false;
      boolean ProductFound = false;
      Iterator<Product> iterator = list.iterator();
      while (iterator.hasNext()) {
        Product product = iterator.next();
        if (product.productId == productId) {
          ProductFound = true;
        }
        if (product.productId == RelatedProductId) {
          RelatedProductFound = true;
        }
      }
      if (ProductFound == false || RelatedProductFound == false) {
        System.out.println("Either Product or Related product does not exist!");
        return;
      }
      adjList.putIfAbsent(productId, new HashSet<>());
      adjList.putIfAbsent(RelatedProductId, new HashSet<>());
      adjList.get(productId).add(RelatedProductId);
      adjList.get(RelatedProductId).add(productId);
      System.out.println("Do you want to continue(0/1):");
      cont = IntegerValidator.getValidatedInteger(sc);
    }

  }

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    Inventory inventory = new Inventory();
    int cont = 1;
    while (cont != 0) {
      System.out.println("1. Add Product");
      System.out.println("2. Update Product");
      System.out.println("3. Delete Product");
      System.out.println("4. Lookup");
      System.out.println("5. Filter");
      System.out.println("6. Show Product Relationship");
      System.out.println("7. Add Product Relationship");
      System.out.println("8. Check for low stock");
      System.out.println("Enter the operation that you want to perform:");
      int op = IntegerValidator.getValidatedInteger(sc);

      switch (op) {
        case 1:
          inventory.AddProducts();
          break;

        case 2:
          inventory.UpdateProducts();
          break;

        case 3:
          inventory.DeleteProducts();
          break;

        case 4:
          inventory.Lookup();
          break;

        case 5:
          inventory.SortProductsByPrice();
          inventory.ProductFiltering();
          break;

        case 6:
          inventory.DisplayProductRelationships();
          break;

        case 7:
          inventory.AddProductRelationships();
          break;

        case 8:
          inventory.LowStockAlert();
          break;

        default:
          System.out.println("Incorrect option! Please try again");
          break;
      }
      System.out.println("Do you want to continue the program(0/1):");
      cont = IntegerValidator.getValidatedInteger(sc);
    }
  }
}
