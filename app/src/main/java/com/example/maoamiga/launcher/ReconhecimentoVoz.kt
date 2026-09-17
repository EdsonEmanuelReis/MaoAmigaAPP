package com.example.maoamiga.launcher

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher

fun iniciarReconhecimentoDeVoz(
    reconhecimentoDeVoz: ActivityResultLauncher<Intent>,
    context: Context
) {
    val intent = Intent(
        RecognizerIntent.ACTION_RECOGNIZE_SPEECH
    ).apply {
        putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )

        putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            "pt-BR"
        )

        putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            "Fale o nome do aplicativo"
        )
    }

    try {
        reconhecimentoDeVoz.launch(intent)
    } catch (erro: Exception) {
        Toast.makeText(
            context,
            "O reconhecimento de voz não está disponível neste aparelho.",
            Toast.LENGTH_LONG
        ).show()
    }
}