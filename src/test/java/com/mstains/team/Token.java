package com.mstains.team;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mstains.team.token.TokenManager;

public class Token {


    public static void main(String[] args) {

//       String token =  TokenManager.getToken("test","lalalll");
//
//       System.out.println(token);

       String tvToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1Nzg4Mjk3NzQsInVzZXJpZCI6ImxhbGFsbGwiLCJpYXQiOjE1Nzg4Mjk0NzQsInVzZXJuYW1lIjoidGVzdCJ9.daWwKsTwgq0ft00WqcZiREXEL5CwTW-008TwyOjZTfY";

       try {
           TokenManager.verifyToken(tvToken);

       }catch (TokenExpiredException e){

           System.out.println("token过期");
       }




    }
}
