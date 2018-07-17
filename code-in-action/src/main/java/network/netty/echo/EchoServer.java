package network.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Usage: <b> an echo Server by netty </b>
 * Test by <b>echo 'doubi' | nc IP PORT</b>
 *
 * @author lucifer
 *         Date 2018-7-17
 *         Device Aurora R5
 */
@Slf4j
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new EchoServer(233).start();
    }

    public void start() {
        log.info("Server starup, listening on port {}", port);

        NioEventLoopGroup group = new NioEventLoopGroup();
        EchoServerHandler handler = new EchoServerHandler();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(handler);
                        }
                    });
            ChannelFuture future = bootstrap.bind().sync();     // sync till binding finish
            future.channel().closeFuture().sync();              // sync till channel finish
        } catch (InterruptedException e) {
            log.error("Server exception", e);
        } finally {
            group.shutdownGracefully();     // Release all resources
        }
    }
}
