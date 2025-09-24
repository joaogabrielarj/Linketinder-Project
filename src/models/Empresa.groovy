package models

class Empresa extends Pessoa {

    private String cnpj
    private String pais

    Empresa(String nome, String email, String cnpj, String pais, String estado, String cep, String descricao, List<String> competencias) {
        super(nome, email, descricao, competencias, cep, estado)

        this.cnpj = cnpj?.trim()
        this.pais = pais?.trim()

        validarDadosEmpresa()
    }

    private void validarDadosEmpresa() {
        if (!cnpj || cnpj.isEmpty()) {
            throw new IllegalArgumentException("CNPJ é obrigatório para empresas")
        }

        if (!validarFormatoCnpj(cnpj)) {
            throw new IllegalArgumentException("CNPJ deve ter formato válido (XX.XXX.XXX/XXXX-XX)")
        }

        if (!pais || pais.isEmpty()) {
            throw new IllegalArgumentException("País é obrigatório para empresas")
        }
    }

    private boolean validarFormatoCnpj(String cnpj) {
        String regex = /^\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}$|^\d{14}$/
        return cnpj.matches(regex)
    }

    String getCnpj() {
        return cnpj
    }

    String getPais() {
        return pais
    }

    boolean isNacional() {
        return pais?.toLowerCase() == "brasil"
    }

    boolean procuraCompetencia(String competencia) {
        return possuiCompetencia(competencia)
    }

    double calcularMatchComCandidato(Candidato candidato) {
        if (!candidato || competencias.isEmpty()) {
            return 0.0
        }

        List<String> competenciasCandidato = candidato.getCompetencias()
        int matchCount = 0

        for (String competencia : competencias) {
            if (competenciasCandidato.contains(competencia)) {
                matchCount++
            }
        }

        return (double) matchCount / competencias.size()
    }

    String getEnderecoCompleto() {
        return "${estado}, ${cep} - ${pais}"
    }

    @Override
    void exibirInformacoes() {
        String tipoEmpresa = isNacional() ? "🇧🇷 Nacional" : "🌍 Internacional"

        println """
╔═══════════════════ EMPRESA ═══════════════════╗
║ Nome: ${nome} (${tipoEmpresa})
║ Email Corporativo: ${email}
║ CNPJ: ${cnpj}
║ Localização: ${getEnderecoCompleto()}
║ Descrição: ${descricao}
║ Competências Desejadas: ${competencias.join(', ')}
╚═════════════════════════════════════════════════╝"""
    }

    @Override
    String toString() {
        return "Empresa: ${nome} (${pais}) - ${email}"
    }
}