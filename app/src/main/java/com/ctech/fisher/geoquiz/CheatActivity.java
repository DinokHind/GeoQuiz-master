package com.ctech.fisher.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String CHEAT_INDEX = "cheat";
    private static final String INT_INDEX = "int";
    private static final String EXTRA_ANSWER_IS_TRUE = "com.ctech.fisher.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_WAS_SHOWN = "com.ctech.fisher.geoquiz.answer_was_shown";
    private boolean mCheated;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_WAS_SHOWN, false);
    }

    public boolean mAnswerIsTrue;

    public int mesResId = R.string.warning_text;
    public TextView mAnswerTextView;
    public Button mShowAnswerButton;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(CHEAT_INDEX, mCheated);
        savedInstanceState.putInt(INT_INDEX, mesResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        if (savedInstanceState != null){
            mAnswerTextView = findViewById(R.id.answer_text_view);
            mCheated = savedInstanceState.getBoolean(CHEAT_INDEX, false);
            setAnswerShownResult(mCheated);
            mesResId = savedInstanceState.getInt(INT_INDEX);
            mAnswerTextView.setText(mesResId);
        }

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mShowAnswerButton = findViewById(R.id.show_answer_button);
        mAnswerTextView = findViewById(R.id.answer_text_view);

        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mesResId = R.string.true_button;
                } else {
                    mesResId = R.string.false_button;
                }
                mAnswerTextView.setText(mesResId);
                setAnswerShownResult(true);
            }
        });
        }

       private void setAnswerShownResult(boolean isAnswerShown) {
            mCheated = isAnswerShown;
            Intent toReturn = new Intent();
            toReturn.putExtra(EXTRA_ANSWER_WAS_SHOWN, isAnswerShown);
            setResult(RESULT_OK, toReturn);
        }

}
