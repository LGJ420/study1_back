package org.zerock.mallapi.controller.advice;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zerock.mallapi.util.CustomJWTException;

/*
 * 사용자가 없는 번호를 전송했거나
 * 페이지를 숫자가 아닌 문자로 전송을 했을때의 예외처리를
 * Advice가 처리해준다
 * 그래서 Controller에 advice패키지를 만들고
 * @RestControllerAdvice를 추가해서 Advice 클래스를 만들어준다
 */

@RestControllerAdvice
public class CustomControllerAdvice {
    

    /*
     * 그리고 @ExceptionHandler를 이용해서 특정한 타입의 예외가 발생하면
     * 이에 대한 결과 데이터를 만들어내는 방식으로 작성
     */
    @ExceptionHandler(NoSuchElementException.class) // 자료가 없을때 예외
    protected ResponseEntity<?> notExist(NoSuchElementException e) {

        String msg = e.getMessage();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("msg", msg));
    }

    
    @ExceptionHandler(MethodArgumentNotValidException.class) // 알맞은 파라미터가 아닐때 예외
    protected ResponseEntity<?> handleIllegalArgummentException(MethodArgumentNotValidException e) {

        String msg = e.getMessage();

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("msg", msg));
    }


    @ExceptionHandler(CustomJWTException.class)
    protected ResponseEntity<?> handleJWTException(CustomJWTException e){

        String msg = e.getMessage();

        return ResponseEntity.ok().body(Map.of("error", msg));
    }
}
