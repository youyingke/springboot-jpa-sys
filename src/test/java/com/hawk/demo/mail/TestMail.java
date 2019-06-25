package com.hawk.demo.mail;

import com.hawk.demo.util.MailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Lenovo on 2019-06-25.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMail {

    @Autowired
    private MailUtil mailUtil;
    @Test
    public void testMail()
    {
     mailUtil.sendSimpleEmail("youyk@tsinghua-tj.org","ceshi","ceshi");
    }


}
