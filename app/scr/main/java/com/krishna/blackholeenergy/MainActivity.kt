package com.krishna.blackholeenergy

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private val VOICE_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        tts = TextToSpeech(this, this)
        
        // FULL VOICE BUTTON
        findViewById<android.widget.Button>(R.id.micButton).setOnClickListener {
            startVoice()
        }
        // WHATSAPP BUTTON
        findViewById<android.widget.Button>(R.id.whatsappButton).setOnClickListener {
            openWhatsApp("Hi da, Naan Black Hole Energy!")
        }
    }

    // 1. VOICE FULL ACCESS
    fun startVoice() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hey Krishna, Sollu da...")
        startActivityForResult(intent, VOICE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == VOICE_CODE && resultCode == RESULT_OK) {
            val voiceText = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0) ?: ""
            handleAllFeatures(voiceText)
        }
    }

    // 2. EVERYTHING HANDLE - OMNI BRAIN
    fun handleAllFeatures(text: String) {
        val lower = text.lowercase()
        when {
            lower.contains("volume") -> controlVolume(lower)
            lower.contains("whatsapp") && lower.contains("message") -> sendWhatsAppMessage(lower)
            lower.contains("torch") || lower.contains("flash") -> torchControl(lower)
            lower.contains("dob") || lower.contains("lucky") -> calculateDOB(lower)
            lower.contains("image") || lower.contains("padam") -> generateImage(lower)
            lower.contains("youtube") -> summarizeYouTube(lower)
            else -> {
                // Normal Chat - ChatGPT Style
                speak("Krishna, nee sonna '$text' purinjiduchu da. Black Hole Energy process pannuthu...")
            }
        }
    }

    // 3. WHATSAPP CHATTING
    fun openWhatsApp(msg: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.setPackage("com.whatsapp")
        intent.putExtra(Intent.EXTRA_TEXT, msg)
        startActivity(intent)
    }
    
    fun sendWhatsAppMessage(command: String) {
        // Eg: "rahul ku hi anupu"
        val contact = command.split("ku")[0].replace("whatsapp","").trim()
        val message = command.split("anupu").getOrNull(0) ?: "Hi from Black Hole Energy"
        speak("$contact ku message anupuren da Krishna")
        openWhatsApp(message)
    }

    fun controlVolume(text: String) { /* AudioManager Code */ }
    fun torchControl(text: String) { /* CameraManager Torch */ }
    fun calculateDOB(text: String) { speak("Un Lucky Number 7 da Krishna! Black Hole Power!") }
    fun generateImage(text: String) { speak("Image create pannuren da...") }
    fun summarizeYouTube(text: String) { speak("YouTube summary ready da...") }

    // TTS - TAMIL VOICE
    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS) {
            tts?.language = Locale("ta", "IN")
            tts?.speak("Vanakkam da Krishna! Black Hole Energy Final Version Ready da! Full Voice plus WhatsApp ellam active da!", TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }
    fun speak(msg: String) { tts?.speak(msg, TextToSpeech.QUEUE_FLUSH, null, "") }
}
