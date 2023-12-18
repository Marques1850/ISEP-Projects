package ecourse.base.ExamMagnament.ForListener;

public class ShortQuestion extends Question{

    String wordNeeded;

    public ShortQuestion(String question) {
        super(question);
    }

    public ShortQuestion() {
        super("");
    }

    public void addWordNeeded(String wordNeeded) {
        this.wordNeeded = wordNeeded;
    }

    public String getWordNeeded() {
        return wordNeeded;
    }
}
