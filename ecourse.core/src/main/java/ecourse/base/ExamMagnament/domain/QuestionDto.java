package ecourse.base.ExamMagnament.domain;

import ecourse.base.ExamMagnament.ExamDto;

public class QuestionDto {

    private String content;
    private String type;

    public QuestionDto(String content, String type) {
        this.content = content;
        this.type = type;
    }

    public static QuestionDto toDto(Question question) {
        return new QuestionDto(question.content().toString(),
                question.type().toString());
    }

    public QuestionDto() {
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }


    @Override
    public String toString() {
        return "QuestionDto[ " +
                "content =" + content +
                " | type='" + type +
                " ]";
    }
}
