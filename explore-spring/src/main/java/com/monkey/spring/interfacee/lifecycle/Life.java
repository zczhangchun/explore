package com.monkey.spring.interfacee.lifecycle;

import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

@Component
public class Life implements Lifecycle {
    /**
     * isRunning为false时，调用start才能生效
     */
    @Override
    public void start() {
        System.out.println("start");
    }

    /**
     * isRunning为true时，调用stop才能生效
     */
    @Override
    public void stop() {
        System.out.println("stop");
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
