package com.ctech.fisher.geoquiz;

class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mAnsweredCorrect;

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

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    Question(int textResId, boolean answerTrue, boolean cheated, boolean answeredCorrect){
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mCheated = cheated;
        mAnsweredCorrect = answeredCorrect;
    }

    int getTextResId() {
        return mTextResId;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isAnsweredCorrect() {
        return mAnsweredCorrect;
    }

    public void setAnsweredCorrect(boolean answeredCorrect) {
        mAnsweredCorrect = answeredCorrect;
    }
}
