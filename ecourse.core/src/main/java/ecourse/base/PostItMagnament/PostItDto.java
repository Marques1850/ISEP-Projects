package ecourse.base.PostItMagnament;

public class PostItDto {

    private String id;

    private String row;
    private String column;
    private String content;

    public PostItDto(String content) {
        this.content = content;
    }

    public String content() {
        return content;
    }

    @Override
    public String toString() {
        return "PostIt [" +
                "content=" + content +
                "]";
    }
}
