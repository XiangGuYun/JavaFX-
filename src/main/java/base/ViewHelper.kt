package sample.base

import javafx.scene.Node
import javafx.scene.paint.Color
import javafx.scene.text.Font
import java.awt.event.MouseEvent

interface ViewHelper {

    fun TextView.click(function: (MouseEvent)->Unit){
        setOnMouseClicked{function}
    }

    fun TextView.setTextColor(color: Color){
        textFill = color
    }

    fun TextView.setTextSize(size: Number){
        font = Font.font(size.toDouble())
    }

}