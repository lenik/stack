package com.bee32.sem.frame.builtins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.servlet.test.AbstractQuitListener;

public class SystemQuitListener
        extends AbstractQuitListener {

    static final Logger logger = LoggerFactory.getLogger(SystemQuitListener.class);

    int TIMEOUT = 5;

    @Override
    public int quit() {
        logger.info("Send shutdown message...");
        SystemMessenger.sendMessage(String.format(//
                "正在准备日常维护工作，系统将在 %d 秒钟后停止服务，本次维护时间大概需要15分钟，给您带来的不便请谅解！", //
                TIMEOUT));
        return TIMEOUT;
    }

}
