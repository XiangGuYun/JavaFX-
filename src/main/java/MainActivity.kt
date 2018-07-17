

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXDialog
import com.jfoenix.controls.JFXListView
import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.layout.StackPane
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.paint.Color
import sample.base.KotlinActivity
import sample.base.Window

/**
 * id与fx:id的区别，前者用在css中，后者用在控制层中，都需要在获取时加#
 * @property dialogExit JFXDialog
 */
class MainActivity : KotlinActivity() {

    val dialogExit = JFXDialog()

    override fun onCreate(window: Window) {
        window.title = "三国演义"
        window.isResizable = false
        window.icons.add(Image("style/image/sanguo.png"))

        val region = region("dialog")
        region.lookup("#btnCancel").setOnMouseClicked {
            dialogExit.close()
        }
        region.lookup("#btnConfirm").setOnMouseClicked {
            System.exit(0)
        }
        dialogExit.content = region

        //监听关闭窗口事件
        window.setOnCloseRequest {
            it.consume()
            dialogExit.show(view("container") as StackPane)
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
