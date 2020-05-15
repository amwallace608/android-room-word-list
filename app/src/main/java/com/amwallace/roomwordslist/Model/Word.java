package com.amwallace.roomwordslist.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//annotation to indicate class is entity in word table
@Entity(tableName = "word_table")
public class Word {
    //word acts as it's own primary key
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String word;

//constructor
    public Word(@NonNull String word) {
        this.word = word;
    }
//getters and setters

    @NonNull
    public String getWord() {
        return word;
    }

    public void setWord(@NonNull String word) {
        this.word = word;
    }
}