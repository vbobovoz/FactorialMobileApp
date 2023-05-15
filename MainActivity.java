package com.example.factorialmobileapp;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {

    private EditText numberEditText;
    private CheckBox processCheckBox;
    private Button calculateButton;
    private TextView resultTextView;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberEditText = findViewById(R.id.numberEditText);
        processCheckBox = findViewById(R.id.processCheckBox);
        calculateButton = findViewById(R.id.calculateButton);
        resultTextView = findViewById(R.id.resultTextView);

        gestureDetector = new GestureDetector(this, new MyGestureListener());

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateFactorial();
            }
        });

        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void calculateFactorial() {
        String numberString = numberEditText.getText().toString();
        if (numberString.isEmpty()) {
            Toast.makeText(MainActivity.this, "Введите число", Toast.LENGTH_SHORT).show();
            return;
        }

        int number = Integer.parseInt(numberString);

        if (number < 0) {
            Toast.makeText(MainActivity.this, "Недопустимое значение! Введите положительное число.", Toast.LENGTH_SHORT).show();
            return;
        }

        BigInteger factorial = calculateFactorial(number);

        if (processCheckBox.isChecked()) {
            showFactorialProcess(factorial);
        } else {
            showFactorialResult(factorial);
        }
    }

    private BigInteger calculateFactorial(int number) {
        BigInteger factorial = BigInteger.ONE;

        for (int i = 2; i <= number; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }

        return factorial;
    }

    private void showFactorialProcess(BigInteger factorial) {
        StringBuilder processBuilder = new StringBuilder();

        for (int i = 2; i <= Integer.parseInt(numberEditText.getText().toString()); i++) {
            processBuilder.append(i).append(" * ");
        }

        String process = processBuilder.substring(0, processBuilder.length() - 2);

        resultTextView.setText("Факториал числа " + numberEditText.getText().toString() + " равен: " + process + " = " + factorial);
    }

    private void showFactorialResult(BigInteger factorial) {
        resultTextView.setText("Факториал числа " + numberEditText.getText().toString() + " равен: " + factorial);
    }

    private void clearFields() {
        numberEditText.setText("");
        resultTextView.setText("");
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            float diffX = event2.getX() - event1.getX();
            float diffY = event2.getY() - event1.getY();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        // Свайп вправо
                        clearFields();
                    } else {
                        // Свайп влево
                        calculateFactorial();
                    }
                    return true;
                }
            }
            return false;
        }
    }
}
