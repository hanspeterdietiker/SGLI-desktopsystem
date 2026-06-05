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


    public static boolean isValid(String cpf) {
        if (cpf == null) return false;
        String trimmed = cpf.trim();
        if (!trimmed.matches(CPF_PATTERN)) return false;
        String d = trimmed.replaceAll("[^0-9]", "");
        if (d.matches("(\\d)\\1{10}")) return false;
        return checkDigit(d, 9) && checkDigit(d, 10);
    }


    public static String mask(String cpf) {
        if (cpf == null || cpf.isBlank()) return "";
        String formatted = cpf.contains(".") ? cpf.trim() : format(cpf.trim());
        if (formatted.length() < 14) return "***.***.***-**";
        return formatted.substring(0, 3) + ".***.***-" + formatted.substring(12);
    }

    private static boolean checkDigit(String digits, int pos) {
        int sum = 0;
        for (int i = 0; i < pos; i++) {
            sum += (digits.charAt(i) - '0') * (pos + 1 - i);
        }
        int r = 11 - (sum % 11);
        int expected = (r >= 10) ? 0 : r;
        return expected == (digits.charAt(pos) - '0');
    }
}
