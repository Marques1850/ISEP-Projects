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
package ecourse.base.persistence.impl.inmemory;

import ecourse.base.EnrollmentManagment.repositories.EnrollmentRepository;
import ecourse.base.ExamMagnament.repositories.ExamRepository;
import ecourse.base.ExamMagnament.repositories.FormativeExamRepository;
import ecourse.base.ExamMagnament.repositories.QuestionRepository;
import ecourse.base.MeetingManagement.InvitationManagement.repositories.MeetingInviteRepository;
import ecourse.base.MeetingManagement.repositories.MeetingRepository;
import ecourse.base.PostItMagnament.PostItRepository;
import ecourse.base.boardManagment.repositories.BoardChangesRepository;
import ecourse.base.boardManagment.repositories.BoardRepository;
import ecourse.base.classSchedule.repositories.ClassRepository;
import ecourse.base.classSchedule.repositories.ExtraClassRepository;
import ecourse.base.clientusermanagement.repositories.ClientUserRepository;
import ecourse.base.clientusermanagement.repositories.SignupRequestRepository;
import ecourse.base.courseManagement.repositories.CourseRepository;
import ecourse.base.infrastructure.bootstrapers.BaseBootstrapper;
import ecourse.base.infrastructure.persistence.RepositoryFactory;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.inmemory.InMemoryUserRepository;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;

/**
 *
 * Created by nuno on 20/03/16.
 */
public class InMemoryRepositoryFactory implements RepositoryFactory {

    static {
        // only needed because of the in memory persistence
        new BaseBootstrapper().execute();
    }

    @Override
    public UserRepository users(final TransactionalContext tx) {
        return new InMemoryUserRepository();
    }

    @Override
    public UserRepository users() {
        return users(null);
    }

    @Override
    public eCourseUserRepository eCourseUsers(TransactionalContext autoTx) {
        return null;
    }

    @Override
    public eCourseUserRepository eCourseUsers() {
        return null;
    }

    @Override
    public ClientUserRepository clientUsers(final TransactionalContext tx) {

        return new InMemoryClientUserRepository();
    }

    @Override
    public ClientUserRepository clientUsers() {
        return clientUsers(null);
    }

    @Override
    public SignupRequestRepository signupRequests() {
        return signupRequests(null);
    }

    @Override
    public CourseRepository courses(TransactionalContext autoTx) {
        /**
        * NEED TO IMPLEMENT
         */
        return null;
    }

    @Override
    public CourseRepository courses() {
        /**
         * NEED TO IMPLEMENT
         */
        return null;
    }

    @Override
    public ExamRepository exams(TransactionalContext autoTx) {
        /**
         * NEED TO IMPLEMENT
         */
        return null;
    }

    @Override
    public ExamRepository exams() {
        /**
         * NEED TO IMPLEMENT
         */
        return null;
    }

    @Override
    public FormativeExamRepository formativeexams(TransactionalContext autoTx) {
        /**
         * NEED TO IMPLEMENT
         */
        return null;
    }

    @Override
    public FormativeExamRepository formativeexams() {
        /**
         * NEED TO IMPLEMENT
         */
        return null;
    }

    @Override
    public EnrollmentRepository enrollments(TransactionalContext autoTx) {
        return null;
    }

    @Override
    public EnrollmentRepository enrollments() {
        return null;
    }

    @Override
    public MeetingRepository meetings(TransactionalContext autoTx) {
        /**
         * NEED TO IMPLEMENT
         */
        return null;
    }

    @Override
    public MeetingRepository meetings() {
        /**
         * NEED TO IMPLEMENT
         */
        return null;
    }

    @Override
    public ClassRepository classes(TransactionalContext autoTx) {
        return null;
    }

    @Override
    public ClassRepository classes() {
        return null;
    }

    @Override
    public ExtraClassRepository extraClasses(TransactionalContext autoTx) {return null;}

    @Override
    public ExtraClassRepository extraClasses() {return null;}

    @Override
    public MeetingInviteRepository meetingInvites(TransactionalContext autoTx) {
        return null;
    }

    @Override
    public MeetingInviteRepository meetingInvites() {
        return null;
    }

    @Override
    public BoardRepository BoardRepository(TransactionalContext autoTx) {
        return null;
    }

    @Override
    public BoardRepository BoardRepository() {
        return null;
    }

    @Override
    public PostItRepository PostItRepository(TransactionalContext autoTx) {
        return null;
    }

    @Override
    public PostItRepository PostItRepository() {
        return null;
    }

    @Override
    public BoardChangesRepository BoardChangesRepository(TransactionalContext autoTx) {
        return null;
    }

    @Override
    public BoardChangesRepository BoardChangesRepository() {
        return null;
    }

    @Override
    public QuestionRepository questions(TransactionalContext autoTx) {
        return null;
    }

    @Override
    public QuestionRepository questions() {
        return null;
    }

    @Override
    public SignupRequestRepository signupRequests(final TransactionalContext tx) {
        return new InMemorySignupRequestRepository();
    }

    @Override
    public TransactionalContext newTransactionalContext() {
        // in memory does not support transactions...
        return null;
    }

}
