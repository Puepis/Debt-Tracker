package com.example.phili.debttracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * This is the Activity class that is launched when a user wants to create a new Account. It
 * inherits from the AppCompatActivity class.
 *
 * The onPause() and onResume() methods are not overwritten (intentionally) because the data
 * only needs to be saved if the user clicks the "Save" button. Otherwise, when the app is closed
 * and reopened, the Main activity is launched.
 *
 * @see AppCompatActivity
 *
 */
public class NewAccountActivity extends AppCompatActivity {

    // Instance variables
    private EditText billAmountEditText;
    private EditText nameEditText;
    private Button saveButton;
    private String nameString = "";
    private String billAmountString = "0.00";
    private Spinner spinner;

    /**
     * This method is called when the Activity is launched. It instantiates the activity's
     * widgets. It returns no values.
     *
     * @param savedInstanceState (Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        billAmountEditText = findViewById(R.id.amountEditText);
        nameEditText = findViewById(R.id.nameEditText);
        saveButton = findViewById(R.id.saveButton);
        spinner = findViewById(R.id.spinner);
        saveButton.setOnClickListener(new ButtonListener());

        // ArrayAdapter for spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set spinner adapter
        spinner.setAdapter(spinnerAdapter);
    }

    /**
     * This helper method sets the string values for the input fields. It returns no values
     * and accepts no parameters.
     */
    private void setText() {
        // Set text
        nameString = nameEditText.getText().toString();
        billAmountString = billAmountEditText.getText().toString();
    }

    /**
     * This inner class represents the event handler for the Save button. When the button is clicked,
     * The activity is finished and the inputted data is returned to the main activity. It implements
     * the View.OnClickListener interface.
     *
     * @see android.view.View.OnClickListener
     */
    class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            setText();

            // Sanity checking
            boolean valid = true;
            if (nameString.equals("")) {
                Toast.makeText(v.getContext(), R.string.no_name_error, Toast.LENGTH_SHORT).show();
                valid = false;
            }
            else if (billAmountString.equals("")) {
                Toast.makeText(v.getContext(), R.string.no_amount_entered_error, Toast.LENGTH_SHORT).show();
                valid = false;
            }
            else if (Double.parseDouble(billAmountString) < 0.01 || Double.parseDouble(billAmountString) >=
                    1000000000000L){
                Toast.makeText(v.getContext(), R.string.zero_amount_error, Toast.LENGTH_SHORT).show();
                valid = false;
            }

            if (valid) {
                // Pass data back
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Account Name", nameString);
                resultIntent.putExtra("Amount", Double.parseDouble(billAmountString));
                resultIntent.putExtra("Account Type", spinner.getSelectedItemPosition());
                setResult(RESULT_OK, resultIntent);
                finish();
            }

        }
    }
}
