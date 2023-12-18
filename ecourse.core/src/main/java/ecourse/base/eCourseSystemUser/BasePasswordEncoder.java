package ecourse.base.eCourseSystemUser;

import ecourse.base.usermanagement.domain.BasePasswordPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.cert.Extension;

public class BasePasswordEncoder implements PasswordEncoder {

    public BasePasswordEncoder() {
        super();
    }

    public static Extension getInstance() {
        return null;
    }

    public String encode(CharSequence rawPassword) {
        return null;
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return false;
    }

    public static PasswordEncoder getEncoder() {
        return new BasePasswordEncoder();
    }

}
