package com.mstains.team.service.impl;

import com.mstains.team.model.UserInfoModel;
import com.mstains.team.dao.UserInfoDao;
import com.mstains.team.service.MPUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mstains
 * @since 2020-01-09
 */
@Service
public class MPUserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfoModel> implements MPUserInfoService {

}
