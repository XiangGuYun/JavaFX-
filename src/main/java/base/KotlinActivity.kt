package sample.base

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.stage.Stage

typealias Window = Stage


abstract class KotlinActivity:Application(),ViewHelper,ColorHelper{

    lateinit var window: Window
    lateinit var contentView:Parent

    @Throws(Exception::class)
    override fun start(window: Stage) {
        this.window = window
        onCreate(window)
        window.show()
    }

    fun view(id:String): Node? {
        return contentView.lookup("#$id")
    }

    fun setContentView(name:String,width:Number,height:Number, color:Color=Color.WHITE){
        contentView = FXMLLoader.load<Parent>(javaClass.classLoader.getResource("$name.fxml"))
        window.scene = Scene(contentView, width.toDouble(),height.toDouble(),color)
        window.scene.stylesheets.add("style/styles.css")
    }

    abstract fun onCreate(window: Window)

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(KotlinActivity::class.java)
        }
    }



}