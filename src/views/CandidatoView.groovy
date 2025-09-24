package views

import models.Candidato

class CandidatoView {

    private Scanner scanner = new Scanner(System.in)
    private static final String SEPARADOR_CANDIDATO = "═" * 55

    void listarCandidatos(List<Candidato> candidatos) {
        if (candidatos.isEmpty()) {
            println """
❌ Nenhum candidato cadastrado no sistema.
   
💡 Dica: Use a opção 'Cadastrar Novo Candidato' para adicionar candidatos.
"""
            return
        }

        println """
🔍 LISTANDO TODOS OS CANDIDATOS
${SEPARADOR_CANDIDATO}

📊 Total encontrado: ${candidatos.size()} candidato(s)
"""

        candidatos.eachWithIndex { candidato, index ->
            println "【${index + 1}/${candidatos.size()}】"
            candidato.exibirInformacoes()

            if (index < candidatos.size() - 1) {
                println ""
            }
        }

        exibirEstatisticasRapidas(candidatos)
    }

    Candidato coletarDadosNovoCandidato() {
        println """
📝 CADASTRO DE NOVO CANDIDATO
${SEPARADOR_CANDIDATO}

Por favor, preencha os dados solicitados:
"""

        try {
            String nome = coletarDado("Nome completo", true)
            String email = coletarEmail()
            String cpf = coletarCpf()
            int idade = coletarIdade()
            String estado = coletarDado("Estado", true)
            String cep = coletarCep()
            String descricao = coletarDescricao()
            List<String> competencias = coletarCompetencias()

            return new Candidato(nome, email, cpf, idade, estado, cep, descricao, competencias)

        } catch (Exception e) {
            throw new RuntimeException("Erro ao coletar dados do candidato: ${e.message}", e)
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

    private String coletarEmail() {
        while (true) {
            String email = coletarDado("Email", true)

            if (email.contains("@") && email.contains(".")) {
                return email.toLowerCase()
            }

            println "❌ Email deve ter formato válido (exemplo: nome@email.com)"
        }
    }

    private String coletarCpf() {
        while (true) {
            String cpf = coletarDado("CPF (formato XXX.XXX.XXX-XX)", true)

            String regex = /^\d{3}\.\d{3}\.\d{3}-\d{2}$|^\d{11}$/
            if (cpf.matches(regex)) {
                return cpf
            }

            println "❌ CPF deve ter formato válido. Exemplos: 123.456.789-10 ou 12345678910"
        }
    }

    private int coletarIdade() {
        while (true) {
            try {
                print "Idade (16-120 anos): "
                int idade = Integer.parseInt(scanner.nextLine().trim())

                if (idade < 16) {
                    println "❌ Candidato deve ter pelo menos 16 anos."
                    continue
                }

                if (idade > 120) {
                    println "❌ Idade deve ser realista (máximo 120 anos)."
                    continue
                }

                return idade

            } catch (NumberFormatException e) {
                println "❌ Por favor, digite um número válido para a idade."
            }
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

    private String coletarDescricao() {
        println "\nDescreva brevemente seu perfil profissional:"
        print "Descrição: "
        String descricao = scanner.nextLine()?.trim()

        if (!descricao || descricao.isEmpty()) {
            return "Profissional em busca de novas oportunidades"
        }

        return descricao
    }

    private List<String> coletarCompetencias() {
        println """

💡 COMPETÊNCIAS TÉCNICAS
Informe suas competências separadas por vírgula.
Exemplos: Java, Python, JavaScript, React, MySQL, Docker, AWS

Competências sugeridas:
• Linguagens: Java, Python, JavaScript, C#, PHP, Go
• Frameworks: Spring, React, Angular, Vue, Django, Laravel  
• Bancos: MySQL, PostgreSQL, MongoDB, Oracle, Redis
• Cloud: AWS, Azure, Google Cloud, Docker, Kubernetes
• Metodologias: Scrum, Agile, TDD, CI/CD, DevOps"""

        while (true) {
            print "\nSuas competências: "
            String competenciasStr = scanner.nextLine()?.trim()

            if (!competenciasStr || competenciasStr.isEmpty()) {
                println "❌ Informe pelo menos uma competência."
                continue
            }

            List<String> competencias = competenciasStr.split(',')
                    .collect { it.trim() }
                    .findAll { it && !it.isEmpty() }

            if (competencias.isEmpty()) {
                println "❌ Formato inválido. Use vírgulas para separar as competências."
                continue
            }

            println "\n✅ Competências informadas:"
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


    void exibirSucessoCadastro(Candidato candidato) {
        println """
${SEPARADOR_CANDIDATO}
✅ CANDIDATO CADASTRADO COM SUCESSO!
${SEPARADOR_CANDIDATO}

🎉 Parabéns! Seu perfil foi criado no Linketinder.
🔍 Agora as empresas podem encontrar você através de suas competências.

"""
        candidato.exibirInformacoes()

        println """
💡 PRÓXIMOS PASSOS:
   • Aguarde por matches com empresas interessadas
   • Mantenha seu perfil sempre atualizado
   • Explore as vagas disponíveis no sistema
"""
    }

    private void exibirEstatisticasRapidas(List<Candidato> candidatos) {
        if (candidatos.isEmpty()) return

        int juniors = candidatos.count { it.isJunior() }
        int plenos = candidatos.count { it.isPleno() }
        int seniors = candidatos.count { it.isSenior() }
        double idadeMedia = candidatos.sum { it.idade } / candidatos.size()

        println """
📊 ESTATÍSTICAS RÁPIDAS:
   • Júniors (até 25 anos): ${juniors}
   • Plenos (26-35 anos): ${plenos}  
   • Sêniors (35+ anos): ${seniors}
   • Idade média: ${String.format('%.1f', idadeMedia)} anos
"""
    }

    /**
     * Exibe candidatos filtrados por competência
     * @param candidatos Lista de candidatos
     * @param competencia Competência filtrada
     */
    void exibirCandidatosPorCompetencia(List<Candidato> candidatos, String competencia) {
        println """
🔍 CANDIDATOS COM COMPETÊNCIA: ${competencia.toUpperCase()}
${SEPARADOR_CANDIDATO}
"""

        if (candidatos.isEmpty()) {
            println "❌ Nenhum candidato encontrado com a competência '${competencia}'"
            return
        }

        listarCandidatos(candidatos)
    }

    String solicitarFiltroCompetencia() {
        print "Digite a competência para filtrar (ex: Java, Python): "
        return scanner.nextLine()?.trim()
    }
}