package org.zerock.mallapi.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.mallapi.util.CustomJWTException;
import org.zerock.mallapi.util.JWTUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
public class APIRefreshController {

    @RequestMapping("api/member/refresh")
    public Map<String, Object> refresh(
        @RequestHeader("Authorization") String authHeader,
        String refreshToken){

        if(refreshToken == null){
            throw new CustomJWTException("NULL_REFRESH")
        }

        if(authHeader == null || authHeader.length() < 7) {
            throw new CustomJWTException("INVALID_STRING");
        }

        String accessToken = authHeader.substring(7);

        //Access 토큰이 만료되지 않은경우
        if(checkExpiredTokken(accessToken) == false){
            return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        }

        //Refresh토큰 검증
        Map<String, Object> claims = JWTUtil.validateToken(refreshToken);

        log.info("refresh ... claims: " + claims);

        String newAccessToken = JWTUtil.generateToken(claims, 10)

        String newRefreshToken = checkTime((Integer)claims.get("exp")) == true ?
            JWTUtil.generateToken(claims, 60*24) : refreshToken;

        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
    }
 
    //시간이 1시간 미만으로 남은 경우를 체크
    private boolean checkTime(Integer exp){

        //JWP exp를 날짜로 변환
        Date expDate = new Date((long)exp * (1000));

        //현재 시간과의 차이 계산(밀리세컨드)
        long gap = expDate.getTime() - System.currentTimeMillis();

        //분단위 계산
        long leftMin = gap / (1000 * 60);

        //1시간 미만인지 리턴
        return leftMin < 60;
    }

    // 토큰이 만료 되었는지를 체크
    private boolean checkExpiredTokken(String token){

        try{
            JWTUtil.validateToken(token);
        }
        catch(CustomJWTException ex){
            if(ex.getMessage().equals("Expired")){
                return true;
            }
        }

        //만료되지 않았음을 리턴
        return false;
    }

}