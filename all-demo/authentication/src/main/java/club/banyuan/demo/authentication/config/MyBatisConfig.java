package club.banyuan.demo.authentication.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis配置类
 *
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"club.banyuan.demo.authentication.dao","com.macro.mall.dao"})
public class MyBatisConfig {
}
