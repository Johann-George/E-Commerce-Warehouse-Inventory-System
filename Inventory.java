class Inventory{

  List<Product> list = new ArrayList<>();
  HashMap<Integer,Product> ProductIdMap = new HashMap<>();
  List<Product> PriceList = new ArrayList<>();
  HashMap<Integer,Integer> adjList = new HashMap<>();

  //All the products will be added here
  public static void AddProducts(){

    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the product ID:");
    int productId = sc.nextInt();
    System.out.println("Enter the price of the product:");
    int price = sc.nextInt();
    System.out.println("Enter the category of the product:");
    String category = sc.nextLine();
    System.out.println("Enter the quantity:");
    int quantity = sc.nextInt();
    Product product = new Product(productId,category,price,quantity);
    list.add(product);

  } 

  //Product Updation will be here
  public static void UpdateProducts(){

    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the product ID:");
    int productId = sc.nextInt();
    int i=0;
    for(Product product: list){
      if (productId==product.productId) {
        System.out.println("Enter the updated price of the product:");
        int price = sc.nextInt();
        System.out.println("Enter the updated category of the product:");
        String category = sc.nextLine();    
        System.out.println("Enter the updated quantity of the product:");
        int quantity = sc.nextInt();
        product.price = price;
        product.category = category;
        product.quantity = quantity;
        list.set(i,product); 
        break;
      }
      i++;
    }
  }

  //Product Deletion will be here
  public static void DeleteProducts(){

    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the product ID:");
    int productId = sc.nextInt();
    for(Product product : list){
      if(productId==product.productId){
        list.remove(product);
      }
    }

  }

  //lookup of products using HashMap
  public static void Lookup(){

    Scanner sc = new Scanner(System.in);
    for(Product product: list){
      map.put(product.productId,product);
    }
    System.out.println("Enter the product ID of the product:");
    int productId = sc.nextInt();
    for(Map.Entry<Integer,Product> entry : ProductIdMap.entrySet()){
      if(entry.getKey().equals(productId)){
        System.out.println("Products found!");
        System.out.println("Product category:"+entry.getValue().category+"\nProduct Price:"+entry.getValue().price);
      }
    }
    HashMap<String,List<Product>> CategoryMap = new HashMap<>();
    for(Product product: list){
      CategoryMap.put(product.category,CategoryMap.getOrDefault(product.category,new ArrayList<>()).add(product));
    }
    System.out.println("Enter the product category:");
    int productCategory = sc.nextLine();
    System.out.println("The list of products in that category are:");
    for(Map.Entry<String,List<Product>> entry: CategoryMap.entrySet()){
      if(productCategory.equals(entry.getKey())){
        for(Product Plist: entry.getValue()){
          System.out.println("Product ID:"+Plist.productId);
          System.out.println("Product Price:"+Plist.price);
          System.out.println("=============================");
        }
      }
    }

  }

  //Sort products by price using Heap Sort
  public static void SortProductsByPrice(){

    int n=PriceList.size();

    for(int i=n/2-1;i>=0;i--){
      heapify(PriceList,n,i);
    }

    for(int i=n-1;i>=0;i--){
      Product temp = PriceList.get(i);
      PriceList.set(i,PriceList.get(0));
      PriceList.set(0,temp);

      heapify(PriceList,i,0);
    }

  }

  public void heapify(List<Product> PriceList,int n, int i){

    int largest = i;
    int left = 2*i+1;
    int right = 2*i+2;

    if(left<n && PriceList.get(left).price>PriceList.get(largest).price){
      largest = left;
    }

    if(right<n && PriceList.get(right).price>PriceList.get(largest).price){
      largest = right;
    }

    if(largest!=i){
      Product swap = PriceList.get(largest);
      PriceList.set(largest,PriceList.get(i));
      PriceList.set(i,swap);

      heapify(PriceList,n,largest);
    }

  }
  
  //Product filtering by Price using Binary Search
  //Did not handle the scenario where same price products come
  public static void ProductFiltering(){

    Scanner sc = new Scanner(System.in);
    System.out.println("Filter products based on price");
    System.out.println("Enter the price:");
    int ProductPrice= sc.nextint();
    int left = 0;
    int right = PriceList.size()-1;
    Product product = BinarySearch(left, right, ProductPrice);
    System.out.println("Product Details:");
    System.out.println("Product ID:"+product.productId);
    System.out.println("Product Category:"+product.category);

  }

  public Product BinarySearch(int l, int r, int price){
    while (l<=r){
      
      int mid = (l+r)/2;
      if(PriceList.get(mid).price == price){
        System.out.println("Product found!");
        return PriceList.get(mid);
      }
      else if(PriceList.get(mid).price > price){
        l=mid+1;
      }
      else{
        r=mid-1;
      }

    }
  }

  //Low stock alerts using PriorityQueue
  public static void LowStockAlert(){

    HashMap<Integer,Integer> OrderMap = new HashMap<>();
    for(Product product : list){
      OrderMap.add(product.productId,product.quantity);
    }
    PriorityQueue<Map.Entry<Integer,Integer>> queue = new PriorityQueue<>(Map.Entry.comparingByValue());
    for(Map.Entry<Integer,Integer> entry: OrderMap.entrySet()){
      if(entry.getValue()<3){
        queue.add(entry);
      }
    }
    while(!queue.isEmpty()){
      Map.Entry<Integer,Integer> entry = queue.poll();
      System.out.println("Product "+entry.getKey()+" is low on stock!");
    }

  }

  //display product relationships
  public static void DisplayProductRelationships() {

    Scanner sc = new Scanner(System.in);
    System.out.println("Display the product relationships:");
    System.out.println("Enter the product ID:");
    int productId = sc.nextInt();
    for(Map.Entry<Integer,List<Integer>> entry: adjList.entrySet()){
      if(adjList.getKey().equals(productId)){
        System.out.println("Related Products are :"+adjList.getValue());
      }
    }
    
  }

  
  //Manage product relationships
  public static void AddProductRelationships() {
    
    Scanner sc = new Scanner(System.in);
    int cont = 1;
    while(cont!=0){
      System.out.println("Enter the product ID of the product:");
      int productId = sc.nextInt();
      System.out.println("Enter the product ID of the related product:");
      int RelatedProductId = sc.nextInt();
      adjList.putIfAbsent(productId,new ArrayList<>());
      adjList.putIfAbsent(RelatedProductId,new ArrayList<>());
      adjList.get(productId).add(RelatedProductId);
      adjList.get(RelatedProductId).add(productId);
      System.out.println("Do you want to continue(0/1):");
      cont = sc.nextInt();
    }

  }
  

  public static void main(String[] args) {
  
    Scanner sc = new Scanner(System.in);
    Inventory inventory = new Inventory();
    while(cont!=0){
      System.out.println("1. Add Product");
      System.out.println("2. Update Product");
      System.out.println("3. Delete Product");
      System.out.println("4. Lookup");
      System.out.println("5. Filter");
      System.out.println("6. Show Product Relationship");
      System.out.println("7. Add Product Relationship");
      System.out.println("8. Check for low stock");
      System.out.println("Enter the operation that you want to perform:");
      int op = sc.nextInt();

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
    }
    

  }

}
