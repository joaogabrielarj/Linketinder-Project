package controllers

import services.LinketinderService
import views.MenuView
import views.CandidatoView
import views.EmpresaView
import models.Candidato
import models.Empresa

class LinketinderController {

    private LinketinderService service

    private MenuView menuView
    private CandidatoView candidatoView
    private EmpresaView empresaView

    private Scanner scanner

    LinketinderController() {
        this.service = new LinketinderService()

        this.menuView = new MenuView()
        this.candidatoView = new CandidatoView()
        this.empresaView = new EmpresaView()

        this.scanner = new Scanner(System.in)
    }

    void iniciarSistema() {
        try {
            menuView.exibirBoasVindas()

            inicializarDados()

            executarMenuPrincipal()

        } catch (Exception e) {
            menuView.exibirErro("Erro fatal na inicialização: ${e.message}")
            e.printStackTrace()
        }
    }

    private void inicializarDados() {
        try {
            menuView.exibirLoading("Inicializando sistema", 1500)
            service.inicializarSistema()

            Map<String, Integer> stats = service.getEstatisticas()
            menuView.exibirEstatisticas(stats)

        } catch (Exception e) {
            throw new RuntimeException("Falha na inicialização dos dados", e)
        }
    }

    private void executarMenuPrincipal() {
        while (true) {
            try {
                menuView.exibirMenuPrincipal()
                int opcao = lerOpcaoMenu()

                switch (opcao) {
                    case 1:
                        processarListarCandidatos()
                        break
                    case 2:
                        processarListarEmpresas()
                        break
                    case 3:
                        processarCadastrarCandidato()
                        break
                    case 4:
                        processarCadastrarEmpresa()
                        break
                    case 5:
                        processarBuscarMatches()
                        break
                    case 6:
                        processarRelatorioSistema()
                        break
                    case 0:
                        encerrarSistema()
                        return
                    default:
                        menuView.exibirErro("Opção inválida! Escolha uma opção entre 0 e 6.")
                }

            } catch (Exception e) {
                menuView.exibirErro("Erro durante a operação: ${e.message}")
                e.printStackTrace()
            }

            menuView.pausarTela()
        }
    }

    private int lerOpcaoMenu() {
        try {
            String input = scanner.nextLine()?.trim()
            return Integer.parseInt(input)
        } catch (NumberFormatException e) {
            return -1
        }
    }

    private void processarListarCandidatos() {
        try {
            menuView.exibirLoading("Carregando candidatos", 800)
            List<Candidato> candidatos = service.getCandidatoService().listarTodos()
            candidatoView.listarCandidatos(candidatos)

        } catch (Exception e) {
            menuView.exibirErro("Não foi possível listar os candidatos: ${e.message}")
        }
    }

    private void processarListarEmpresas() {
        try {
            menuView.exibirLoading("Carregando empresas", 800)
            List<Empresa> empresas = service.getEmpresaService().listarTodas()
            empresaView.listarEmpresas(empresas)

            List<Empresa> empresasDrPacoca = service.getEmpresaService().buscarEmpresasDrPacoca()
            if (empresasDrPacoca.size() > 0) {
                println "\n" + "─" * 50
                empresaView.exibirEmpresasDrPacoca(empresasDrPacoca)
            }

        } catch (Exception e) {
            menuView.exibirErro("Não foi possível listar as empresas: ${e.message}")
        }
    }

    private void processarCadastrarCandidato() {
        try {
            Candidato novoCandidato = candidatoView.coletarDadosNovoCandidato()

            if (service.getCandidatoService().candidatoExiste(novoCandidato.getEmail())) {
                menuView.exibirErro("Já existe um candidato cadastrado com este email!")
                return
            }

            service.getCandidatoService().adicionarCandidato(novoCandidato)
            candidatoView.exibirSucessoCadastro(novoCandidato)

            sugerirMatchesParaCandidato(novoCandidato)

        } catch (Exception e) {
            menuView.exibirErro("Erro ao cadastrar candidato: ${e.message}")
        }
    }

    private void processarCadastrarEmpresa() {
        try {
            Empresa novaEmpresa = empresaView.coletarDadosNovaEmpresa()

            if (service.getEmpresaService().empresaExiste(novaEmpresa.getEmail())) {
                menuView.exibirErro("Já existe uma empresa cadastrada com este email!")
                return
            }

            service.getEmpresaService().adicionarEmpresa(novaEmpresa)
            empresaView.exibirSucessoCadastro(novaEmpresa)

            sugerirCandidatosParaEmpresa(novaEmpresa)

        } catch (Exception e) {
            menuView.exibirErro("Erro ao cadastrar empresa: ${e.message}")
        }
    }

    private void processarBuscarMatches() {
        try {
            menuView.exibirMenuMatches()
            int opcao = lerOpcaoMenu()

            double percentualMinimo
            switch (opcao) {
                case 1:
                    percentualMinimo = 0.3
                    break
                case 2:
                    percentualMinimo = 0.5
                    break
                case 3:
                    percentualMinimo = 0.7
                    break
                case 4:
                    percentualMinimo = menuView.solicitarPercentual()
                    break
                case 0:
                    return
                default:
                    menuView.exibirErro("Opção inválida!")
                    return
            }

            menuView.exibirLoading("Buscando matches", 2000)
            List<Map> matches = service.executarBuscaMatches(percentualMinimo)
            menuView.exibirMatches(matches)

        } catch (Exception e) {
            menuView.exibirErro("Erro na busca de matches: ${e.message}")
        }
    }

    private void processarRelatorioSistema() {
        try {
            menuView.exibirLoading("Gerando relatório", 1500)
            String relatorio = service.gerarRelatorioCompleto()
            menuView.exibirRelatorio(relatorio)

            if (menuView.solicitarConfirmacao("Deseja ver estatísticas detalhadas?")) {
                exibirEstatisticasDetalhadas()
            }

        } catch (Exception e) {
            menuView.exibirErro("Erro ao gerar relatório: ${e.message}")
        }
    }

    private void exibirEstatisticasDetalhadas() {
        try {
            Map<String, Object> stats = service.getEstatisticasCompletas()

            println """
📊 ESTATÍSTICAS DETALHADAS
${"═" * 60}

🔍 COMPETÊNCIAS MAIS PROCURADAS PELAS EMPRESAS:"""

            Map<String, Integer> competenciasEmpresas = stats.empresas.competenciasMapMaisDesejadas
            competenciasEmpresas.take(10).eachWithIndex { entry, index ->
                println "   ${index + 1}. ${entry.key}: ${entry.value} empresas"
            }

            println """
💼 COMPETÊNCIAS MAIS COMUNS DOS CANDIDATOS:"""

            Map<String, Integer> competenciasCandidatos = stats.candidatos.competenciasMaisComuns
            competenciasCandidatos.take(10).eachWithIndex { entry, index ->
                println "   ${index + 1}. ${entry.key}: ${entry.value} candidatos"
            }

            println """
🌎 DISTRIBUIÇÃO GEOGRÁFICA:"""

            Map<String, Integer> estados = stats.empresas.estadosComMaisEmpresas
            estados.take(5).eachWithIndex { entry, index ->
                println "   ${index + 1}. ${entry.key}: ${entry.value} empresas"
            }

        } catch (Exception e) {
            menuView.exibirErro("Erro ao exibir estatísticas detalhadas: ${e.message}")
        }
    }

    private void sugerirMatchesParaCandidato(Candidato candidato) {
        try {
            List<Map> matches = service.getEmpresaService()
                    .encontrarEmpresasCompativeis(candidato, 0.3)

            if (!matches.isEmpty()) {
                println "\n🎯 MATCHES ENCONTRADOS PARA VOCÊ:"
                matches.take(3).each { match ->
                    double percentual = match.percentualMatch * 100
                    println """
🏢 ${match.empresa.nome} - ${String.format('%.0f', percentual)}% de compatibilidade
   📧 ${match.empresa.email}
   🔗 Competências em comum: ${match.competenciasComuns.join(', ')}"""
                }

                if (matches.size() > 3) {
                    println "\n   + ${matches.size() - 3} outras empresas interessadas!"
                }
            }
        } catch (Exception e) {
        }
    }

    private void sugerirCandidatosParaEmpresa(Empresa empresa) {
        try {
            List<Candidato> candidatos = service.getCandidatoService().listarTodos()
            List<Map> matches = []

            candidatos.each { candidato ->
                double match = empresa.calcularMatchComCandidato(candidato)
                if (match >= 0.3) {
                    matches.add([
                            'candidato': candidato,
                            'percentualMatch': match,
                            'competenciasComuns': empresa.competencias.findAll {
                                candidato.competencias.contains(it)
                            }
                    ])
                }
            }

            if (!matches.isEmpty()) {
                matches = matches.sort { -it.percentualMatch }

                println "\n🎯 CANDIDATOS COMPATÍVEIS COM SUA EMPRESA:"
                matches.take(3).each { match ->
                    double percentual = match.percentualMatch * 100
                    println """
👨‍💼 ${match.candidato.nome} - ${String.format('%.0f', percentual)}% de compatibilidade
   📧 ${match.candidato.email}
   🎂 ${match.candidato.idade} anos (${match.candidato.getNivelSenioridade()})
   🔗 Competências: ${match.competenciasComuns.join(', ')}"""
                }

                if (matches.size() > 3) {
                    println "\n   + ${matches.size() - 3} outros candidatos qualificados!"
                }
            }
        } catch (Exception e) {
        }
    }

    private void encerrarSistema() {
        try {
            menuView.exibirLoading("Finalizando sistema", 1000)
            menuView.exibirDespedida()

            if (scanner != null) {
                scanner.close()
            }

        } catch (Exception e) {
            println "Sistema encerrado."
        }
    }

    private void tratarExcecaoGeral(Exception e) {
        menuView.exibirErro("Ops! Algo inesperado aconteceu.")

        if (menuView.solicitarConfirmacao("Deseja ver detalhes do erro?")) {
            println "\n--- DETALHES DO ERRO ---"
            e.printStackTrace()
        }

        menuView.exibirInfo("O sistema continuará funcionando normalmente.")
    }

    boolean verificarSaudeDoSistema() {
        return service.isSistemaFuncionando()
    }

    LinketinderService getService() {
        return service
    }
}