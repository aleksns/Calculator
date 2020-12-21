package com.example.calculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private Logic logic = new Logic();
    private FormatNumbers format = new FormatNumbers();
    private ParseListManager listManager = new ParseListManager();

    private TextView ScreenInput;
    private TextView ScreenOutput;

    private String input = "0";
    private String inputView = input;
    private String output = "";
    private boolean endOfOperation = false;
    private boolean equalsState = false;
    private boolean percentState = false;

    private ToggleButton colorToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        manageThemeColorAtStart();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScreenInput = findViewById(R.id.screenInput);
        ScreenOutput = findViewById(R.id.screenOutput);
        colorToggle = findViewById(R.id.colorToggle);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            colorToggle.setChecked(true);
        }

        runThemeColorActivity();
        ScreenInput.setText(inputView);
        ScreenOutput.setText(output);
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void buttonClick(View view) {
        Button button = (Button) view;
        String data = button.getText().toString();
        if (endOfOperation) {
            resetAll();
            endOfOperation = false;
        }

        handleIfMathOperatorInputtedTwice(data);

        if (data.matches("[0-9]")) {
            if (input.equals("0")) {
                input = data;
            } else if (listManager.isOnlyZeroInLastInputtedNumber()) {
                return;
            } else {
                input += data;
            }
        } else if (isMathSymbol(data)) {
            input += data;
        }

        switch (data) {
            case "AC":
                resetAll();
                break;
            case "⌫":
                deleteOperation();
                break;
            case "=":
                equalsOperation();
                break;
            case "%":
                percentOperation();
                break;
            case ".":
                decimalOperation();
                break;
            default:
                if (input == null) {
                    input = "";
                }
        }

        handleInputOutputDependingOnState();
        if (output.equals("Can't divide by zero")) {
            endOfOperation = true;
        }

        ScreenInput.setText(format.formatInputSeparatingWithCommas(input, false));
        ScreenOutput.setText(format.formatInputSeparatingWithCommas(output, true));
    }

    private void handleInputOutputDependingOnState() {
        if (percentState) {
            if (mathSymbolAtTheEnd()) {
                deleteOperation();
                listManager.deleteLastElementFromList();
            }
            output = logic.calculatePercent(listManager.getLastElementInList()) + "";
            input = output + "";
            percentState = false;
            return;
        }
        if (!equalsState) {
            if (input.equals("0")) {
                output = "";
            } else {
                listManager.parseStringIntoList(input);
                output = logic.calculateWithOrderMDAS(listManager.getList());
            }
        } else {
            input = output;
            output = "";
            equalsState = false;
        }
    }

    private void resetAll() {
        equalsState = false;
        percentState = false;
        input = "0";
        inputView = input;
        output = "";
    }

    private void deleteOperation() {
        input = logic.deleteLastChar(input);
    }

    private void equalsOperation() {
        listManager.parseStringIntoList(input);
        output = logic.calculateWithOrderMDAS(listManager.getList());
        equalsState = true;
    }

    private void percentOperation() {
        if (input.equals("0")) {
            return;
        }
        percentState = true;
    }

    private void decimalOperation() {
        if (mathSymbolAtTheEnd()) {
            input += "0.";
        } else if (input.equals("0") || listManager.isDecimalCanBeInputted()) {
            input += ".";
        }
    }

    private void manageThemeColorAtStart() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.LightTheme);
    }

    private void runThemeColorActivity() {
        colorToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    recreate();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    recreate();
                }
            }
        });
    }

    private void handleIfMathOperatorInputtedTwice(String data) {
        if (isMathSymbol(data) && mathSymbolAtTheEnd()) {
            input = input.substring(0, input.length() - 1);
        }
    }

    public boolean mathSymbolAtTheEnd() {
        String foo = input.charAt(input.length() - 1) + "";
        return foo.matches("[/*+-]");
    }

    public boolean isMathSymbol(String data) {
        return (data.equals("-") || data.equals("+") || data.equals("/") || data.equals("*"));
    }

}
