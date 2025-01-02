package kr.co.znznfltm.myrestfulservice.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import kr.co.znznfltm.myrestfulservice.bean.AdminUser;
import kr.co.znznfltm.myrestfulservice.bean.AdminUserV2;
import kr.co.znznfltm.myrestfulservice.bean.User;
import kr.co.znznfltm.myrestfulservice.dao.UserDaoService;
import kr.co.znznfltm.myrestfulservice.exception.UserNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

// 관리자를 위한 유저 검색
@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService service;

    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    // /admin/users/{id}
//    @GetMapping("/users/{id}")
//    @GetMapping("/v1/users/{id}") // Version관리를 적용 (URI방식)
//    @GetMapping(value="/users/{id}", params = "version=1") // Version 관리를 적용 (Request 파라미터 방식)
//    @GetMapping(value="/users/{id}", headers = "X-API-VERSION=1") // Version 관리를 적용 (Request 헤더 방식)
    @GetMapping(value="/users/{id}", produces = "application/vnd.company.appv1+json") // Version 관리를 적용 (마임타임 방식)
    public MappingJacksonValue retrieveUser4Admin(@PathVariable int id) {
        User user = service.findOne(id);

        AdminUser adminUser = new AdminUser();

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            // adminUser.setName(user.getName()); // 프로퍼티가 한 두가지면 상관이 없지만 많다면 그에 해당하는 값을 다 일일이 복사해주는 작업을 해주어야한다.
            BeanUtils.copyProperties(user, adminUser); // 타겟, 대상
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

//    @GetMapping("/v2/users/{id}") // Version관리를 적용(URI방식)
//    @GetMapping(value="/users/{id}", params = "version=2") // Version 관리를 적용 (Request 파라미터 방식)
//@GetMapping(value="/users/{id}", headers = "X-API-VERSION=2") // Version 관리를 적용 (Request 헤더 방식)
@GetMapping(value="/users/{id}", produces = "application/vnd.company.appv2+json") // Version 관리를 적용 (마임타임 방식)
    public MappingJacksonValue retrieveUser4AdminV2(@PathVariable int id) {
        User user = service.findOne(id);

        AdminUserV2 adminUser = new AdminUserV2();

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            // adminUser.setName(user.getName()); // 프로퍼티가 한 두가지면 상관이 없지만 많다면 그에 해당하는 값을 다 일일이 복사해주는 작업을 해주어야한다.
            BeanUtils.copyProperties(user, adminUser); // 타겟, 대상
            adminUser.setGrade("VIP");  // grade 프로퍼티가 추가되었음.
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

    // /admin/users
    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers4Admin() {
        List<User> users = service.findAll();

        List<AdminUser> adminUsers = new ArrayList<>();
        AdminUser adminUser = null;

        for(User user : users) {
            adminUser = new AdminUser();
            BeanUtils.copyProperties(user, adminUser);

            adminUsers.add(adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
        mapping.setFilters(filters);

        return mapping;
    }

}
