package club.banyuan.demo.stream;

import club.banyuan.demo.authentication.dao.entity.UmsResource;
import club.banyuan.demo.authentication.service.ResourceService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StreamDemoTest {

  @Autowired
  private ResourceService resourceService;

  @Test
  public void filter() {
    List<UmsResource> umsResourceList = resourceService.listAllResource();

    List<UmsResource> prodResourceList = new ArrayList<>();
    List<Product> productList = new ArrayList<>();

    for (UmsResource ums : umsResourceList) {
      if (ums.getName().startsWith("商品")) {
        prodResourceList.add(ums);
      }
    }

    List<UmsResource> prodResourceListByStream = umsResourceList.stream()
        .filter(t -> t.getName().startsWith("商品")).collect(Collectors.toList());

    Set<String> set = getSet(prodResourceListByStream);
    Assert.assertTrue(set.containsAll(
        getSet(prodResourceList)));

  }

  private Set<String> getSet(List<? extends Supplier<String>> supplierList) {
    return supplierList.stream().map(Supplier::get)
        .collect(Collectors.toSet());
  }

  @Test
  public void map() {
    List<UmsResource> umsResourceList = resourceService.listAllResource();

    List<Product> productList = new ArrayList<>();
    for (UmsResource ums : umsResourceList) {
      if (ums.getName().startsWith("商品")) {
        productList.add(new Product(ums.getName(), ums.getId()));
      }
    }

    List<Product> productListByStream = umsResourceList.stream()
        .map(t -> new Product(t.getName(), t.getId()))
        .collect(Collectors.toList());
    Set<String> set = getSet(productListByStream);
    set.containsAll(getSet(productList));
  }
}
