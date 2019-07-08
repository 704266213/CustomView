package com.customview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun shapeDraw(view: View) {
        val intent = Intent(this, DrawBaseShapeActivity::class.java);
        startActivity(intent)
    }

    fun pathDraw(view: View) {
        val intent = Intent(this, PathDrawActivity::class.java);
        startActivity(intent)
    }

    fun switchButton(view: View) {
        val intent = Intent(this, SwitchViewActivity::class.java);
        startActivity(intent)
    }
}
