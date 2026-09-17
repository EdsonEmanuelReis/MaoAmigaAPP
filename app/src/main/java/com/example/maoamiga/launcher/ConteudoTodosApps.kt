package com.example.maoamiga.launcher

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maoamiga.AppInstalado
import com.example.maoamiga.CoresDosCards
import com.example.maoamiga.TemaClaro

@Composable
fun ConteudoTodosApps(
    apps: List<AppInstalado>,
    tamanhoFonte: Float,
    onAbrirApp: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 4.dp,
                    bottom = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Outros aplicativos",
                color = TemaClaro.textoPrincipal,
                fontSize = tamanhoFonte.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "${apps.size}",
                color = TemaClaro.textoSecundario,
                fontSize = (tamanhoFonte - 2f)
                    .coerceAtLeast(14f)
                    .sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (apps.isEmpty()) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Nenhum aplicativo encontrado.",
                    color = TemaClaro.textoSecundario,
                    fontSize = tamanhoFonte.sp,
                    textAlign = TextAlign.Center
                )
            }

        } else {

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

                itemsIndexed(apps) { indice, app ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(196.dp),
                        shape = RoundedCornerShape(26.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = CoresDosCards[
                                indice % CoresDosCards.size
                            ]
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        ),
                        onClick = {
                            onAbrirApp(app.pacote)
                        }
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Image(
                                bitmap = app.icone,
                                contentDescription = app.nome,
                                modifier = Modifier.size(64.dp)
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            Text(
                                text = app.nome,
                                color = TemaClaro.textoSobreCabecalho,
                                fontSize = tamanhoFonte.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}