package com.amwallace.roomwordslist.Data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.amwallace.roomwordslist.Model.Word;

//Room database class for Word entities
@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    //Abstract getter method for word dao
    public abstract WordDao wordDao();
    //database instance
    private static WordRoomDatabase INSTANCE;
    //create database as a singleton
    public static WordRoomDatabase getDatabase(final Context context){
        //prevent opening multiple instances of database at the same time
        if(INSTANCE == null){
            synchronized (WordRoomDatabase.class){
                if (INSTANCE == null){
                    //create database word_database
                    //wipe/rebuild instead of migrating if no migration obj
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //delete all content and repopulate db when app is started w/ callback
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        //override onOpen method
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            //create and exec asyncTask to populate db
            new PopulateDBAsync(INSTANCE).execute();
        }
    };


    //populate db in background asyncTask
    private static class PopulateDBAsync extends AsyncTask<Void,Void,Void> {
        private final WordDao dao;
        String[] words = {"Asymptote", "Deliverable", "Ambitious"};
        //constructor w/ Dao from db
        PopulateDBAsync(WordRoomDatabase db) {
            dao = db.wordDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //start app with clean db
            dao.deleteAll();
            //populate db w/ words in list
            for(int i = 0; i <= words.length - 1; i++) {
                Word tempWord = new Word(words[i]);
                dao.insert(tempWord);
            }
            return null;
        }
    }

}
