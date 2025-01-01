package kr.co.znznfltm.myrestfulservice.bean;


import lombok.AllArgsConstructor;
import lombok.Data;

// lombok 에러 때문에 사용 못하고 있다..
@Data
@AllArgsConstructor
public class HelloWorldBean {
    private String message;

}
