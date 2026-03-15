import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class FortchatServer {
    private static final int PORT = 7777;

    private static Map<String, List<PrintWriter>> salas = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        System.out.println("Fortchat Server inicializado na porta " + PORT);
        System.out.println("Aguardando conexões...");
        System.out.println("Nenhuma mensagem será descriptografada aqui.");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nova conexão de: " + clientSocket.getInetAddress());

                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String salaAtual;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Sistema: Digite o nome da sala");
                salaAtual = in.readLine();

                salas.putIfAbsent(salaAtual, new CopyOnWriteArrayList<>());
                salas.get(salaAtual).add(out);

                System.out.println("Cliente entrou na sala: " + salaAtual);
                out.println("Sistema: Conectado a sala '" + salaAtual + "'.");

                String mensagem;
                while((mensagem = in.readLine()) != null) {
                    broadcast(salaAtual, mensagem);
                }
            } catch (IOException e) {
                System.out.println("Cliente desconectado da sala: " + salaAtual);
            } finally {
                if (salaAtual != null && salas.containsKey(salaAtual)) {
                    salas.get(salaAtual).remove(out);
                }
                try { socket.close(); } catch (IOException e) {}
            }
        }

        private void broadcast(String sala, String mensagem) {
            for (PrintWriter writer : salas.get(sala)) {
                writer.println(mensagem);
            }
        }
    }
}
