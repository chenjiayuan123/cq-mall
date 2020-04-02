package club.banyuan.demo.redis.config;

import club.banyuan.demo.redis.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonDemo {

  public static void main(String[] args) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    String str = mapper.writer().writeValueAsString(new User("张三", "李四"));
    System.out.println(str);

    String json = "{\"name\":\"张三\",\"pwd\":\"李四\"}";

    User user = mapper.readValue(json, User.class);
    System.out.println(user);
  }
}
