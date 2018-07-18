import base.Window
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
import javafx.scene.media.MediaPlayer
import sample.base.KotlinActivity

/**
 * id与fx:id的区别，前者用在css中，后者用在控制层中，都需要在获取时加#
 * @property dialogExit JFXDialog
 */
class Main : KotlinActivity() {

    val dialogExit = JFXDialog()//退出对话框
    val player = MediaPlayer(media("/media/gtp.mp3")) //媒体播放器
    lateinit var ivEnter: ImageView //进入按钮

    override fun onCreate(window: Window) {
        window.title = "三国演义"
        window.isResizable = false
        window.icons.add(Image("image/sanguo.png"))
        //监听关闭窗口事件
        window.setOnCloseRequest {
            it.consume()
            dialogExit.show(view("container") as StackPane)
        }
        setContentView("layout/sample",640,380)

        setExitDialog()//设置退出对话框
        setPlayerAndGallery()//播放音乐和画廊
        setEnterBtn()//设置进入按钮
    }

    private fun setEnterBtn() {
        val btnEnter = view("box") as VBox
        ivEnter = ImageView(Image("/image/enter.png"))
        btnEnter.addChild(ivEnter)
        btnEnter.setOnMouseClicked {
            player.stop()
            startActivity("layout/second", 1200, 800)
            addStyle("/style/list")
            readerActivity()
        }
    }

    private fun readerActivity() {
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

    private fun setExitDialog() {
        val region = region("layout/dialog")
        region.lookup("#btnCancel").setOnMouseClicked {
            dialogExit.close()
        }
        region.lookup("#btnConfirm").setOnMouseClicked {
            System.exit(0)
        }
        dialogExit.content = region
    }

    private fun setPlayerAndGallery() {
        player.cycleCount = Int.MAX_VALUE
        player.play()
        val bg = view("bg") as HBox
        for (i in 1..27){
            bg.children.add(ImageView(Image("/image/head$i.jpg")))
        }
        Thread({
            while (true){
                Thread.sleep(50)
                bg.translateX=bg.translateX+1
                ivEnter.rotate = ivEnter.rotate+1
            }
        }).start()
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(Main::class.java)
        }
    }

}
