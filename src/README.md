# 🚀 LINKETINDER

## Sistema de Match Profissional

**O futuro do recrutamento chegou!** Sistema inspirado no **Tinder + LinkedIn** que conecta candidatos talentosos com empresas em busca de competências específicas.

---

## 📋 **Sobre o Projeto**

Desenvolvido especialmente para o **Dr. Antônio Paçoca**, empresário visionário e proprietário das empresas:
- 🍚 **Arroz-Gostoso** - Líder no segmento alimentício
- 🎳 **Império do Boliche** - Rede de entretenimento inovadora

### 💰 **Investimento**
- **Pagamento**: 1 Pão de Queijo Premium 🥖
- **Valor estimado**: Inestimável (para quem ama pão de queijo!)

---

## ⚡ **Características do MVP**

### ✅ **Requisitos Obrigatórios Implementados**
- [x] **5+ Candidatos pré-cadastrados** com dados completos
- [x] **5+ Empresas pré-cadastradas** (incluindo as do Dr. Paçoca)
- [x] **Interface IPessoa obrigatória** implementada
- [x] **Classe Pessoa abstrata** como base
- [x] **Sistema de competências** por arrays
- [x] **Menu interativo** no terminal
- [x] **Atributos completos**: Nome, Email, CPF/CNPJ, Idade, Estado, CEP, Descrição

### 🎯 **Funcionalidades Extras**
- [x] **Cadastro dinâmico** de candidatos e empresas
- [x] **Sistema de matches inteligente** com percentuais de compatibilidade
- [x] **Validações robustas** de dados
- [x] **Relatórios e estatísticas** completas
- [x] **Interface visual elegante** com emojis e formatação
- [x] **Tratamento de erros** abrangente

---

## 🏗️ **Arquitetura MVC**

### 📁 **Estrutura do Projeto**
```
linketinder/
├── src/
│   ├── interfaces/
│   │   └── IPessoa.groovy                 # Interface obrigatória
│   ├── models/
│   │   ├── Pessoa.groovy                  # Classe abstrata base
│   │   ├── Candidato.groovy               # Modelo de Pessoa Física
│   │   └── Empresa.groovy                 # Modelo de Pessoa Jurídica
│   ├── services/
│   │   ├── CandidatoService.groovy        # Lógica de negócio - Candidatos
│   │   ├── EmpresaService.groovy          # Lógica de negócio - Empresas
│   │   └── LinketinderService.groovy      # Service principal
│   ├── views/
│   │   ├── MenuView.groovy                # Interface de menus
│   │   ├── CandidatoView.groovy           # Interface de candidatos
│   │   └── EmpresaView.groovy             # Interface de empresas
│   └── controllers/
│       └── LinketinderController.groovy   # Controlador MVC
├── LinketinderApp.groovy                  # Aplicação principal
└── README.md                              # Esta documentação
```

### 🎯 **Padrão MVC Implementado**

#### **📋 Models (Camada de Dados)**
- **`IPessoa`** - Interface que define o contrato obrigatório
- **`Pessoa`** - Classe abstrata que implementa comportamentos comuns
- **`Candidato`** - Pessoa Física com CPF e idade
- **`Empresa`** - Pessoa Jurídica com CNPJ e país

#### **⚙️ Services (Camada de Negócio)**
- **`CandidatoService`** - CRUD e regras de negócio para candidatos
- **`EmpresaService`** - CRUD e regras de negócio para empresas
- **`LinketinderService`** - Orquestração e coordenação geral

#### **👁️ Views (Camada de Apresentação)**
- **`MenuView`** - Menus e interfaces gerais
- **`CandidatoView`** - Interfaces específicas para candidatos
- **`EmpresaView`** - Interfaces específicas para empresas

#### **🎮 Controllers (Camada de Controle)**
- **`LinketinderController`** - Coordena Views e Services

---

## 🚀 **Como Executar**

### **Pré-requisitos**
- Groovy 3.0+ instalado
- JVM 8+ configurada
- Terminal com suporte a UTF-8

### **Execução**
```bash
# Método simples
groovy LinketinderApp.groovy

# Com debug habilitado
groovy LinketinderApp.groovy --debug

# Ver versão
groovy LinketinderApp.groovy --version

# Ver ajuda
groovy LinketinderApp.groovy --help
```

---

## 💡 **Funcionalidades Detalhadas**

### 🔍 **Menu Principal**
1. **Listar Candidatos** - Visualiza todos os candidatos com estatísticas
2. **Listar Empresas** - Visualiza todas as empresas (destaque para as do Dr. Paçoca)
3. **Cadastrar Candidato** - Cadastro completo com validações
4. **Cadastrar Empresa** - Cadastro corporativo com validações
5. **Buscar Matches** - Algoritmo inteligente de compatibilidade
6. **Relatório do Sistema** - Estatísticas completas e insights

### 🎯 **Sistema de Matches**
- **30% de compatibilidade** - Matches básicos
- **50% de compatibilidade** - Matches qualificados
- **70% de compatibilidade** - Matches premium
- **Busca personalizada** - Define seu próprio percentual

### 📊 **Relatórios e Estatísticas**
- Distribuição por senioridade (Júnior/Pleno/Sênior)
- Competências mais procuradas
- Distribuição geográfica
- Idade média dos candidatos
- Empresas nacionais vs internacionais

---

## 🛠️ **Boas Práticas Implementadas**

### **🔒 Segurança e Validações**
- [x] **Validação de Email** - Formato obrigatório com @ e .
- [x] **Validação de CPF** - Formato XXX.XXX.XXX-XX ou 11 dígitos
- [x] **Validação de CNPJ** - Formato XX.XXX.XXX/XXXX-XX ou 14 dígitos
- [x] **Validação de CEP** - Formato XXXXX-XXX ou 8 dígitos
- [x] **Validação de Idade** - Entre 16 e 120 anos
- [x] **Prevenção de Duplicatas** - Email único por pessoa

---

## 📈 **Dados Pré-cadastrados**

### 👨‍💼 **Candidatos (5+)**
1. **João Silva** - Full Stack Developer (28 anos, SP)
2. **Maria Santos** - Data Science Specialist (32 anos, RJ)
3. **Pedro Costa** - Frontend Developer (25 anos, MG)
4. **Ana Oliveira** - DevOps Engineer (29 anos, PR)
5. **Carlos Ferreira** - Software Architect (35 anos, RS)

### 🏢 **Empresas (5+)**
1. **Arroz-Gostoso** 🍚 - Empresa do Dr. Paçoca (Alimentício)
2. **Império do Boliche** 🎳 - Empresa do Dr. Paçoca (Entretenimento)
3. **TechNova Solutions** - Consultoria em Transformação Digital
4. **CloudFirst Technologies** - Especialista em Cloud e DevOps
5. **DataMind Analytics** - IA e Machine Learning

---

## 🎯 **Competências Técnicas**

### **💻 Linguagens**
`Java`, `Python`, `JavaScript`, `C#`, `PHP`, `Go`, `Kotlin`, `Swift`

### **🚀 Frameworks**
`Spring Framework`, `React`, `Angular`, `Vue`, `Django`, `Laravel`, `Node.js`

### **🗄️ Bancos de Dados**
`MySQL`, `PostgreSQL`, `MongoDB`, `Oracle`, `Redis`, `Elasticsearch`

### **☁️ Cloud & DevOps**
`AWS`, `Azure`, `Google Cloud`, `Docker`, `Kubernetes`, `CI/CD`, `Jenkins`

### **📋 Metodologias**
`Scrum`, `Agile`, `Kanban`, `TDD`, `Clean Code`, `Microservices`

---

## 🎮 **Interface do Usuário**

### **🎨 Características Visuais**
- ✨ Interface colorida com emojis
- 📊 Bordas elegantes em ASCII Art
- 🎯 Loading animado para operações
- ⚡ Feedback visual imediato
- 🔍 Estatísticas em tempo real

### **🎪 Exemplo de Output**
```
╔═══════════════════ CANDIDATO ═══════════════════╗
║ Nome: João Silva
║ Email: joao.silva@email.com
║ CPF: 123.456.789-10
║ Idade: 28 anos (Pleno)
║ Estado: São Paulo
║ CEP: 01310-100
║ Descrição: Desenvolvedor Full Stack com 5 anos de experiência
║ Competências: Java, Spring Framework, JavaScript, React, MySQL
╚═══════════════════════════════════════════════════╝
```

---

## 🧪 **Como Testar**

### **📝 Cenários de Teste**
1. **Cadastro de Candidato**
    - Teste com dados válidos
    - Teste com email duplicado
    - Teste com CPF inválido
    - Teste com idade fora do range

2. **Cadastro de Empresa**
    - Teste com dados válidos
    - Teste com CNPJ inválido
    - Teste com email duplicado

3. **Sistema de Matches**
    - Teste com diferentes percentuais
    - Verificar ordenação por compatibilidade
    - Validar cálculo de matches


---

## 📞 **Suporte**

### **🆘 Problemas Conhecidos**
- Sistema requer terminal UTF-8 para emojis
- Performance pode variar com muitos dados
- Validações de CPF/CNPJ são apenas de formato

### **💡 Dicas de Uso**
- Mantenha dados de entrada consistentes
- Terminal em tela cheia para melhor experiência
- Use Ctrl+C para interromper quando necessário

---

## 🎉 **Considerações Finais**

O **Linketinder MVP** foi desenvolvido seguindo rigorosamente todos os requisitos obrigatórios e implementando funcionalidades extras que agregam valor ao produto final.

### **🚀 Próximos Passos**

**Desenvolvido com ❤️ e muito ☕ para revolucionar o mundo do recrutamento!**

*"Conectando talentos às oportunidades ideais, um match por vez."*