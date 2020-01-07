package com.mstains.team.service.impl;

import com.mstains.team.model.UserLoginModel;
import com.mstains.team.dao.UserLoginDao;
import com.mstains.team.service.MPUserLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Erwin Feng
 * @since 2020-01-07
 */
@Service
public class MPUserLoginServiceImpl extends ServiceImpl<UserLoginDao, UserLoginModel> implements MPUserLoginService {

}
