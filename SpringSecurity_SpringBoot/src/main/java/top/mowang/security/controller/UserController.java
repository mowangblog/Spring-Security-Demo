package top.mowang.security.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;
import top.mowang.security.service.ITUserService;
import top.mowang.security.pojo.User;

import java.util.List;

/**
 * <p>
 *  前端控制器
 *  security注解介绍
 *  PreAuthorize-》方法执行之前
 *  PostAuthorize-》方法执行之后
 *  PostFilter-》对返回参数过滤
 *  PreFilter-》对入参进行过滤
 * </p>
 *
 * @author Xuan Li
 * @since 2021-10-18
 */
@RestController
public class UserController {

    @Autowired
    ITUserService userService;

    @PostFilter("filterObject.username=='admin'")
    @GetMapping("/user")
    public List<User> getUser(){
        List<User> list = userService.list();
        return list;
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Integer id){
        return userService.getById(id);
    }

    @PostMapping("/user")
    public String saveUser(User user){
        boolean save = userService.save(user);
        return save?"添加成功":"添加失败";
    }

    @PutMapping("/user")
    public String updateUser(User user){
        boolean update = userService.updateById(user);
        return update?"修改成功":"修改失败";
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Integer id){
        boolean remove = userService.removeById(id);
        return remove?"删除成功":"删除失败";
    }

    @GetMapping("/userPage/{page}/{size}")
//    @Secured({"ROLE_admin"})
    @PostAuthorize("hasAnyRole('admin,魔王')")
    public Page<User> userPage(@PathVariable("page") Integer currentPage,
                               @PathVariable("size") Integer pageSize){
        Page<User> page = new Page<>(currentPage,pageSize);
        Page<User> userPage = userService.page(page,null);
        return userPage;
    }

}

