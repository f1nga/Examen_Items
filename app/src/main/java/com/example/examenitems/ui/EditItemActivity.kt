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

class EditItemActivity : AppCompatActivity() {

    private lateinit var addNoteBackground: RelativeLayout
    private lateinit var addNoteWindowBg: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_items)

        addNoteBackground = findViewById(R.id.add_note_background)
        addNoteWindowBg = findViewById(R.id.add_note_window_bg)

        setActivityStyle()

        val itemName = intent.getStringExtra("item_name")
        val itemPrice = intent.getStringExtra("item_price")

        val addItemName = findViewById<TextView>(R.id.inputName)
        addItemName.text = itemName ?: ""

        val addItemPrice = findViewById<TextView>(R.id.inputPrice)
        addItemPrice.text = itemPrice ?: ""

        val updateItemButton = findViewById<Button>(R.id.update_item_button)
        updateItemButton.setOnClickListener {
            // Return the note back to the Not  esActivity
            val data = Intent()
            data.putExtra("item_name", addItemName.text.toString())
            data.putExtra("item_price", addItemPrice.text.toString())
            setResult(Activity.RESULT_OK, data)
            // Close current window
            onBackPressed()
        }
    }

    @SuppressLint("RestrictedApi")
    private fun setActivityStyle() {
        // Make the background full screen, over status bar
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        this.window.statusBarColor = Color.TRANSPARENT
        val winParams = this.window.attributes
        winParams.flags =
            winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        this.window.attributes = winParams

        // Fade animation for the background of Popup Window
        val alpha = 100 //between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            addNoteBackground.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()

        addNoteWindowBg.alpha = 0f
        addNoteWindowBg.animate().alpha(1f).setDuration(500)
            .setInterpolator(DecelerateInterpolator()).start()

        // Close window when you tap on the dim background
        addNoteBackground.setOnClickListener { onBackPressed() }
        addNoteWindowBg.setOnClickListener { /* Prevent activity from closing when you tap on the popup's window background */ }
    }


    @SuppressLint("RestrictedApi")
    override fun onBackPressed() {
        // Fade animation for the background of Popup Window when you press the back button
        val alpha = 100 // between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            addNoteBackground.setBackgroundColor(
                animator.animatedValue as Int
            )
        }

        // Fade animation for the Popup Window when you press the back button
        addNoteWindowBg.animate().alpha(0f).setDuration(500).setInterpolator(
            DecelerateInterpolator()
        ).start()

        // After animation finish, close the Activity
        colorAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                finish()
                overridePendingTransition(0, 0)
            }
        })
        colorAnimation.start()
    }
}