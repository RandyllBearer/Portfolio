package edu.pitt.cs.cs1635.rlb97.postagecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //disable the calculate button until there is a weight entered
        Button calculateResult = (Button) findViewById(R.id.calculate_cost_button);
        calculateResult.setEnabled(false);

        //enable the calculate button once the package weight text field is changed
        final EditText plainText = (EditText) findViewById(R.id.package_weight_plaintext);
        plainText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Do Nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Do Nothing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Button calculateResult = (Button) findViewById(R.id.calculate_cost_button);

                if(plainText.getText().toString().equals("")){
                    calculateResult.setEnabled(false);
                    TextView resultTextView = (TextView) findViewById(R.id.result);
                    resultTextView.setText("");
                }else{  //as long as something exists, it will be error handled later
                    calculateResult.setEnabled(true);
                }

            }
        });

    }   //end onCreate()




    //Calculate the cost once the "Calculate Cost" button has been pressed
    public void calculateCost(View view){
        //get the results of the spinner
        Spinner mySpinner = (Spinner) findViewById(R.id.package_type);
        String packageType = mySpinner.getSelectedItem().toString();

        //get the user inputed package weight and verify it
        EditText myEditText = (EditText) findViewById(R.id.package_weight_plaintext);
        String packageWeightText = myEditText.getText().toString();
        double packageWeight = Double.parseDouble(packageWeightText);

        if(packageWeight <= 0){
            TextView resultTextView = (TextView) findViewById(R.id.result);
            resultTextView.setText("ERROR: All Package weights must be >0!");
        }else if(packageWeight >= 13){
            TextView resultTextView = (TextView) findViewById(R.id.result);
            resultTextView.setText("ERROR: All Package weights must be <13!");
        }else if(packageWeight > 3.5 && packageType.contains("Letter")){
            TextView resultTextView = (TextView) findViewById(R.id.result);
            resultTextView.setText("ERROR: All Letter weights must be <3.6! Otherwise, it is a large envelope.");
        }else{
            //calculate result
            double result = 0.0;
            if(packageType.equals("Letter (Stamped)")){
                result = .5 + (Math.ceil(packageWeight) * .21) - .21;

            }else if(packageType.equals("Letter (Metered)")){
                result = .47 + (Math.ceil(packageWeight) * .21) - .21;

            }else if(packageType.equals("Large Envelope (Flats)")){
                result = 1 + (Math.ceil(packageWeight) * .21) - .21;

            }else if(packageType.equals("Package")){
               if(packageWeight < 4){
                   result = 3.5;
               }else if(packageWeight < 8){
                   result = 3.75;
               }else if(packageWeight < 9){
                   result = 4.10;
               }else if(packageWeight < 10){
                   result = 4.45;
               }else if(packageWeight < 11){
                   result = 4.8;
               }else if(packageWeight < 12){
                   result = 5.15;
               }else if(packageWeight < 13){
                   result = 5.50;
               }

            }

            //update result
            String resultString = String.format("%.2f", result);    //convert to currency
            TextView resultTextView = (TextView) findViewById(R.id.result);
            resultTextView.setText("Your estimated shipping cost is: $"+resultString);
        }



    }   //end calculateCost()

}   //end main
