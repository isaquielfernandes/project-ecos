package cv.com.escola.model.util;

import java.text.DecimalFormat;

public class CustonFormat {

    private CustonFormat() {
        
    }

    public static String customFormat(String pattern, double value) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return myFormatter.format(value);

    }

}
