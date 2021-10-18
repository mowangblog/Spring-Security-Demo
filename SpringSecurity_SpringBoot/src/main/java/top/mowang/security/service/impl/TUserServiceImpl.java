package top.mowang.security.service.impl;

import org.springframework.context.annotation.Primary;
import top.mowang.security.pojo.User;
import top.mowang.security.mapper.TUserMapper;
import top.mowang.security.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xuan Li
 * @since 2021-10-18
 */
@Service
@Primary
public class TUserServiceImpl extends ServiceImpl<TUserMapper, User> implements ITUserService {

}
