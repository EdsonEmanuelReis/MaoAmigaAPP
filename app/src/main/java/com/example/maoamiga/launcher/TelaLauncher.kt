package com.example.maoamiga.launcher

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.maoamiga.IconeAtalho
import com.example.maoamiga.IconeDesenhado
import com.example.maoamiga.MainActivity
import com.example.maoamiga.TemaClaro
import com.example.maoamiga.acessibilidade.CHAVE_TAMANHO_FONTE
import com.example.maoamiga.acessibilidade.FONTE_NORMAL
import com.example.maoamiga.acessibilidade.NOME_PREFERENCIAS
import com.example.maoamiga.listarAppsInstalados

private enum class TelaAtual {
    INICIO,
    TODOS_APPS
}

@Composable
fun TelaLauncher() {

    val context = LocalContext.current

    var textoReconhecido by remember {
        mutableStateOf("")
    }

    val reconhecimentoDeVoz =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { resultado ->

            val dados = resultado.data

            val resultados =
                dados?.getStringArrayListExtra(
                    "android.speech.extra.RESULTS"
                )

            val texto = resultados?.firstOrNull()

            if (!texto.isNullOrBlank()) {

                textoReconhecido = texto

                val funcao =
                    identificarFuncaoPorVoz(texto)

                if (funcao != null) {

                    Toast.makeText(
                        context,
                        "Abrindo ${funcao.nome}...",
                        Toast.LENGTH_SHORT
                    ).show()

                    funcao.acao(context)

                } else {

                    Toast.makeText(
                        context,
                        "Não entendi o comando.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    val pedirPermissaoMicrofone =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { concedida ->

            if (concedida) {

                iniciarReconhecimentoDeVoz(
                    reconhecimentoDeVoz = reconhecimentoDeVoz,
                    context = context
                )

            } else {

                Toast.makeText(
                    context,
                    "Permissão para usar o microfone foi negada.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    fun ouvirComMicrofone() {

        val permissao =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            )

        if (permissao == PackageManager.PERMISSION_GRANTED) {

            iniciarReconhecimentoDeVoz(
                reconhecimentoDeVoz = reconhecimentoDeVoz,
                context = context
            )

        } else {

            pedirPermissaoMicrofone.launch(
                Manifest.permission.RECORD_AUDIO
            )
        }
    }

    val preferencias =
        remember {
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

    val listener =
        remember {

            SharedPreferences.OnSharedPreferenceChangeListener { _, chave ->

                if (chave == CHAVE_TAMANHO_FONTE) {

                    tamanhoFonte =
                        preferencias.getFloat(
                            CHAVE_TAMANHO_FONTE,
                            FONTE_NORMAL
                        )
                }
            }
        }

    DisposableEffect(Unit) {

        preferencias.registerOnSharedPreferenceChangeListener(
            listener
        )

        onDispose {

            preferencias.unregisterOnSharedPreferenceChangeListener(
                listener
            )
        }
    }

    val apps = remember {

        val pacotesComAtalhoFixo =
            pacotesDosAtalhosFixos(context)

        listarAppsInstalados(context)
            .filter {

                it.pacote != context.packageName &&
                        it.pacote !in pacotesComAtalhoFixo
            }
    }

    var telaAtual by remember {
        mutableStateOf(TelaAtual.INICIO)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TemaClaro.fundo)
    ) {

        /*
         * =====================================================
         * CONTEÚDO PRINCIPAL
         * =====================================================
         */

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 95.dp)
        ) {

            if (telaAtual == TelaAtual.INICIO) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "🤝 Mão Amiga",
                            color = TemaClaro.textoPrincipal,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Acesso fácil aos seus aplicativos",
                            color = TemaClaro.textoSecundario,
                            fontSize = 14.sp
                        )
                    }

                    /*
                     * Configurações
                     *
                     * O antigo botão do microfone foi retirado
                     * daqui para deixar a função de voz muito
                     * mais evidente na parte inferior.
                     */

                    IconButton(
                        onClick = {

                            context.startActivity(
                                Intent(
                                    context,
                                    MainActivity::class.java
                                )
                            )
                        }
                    ) {

                        IconeDesenhado(
                            tipo = IconeAtalho.CONFIGURACOES,
                            cor = TemaClaro.destaque,
                            contentDescription = "Configurações",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

            } else {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            telaAtual = TelaAtual.INICIO
                        }
                    ) {

                        IconeDesenhado(
                            tipo = IconeAtalho.VOLTAR,
                            cor = TemaClaro.destaque,
                            contentDescription = "Voltar",
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(
                        modifier = Modifier.size(8.dp)
                    )

                    Text(
                        text = "Mais Apps",
                        color = TemaClaro.textoPrincipal,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            /*
             * =================================================
             * TELAS
             * =================================================
             */

            when (telaAtual) {

                TelaAtual.INICIO -> {

                    ConteudoInicio(
                        tamanhoFonte = tamanhoFonte,
                        textoReconhecido = textoReconhecido,

                        onTelefone = {
                            abrirTelefone(context)
                        },

                        onMensagens = {
                            abrirMensagens(context)
                        },

                        onCamera = {
                            abrirCamera(context)
                        },

                        onGaleria = {
                            abrirGaleria(context)
                        },

                        onEmergencia = {
                            abrirEmergencia(context)
                        },

                        onMaisApps = {
                            telaAtual = TelaAtual.TODOS_APPS
                        }
                    )
                }

                TelaAtual.TODOS_APPS -> {

                    ConteudoTodosApps(
                        apps = apps,
                        tamanhoFonte = tamanhoFonte,

                        onAbrirApp = { pacote ->

                            abrirApp(
                                context,
                                pacote
                            )
                        }
                    )
                }
            }
        }

        /*
         * =====================================================
         * BOTÃO PRINCIPAL DE VOZ
         * =====================================================
         *
         * Fica sempre visível na tela inicial.
         */

        if (telaAtual == TelaAtual.INICIO) {

            Button(
                onClick = {
                    ouvirComMicrofone()
                },

                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 16.dp
                    )
                    .size(
                        width = 0.dp,
                        height = 64.dp
                    ),

                shape = RoundedCornerShape(20.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "🎙️",
                        fontSize = 30.sp
                    )

                    Spacer(
                        modifier = Modifier.size(12.dp)
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "FALAR",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Toque para falar",
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}