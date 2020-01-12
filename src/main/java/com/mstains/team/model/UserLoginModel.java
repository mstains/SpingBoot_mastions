package com.mstains.team.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author mstains
 * @since 2020-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_login")
public class UserLoginModel extends Model<UserLoginModel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "login_id", type = IdType.AUTO)
    private Integer loginId;

    private String userId;

    private String loginName;

    private String passWord;


    @Override
    protected Serializable pkVal() {
        return this.loginId;
    }

}
