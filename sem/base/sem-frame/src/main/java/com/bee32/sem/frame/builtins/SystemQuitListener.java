package com.bee32.sem.frame.builtins;

import com.bee32.plover.servlet.test.AbstractQuitListener;

public class SystemQuitListener
        extends AbstractQuitListener {

    static int TIMEOUT = 3;

    @Override
    public void quit() {
        SystemMessenger messenger = SystemMessenger.getInstance();
        messenger.sendMessage(String.format("提示：正在准备日常维护工作，系统将在 %d 分钟后停止服务，给您带来不便还请谅解！", //
                TIMEOUT));
        try {
            Thread.sleep(TIMEOUT * 60000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
