package de.avpptr.umweltzone.utils;

import java.util.ArrayList;
import java.util.List;

public class FaqDataProvider {

    List<String> mFaqQuestions;
    List<String> mFaqAnswers;

    public FaqDataProvider() {
        mFaqQuestions = new ArrayList<String>();
        mFaqAnswers = new ArrayList<String>();

        mFaqQuestions.add("Frage 1");
        mFaqQuestions.add("Frage 2");

        mFaqAnswers.add("Antwort 1");
        mFaqAnswers.add("Antwort 2");
    }

    public List<String> getFaqQuestions() {
        return mFaqQuestions;
    }

    public List<String> getFaqAnswers() {
        return mFaqAnswers;
    }


}
