package com.example.maoamiga.acessibilidade

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maoamiga.TemaClaro

@Composable
fun TelaFonteAcessivel(
    context: Context
) {
    val preferencias = remember {
        context.getSharedPreferences(
            NOME_PREFERENCIAS,
            Context.MODE_PRIVATE
        )
    }

    var tamanhoFonte by remember {
        mutableStateOf(
            preferencias.getFloat(
                CHAVE_TAMANHO_FONTE,
                FONTE_NORMAL
            )
        )
    }

    fun salvarTamanho(
        novoTamanho: Float
    ) {
        tamanhoFonte = novoTamanho

        preferencias
            .edit()
            .putFloat(
                CHAVE_TAMANHO_FONTE,
                novoTamanho
            )
            .apply()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Configurações",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TemaClaro.textoPrincipal,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Tamanho da fonte",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TemaClaro.textoPrincipal,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = TemaClaro.superficie
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Exemplo de texto",
                    fontSize = tamanhoFonte.sp,
                    fontWeight = FontWeight.Bold,
                    color = TemaClaro.textoPrincipal,
                    textAlign = TextAlign.Center
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Assim ficará o tamanho da fonte no aplicativo.",
                    fontSize = (tamanhoFonte - 2f)
                        .coerceAtLeast(14f)
                        .sp,
                    color = TemaClaro.textoSecundario,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        BotaoTamanhoFonte(
            texto = "Normal",
            tamanho = FONTE_NORMAL,
            selecionado = tamanhoFonte == FONTE_NORMAL,
            onClick = {
                salvarTamanho(FONTE_NORMAL)
            }
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        BotaoTamanhoFonte(
            texto = "Grande",
            tamanho = FONTE_GRANDE,
            selecionado = tamanhoFonte == FONTE_GRANDE,
            onClick = {
                salvarTamanho(FONTE_GRANDE)
            }
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        BotaoTamanhoFonte(
            texto = "Extra grande",
            tamanho = FONTE_EXTRA_GRANDE,
            selecionado = tamanhoFonte == FONTE_EXTRA_GRANDE,
            onClick = {
                salvarTamanho(FONTE_EXTRA_GRANDE)
            }
        )
    }
}

@Composable
private fun BotaoTamanhoFonte(
    texto: String,
    tamanho: Float,
    selecionado: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor =
                if (selecionado) {
                    TemaClaro.destaque
                } else {
                    TemaClaro.destaqueSecundario
                }
        )
    ) {
        Text(
            text = texto,
            fontSize = tamanho.sp,
            fontWeight = FontWeight.Bold,
            color = TemaClaro.textoSobreCabecalho
        )
    }
}