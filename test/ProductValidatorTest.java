import org.junit.jupiter.api.Test.*;
import org.junit.jupiter.api.Assertions.*;
import model.Product;

public class ProductValidatorTest {

  @Test
  public void testValidProduct() {
    Product product = new Product(1, "Electronics", 100, 5);
    assertDoesNotThrow(() -> ProductValidator.isValidProduct(product));
  }

  @Test
  public void testNegativePrice() {
    Product product = new Product(1, "Electronics", -100, 5);
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ProductValidator.isValidProduct(product);
    });
    assertEquals("Product price must be greater than 0", exception.getMessage());
  }

  @Test
  public void testNegativeID() {
    Product product = new Product(-1, "Electronics", 100, 5);
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ProductValidator.isValidProduct(product);
    });
    assertEquals("Product ID cannot be empty", exception.getMessage());
  }

}
