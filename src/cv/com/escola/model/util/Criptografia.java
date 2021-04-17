package cv.com.escola.model.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {

    private Criptografia() {
        throw new IllegalStateException("Utility class");
    }
    
    /**
     * Converter string em hash SHA-256 para criptografia da senha do usuario na base de dados
     * @param original
     * @return 
     */
    public static String converter(String original) {
        try {
            StringBuilder hexString = new StringBuilder();
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = algorithm.digest(original.getBytes(StandardCharsets.UTF_8));
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            Mensagem.erro("Erro ao converter senha do usuário \n" + ex);
        }
        return "";
    }
}
