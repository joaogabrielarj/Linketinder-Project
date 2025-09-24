package services

import models.Candidato

class CandidatoService {

    private List<Candidato> candidatos

    CandidatoService() {
        this.candidatos = []
    }

    void inicializarCandidatos() {
        try {
            candidatos.addAll([
                    new Candidato(
                            "João Silva",
                            "joao.silva@email.com",
                            "123.456.789-10",
                            28,
                            "São Paulo",
                            "01310-100",
                            "Desenvolvedor Full Stack com 5 anos de experiência em projetos web",
                            ['Java', 'Spring Framework', 'JavaScript', 'React', 'MySQL']
                    ),
                    new Candidato(
                            "Maria Santos",
                            "maria.santos@email.com",
                            "987.654.321-00",
                            32,
                            "Rio de Janeiro",
                            "20040-020",
                            "Especialista em Python e Data Science com mestrado em IA",
                            ['Python', 'MongoDB', 'Docker', 'AWS', 'TDD']
                    ),
                    new Candidato(
                            "Pedro Costa",
                            "pedro.costa@email.com",
                            "456.789.123-45",
                            25,
                            "Minas Gerais",
                            "30112-000",
                            "Desenvolvedor Frontend apaixonado por UX/UI e tecnologias modernas",
                            ['JavaScript', 'Angular', 'React', 'Node.js', 'Git']
                    ),
                    new Candidato(
                            "Ana Oliveira",
                            "ana.oliveira@email.com",
                            "321.654.987-12",
                            29,
                            "Paraná",
                            "80010-000",
                            "DevOps Engineer com expertise em Cloud Computing e automação",
                            ['Docker', 'Kubernetes', 'AWS', 'Azure', 'Python']
                    ),
                    new Candidato(
                            "Carlos Ferreira",
                            "carlos.ferreira@email.com",
                            "789.123.456-78",
                            35,
                            "Rio Grande do Sul",
                            "90010-150",
                            "Arquiteto de Software e líder técnico com 12 anos de experiência",
                            ['Java', 'Microservices', 'REST API', 'Scrum', 'PostgreSQL']
                    )
            ])
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inicializar candidatos pré-cadastrados: ${e.message}", e)
        }
    }

    List<Candidato> listarTodos() {
        return new ArrayList<>(candidatos)
    }

    void adicionarCandidato(Candidato candidato) {
        if (!candidato) {
            throw new IllegalArgumentException("Candidato não pode ser null")
        }

        if (candidatoExiste(candidato.getEmail())) {
            throw new IllegalArgumentException("Já existe um candidato com o email: ${candidato.getEmail()}")
        }

        candidatos.add(candidato)
    }

    boolean candidatoExiste(String email) {
        if (!email) return false
        return candidatos.any { it.email.toLowerCase() == email.toLowerCase() }
    }

    Candidato buscarPorEmail(String email) {
        if (!email) return null
        return candidatos.find { it.email.toLowerCase() == email.toLowerCase() }
    }

    List<Candidato> buscarPorCompetencia(String competencia) {
        if (!competencia) return []
        return candidatos.findAll { it.possuiCompetencia(competencia) }
    }

    List<Candidato> filtrarPorIdade(int idadeMin, int idadeMax) {
        return candidatos.findAll { it.idade >= idadeMin && it.idade <= idadeMax }
    }

    List<Candidato> filtrarPorEstado(String estado) {
        if (!estado) return []
        return candidatos.findAll { it.estado.toLowerCase().contains(estado.toLowerCase()) }
    }

    List<Candidato> listarJuniors() {
        return candidatos.findAll { it.isJunior() }
    }

    List<Candidato> listarPlenos() {
        return candidatos.findAll { it.isPleno() }
    }

    List<Candidato> listarSeniors() {
        return candidatos.findAll { it.isSenior() }
    }

    boolean removerCandidato(String email) {
        if (!email) return false
        return candidatos.removeIf { it.email.toLowerCase() == email.toLowerCase() }
    }

    boolean atualizarCandidato(String email, Candidato candidatoAtualizado) {
        if (!email || !candidatoAtualizado) return false

        int index = candidatos.findIndexOf { it.email.toLowerCase() == email.toLowerCase() }
        if (index >= 0) {
            candidatos[index] = candidatoAtualizado
            return true
        }
        return false
    }

    int getTotalCandidatos() {
        return candidatos.size()
    }

    Map<String, Object> getEstatisticas() {
        return [
                'total': getTotalCandidatos(),
                'juniors': listarJuniors().size(),
                'plenos': listarPlenos().size(),
                'seniors': listarSeniors().size(),
                'idadeMedia': candidatos.isEmpty() ? 0 : candidatos.sum { it.idade } / candidatos.size(),
                'competenciasMaisComuns': getCompetenciasMaisComuns()
        ]
    }

    private Map<String, Integer> getCompetenciasMaisComuns() {
        Map<String, Integer> contadorCompetencias = [:]

        candidatos.each { candidato ->
            candidato.competencias.each { competencia ->
                contadorCompetencias[competencia] = (contadorCompetencias[competencia] ?: 0) + 1
            }
        }

        return contadorCompetencias.sort { -it.value }.take(10)
    }

    boolean validarRequisitosMinimos() {
        return candidatos.size() >= 5
    }
}