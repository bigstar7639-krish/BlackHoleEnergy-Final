
package com.krishna.blackholeenergy

import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var chatLayout: LinearLayout
    private lateinit var scrollView: ScrollView
    private lateinit var input: EditText

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    private fun rounded(color: Int, radius: Int): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(radius).toFloat()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.rgb(8, 8, 20)
        window.navigationBarColor = Color.rgb(8, 8, 20)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.rgb(8, 8, 20))
            setPadding(dp(16), dp(12), dp(16), dp(12))
        }

        val header = TextView(this).apply {
            text = "🕳️ BLACK HOLE ENERGY\n        OMNI AI  ⚡"
            textSize = 23f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            setPadding(0, dp(16), 0, dp(20))
        }

        val subtitle = TextView(this).apply {
            text = "Your Universe. Your AI."
            textSize = 13f
            gravity = Gravity.CENTER
            setTextColor(Color.rgb(0, 220, 200))
        }

        val top = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(header)
            addView(subtitle)
            setPadding(0, 0, 0, dp(18))
        }

        scrollView = ScrollView(this)

        chatLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(4), dp(8), dp(4), dp(12))
        }

        scrollView.addView(chatLayout)

        addBubble(
            "Vanakkam Krishna! 👋\n\nI'm Black Hole Energy AI. " +
            "Your journey starts here. Ask me anything!",
            false
        )

        val bottom = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(0, dp(12), 0, dp(4))
        }

        input = EditText(this).apply {
            hint = "Ask Black Hole Energy..."
            setHintTextColor(Color.GRAY)
            setTextColor(Color.WHITE)
            textSize = 15f
            singleLine = true
            imeOptions = EditorInfo.IME_ACTION_SEND
            setPadding(dp(14), dp(12), dp(14), dp(12))
            background = rounded(Color.rgb(30, 30, 48), 24)
        }

        val send = Button(this).apply {
            text = "SEND"
            textSize = 12f
            setTextColor(Color.BLACK)
            background = rounded(Color.rgb(0, 220, 190), 22)
            setOnClickListener { sendMessage() }
        }

        bottom.addView(
            input,
            LinearLayout.LayoutParams(0, dp(52), 1f).apply {
                marginEnd = dp(8)
            }
        )

        bottom.addView(
            send,
            LinearLayout.LayoutParams(dp(82), dp(52))
        )

        input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendMessage()
                true
            } else false
        }

        root.addView(top)
        root.addView(
            scrollView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f
            )
        )
        root.addView(bottom)

        setContentView(root)
    }

    private fun addBubble(message: String, isUser: Boolean) {
        val bubble = TextView(this).apply {
            text = message
            textSize = 15f
            setTextColor(Color.WHITE)
            setPadding(dp(14), dp(12), dp(14), dp(12))
            background = rounded(
                if (isUser) Color.rgb(20, 110, 105)
                else Color.rgb(35, 35, 58),
                18
            )
        }

        val wrapper = LinearLayout(this).apply {
            gravity = if (isUser) Gravity.END else Gravity.START
            setPadding(0, dp(5), 0, dp(5))
            addView(
                bubble,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    width = dp(280)
                }
            )
        }

        chatLayout.addView(wrapper)
        scrollView.post { scrollView.fullScroll(ScrollView.FOCUS_DOWN) }
    }

    private fun sendMessage() {
        val message = input.text.toString().trim()
        if (message.isEmpty()) return

        addBubble(message, true)
        input.text.clear()

        addBubble(
            "Chat interface ready! ⚡ Real AI connection " +
            "will be added in the next step.",
            false
        )
    }
}
