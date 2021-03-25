package cv.com.escola.model.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {
    
    /**
     * Converter string em hash MD5 para criptografia da senha do usuario na base de dados
     * @param original
     * @return 
     */
    public static String converter(String original) {
        try {
            StringBuilder hexString = new StringBuilder();
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            byte messageDigest[] = algorithm.digest(original.getBytes("UTF-8"));

            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Mensagem.erro("Erro ao converter senha do usuário \n" + ex);
        }

        return "";
    }
}
