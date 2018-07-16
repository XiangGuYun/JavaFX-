import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * 注意字符集的设置一定要统一为UTF-8
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = Unpooled.copiedBuffer("Netty rocks!",Charset.forName("UTF-8"));
        System.out.println(byteBuf.toString(Charset.forName("UTF-8")));
        ctx.writeAndFlush(byteBuf);
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("Client received: "+byteBuf.toString(Charset.forName("UTF-8")));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
