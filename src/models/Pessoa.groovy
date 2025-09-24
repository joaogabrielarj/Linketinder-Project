package models

import interfaces.IPessoa

abstract class Pessoa implements IPessoa {

    protected String nome
    protected String email
    protected String descricao
    protected List<String> competencias
    protected String cep
    protected String estado

    Pessoa(String nome, String email, String descricao, List<String> competencias, String cep, String estado) {
        this.nome = nome?.trim()
        this.email = email?.trim()?.toLowerCase()
        this.descricao = descricao?.trim()
        this.competencias = competencias ?: []
        this.cep = cep?.trim()
        this.estado = estado?.trim()

        validarDados()
    }

    private void validarDados() {
        if (!nome || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório")
        }

        if (!email || email.isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório")
        }

        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email deve ter formato válido")
        }

        if (!cep || cep.isEmpty()) {
            throw new IllegalArgumentException("CEP é obrigatório")
        }

        if (!estado || estado.isEmpty()) {
            throw new IllegalArgumentException("Estado é obrigatório")
        }
    }

    @Override
    String getNome() {
        return nome
    }

    @Override
    String getEmail() {
        return email
    }

    @Override
    String getDescricao() {
        return descricao
    }

    @Override
    List<String> getCompetencias() {
        return new ArrayList<>(competencias)
    }

    String getCep() {
        return cep
    }

    String getEstado() {
        return estado
    }

    void adicionarCompetencia(String competencia) {
        if (competencia && !competencias.contains(competencia.trim())) {
            competencias.add(competencia.trim())
        }
    }

    void removerCompetencia(String competencia) {
        competencias.remove(competencia)
    }

    boolean possuiCompetencia(String competencia) {
        return competencias.contains(competencia)
    }

    @Override
    String toString() {
        return "${this.class.simpleName}: ${nome} (${email})"
    }

    @Override
    boolean equals(Object obj) {
        if (this.is(obj)) return true
        if (obj == null || getClass() != obj.getClass()) return false

        Pessoa pessoa = (Pessoa) obj
        return email?.equals(pessoa.email)
    }

    @Override
    int hashCode() {
        return email?.hashCode() ?: 0
    }
}