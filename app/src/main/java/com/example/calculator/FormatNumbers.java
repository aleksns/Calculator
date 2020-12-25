package com.example.calculator;

public class FormatNumbers {
    private boolean decimalFound = false;

    public String formatInputSeparatingWithCommas(String input, boolean isForOutput) {
        StringBuilder sb = new StringBuilder();
        StringBuffer buffer = new StringBuffer();

        for (int x = 0; x < input.length(); x++) {
            String s = input.charAt(x) + "";
            if (s.equals(".")) {
                decimalFound = true;
            }
            if (s.matches("[-+/*]")) {
                if (decimalFound) {
                    if (isForOutput) {
                        sb.append(replaceDecimalWithComma(buffer.toString()));
                    } else {
                        sb.append(buffer);
                    }
                    sb.append(s);
                    buffer.delete(0, buffer.length());
                    decimalFound = false;
                } else {
                    sb.append(formatNumbers(buffer.toString()));
                    sb.append(input.charAt(x));
                    buffer.delete(0, buffer.length());
                }
            } else {
                buffer.append(s);
            }
        }
        if (decimalFound) {
            if (isForOutput) {
                sb.append(replaceDecimalWithComma(buffer.toString()));
            } else {
                sb.append(buffer);
            }
            decimalFound = false;
        } else {
            sb.append(formatNumbers(buffer.toString()));
        }
        return sb.toString();
    }

      private String formatNumbers(String input) {
        return input.replaceAll("(\\d)(?=(\\d{3})+$)", "$1,");
      }

    private String replaceDecimalWithComma(String input) {
        return input.replace(".", ",");
    }

}
