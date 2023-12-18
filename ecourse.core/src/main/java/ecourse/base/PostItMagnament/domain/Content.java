package ecourse.base.PostItMagnament.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Content {
    @Column
    private String content;
    @Column
    private String previousContent;

    public Content(String content) {
        this.content = content;
    }

    public Content() {

    }

    public String seePostItContent() {
        return content;
    }

    public void updatePostItContent(String content) {
        this.previousContent= this.content;
        this.content = content;
    }

    public String content() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }
}
