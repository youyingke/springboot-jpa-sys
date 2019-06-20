package com.hawk.demo.event.eventlistener;

import com.hawk.demo.event.createevent.DoSomethingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by Lenovo on 2019-06-20.
 * 监听DoSomeThing事件
 */
@Component
public class MessageListener implements ApplicationListener<DoSomethingEvent> {
    @Override
    public void onApplicationEvent(DoSomethingEvent doSomethingEvent) {
        System.out.println("监听到事件发生"+doSomethingEvent.getSource()+"Do somethingEven happened");
    }
}
