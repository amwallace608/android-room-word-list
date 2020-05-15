package com.amwallace.roomwordslist.Data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.amwallace.roomwordslist.Model.Word;

import java.util.List;

public class WordRepository {
    //word dao member
    private WordDao wordDao;
    //word list member
    private LiveData<List<Word>> allWords;

    //constructor - gets handle to db and init members
    WordRepository(Application application){
        //get handle to db
        WordRoomDatabase database = WordRoomDatabase.getDatabase(application);
        //init members
        wordDao = database.wordDao();
        allWords = wordDao.getAllWords();
    }

    //wrapper method to return cached words as LiveData
    LiveData<List<Word>> getAllWords() {
        return allWords;
    }

    //wrapper method for insert - AsyncTask to call insert() on non-UI thread
    public void insert (Word word){
        new insertAsyncTask(wordDao).execute(word);
    }

    //private inner asyncTask class
    private static class insertAsyncTask extends AsyncTask<Word, Void, Void>{
        private WordDao asyncTaskDao;
        //constructor
        insertAsyncTask(WordDao dao){
            asyncTaskDao = dao;
        }
        //do in background method
        @Override
        protected Void doInBackground(Word... words) {
            asyncTaskDao.insert(words[0]);
            return null;
        }
    }
}
