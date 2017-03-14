package com.dc.nettywebsocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lvdanchen on 17/3/14.
 */
public class ConnectionManager {
    public static ConnectionManager getInstance(){
        return InstanceFactory.instance;
    }

    private static class InstanceFactory{
        private static ConnectionManager instance=new ConnectionManager();
    }

    private final ConcurrentHashMap<ChannelId,Connection> connections=new ConcurrentHashMap<ChannelId,Connection>();

    public Connection getConnection(ChannelId channelId){
        return connections.get(channelId);
    }

    public Connection getConnection(Channel channel){
        return connections.get(channel.id());
    }

    public void remove(Channel channel){
        connections.remove(channel.id());
    }

    /**
     * 针对client返回单一的connection
     */
    public Connection getConnection(){
        Iterator<Map.Entry<ChannelId,Connection>> iter=connections.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<ChannelId,Connection> entry=iter.next();
            if(!entry.getValue().getState().equals(ConnectionState.Closed)){
                return entry.getValue();
            }
        }
        return null;
    }

    public void add(Channel channel,Connection client){
        connections.put(channel.id(), client);
    }

    public synchronized Connection createConnection(Channel channel){
        Connection connection=connections.get(channel.id());
        if(connection!=null&&!connection.getState().equals(ConnectionState.Closed)){
            return connection;
        }
        connection=new Connection(channel);
        connection.setState(ConnectionState.Connected);
        connections.put(channel.id(),connection);
        return connection;
    }
}
