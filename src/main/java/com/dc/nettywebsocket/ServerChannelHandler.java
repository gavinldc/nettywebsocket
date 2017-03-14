package com.dc.nettywebsocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.websocketx.*;

import java.nio.ByteBuffer;

/**
 * Created by lvdanchen on 17/3/14.
 */
public class ServerChannelHandler extends ChannelInboundHandlerAdapter {




    @Override
    public void channelActive(ChannelHandlerContext ctx)throws Exception{
         System.out.println("channel open");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
          if(msg instanceof HttpRequest){
              System.out.println("this is a httpRequest");
              ctx.close();
          }else if(msg instanceof WebSocketFrame){
              System.out.println("this is a websocketrequest");
              try {
                  processWebsocketRequest(ctx.channel(),(WebSocketFrame)msg);
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }else{
              System.out.println("unknown request type");
              ctx.close();
          }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause)throws Exception{
          cause.printStackTrace();
          ctx.close();
    }


    private void processWebsocketRequest(Channel channel, WebSocketFrame request) throws Exception{
        if(request instanceof CloseWebSocketFrame){
            channel.close().sync();
        }else if(request instanceof PingWebSocketFrame){
            channel.write(new PongWebSocketFrame(request.content()));
        }else if(request instanceof TextWebSocketFrame){
            System.out.println(((TextWebSocketFrame)request).text());
        }else {
            System.out.println("unknown message type");
        }
    }
}
