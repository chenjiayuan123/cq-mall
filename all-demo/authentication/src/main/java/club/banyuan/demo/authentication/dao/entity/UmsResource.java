package club.banyuan.demo.authentication.dao.entity;

import java.util.Date;
import java.util.function.Supplier;

/**
 * ums_resource
 *
 * @author
 */
// 实现的supplier方法用于演示
public class UmsResource implements Supplier<String> {

  private Long id;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 资源名称
   */
  private String name;

  /**
   * 资源URL
   */
  private String url;

  /**
   * 描述
   */
  private String description;

  /**
   * 资源分类ID
   */
  private Long categoryId;

  private static final long serialVersionUID = 1L;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  @Override
  public String get() {
    return getName();
  }
}