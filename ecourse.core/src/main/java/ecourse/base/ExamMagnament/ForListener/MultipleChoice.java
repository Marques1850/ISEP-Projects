package ecourse.base.ExamMagnament.ForListener;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoice extends Question{

    private List<String> options;
    private int correctAnswer;

    public MultipleChoice() {
        super("");
        options = new ArrayList<>();
    }

    public MultipleChoice(String question) {
        super(question);
        options = new ArrayList<>();
    }

    public void addOption(String option) {
        options.add(option);
    }

    public void setCorrectOption(int choiceNumber) {
        this.correctAnswer = choiceNumber;
    }

    public String getCorrectOption() {
        Integer i = correctAnswer;
        i++;
        return i.toString();
    }

    public List<String> getOptions() {
        return options;
    }

    /*
    public MultipleChoice(String question, String[] options, int correctAnswer) {
        super(question);
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

     */
}
