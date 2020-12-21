package com.example.calculator;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Logic {
    private DecimalFormat df = new DecimalFormat("###.###");

    public String calculateWithOrderMDAS(ArrayList<String> list) {
        return parseListCalculateValuesWithOrderMDAS(list) + "";
    }

    private String parseListCalculateValuesWithOrderMDAS(ArrayList<String> list) {
        ArrayList<String> calculated = new ArrayList(list);
        double x;
        double y;

        for (int i = 0; i < calculated.size() - 1; i++) {
            String s = calculated.get(i) + "";

            if (isDivision(s) || isMultiplication(s)) {
                x = Double.parseDouble(calculated.get(i - 1));
                y = Double.parseDouble(calculated.get(i + 1));
                removeThreeObjectsFromList(calculated, i);

                if (isDivision(s)) {
                    if (y == 0) {
                        return "Can't divide by zero";
                    }
                    calculated.add(i - 1, division(x, y) + "");
                } else {
                    calculated.add(i - 1, multiplication(x, y) + "");
                }
                i = 0;
            }
        }

        for (int i = 0; i < calculated.size() - 1; i++) {
            String s = calculated.get(i);

            if (isAddition(s) || isSubtraction(s)) {
                x = Double.parseDouble(calculated.get(i - 1));
                y = Double.parseDouble(calculated.get(i + 1));
                removeThreeObjectsFromList(calculated, i);

                if (isAddition(s)) {
                    calculated.add(i - 1, addition(x, y) + "");
                } else {
                    calculated.add(i - 1, subtraction(x, y) + "");
                }
                i = 0;
            }
        }
        return removeTrailingZeroOutput(Double.parseDouble(calculated.remove(0))) + "";
    }

    private String removeTrailingZeroOutput(Double number) {
        String result = number + "";
        String[] foo = result.split("\\.");
        if (foo[1].equals("0") || foo[1].length() > 4) {
            result = df.format(number) + "";
        }
        return result;
    }

    private void removeThreeObjectsFromList(ArrayList<String> list, int i) {
        list.remove(i + 1);
        list.remove(i);
        list.remove(i - 1);
    }

    private boolean isAddition(String s) {
        return s.equals("+");
    }

    private boolean isSubtraction(String s) {
        return s.equals("-");
    }

    private boolean isDivision(String s) {
        return s.equals("/");
    }

    private boolean isMultiplication(String s) {
        return s.equals("*");
    }

    public String deleteLastChar(String input) {
        if (input.length() == 1) {
            return "0";
        }
        return input.substring(0, input.length() - 1);
    }

    public double calculatePercent(String number) {
        double result = division(Double.parseDouble(number), 100);
        return result;
    }

    private double division(double x, double y) {
        return x / y;
    }

    private double multiplication(double x, double y) {
        return x * y;
    }

    private double subtraction(double x, double y) {
        return x - y;
    }

    private double addition(double x, double y) {
        return x + y;
    }
}
