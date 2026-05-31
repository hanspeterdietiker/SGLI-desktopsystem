package org.desktop.system.sgli.sgli.Utils;

public class CpfUtils {

    private static final String CPF_PATTERN = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";

    private CpfUtils() {
        throw new UnsupportedOperationException("Esta é uma classe utilitária e não pode ser instanciada");
    }

    public static boolean isFormatValid(String cpf) {
        return cpf != null && cpf.trim().matches(CPF_PATTERN);
    }

    public static String format(String digits) {
        int len = digits.length();
        if (len <= 3) return digits;
        if (len <= 6) return digits.substring(0, 3) + "." + digits.substring(3);
        if (len <= 9) return digits.substring(0, 3) + "." + digits.substring(3, 6) + "." + digits.substring(6);
        return digits.substring(0, 3) + "." + digits.substring(3, 6) + "." + digits.substring(6, 9) + "-" + digits.substring(9);
    }
}
