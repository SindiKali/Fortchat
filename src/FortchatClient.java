import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FortchatClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 7777;
    private static String chaveSala;
    private static String username;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("||| Inicializando Terminal Fortchat |||");

        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println(in.readLine());
            String sala = scanner.nextLine();
            out.println(sala);

            System.out.println(in.readLine());

            System.out.print("Digite a Chave de Criptografia da sala: ");
            chaveSala = scanner.nextLine();

            System.out.print("Digite seu Username: ");
            username = scanner.nextLine();

            System.out.println("\nConexão Segura Estabelecida. Tudo que você digitar será criptografado.");
            System.out.println("------------------------------------------------------");

            Thread listenerThread = new Thread(() -> {
                try {
                    String mensagemCriptografada;
                    while ((mensagemCriptografada = in.readLine()) != null) {
                        String mensagemAberta = CryptoUtils.decrypt(mensagemCriptografada, chaveSala);
                        System.out.println(mensagemAberta);
                    }
                } catch (IOException e) {
                    System.out.println("Conexão com o servidor perdida.");
                }
            });
            listenerThread.start();

            while (true) {
                String texto = scanner.nextLine();
                if (texto.equalsIgnoreCase("/sair")) {
                    break;
                }

                String mensagemCompleta = username + ": " + texto;

                String pacoteSeguro = CryptoUtils.encrypt(mensagemCompleta, chaveSala);

                out.println(pacoteSeguro);
            }

        } catch (IOException e) {
            System.out.println("Erro de conexão. O servidor do Fortchat está rodando?");
        }
    }
}