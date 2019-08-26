# JAVA API RESTful + Jersey + Mongodb

API RESTful em java utilizando o framework Jersey e o banco de dados Mongodb, um banco de dados
orientado a documentos que servirá para armazenar os usuários e os tokens.

### Funcionamento

O usuário primeiramente efetua o cadastro, informando seu nome, email e senha. Após o cadastro,
o usuário deverá se autenticar para começar a consumir os recursos. Durante a autenticação, um token
será gerado e deverá ser adicionado ao cabeçalho de cada requisição efetuada posteriormente.

Existem 3 rotas:

- http://IP_HOST:PORT_HOST/java-api-restful/api/auth/register

Rota para efetuar o cadastro. Um objeto JSON é aguardado contendo o nome, email e senha do usuário.

Request:
```json
{
    "name": "Leonardo Barbosa",
    "email": "leonardo@gmail.com",
    "password": "d7sadvajsasd"
}
```

Response:
```json
{
    "name": "Leonardo Barbosa",
    "email": "leonardo@gmail.com",
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTU2NjkzNjM3MX0.xInVrNJ-QgJS0_tMsN5xN4nNfdv8p-bSu5_yPsZrv6g"
}
```

- http://IP_HOST:PORT_HOST/java-api-restful/api/auth/authenticate

Rota para autenticação. Um objeto JSON é aguardado contendo o email e senha do usuário. Se existir um
usuário com essas credenciais, um token será gerado e retornado ao usuário. Esse token será utilizado
para consumir os recursos da API.

Request:
```json
{
    "email": "linndsei@gmail.com",
    "password": "1q2w3e4r5t6y"
}
```

Response:
```json
{
    "name": "Nathalia Lindsei",
    "email": "linndsei@gmail.com",
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTU2NjkzNjQwMn0.KEYI1ShO_AscaFdDriBsjQD4hvFEp2Fdw97Pt0--Pxo"
}
```

- http://IP_HOST:PORT_HOST/java-api-restful/api/users

Recurso disponível somente para um usuário autenticado e com um token válido.O token deve ser informado no
cabeçalho da requisição. Se o token for válido, uma lista com os usuários cadastrados será retornado.

Request cURL:
```
curl -X GET \
  http://<ip>:<port>/java-api-restful/api/users \
  -H 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTU2NjkzNjQwMn0.KEYI1ShO_AscaFdDriBsjQD4hvFEp2Fdw97Pt0--Pxo'
```

Response:
```json
[
    {
        "name": "Leonardo Barbosa",
        "email": "leobar1995@gmail.com",
        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTU2NjkzMTU5Mn0.HGwNnk5K-oKUTOT9_auHxUrtHte-NTdtWKiJf97rZhc"
    },
    {
        "name": "Nathalia Lindsei",
        "email": "linndsei@gmail.com",
        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTU2NjkzNjQwMn0.KEYI1ShO_AscaFdDriBsjQD4hvFEp2Fdw97Pt0--Pxo"
    }
]
```

### Dependências

- **Jersey:** Framework para construção da API RESTful
- **Mongodb:** Biblioteca para manipulação de coleções (consulta, inserção, atualização e remoção de dados) no Mongodb
- **JWT (JSON Web Token):** Biblioteca utilizada para gerar, decodificar e verificar tokens.
- **Apache Commons Codec:** Biblioteca utilizada para gerar um Hash a partir da senha do usuário.
- **Jackson:** Biblioteca utilizada para converter string em um objeto JSON e vice-versa.
