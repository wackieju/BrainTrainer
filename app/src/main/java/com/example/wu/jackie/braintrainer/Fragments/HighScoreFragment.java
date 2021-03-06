package com.example.wu.jackie.braintrainer.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wu.jackie.braintrainer.Adapter.HighScoreListAdapter;
import com.example.wu.jackie.braintrainer.R;
import com.example.wu.jackie.braintrainer.db.HighScoreEntity;
import com.example.wu.jackie.braintrainer.db.HighScoreViewModel;

import java.util.List;

public class HighScoreFragment extends Fragment {

    private HighScoreEntity mHighScore;
    private String playerName;
    private int playerScore;
    private int percent;

    private HighScoreViewModel mHighScoreViewModel;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.high_score_fragment, container, false);

        mHighScore = new HighScoreEntity(playerName, playerScore, percent);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.highScoreRecyclerView);
        final HighScoreListAdapter adapter = new HighScoreListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mHighScoreViewModel = ViewModelProviders.of(this).get(HighScoreViewModel.class);

        mHighScoreViewModel.insert(mHighScore);

        mHighScoreViewModel.getAllHighScores().observe(HighScoreFragment.this, new Observer<List<HighScoreEntity>>() {
            @Override
            public void onChanged(@Nullable List<HighScoreEntity> highScores) {
                adapter.setHighScores(highScores);
            }
        });

        return view;
    }

    public void sendHighScore(HighScoreEntity highScore) {
        this.playerName = highScore.getPlayerName();
        this.playerScore = highScore.getPlayerScore();
        this.percent = highScore.getPercentQuestionsCorrect();
    }
}
