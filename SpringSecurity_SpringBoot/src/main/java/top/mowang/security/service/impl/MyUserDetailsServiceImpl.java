package top.mowang.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.mowang.security.service.ITUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring-Security-Demo
 *
 * @author : Xuan Li
 * @website : https://mowangblog.top
 * @date : 2021/10/18 16:55
 **/
@Service("userDetailsService")
public class MyUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ITUserService userService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
       QueryWrapper<top.mowang.security.pojo.User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(top.mowang.security.pojo.User::getUsername,s);
        top.mowang.security.pojo.User user = userService.getOne(wrapper);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }else {
            List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_"+user.getUsername());
            return new User(s,passwordEncoder.encode(user.getPassword()),auths);
        }


    }
}
