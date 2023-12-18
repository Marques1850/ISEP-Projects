grammar Exam;

@header {
   package ecourse.base.ExamMagnament;
}

exam: title NEWLINE header NEWLINE date NEWLINE section+;

title: phrase;

phrase: WORD+;

header: DESCRIPTIONHEADER phrase NEWLINE finalgrade;

finalgrade: FINALGRADE REAL_NUMBER;

section: SECTION NUMBER NEWLINE sectionDescription NEWLINE qt+;

sectionDescription: SECTIONDESCRIPTION phrase;

qt: QUESTION NUMBER NEWLINE cotation NEWLINE question;

cotation: QUESTIONCOTATION '(' REAL_NUMBER ')';

question: multipleChoice | trueFalse | shortAnswer | numericalAnswer | matchingAnswer | missingWordAnswer;

multipleChoice: MULTIPLECHOICE phrase NEWLINE option+;

option: OPTION AST? phrase NEWLINE;

trueFalse: TRUEORFALSE NEWLINE tf+;

tf: AST? phrase NEWLINE;

shortAnswer: SHORTANSWER phrase correctAnswer NEWLINE;

numericalAnswer: NUMERICALANSWER equation+ correcNumericaltAnswer NEWLINE;

matchingAnswer: MATCHINGANSWER NEWLINE answer+;

answer: phrase correctAnswer NEWLINE | correctAnswer NEWLINE;

missingWordAnswer: MISSINGWORDANSWER phrase correctAnswer phrase NEWLINE;

equation: NUMBER | OPERADOR | REAL_NUMBER;

correctAnswer: '[' phrase ']';

correcNumericaltAnswer: '[' NUMBER ',' NUMBER ']';

date: DATE;

DATE: DAY '/' MONTH '/' YEAR;

fragment DAY: '0'? [1-9] | '1' [0-9] | '2' [0-9] | '3' '0' | '3' '1';
fragment MONTH: '0'? [1-9] | '1' '0' | '1' '1' | '1' '2';
fragment YEAR: [0-9] [0-9] [0-9] [0-9];

fragment DIGIT: [0-9];
fragment FINALNOTATION: '.' | '?' | '!';
fragment TEXT: [a-zA-Z\u0080-\uFFFF-,_]+;


SECTIONDESCRIPTION: 'Section Description:';
DESCRIPTIONHEADER: 'Header Description:';
FINALGRADE: 'Final Grade:';
SECTION: 'Section:';
QUESTIONCOTATION: 'Question Cotation:';
TRUEORFALSE: 'True or False:';
MULTIPLECHOICE: 'Multiple Choice:';
SHORTANSWER: 'Short Answer:';
NUMERICALANSWER: 'Numerical Answer:';
MATCHINGANSWER: 'Matching Answer:';
MISSINGWORDANSWER: 'Missing Word Answer:';
QUESTION: 'Question:';
OPTION: 'Option:';
AST: '*';

OPERADOR: '+' | '-' | '*' | '/' | '=';


WORD: TEXT FINALNOTATION?;
NUMBER: DIGIT+;
REAL_NUMBER: DIGIT+ '.' DIGIT+;


WS: [ \t:]+ -> skip;
NEWLINE: [\r\n]+;