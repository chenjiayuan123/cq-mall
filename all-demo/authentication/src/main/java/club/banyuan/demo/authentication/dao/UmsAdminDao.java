package club.banyuan.demo.authentication.dao;

import club.banyuan.demo.authentication.dao.entity.UmsAdmin;

public interface UmsAdminDao {

  int deleteByPrimaryKey(Long id);

  int insert(UmsAdmin record);

  int insertSelective(UmsAdmin record);

  UmsAdmin selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(UmsAdmin record);

  int updateByPrimaryKey(UmsAdmin record);

  UmsAdmin selectByUsername(String username);
}