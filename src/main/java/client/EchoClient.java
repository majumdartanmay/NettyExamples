package client;

import client.handlers.EchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import server.EchoServer;

import java.net.InetSocketAddress;

public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(final String... arg0) throws InterruptedException {
        final EchoClient client = new EchoClient("127.0.0.1", EchoServer.PORT);
        final EventLoopGroup group = new NioEventLoopGroup();
        try  {
            final Bootstrap clientBootstrap = new Bootstrap();
            clientBootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(client.host, client.port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture f = clientBootstrap.connect().sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            group.shutdownGracefully().sync();
        }
    }
}
