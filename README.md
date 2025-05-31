# 🏗️ Aura Projetos e Construções

**Aplicação desenvolvida como Trabalho de Conclusão de Curso (TCC) para o curso de Análise e Desenvolvimento de Sistemas.**  
O sistema tem como objetivo auxiliar na **gestão de projetos e obras**, oferecendo funcionalidades como cadastro de clientes, geração de orçamentos, cronogramas, contratos, e controle da execução da obra.

---

## 📌 Funcionalidades

- Cadastro de **clientes**, **executores** e **visitas técnicas**
- Criação e gestão de **orçamentos com ou sem materiais**
- Organização de **itens de orçamento** com valor unitário e quantidade
- Vínculo com **subtipos de orçamento** (Projeto, Reforma, Drywall, Acabamento)
- Geração de **cronogramas**
- Registro de **diário de obra**
- Integração entre cliente, mestre de obras e equipe auxiliar

---

## 🧱 Tecnologias utilizadas

| Camada | Tecnologia |
|--------|------------|
| Backend | Java 21 + Spring Boot 3.4.4 |
| ORM | Spring Data JPA + Hibernate |
| Banco de Dados | PostgreSQL ou H2 para testes |
| Gerenciamento de dependências | Maven |
| IDE recomendada | IntelliJ IDEA |
| Lombok | Redução de boilerplate |

---

## 🛠️ Como rodar o projeto localmente

1. Clone o repositório:

```bash
git clone https://github.com/Flimau/tcc_auraprojetoseconstrucoes.git
cd tcc_auraprojetoseconstrucoes
```

2. Configure o banco no `application.properties` (H2 para testes ou PostgreSQL):

```properties
# Para usar banco em memória H2
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```

3. Rode a aplicação:

```bash
./mvnw spring-boot:run
```

4. Acesse via navegador:

```
http://localhost:8080
```

---

## 🗃️ Estrutura de pacotes

```
src
└── main
    └── java
        └── com.tccfer.application
            ├── controller
            ├── model.entity
            │   ├── pessoa
            │   ├── orcamento
            │   ├── visitas
            ├── repository
            ├── service
            └── Application.java
```

---

## 👩‍💻 Desenvolvedora

Feito com 💛 por **Fernanda Lima Ulrich**  
[LinkedIn](https://www.linkedin.com/in/fernanda-ulrich/) | [GitHub](https://github.com/Flimau)

---

## 📚 Licença

Este projeto é parte de um TCC acadêmico e seu uso é livre para fins educacionais.
