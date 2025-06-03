package com.example.cupcakemaker3

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class WelcomeActivity : AppCompatActivity() {

    companion object {
        var mediaPlayer: MediaPlayer? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // 🎵 Start background music once after splash
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.cupcake_theme)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
        }

        val startButton = findViewById<Button>(R.id.startButton)
        val instructionsButton = findViewById<Button>(R.id.instructionsButton)

        startButton.setOnClickListener {
            startActivity(Intent(this, MainPageActivity::class.java))
        }

        instructionsButton.setOnClickListener {
            showInstructionsPopup()
        }
    }

    private fun showInstructionsPopup() {
        val dialog = Dialog(this)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val frameLayout = FrameLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
            )
            background = ContextCompat.getDrawable(this@WelcomeActivity, R.drawable.popbg)
            setPadding(60, 140, 100, 80)
        }

        val linearLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val welcomeText = "👩‍🍳 Welcome to Cupcake Maker!\n\n"
        val instructionsText =
            "1. Choose a flavor.\n2. Select a topping.\n3. Tap 'Make Cupcake!' to see your creation.\n\n🧁 Have fun baking!"

        val spannable = SpannableString(welcomeText + instructionsText).apply {
            setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                welcomeText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        val textView = TextView(this).apply {
            text = spannable
            textSize = 14f
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER
            setLineSpacing(1.3f, 1.3f)
            setPadding(90, 220, 90, 220)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 40
            }
        }

        val closeButton = Button(this).apply {
            text = "CLOSE"
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.parseColor("#FF8A65"))
            setPadding(40, 5, 40, 5)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
            setOnClickListener { dialog.dismiss() }
        }

        linearLayout.addView(textView)
        linearLayout.addView(closeButton)
        frameLayout.addView(linearLayout)

        dialog.setContentView(frameLayout)
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        // ❌ Do not stop music here so it continues in MainPage
    }
}
