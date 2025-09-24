package views

import models.Empresa

class EmpresaView {

    private Scanner scanner = new Scanner(System.in)
    private static final String SEPARADOR_EMPRESA = "═" * 55

    void listarEmpresas(List<Empresa> empresas) {
        if (empresas.isEmpty()) {
            println """
❌ Nenhuma empresa cadastrada no sistema.

💡 Dica: Use a opção 'Cadastrar Nova Empresa' para adicionar empresas.
"""
            return
        }

        println """
🏢 LISTANDO TODAS AS EMPRESAS
${SEPARADOR_EMPRESA}

📊 Total encontrado: ${empresas.size()} empresa(s)
"""

        empresas.eachWithIndex { empresa, index ->
            println "【${index + 1}/${empresas.size()}】"
            empresa.exibirInformacoes()

            if (index < empresas.size() - 1) {
                println ""
            }
        }

        exibirEstatisticasRapidas(empresas)
    }

    Empresa coletarDadosNovaEmpresa() {
        println """
🏢 CADASTRO DE NOVA EMPRESA
${SEPARADOR_EMPRESA}

Por favor, preencha os dados solicitados:
"""

        try {
            String nome = coletarDado("Nome da empresa", true)
            String email = coletarEmailCorporativo()
            String cnpj = coletarCnpj()
            String pais = coletarPais()
            String estado = coletarDado("Estado", true)
            String cep = coletarCep()
            String descricao = coletarDescricaoEmpresa()
            List<String> competencias = coletarCompetenciasDesejadas()

            return new Empresa(nome, email, cnpj, pais, estado, cep, descricao, competencias)

        } catch (Exception e) {
            throw new RuntimeException("Erro ao coletar dados da empresa: ${e.message}", e)
        }
    }

    private String coletarDado(String campo, boolean obrigatorio = false) {
        while (true) {
            print "${campo}${obrigatorio ? ' (obrigatório)' : ''}: "
            String valor = scanner.nextLine()?.trim()

            if (obrigatorio && (!valor || valor.isEmpty())) {
                println "❌ ${campo} é obrigatório. Tente novamente."
                continue
            }

            return valor ?: ""
        }
    }

    private String coletarEmailCorporativo() {
        while (true) {
            String email = coletarDado("Email corporativo", true)

            if (email.contains("@") && email.contains(".")) {
                return email.toLowerCase()
            }

            println "❌ Email deve ter formato válido (exemplo: rh@empresa.com.br)"
        }
    }

    private String coletarCnpj() {
        while (true) {
            String cnpj = coletarDado("CNPJ (formato XX.XXX.XXX/XXXX-XX)", true)

            String regex = /^\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}$|^\d{14}$/
            if (cnpj.matches(regex)) {
                return cnpj
            }

            println "❌ CNPJ deve ter formato válido. Exemplos: 12.345.678/0001-90 ou 12345678000190"
        }
    }

    private String coletarPais() {
        println "\nPaíses sugeridos: Brasil, Estados Unidos, Canadá, Reino Unido, Alemanha"
        String pais = coletarDado("País", true)

        switch (pais.toLowerCase()) {
            case ['brasil', 'brazil', 'br']:
                return "Brasil"
            case ['eua', 'usa', 'estados unidos', 'united states']:
                return "Estados Unidos"
            case ['canada', 'canadá']:
                return "Canadá"
            case ['reino unido', 'uk', 'england', 'inglaterra']:
                return "Reino Unido"
            case ['alemanha', 'germany']:
                return "Alemanha"
            default:
                return pais
        }
    }

    private String coletarCep() {
        while (true) {
            String cep = coletarDado("CEP (formato XXXXX-XXX)", true)

            String regex = /^\d{5}-\d{3}$|^\d{8}$/
            if (cep.matches(regex)) {
                return cep
            }

            println "❌ CEP deve ter formato válido. Exemplos: 12345-678 ou 12345678"
        }
    }

    private String coletarDescricaoEmpresa() {
        println """
Descreva sua empresa (área de atuação, missão, diferencial):"""
        print "Descrição: "
        String descricao = scanner.nextLine()?.trim()

        if (!descricao || descricao.isEmpty()) {
            return "Empresa em busca dos melhores talentos do mercado"
        }

        return descricao
    }

    private List<String> coletarCompetenciasDesejadas() {
        println """

🎯 COMPETÊNCIAS DESEJADAS NOS CANDIDATOS
Informe as competências que sua empresa procura, separadas por vírgula.

Exemplos por área:
• Desenvolvimento: Java, Python, JavaScript, React, Spring, Angular
• DevOps: Docker, Kubernetes, AWS, Azure, CI/CD, Linux
• Dados: SQL, MongoDB, Python, Power BI, Tableau, Machine Learning
• Mobile: Android, iOS, Flutter, React Native, Xamarin
• Gestão: Scrum, Agile, Kanban, Liderança, PMP"""

        while (true) {
            print "\nCompetências desejadas: "
            String competenciasStr = scanner.nextLine()?.trim()

            if (!competenciasStr || competenciasStr.isEmpty()) {
                println "❌ Informe pelo menos uma competência desejada."
                continue
            }

            List<String> competencias = competenciasStr.split(',')
                    .collect { it.trim() }
                    .findAll { it && !it.isEmpty() }

            if (competencias.isEmpty()) {
                println "❌ Formato inválido. Use vírgulas para separar as competências."
                continue
            }

            println "\n✅ Competências desejadas nos candidatos:"
            competencias.eachWithIndex { comp, index ->
                println "   ${index + 1}. ${comp}"
            }

            print "\nConfirma essas competências? (s/N): "
            String confirmacao = scanner.nextLine()?.trim()?.toLowerCase()

            if (confirmacao in ['s', 'sim', 'y', 'yes']) {
                return competencias
            }

            println "Vamos tentar novamente..."
        }
    }

    void exibirSucessoCadastro(Empresa empresa) {
        println """
${SEPARADOR_EMPRESA}
✅ EMPRESA CADASTRADA COM SUCESSO!
${SEPARADOR_EMPRESA}

🎉 Parabéns! Sua empresa foi registrada no Linketinder.
🔍 Agora você pode encontrar os melhores talentos do mercado.

"""
        empresa.exibirInformacoes()

        println """
💡 PRÓXIMOS PASSOS:
   • Explore os candidatos disponíveis no sistema
   • Utilize a busca por matches para encontrar talentos
   • Mantenha as competências desejadas sempre atualizadas
   • Aguarde candidatos compatíveis com seu perfil

🚀 Bem-vinda ao futuro do recrutamento!
"""
    }

    private void exibirEstatisticasRapidas(List<Empresa> empresas) {
        if (empresas.isEmpty()) return

        int nacionais = empresas.count { it.isNacional() }
        int internacionais = empresas.size() - nacionais

        Map<String, Integer> estadosCount = [:]
        empresas.each { empresa ->
            String estado = empresa.estado
            estadosCount[estado] = (estadosCount[estado] ?: 0) + 1
        }

        String estadoMaisComum = estadosCount.isEmpty() ? "N/A" :
                estadosCount.max { it.value }.key

        println """
📊 ESTATÍSTICAS RÁPIDAS:
   • Empresas nacionais: ${nacionais}
   • Empresas internacionais: ${internacionais}
   • Estado com mais empresas: ${estadoMaisComum}
"""
    }

    void exibirEmpresasPorCompetencia(List<Empresa> empresas, String competencia) {
        println """
🔍 EMPRESAS QUE PROCURAM: ${competencia.toUpperCase()}
${SEPARADOR_EMPRESA}
"""

        if (empresas.isEmpty()) {
            println "❌ Nenhuma empresa encontrada procurando a competência '${competencia}'"
            return
        }

        listarEmpresas(empresas)
    }

    void exibirEmpresasDrPacoca(List<Empresa> empresasDrPacoca) {
        println """
🥖 EMPRESAS DO DR. ANTÔNIO PAÇOCA
${SEPARADOR_EMPRESA}

👑 Conheça as empresas do nosso cliente especial:
"""

        empresasDrPacoca.each { empresa ->
            empresa.exibirInformacoes()
            println ""
        }

        if (empresasDrPacoca.size() >= 2) {
            println "🎯 Duas empresas de sucesso, duas oportunidades incríveis!"
        }
    }

    String solicitarFiltroCompetencia() {
        print "Digite a competência para filtrar (ex: Java, Python): "
        return scanner.nextLine()?.trim()
    }

    String solicitarFiltroPais() {
        print "Digite o país para filtrar (ex: Brasil, Estados Unidos): "
        return scanner.nextLine()?.trim()
    }
}