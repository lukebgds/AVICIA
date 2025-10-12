# AVICIA

## 📋 Pré-requisitos
Antes de executar o projeto, certifique-se de ter instalado em sua máquina:
- **Docker Desktop** e **Docker Compose**
- **OpenSSL** (necessário para gerar as chaves RSA)

---

## 🔑 Gerando chaves RSA (necessário para o backend)
O backend utiliza autenticação baseada em **JWT**, que depende de chaves RSA.  
As chaves **não são versionadas** no repositório por questões de segurança, então você precisará gerá-las manualmente **antes de iniciar o Docker Compose**.

### Passo a passo (Windows)
1. Abra o **Prompt de Comando (cmd)** na pasta raiz do projeto `AVICIA`.
2. Execute os seguintes comandos:
```bash
# 1. Criar a pasta onde as chaves ficarão armazenadas
mkdir api\src\main\resources\keys

# 2. Gerar a chave privada (private_key.pem)
openssl genpkey -algorithm RSA -out api\src\main\resources\keys\private_key.pem -pkeyopt rsa_keygen_bits:2048

# 3. Gerar a chave pública (public_key.pem) a partir da chave privada
openssl rsa -pubout -in api\src\main\resources\keys\private_key.pem -out api\src\main\resources\keys\public_key.pem
```

**Nota:** As chaves serão montadas no container do backend via Docker Compose. Certifique-se de que elas estejam geradas antes de prosseguir.

---

## 🚀 Guia de execução
O projeto agora é executado de forma automatizada via **Docker Compose**, que cria e inicia os containers para o **backend**, **frontend** e **banco de dados** automaticamente. Não é mais necessário instalar dependências manualmente (como Maven, Node, Java ou npm), pois tudo é gerenciado pelos containers.

1. Abra o **Prompt de Comando (cmd)** na pasta `docker`.
2. Inicie os containers executando o seguinte comando:
```bash
docker compose up -d
```

- O **frontend** estará disponível em `http://localhost:8080`
- O **backend** estará disponível em `http://localhost:9081`
- O **banco de dados** (PostgreSQL) estará acessível internamente via Docker.
