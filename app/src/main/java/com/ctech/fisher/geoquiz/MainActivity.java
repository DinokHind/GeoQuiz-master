package com.ctech.fisher.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String CHEAT_NUM_INDEX = "cheat_number";
    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    public boolean userPressedTrue;
    private TextView mQuestionTextView;

    private boolean mIsCheater;
    private int mNumCheats = 0;

    private Question[] mQuestionBank = new Question[]{

            new Question(R.string.question_australia, true, false),
            new Question(R.string.question_canada, true, false),
            new Question(R.string.question_dead_sea, true, false),
            new Question(R.string.question_greenland, false, false),
            new Question(R.string.question_japan, true, false),
            new Question(R.string.question_montana, false, false),
    };

    private int mCurrentIndex = 0;

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onResume has been called");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume has been called");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause has been called");
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        for (int i = 0; i < mQuestionBank.length; ++i){
            savedInstanceState.putBoolean(Integer.toString(i), mQuestionBank[i].isCheated());
        }
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(CHEAT_NUM_INDEX, mNumCheats);
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop has been called");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy has been called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate(Bundle) has been called");

        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            for (int i = 0; i < mQuestionBank.length; ++i){
                mQuestionBank[i].setCheated(savedInstanceState.getBoolean(Integer.toString(i), false));
                mNumCheats = savedInstanceState.getInt(CHEAT_NUM_INDEX, 0);
            }
        }

        mQuestionTextView = findViewById(R.id.question_text_view);
        updateQuestion();

        Button trueButton = findViewById(R.id.true_button);
        Button falseButton = findViewById(R.id.false_button);
        ImageButton nextButton = findViewById(R.id.next_button);
        ImageButton previousButton = findViewById(R.id.previous_button);
        Button cheatButton = findViewById(R.id.cheat_button);

        trueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                userPressedTrue = true;
                checkAnswer();
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                userPressedTrue = false;
                checkAnswer();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mIsCheater = false;

                if (mCurrentIndex == mQuestionBank.length-1){
                    mCurrentIndex = 0;
                    updateQuestion();
                } else {
                    mCurrentIndex = (mCurrentIndex + 1);
                    updateQuestion();
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mIsCheater = false;

                if (mCurrentIndex == 0){
                    mCurrentIndex = mQuestionBank.length-1;
                    updateQuestion();
                }
                else {
                    mCurrentIndex = (mCurrentIndex - 1);
                    updateQuestion();
                }
            }
        });

        cheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (mQuestionBank[mCurrentIndex].isCheated()) {
                        Toast toast = Toast.makeText(MainActivity.this, R.string.already_cheated_toast, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.show();

                }else{
                    if (mNumCheats < 3) {
                        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                        Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                        startActivityForResult(intent, REQUEST_CODE_CHEAT);
                    } else {
                        Toast toast = Toast.makeText(MainActivity.this, R.string.max_cheat_toast, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.show();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT){
            if (data == null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            if (mIsCheater){
                mQuestionBank[mCurrentIndex].setCheated(true);
                mNumCheats = (mNumCheats + 1);
            }
        }
    }

    private void updateQuestion(){
        Log.d(TAG, "Updating message text", new Exception());
        int questionResourceId = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(questionResourceId);
    }

    private void checkAnswer(){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId;

        if (mQuestionBank[mCurrentIndex].isCheated()){
            messageResId = R.string.judgement_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast toast = Toast.makeText(MainActivity.this, messageResId,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0,0);
        toast.show();
    }
}