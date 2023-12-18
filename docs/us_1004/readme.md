# US 1004

## 1. Context

*We are starting the implementation form scratch*

## 2. Requirements

**US 1004** As Manager, I want to open and close courses

*Regarding this requirement we understand it depends on the course creation on US 1002.
The course opening process  may be limited by the minimum and maximum number of allowed students.
A closed course can't allow any activity related to it.*


## 3. Analysis
### Domain Model ###
![RelevantDMexcerpt](DomainModel1004.svg)


### System Sequence Diagrams ###

#### Alternative 1 ####
![First alternative](SSD1_US_1004.svg)

#### Alternative 2 ####
![open course ](SSD2o_US_1004.svg)
![close course ](SSD2c_US_1004.svg)


#### Alternative 2  was chosen ####

## 4. Design

### Sequence Diagram

#### open course ####
![a sequence diagram 1](SDc_US_1004.svg)

#### close course ####
![a sequence diagram 2](SDo_US_1004.svg)


### 4.1. Realization

### 4.2. Class Diagram

![a class diagram](class-diagram-01.svg "A Class Diagram")

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