package com.example.maoamiga

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

enum class IconeAtalho {
    TELEFONE,
    MENSAGENS,
    CAMERA,
    GALERIA,
    EMERGENCIA,
    APPS,
    CONFIGURACOES,
    VOLTAR
}

@Composable
fun IconeDesenhado(
    tipo: IconeAtalho,
    cor: Color,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .semantics { this.contentDescription = contentDescription ?: "" }
    ) {
        val espessura = (size.minDimension * 0.10f).coerceAtLeast(2f)
        val centro = Offset(size.width / 2f, size.height / 2f)
        val raio = size.minDimension * 0.34f

        when (tipo) {
            IconeAtalho.TELEFONE -> {
                rotate(-45f, centro) {
                    drawRoundRect(
                        color = cor,
                        topLeft = Offset(size.width * 0.36f, size.height * 0.12f),
                        size = Size(size.width * 0.28f, size.height * 0.76f),
                        cornerRadius = CornerRadius(size.width * 0.14f),
                        style = Stroke(espessura)
                    )
                }
            }

            IconeAtalho.MENSAGENS -> {
                drawRoundRect(
                    color = cor,
                    topLeft = Offset(size.width * 0.15f, size.height * 0.18f),
                    size = Size(size.width * 0.70f, size.height * 0.56f),
                    cornerRadius = CornerRadius(size.width * 0.10f),
                    style = Stroke(espessura)
                )

                val caminho = Path().apply {
                    moveTo(size.width * 0.30f, size.height * 0.74f)
                    lineTo(size.width * 0.24f, size.height * 0.90f)
                    lineTo(size.width * 0.44f, size.height * 0.74f)
                }

                drawPath(
                    caminho,
                    color = cor,
                    style = Stroke(espessura)
                )
            }

            IconeAtalho.CAMERA -> {
                drawRoundRect(
                    color = cor,
                    topLeft = Offset(size.width * 0.12f, size.height * 0.28f),
                    size = Size(size.width * 0.76f, size.height * 0.50f),
                    cornerRadius = CornerRadius(size.width * 0.08f),
                    style = Stroke(espessura)
                )

                drawRoundRect(
                    color = cor,
                    topLeft = Offset(size.width * 0.30f, size.height * 0.16f),
                    size = Size(size.width * 0.25f, size.height * 0.16f),
                    cornerRadius = CornerRadius(size.width * 0.04f),
                    style = Stroke(espessura)
                )

                drawCircle(
                    color = cor,
                    radius = raio * 0.58f,
                    center = centro,
                    style = Stroke(espessura)
                )
            }

            IconeAtalho.GALERIA -> {
                drawRoundRect(
                    color = cor,
                    topLeft = Offset(size.width * 0.12f, size.height * 0.14f),
                    size = Size(size.width * 0.76f, size.height * 0.72f),
                    cornerRadius = CornerRadius(size.width * 0.06f),
                    style = Stroke(espessura)
                )

                drawCircle(
                    color = cor,
                    radius = size.minDimension * 0.07f,
                    center = Offset(
                        size.width * 0.34f,
                        size.height * 0.34f
                    )
                )

                val montanhas = Path().apply {
                    moveTo(size.width * 0.18f, size.height * 0.74f)
                    lineTo(size.width * 0.42f, size.height * 0.48f)
                    lineTo(size.width * 0.55f, size.height * 0.62f)
                    lineTo(size.width * 0.68f, size.height * 0.45f)
                    lineTo(size.width * 0.82f, size.height * 0.74f)
                }

                drawPath(
                    montanhas,
                    color = cor,
                    style = Stroke(espessura)
                )
            }

            IconeAtalho.EMERGENCIA -> {
                drawCircle(
                    color = cor,
                    radius = size.minDimension * 0.35f,
                    center = centro,
                    style = Stroke(espessura)
                )

                drawRect(
                    color = cor,
                    topLeft = Offset(
                        size.width * 0.42f,
                        size.height * 0.25f
                    ),
                    size = Size(
                        size.width * 0.16f,
                        size.height * 0.50f
                    )
                )

                drawRect(
                    color = cor,
                    topLeft = Offset(
                        size.width * 0.25f,
                        size.height * 0.42f
                    ),
                    size = Size(
                        size.width * 0.50f,
                        size.height * 0.16f
                    )
                )
            }

            IconeAtalho.APPS -> {
                val quadrado = size.minDimension * 0.15f
                val gap = size.minDimension * 0.08f
                val inicioX =
                    (size.width - quadrado * 3f - gap * 2f) / 2f
                val inicioY =
                    (size.height - quadrado * 3f - gap * 2f) / 2f

                repeat(3) { linha ->
                    repeat(3) { coluna ->
                        drawRoundRect(
                            color = cor,
                            topLeft = Offset(
                                inicioX + coluna * (quadrado + gap),
                                inicioY + linha * (quadrado + gap)
                            ),
                            size = Size(quadrado, quadrado),
                            cornerRadius = CornerRadius(quadrado * 0.25f)
                        )
                    }
                }
            }

            IconeAtalho.CONFIGURACOES -> {
                drawCircle(
                    color = cor,
                    radius = raio,
                    center = centro,
                    style = Stroke(espessura)
                )

                drawCircle(
                    color = cor,
                    radius = raio * 0.30f,
                    center = centro,
                    style = Stroke(espessura)
                )

                repeat(8) { indice ->
                    rotate(indice * 45f, centro) {
                        drawRoundRect(
                            color = cor,
                            topLeft = Offset(
                                size.width * 0.46f,
                                size.height * 0.04f
                            ),
                            size = Size(
                                size.width * 0.08f,
                                size.height * 0.22f
                            ),
                            cornerRadius = CornerRadius(
                                size.width * 0.03f
                            )
                        )
                    }
                }
            }

            IconeAtalho.VOLTAR -> {
                val caminho = Path().apply {
                    moveTo(
                        size.width * 0.78f,
                        size.height * 0.50f
                    )

                    lineTo(
                        size.width * 0.28f,
                        size.height * 0.50f
                    )

                    moveTo(
                        size.width * 0.28f,
                        size.height * 0.50f
                    )

                    lineTo(
                        size.width * 0.52f,
                        size.height * 0.25f
                    )

                    moveTo(
                        size.width * 0.28f,
                        size.height * 0.50f
                    )

                    lineTo(
                        size.width * 0.52f,
                        size.height * 0.75f
                    )
                }

                drawPath(
                    caminho,
                    color = cor,
                    style = Stroke(
                        espessura,
                        cap = StrokeCap.Round
                    )
                )
            }
        }
    }
}