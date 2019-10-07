package com.cisco.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthApplication.class, properties = "spring.data.mongodb.host=mongo-test")
public class AuthApplicationTest {

    @Test
    public void contextLoads() {}

    @Test
    public void main() {
        AuthApplication.main(new String[]{
                "--spring.data.mongodb.host=mongo-test",
        });

    }
}
