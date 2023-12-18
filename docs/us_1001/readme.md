# US 1001

## 1. Context

*It is the first time the task is assigned to be developed.*

## 2. Requirements

**US 1001** As Manager, I want to be able to register, disable/enable, and list users of the system (Teachers and Students, as well as Managers)

*For now there is no Acceptence Criteria for this US*

## 3. Analysis

*In this section, the team should report the study/analysis/comparison that was done in order to take the best design decisions for the requirement. This section should also include supporting diagrams/artifacts (such as domain model; use case diagrams, etc.),*

### Domain Model ###

![a domain model](Domain Model_US_1001.svg)


### Use Case Diagram ###

![a use case diagram](UC Diagram.svg)


### System Sequence Diagrams ###

#### Register User ####
![a system sequence diagram 1](SSD-1 US_1001.svg)

#### Disable/Enable User ####
![a system sequence diagram 2](SSD-2 US_1001.svg)

#### List Users ####
![a system sequence diagram 3](SSD-3 US_1001.svg)




## 4. Design

*In this sections, the team should present the solution design that was adopted to solve the requirement. This should include, at least, a diagram of the realization of the functionality (e.g., sequence diagram), a class diagram (presenting the classes that support the functionality), the identification and rational behind the applied design patterns and the specification of the main tests used to validade the functionality.*

### 4.1. Realization

### 4.2. Class Diagram

![a class diagram](class-diagram-01.svg "A Class Diagram")

### Sequence Diagram 

#### Register User ####
![a sequence diagram 1](SD-1 US_1001.svg)

#### Disable/Enable User ####
![a sequence diagram 2](SD-2 US_1001.svg)

#### List Users ####
![a sequence diagram 3](SD-3 US_1001.svg)

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