package ecourse.base.ExamMagnament.ForListener;

public class MissingAnswer extends Question{

    private String missingWord;

    private String afterMissingWord;
    public MissingAnswer(String question) {
        super(question);
    }

    public MissingAnswer() {
        super("");
    }

    public void addMissingWord(String missingWord) {
        this.missingWord = missingWord;
    }

    public String getMissingWord() {
        return missingWord;
    }

    public void addAfterMissingWord(String s) {
        this.afterMissingWord = s;
    }
}
