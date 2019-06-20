package com.hawk.demo.event;

import com.hawk.demo.event.createevent.DoSomethingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Created by Lenovo on 2019-06-20.
 */
public class TestEvent {

    @Autowired
    private static ApplicationContext applicationContext;


    public static void main(String args[])
    {
             TestEvent testEvent=new TestEvent();
        testEvent.doSomeThing();
        //发布dosomething事件

        DoSomethingEvent doSomeThingEvent=new DoSomethingEvent("do something event");
        applicationContext.publishEvent(doSomeThingEvent);

    }

public void doSomeThing()
{
    System.out.println("do something event");
}
}
