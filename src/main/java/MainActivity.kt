

import base.AlertDialog
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXListView
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import javafx.application.Application
import javafx.beans.value.ObservableValue
import javafx.geometry.Pos
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import sample.base.KotlinActivity
import sample.base.TextView
import sample.base.Window
import java.io.File
import java.net.InetSocketAddress
import javax.swing.event.ChangeListener
import com.jfoenix.controls.JFXDialog
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.layout.*
import javafx.scene.paint.Color


class MainActivity : KotlinActivity() {


    override fun onCreate(window: Window) {
        window.title = "三国演义"
        window.isResizable = false
        window.icons.add(Image("style/image/sanguo.png"))

        val dialog = JFXDialog()
        val box = VBox()
        box.styleClass.add("dialog")
        box.alignment=Pos.CENTER
        box.prefWidth=200.0
        box.prefHeight=100.0
        val label = Label("确定要退出吗？")
        label.textFill = Color.BLACK
        box.children.add(label)
        val childBox = HBox()
        childBox.alignment=Pos.CENTER
        val btnCancel = JFXButton("取消")
        val btnConfirm = JFXButton("确定")
        btnCancel.setOnMouseClicked { dialog.close() }
        btnConfirm.setOnMouseClicked {
            dialog.close()
            System.exit(0) }
        childBox.children.add(btnCancel)
        childBox.children.add(btnConfirm)
        box.children.add(childBox)
        dialog.content = box

        window.setOnCloseRequest {
            it.consume()
            dialog.show(view("container") as StackPane)
        }
        setContentView("sample")
        moveToCenter(640,380)
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
            if ((view("tvUsername") as TextField).text == "1" &&
                    (view("tvPassword") as PasswordField).text == "1") {
                "登录成功".pln()
                startActivity("second", 1200, 800)
                moveToCenter(1200,800)
                setStyle("style/list")
                val tvShow = view("tvShow") as Label
                val scroll = view("scroll") as ScrollPane
                tvShow.text = file("/data/c1.txt").readText()
                tvShow.setTextSize(15)
                tvShow.setTextColor(color("000000"))
                tvShow.alignment = Pos.CENTER
                val file = file("/data/catalog.txt")
                val items = file.readLines()
                val list = view("listView") as JFXListView<Label>
                for (item in items) {
                    val label = Label(item)
                    label.styleClass.add("tvCell")
                    list.addData(label)
                }
                //设置列表项点击事件
                list.itemClick { newValue ->
                    scroll.vvalue = 0.0//回到顶部
                    tvShow.text = file("/data/c${items.indexOf(newValue.text) + 1}.txt").readText()
                }
            }else{
                val dialog = JFXDialog()
                val text = Label()
                text.prefWidth = 200.0
                text.prefHeight = 40.0
                text.text = "账号或密码错误!"
                text.alignment =Pos.CENTER
                text.setTextSize(20)
                dialog.content = text
                dialog.setOnDialogOpened {
                    text.textFill = Color.RED
                }
                dialog.transitionType = JFXDialog.DialogTransition.BOTTOM
                dialog.show(view("container") as StackPane)
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
