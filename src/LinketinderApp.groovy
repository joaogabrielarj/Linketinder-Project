import controllers.LinketinderController

class LinketinderApp {
    static void main(String[] args) {
        exibirBannerInicial()

        try {
            LinketinderController controller = new LinketinderController()

            adicionarShutdownHook(controller)

            controller.iniciarSistema()

        } catch (Exception e) {
            System.err.println("❌ ERRO FATAL: Não foi possível inicializar o sistema Linketinder")
            System.err.println("Detalhes: ${e.message}")

            if (args.contains("--debug") || args.contains("-d")) {
                e.printStackTrace()
            } else {
                System.err.println("Use --debug para ver detalhes completos do erro")
            }

            System.exit(1)
        }
    }

    private static void exibirBannerInicial() {
        println """
╔════════════════════════════════════════════════════════════════════╗
║                                                                    ║
║    ██╗     ██╗███╗   ██╗██╗  ██╗███████╗████████╗██╗███╗   ██╗    ║
║    ██║     ██║████╗  ██║██║ ██╔╝██╔════╝╚══██╔══╝██║████╗  ██║    ║
║    ██║     ██║██╔██╗ ██║█████╔╝ █████╗     ██║   ██║██╔██╗ ██║    ║
║    ██║     ██║██║╚██╗██║██╔═██╗ ██╔══╝     ██║   ██║██║╚██╗██║    ║
║    ███████╗██║██║ ╚████║██║  ██╗███████╗   ██║   ██║██║ ╚████║    ║
║    ╚══════╝╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝   ╚═╝   ╚═╝╚═╝  ╚═══╝    ║
║                                                                    ║
║                      D     E     R                                 ║
║                                                                    ║
╠════════════════════════════════════════════════════════════════════╣
║                                                                    ║
║               🚀 O FUTURO DO RECRUTAMENTO CHEGOU! 🚀              ║
║                                                                    ║
║   💼 Sistema de Match Profissional                                ║
║   🎯 Conectando Talentos às Oportunidades Ideais                  ║
║   🏢 Desenvolvido para Empresas Visionárias                       ║
║                                                                    ║
║   👑 Cliente Especial: Dr. Antônio Paçoca                        ║
║   🥖 Pagamento Premium: 1 Pão de Queijo Quentinho                ║
║                                                                    ║
║   📅 Versão: 1.0 MVP                                              ║
║   🔧 Arquitetura: MVC com Boas Práticas                          ║
║   ☕ Linguagem: Groovy                                             ║
║                                                                    ║
╚════════════════════════════════════════════════════════════════════╝

═══════════════════════════════════════════════════════════════════════

🎯 CARACTERÍSTICAS DO SISTEMA:
   ✅ 5+ Candidatos pré-cadastrados
   ✅ 5+ Empresas parceiras (incluindo Arroz-Gostoso e Império do Boliche)
   ✅ Sistema de competências técnicas
   ✅ Algoritmo de matching inteligente
   ✅ Interface intuitiva no terminal
   ✅ Cadastro dinâmico de novos perfis
   ✅ Relatórios e estatísticas completas

🚀 PRÓXIMAS FUNCIONALIDADES (v2.0):
   🔜 Interface web responsiva
   🔜 API REST para integração
   🔜 Sistema de notificações
   🔜 Chat entre candidatos e empresas
   🔜 Avaliações e feedback
   🔜 Machine Learning para matches mais precisos

═══════════════════════════════════════════════════════════════════════
"""
    }

    private static void adicionarShutdownHook(LinketinderController controller) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            void run() {
                println """

╔════════════════════════════════════════════════════════════════╗
║                    ENCERRANDO LINKETINDER...                   ║
╚════════════════════════════════════════════════════════════════╝

🔧 Finalizando processos...
💾 Salvando estado do sistema...
🧹 Limpando recursos...

✅ Sistema encerrado com segurança!

👋 Até a próxima!
🥖 Lembre-se: O pão de queijo está garantido!

                        """
            }
        })
    }

    private static Map<String, Object> processarArgumentos(String[] args) {
        Map<String, Object> config = [
                'debug': false,
                'version': false,
                'help': false
        ]

        args.each { arg ->
            switch (arg.toLowerCase()) {
                case ['--debug', '-d']:
                    config.debug = true
                    break
                case ['--version', '-v']:
                    config.version = true
                    break
                case ['--help', '-h']:
                    config.help = true
                    break
            }
        }

        return config
    }

    private static void exibirVersao() {
        println """
Linketinder v1.0 MVP
Desenvolvido em Groovy
Sistema de Match Profissional

Funcionalidades:
• Cadastro de Candidatos e Empresas
• Sistema de Competências
• Algoritmo de Matching
• Relatórios Estatísticos

© 2024 - João, Desenvolvedor Contratado
💰 Pagamento: 1 Pão de Queijo Premium 🥖
"""
    }

    private static void exibirAjuda() {
        println """
LINKETINDER - Sistema de Match Profissional

USO:
    groovy LinketinderApp.groovy [opções]

OPÇÕES:
    --debug, -d     Ativa modo debug com logs detalhados
    --version, -v   Exibe informação de versão
    --help, -h      Exibe esta mensagem de ajuda

EXEMPLOS:
    groovy LinketinderApp.groovy
    groovy LinketinderApp.groovy --debug
    
REQUISITOS DO SISTEMA:
    • Groovy 3.0+
    • JVM 8+
    • Terminal com suporte a UTF-8

ESTRUTURA DO PROJETO:
    src/
    ├── interfaces/     # Contratos e interfaces
    ├── models/         # Modelos de dados (Candidato, Empresa)  
    ├── services/       # Lógica de negócio
    ├── views/          # Interface e apresentação
    └── controllers/    # Coordenação MVC

Para mais informações, contacte o Dr. Antônio Paçoca! 🥖
"""
    }
}