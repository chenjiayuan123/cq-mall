package club.banyuan.demo.redis.user;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  static int count = 0;

  // 对于使用@Cacheable标注的方法，Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，
  // 如果存在就不再执行该方法，而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。

  // values 指定缓存前缀，即缓存的cache位置
  // value = {"zhangsan", "lisi"}
  // "zhangsan::SimpleKey []"
  // "lisi::SimpleKey []"

  // key 指定缓存使用的key 传入EL表达式
  // (value = {"zhangsan", "lisi"},key = "methodName")
  // 1) "lisi::getUser"
  // 2) "zhangsan::getUser"

  // condition属性值是通过SpringEL表达式来指定的，默认是true
  // 当为true时表示进行缓存处理；当为false时表示不进行缓存处理，即每次调用该方法时该方法都会执行一次。
  @Cacheable(value = {"zhangsan", "lisi"}, key = "methodName")
  public User getUser() {
    return new User("user", (count++) + "");
  }

  // @CachePut也可以声明一个方法支持缓存功能。与@Cacheable不同的是
  // 使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，
  // 而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。

  //每次都会执行方法，并将结果存入指定的缓存中
  @CachePut(value = "users", key = "'cachePut'")
  public User cachePut() {
    return new User("王五", "123456");
  }


  // @CacheEvict是用来标注在需要清除缓存元素的方法或类上的。
  // 当标记在一个类上时表示其中所有的方法的执行都会触发缓存的清除操作。
  // @CacheEvict可以指定的属性有value、key、condition、allEntries和beforeInvocation。
  // 其中value、key和condition的语义与@Cacheable对应的属性类似。
  // 即value表示清除操作是发生在哪些Cache上的（对应Cache的名称）；
  // key表示需要清除的是哪个key，如未指定则会使用默认策略生成的key；
  // condition表示清除操作发生的条件。
  @CacheEvict(value = {"zhangsan", "lisi"}, key = "'getUser'")
  public void evictUser() {

  }
}
