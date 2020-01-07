package com.mstains.team.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.mstains.team.config.RequestCodeConfig;
import com.mstains.team.entity.LoginEntity;
import com.mstains.team.entity.RegisterEntity;
import com.mstains.team.model.UserLoginModel;
import com.mstains.team.service.MPUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Erwin Feng
 * @since 2020-01-07
 */
@RestController
public class LoginController {

    @Autowired
    private MPUserLoginService userLoginService;


    @RequestMapping("app/api/v1/login")
    public String onUserLogin(@RequestParam("userName") String loginName,
                              @RequestParam("passWord") String passWord) {
        QueryWrapper<UserLoginModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserLoginModel::getLoginName, loginName);
        queryWrapper.lambda().eq(UserLoginModel::getPassWord, passWord);
        UserLoginModel userLoginModel = userLoginService.getOne(queryWrapper);

        LoginEntity loginEntity = new LoginEntity();

        if (userLoginModel == null) {
            loginEntity.setReturnCode(RequestCodeConfig.USER_ERROR_EXIST_C0DE);
            loginEntity.setReturnMsg("用户不存在");
        } else if (userLoginModel.getLoginName().equals(loginName) && userLoginModel.getPassWord().equals(passWord)) {
            loginEntity.setReturnCode(RequestCodeConfig.NORMAL_CODE);
            loginEntity.setReturnMsg("登录成功");
        } else {
            loginEntity.setReturnCode(RequestCodeConfig.USER_PASSWORD_ERROR_CODE);
            loginEntity.setReturnMsg("密码错误，请输入正确密码！");

        }
        Gson gson = new Gson();

        return gson.toJson(loginEntity);

    }


    @RequestMapping("app/api/v1/register")
    public String onUserRegister(@RequestParam("userName") String userName,
                               @RequestParam("passWord") String passWord) {
        UserLoginModel userLoginModel = new UserLoginModel();
        userLoginModel.setLoginName(userName);
        userLoginModel.setPassWord(passWord);
       boolean success = userLoginService.save(userLoginModel);
        RegisterEntity registerEntity = new RegisterEntity();
        registerEntity.setReturnCode(RequestCodeConfig.SERVICE_ERROR_CODE);
        registerEntity.setReturnMsg("注册失败，服务器异常");
       if (success){
           registerEntity.setReturnCode(RequestCodeConfig.NORMAL_CODE);
           registerEntity.setReturnMsg("注册成功");
           registerEntity.setData("请开始你的表演");
       }

        Gson gson = new Gson();
       return gson.toJson(registerEntity);

    }
}
