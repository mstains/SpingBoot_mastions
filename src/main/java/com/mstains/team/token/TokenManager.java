package com.mstains.team.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class TokenManager {

    public static String getToken(String userName,String passWord,String userId){

        String token = "";
        token = JWT.create()
                .withClaim("username",userName)
                .withClaim("generatetime",System.currentTimeMillis())
                .withClaim("userid",userId)
                .withClaim("exptime",1000*1*60*60)
                .sign(Algorithm.HMAC256(passWord));
        return token;
    }

    public static String queryUserId(String token){
        String userId = "";
        userId = JWT.decode(token).getClaim("userid").asString();
        System.out.println(userId);
        return userId;
    }
}
