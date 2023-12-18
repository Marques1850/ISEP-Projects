# US 2005

## 1. Context

*It is the first time the task is assigned to be developed.*

## 2. Requirements

**US 2005** As Student, I want to view a list of my grades

*Acceptance Criteria:*

- *AC1* - The list of grades is presented in a table with the following columns: Student, Grade, Exam (code, name, description,CourseCode, opendDate, closeDate)

*Other US Dependencies:*
- US 2004 (As Student, I want to take an exam)
- 
## 3. Analysis


### Domain Model ###

![a domain model](Domain Model_US_2005.svg)


### Use Case Diagram ###

![a use case diagram](UC Diagram.svg)
### System Sequence Diagrams ###

#### Register User ####
![a system sequence diagram](US_2005-SSD.svg)


## 4. Design

### 4.1. Realization

### 4.2. Class Diagram

![a class diagram](class-diagram-01.svg "A Class Diagram")

### Sequence Diagram

![a sequence diagram](US_2005-SD.svg)


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
