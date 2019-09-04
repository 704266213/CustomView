package com.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_girl_layout.*
import kotlinx.android.synthetic.main.activity_girl_layout.girlLayout
import kotlinx.android.synthetic.main.activity_scroll_girl_layout.*

class GirlLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_girl_layout)
        setContentView(R.layout.activity_scroll_girl_layout)

//        val layoutInflater = LayoutInflater.from(this)
//        add.setOnClickListener {
//
//            val view = layoutInflater.inflate(R.layout.girl_layout_item, girlLayout)
//            girlLayout.addView(view)
//    }

        one.setOnClickListener {
            Toast.makeText(this, "==========one============", Toast.LENGTH_SHORT).show()
        }

        two.setOnClickListener {
            Toast.makeText(this, "==========two============", Toast.LENGTH_SHORT).show()
        }
    }


}
