package com.lantu;

import com.lantu.common.utils.JwtUtil;
import com.lantu.sys.entity.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtUtilTest {
    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testCreateJwt(){
        User user=new User();
        user.setUsername("zhangsan");
        user.setPhone("12399988898");
        String token=jwtUtil.createToken(user);
        System.out.println(token);
    }
    @Test
    public void testParseJwt(){
        String token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1Y2U5ZDQxNS05NjdmLTQyYmQtYmU0ZC1jZWUxZTNhNzY1OGEiLCJzdWIiOiJ7XCJwaG9uZVwiOlwiMTIzOTk5ODg4OThcIixcInVzZXJuYW1lXCI6XCJ6aGFuZ3NhblwifSIsImlzcyI6InN5c3RlbSIsImlhdCI6MTY3OTU3MzY2MSwiZXhwIjoxNjc5NTc1NDYxfQ.uRxqdH5OShKTMZLPyGY879_cVxxu7LKf6v9lDyQP-GE";
        Claims claims = jwtUtil.parseToken(token);
        System.out.println(claims);
    }
    @Test
    public void testParseJwt2(){
        String token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1Y2U5ZDQxNS05NjdmLTQyYmQtYmU0ZC1jZWUxZTNhNzY1OGEiLCJzdWIiOiJ7XCJwaG9uZVwiOlwiMTIzOTk5ODg4OThcIixcInVzZXJuYW1lXCI6XCJ6aGFuZ3NhblwifSIsImlzcyI6InN5c3RlbSIsImlhdCI6MTY3OTU3MzY2MSwiZXhwIjoxNjc5NTc1NDYxfQ.uRxqdH5OShKTMZLPyGY879_cVxxu7LKf6v9lDyQP-GE";
        User user = jwtUtil.parseToken(token, User.class);
        System.out.println(user);
    }
}
