package com.mstains.team.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.mstains.team.config.RequestCodeConfig;
import com.mstains.team.entity.LoginEntity;
import com.mstains.team.entity.RegisterEntity;
import com.mstains.team.entity.TokenEntity;
import com.mstains.team.model.UserInfoModel;
import com.mstains.team.model.UserLoginModel;
import com.mstains.team.service.MPUserInfoService;
import com.mstains.team.service.MPUserLoginService;
import com.mstains.team.token.TokenManager;
import com.mstains.team.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Erwin Feng
 * @since 2020-01-07
 */
@RestController
@RequestMapping("app/api/v1/")
public class LoginController {

    @Autowired
    private MPUserLoginService userLoginService;

    @Autowired
    private MPUserInfoService userInfoService;


    @PostMapping("login")
    public String onUserLogin(@RequestParam("userName") String loginName,
                              @RequestParam("passWord") String passWord) {
        QueryWrapper<UserLoginModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserLoginModel::getLoginName, loginName);
        queryWrapper.lambda().eq(UserLoginModel::getPassWord, passWord);
        UserLoginModel userLoginModel = userLoginService.getOne(queryWrapper);
        LoginEntity loginEntity = new LoginEntity();
        if (userLoginModel == null) {
            loginEntity.setReturnCode(RequestCodeConfig.USER_ERROR_EXIST_C0DE);
            loginEntity.setReturnMsg("用户名或密码错误，请重试");
        } else if (userLoginModel.getLoginName().equals(loginName) && userLoginModel.getPassWord().equals(passWord)) {
            loginEntity.setReturnCode(RequestCodeConfig.NORMAL_CODE);
            loginEntity.setReturnMsg("登录成功");
            String token = TokenManager.getToken(loginName,userLoginModel.getUserId());

            TokenEntity tokenEntity = new TokenEntity();
            tokenEntity.setToken(token);
            loginEntity.setData(new Gson().toJson(tokenEntity));
        }
        Gson gson = new Gson();
        return gson.toJson(loginEntity);
    }

    @PostMapping("register")
    public String onUserRegister(@RequestParam("userName") String userName,
                                 @RequestParam("passWord") String passWord) {

        QueryWrapper<UserLoginModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserLoginModel::getLoginName, userName);
        UserLoginModel loginModel = userLoginService.getOne(queryWrapper);
        RegisterEntity registerEntity = new RegisterEntity();
        if (loginModel != null) {
            registerEntity.setReturnCode(RequestCodeConfig.SERVICE_ERROR_CODE);
            registerEntity.setReturnMsg("该账号已被注册");
        }
        else {
            UserLoginModel userLoginModel = new UserLoginModel();
            userLoginModel.setLoginName(userName);
            userLoginModel.setPassWord(MD5Utils.encode(passWord));
            userLoginModel.setUserId(UUID.randomUUID().toString().replaceAll("-", "").trim());

            boolean success = userLoginService.save(userLoginModel);
            registerEntity.setReturnCode(RequestCodeConfig.SERVICE_ERROR_CODE);
            registerEntity.setReturnMsg("注册失败，服务器异常");
            if (success) {
                UserInfoModel userInfoModel = new UserInfoModel();
                userInfoModel.setUserId(userLoginModel.getUserId());
                userInfoModel.setUserName(userLoginModel.getLoginName());

                userInfoService.save(userInfoModel);

                registerEntity.setReturnCode(RequestCodeConfig.NORMAL_CODE);
                registerEntity.setReturnMsg("注册成功");
            }
        }
        Gson gson = new Gson();
        return gson.toJson(registerEntity);
    }
}
