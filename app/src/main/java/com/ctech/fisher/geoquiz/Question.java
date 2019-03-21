package com.ctech.fisher.geoquiz;

class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    boolean isCheated() {
        return mCheated;
    }

    void setCheated(boolean cheated) {
        mCheated = cheated;
    }

    private boolean mCheated;

    boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    Question(int textResId, boolean answerTrue, boolean cheated){
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mCheated = cheated;
    }

    int getTextResId() {
        return mTextResId;
    }
}
