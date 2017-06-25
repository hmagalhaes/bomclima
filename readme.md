# Bom Clima
Este é um pequeno exemplo de aplicação web Java usando Weld como provedor CDI.

## Projeto

Na verdade, este projeto foi feito para atender a um trabalho de um curso em Engenharia de Software, onde era necessário atender a alguns requisitos específicos, tais como:

- arquitetura MVC;
- padrão Front Controller;
- padrão Command;
- modelo de dados contendo alguma herança de no mínimo 3 níveis;
- CRUD em banco de dados relacional;
- padrão DAO, incluindo um DAO genérico para abstrair lógicas comuns;
- controle de sessão de usuário (login e logout).

Dado o objetivo educacional da aplicação, optou-se por manter a implementação a mais pura possível, sem a adição de maiores bibliotecas e frameworks externos. A exceção fica por conta do CDI, o que julgou-se menos intromissivo e de grande benefício na gestão dos componentes, principalmente ao evitar práticas como referências estáticas e Singletons, o que poderia levar a uma estrutura com maior acomplamento.

## Persistência

O projeto está configurado para fazer uso de um banco de dados em memória (H2DB), onde a estrutura, os dados hard-coded e alguns dados de exemplo são inicializados durante a inicialização da aplicação.

A implementação busca o desacoplamento da solução de persistência em questão, sendo possível a configuração de alguma solução realmente persistente, tal como MySQL. Possivelmente alguma alteração seja necessária nas consultas SQL, caso alguma função específica tenha sido usada nas consultas (não realizei os testes).

## Utilização

Para rodar a aplicação utilize o plugin do Tomcat para Maven, que já está configurado no ``pom.xmol``. Acesse o diretório raiz do projeto e rode o seguinte comando:
``mvn clean install tomcat7:run``
A aplicação poderá então ser acessada em [http://localhost:8080/bomclima](http://localhost:8080/bomclima) 
