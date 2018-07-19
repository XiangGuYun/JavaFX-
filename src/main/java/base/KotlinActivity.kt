package sample.base

import base.Window
import com.jfoenix.controls.JFXListView
import javafx.application.Application
import javafx.beans.Observable
import javafx.beans.value.ObservableValue
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.Pane
import javafx.scene.layout.Region
import javafx.scene.media.Media
import javafx.scene.paint.Color
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.stage.WindowEvent
import java.io.*


abstract class KotlinActivity:Application(),ViewHelper,ColorHelper{

    lateinit var window: Window
    lateinit var contentView:Parent
    val screen = Screen.getPrimary()
    val minX = screen.bounds.minX
    val minY = screen.bounds.minY
    val maxX = screen.bounds.maxX
    val maxY = screen.bounds.maxY

    @Throws(Exception::class)
    override fun start(window: Stage) {
        this.window = window
        onCreate(window)
        window.show()
    }

    fun moveToCenter(width:Number,height: Number){
        window.x = (maxX - width.toDouble())/2
        window.y = (maxY-height.toDouble())/2
    }

    fun view(id:String): Node? {
        return contentView.lookup("#$id")
    }

    fun setContentView(name:String){
        contentView = FXMLLoader.load<Parent>(javaClass.classLoader.getResource("$name.fxml"))
        window.scene = Scene(contentView)
        window.scene.stylesheets.add("style/styles.css")
    }

    fun setContentView(name:String,width:Number,height: Number){
        contentView = FXMLLoader.load<Parent>(javaClass.classLoader.getResource("$name.fxml"))
        window.scene = Scene(contentView,width.toDouble(),height.toDouble())
        window.scene.stylesheets.add("style/styles.css")
        moveToCenter(width,height)
    }


    abstract fun onCreate(window: Window)

    fun scene(name:String,width: Number,height: Number): Scene {
        contentView = FXMLLoader.load<Parent>(javaClass.classLoader.getResource("$name.fxml"))
        return Scene(contentView,
                width.toDouble(),height.toDouble())
    }

    fun region(name:String): Region {
        return FXMLLoader.load<Region>(javaClass.classLoader.getResource("$name.fxml"))
    }

    /**
     * 添加样式文件，如果之前已经添加过，那么会替换掉之前的文件
     * @param path String
     */
    fun addStyle(path:String){
        window.scene.stylesheets.add("$path.css")
    }

    /**
     * 跳转到另外一个窗口
     * @param name String
     * @param width Number
     * @param height Number
     */
    fun startActivity(name:String,width: Number,height: Number){
        window.scene = scene(name,width,height)
        moveToCenter(width,height)
    }

    fun String.pln(){
        println(this)
    }

//    fun file(path:String): File {
//        val input = javaClass.getResourceAsStream(path)
//        val reader = input.reader()
//        println(reader.readText())
//        return File(javaClass.getResource(path).toURI())
//    }

    fun file(path:String): InputStreamReader {
        val input = javaClass.getResourceAsStream(path)
        return input.reader()
    }

    fun fileText(path:String): String {
        val input = javaClass.getResourceAsStream(path)
        val reader = input.reader()
        return reader.readText()
    }

    fun <T> JFXListView<T>.itemClick(func:(T)->Unit){
        selectionModel.selectedItemProperty().addListener { observable, oldValue, newValue ->
           func.invoke(newValue)
        }
    }

    fun <T> JFXListView<T>.addData(data:T){
       items.add(data)
    }

    fun media(path:String): Media {
        return Media(javaClass.getResource(path).toString())
    }

    fun Pane.addChild(child:Node){
        children.add(child)
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(KotlinActivity::class.java)
        }
    }

}