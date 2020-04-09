package club.banyuan.demo.authentication.dao;

import club.banyuan.demo.authentication.dao.entity.UmsResource;
import java.util.List;

public interface UmsResourceDao {

  int deleteByPrimaryKey(Long id);

  int insert(UmsResource record);

  int insertSelective(UmsResource record);

  UmsResource selectByPrimaryKey(Long id);

  List<UmsResource> selectAll();

  List<UmsResource> selectByUsername(String username);

  int updateByPrimaryKeySelective(UmsResource record);

  int updateByPrimaryKey(UmsResource record);
}