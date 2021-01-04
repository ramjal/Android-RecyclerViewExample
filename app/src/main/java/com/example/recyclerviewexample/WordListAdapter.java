package com.example.recyclerviewexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private final LinkedList<String> mWordList;
    final OnWordClickListener onWordClickListener;
    private LayoutInflater mInfrater;
    private RecyclerView mParent;

    public WordListAdapter(Context context, LinkedList<String> wordList, OnWordClickListener onWordClickListener) {
        mWordList = wordList;
        mInfrater = LayoutInflater.from(context);
        this.onWordClickListener = onWordClickListener;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInfrater.inflate(R.layout.wordlist_item, parent, false);
        mParent = (RecyclerView) parent;
        return new WordViewHolder(mItemView, onWordClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        String mCurrent = mWordList.get(position);
        holder.wordItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView wordItemView;
        final OnWordClickListener onWordClickListener;

        public WordViewHolder(View itemView, OnWordClickListener onWordClickListener) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.onWordClickListener = onWordClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //will call onWordClick inside first fragment
            onWordClickListener.onWordClick(getAdapterPosition());
        }

    }

    public interface OnWordClickListener {
        void onWordClick(int position);
    }

}
