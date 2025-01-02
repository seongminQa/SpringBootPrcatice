package kr.co.znznfltm.myrestfulservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 현재 postman으로 디비에 없는 요청을 했을 때, 500번대의 에러 상태를 반환한다.
// 이는 디비 서버에 대한 잘못이 아닌 사용자의 요청처리에 대한 에러이기 때문에 404 Status를 발생시키기 위해 어노테이션을 붙여준다.
@ResponseStatus(HttpStatus.NOT_FOUND) // 서버의 리소스를 찾을 수 없다는 404 -> NOT_FOUND
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
