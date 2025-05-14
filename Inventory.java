class Inventory{

  List<Product> list = new ArrayList<>();
  HashMap<Integer,Product> ProductIdMap = new HashMap<>();
  List<Product> PriceList = new ArrayList<>();

  //All the products will be added here
  public static void AddProducts(){

    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the product ID:");
    int productId = sc.nextInt();
    System.out.println("Enter the price of the product:");
    int price = sc.nextInt();
    System.out.println("Enter the category of the product:");
    String category = sc.nextLine();
    Product product = new Product(productId,category,price);
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
        i++;
      }
    }
    Product product = new Product(productId,category,price);
    list.set(i,product); 

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
  public static void ProductFiltering(){

    Scanner sc = new Scanner(System.in);
    System.out.println("Filter products based on price");
    System.out.println("Enter the price:");
    int ProductPrice= sc.nextint();
    int left = 0;
    int right = 0;
    int index = BinarySearch(left, right, ProductPrice);

  }

  public int BinarySearch(int l, int r, int price){
    while(l<=r){
      
      int mid = (l+r)/2;
      if(PriceList.get(mid).price == price){
      
      }

    }
  }

  //Low stock alerts using PriorityQueue

  //display product relationships
  
  //Manage product relationships

  public static void main(String[] args) {
    
  }

}
