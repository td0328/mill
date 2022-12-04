package com.myth.system.service.imple;

import com.myth.system.bean.SysPermission;
import com.myth.system.bean.SysUser;
import com.myth.system.mapper.SysPermissionMapper;
import com.myth.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecurityUserServiceImpl implements SecurityUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    /**
     * 根据用户名查找数据库，判断是否存在这个用户
     * */
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {

        // 用户名必须是唯一的，不允许重复
        SysUser sysUser = sysUserMapper.selectOne(account);

        if(StringUtils.isEmpty(sysUser)){
            throw new UsernameNotFoundException("根据用户名找不到该用户的信息！");
        }

        List<SysPermission> sysPermissions = sysPermissionMapper.getUserRolesByUserId(sysUser.getId());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        sysPermissions.stream().forEach(sysPermission -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysPermission.getPermissionCode());
            grantedAuthorities.add(grantedAuthority);
        });
        return new User(sysUser.getAccount(), sysUser.getPassword(), sysUser.getEnabled(), sysUser.getNotExpired(), sysUser.getCredentialsNotExpired(), sysUser.getAccountNotLocked(), grantedAuthorities);
    }
}
