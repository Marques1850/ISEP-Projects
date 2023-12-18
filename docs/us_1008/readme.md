# US 1008

*As Student, I want to request my enrollment in a course*

## 1. Context

*It is the first time this tasked is assigned to be developed*

>### Client Forum

#### None

## 2. Requirements

**US 1008** As Student, I want to request my enrollment in a course

*Regarding this requirement we understand that it relates to the request of students applications to certain courses*

## 3. Analysis

*This user story was tackled in the following ways:*

* Use a service to do the work and intercomunicate with the repository.
* The student is presented with a list of courses with open enrollments.
* The service then works with the repository to save the new Enrollment
* The controller is used as a bridge between the UI and the service.

## 4. Design

![SD](SD.svg "US 1008 Sequence Diagram")

### 4.1. Realization

>SELECT  * FROM Course WHERE
>EnrollmentStatus=Open

### 4.2. Class Diagram

![CD](CD.svg "US 1008 Class Diagram")

### 4.3. Applied Patterns

### 4.4. Tests

**Test 1:** *Verifies that it is not possible to create an instance of the Example class with null values.*

```
@Test(expected = IllegalArgumentException.class)
public void ensureNullIsNotAllowed() {
	Example instance = new Example(null, null);
}
````

## 5. Implementation

*In this section the team should present, if necessary, some evidencies that the implementation is according to the design. It should also describe and explain other important artifacts necessary to fully understand the implementation like, for instance, configuration files.*

*It is also a best practice to include a listing (with a brief summary) of the major commits regarding this requirement.*

## 6. Integration/Demonstration

*In this section the team should describe the efforts realized in order to integrate this functionality with the other parts/components of the system*

*It is also important to explain any scripts or instructions required to execute an demonstrate this functionality*

## 7. Observations

*This section should be used to include any content that does not fit any of the previous sections.*

*The team should present here, for instance, a critical prespective on the developed work including the analysis of alternative solutioons or related works*

*The team should include in this section statements/references regarding third party works that were used in the development this work.*