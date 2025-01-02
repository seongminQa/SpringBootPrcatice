package kr.co.znznfltm.myrestfulservice.controller;

import kr.co.znznfltm.myrestfulservice.bean.HelloWorldBean;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {
    // MyRestfulServiceApplication에 등록된 Bean을 가져와보자.

    // 메세지 소스 값을 하나 선언하고 생성자를 통해 주입 받도록 한다.
    private MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    // '주입'은 스프링 컨텍스트에 의해 기동될 때 해당하는 인스턴스를 미리 만들어 놓고
    // 메모리에 등록을 한다. 위와 같은 경우는 미리 등록되어진 다른 스프링의 빈을 가지고 와서
    // 현재 있는 클래스에서 사용할 수 있도록 객체를 생성하지 않더라도 참조할 수 있는 형태로 받아온다.

    // HTTP

    // GET
    // URI - /hello-world
    // @RequestMapping(method=RequestMethod.GET, path="/hello-world")
    @GetMapping(path="/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    // GET
    // URI - /hello-world-bean
    // @RequestMapping(method=RequestMethod.GET, path="/hello-world-bean")
    @GetMapping(path="/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello Wolrd!");
    }

    @GetMapping(path="/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBeanPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World!!, %s", name));
    }

    @GetMapping(path="/hello-world-internationalized")
    public String helloWorldInternalized(
            @RequestHeader(name="Accept-Language", required = false) Locale locale) {

        return messageSource.getMessage("greeting.message", null, locale);
    }

}
