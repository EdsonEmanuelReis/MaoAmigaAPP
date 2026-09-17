package com.example.maoamiga.launcher

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast

fun abrirTelefone(context: Context) {
    tentarAbrir(
        context = context,
        tentativas = listOf(
            Intent(Intent.ACTION_DIAL),
            Intent(Intent.ACTION_VIEW, Uri.parse("tel:"))
        ),
        mensagemErro = "Não foi possível abrir o telefone."
    )
}

fun abrirMensagens(context: Context) {
    tentarAbrir(
        context = context,
        tentativas = listOf(
            Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_APP_MESSAGING)
            },
            Intent(
                Intent.ACTION_SENDTO,
                Uri.parse("smsto:")
            ),
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("sms:")
            )
        ),
        mensagemErro = "Não foi possível abrir as mensagens."
    )
}

fun abrirCamera(context: Context) {
    tentarAbrir(
        context = context,
        tentativas = listOf(
            Intent(MediaStore.ACTION_IMAGE_CAPTURE),
            Intent(Intent.ACTION_MAIN).apply {
                addCategory("android.intent.category.APP_CAMERA")
            }
        ),
        mensagemErro = "Não foi possível abrir a câmera."
    )
}

fun abrirGaleria(context: Context) {
    tentarAbrir(
        context = context,
        tentativas = listOf(
            Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_APP_GALLERY)
            },
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("content://media/external/images/media")
            ),
            Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
            }
        ),
        mensagemErro = "Não foi possível abrir a galeria."
    )
}

fun abrirEmergencia(context: Context) {
    tentarAbrir(
        context = context,
        tentativas = listOf(
            Intent(
                Intent.ACTION_DIAL,
                Uri.parse("tel:192")
            )
        ),
        mensagemErro = "Não foi possível abrir o telefone de emergência."
    )
}

fun abrirApp(
    context: Context,
    pacote: String
) {
    val intent =
        context.packageManager.getLaunchIntentForPackage(pacote)

    if (intent != null) {
        try {
            context.startActivity(intent)
        } catch (erro: Exception) {
            Toast.makeText(
                context,
                "Não foi possível abrir este aplicativo.",
                Toast.LENGTH_LONG
            ).show()
        }
    } else {
        Toast.makeText(
            context,
            "Não foi possível abrir este aplicativo.",
            Toast.LENGTH_LONG
        ).show()
    }
}

private fun tentarAbrir(
    context: Context,
    tentativas: List<Intent>,
    mensagemErro: String
): Boolean {

    for (tentativa in tentativas) {
        tentativa.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            context.startActivity(tentativa)
            return true
        } catch (erro: Exception) {
        }
    }

    Toast.makeText(
        context,
        mensagemErro,
        Toast.LENGTH_LONG
    ).show()

    return false
}

fun pacotesDosAtalhosFixos(
    context: Context
): Set<String> {

    val gerenciador = context.packageManager

    val intents = listOf(
        Intent(Intent.ACTION_DIAL),
        Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_APP_MESSAGING)
        },
        Intent(MediaStore.ACTION_IMAGE_CAPTURE),
        Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_APP_GALLERY)
        }
    )

    return intents
        .mapNotNull { intent: Intent ->
            gerenciador
                .resolveActivity(intent, 0)
                ?.activityInfo
                ?.packageName
        }
        .toSet()
}