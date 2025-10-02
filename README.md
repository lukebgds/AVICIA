# AVICIA

## 📋 Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado em sua máquina:

- **Java 21**
- **Node.js**
- **Docker Desktop** e **Docker Compose**
- **Maven**
- **OpenSSL**

---

## 🔑 Gerando chaves RSA (necessário para o backend)

O backend utiliza autenticação baseada em **JWT**, que depende de chaves RSA.  
As chaves **não são versionadas** no repositório por questões de segurança, então você precisará gerá-las manualmente.

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

## 🚀 Guia de execução

Existem duas formas de iniciar o projeto:

### 🔹 Opção 1 – Usando os scripts automatizados
1. Execute o script **`Install-Dependencies`** para instalar todas as dependências necessárias.  
2. Em seguida, execute o script **`Start-Project`** para iniciar a aplicação.

---

### 🔹 Opção 2 – Execução manual
Caso ocorra algum problema com os scripts, siga os comandos abaixo manualmente no terminal:

```bash
# 1. Instalar dependências do frontend
cd AVICIA-main/avicia
npm install

# 2. Subir containers do Docker (banco de dados e serviços necessários)
cd ../docker
docker-compose up -d

# 3. Executar o backend (Spring Boot)
cd ../api
mvn spring-boot:run

# 4. Iniciar o frontend em modo de desenvolvimento
cd ../avicia
npm run dev


