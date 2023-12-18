grammar Question;

@header {
   package ecourse.base.ExamMagnament;
}

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

phrase: WORD+;

date: DATE;

DATE: DAY '/' MONTH '/' YEAR;

fragment DAY: '0'? [1-9] | '1' [0-9] | '2' [0-9] | '3' '0' | '3' '1';
fragment MONTH: '0'? [1-9] | '1' '0' | '1' '1' | '1' '2';
fragment YEAR: [0-9] [0-9] [0-9] [0-9];

fragment DIGIT: [0-9];
fragment FINALNOTATION: '.' | '?' | '!';
fragment TEXT: [a-zA-Z\u0080-\uFFFF-,_]+;



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