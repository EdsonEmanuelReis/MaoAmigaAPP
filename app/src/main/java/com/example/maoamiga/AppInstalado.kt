package com.example.maoamiga

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.Canvas as AndroidCanvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

data class AppInstalado(
    val nome: String,
    val pacote: String,
    val icone: ImageBitmap
)

fun listarAppsInstalados(
    context: Context
): List<AppInstalado> {

    val gerenciador: PackageManager = context.packageManager

    val intent = Intent(Intent.ACTION_MAIN, null).apply {
        addCategory(Intent.CATEGORY_LAUNCHER)
    }

    val resolvidos: List<ResolveInfo> =
        gerenciador.queryIntentActivities(intent, 0)

    return resolvidos
        .map { resolveInfo ->
            AppInstalado(
                nome = resolveInfo.loadLabel(gerenciador).toString(),
                pacote = resolveInfo.activityInfo.packageName,
                icone = resolveInfo.loadIcon(gerenciador).paraImageBitmap()
            )
        }
        .sortedBy { it.nome.lowercase() }
}

fun Drawable.paraImageBitmap(): ImageBitmap {

    if (this is BitmapDrawable && bitmap != null) {
        return bitmap.asImageBitmap()
    }

    val largura = intrinsicWidth.coerceAtLeast(1)
    val altura = intrinsicHeight.coerceAtLeast(1)

    val bitmap = Bitmap.createBitmap(
        largura,
        altura,
        Bitmap.Config.ARGB_8888
    )

    val canvas = AndroidCanvas(bitmap)

    setBounds(
        0,
        0,
        canvas.width,
        canvas.height
    )

    draw(canvas)

    return bitmap.asImageBitmap()
}