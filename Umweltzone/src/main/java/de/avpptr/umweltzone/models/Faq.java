package de.avpptr.umweltzone.models;

public class Faq extends Object {

    public int position;
    public String question;
    public String answer;
    public String sourceUrl;

    @Override
    public String toString() {
        return "Position: " + position +
                ", Question: " + question +
                ", Answer: " + answer +
                ", SourceUrl: " + sourceUrl;
    }

}
