package com.example.audio

import android.media.AudioManager
import android.media.ToneGenerator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object SoundManager {
    private var toneGen: ToneGenerator? = null
    var isSoundEnabled: Boolean = false

    init {
        try {
            toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 75)
        } catch (_: Exception) {
            toneGen = null
        }
    }

    fun playClick() {
        if (!isSoundEnabled) return
        try {
            toneGen?.startTone(ToneGenerator.TONE_PROP_BEEP, 40)
        } catch (_: Exception) {}
    }

    fun playCorrect() {
        if (!isSoundEnabled) return
        CoroutineScope(Dispatchers.Default).launch {
            try {
                toneGen?.startTone(ToneGenerator.TONE_DTMF_8, 80)
                delay(90)
                toneGen?.startTone(ToneGenerator.TONE_DTMF_C, 120)
            } catch (_: Exception) {}
        }
    }

    fun playIncorrect() {
        if (!isSoundEnabled) return
        CoroutineScope(Dispatchers.Default).launch {
            try {
                toneGen?.startTone(ToneGenerator.TONE_PROP_NACK, 160)
            } catch (_: Exception) {}
        }
    }

    fun playCombo() {
        if (!isSoundEnabled) return
        CoroutineScope(Dispatchers.Default).launch {
            try {
                toneGen?.startTone(ToneGenerator.TONE_DTMF_5, 60)
                delay(70)
                toneGen?.startTone(ToneGenerator.TONE_DTMF_9, 80)
                delay(90)
                toneGen?.startTone(ToneGenerator.TONE_DTMF_D, 140)
            } catch (_: Exception) {}
        }
    }

    fun playLevelUp() {
        if (!isSoundEnabled) return
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val notes = intArrayOf(
                    ToneGenerator.TONE_DTMF_1,
                    ToneGenerator.TONE_DTMF_3,
                    ToneGenerator.TONE_DTMF_5,
                    ToneGenerator.TONE_DTMF_8,
                    ToneGenerator.TONE_DTMF_D
                )
                for (note in notes) {
                    toneGen?.startTone(note, 90)
                    delay(100)
                }
            } catch (_: Exception) {}
        }
    }

    fun playTick() {
        if (!isSoundEnabled) return
        try {
            toneGen?.startTone(ToneGenerator.TONE_PROP_PROMPT, 30)
        } catch (_: Exception) {}
    }
}
