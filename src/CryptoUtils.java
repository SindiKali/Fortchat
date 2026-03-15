import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class CryptoUtils {

    private static SecretKeySpec gerarChave(String senha) {
        String chaveAjustada = String.format("%-32s", senha).substring(0, 32).replace(' ', '0');
        return new SecretKeySpec(chaveAjustada.getBytes(StandardCharsets.UTF_8), "AES");
    }

    public static String encrypt(String mensagem, String senhaSala) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, gerarChave(senhaSala));
            byte[] textoCifrado = cipher.doFinal(mensagem.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(textoCifrado);
        } catch (Exception e) {
            return "Erro ao criptografar: " + e.getMessage();
        }
    }

    public static String decrypt(String mensagemCriptografada, String senhaSala) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, gerarChave(senhaSala));
            byte[] textoDecodificado = Base64.getDecoder().decode(mensagemCriptografada);
            return new String(cipher.doFinal(textoDecodificado), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "[Mensagem Ilegível - Chave Incorreta]";
        }
    }
}