package com.example.calculator;

import java.util.ArrayList;

public class ParseListManager {
    private boolean decimalFound = false;
    private ArrayList<String> inputList = new ArrayList();

    public void parseStringIntoList(String input) {
        StringBuffer buffer = new StringBuffer();
        inputList.clear();
        int i = 0;
        if (input.startsWith("-")) {
            buffer.append("-");
            i = 1;
        }

        for (int x = i; x < input.length(); x++) {
            String s = input.charAt(x) + "";
            if (s.equals(".")) {
                decimalFound = true;
            }
            if (isOperator(s)) {
                if (decimalFound) {
                    inputList.add(buffer.toString());
                    inputList.add(s);
                    buffer.delete(0, buffer.length());
                    decimalFound = false;
                } else {
                    inputList.add(buffer.toString());
                    inputList.add(input.charAt(x) + "");
                    buffer.delete(0, buffer.length());
                }
            } else {
                buffer.append(s);
            }
        }
        if (decimalFound) {
            inputList.add(buffer.toString());
            decimalFound = false;
        } else if (!buffer.toString().equals("")) {
            inputList.add(buffer.toString());
        }
    }

    public boolean isDecimalCanBeInputted() {
        return (isNoDecimal(getLastElementInList()));
    }

    public boolean isOnlyZeroInLastInputtedNumber() {
        return (isNoDecimal(getLastElementInList()) && getLastElementInList().equals("0"));
    }

    public void deleteLastElementFromList() {
        inputList.remove(getLastElementInList());
    }

    private boolean isOperator(String s) {
        return s.matches("[-/*+]");
    }

    private boolean isNoDecimal(String num) {
        return !num.contains(".");
    }

    public String getLastElementInList() {
        return inputList.get(inputList.size() - 1);
    }

    public ArrayList<String> getList() {
        return this.inputList;
    }
}
