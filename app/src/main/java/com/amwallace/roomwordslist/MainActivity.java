package com.amwallace.roomwordslist;

import android.content.Intent;
import android.os.Bundle;

import com.amwallace.roomwordslist.Data.WordViewModel;
import com.amwallace.roomwordslist.Model.Word;
import com.amwallace.roomwordslist.UI.WordListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private WordViewModel wordViewModel;
    //constant for new word activity request code
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //get viewmodel from ViewModelProviders class
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        //setup recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //setup recycler view adapter
        final WordListAdapter adapter = new WordListAdapter(this);
        //set adapter for recycler view
        recyclerView.setAdapter(adapter);
        //set layout manager for recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //observer for LiveData word list returned from getAllWords
        wordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                //update data cached in adapter to observed data change
                adapter.setWordList(words);
                Log.d("NUM WORDS IN LIST:", words.size() + " Words passed to adapter");
                Log.d("NUM WORDS IN ADAPTER LIST:", adapter.getItemCount()
                        + " Words in adapter list");
            }
        });
        //fab setup
        FloatingActionButton fab = findViewById(R.id.fab);
        //fab onclick listener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to new word activity - start for result
                Intent intent = new Intent(MainActivity.this,NewWordActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    //on activity result method - insert new word to database
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //check result and request code
        if(requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            //get word from intent extra
            Word newWord = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            //insert into db
            wordViewModel.insert(newWord);
        } else {
            //indicate error with request or result code
            Toast.makeText(getApplicationContext(),
                    "Error with Activity Request Code or Result Code",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
