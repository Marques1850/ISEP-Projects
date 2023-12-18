# US 3010

## 1. Context

*It is the first time the task is assigned to be developed.*

## 2. Requirements

**US 3010** As User,I want to archive a board I own.

*Acceptence Criteria:*

- This functional part of the system has very specific technical requirements, particularly some concerns about synchronization problems.

- In fact, several clients will try to concurrently update boards.

- As such, the solution design and implementation must be based on threads, condition variables and mutexes. Specific requirements will be provided in SCOMP.

## 3. Analysis

*In this section, the team should report the study/analysis/comparison that was done in order to take the best design decisions for the requirement. This section should also include supporting diagrams/artifacts (such as domain model; use case diagrams, etc.),*

### Domain Model ###

![a domain model](Domain_Model_US_3010.svg)


### Use Case Diagram ###

![a use case diagram](UC_Diagram.svg)


### System Sequence Diagrams ###

#### Register User ####
![a system sequence diagram](SSD_US_3010.svg)




## 4. Design

*In this sections, the team should present the solution design that was adopted to solve the requirement. This should include, at least, a diagram of the realization of the functionality (e.g., sequence diagram), a class diagram (presenting the classes that support the functionality), the identification and rational behind the applied design patterns and the specification of the main tests used to validade the functionality.*

### 4.1. Realization

### 4.2. Class Diagram

![a class diagram](class-diagram-01.svg "A Class Diagram")

### Sequence Diagram

![a sequence diagram](SD_US_3010.svg)


### 4.3. Applied Patterns

* **Model-View-Controller (MVC):** The `ArchiveBoardUI` class represents the **View** component, which is responsible for displaying information to the user and receiving user input. The `ArchiveBoardController` class represents the **Controller** component, which handles user input and updates the model accordingly. The `ShareBoard` class represents the **Model** component, which manages the data and business logic of the application.

* **Pure Fabrication:** The **ShareBoardServer** and **AuthorizationService** are responsible for handling specific tasks that do not directly correspond to a problem domain concept.

* **Singleton:** The `SharedBoardServer` class uses the `getInstance()` method to ensure that only one instance of `ArchiveBoardController` is created.

* **Factory Method:** The `RepositoryFactory` class is used to create objects without specifying the exact class of object that will be created.

* **Repository:** The `BoardRepsitory` and `eCourseUserRepository` classes are used to manage the data storage and retrieval for their respective entities.

* **Publisher/Subscriber:** The `EventPublisher` class is used to publish events to subscribers.

* **Data Mapper:** The `ShareBoardDto` class is used to map between the data model and the domain model.

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
