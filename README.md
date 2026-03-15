# 🛡️ Fortchat

Um sistema de chat cliente-servidor ultrasseguro em Java, construído com criptografia de ponta a ponta.

## 🚀 O Projeto

O Fortchat foi desenvolvido para garantir comunicação totalmente privada em redes locais ou remotas. A chave de criptografia nunca sai da máquina do usuário, garantindo que o servidor atue apenas como um roteador de tráfego cego. Se o servidor for interceptado, os pacotes capturados serão texto cifrado ilegível.

## ✨ Funcionalidades

* **Criptografia E2EE (End-to-End Encryption):** Utiliza criptografia AES-256 para cifrar as mensagens antes de entrarem na rede.
* **Arquitetura Zero-Knowledge:** O servidor que gerencia as salas e roteia as mensagens não possui acesso às chaves de descriptografia.
* **Ajuste Dinâmico de Chave:** O sistema adapta senhas de qualquer tamanho para o padrão estrito de 32 bytes exigido pelo AES-256 de forma transparente para o usuário.
* **Salas Simultâneas:** Suporte para múltiplos usuários e diferentes salas de chat rodando paralelamente em Threads dedicadas.

## 🛠️ Tecnologias Utilizadas

* **Java 8**
* **IntelliJ IDE**

## ⚙️ Como Executar

1. Compile todos os arquivos `.java`.
2. Inicie o servidor:
   ```bash
   java FortchatServer






   

Don't worry about this Spy P.
