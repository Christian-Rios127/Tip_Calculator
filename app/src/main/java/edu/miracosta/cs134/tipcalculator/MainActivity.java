package edu.miracosta.cs134.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import edu.miracosta.cs134.tipcalculator.model.Bill;

public class MainActivity extends AppCompatActivity {

    //Instance varables
    // Bridge th eview and model
    private Bill currentBill;

    private EditText amountEditText;
    private TextView percentTextView;
    private SeekBar percentSeekBar;
    private TextView tipTextView;
    private TextView totalTextView;

    //Instance variables 1 for currency and 1 for Percent
    // for anywhere in the world when the currency and the percent
    NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.getDefault());
    NumberFormat percent = NumberFormat.getPercentInstance(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // "Wire up" instance variables (initialize them)
        // Ctrl + D = copies it with out the variables
        currentBill = new Bill();
        amountEditText = findViewById(R.id.amountEditText);
        percentTextView = findViewById(R.id.percentTextView);
        percentSeekBar = findViewById(R.id.percentSeekBar);
        tipTextView = findViewById(R.id.tipTextView);
        totalTextView = findViewById(R.id.totalTextView);

        //Lets set the current tip percentage
        //divide the tip by 100.0% for the 1.5%
        currentBill.setTipPercent(percentSeekBar.getProgress() / 100.0);

        //Implements the interface for the EditText
        //Anonymous classes
        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // read the input from the amountEditText (View) an store in the currentBill (Model)
                try {
                    double amount = Double.parseDouble(amountEditText.getText().toString());
                    //store it all in the bill
                    currentBill.setAmount(amount);

                } catch(NumberFormatException e){
                    currentBill.setAmount(0.0);
                }
                calcuateBill();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //Implement the interface for the SeekBar
        percentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // update the tip percent

                currentBill.setTipPercent(percentSeekBar.getProgress() / 100.0);
                //update the view as well
                percentTextView.setText(percent.format((currentBill.getTipPercent())));
                calcuateBill();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    public void calcuateBill(){
        //update the tipTextView & totalTextView
        tipTextView.setText(currency.format(currentBill.getTipAmount()));
        totalTextView.setText(currency.format(currentBill.getTotalAmount()));

    }
}
