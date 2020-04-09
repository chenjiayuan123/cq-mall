package club.banyuan.demo.stream;

import java.util.function.Supplier;

public class Product implements Supplier<String> {

  private String productName;
  private Long productId;

  public Product(String productName, Long productId) {
    this.productName = productName;
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  @Override
  public String get() {
    return getProductName();
  }
}
