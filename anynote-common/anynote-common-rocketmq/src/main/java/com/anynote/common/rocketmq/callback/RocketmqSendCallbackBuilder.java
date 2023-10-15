package com.anynote.common.rocketmq.callback;

/**
 * @author paulG
 * @since 2020/11/4
 **/
public class RocketmqSendCallbackBuilder {


    public static RocketmqSendCallback commonCallback() {
        return new RocketmqSendCallback();
    }

}
