package Lib;
import Product;

public class ProductValidator{

  public static void isValidProduct(Product p) throws IllegalArgumentException{

    if(p==null){
      throw new IllegalArgumentException("Product cannot be null");
    }
    if(p.productId < 0){
      throw new IllegalArgumentException("Product ID cannot be empty");
    }
    if(p.price<0){
      throw new IllegalArgumentException("Product price must be greater than 0");
    }

  }

}
