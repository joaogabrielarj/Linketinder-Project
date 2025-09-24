package models

class Candidato extends Pessoa {

    private String cpf
    private int idade

    Candidato(String nome, String email, String cpf, int idade, String estado, String cep, String descricao, List<String> competencias) {
        super(nome, email, descricao, competencias, cep, estado)

        this.cpf = cpf?.trim()
        this.idade = idade

        validarDadosCandidato()
    }

    private void validarDadosCandidato() {
        if (!cpf || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório para candidatos")
        }

        if (!validarFormatoCpf(cpf)) {
            throw new IllegalArgumentException("CPF deve ter formato válido (XXX.XXX.XXX-XX)")
        }

        if (idade < 16) {
            throw new IllegalArgumentException("Candidato deve ter pelo menos 16 anos")
        }

        if (idade > 120) {
            throw new IllegalArgumentException("Idade deve ser válida")
        }
    }

    private boolean validarFormatoCpf(String cpf) {
        String regex = /^\d{3}\.\d{3}\.\d{3}-\d{2}$|^\d{11}$/
        return cpf.matches(regex)
    }

    String getCpf() {
        return cpf
    }

    int getIdade() {
        return idade
    }

    boolean isJunior() {
        return idade <= 25
    }

    boolean isPleno() {
        return idade >= 26 && idade <= 35
    }

    boolean isSenior() {
        return idade > 35
    }

    String getNivelSenioridade() {
        if (isJunior()) return "Júnior"
        if (isPleno()) return "Pleno"
        return "Sênior"
    }

    @Override
    void exibirInformacoes() {
        println """
╔═══════════════════ CANDIDATO ═══════════════════╗
║ Nome: ${nome}
║ Email: ${email}
║ CPF: ${cpf}
║ Idade: ${idade} anos (${getNivelSenioridade()})
║ Estado: ${estado}
║ CEP: ${cep}
║ Descrição: ${descricao}
║ Competências: ${competencias.join(', ')}
╚═══════════════════════════════════════════════════╝"""
    }

    @Override
    String toString() {
        return "Candidato: ${nome} (${idade} anos) - ${email}"
    }
}