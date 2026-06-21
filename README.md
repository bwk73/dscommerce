# DSCommerce — Backend

Demo project for Spring Boot. Sistema de e-commerce com autenticação e autorização, desenvolvido durante o curso da escola [DevSuperior](https://github.com/devsuperior), ministrado pelo professor [Nélio Alves](https://github.com/nelioalves) (UFU).

> Este repositório contém o **backend** do sistema. O frontend está disponível em: [dscommerce-frontend](https://github.com/bwk73/dscommerce-frontend)

## Sobre o projeto

O DSCommerce é uma aplicação de e-commerce completa, com catálogo de produtos por categoria, carrinho/pedidos, pagamento e dois perfis de acesso (cliente e administrador). Visitantes podem navegar pelo catálogo livremente; criar, alterar ou cancelar pedidos exige autenticação. Administradores, além de tudo que o cliente pode fazer, têm acesso a gestão de produtos e acompanhamento de pedidos e pagamentos.

### Modelo de domínio

- **Category** (`tb_category`) — `id`, `name`
- **Product** (`tb_product`) — `id`, `name`, `price`, `description`, `img_url`
- **ProductCategory** (`tb_product_category`) — `product_id`, `category_id` (relação muitos-para-muitos entre produto e categoria)
- **User** (`tb_user`) — `id`, `name`, `email`, `phone`, `password`, `birth_date`
- **Role** (`tb_role`) — `authority` (`ROLE_CLIENT` ou `ROLE_ADMIN`)
- **UserRole** (`tb_user_role`) — `user_id`, `role_id` (relação muitos-para-muitos entre usuário e perfil)
- **Order** (`tb_order`) — `id`, `moment`, `status` (enum `OrderStatus`, mapeado por valor ordinal — sem tabela própria no banco), `client_id`
- **OrderItem** (`tb_order_item`) — `order_id`, `product_id`, `quantity`, `price`
- **Payment** (`tb_payment`) — `order_id`, `moment`

## Autenticação e autorização

O backend implementa OAuth2 com tokens JWT para autenticação e controle de acesso baseado em papéis (`ROLE_CLIENT` / `ROLE_ADMIN`):

- `AuthorizationServerConfig` — configuração do servidor de autorização OAuth2.
- `ResourceServerConfig` — configuração do servidor de recursos e CORS.
- `WebSecurityConfig` — configuração de segurança, incluindo `BCryptPasswordEncoder` (criptografia de senha), `JwtAccessTokenConverter` e `JwtTokenStore` (geração/leitura de tokens JWT) e `AuthenticationManager`.

**Regras de acesso:**
- Catálogo de produtos e categorias: acesso público (sem autenticação).
- Criar, alterar ou excluir pedidos: requer usuário autenticado (`ROLE_CLIENT` ou superior).
- Gestão de produtos (inserir, alterar, excluir) e acompanhamento de pedidos/pagamentos: restrito a `ROLE_ADMIN`.

## Arquitetura

O backend segue a arquitetura em camadas padrão dos projetos da DevSuperior:

- **Repository** — acesso a dados via Spring Data JPA.
- **Service** — regras de negócio (gestão de produtos, pedidos, pagamentos, controle de acesso).
- **Controller** — endpoints REST, comunicação via DTO em JSON.
- **Config** — configuração de segurança, OAuth2/JWT e CORS.

```
Frontend → Controller (REST/JSON) → Service → Repository (JPA) → Banco de dados
                  ↑
       Config (OAuth2 + JWT + CORS)
```

## Validação de dados

O backend utiliza o `spring-boot-starter-validation` para validar os dados recebidos via DTO antes da persistência.

## Tecnologias

- Java
- Spring Boot
- Spring Data JPA
- Spring Security + OAuth2 (`spring-security-oauth2-autoconfigure`)
- Spring Validation
- Spring Cloud Dependencies
- Maven
- H2 (banco em memória, ambiente de desenvolvimento)

## Conceitos aplicados

- Arquitetura em camadas (Controller / Service / Repository)
- Padrão DTO
- Autenticação OAuth2 com JWT
- Controle de acesso baseado em papéis (role-based access control)
- Criptografia de senha (BCrypt)
- Modelagem de enum sem tabela dedicada (`OrderStatus`)
- Relacionamentos muitos-para-muitos (Product/Category, User/Role)
- Validação de dados de entrada
- Configuração de CORS

## Como executar

```bash
./mvnw spring-boot:run
```

> O projeto usa o banco H2 em memória por padrão para desenvolvimento local — não é necessário configurar um banco externo para rodar localmente.

## Contexto

Este projeto foi desenvolvido durante minha jornada de transição de carreira da fisioterapia para o desenvolvimento de software, como parte da formação prática oferecida pela DevSuperior.
