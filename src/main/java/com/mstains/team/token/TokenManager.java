package com.mstains.team.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class TokenManager {

    private static final String KEY = "8D4F16E8F94796FC";

    public static final String USER_ID = "userid";


    public static String getToken(String userName,String userId){

        String token = "";
        //签名发布时间
        Date iatDate=new Date();
        System.out.println(iatDate);//英文时间

        Calendar nowTime=Calendar.getInstance();
        nowTime.add(Calendar.MINUTE,5);

        Date expire = nowTime.getTime();

        System.out.println(expire);

        token = JWT.create()
                .withClaim("username",userName)
                .withClaim("userid",userId)
                .withIssuedAt(iatDate)
                .withExpiresAt(expire)
                .sign(Algorithm.HMAC256(KEY));

        return token;
    }



    public static Map<String, Claim> verifyToken(String token) throws TokenExpiredException, SignatureVerificationException {

        JWTVerifier verifier = null;

        DecodedJWT jwt = null;

        verifier = JWT.require(Algorithm.HMAC256(KEY)).build();

        jwt = verifier.verify(token);

        return jwt.getClaims();

    }



}
