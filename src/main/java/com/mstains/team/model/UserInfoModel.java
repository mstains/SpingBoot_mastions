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
 * @since 2020-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_info")
public class UserInfoModel extends Model<UserInfoModel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uuid", type = IdType.AUTO)
    private Integer uuid;

    private String userId;

    private String userName;

    private String picUrl;

    private String age;

    private String sex;

    private String phone;

    private String idCard;

    private String address;


    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

}
