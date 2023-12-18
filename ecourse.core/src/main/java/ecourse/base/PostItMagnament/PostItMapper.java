package ecourse.base.PostItMagnament;

import ecourse.base.PostItMagnament.domain.PostIt;

public class PostItMapper {

    public static PostItDto toDto(PostIt postIt) {
        return new PostItDto(
                postIt.getContent().content()
        );
    }
}
