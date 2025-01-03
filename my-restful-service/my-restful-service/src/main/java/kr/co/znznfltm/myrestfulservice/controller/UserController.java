package kr.co.znznfltm.myrestfulservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.znznfltm.myrestfulservice.bean.User;
import kr.co.znznfltm.myrestfulservice.dao.UserDaoService;
import kr.co.znznfltm.myrestfulservice.exception.UserNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Tag(name = "user-controller", description = "일반 사용자 서비스를 위한 컨트롤러") // Swagger 클래스에 대한 설명 어노테이션
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users") // path="/users"와 같다. path는 생략 가능하다.
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    /*@GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);
        
        // 만약 유저가 없다면. // 현재 postman으로 요청을 했을 때, ID가 없어도 200 OK 코드를 반환하기 때문에 예외처리를 하는 것
        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user;
    }*/
    // HATEOAS 를 적용 -> REST API 레벨 3단계를 위함.
    /*@GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);

        // 만약 유저가 없다면. // 현재 postman으로 요청을 했을 때, ID가 없어도 200 OK 코드를 반환하기 때문에 예외처리를 하는 것
        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        // 엔티티 모델은 직접 생성자가 제공되지 않는다.
        // 우리가 위에서 반환 받아왔던 유저 객체를 통해 만들 수 있다.
        EntityModel entityModel = EntityModel.of(user);
        // HATEOAS 기능이라고 해서 링크 관련되어 있는 기능을 추가하지 않은 상태이기 때문에
        // 지금 만들어진 유저 객체에다가 링크 작업을 추가한다.
        WebMvcLinkBuilder linTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        // 엔티티 모델이라는 객체에다가 링크 작업을 연동
        // 어떤 이름을 가지고 화면에 표시가 되게 할 건지 적용
        entityModel.add(linTo.withRel("all-users"));  // all-users --> http://localhost:8088/users

        return entityModel;
    }*/
    @Operation(summary = "사용자 정보 조회 API", description = "사용자 ID를 이용해서 사용자 상세 정보 조회를 합니다.") // Swagger를 이용한 API 메소드 설명
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST!!"),
            @ApiResponse(responseCode = "404", description = "USER NOT FOUND!!"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR!!"),
    })
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(
            @Parameter(description = "사용자 ID", required = true, example = "1") @PathVariable int id) {
        User user = service.findOne(id);

        // 만약 유저가 없다면. // 현재 postman으로 요청을 했을 때, ID가 없어도 200 OK 코드를 반환하기 때문에 예외처리를 하는 것
        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        // 엔티티 모델은 직접 생성자가 제공되지 않는다.
        // 우리가 위에서 반환 받아왔던 유저 객체를 통해 만들 수 있다.
        EntityModel entityModel = EntityModel.of(user);
        // HATEOAS 기능이라고 해서 링크 관련되어 있는 기능을 추가하지 않은 상태이기 때문에
        // 지금 만들어진 유저 객체에다가 링크 작업을 추가한다.
        WebMvcLinkBuilder linTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        // 엔티티 모델이라는 객체에다가 링크 작업을 연동
        // 어떤 이름을 가지고 화면에 표시가 되게 할 건지 적용
        entityModel.add(linTo.withRel("all-users"));  // all-users --> http://localhost:8088/users

        return entityModel;
    }

    // CREATE
    // input - details of uesr
    // output - CREATE & Return the create URI
//    @PostMapping("/users")
//    public void createUser(@RequestBody User user) {
//        User savedUser = service.save(user);
//    }
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

//    @DeleteMapping("/users/{id}")
//    public void deleteUser(@PathVariable int id) {
//        User user = service.deleteById(id);
//
//        if(user == null) {
//            throw new UserNotFoundException(String.format("ID[%s] not found", id));
//        }
//    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {
        User user = service.deleteById(id);

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return ResponseEntity.noContent().build();
    }

}
