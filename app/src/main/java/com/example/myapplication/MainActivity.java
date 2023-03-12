package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView result, solution;
    MaterialButton buttonC, buttonOpenBracket, buttonCloseBracket, buttonDivide,
            buttonSeven, buttonEight, buttonNine, buttonMultiply, buttonFour,
            buttonFive, buttonSix, buttonPlus, buttonOne, buttonTwo, buttonThree,
            buttonMinus, buttonAllClear, buttonZero, buttonDot, buttonEquals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (buttonText.equalsIgnoreCase("c")) {
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
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult =  context
                    .evaluateString(scriptable, data, "Javascript", 1, null)
                    .toString();
            if (finalResult.endsWith(".0")) finalResult =  finalResult.replace(".0", "");
            return finalResult;
        } catch(Exception e) {
            return "Error";
        }
    }
}