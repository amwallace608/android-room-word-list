package com.amwallace.roomwordslist.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.amwallace.roomwordslist.Model.Word;

import java.util.List;

//annotation to identify as a DAO class for Room
@Dao
public interface WordDao {

    //insert word method - replace word if duplicate
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Word word);

    //delete all words method
    @Query("DELETE FROM word_table")
    void deleteAll();

    //get all words method
    @Query("SELECT * FROM word_table ORDER BY word ASC")
    LiveData<List<Word>> getAllWords();
}
