package com.example.assignment1;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class InstructionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction); // Ensure your XML file is named correctly

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Instruction");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();  // Or use finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}