package ecourse.base.eCourseSystemUser;

public class eCourseSystemUserMapper {

    public static eCourseSystemUserDto toDto(eCourseSystemUser user) {
        return new eCourseSystemUserDto(
                user.eCourseUserEmail().toString(),
                user.systemUser().toString(),
                user.eCourseUserNIF().toString(),
                user.eCourseUserBirthDate().toString(),
                user.eCourseUserRole().toString(),
                user.eCourseUserStatus().toString()
        );
    }
}

