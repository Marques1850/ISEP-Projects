package ecourse.base.eCourseSystemUser;

public class eCourseSystemUserDto {
    private String systemUser;
    private String email;
    private String nif;
    private String birthDate;
    private String role;

    private String acronym;

    private String status;

    public eCourseSystemUserDto(String email, String systemUser, String nif, String birthDate, String role, String status) {
        this.systemUser = systemUser;
        this.email = email;
        this.nif = nif;
        this.birthDate = birthDate;
        this.role = role;
        this.status = status;
    }

    public eCourseSystemUserDto(String email, String systemUser, String nif, String birthDate, String acronym) {
        this.systemUser = systemUser;
        this.email = email;
        this.nif = nif;
        this.birthDate = birthDate;
        this.acronym = acronym;
    }
    public eCourseSystemUserDto(){}

    public String getSystemUser() {
        return systemUser;
    }

    public String getName() {
        return systemUser;
    }

    public String getEmail() {
        return email;
    }

    public String getNif() {
        return nif;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getRole() {
        return role;
    }

    public String userStatus() {
        return status;
    }

    public String getAcronym() {
        return acronym;
    }


    @Override
    public String toString() {
        return "eCourseSystemUser [ " +
                "systemUser=" + systemUser +
                " | email='" + email +
                " | nif='" + nif +
                " | birthDate='" + birthDate +
                " | role='" + role +
                " | acronym='" + acronym +
                " | status='" + status +
                " ]";
    }
}
