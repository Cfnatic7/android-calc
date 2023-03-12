package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView result, solution;
    MaterialButton buttonC, buttonOpenBracket, buttonCloseBracket, buttonDivide,
            buttonSeven, buttonEight, buttonNine, buttonMultiply, buttonFour,
            buttonFive, buttonSix, buttonPlus, buttonOne, buttonTwo, buttonThree,
            buttonMinus, buttonAllClear, buttonZero, buttonDot, buttonEquals, buttonSinus,
            buttonCosinus, buttonTangens, buttonNaturalLog, buttonPowerOfTwo, buttonSquareRoot,
            buttonPower, buttonLogarithm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
        } else {
            // In portrait
        }
        setContentView(R.layout.basic_calc);
        result = findViewById(R.id.result_text_view);
        solution = findViewById(R.id.solution_text_view);

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

        assignId(R.id.button_sinus);
        assignId(R.id.button_cosinus);
        assignId(R.id.button_tangens);
        assignId(R.id.button_natural_log);
        assignId(R.id.button_power_of_two);
        assignId(R.id.button_square_root);
        assignId(R.id.button_power);
        assignId(R.id.button_logarithm);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        int orientation = newConfig.orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.basic_calc);
        }
        else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.advanced_calc);
        }
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

        if (buttonText.equalsIgnoreCase("ac")) {
            solution.setText("");
            result.setText("0");
            return;
        }
        if (buttonText.equals("=")) {
            solution.setText(result.getText());
            return;
        }
        if (buttonText.equalsIgnoreCase("sin")) {
            dataToCalculate = "sin(" + dataToCalculate + ")";
        }
        else if (buttonText.equalsIgnoreCase("cos")) {
            dataToCalculate = "cos(" + dataToCalculate + ")";
        }
        else if (buttonText.equalsIgnoreCase("tan")) {
            dataToCalculate = "tan(" + dataToCalculate + ")";
        }
        else if (buttonText.equalsIgnoreCase("ln")) {
            dataToCalculate = "ln(" + dataToCalculate + ")";
        }
        else if (buttonText.equalsIgnoreCase("x^2")) {
            dataToCalculate = "(" + dataToCalculate + ")^2";
        }
        else if (buttonText.equalsIgnoreCase("sqrt")) {
            dataToCalculate = "(" + dataToCalculate + ")^(1/2)";
        }
        else if (buttonText.equalsIgnoreCase("x^y")) {
            dataToCalculate = "(" + dataToCalculate + ")^";
        }
        else if (buttonText.equalsIgnoreCase("log")) {
            dataToCalculate = "log(" + dataToCalculate + ")";
        }
        else if (buttonText.equalsIgnoreCase("c")) {
            dataToCalculate = dataToCalculate.substring(0, databaseList().length - 1);
        } else {
            dataToCalculate += buttonText;
        }
        solution.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);
        if (!finalResult.equals("Error")) {
            result.setText(finalResult);
        }
    }

    String getResult(String data) {
        try {
            DoubleEvaluator eval = new DoubleEvaluator();
            String finalResult =  eval.evaluate(data).toString();
            if (finalResult.endsWith(".0")) finalResult =  finalResult.replace(".0", "");
            return finalResult;
        } catch(Exception e) {
            return "Error";
        }
    }
}