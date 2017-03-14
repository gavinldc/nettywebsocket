package com.dc.nettywebsocket;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * Created by lvdanchen on 17/3/14.
 */
public class ConnectionSendFutureListener implements ChannelFutureListener {
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (channelFuture.isSuccess()) {
            System.out.println("send success " + System.currentTimeMillis());
        } else {
            System.out.println("connection send msg error  "+ channelFuture.cause());
        }
    }
}
