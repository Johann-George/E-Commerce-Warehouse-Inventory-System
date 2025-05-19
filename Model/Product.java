package Model;

public class Product{

  public int productId;
  public String category;
  public int price;
  public int quantity;

  public Product(int productId, String category, int price, int quantity){
    this.productId=productId;
    this.category=category;
    this.price=price;
    this.quantity=quantity;
  }

}
