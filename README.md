# 🚢 Batalha Naval

Projeto de um jogo **Batalha Naval (Player vs Player)** desenvolvido em **Java**, com suporte à **comunicação via sockets**, **persistência de estado em arquivos** e **rankings de jogadores**.

---

## 🎮 Sobre o jogo

Dois jogadores conectam-se ao servidor e duelam em um clássico jogo de Batalha Naval. O sistema mantém:

- Mapa oculto e visível
- Tipos variados de embarcações (com herança)
- Pontuação por acertos
- Persistência automática do estado da partida
- Ranking histórico de jogadores

---

## 🛠️ Tecnologias Utilizadas

- Java 8+
- Programação Orientada a Objetos
- Sockets (Cliente/Servidor)
- Serialização de objetos
- Arquivos `.txt` para persistência de dados

---

## 🚀 Como executar o projeto

### 1. Clone o repositório

```bash
git clone https://github.com/OtavioAugustoo/Batalha-naval.git
cd Batalha-naval
```

### 2. Compile os arquivos Java

Você pode compilar pelo terminal (Windows):
```bash
javac network/Servidor.java
javac network/Cliente.java
```

### 3. Execute o servidor

```bash
java network.Servidor
```

### 4. Em outro terminal, execute os clientes (dois terminais separados):

```bash
java network.Cliente
```

---

## 💾 Funcionalidades principais

- Alocação de navios em direções variadas com restrição de adjacência
- Sistema de pontuação individual por jogador
- Continuação da partida de onde parou (checkpoint automático)
- Ranking salvo em `ranking.txt`

---

## 👨‍💻 Autor

Desenvolvido por **Otavio Augusto Santos**  
📧 contatootavioempre@gmail.com  
🔗 [LinkedIn](https://www.linkedin.com/in/otavio-augusto-santos/) | [GitHub](https://github.com/OtavioAugustoo)

---

## 📄 Licença

Este projeto está sob a licença **MIT**. Veja o arquivo `LICENSE` para mais detalhes.
