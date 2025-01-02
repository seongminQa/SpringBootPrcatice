package kr.co.znznfltm.myrestfulservice.dao;

import kr.co.znznfltm.myrestfulservice.bean.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(new User(1, "Seongmin", new Date(), "test1", "111111-1111111"));
        users.add(new User(2, "Alice", new Date(), "test2", "111111-1111111"));
        users.add(new User(3, "Elena", new Date(), "test3", "111111-1111111"));
    }

    // 유저 전체 검색
    public List<User> findAll() {
        return users;
    }

    // 새로운 유저 저장
    public User save(User user) {
        if(user.getId() == null) {
            user.setId(++usersCount);
        }

        if(user.getJoinDate() == null) {
            user.setJoinDate(new Date());
        }

        users.add(user);

        return user;
    }

    // 유저 한 명 검색
    public User findOne(int id) {
        for(User user : users) {
            if(user.getId() == id) {
                return user;
            }
        }

        return null;
    }

    // 유저 삭제
    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator();

        while(iterator.hasNext()) {
            User user = iterator.next();

            if(user.getId() == id) {
                iterator.remove();
                return user;
            }
        }

        return null;
    }

}
