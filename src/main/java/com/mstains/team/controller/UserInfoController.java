package com.mstains.team.controller;


import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
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

import java.util.Map;

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

        Gson gson = new Gson();

        UserInfoEntity userInfoEntity = new UserInfoEntity();

        try {
            Map<String, Claim>  claimMap =  TokenManager.verifyToken(token);

            String userId = claimMap.get(TokenManager.USER_ID).asString();

            log.info(userId);
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

                if (userInfoModel != null) {

                    userInfoEntity.setReturnCode(RequestCodeConfig.NORMAL_CODE);

                    userInfoEntity.setReturnMsg("获取成功。");

                    userInfoEntity.setData(gson.toJson(userInfoModel));
                }
            }

        }catch (TokenExpiredException exception){

            userInfoEntity.setReturnCode(RequestCodeConfig.TOKEN_ERROR_CODE);

            userInfoEntity.setReturnMsg("登录超时");

        }
        catch (SignatureVerificationException exception){

            userInfoEntity.setReturnCode(RequestCodeConfig.TOKEN_ERROR_CODE);

            userInfoEntity.setReturnMsg("登录超时");
        }











        return gson.toJson(userInfoEntity);
    }

    public void onUserInfoUpdate(@RequestHeader("token")String token){




    }



}
