package com.example.recyclerviewexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewexample.databinding.FragmentFirstBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.LinkedList;

public class FirstFragment extends Fragment implements WordListAdapter.OnWordClickListener {

    private FragmentFirstBinding binding;
    //private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;

    private final LinkedList<String> mWordList = new LinkedList<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //region recycler view setup
        // Put initial data into the word list.
        initializeWordList();
        // Create an adapter and supply the data to be displayed.
        mAdapter = new WordListAdapter(view.getContext(), mWordList, this);
        // Connect the adapter with the RecyclerView.
        binding.recView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        binding.recView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        binding.recView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Testing", Toast.LENGTH_SHORT).show();
            }
        });
        //endregion

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        //FloatingActionButton clicked
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertNewItem();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Reset the list
        if (id == R.id.action_reset) {
            initializeWordList();
            mAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //This is from the interface inside WordViewHolder
    @Override
    public void onWordClick(int position) {
        changeWordListItem(position);
    }

    private void changeWordListItem(int position) {
        // Use that to access the affected item in mWordList.
        String element = mWordList.get(position);
        // Change the word in the mWordList.
        mWordList.set(position, element + " - Clicked!");
        // Notify the adapter, that the data has changed so it can
        // update the RecyclerView to display the data.
        mAdapter.notifyDataSetChanged();
    }

    private void insertNewItem() {
        int wordListSize = mWordList.size();
        // Add a new word to the wordList.
        mWordList.addLast("+ Word " + wordListSize);
        // Notify the adapter, that the data has changed.
        mAdapter.notifyItemInserted(wordListSize);
        // Scroll to the bottom.
        //mAdapter.notifyDataSetChanged();
        binding.recView.smoothScrollToPosition(wordListSize);
        //binding.recView.getLayoutManager().ref
    }

    private void initializeWordList() {
        mWordList.clear();
        for (int i = 0; i < 20; i++) {
            mWordList.addLast("Word " + i);
        }
    }



}