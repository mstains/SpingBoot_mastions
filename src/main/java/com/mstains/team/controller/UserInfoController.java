package com.mstains.team.controller;


import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.mstains.team.config.RequestCodeConfig;
import com.mstains.team.entity.UserInfoEntity;
import com.mstains.team.model.UserInfoModel;
import com.mstains.team.service.MPUserInfoService;
import com.mstains.team.token.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Erwin Feng
 * @since 2020-01-09
 */
@RestController
@Slf4j
@RequestMapping("/app/api/v1/")
public class UserInfoController {

    @Autowired
    private MPUserInfoService userInfoService;

    @Autowired
    private RedisTemplate<Object, Object> template;

    @PostMapping("userInfo")
    public String onUserInfo(@RequestHeader("token") String token) {

        String userId = TokenManager.queryUserId(token);

        log.info(userId);
        Gson gson = new Gson();

        String userJson = (String) template.opsForValue().get(userId);
        UserInfoModel userInfoModel = null;
        if (userJson == null) {
            log.info("查询数据库");
            QueryWrapper<UserInfoModel> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(UserInfoModel::getUserId, userId);

            userInfoModel = userInfoService.getOne(queryWrapper);
            template.opsForValue().set(userId, gson.toJson(userInfoModel));
        } else {
            log.info("获取缓存");
            userInfoModel = gson.fromJson(userJson, UserInfoModel.class);
        }

        UserInfoEntity userInfoEntity = new UserInfoEntity();

        userInfoEntity.setReturnCode(RequestCodeConfig.SERVICE_ERROR_CODE);

        userInfoEntity.setReturnMsg("服务异常，请稍后再试。");

        if (userInfoModel != null) {

            userInfoEntity.setReturnCode(RequestCodeConfig.NORMAL_CODE);

            userInfoEntity.setReturnMsg("获取成功。");

            userInfoEntity.setData(gson.toJson(userInfoModel));
        }
        return gson.toJson(userInfoEntity);
    }

    public void onUserInfoUpdate(@RequestHeader("token")String token){




    }



}
