/*
 * Copyright (c) 2013-2023 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package ecourse.base.usermanagement.UserStatus;

import eapli.framework.application.UseCaseController;
import eapli.framework.general.domain.model.EmailAddress;
import ecourse.base.eCourseSystemUser.eCourseSystemUser;
import ecourse.base.eCourseSystemUser.eCourseSystemUserDto;
import ecourse.base.eCourseSystemUser.eCourseSystemUserMapper;
import ecourse.base.infrastructure.persistence.PersistenceContext;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;

/**
 *
 * Created by Sérgio Marques on 02/05/23.
 */
@UseCaseController
public class AlterUserStatusController {

    private final eCourseUserRepository userRep = PersistenceContext.repositories().eCourseUsers();

    private final eCourseSystemUserMapper userMapper = new eCourseSystemUserMapper();
    private eCourseSystemUserDto user;

    public eCourseSystemUserDto searchUser(String id) {
        eCourseSystemUser user = userRep.searchUser(EmailAddress.valueOf(id)).get();
        
        eCourseSystemUserDto userDto = userMapper.toDto(user);

        if (userDto == null) {
            return null;
        }

        return userDto;
    }

    public String alterUserStatus(eCourseSystemUserDto user) {
        eCourseSystemUser user1 = userRep.alterUserStatus(user);
        userRep.save(user1);
        user = eCourseSystemUserMapper.toDto(user1);
        return user.userStatus();
    }

}
