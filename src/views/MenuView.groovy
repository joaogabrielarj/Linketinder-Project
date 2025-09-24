package views

class MenuView {

    private static final String LINHA_SEPARADORA = "=" * 60
    private static final String LINHA_TITULO = "╔" + "═" * 54 + "╗"
    private static final String LINHA_RODAPE = "╚" + "═" * 54 + "╝"

    void exibirMenuPrincipal() {
        println """
╔══════════════════════════════════════════════════════╗
║                    LINKETINDER                       ║
║              Sistema de Match Profissional          ║
║                                                      ║
║  Desenvolvido para Dr. Antônio Paçoca 🥖            ║
╠══════════════════════════════════════════════════════╣
║                                                      ║
║  1️⃣  - Listar Candidatos                           ║
║  2️⃣  - Listar Empresas                             ║
║  3️⃣  - Cadastrar Novo Candidato                    ║
║  4️⃣  - Cadastrar Nova Empresa                      ║
║  5️⃣  - Buscar Matches                              ║
║  6️⃣  - Relatório do Sistema                        ║
║  0️⃣  - Sair                                        ║
║                                                      ║
╚══════════════════════════════════════════════════════╝"""
        print "Escolha uma opção: "
    }

    void exibirBoasVindas() {
        println """
${LINHA_SEPARADORA}
🚀 BEM-VINDO AO LINKETINDER! 🚀
${LINHA_SEPARADORA}

O futuro dos matches profissionais está aqui!
Conectando talentos às oportunidades ideais.

💼 Sistema desenvolvido especialmente para:
   Dr. Antônio Paçoca - Empresário Visionário

🏢 Empresas participantes:
   • Arroz-Gostoso
   • Império do Boliche
   • E muito mais...

Inicializando sistema...
"""
    }

    void exibirDespedida() {
        println """
${LINHA_TITULO}
║                   OBRIGADO!                          ║
╠══════════════════════════════════════════════════════╣
║                                                      ║
║ 👋 Obrigado por usar o LINKETINDER!                 ║
║                                                      ║
║ 💼 Dr. Antônio Paçoca ficará orgulhoso do resultado! ║
║                                                      ║
║ 🥖 Seu pão de queijo está garantido!                ║
║                                                      ║
║ 🚀 Volte sempre para encontrar novos matches!       ║
║                                                      ║
${LINHA_RODAPE}

Desenvolvido com ❤️  para revolucionar o recrutamento!
"""
    }

    void exibirEstatisticas(Map<String, Integer> stats) {
        println """
✅ SISTEMA INICIALIZADO COM SUCESSO!

📊 Dados carregados:
   • ${stats.candidatos} candidatos cadastrados
   • ${stats.empresas} empresas parceiras
   • Sistema pronto para matches!

${"-" * 50}
"""
    }

    void exibirErro(String mensagem) {
        println """
❌ ERRO: ${mensagem}

Por favor, tente novamente ou contate o suporte.
"""
    }

    void exibirSucesso(String mensagem) {
        println """
✅ SUCESSO: ${mensagem}
"""
    }

    void exibirAviso(String mensagem) {
        println """
⚠️  AVISO: ${mensagem}
"""
    }

    void exibirInfo(String mensagem) {
        println """
ℹ️  INFO: ${mensagem}
"""
    }

    void pausarTela() {
        println "\n" + "─" * 50
        println "Pressione Enter para continuar..."
        try {
            System.in.newReader().readLine()
        } catch (Exception e) {
        }
    }

    void exibirCabecalho(String titulo) {
        String tituloFormatado = titulo.toUpperCase()
        String padding = " " * Math.max(0, (50 - tituloFormatado.length()) / 2)

        println """
${LINHA_SEPARADORA}
${padding}${tituloFormatado}
${LINHA_SEPARADORA}
"""
    }


    void exibirMenuMatches() {
        println """
╔══════════════════════════════════════════════════════╗
║                  BUSCA DE MATCHES                    ║
╠══════════════════════════════════════════════════════╣
║                                                      ║
║  1️⃣  - Matches com 30% de compatibilidade          ║
║  2️⃣  - Matches com 50% de compatibilidade          ║
║  3️⃣  - Matches com 70% de compatibilidade          ║
║  4️⃣  - Busca personalizada                         ║
║  0️⃣  - Voltar ao menu principal                    ║
║                                                      ║
╚══════════════════════════════════════════════════════╝"""
        print "Escolha uma opção: "
    }

    void exibirMatches(List<Map> matches) {
        if (matches.isEmpty()) {
            exibirAviso("Nenhum match encontrado com os critérios especificados.")
            return
        }

        exibirCabecalho("MATCHES ENCONTRADOS")

        matches.eachWithIndex { match, index ->
            double percentual = match.percentualMatch * 100

            println """
🎯 MATCH #${index + 1} (${String.format('%.1f', percentual)}% de compatibilidade)
${"-" * 60}
👨‍💼 Candidato: ${match.candidato.nome}
   📧 ${match.candidato.email}
   🎂 ${match.candidato.idade} anos (${match.candidato.getNivelSenioridade()})

🏢 Empresa: ${match.empresa.nome}
   📧 ${match.empresa.email}
   📍 ${match.empresa.estado}, ${match.empresa.pais}

🔗 Competências em Comum:
   ${match.competenciasComuns.join(', ')}

${"=" * 60}
"""
        }

        exibirInfo("Total de matches encontrados: ${matches.size()}")
    }

    void exibirRelatorio(String relatorio) {
        println "\n${relatorio}\n"
    }

    boolean solicitarConfirmacao(String mensagem) {
        print "${mensagem} (s/N): "
        try {
            String resposta = System.in.newReader().readLine()?.trim()?.toLowerCase()
            return resposta in ['s', 'sim', 'y', 'yes']
        } catch (Exception e) {
            return false
        }
    }

    double solicitarPercentual() {
        print "Digite o percentual mínimo de compatibilidade (0-100): "
        try {
            String input = System.in.newReader().readLine()?.trim()
            double valor = Double.parseDouble(input)

            if (valor < 0 || valor > 100) {
                exibirErro("Percentual deve estar entre 0 e 100")
                return solicitarPercentual()
            }

            return valor / 100.0
        } catch (NumberFormatException e) {
            exibirErro("Por favor, digite um número válido")
            return solicitarPercentual()
        } catch (Exception e) {
            return 0.3 // Valor padrão
        }
    }

    void exibirLoading(String mensagem = "Processando", int duracao = 2000) {
        String[] animacao = ['⠋', '⠙', '⠹', '⠸', '⠼', '⠴', '⠦', '⠧', '⠇', '⠏']
        int ciclos = duracao / 100

        print "\r"
        for (int i = 0; i < ciclos; i++) {
            print "\r${animacao[i % animacao.length]} ${mensagem}..."
            Thread.sleep(100)
        }
        println "\r✅ ${mensagem} concluído!          "
    }

    void limparTela() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor()
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor()
            }
        } catch (Exception e) {
            (0..50).each { println "" }
        }
    }

    void exibirBarraProgresso(int atual, int total, int largura = 50) {
        double percentual = (double) atual / total
        int preenchido = (int) (percentual * largura)
        int vazio = largura - preenchido

        String barra = "█" * preenchido + "░" * vazio
        String porcentagem = String.format("%.1f", percentual * 100)

        print "\r[${barra}] ${porcentagem}% (${atual}/${total})"

        if (atual == total) {
            println ""
        }
    }
}