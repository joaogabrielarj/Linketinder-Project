package services

class LinketinderService {

    private CandidatoService candidatoService
    private EmpresaService empresaService
    private boolean sistemaInicializado = false

    LinketinderService() {
        this.candidatoService = new CandidatoService()
        this.empresaService = new EmpresaService()
    }

    void inicializarSistema() {
        try {
            if (sistemaInicializado) {
                throw new IllegalStateException("Sistema já foi inicializado")
            }

            candidatoService.inicializarCandidatos()
            empresaService.inicializarEmpresas()

            validarRequisitosDoSistema()

            sistemaInicializado = true

        } catch (Exception e) {
            throw new RuntimeException("Falha ao inicializar o sistema Linketinder: ${e.message}", e)
        }
    }

    private void validarRequisitosDoSistema() {
        List<String> erros = []

        if (!candidatoService.validarRequisitosMinimos()) {
            erros.add("Sistema deve ter no mínimo 5 candidatos pré-cadastrados")
        }

        if (!empresaService.validarRequisitosMinimos()) {
            erros.add("Sistema deve ter no mínimo 5 empresas pré-cadastradas")
        }

        List empresasDrPacoca = empresaService.buscarEmpresasDrPacoca()
        if (empresasDrPacoca.size() < 2) {
            erros.add("Sistema deve incluir as empresas do Dr. Antônio Paçoca: Arroz-Gostoso e Império do Boliche")
        }

        if (!erros.isEmpty()) {
            throw new IllegalStateException("Requisitos não atendidos:\n- " + erros.join("\n- "))
        }
    }

    CandidatoService getCandidatoService() {
        validarSistemaInicializado()
        return candidatoService
    }

    EmpresaService getEmpresaService() {
        validarSistemaInicializado()
        return empresaService
    }


    private void validarSistemaInicializado() {
        if (!sistemaInicializado) {
            throw new IllegalStateException("Sistema não foi inicializado. Chame inicializarSistema() primeiro")
        }
    }

    Map<String, Object> getEstatisticasCompletas() {
        validarSistemaInicializado()

        Map<String, Object> estatisticasCandidatos = candidatoService.getEstatisticas()
        Map<String, Object> estatisticasEmpresas = empresaService.getEstatisticas()

        return [
                'sistemaInicializado': sistemaInicializado,
                'versao': '1.0',
                'proprietario': 'Dr. Antônio Paçoca',
                'candidatos': estatisticasCandidatos,
                'empresas': estatisticasEmpresas,
                'totalUsuarios': estatisticasCandidatos.total + estatisticasEmpresas.total,
                'dataInicializacao': new Date().toString()
        ]
    }

    Map<String, Integer> getEstatisticas() {
        validarSistemaInicializado()

        return [
                'candidatos': candidatoService.getTotalCandidatos(),
                'empresas': empresaService.getTotalEmpresas()
        ]
    }

    List<Map> executarBuscaMatches(double percentualMinimo = 0.3) {
        validarSistemaInicializado()

        List<Map> todosMatches = []

        candidatoService.listarTodos().each { candidato ->
            List<Map> matchesEmpresa = empresaService.encontrarEmpresasCompativeis(candidato, percentualMinimo)

            matchesEmpresa.each { match ->
                todosMatches.add([
                        'candidato': candidato,
                        'empresa': match.empresa,
                        'percentualMatch': match.percentualMatch,
                        'competenciasComuns': match.competenciasComuns
                ])
            }
        }

        return todosMatches.sort { -it.percentualMatch }
    }

    String gerarRelatorioCompleto() {
        validarSistemaInicializado()

        Map<String, Object> stats = getEstatisticasCompletas()

        return """
╔══════════════════════════════════════════════════════╗
║                 RELATÓRIO LINKETINDER                ║
╠══════════════════════════════════════════════════════╣
║ Proprietário: ${stats.proprietario}
║ Versão: ${stats.versao}
║ Status: ${stats.sistemaInicializado ? 'Ativo' : 'Inativo'}
║ 
║ 📊 ESTATÍSTICAS GERAIS:
║ • Total de Usuários: ${stats.totalUsuarios}
║ • Candidatos: ${stats.candidatos.total}
║ • Empresas: ${stats.empresas.total}
║
║ 👨‍💼 CANDIDATOS:
║ • Júniors: ${stats.candidatos.juniors}
║ • Plenos: ${stats.candidatos.plenos}
║ • Sêniors: ${stats.candidatos.seniors}
║ • Idade Média: ${String.format('%.1f', stats.candidatos.idadeMedia)} anos
║
║ 🏢 EMPRESAS:
║ • Nacionais: ${stats.empresas.nacionais}
║ • Internacionais: ${stats.empresas.internacionais}
║
║ 🚀 COMPETÊNCIAS MAIS PROCURADAS:
${formatarCompetencias(stats.empresas.competenciasMapMaisDesejadas)}
║
╚══════════════════════════════════════════════════════╝
"""
    }

    private String formatarCompetencias(Map<String, Integer> competencias) {
        StringBuilder sb = new StringBuilder()
        competencias.take(5).eachWithIndex { entry, index ->
            sb.append("║ ${index + 1}. ${entry.key}: ${entry.value} empresas\n")
        }
        return sb.toString().trim()
    }

    void reiniciarSistema() {
        sistemaInicializado = false
        candidatoService = new CandidatoService()
        empresaService = new EmpresaService()
    }

    Map<String, Object> verificarSaudeDoSistema() {
        return [
                'sistemaInicializado': sistemaInicializado,
                'candidatosCarregados': sistemaInicializado ? candidatoService.getTotalCandidatos() > 0 : false,
                'empresasCarregadas': sistemaInicializado ? empresaService.getTotalEmpresas() > 0 : false,
                'requisitosAtendidos': sistemaInicializado ?
                        candidatoService.validarRequisitosMinimos() && empresaService.validarRequisitosMinimos() : false,
                'timestamp': new Date()
        ]
    }

    boolean isSistemaFuncionando() {
        Map<String, Object> saude = verificarSaudeDoSistema()
        return saude.sistemaInicializado && saude.candidatosCarregados &&
                saude.empresasCarregadas && saude.requisitosAtendidos
    }
}