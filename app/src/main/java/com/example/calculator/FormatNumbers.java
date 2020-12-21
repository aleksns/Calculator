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
        String toReturn = input;
        int a = toReturn.length();
        if (a < 4) {
            return input;
        }

        switch (a) {
            case 4:
                toReturn = addChar(toReturn, ',', 1);
                break;
            case 5:
                toReturn = addChar(toReturn, ',', 2);
                break;
            case 6:
                toReturn = addChar(toReturn, ',', 3);
                break;
            case 7:
                toReturn = addChar(toReturn, ',', 1);
                toReturn = addChar(toReturn, ',', 5);
                break;
            case 8:
                toReturn = addChar(toReturn, ',', 2);
                toReturn = addChar(toReturn, ',', 6);
                break;
            case 9:
                toReturn = addChar(toReturn, ',', 3);
                toReturn = addChar(toReturn, ',', 7);
                break;
            case 10:
                toReturn = addChar(toReturn, ',', 1);
                toReturn = addChar(toReturn, ',', 5);
                toReturn = addChar(toReturn, ',', 9);
                break;
            case 11:
                toReturn = addChar(toReturn, ',', 2);
                toReturn = addChar(toReturn, ',', 6);
                toReturn = addChar(toReturn, ',', 10);
                break;
            case 12:
                toReturn = addChar(toReturn, ',', 3);
                toReturn = addChar(toReturn, ',', 7);
                toReturn = addChar(toReturn, ',', 11);
                break;
        }
        return toReturn;
    }

    private String replaceDecimalWithComma(String input) {
        return input.replace(".", ",");
    }

    private String addChar(String str, char ch, int position) {
        StringBuilder sb = new StringBuilder(str);
        sb.insert(position, ch);
        return sb.toString();
    }

}
