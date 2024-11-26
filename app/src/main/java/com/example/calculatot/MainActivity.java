package com.example.calculatot;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private TextView textViewExercise;
    private String currentNumber = "";
    private String firstOperand = "";
    private String operator = "";
    private String exercise = "";
    private boolean afterEqualFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewResult = findViewById(R.id.textViewResult);
        textViewExercise = findViewById(R.id.textViewExercise);
        textViewResult.setText(""); //check if it necessary
    }

    public void onOperatorClick(View view) {
        Button button = (Button) view;
        if (!currentNumber.isEmpty()){
            firstOperand = currentNumber;
            operator = button.getText().toString();
            exercise += firstOperand + " " + operator;
            currentNumber = "";
            textViewExercise.setText(exercise);
            textViewResult.setText("");
        }
    }

    public void onNumberClick(View view) {
        Button button = (Button) view;
        if(afterEqualFlag){
            currentNumber = "";
            textViewResult.setText("");
            textViewExercise.setText("");
            afterEqualFlag = false;
        }
        currentNumber += button.getText().toString();
        textViewResult.setText(currentNumber);
    }

    public void onClearClick(View view) {
        currentNumber = "";
        firstOperand = "";
        operator = "";
        textViewResult.setText("");
        textViewExercise.setText("");
    }

    public void onEqualClick(View view){
        if (!firstOperand.isEmpty() && !currentNumber.isEmpty() && !operator.isEmpty()){
            double result = calculateResult(
                    Double.parseDouble(firstOperand),
                    Double.parseDouble(currentNumber),
                    operator
            );
            exercise += " " + currentNumber;
            textViewExercise.setText(exercise);
            if (result == (int) result) {
                textViewResult.setText(String.valueOf((int) result));
                currentNumber = String.valueOf((int)result);
            } else {
                textViewResult.setText(String.valueOf(result));
                currentNumber = String.valueOf(result);
            }
            firstOperand = "";
            operator = "";
            afterEqualFlag = true;
            exercise = "";
        }
    }

    // Perform calculation
    private double calculateResult(double operand1, double operand2, String operator){
        switch (operator){
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "X":
                return operand1 * operand2;
            case "%":
                return operand1 / operand2;
            default:
                return 0;
        }
    }

}