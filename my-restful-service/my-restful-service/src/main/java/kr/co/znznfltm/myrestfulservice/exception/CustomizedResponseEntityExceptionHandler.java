package kr.co.znznfltm.myrestfulservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

// postman으로 요청처리 했을 땐, 예외에 관한 trace까지 보여주게 된다.
// 이는 보안상 문제를 일으키기 때문에 처리해준다.

@ControllerAdvice // 모든 컨트롤러가 실행될 때 반드시 이 것이 실행될 수 있도록 함.
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // 모든 에러에 대해서는 이렇게 처리를 하겠다는 정의를 해보았다.
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 유저를 찾을 때, 유저가 없는 경우엔 404 에러를 내겠다는 의미. -> 디비 서버엔 리소스가 없는데, 사용자의 요청이 잘못된 경우.
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

}
