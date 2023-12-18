package ecourse.base.ExamMagnament.ForListener;

public class Question {

    private String question;

    private double cotation;

    public Question(String question) {
        this.question = question;
    }

    public void addQuestion(String questionDescription) {
        this.question = questionDescription;
    }

    public void setCotation(double cotation) {
        this.cotation = cotation;
    }

    public double getCotation() {
        return cotation;
    }

    public String getQuestion() {
        return question;
    }
}
