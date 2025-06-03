package com.example.cupcakemaker3

import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainPageActivity : AppCompatActivity() {

    private var selectedFlavor: String? = null
    private var selectedTopping: String? = null

    private lateinit var vanillaBtn: ImageButton
    private lateinit var chocolateBtn: ImageButton
    private lateinit var strawberryBtn: ImageButton

    private lateinit var sprinkleRb: RadioButton
    private lateinit var cherryRb: RadioButton
    private lateinit var chocoChipRb: RadioButton

    private lateinit var toppingsGroup: RadioGroup
    private lateinit var finalCupcakeImg: ImageView

    private lateinit var makeCupcakeBtn: Button
    private lateinit var goBackBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        // 🎵 Continue music from WelcomeActivity if not playing
        if (WelcomeActivity.mediaPlayer?.isPlaying == false) {
            WelcomeActivity.mediaPlayer?.start()
        }

        vanillaBtn = findViewById(R.id.vanillaBtn)
        chocolateBtn = findViewById(R.id.chocolateBtn)
        strawberryBtn = findViewById(R.id.strawberryBtn)

        sprinkleRb = findViewById(R.id.sprinkle)
        cherryRb = findViewById(R.id.cherry)
        chocoChipRb = findViewById(R.id.chocoChip)

        toppingsGroup = findViewById(R.id.toppingsRadioGroup)
        finalCupcakeImg = findViewById(R.id.finalCupcakeImg)

        makeCupcakeBtn = findViewById(R.id.makeCupcakeBtn)
        goBackBtn = findViewById(R.id.goBackBtn)

        // Initially toppings disabled
        setToppingsEnabled(false)

        // Flavor buttons click listeners
        vanillaBtn.setOnClickListener { selectFlavor("vanilla") }
        chocolateBtn.setOnClickListener { selectFlavor("chocolate") }
        strawberryBtn.setOnClickListener { selectFlavor("strawberry") }

        // Toppings radio group change listener
        toppingsGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedTopping = when (checkedId) {
                R.id.sprinkle -> "sprinkle"
                R.id.cherry -> "cherry"
                R.id.chocoChip -> "chocolate_chip"
                else -> null
            }
        }

        // Make cupcake button click
        makeCupcakeBtn.setOnClickListener {
            if (selectedFlavor == null || selectedTopping == null) {
                Toast.makeText(this, "Select both flavor and topping!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            updateCupcakeImage()
        }

        // Go back button click to finish this activity
        goBackBtn.setOnClickListener {
            finish()
        }
    }

    private fun selectFlavor(flavor: String) {
        selectedFlavor = flavor
        Toast.makeText(this, "Selected flavor: $flavor", Toast.LENGTH_SHORT).show()

        highlightSelectedFlavor(flavor)

        setToppingsEnabled(true)
        toppingsGroup.clearCheck()
        selectedTopping = null
        finalCupcakeImg.visibility = ImageView.GONE
    }

    private fun highlightSelectedFlavor(flavor: String) {
        vanillaBtn.setBackgroundColor(Color.TRANSPARENT)
        chocolateBtn.setBackgroundColor(Color.TRANSPARENT)
        strawberryBtn.setBackgroundColor(Color.TRANSPARENT)

        val highlightColor = Color.parseColor("#FFCC80")
        when (flavor) {
            "vanilla" -> vanillaBtn.setBackgroundColor(highlightColor)
            "chocolate" -> chocolateBtn.setBackgroundColor(highlightColor)
            "strawberry" -> strawberryBtn.setBackgroundColor(highlightColor)
        }
    }

    private fun setToppingsEnabled(enabled: Boolean) {
        val enabledColor = Color.BLACK
        val disabledColor = Color.GRAY

        sprinkleRb.isEnabled = enabled
        cherryRb.isEnabled = enabled
        chocoChipRb.isEnabled = enabled

        sprinkleRb.setTextColor(if (enabled) enabledColor else disabledColor)
        cherryRb.setTextColor(if (enabled) enabledColor else disabledColor)
        chocoChipRb.setTextColor(if (enabled) enabledColor else disabledColor)
    }

    private fun updateCupcakeImage() {
        val imageName = "${selectedFlavor}_${selectedTopping}"
        val resId = resources.getIdentifier(imageName, "drawable", packageName)

        if (resId != 0) {
            finalCupcakeImg.setImageResource(resId)
            finalCupcakeImg.visibility = ImageView.VISIBLE
        } else {
            finalCupcakeImg.visibility = ImageView.GONE
            Toast.makeText(this, "Image not found for $imageName", Toast.LENGTH_SHORT).show()
        }
    }

    // Removed mediaPlayer.release() to keep music running
    override fun onDestroy() {
        super.onDestroy()
    }
}
