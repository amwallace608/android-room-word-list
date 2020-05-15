package com.amwallace.roomwordslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewWordActivity extends AppCompatActivity {
    //constant for Intent Extra
    public static final String EXTRA_REPLY =
            "com.amwallace.roomwordslist.REPLY";
    private EditText newWordEdt;
    private Button saveWordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        //set up edit text and button
        newWordEdt = (EditText) findViewById(R.id.newWordEdt);
        saveWordBtn = (Button) findViewById(R.id.saveWordBtn);

        //button on click listener
        saveWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if input is empty
                if(newWordEdt.getText().toString().isEmpty()){
                    //indicate user needs to enter word to save
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.empty_word_err),Toast.LENGTH_SHORT).show();
                } else {
                    //user entered text - send in reply intent
                    String word = newWordEdt.getText().toString();
                    Intent replyIntent = new Intent();
                    //put word as extra for Intent
                    replyIntent.putExtra(EXTRA_REPLY, word);
                    //set result of intent
                    setResult(RESULT_OK, replyIntent);
                    //finish activity
                    finish();
                }
            }
        });
    }
}
