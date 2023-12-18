package ecourse.base.ExamMagnament.ForListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TrueOrFalse extends Question{

    private List<String> trueAnswers;

    private List<String> questions;


    public TrueOrFalse() {
        super("");
        questions = new ArrayList<>();
        trueAnswers = new ArrayList<>();
    }
    public TrueOrFalse(String question) {
        super(question);
        questions = new ArrayList<>();
        trueAnswers = new ArrayList<>();
    }

    public void addQuestions(String s) {
        this.questions.add(s);
    }

    public void setCorrectOption(String leters) {
        trueAnswers.add(leters);
    }

    public List<String> getQuestions() {
        return questions;
    }

    public List<String> getTrueAnswers() {
        return trueAnswers;
    }
}
