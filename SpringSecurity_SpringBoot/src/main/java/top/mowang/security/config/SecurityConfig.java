package top.mowang.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * Spring-Security-Demo
 *
 * @author : Xuan Li
 * @website : https://mowangblog.top
 * @date : 2021/10/18 16:40
 **/
@Configuration
@SuppressWarnings("all")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //通过配置类配置用户名密码
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String encode = bCryptPasswordEncoder.encode("123");
//        auth.inMemoryAuthentication().passwordEncoder(bCryptPasswordEncoder).withUser("mowang").password(encode).roles("admin");
        //自定义实现类
        auth.userDetailsService(userDetailsService).passwordEncoder(password());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //配置退出登录logoutUrl配置跳转链接
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/user").permitAll();
        //配置没有权限跳转页面
        http.exceptionHandling().accessDeniedPage("/error.html");
        http.formLogin() //自己编辑登录页面
            .loginPage("/login.html")//登录页面设置
            .loginProcessingUrl("/user/login") //登录访问路径
            .defaultSuccessUrl("/success.html").permitAll()
                .and().authorizeRequests()
                 //设置哪些路径不需要认证
                .antMatchers("/","/user","user/login").permitAll()
                //设置路径"/user/*"只有admin权限才可以访问
//                .antMatchers("/user/*").hasAnyAuthority("admin")
                //hasAnyAuthority可以设置多个权限
//                .antMatchers("/user/*").hasAnyAuthority("admin,特朗普")
                //有ROLE_admin角色的才能访问
//                .antMatchers("/user/*").hasRole("admin")
                .antMatchers("/user/*").hasAnyRole("admin,特朗普")
                .anyRequest().authenticated()
                .and().rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60*60)
                .userDetailsService(userDetailsService)
                .and().csrf().disable();//关闭csrf防护
    }

    @Bean
    PasswordEncoder password(){
        return new BCryptPasswordEncoder();
    }
}
