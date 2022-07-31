package com.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculatorActivity extends AppCompatActivity {


    // Global elements:
    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        display = (TextView) findViewById(R.id.textViewCalculator);
        setupButtons();
    }

    public void setupButtons() {
        List<Button> digitButtons;
        List<Button> operatorButtons;
        // Button[] digitButtons = new Button[10];
        // Button[] operatorButtons = new Button[10];

        /* ======================== */
        /* Setup all digit-buttons: */
        /* ======================== */
        digitButtons = new ArrayList<>();

        digitButtons.add( findViewById(R.id.button0) );
        digitButtons.add( findViewById(R.id.button1) );
        digitButtons.add( findViewById(R.id.button2) );
        digitButtons.add( findViewById(R.id.button3) );
        digitButtons.add( findViewById(R.id.button4) );
        digitButtons.add( findViewById(R.id.button5) );
        digitButtons.add( findViewById(R.id.button6) );
        digitButtons.add( findViewById(R.id.button7) );
        digitButtons.add( findViewById(R.id.button8) );
        digitButtons.add( findViewById(R.id.button9) );

        // Iterate through all digit-buttons and assign their attributes:
        for (Button b : digitButtons) {
            b.setOnClickListener(this::onDigitClick);
            b.setBackgroundColor(Color.GRAY);
        }

        /* =========================== */
        /* Setup all operator-buttons: */
        /* =========================== */
        operatorButtons = new ArrayList<>();

        operatorButtons.add( findViewById(R.id.buttonAdd) );
        operatorButtons.add( findViewById(R.id.buttonSubtract) );
        operatorButtons.add( findViewById(R.id.buttonMultiply) );
        operatorButtons.add( findViewById(R.id.buttonDivide) );

        Log.e("Calculator", operatorButtons.toString());
        // Iterate through all operator-buttons and assign their attributes:
        for (Button b : operatorButtons) {
            b.setOnClickListener(this::onOperatorClick);
            b.setBackgroundColor(Color.DKGRAY);
        }

        // Setup "Calculate" (=) button:
        findViewById(R.id.buttonEquals).setOnClickListener(this::onEqualsClick);

        // Setup CLR button (Clear):
        findViewById(R.id.buttonClearText).setOnClickListener(this::onClearButtonClick);

    }

    public void onDigitClick(View v) {
        String digitVal = ((Button) v).getText().toString();
        String currentEq = display.getText().toString();

        display.setText(currentEq + digitVal);
    }

    public void onOperatorClick(View v) {
        String operatorVal = ((Button) v).getText().toString();
        String currentEq = display.getText().toString();

        if (currentEq.isEmpty()) {
            // Allow only `-` (Subtraction):
        }
        else {

            String equation[] = currentEq.split(" ");
            String newEquation = "";

            if (equation.length == 1) {
                newEquation = currentEq + " " + operatorVal + " ";

            }

            else if (equation.length == 2) {
                newEquation = equation[0] + " " + operatorVal + " ";
            }
            else { // size >= 3
                // Equation is something like: a * b
                double result = evaluate(currentEq); // Get the result of the current equation

                // Append result and next operator
                newEquation = result + " " + operatorVal + " ";
            }
            display.setText(newEquation);
        }
    }

    public void onClearButtonClick(View v) {
        display.setText("");
    }

    public void onEqualsClick(View v) {
        String equation = display.getText().toString();

        double result = evaluate(equation);

        String newEq = result + "";
        display.setText(newEq);
    }


    private double evaluate(String term) {

        if (term.isEmpty()) return 0;
        String[] elements = term.split(" "); // a + b => ["a", "+", "b"]
        Log.e("eval", Arrays.toString(elements));

        if (elements.length == 1) {
            return Double.parseDouble(term);
        }
        else if (elements.length == 3) {
            switch (elements[1].toUpperCase()) {
                case "+":
                    return Double.parseDouble(elements[0]) + Double.parseDouble(elements[2]);
                case "-":
                    return Double.parseDouble(elements[0]) - Double.parseDouble(elements[2]);
                case "X":
                    return Double.parseDouble(elements[0]) * Double.parseDouble(elements[2]);
                case "/":
                    if (Double.parseDouble(elements[2]) != 0.0)
                        return Double.parseDouble(elements[0]) / Double.parseDouble(elements[2]);
                    else // ERROR! Cannot divide by 0!
                        return -1;
            }
        }
        else throw new UnsupportedOperationException();

        return -1;
    }
}