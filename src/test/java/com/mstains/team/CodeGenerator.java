package com.mstains.team;

import com.baomidou.mybatisplus.annotation.DbType;
import com.mstains.team.utils.CommonUtils;
import com.mstains.team.utils.Config;

public class CodeGenerator {

    public static void main(String[] args) {

        System.out.println(Config.projectPath);

        DbType dbType = DbType.MYSQL;
        String dbUrl = "jdbc:mysql://localhost:3306/mstains?useUnicode=true&useSSL=false&characterEncoding=utf8";
        String userName = "root";
        String passWord = "root";
        String driver = "com.mysql.jdbc.Driver";

        // 表前缀，生成的实体类，不含前缀
        String [] tablePrefixes = {""};
        // 表名，为空，生成所有的表
        String [] tableNames = {"user_login","user_info"};
        // 字段前缀
        String [] fieldPrefixes = {""};

        String packageName = "com.mstains.team";

        CommonUtils.execute(dbType,dbUrl,userName,passWord,driver,tablePrefixes,tableNames,packageName,fieldPrefixes);
    }




}
