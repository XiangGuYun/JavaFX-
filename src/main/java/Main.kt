import com.jfoenix.controls.JFXDialog
import com.jfoenix.controls.JFXListView
import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import sample.base.KotlinActivity
import sample.base.Window
import java.util.*

/**
 * id与fx:id的区别，前者用在css中，后者用在控制层中，都需要在获取时加#
 * @property dialogExit JFXDialog
 */
class Main : KotlinActivity() {

    val dialogExit = JFXDialog()
    val player = MediaPlayer(Media(javaClass.getResource("/media/gtp.mp3").toString()))

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

        setContentView("sample",650,320)
        moveToCenter(640,380)
        initView()
        initEvent()
        val bg = view("bg") as HBox
        for (i in 1..27){
            bg.children.add(ImageView(Image("/style/image/head$i.jpg")))
        }
        Thread({
            while (true){
                Thread.sleep(50)
                bg.translateX=bg.translateX+1
                println("${bg.translateX}-----$maxX--------")
            }
        }).start()

    }

    private fun initEvent() {
        player.cycleCount = Int.MAX_VALUE
        player.play()
    }

    private fun initView() {
        val btn = view("box") as VBox
        val image = Image("/style/image/enter.png")
        val iv = ImageView(image)
        btn.children.add(iv)
        btn.setOnMouseClicked {
                startActivity("second", 1200, 800)
                player.stop()
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
            }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(Main::class.java)
        }
    }

}
