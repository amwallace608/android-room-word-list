package com.amwallace.roomwordslist.Data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.amwallace.roomwordslist.Data.WordRepository;
import com.amwallace.roomwordslist.Model.Word;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private WordRepository repository;
    private LiveData<List<Word>> allWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        this.repository = new WordRepository(application);
        this.allWords = repository.getAllWords();
    }

    //get all words getter method - hides implementation from UI
    public LiveData<List<Word>> getAllWords() {return allWords;};

    //insert wrapper method - hides implementation from UI
    public void insert(Word word) {repository.insert(word);}


}
