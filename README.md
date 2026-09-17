# 🤝 Mão Amiga

> **Tecnologia para facilitar o dia a dia de pessoas idosas.**

## 📌 Sobre o projeto

O **Mão Amiga** é um projeto acadêmico voltado à utilização da tecnologia para auxiliar pessoas idosas, especialmente aquelas que podem apresentar dificuldades relacionadas à visão e à utilização de dispositivos móveis.

Este repositório contém o **aplicativo Android** do projeto, desenvolvido com o objetivo de oferecer uma interface simplificada e recursos de acessibilidade que facilitem a utilização do smartphone.

O aplicativo possui funcionamento independente e, atualmente, **não depende de uma API ou sistema externo para funcionar**.

---

# 🎯 Objetivos

* Facilitar a utilização de dispositivos móveis por pessoas idosas.
* Desenvolver uma interface simples e acessível.
* Facilitar o acesso às funções mais utilizadas do smartphone.
* Utilizar tecnologia como ferramenta de apoio à autonomia e ao bem-estar.
* Reduzir a complexidade das interfaces convencionais de smartphones.

---

# 👥 Público-alvo

* Pessoas idosas, especialmente aquelas com dificuldades de visão ou utilização de smartphones.
* Pessoas que necessitam de uma interface mais simples e acessível para utilizar o celular.

---

# 📱 Aplicativo Android

O aplicativo possui como objetivo principal **facilitar a utilização do celular por pessoas idosas**, oferecendo uma interface mais simples e recursos de acessibilidade.

O protótipo está sendo desenvolvido utilizando **Android Studio e Kotlin**.

Entre os recursos trabalhados estão:

* 📞 **Telefone**
* 💬 **Mensagens**
* 📷 **Câmera**
* 🖼️ **Galeria**
* 🚨 **Emergência**
* 📱 **Mais aplicativos**
* 🔠 **Aumento do tamanho da fonte**
* 🎙️ **Comandos por voz**
* Interface simplificada
* Recursos de acessibilidade

---

# ♿ Acessibilidade

O projeto considera recursos para facilitar a utilização por pessoas idosas e pessoas com baixa visão.

Entre os recursos desenvolvidos ou trabalhados:

* Textos maiores.
* Ícones de fácil identificação.
* Interface simplificada.
* Botões maiores.
* Navegação direta.
* Aumento do tamanho da fonte.
* Redução da quantidade de elementos por tela.

A proposta é reduzir as dificuldades encontradas em interfaces convencionais e tornar as funções principais do smartphone mais fáceis de localizar e utilizar.

---

# 🎙️ Comandos por voz

O aplicativo possui recursos de **reconhecimento de voz** para facilitar o acesso aos aplicativos.

O sistema trabalha com diferentes formas de identificação dos aplicativos, permitindo reconhecer algumas variações de escrita, pronúncia e comandos.

Exemplos:

```text
"WhatsApp"
"Whats app"
"Zap"
"Zap zap"

"Facebook"
"Face book"

"Instagram"
"Instagran"

"Abra o Uber"
```

O recurso busca tornar a utilização do smartphone mais acessível para usuários que possam apresentar dificuldades na navegação tradicional pela interface.

---

# 🏗️ Estrutura do projeto

O aplicativo é organizado de forma a separar responsabilidades relacionadas à interface, launcher, acessibilidade e reconhecimento de voz.

```text
app/
└── src/
    └── main/
        └── java/
            └── com.example.maoamiga/
                ├── acessibilidade/
                │   ├── PreferenciasFonte.kt
                │   └── TelaFonteAcessivel.kt
                │
                └── launcher/
                    ├── LauncherActivity.kt
                    ├── TelaLauncher.kt
                    ├── ConteudoInicio.kt
                    ├── ConteudoTodosApps.kt
                    ├── AcoesAplicativos.kt
                    ├── ComandosVoz.kt
                    └── ReconhecimentoVoz.kt
```

A organização busca facilitar a manutenção do aplicativo e separar os componentes responsáveis pelas diferentes funcionalidades.

---

# 🛠️ Tecnologias utilizadas

## Aplicativo

* Kotlin
* Android
* Jetpack Compose
* Android Studio
* Android SDK

## Ferramentas

* Git
* GitHub
* IntelliJ IDEA

---

# 🧪 Testes

O aplicativo é testado considerando principalmente sua utilização por pessoas que podem apresentar dificuldades com interfaces convencionais.

São realizados testes relacionados à:

* Navegação.
* Abertura das funções.
* Tamanho da interface.
* Aumento da fonte.
* Persistência das configurações.
* Abertura de aplicativos.
* Reconhecimento de comandos por voz.
* Facilidade de utilização.

---

# 📚 Documentação

A documentação do projeto está sendo desenvolvida no **GitBook**, contendo informações sobre:

* Objetivos
* Requisitos
* Interface
* Acessibilidade
* Testes
* Evolução do projeto

---

# 👥 Equipe

| **Integrante** | **Responsabilidade**                                    |
| -------------- | ------------------------------------------------------- |
| **Edson**      | Desenvolvimento do aplicativo e integração              |
| **Lucas**      | Backend e testes                                        |
| **Luiza**      | Inteligência Artificial                                 |
| **Diego**      | Inteligência Artificial e discussão do módulo de idosos |
| **Karlla**     | Frontend, interface e testes                            |
| **Rafael**     | A definir                                               |

> **Observação:** neste repositório, o foco está no desenvolvimento do **aplicativo Android**.

---

# 🚀 Evolução do projeto

### Etapa 1 — Definição do projeto

* Definição do problema.
* Identificação do público-alvo.
* Definição dos objetivos.
* Planejamento dos recursos de acessibilidade.

### Etapa 2 — Protótipo Android

* Criação do aplicativo Android.
* Desenvolvimento da interface mobile.
* Implementação do launcher simplificado.
* Implementação dos recursos de acessibilidade.

### Etapa 3 — Recursos de acessibilidade

* Aumento do tamanho da fonte.
* Desenvolvimento de interface simplificada.
* Implementação de botões e elementos maiores.
* Organização das funções principais do smartphone.

### Etapa 4 — Comandos por voz

* Implementação do reconhecimento de voz.
* Identificação de comandos.
* Abertura de aplicativos por voz.
* Testes de diferentes formas de pronúncia e reconhecimento.

### Próximas etapas

* Evolução dos recursos de acessibilidade.
* Novos testes de acessibilidade e usabilidade.
* Melhorias no reconhecimento de voz.
* Avaliação de novos recursos para facilitar a utilização do smartphone.

---

# 📊 Status

🟡 **Em desenvolvimento**

### Atualmente implementado

* ✅ Protótipo Android
* ✅ Launcher simplificado
* ✅ Acesso rápido ao telefone
* ✅ Acesso rápido às mensagens
* ✅ Acesso à câmera
* ✅ Acesso à galeria
* ✅ Acesso à emergência
* ✅ Visualização de aplicativos
* ✅ Ajuste do tamanho da fonte
* ✅ Interface acessível
* ✅ Reconhecimento de voz
* ✅ Abertura de aplicativos por comandos de voz

### Ainda em desenvolvimento

* ⏳ Novos recursos de acessibilidade
* ⏳ Melhorias nos comandos por voz
* ⏳ Novos testes de acessibilidade e usabilidade
* ⏳ Evolução da interface
