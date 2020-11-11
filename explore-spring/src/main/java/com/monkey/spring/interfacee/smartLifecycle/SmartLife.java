package com.monkey.spring.interfacee.smartLifecycle;

import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Component
public class SmartLife implements SmartLifecycle {
    /**
     * isRunning为false时，调用start才能生效
     */
    @Override
    public void start() {

        System.out.println("smart start");
    }

    /**
     * 如果是smartLifeCycle，那么这个stop方法就么用
     */
    @Override
    public void stop() {
        System.out.println("life stop");
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    /**
     * 设置为true，就不用显示的调用start()方法，spring会自动帮我们调用start方法
     * @return
     */
    @Override
    public boolean isAutoStartup() {
        return false;
    }

    /**
     * isRunning为true时，调用stop才能生效
     */
    @Override
    public void stop(Runnable callback) {
        System.out.println("smartLife stop");
        callback.run();
    }

    @Override
    public int getPhase() {
        return 0;
    }
}
