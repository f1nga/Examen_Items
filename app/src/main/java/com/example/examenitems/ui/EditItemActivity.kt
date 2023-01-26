package com.example.examenitems.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import com.example.examenitems.R
import java.util.*

@SuppressLint("RestrictedApi")
class EditItemActivity : AppCompatActivity() {

    private lateinit var addItemBackground: RelativeLayout
    private lateinit var addItemWindowBg: LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_items)

        addItemBackground = findViewById(R.id.add_item_background)
        addItemWindowBg = findViewById(R.id.add_item_window_bg)

        setActivityStyle()

        val itemName = intent.getStringExtra("item_name")
        val itemPrice = intent.getStringExtra("item_price")

        val addItemName = findViewById<TextView>(R.id.inputName)
        addItemName.text = itemName ?: ""

        val addItemPrice = findViewById<TextView>(R.id.inputPrice)
        addItemPrice.text = itemPrice ?: ""

        val updateItemButton = findViewById<Button>(R.id.update_item_button)
        updateItemButton.setOnClickListener {
            val data = Intent()
            data.putExtra("item_name", addItemName.text.toString())
            data.putExtra("item_price", addItemPrice.text.toString())
            setResult(Activity.RESULT_OK, data)
            onBackPressed()
        }
    }

    private fun setActivityStyle() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        this.window.statusBarColor = Color.TRANSPARENT
        val winParams = this.window.attributes
        winParams.flags =
            winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        this.window.attributes = winParams

        val alpha = 100
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = 500
        colorAnimation.addUpdateListener { animator ->
            addItemBackground.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()

        addItemWindowBg.alpha = 0f
        addItemWindowBg.animate().alpha(1f).setDuration(500)
            .setInterpolator(DecelerateInterpolator()).start()

        addItemBackground.setOnClickListener { onBackPressed() }
        addItemWindowBg.setOnClickListener { }
    }

    override fun onBackPressed() {
        val alpha = 100
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)
        colorAnimation.duration = 500
        colorAnimation.addUpdateListener { animator ->
            addItemBackground.setBackgroundColor(
                animator.animatedValue as Int
            )
        }

        addItemWindowBg.animate().alpha(0f).setDuration(500).setInterpolator(
            DecelerateInterpolator()
        ).start()

        colorAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                finish()
                overridePendingTransition(0, 0)
            }
        })
        colorAnimation.start()
    }
}