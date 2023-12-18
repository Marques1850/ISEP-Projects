# Domain Model alterations #

=================================================================


### Packages: ###

* Exam Package - Updeated  
* Question Package - Added
* ShareBoard Package - Updated
* Post-It Package - Updated

-----------------------------------------------------------------

### Classes: ###

* class FormativeExam
* class TakeFormativeExamService
* class ListFormativeExamCoursesService
* class CreatePostItService

-----------------------------------------------------------------

### Services: ###

* VerifQuestionService - Added
* TakeExamService - Added
* ListExamCoursesService - Added

-----------------------------------------------------------------

### Relations: ###

* Updated:
    * `eCourseSystemUser "*" -- "1" Exam : Student takes >` to `eCourseSystemUser "*" -- "*" Exam : Student takes >`
    * `Course "*" - "*" eCourseSystemUser : teaches <` to `Course "*" - "*" eCourseSystemUser : teacher teaches <`
    * `SharedBoard "1" -- "1..20" Line : has >` to `Board "1" -- "1..20" RowCell : has >`
    * `SharedBoard "1" -- "1..10" Column : has >` to `Board "1" -- "1..10" ColumnCell : has >`

* Added:
    * `Course "*" - "*" eCourseSystemUser : student is in a <`
    * `eCourseSystemUser --> "*" Board : owns >`
    * `eCourseSystemUser --> "*" Board : shared to <`
    * `eCourseSystemUser --> "*" PostIt : creates a >`
    * `eCourseSystemUser --> "*" PostIt : updates a >`
    * `eCourseSystemUser --> "*" PostIt : deletes a >`
    * `eCourseSystemUser --> "*" Content : creates a >`
    * `eCourseSystemUser --> "*" Content : updates a >`
    * `eCourseSystemUser --> "*" Content : deletes a >`
    * `Board "1" -- "*" PostIt : has >`
    * `ColumnCell --|> Cell`
    * `RowCell --|> Cell`
    * `PostIt "*" -- "*" Content : contains >`
    * `PostIt "*" -- "*" eCourseSystemUser : owns <`
    * `Exam "*" -- "1" CourseCode : has >`
    * `Exam "*" -- "*" ExamGrade : has >`
    * `ExamGrade "*" -- "1" eCourseSystemUser : has >`
    * `PostIt "*" -- "*" Content : contains >`
    * `PostIt "*" -- "*" eCourseSystemUser : owns <`
    * `eCourseSystemUser --> TakeExamService : use >`
    * `eCourseSystemUser --> ListExamCoursesService : use >`
    * `eCourseSystemUser --> VerifQuestionService : uses >`
    * `ExamGrade "1" -- "1" eCourseSystemUser : has >`
    * `eCourseSystemUser "*" -- "*" FormativeExam : Student take a >`
    * `eCourseSystemUser "1" -- "*" Question : Teacher creates >`
    * `eCourseSystemUser "1" -- "*" FormativeExam : Teacher creates >`
    * `FormativeExam "1" -- "1" ExamGrade : has >`
    * `FormativeExam "*" -- "1" CourseCode : has >`
    * `Course "1" -- "0..*" FormativeExam : related to <`
    * `eCourseSystemUser --> TakeFormativeExamService : use >`
    * `eCourseSystemUser --> ListFormativeExamCoursesService : use >`
    * `eCourseSystemUser --> CreatePostItService : use >`

* Deleted:
    * `Exam "1" -- "1" Header : has >`
    * `Exam "1" -- "*" Section : has >`
    * `Header --> "1" Description`
    * `Header --> "1" Date`
    * `Header --> "1" GradeType`
    * `Section "1" -- "1..*" Question : has >`
    * `Section --> "1..*" QuestionType`
    * `Cell "1" -- "1" Line : is in >`
    * `Cell "1" -- "1" Column : is in >`
    * `Cell "1" -- "1" Content : has >`
    * `Cell --> "1..*" MovementType`
    * `TextContent --|> Content`
    * `ImageContent --|> Content`

-----------------------------------------------------------------