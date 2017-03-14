package com.dc.nettywebsocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * Created by lvdanchen on 17/3/14.
 */
public class Connection {
    private int id;

    private Channel channel;

    private ConnectionState state = ConnectionState.Normal;

    public Connection(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public ConnectionState getState() {
        return state;
    }

    public void setState(ConnectionState state) {
        this.state = state;
    }

    public void close() {
        state = ConnectionState.Closed;
        channel.close();
    }

    /**
     *
     * @param message
     *            包
     * @param listener
     *            发送过程的监听处理可以为null
     * @return
     */
    public ChannelFuture send(String message, ChannelFutureListener listener) {
        if (listener != null) {
            return channel.writeAndFlush(message).addListener(listener);
        }
        return channel.writeAndFlush(message);
    }

    public String getRemoteAddress() {
        return channel.remoteAddress().toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
