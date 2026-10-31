package com.krishna.blackholeenergy
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tv = TextView(this)
        tv.text = "BLACK HOLE ENERGY FINAL\nBY KRISHNA\nSUCCESS!"
        tv.textSize = 24f
        tv.setPadding(50,200,50,50)
        setContentView(tv)
    }
}
