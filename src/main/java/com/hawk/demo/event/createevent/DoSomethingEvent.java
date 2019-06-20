package com.hawk.demo.event.createevent;

import org.springframework.context.ApplicationEvent;

/**
 * Created by hawk on 2019-06-20.
 * 首先声明一个事件
 *
 */
public class DoSomethingEvent extends ApplicationEvent {
    public DoSomethingEvent(Object source) {
        super(source);
    }
}
