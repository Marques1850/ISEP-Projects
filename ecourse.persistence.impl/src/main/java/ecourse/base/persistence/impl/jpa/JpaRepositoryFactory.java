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
package ecourse.base.persistence.impl.jpa;

import ecourse.base.Application;
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
import ecourse.base.clientusermanagement.repositories.SignupRequestRepository;
import ecourse.base.courseManagement.repositories.CourseRepository;
import ecourse.base.infrastructure.persistence.RepositoryFactory;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.jpa.JpaAutoTxUserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import ecourse.base.usermanagement.UserRegistration.eCourseUserRepository;

/**
 *
 * Created by nuno on 21/03/16.
 */
public class JpaRepositoryFactory implements RepositoryFactory {

    @Override
    public UserRepository users(final TransactionalContext autoTx) {
        return new JpaAutoTxUserRepository(autoTx);
    }

    @Override
    public UserRepository users() {
        return new JpaAutoTxUserRepository(Application.settings().getPersistenceUnitName(),
                Application.settings().getExtendedPersistenceProperties());
    }

    @Override
    public eCourseUserRepository eCourseUsers(final TransactionalContext autoTx) {
        return new JpaeCourseUserRepository(autoTx);
    }

    @Override
    public eCourseUserRepository eCourseUsers() {
        return new JpaeCourseUserRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public JpaClientUserRepository clientUsers(final TransactionalContext autoTx) {
        return new JpaClientUserRepository(autoTx);
    }

    @Override
    public JpaClientUserRepository clientUsers() {
        return new JpaClientUserRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public SignupRequestRepository signupRequests(final TransactionalContext autoTx) {
        return new JpaSignupRequestRepository(autoTx);
    }

    @Override
    public SignupRequestRepository signupRequests() {
        return new JpaSignupRequestRepository(Application.settings().getPersistenceUnitName());
    }


    @Override
    public CourseRepository courses(final TransactionalContext autoTx) {
        return new JpaCourseRepository(autoTx);
    }

    @Override
    public CourseRepository courses() {
        return new JpaCourseRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public ExamRepository exams(final TransactionalContext autoTx) {
        return new JpaExamRepository(autoTx);
    }

    @Override
    public ExamRepository exams() {
        return new JpaExamRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public FormativeExamRepository formativeexams(final TransactionalContext autoTx) {
        return new JpaFormativeExamRepository(autoTx);
    }

    @Override
    public FormativeExamRepository formativeexams() {
        return new JpaFormativeExamRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public EnrollmentRepository enrollments (final TransactionalContext autoTx) {
        return new JpaEnrollmentRepository(autoTx);
    }

    @Override
    public EnrollmentRepository enrollments() {
        return new JpaEnrollmentRepository(Application.settings().getPersistenceUnitName());
    }
    public MeetingRepository meetings(TransactionalContext autoTx) {
        return new JpaMeetingRepository(autoTx);
    }

    @Override
    public MeetingRepository meetings() {
        return new JpaMeetingRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public MeetingInviteRepository meetingInvites(TransactionalContext autoTx) {return new JpaMeetingInviteRepository(autoTx);}
    @Override
    public MeetingInviteRepository meetingInvites() {
        return new JpaMeetingInviteRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public ClassRepository classes(TransactionalContext autoTx) {
        return new JpaClassRepository(autoTx);
    }

    @Override
    public ClassRepository classes() {
        return new JpaClassRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public ExtraClassRepository extraClasses(TransactionalContext autoTx) {
        return new JpaExtraClassRepository(autoTx);
    }

    @Override
    public ExtraClassRepository extraClasses() {
        return new JpaExtraClassRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public BoardRepository BoardRepository(TransactionalContext autoTx) {
        return new JpaBoardRepository(autoTx);
    }

    @Override
    public BoardRepository BoardRepository() {
        return new JpaBoardRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public PostItRepository PostItRepository(TransactionalContext autoTx) {
        return new JpaPostItRepository(autoTx);
    }

    @Override
    public PostItRepository PostItRepository() {
        return new JpaPostItRepository (Application.settings().getPersistenceUnitName());
    }

    public BoardChangesRepository BoardChangesRepository(TransactionalContext autoTx) {
        return new JpaBoardChangesRepository(autoTx);
    }

    public BoardChangesRepository BoardChangesRepository() {
        return new JpaBoardChangesRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public QuestionRepository questions(TransactionalContext autoTx) {
        return new JpaQuestionRepository(autoTx);
    }

    @Override
    public QuestionRepository questions() {
        return new JpaQuestionRepository(Application.settings().getPersistenceUnitName());
    }


    @Override
    public TransactionalContext newTransactionalContext() {
        return JpaAutoTxRepository.buildTransactionalContext(Application.settings().getPersistenceUnitName(),
                Application.settings().getExtendedPersistenceProperties());
    }



}
