# AVICIA

## 📋 Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado em sua máquina:

- **Java 21**
- **Node.js**
- **Docker Desktop** e **Docker Compose**
- **Maven**

---

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
npm run devv
