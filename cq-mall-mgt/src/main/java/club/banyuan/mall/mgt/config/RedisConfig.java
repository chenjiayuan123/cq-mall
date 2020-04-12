package club.banyuan.mall.mgt.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类
 */
// 注释触发一个后处理器(post processor )，
// 它检查每个Spring bean是否存在公共方法(public method)上的缓存注释(@Cacheable @CachePut @CacheEvict)。
// 如果找到这样的注释，则自动创建代理以拦截方法调用并相应地处理缓存行为。
@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

  /**
   * 每个 Template 都有四个字段存储序列化器
   * <p>
   * keySerializer - key 序列化器
   * valueSerializer - 值序列化器
   * hashKeySerializer - 用于 Hash 操作的 key 序列化器
   * hashValueSerializer - 用于 Hash 操作的 value 序列化器
   * 默认情况下如果不设置， 各个序列化器会直接使用 defaultSerializer ，
   * 如果不全部都指定的话，建议设置一个 defaultSerializer 。
   * 这个行为是在 RedisTempalte 的 afterPropertiesSet() 方法里的。
   * 新建完一个 RedisTemplate 必须调用一次这个方法，不然会报错
   * <p>
   * 官方提供的两个默认 Serializer：
   * <p>
   * StringRedisSerializer - 直接序列化成 String ，只支持 String 。
   * GenericJackson2JsonRedisSerializer - 序列化成 Json String 。
   *
   * @param redisConnectionFactory
   * @return
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisSerializer<Object> serializer = redisSerializer();
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    //使用StringRedisSerializer来序列化和反序列化redis的key值
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(serializer);
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(serializer);
    // 必须执行这个函数,初始化RedisTemplate
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }

  @Bean
  public RedisSerializer<Object> redisSerializer() {
    //创建JSON序列化器
    // 使用GenericJacksonRedisSerializer比Jackson2JsonRedisSerializer效率低，占用内存高
    Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(
        Object.class);
    ObjectMapper objectMapper = new ObjectMapper();
    // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
    objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等抛异常
    // redis会在key中保存类的全限定名，这样可以在反序列化的时候生成对应的类
    // "[\"club.banyuan.demo.redis.user.User\",{\"name\":\"\xe5\x8d\x8a\xe5\x9c\x86\",\"pwd\":\"123456\"}]"
    objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    serializer.setObjectMapper(objectMapper);
    return serializer;
  }

  @Bean
  public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
    // 非阻塞比阻塞（基于锁）并发要快得多
    RedisCacheWriter redisCacheWriter = RedisCacheWriter
        .nonLockingRedisCacheWriter(redisConnectionFactory);
    //设置Redis缓存有效期为1天
    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer()))
        .entryTtl(Duration.ofDays(1));
    return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
  }

}
