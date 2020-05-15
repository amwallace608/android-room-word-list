package com.amwallace.roomwordslist.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.amwallace.roomwordslist.Model.Word;
import com.amwallace.roomwordslist.R;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private final LayoutInflater inflater;
    private List<Word> wordList;    //cached copy of words

    //adapter constructor
    public WordListAdapter(Context context) {inflater = LayoutInflater.from(context); }

    //inflate word item view, return in new viewholder
    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.word_item, parent, false);
        return new WordViewHolder(itemView);
    }
    //bind word data to view/set textview
    @Override
    public void onBindViewHolder(@NonNull WordListAdapter.WordViewHolder holder, int position) {
        //check if there are words in list
        if (wordList != null){
            //set textview to word at current position
            holder.wordItemTxt.setText(wordList.get(position).getWord());
        } else {
            //word data not ready yet
            holder.wordItemTxt.setText("No Word Available");
        }
    }

    //set word list method
    public void setWordList(List<Word> words){
        //set list
        wordList = words;
        //notify adapter to update
        notifyDataSetChanged();
    }

    //get number of words in list method
    @Override
    public int getItemCount() {
        if(wordList != null){
            //wordlist has words, return number of words in list
            return wordList.size();
        } else {
            //first time called wordlist is null, return size 0
            return 0;
        }
    }

    //inner class WordViewHolder - manage view for one list item
    class WordViewHolder extends RecyclerView.ViewHolder {
        //word item text view
        private final TextView wordItemTxt;
        //constructor - setup word item text view
        private WordViewHolder(View itemView){
            super(itemView);
            wordItemTxt = itemView.findViewById(R.id.wordItemTxt);
        }
    }
}
