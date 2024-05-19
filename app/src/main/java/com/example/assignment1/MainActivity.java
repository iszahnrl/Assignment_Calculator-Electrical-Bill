package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText NumberElectric, Rebate;
    Button btnCalc, btnClear;
    TextView NumElectricOutput, FinalCostOutput, RebateOutput;
//Change 1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NumberElectric = findViewById(R.id.NumberElectric);
        Rebate = findViewById(R.id.Rebate);
        btnCalc = findViewById(R.id.btnCalc);
        btnClear = findViewById(R.id.btnClear);
        NumElectricOutput = findViewById(R.id.NumElectricOutput);
        FinalCostOutput = findViewById(R.id.FinalCostOutput);
        RebateOutput = findViewById(R.id.RebateOutput);

        btnCalc.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCalc) {
            String inputElectricity = NumberElectric.getText().toString();
            String inputRebate = Rebate.getText().toString();

            if (TextUtils.isEmpty(inputElectricity) || TextUtils.isEmpty(inputRebate)) {
                Toast.makeText(this, "Enter input values for electricity and rebate", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double electricity = Double.parseDouble(inputElectricity);
                int rebateValue = Integer.parseInt(inputRebate); // Change to int for easier range checking

                // Check if the rebate value is within the allowed range
                if (rebateValue < 1 || rebateValue > 5) {
                    Toast.makeText(this, "Rebate must be between 1 and 5", Toast.LENGTH_SHORT).show();
                    return;
                }

                double electricityResult = calculateElectricityBill(electricity);
                double finalCost = electricityResult - (electricityResult * (rebateValue / 100.0));

                DecimalFormat decimalFormat = new DecimalFormat("#,##0.000");
                decimalFormat.setRoundingMode(RoundingMode.DOWN);

                String formattedElectricityResult = decimalFormat.format(electricityResult / 100.0);
                String formattedFinalCost = decimalFormat.format(finalCost / 100.0);

                String electricityText = getString(R.string.electricity_output, formattedElectricityResult);
                String finalCostText = getString(R.string.final_cost_output, formattedFinalCost);
                String rebateCostText = getString(R.string.rebate_output, formattedFinalCost);

                NumElectricOutput.setText(electricityText);
                FinalCostOutput.setText(finalCostText);
                RebateOutput.setText("Rebate: " + inputRebate + "%");

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid input format", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btnClear) {
            NumberElectric.setText("");
            Rebate.setText("");
            NumElectricOutput.setText("");
            FinalCostOutput.setText("");
            RebateOutput.setText(""); // Clear the rebate output as well
        }
    }

    private double calculateElectricityBill(double electricity) {
        double TotalCharge = 0.0;

        if (electricity <= 200) {
            TotalCharge = electricity * 21.8;
        } else if (electricity <= 300) {
            TotalCharge = (200 * 21.8) + ((electricity - 200) * 33.4);
        } else if (electricity <= 600) {
            TotalCharge = (200 * 21.8) + (100 * 33.4) + ((electricity - 300) * 51.6);
        } else if (electricity > 600) {
            TotalCharge = (200 * 21.8) + (100 * 33.4) + (300 * 51.6) + ((electricity - 600) * 54.6);
        }

        return TotalCharge;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
            return true;
        } else if (itemId == R.id.instruction) {
            Intent instructionsIntent = new Intent(this, InstructionActivity.class);
            startActivity(instructionsIntent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
