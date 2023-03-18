package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView result, solution;

    private static final List<String> SYMBOLS = List.of("sin", "cos", "ln", "^(1/2)", "sqrt",
            "tan", "log", "^", "+", "-", "*", "/", ".", "e", "pi");

    private static final List<String> NON_OPERATORS = List.of("sin", "cos", "ln", "^(1/2)", "sqrt",
            "tan", "log", "e", "pi");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int orientation = getResources().getConfiguration().orientation;
        if (Configuration.ORIENTATION_PORTRAIT == orientation) {
            setContentView(R.layout.basic_calc);
        }
        else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.advanced_calc);
            assingAdvancedButtons();
        }
        basicSetup();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("result", result.getText().toString());
        outState.putString("solution", solution.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        result.setText(inState.getString("result"));
        solution.setText(inState.getString("solution"));
    }

    private void assingAdvancedButtons() {
        assignId(R.id.button_sinus);
        assignId(R.id.button_cosinus);
        assignId(R.id.button_tangens);
        assignId(R.id.button_natural_log);
        assignId(R.id.button_power_of_two);
        assignId(R.id.button_square_root);
        assignId(R.id.button_power);
        assignId(R.id.button_logarithm);
        assignId(R.id.button_euler_number);
        assignId(R.id.button_pi);
    }

    private void assignBasicButtons() {
        assignId(R.id.button_clear);
        assignId(R.id.button_open_bracket);
        assignId(R.id.button_close_bracket);
        assignId(R.id.button_divide);

        assignId(R.id.button_7);
        assignId(R.id.button_8);
        assignId(R.id.button_9);
        assignId(R.id.button_multiply);

        assignId(R.id.button_4);
        assignId(R.id.button_5);
        assignId(R.id.button_6);
        assignId(R.id.button_plus);

        assignId(R.id.button_1);
        assignId(R.id.button_2);
        assignId(R.id.button_3);
        assignId(R.id.button_minus);

        assignId(R.id.button_ac);
        assignId(R.id.button_0);
        assignId(R.id.button_dot);
        assignId(R.id.button_equals);
    }

    private void basicSetup() {
        assignBasicButtons();
        result = findViewById(R.id.result_text_view);
        solution = findViewById(R.id.solution_text_view);
    }

    void assignId(int id) {
        MaterialButton button = findViewById(id);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solution.getText().toString();

        if (result.getText().toString().equalsIgnoreCase("0")) {
            result.setText("0");
        }

        if (buttonText.equalsIgnoreCase("ac")) {
            solution.setText("");
            result.setText("0");
            return;
        }
        if (buttonText.equals("=")) {
            solution.setText(result.getText());
            return;
        }
        else if (isInputEndingWithSymbols(buttonText)) {
            if (isSymbolAllowed(dataToCalculate, buttonText)) {
                dataToCalculate += buttonText;
            }
        }
        else if (isStringValidInteger(buttonText)) {
            if(!isInputEndingWithNonOperator(dataToCalculate)) {
                dataToCalculate += buttonText;
            }
        }
        else if (buttonText.equalsIgnoreCase("c")) {
            if (dataToCalculate.length() > 0) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
        } else {
            if (isSymbolAllowed(dataToCalculate, buttonText)) {
                dataToCalculate += buttonText;
            }
        }
        solution.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);
        if (!finalResult.equals("Error")) {
            result.setText(finalResult);
        }
        else {
            handleImpossibleCalculations(dataToCalculate);
        }
    }

    private void handleImpossibleCalculations(String dataToCalculate) {
        if (dataToCalculate.matches(".*ln[(][-][0-9]?([0-9]*[.])?[0-9]+[)].*")) {
            result.setText("NaN");
        }
        if (dataToCalculate.matches(".*log[(][-][0-9]?([0-9]*[.])?[0-9]+[)].*")) {
            result.setText("NaN");
        }
        if (dataToCalculate.matches(".*[(][-][0-9]?([0-9]*[.])?[0-9]+[)]\\^[(]1/2[)].*")) {
            result.setText("NaN");
        }
    }

    String getResult(String data) {
        try {
            DoubleEvaluator eval = new DoubleEvaluator();
            String finalResult =  eval.evaluate(data).toString();
            if (finalResult.endsWith(".0")) finalResult =  finalResult.replace(".0", "");
            if (finalResult.length() > 7) finalResult = finalResult.substring(0, 7);
            return finalResult;
        } catch(Exception e) {
            return "Error";
        }
    }

    private boolean isStringValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isSymbolAllowed(String dataToCalculate, String input) {
        boolean dataToCalculateEndsWithSymbol = isInputEndingWithSymbols(dataToCalculate);

        boolean inputEndsWithSymbol = isInputEndingWithSymbols(input);
        if ((dataToCalculate.endsWith("e") || dataToCalculate.endsWith("pi"))
                && !isInputEndingWithNonOperator(input)) {
            return true;
        }
        else if (!isInputEndingWithNonOperator(dataToCalculate) &&
                (input.endsWith("e") || input.endsWith("pi"))) {
            return true;
        }
        return !(dataToCalculateEndsWithSymbol && inputEndsWithSymbol);
    }

    private boolean isInputEndingWithSymbols(String input) {
        boolean inputEndsWithSymbol = false;
        for (String symbol: SYMBOLS) {
            if (input.endsWith(symbol)) {
                inputEndsWithSymbol = true;
                break;
            }
        }
        return inputEndsWithSymbol;
    }

    private boolean isInputEndingWithNonOperator(String input) {
        boolean endsWithNonOperator = false;
        for (String nonOperator: NON_OPERATORS) {
            if (input.endsWith(nonOperator)) {
                endsWithNonOperator = true;
                break;
            }
        }
        return endsWithNonOperator;
    }
}