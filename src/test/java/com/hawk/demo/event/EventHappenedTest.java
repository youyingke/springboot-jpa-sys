package com.hawk.demo.event;

import com.hawk.demo.event.createevent.DoSomethingEvent;
import com.hawk.demo.sys.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
@SpringBootTest
@RunWith(SpringRunner.class)
/**
 * Created by Lenovo on 2019-06-20.
 */
public class EventHappenedTest {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private UserService userService;

    @Test
    public void TestEventHappened()
    {
        userService.findById(1l);
        DoSomethingEvent doSomethingEvent=new DoSomethingEvent("查找用户事件发生");
        applicationContext.publishEvent(doSomethingEvent);
    }
}
