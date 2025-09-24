package services

import models.Empresa
import models.Candidato

class EmpresaService {

    private List<Empresa> empresas

    EmpresaService() {
        this.empresas = []
    }

    void inicializarEmpresas() {
        try {
            empresas.addAll([
                    new Empresa(
                            "Arroz-Gostoso",
                            "rh@arrozgostoso.com.br",
                            "12.345.678/0001-90",
                            "Brasil",
                            "São Paulo",
                            "01310-100",
                            "Empresa líder no segmento alimentício, focada em inovação tecnológica e sustentabilidade",
                            ['Java', 'Spring Framework', 'MySQL', 'AWS', 'Scrum']
                    ),
                    new Empresa(
                            "Império do Boliche",
                            "contato@imperiodoboliche.com.br",
                            "98.765.432/0001-10",
                            "Brasil",
                            "Rio de Janeiro",
                            "20040-020",
                            "Rede de entretenimento que revoluciona a experiência do boliche com tecnologia de ponta",
                            ['JavaScript', 'React', 'Node.js', 'MongoDB', 'Docker']
                    ),
                    new Empresa(
                            "TechNova Solutions",
                            "careers@technova.com",
                            "11.222.333/0001-44",
                            "Brasil",
                            "Minas Gerais",
                            "30112-000",
                            "Consultoria especializada em transformação digital e desenvolvimento de software sob medida",
                            ['Python', 'Angular', 'PostgreSQL', 'Microservices', 'TDD']
                    ),
                    new Empresa(
                            "CloudFirst Technologies",
                            "jobs@cloudfirst.tech",
                            "55.666.777/0001-88",
                            "Brasil",
                            "Paraná",
                            "80010-000",
                            "Empresa pioneira em soluções de nuvem e DevOps para transformação empresarial",
                            ['Docker', 'Kubernetes', 'AWS', 'Azure', 'Python']
                    ),
                    new Empresa(
                            "DataMind Analytics",
                            "talent@datamind.com.br",
                            "33.444.555/0001-22",
                            "Brasil",
                            "Rio Grande do Sul",
                            "90010-150",
                            "Líder em análise de dados, inteligência artificial e machine learning para negócios",
                            ['Python', 'REST API', 'MongoDB', 'Git', 'Agile']
                    )
            ])
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inicializar empresas pré-cadastradas: ${e.message}", e)
        }
    }

    List<Empresa> listarTodas() {
        return new ArrayList<>(empresas)
    }

    void adicionarEmpresa(Empresa empresa) {
        if (!empresa) {
            throw new IllegalArgumentException("Empresa não pode ser null")
        }

        if (empresaExiste(empresa.getEmail())) {
            throw new IllegalArgumentException("Já existe uma empresa com o email: ${empresa.getEmail()}")
        }

        empresas.add(empresa)
    }

    boolean empresaExiste(String email) {
        if (!email) return false
        return empresas.any { it.email.toLowerCase() == email.toLowerCase() }
    }

    Empresa buscarPorEmail(String email) {
        if (!email) return null
        return empresas.find { it.email.toLowerCase() == email.toLowerCase() }
    }

    List<Empresa> buscarPorCompetencia(String competencia) {
        if (!competencia) return []
        return empresas.findAll { it.procuraCompetencia(competencia) }
    }

    List<Empresa> filtrarPorPais(String pais) {
        if (!pais) return []
        return empresas.findAll { it.pais.toLowerCase().contains(pais.toLowerCase()) }
    }

    List<Empresa> filtrarPorEstado(String estado) {
        if (!estado) return []
        return empresas.findAll { it.estado.toLowerCase().contains(estado.toLowerCase()) }
    }

    List<Empresa> listarNacionais() {
        return empresas.findAll { it.isNacional() }
    }

    List<Empresa> listarInternacionais() {
        return empresas.findAll { !it.isNacional() }
    }

    List<Map> encontrarEmpresasCompativeis(Candidato candidato, double percentualMinimo = 0.3) {
        if (!candidato) return []

        List<Map> matches = []

        empresas.each { empresa ->
            double percentualMatch = empresa.calcularMatchComCandidato(candidato)
            if (percentualMatch >= percentualMinimo) {
                matches.add([
                        'empresa': empresa,
                        'percentualMatch': percentualMatch,
                        'competenciasComuns': encontrarCompetenciasComuns(empresa, candidato)
                ])
            }
        }


        return matches.sort { -it.percentualMatch }
    }

    private List<String> encontrarCompetenciasComuns(Empresa empresa, Candidato candidato) {
        List<String> competenciasEmpresa = empresa.getCompetencias()
        List<String> competenciasCandidato = candidato.getCompetencias()

        return competenciasEmpresa.findAll { competenciasCandidato.contains(it) }
    }

    boolean removerEmpresa(String email) {
        if (!email) return false
        return empresas.removeIf { it.email.toLowerCase() == email.toLowerCase() }
    }

    boolean atualizarEmpresa(String email, Empresa empresaAtualizada) {
        if (!email || !empresaAtualizada) return false

        int index = empresas.findIndexOf { it.email.toLowerCase() == email.toLowerCase() }
        if (index >= 0) {
            empresas[index] = empresaAtualizada
            return true
        }
        return false
    }

    int getTotalEmpresas() {
        return empresas.size()
    }

    Map<String, Object> getEstatisticas() {
        return [
                'total': getTotalEmpresas(),
                'nacionais': listarNacionais().size(),
                'internacionais': listarInternacionais().size(),
                'competenciasMapMaisDesejadas': getCompetenciasMaisDesejadas(),
                'estadosComMaisEmpresas': getEstadosComMaisEmpresas()
        ]
    }

    private Map<String, Integer> getCompetenciasMaisDesejadas() {
        Map<String, Integer> contadorCompetencias = [:]

        empresas.each { empresa ->
            empresa.competencias.each { competencia ->
                contadorCompetencias[competencia] = (contadorCompetencias[competencia] ?: 0) + 1
            }
        }

        return contadorCompetencias.sort { -it.value }.take(10)
    }

    private Map<String, Integer> getEstadosComMaisEmpresas() {
        Map<String, Integer> contadorEstados = [:]

        empresas.each { empresa ->
            String estado = empresa.estado
            contadorEstados[estado] = (contadorEstados[estado] ?: 0) + 1
        }

        return contadorEstados.sort { -it.value }
    }

    boolean validarRequisitosMinimos() {
        return empresas.size() >= 5
    }

    List<Empresa> buscarEmpresasDrPacoca() {
        return empresas.findAll {
            it.nome in ['Arroz-Gostoso', 'Império do Boliche']
        }
    }
}