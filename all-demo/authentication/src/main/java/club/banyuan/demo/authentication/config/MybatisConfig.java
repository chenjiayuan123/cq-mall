package club.banyuan.demo.authentication.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"club.banyuan.demo.authentication.dao"})
public class MybatisConfig {

}
