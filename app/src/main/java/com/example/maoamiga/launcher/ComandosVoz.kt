package com.example.maoamiga.launcher

import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.text.Normalizer

data class FuncaoPorVoz(
    val nome: String,
    val palavrasChave: List<String>,
    val acao: (Context) -> Unit
)

fun normalizarTextoVoz(texto: String): String {
    return Normalizer
        .normalize(
            texto.lowercase(),
            Normalizer.Form.NFD
        )
        .replace(
            "\\p{InCombiningDiacriticalMarks}+".toRegex(),
            ""
        )
        .replace(
            "[^a-z0-9 ]".toRegex(),
            " "
        )
        .replace(
            "\\s+".toRegex(),
            " "
        )
        .trim()
}

/*
 * Remove palavras que servem apenas como comando de ação.
 *
 * Exemplos:
 * "abrir whatsapp" -> "whatsapp"
 * "abra o instagram" -> "instagram"
 * "quero abrir a camera" -> "camera"
 */
private fun removerPalavrasDeComando(texto: String): String {

    val palavrasIgnoradas = setOf(
        "abrir",
        "abra",
        "abre",
        "abrindo",
        "entrar",
        "entra",
        "entrando",
        "ir",
        "vai",
        "quero",
        "acessar",
        "acessa",
        "mostrar",
        "mostra",
        "abrindo",
        "o",
        "a",
        "os",
        "as",
        "no",
        "na",
        "para",
        "pro",
        "pra"
    )

    return texto
        .split(" ")
        .filter { palavra ->
            palavra !in palavrasIgnoradas
        }
        .joinToString(" ")
        .trim()
}

/*
 * ============================================================
 * IDENTIFICAÇÃO PRINCIPAL
 * ============================================================
 */
fun identificarFuncaoPorVoz(
    texto: String
): FuncaoPorVoz? {

    val fala = normalizarTextoVoz(texto)

    if (fala.isBlank()) {
        return null
    }

    /*
     * Primeiro verificamos os comandos fixos.
     *
     * Eles vêm antes dos aplicativos para evitar conflitos.
     */
    val funcoesFixas = criarFuncoesFixas()

    /*
     * Segundo: aplicativos.
     */
    val aplicativos = criarFuncoesAplicativos()

    val todasFuncoes =
        funcoesFixas + aplicativos

    /*
     * ========================================================
     * 1. CORRESPONDÊNCIA EXATA
     * ========================================================
     *
     * "telefone"
     * "camera"
     * "whatsapp"
     * "instagram"
     */
    val exata = todasFuncoes.firstOrNull { funcao ->

        funcao.palavrasChave.any { palavra ->

            normalizarTextoVoz(palavra) == fala
        }
    }

    if (exata != null) {
        return exata
    }

    /*
     * ========================================================
     * 2. REMOVE PALAVRAS DE AÇÃO
     * ========================================================
     *
     * "abrir whatsapp"
     * vira:
     * "whatsapp"
     */
    val falaLimpa =
        removerPalavrasDeComando(fala)

    if (falaLimpa.isBlank()) {
        return null
    }

    /*
     * ========================================================
     * 3. NOVA CORRESPONDÊNCIA EXATA
     * ========================================================
     *
     * Isso é importante porque agora:
     *
     * "abrir whatsapp"
     * "abra o whatsapp"
     * "quero entrar no whatsapp"
     *
     * todos chegam em:
     *
     * "whatsapp"
     */
    val exataDepoisDaLimpeza =
        todasFuncoes.firstOrNull { funcao ->

            funcao.palavrasChave.any { palavra ->

                normalizarTextoVoz(palavra) == falaLimpa
            }
        }

    if (exataDepoisDaLimpeza != null) {
        return exataDepoisDaLimpeza
    }

    /*
     * ========================================================
     * 4. PALAVRA-CHAVE DENTRO DA FRASE
     * ========================================================
     *
     * Aqui usamos somente palavras-chave relativamente
     * específicas.
     *
     * Isso evita coisas como:
     *
     * "foto" -> Instagram
     *
     * ou:
     *
     * "face" -> Facebook
     */
    val correspondenciaPorTrecho =
        encontrarPorTrecho(
            fala = falaLimpa,
            funcoes = todasFuncoes
        )

    if (correspondenciaPorTrecho != null) {
        return correspondenciaPorTrecho
    }

    /*
     * ========================================================
     * 5. TOLERÂNCIA A ERROS
     * ========================================================
     *
     * Exemplos:
     *
     * facebok -> Facebook
     * instagran -> Instagram
     * whatsap -> WhatsApp
     */
    return encontrarPorAproximacao(
        fala = falaLimpa,
        funcoes = todasFuncoes
    )
}

/*
 * ============================================================
 * COMANDOS FIXOS
 * ============================================================
 */
private fun criarFuncoesFixas(): List<FuncaoPorVoz> {

    return listOf(

        FuncaoPorVoz(
            nome = "Telefone",
            palavrasChave = listOf(
                "telefone",
                "celular",
                "ligar",
                "fazer ligacao",
                "chamada",
                "discador"
            ),
            acao = ::abrirTelefone
        ),

        FuncaoPorVoz(
            nome = "Mensagens",
            palavrasChave = listOf(
                "mensagem",
                "mensagens",
                "mandar mensagem",
                "enviar mensagem",
                "sms"
            ),
            acao = ::abrirMensagens
        ),

        FuncaoPorVoz(
            nome = "Câmera",
            palavrasChave = listOf(
                "camera",
                "cameras",
                "tirar foto",
                "tirar uma foto",
                "fotografar",
                "selfie"
            ),
            acao = ::abrirCamera
        ),

        FuncaoPorVoz(
            nome = "Galeria",
            palavrasChave = listOf(
                "galeria",
                "ver fotos",
                "album",
                "imagens"
            ),
            acao = ::abrirGaleria
        ),

        FuncaoPorVoz(
            nome = "Emergência",
            palavrasChave = listOf(
                "emergencia",
                "socorro",
                "emergencia medica",
                "preciso de ajuda"
            ),
            acao = ::abrirEmergencia
        )
    )
}

/*
 * ============================================================
 * APLICATIVOS
 * ============================================================
 */
private fun criarFuncoesAplicativos(): List<FuncaoPorVoz> {

    return listOf(

        FuncaoPorVoz(
            nome = "WhatsApp",
            palavrasChave = listOf(
                "whatsapp",
                "whats app",
                "whatsap",
                "watsap",
                "watts app",
                "zap",
                "zap zap",
                "zapzap"
            ),
            acao = { context ->
                abrirAplicativoPorPacotes(
                    context = context,
                    pacotes = listOf(
                        "com.whatsapp",
                        "com.whatsapp.w4b"
                    ),
                    mensagemErro =
                        "O WhatsApp não foi encontrado."
                )
            }
        ),

        FuncaoPorVoz(
            nome = "Telegram",
            palavrasChave = listOf(
                "telegram",
                "telegrama"
            ),
            acao = { context ->
                abrirAplicativoPorPacotes(
                    context,
                    listOf(
                        "org.telegram.messenger"
                    ),
                    "O Telegram não foi encontrado."
                )
            }
        ),

        FuncaoPorVoz(
            nome = "Uber",
            palavrasChave = listOf(
                "uber",
                "uber x",
                "uberx"
            ),
            acao = { context ->
                abrirAplicativoPorPacotes(
                    context,
                    listOf(
                        "com.ubercab"
                    ),
                    "O Uber não foi encontrado."
                )
            }
        ),

        FuncaoPorVoz(
            nome = "iFood",
            palavrasChave = listOf(
                "ifood",
                "i food"
            ),
            acao = { context ->
                abrirAplicativoPorPacotes(
                    context,
                    listOf(
                        "br.com.brainweb.ifood"
                    ),
                    "O iFood não foi encontrado."
                )
            }
        ),

        FuncaoPorVoz(
            nome = "Facebook",
            palavrasChave = listOf(
                "facebook",
                "face book",
                "feice book",
                "feicebuqui"
            ),
            acao = { context ->
                abrirAplicativoPorPacotes(
                    context,
                    listOf(
                        "com.facebook.katana",
                        "com.facebook.lite"
                    ),
                    "O Facebook não foi encontrado."
                )
            }
        ),

        FuncaoPorVoz(
            nome = "Instagram",
            palavrasChave = listOf(
                "instagram",
                "insta"
            ),
            acao = { context ->
                abrirAplicativoPorPacotes(
                    context,
                    listOf(
                        "com.instagram.android"
                    ),
                    "O Instagram não foi encontrado."
                )
            }
        ),

        FuncaoPorVoz(
            nome = "YouTube",
            palavrasChave = listOf(
                "youtube",
                "you tube",
                "iutube"
            ),
            acao = { context ->
                abrirAplicativoPorPacotes(
                    context,
                    listOf(
                        "com.google.android.youtube"
                    ),
                    "O YouTube não foi encontrado."
                )
            }
        ),

        FuncaoPorVoz(
            nome = "Netflix",
            palavrasChave = listOf(
                "netflix",
                "net flix"
            ),
            acao = { context ->
                abrirAplicativoPorPacotes(
                    context,
                    listOf(
                        "com.netflix.mediaclient"
                    ),
                    "O Netflix não foi encontrado."
                )
            }
        ),

        FuncaoPorVoz(
            nome = "Spotify",
            palavrasChave = listOf(
                "spotify",
                "spotfy",
                "espotifai"
            ),
            acao = { context ->
                abrirAplicativoPorPacotes(
                    context,
                    listOf(
                        "com.spotify.music"
                    ),
                    "O Spotify não foi encontrado."
                )
            }
        ),

        FuncaoPorVoz(
            nome = "Gmail",
            palavrasChave = listOf(
                "gmail",
                "g mail",
                "email",
                "e mail"
            ),
            acao = { context ->
                abrirAplicativoPorPacotes(
                    context,
                    listOf(
                        "com.google.android.gm"
                    ),
                    "O Gmail não foi encontrado."
                )
            }
        ),

        FuncaoPorVoz(
            nome = "Google Maps",
            palavrasChave = listOf(
                "google maps",
                "maps",
                "mapa",
                "mapas"
            ),
            acao = { context ->
                abrirAplicativoPorPacotes(
                    context,
                    listOf(
                        "com.google.android.apps.maps"
                    ),
                    "O Google Maps não foi encontrado."
                )
            }
        ),

        FuncaoPorVoz(
            nome = "Configurações",
            palavrasChave = listOf(
                "configuracoes",
                "configuracao",
                "ajustes"
            ),
            acao = { context ->
                abrirAplicativoPorPacotes(
                    context,
                    listOf(
                        "com.android.settings"
                    ),
                    "As configurações não foram encontradas."
                )
            }
        ),

        FuncaoPorVoz(
            nome = "Calculadora",
            palavrasChave = listOf(
                "calculadora"
            ),
            acao = { context ->
                abrirAplicativoPorPacotes(
                    context,
                    listOf(
                        "com.google.android.calculator",
                        "com.android.calculator2",
                        "com.sec.android.app.popupcalculator"
                    ),
                    "A calculadora não foi encontrada."
                )
            }
        ),

        FuncaoPorVoz(
            nome = "Relógio",
            palavrasChave = listOf(
                "relogio",
                "alarme",
                "despertador",
                "cronometro"
            ),
            acao = { context ->
                abrirAplicativoPorPacotes(
                    context,
                    listOf(
                        "com.google.android.deskclock",
                        "com.sec.android.app.clockpackage",
                        "com.android.deskclock"
                    ),
                    "O relógio não foi encontrado."
                )
            }
        ),

        FuncaoPorVoz(
            nome = "Play Store",
            palavrasChave = listOf(
                "play store",
                "playstore",
                "google play"
            ),
            acao = { context ->
                abrirAplicativoPorPacotes(
                    context,
                    listOf(
                        "com.android.vending"
                    ),
                    "A Play Store não foi encontrada."
                )
            }
        )
    )
}

/*
 * ============================================================
 * BUSCA POR TRECHO
 * ============================================================
 */
private fun encontrarPorTrecho(
    fala: String,
    funcoes: List<FuncaoPorVoz>
): FuncaoPorVoz? {

    val palavrasDaFala =
        fala.split(" ")
            .filter { it.isNotBlank() }

    /*
     * Procuramos primeiro as palavras-chave maiores.
     *
     * Isso faz:
     *
     * "google maps"
     *
     * ganhar de:
     *
     * "maps"
     */
    val funcoesOrdenadas =
        funcoes.sortedByDescending { funcao ->
            funcao.palavrasChave.maxOfOrNull {
                normalizarTextoVoz(it).length
            } ?: 0
        }

    for (funcao in funcoesOrdenadas) {

        for (palavraChave in funcao.palavrasChave) {

            val chave =
                normalizarTextoVoz(palavraChave)

            /*
             * Não usamos palavras muito pequenas aqui.
             * Isso reduz falsos positivos.
             */
            if (chave.length < 5) {
                continue
            }

            /*
             * Caso seja uma expressão com várias palavras,
             * procuramos a expressão inteira.
             */
            if (fala.contains(chave)) {
                return funcao
            }

            /*
             * Caso seja uma palavra única, verificamos
             * cada token individualmente.
             */
            if (
                !chave.contains(" ") &&
                palavrasDaFala.contains(chave)
            ) {
                return funcao
            }
        }
    }

    return null
}

/*
 * ============================================================
 * BUSCA APROXIMADA
 * ============================================================
 */
private fun encontrarPorAproximacao(
    fala: String,
    funcoes: List<FuncaoPorVoz>
): FuncaoPorVoz? {

    val palavrasDaFala =
        fala.split(" ")
            .filter {
                it.length >= 4
            }

    var melhorFuncao: FuncaoPorVoz? = null
    var menorDistancia = Int.MAX_VALUE

    for (funcao in funcoes) {

        for (palavraChave in funcao.palavrasChave) {

            val chave =
                normalizarTextoVoz(palavraChave)

            /*
             * Só fazemos aproximação de palavras simples.
             *
             * Frases inteiras aumentariam bastante os
             * falsos positivos.
             */
            if (
                chave.length < 5 ||
                chave.contains(" ")
            ) {
                continue
            }

            for (palavraFalada in palavrasDaFala) {

                val distancia =
                    distanciaLevenshtein(
                        palavraFalada,
                        chave
                    )

                val limite =
                    when {
                        chave.length <= 6 -> 1
                        chave.length <= 10 -> 2
                        else -> 3
                    }

                if (
                    distancia <= limite &&
                    distancia < menorDistancia
                ) {
                    menorDistancia = distancia
                    melhorFuncao = funcao
                }
            }
        }
    }

    return melhorFuncao
}

/*
 * ============================================================
 * ABRIR APLICATIVO
 * ============================================================
 */
private fun abrirAplicativoPorPacotes(
    context: Context,
    pacotes: List<String>,
    mensagemErro: String
) {

    val packageManager =
        context.packageManager

    for (pacote in pacotes) {

        val intent =
            packageManager.getLaunchIntentForPackage(
                pacote
            )

        if (intent != null) {

            try {

                intent.addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK
                )

                context.startActivity(intent)

                return

            } catch (_: Exception) {
                // Tenta o próximo pacote.
            }
        }
    }

    Toast.makeText(
        context,
        mensagemErro,
        Toast.LENGTH_LONG
    ).show()
}

/*
 * ============================================================
 * LEVENSHTEIN
 * ============================================================
 */
fun distanciaLevenshtein(
    primeiro: String,
    segundo: String
): Int {

    val matriz =
        Array(primeiro.length + 1) {
            IntArray(
                segundo.length + 1
            )
        }

    for (i in 0..primeiro.length) {
        matriz[i][0] = i
    }

    for (j in 0..segundo.length) {
        matriz[0][j] = j
    }

    for (i in 1..primeiro.length) {

        for (j in 1..segundo.length) {

            val custo =
                if (
                    primeiro[i - 1] ==
                    segundo[j - 1]
                ) {
                    0
                } else {
                    1
                }

            matriz[i][j] = minOf(
                matriz[i - 1][j] + 1,
                matriz[i][j - 1] + 1,
                matriz[i - 1][j - 1] + custo
            )
        }
    }

    return matriz[
        primeiro.length
    ][
        segundo.length
    ]
}