package ecourse.base.ExamMagnament.ForListener;

public class NumericalQuestion extends Question{

    private double answer;
    private double tolerance;

    public NumericalQuestion() {
        super("");
    }
    public NumericalQuestion(String question) {
        super(question);
    }

    public void setAnswer(double answer) {
        this.answer = answer;
    }
    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    public double getAnswer() {
        return answer;
    }

    public double getTolerance() {
        return tolerance;
    }
}
