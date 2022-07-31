package com.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup all buttons on screen inside
        // a different function (Method) to keep clean code
        initializeButtons();

        display = (TextView) findViewById(R.id.textView_display);
    }

    /**
     * פונקציות המקבלות כפרמטר את אותו אלמנט שנלחץ.
     * חייבים להעביר את View v כדי לחבר את הכפתור לפעולה-לאחר-לחיצה
     * @param v - The pressed element (Button)
     */
    public void onDigitClick(View v) {
        String digitVal = ((Button) v).getText().toString();
        String currentEq = display.getText().toString();

        display.setText(currentEq + digitVal);
    }


    public void onOperatorClick(View v) {
        String currentEq = display.getText().toString();

        if (currentEq.isEmpty()) {
            if (v.getId() == R.id.btnSubtract) {
                display.setText("-"); // allow negative numbers
            }
        } else {
//            display.setText("" + eval(currentEq));
            // Text contains a number or an equation
            String[] term = currentEq.split(" ");

            if (term.length == 1) {
                // Only a number is found in the equation - add an operator {+, -, *, /}:
                currentEq = term[0] + " " + ((Button) v).getText().toString() + " ";
                display.setText(currentEq);
            }

            else if (term.length == 2) {
                // Text contains something like: "a + "
                String result = term[0] + " " + ((Button) v).getText().toString() + " ";
                display.setText(result);
            }

            else {
                // Text contains something like: "a * b", where `*` is any of {+, -, X, /}
                String result = evaluate(currentEq) + "";

                display.setText(result);
            }
        }
    }

    public void onClearButtonClick(View v) {
        display.setText("");
    }

    public void onEqualsClick(View v) {
        String result = "" + evaluate(display.getText().toString());
        display.setText(result);
    }


    private void initializeButtons() {
        List<Button> digitButtons, operatorButtons;

        /* ======================== */
        /* Setup all digit-buttons: */
        /* ======================== */
        digitButtons = new ArrayList<>();

        digitButtons.add( findViewById(R.id.btnZero) );
        digitButtons.add( findViewById(R.id.btnOne) );
        digitButtons.add( findViewById(R.id.btnTwo) );
        digitButtons.add( findViewById(R.id.btnThree) );
        digitButtons.add( findViewById(R.id.btnFour) );
        digitButtons.add( findViewById(R.id.btnFive) );
        digitButtons.add( findViewById(R.id.btnSix) );
        digitButtons.add( findViewById(R.id.btnSeven) );
        digitButtons.add( findViewById(R.id.btnEight) );
        digitButtons.add( findViewById(R.id.btnNine) );

        // Iterate through all digit-buttons and assign their attributes:
        for (Button b : digitButtons) {
            b.setOnClickListener(this::onDigitClick);
            b.setBackgroundColor(Color.GRAY);
        }

        /* =========================== */
        /* Setup all operator-buttons: */
        /* =========================== */
        operatorButtons = new ArrayList<>();

        operatorButtons.add( findViewById(R.id.btnAdd) );
        operatorButtons.add( findViewById(R.id.btnSubtract) );
        operatorButtons.add( findViewById(R.id.btnMultiply) );
        operatorButtons.add( findViewById(R.id.btnDivide) );

        // Iterate through all operator-buttons and assign their attributes:
        for (Button b : operatorButtons) {
            b.setOnClickListener(this::onOperatorClick);
            b.setBackgroundColor(Color.DKGRAY);
        }

        // Setup "Calculate" (=) button:
        findViewById(R.id.btnEquals).setOnClickListener(this::onEqualsClick);

        // Setup CLR button (Clear):
        findViewById(R.id.btnClear).setOnClickListener(this::onClearButtonClick);
    }


    private double evaluate(String term) {

        if (term.isEmpty()) return 0;
        String[] elements = term.split(" ");
        Log.e("eval", Arrays.toString(elements));

        if (elements.length == 1) {
            return Double.parseDouble(term);
        }
//        else if (elements.length == 2) {
//            return Double.parseDouble(elements[0]);
//        }
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