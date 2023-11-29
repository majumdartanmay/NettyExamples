package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import server.handlers.EchoServerHandler;

import java.net.InetSocketAddress;

public class EchoServer {

    private final int port;
    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(final String... arg0) {
        final EchoServer server = new EchoServer(8899);
        try (final EventLoopGroup group = new NioEventLoopGroup()) {
            final ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(server.port))
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            final ChannelFuture f = bootstrap.bind().sync();
            System.out.println("Netty listening on " + server.port);
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
