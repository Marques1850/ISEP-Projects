grammar FormativeExam;

@header {
   package ecourse.base.ExamMagnament;
}

exam: title NEWLINE header NEWLINE date NEWLINE section+;

title: phrase;

phrase: WORD+;

header: DESCRIPTIONHEADER phrase NEWLINE finalgrade;

finalgrade: FINALGRADE REAL_NUMBER;

section: SECTION NUMBER NEWLINE sectionDescription NEWLINE QUESTIONTYPETRUEORFALSE NEWLINE qttrueFalse+
        | SECTION NUMBER NEWLINE sectionDescription NEWLINE QUESTIONTYPEMULTIPLECHOICE NEWLINE qtmultiple+
        | SECTION NUMBER NEWLINE sectionDescription NEWLINE QUESTIONTYPESHORTANSWER NEWLINE qtshortAnswer+
        | SECTION NUMBER NEWLINE sectionDescription NEWLINE QUESTIONTYPENUMERICALANSWER NEWLINE qtnumericalAnswer+
        | SECTION NUMBER NEWLINE sectionDescription NEWLINE QUESTIONTYPEMATCHINGANSWER NEWLINE qtmatchingAnswer+
        | SECTION NUMBER NEWLINE sectionDescription NEWLINE QUESTIONTYPEMISSINGWORDANSWER NEWLINE qtmissingWordAnswer+
        ;

sectionDescription: SECTIONDESCRIPTION phrase;

cotation: QUESTIONCOTATION '(' REAL_NUMBER ')';

qtmultiple: QUESTION NUMBER NEWLINE cotation NEWLINE multipleChoice;

qttrueFalse: QUESTION NUMBER NEWLINE cotation NEWLINE trueFalse;

qtshortAnswer: QUESTION NUMBER NEWLINE cotation NEWLINE shortAnswer;

qtnumericalAnswer: QUESTION NUMBER NEWLINE cotation NEWLINE numericalAnswer;

qtmatchingAnswer: QUESTION NUMBER NEWLINE cotation NEWLINE matchingAnswer;

qtmissingWordAnswer: QUESTION NUMBER NEWLINE cotation NEWLINE missingWordAnswer;

multipleChoice: phrase NEWLINE option+;

option: OPTION AST? phrase NEWLINE;

trueFalse: AST? phrase NEWLINE;

shortAnswer: phrase correctAnswer NEWLINE;

numericalAnswer: equation+ correcNumericaltAnswer NEWLINE;

matchingAnswer: answer+;

answer: phrase correctAnswer NEWLINE | correctAnswer NEWLINE;

missingWordAnswer: phrase correctAnswer phrase NEWLINE;

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


FINALGRADE: 'Final Grade:';
SECTIONDESCRIPTION: 'Section Description:';
DESCRIPTIONHEADER: 'Header Description:';
SECTION: 'Section:';
QUESTIONCOTATION: 'Question Cotation:';
QUESTION: 'Question:';
QN: 'Qt:';
OPTION: 'Option:';
AST: '*';

QUESTIONTYPETRUEORFALSE: 'True or False type question!';
QUESTIONTYPEMULTIPLECHOICE: 'Multiple Choice type question!';
QUESTIONTYPESHORTANSWER: 'Short Answer type question!';
QUESTIONTYPENUMERICALANSWER: 'Numerical Answer type question!';
QUESTIONTYPEMATCHINGANSWER: 'Matching Answer type question!';
QUESTIONTYPEMISSINGWORDANSWER: 'Missing Word Answer type question!';

OPERADOR: '+' | '-' | '*' | '/' | '=';

WORD: TEXT FINALNOTATION?;
NUMBER: DIGIT+;
REAL_NUMBER: DIGIT+ '.' DIGIT+;

WS: [ \t:]+ -> skip;
NEWLINE: [\r\n]+;