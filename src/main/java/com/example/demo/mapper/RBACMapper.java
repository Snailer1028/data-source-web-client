package com.example.demo.mapper;

import com.example.demo.mapper.query.BaseMapperX;
import com.example.demo.model.entity.UserEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RBACMapper extends BaseMapperX<UserEntity> {
    @Select("SELECT t_role.`name` " +
            "FROM t_user " +
            "   LEFT JOIN t_user_role ON t_user.id = t_user_role.user_id " +
            "   LEFT JOIN t_role ON t_user_role.role_id = t_role.id " +
            "WHERE t_user.id = #{userId};")
    List<String> selectRolesByUserId(@Param("userId") Integer userId);

    @Select("SELECT p.`name` AS permission_name " +
            "FROM `t_user` u " +
            "   JOIN `t_user_role` ur ON u.`id` = ur.`user_id` " +
            "   JOIN `t_role` r ON ur.`role_id` = r.`id` " +
            "   JOIN `t_role_permission` rp ON r.`id` = rp.`role_id` " +
            "   JOIN `t_permission` p ON rp.`permission_id` = p.`id` " +
            "WHERE u.`id` = #{userId};")
    List<String> selectPermissionByUserId(@Param("userId") Integer userId);
}




