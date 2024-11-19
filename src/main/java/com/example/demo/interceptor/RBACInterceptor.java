package com.example.demo.interceptor;

import com.example.demo.mapper.RBACMapper;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;

@Slf4j
@Component
public class RBACInterceptor implements StpInterface {
    @Resource
    private RBACMapper rbacMapper;

    @Override
    public List<String> getPermissionList(Object loginId, String s) {
        int userId = Integer.parseInt(loginId.toString());
        List<String> permissionList = rbacMapper.selectPermissionByUserId(userId);
        log.info("用户 {} 的权限为 {}", loginId, permissionList);
        return CollUtil.isNotEmpty(permissionList) ? permissionList : Collections.emptyList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String s) {
        int userId = Integer.parseInt(loginId.toString());
        List<String> roleList = rbacMapper.selectRolesByUserId(userId);
        log.info("用户 {} 的角色为 {}", loginId, roleList);
        return CollUtil.isNotEmpty(roleList) ? roleList : Collections.emptyList();
    }
}
