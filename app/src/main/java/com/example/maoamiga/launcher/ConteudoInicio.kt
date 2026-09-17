package com.example.maoamiga.launcher

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maoamiga.IconeAtalho
import com.example.maoamiga.IconeDesenhado
import com.example.maoamiga.TemaClaro

private data class CartaoFuncaoDados(
    val titulo: String,
    val icone: IconeAtalho,
    val cor: Color,
    val aoTocar: () -> Unit
)

@Composable
fun ConteudoInicio(
    tamanhoFonte: Float,
    textoReconhecido: String,
    onTelefone: () -> Unit,
    onMensagens: () -> Unit,
    onCamera: () -> Unit,
    onGaleria: () -> Unit,
    onEmergencia: () -> Unit,
    onMaisApps: () -> Unit
) {
    val cartoes = listOf(
        CartaoFuncaoDados(
            titulo = "Telefone",
            icone = IconeAtalho.TELEFONE,
            cor = Color(0xFF168A5B),
            aoTocar = onTelefone
        ),
        CartaoFuncaoDados(
            titulo = "Mensagens",
            icone = IconeAtalho.MENSAGENS,
            cor = Color(0xFF2878D7),
            aoTocar = onMensagens
        ),
        CartaoFuncaoDados(
            titulo = "Câmera",
            icone = IconeAtalho.CAMERA,
            cor = Color(0xFF6849B8),
            aoTocar = onCamera
        ),
        CartaoFuncaoDados(
            titulo = "Galeria",
            icone = IconeAtalho.GALERIA,
            cor = Color(0xFFE77B22),
            aoTocar = onGaleria
        ),
        CartaoFuncaoDados(
            titulo = "Emergência",
            icone = IconeAtalho.EMERGENCIA,
            cor = Color(0xFFD94758),
            aoTocar = onEmergencia
        ),
        CartaoFuncaoDados(
            titulo = "Mais Apps",
            icone = IconeAtalho.APPS,
            cor = Color(0xFF46697A),
            aoTocar = onMaisApps
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        if (textoReconhecido.isNotBlank()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = TemaClaro.superficie
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Você falou:",
                        color = TemaClaro.textoSecundario,
                        fontSize = (tamanhoFonte - 2f)
                            .coerceAtLeast(14f)
                            .sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = textoReconhecido,
                        color = TemaClaro.textoPrincipal,
                        fontSize = tamanhoFonte.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 4.dp,
                end = 4.dp,
                top = 4.dp,
                bottom = 24.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            items(cartoes) { cartao ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clickable {
                            cartao.aoTocar()
                        },
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = cartao.cor
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        IconeDesenhado(
                            tipo = cartao.icone,
                            cor = TemaClaro.textoSobreCabecalho,
                            contentDescription = cartao.titulo,
                            modifier = Modifier.size(52.dp)
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text(
                            text = cartao.titulo,
                            color = TemaClaro.textoSobreCabecalho,
                            fontSize = tamanhoFonte.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}