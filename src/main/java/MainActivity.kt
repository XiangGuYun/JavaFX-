

import com.jfoenix.controls.JFXButton
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import javafx.application.Application
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import sample.base.KotlinActivity
import sample.base.Window
import java.net.InetSocketAddress


class MainActivity : KotlinActivity() {


    override fun onCreate(window: Window) {
        window.title = "我的客户端"
        window.isResizable = false
        setContentView("sample",640,380)
        initView()
        initEvent()
    }

    private fun initEvent() {
        val player = MediaPlayer(Media(javaClass.getResource("/media/gtp.mp3").toString()))
        player.cycleCount = Int.MAX_VALUE
        player.play()
    }

    private fun initView() {
        val btn = view("btnLogin") as JFXButton
        btn.setOnMouseClicked {
//            if((view("tvUsername") as TextField).text=="yida9456388"&&
//                    (view("tvPassword") as PasswordField).text=="123456"){
//                println("登录成功")
//            }
            val group = NioEventLoopGroup()
            val b = Bootstrap()
            b.group(group)
                    .channel(NioSocketChannel::class.java)
                    .remoteAddress(InetSocketAddress("172.24.237.3", 9966))
                    .handler(object : ChannelInitializer<SocketChannel>() {
                        @Throws(Exception::class)
                        override fun initChannel(socketChannel: SocketChannel) {
                            socketChannel.pipeline().addLast(EchoClientHandler())
                        }
                    })
            try {
                val f = b.connect().sync()
                f.channel().closeFuture().sync()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                try {
                    group.shutdownGracefully().sync()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(MainActivity::class.java)
        }
    }

}
