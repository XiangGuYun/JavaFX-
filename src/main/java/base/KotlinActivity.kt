package sample.base

import com.jfoenix.controls.JFXListView
import javafx.application.Application
import javafx.beans.Observable
import javafx.beans.value.ObservableValue
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.paint.Color
import javafx.stage.Screen
import javafx.stage.Stage
import java.io.File

typealias Window = Stage


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

    abstract fun onCreate(window: Window)

    fun scene(name:String,width: Number,height: Number): Scene {
        contentView = FXMLLoader.load<Parent>(javaClass.classLoader.getResource("$name.fxml"))
        return Scene(contentView,
                width.toDouble(),height.toDouble())
    }

    fun setStyle(path:String){
        window.scene.stylesheets.add("$path.css")
    }

    fun startActivity(name:String,width: Number,height: Number){
        window.scene = scene(name,width,height)
    }

    fun String.pln(){
        println(this)
    }

    fun file(path:String): File {
        return File(javaClass.getResource(path).toURI())
    }

    fun <T> JFXListView<T>.itemClick(func:(T)->Unit){
        selectionModel.selectedItemProperty().addListener { observable, oldValue, newValue ->
           func.invoke(newValue)
        }
    }

    fun <T> JFXListView<T>.addData(data:T){
       items.add(data)
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(KotlinActivity::class.java)
        }
    }



}