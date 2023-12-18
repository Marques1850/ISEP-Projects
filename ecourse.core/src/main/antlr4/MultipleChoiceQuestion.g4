grammar MultipleChoiceQuestion;

@header {
   package ecourse.base.ExamMagnament;
}

multipleChoice: MULTIPLECHOICE phrase NEWLINE option+;

option: OPTION AST? phrase NEWLINE;


phrase: WORD+;

date: DATE;

DATE: DAY '/' MONTH '/' YEAR;

fragment DAY: '0'? [1-9] | '1' [0-9] | '2' [0-9] | '3' '0' | '3' '1';
fragment MONTH: '0'? [1-9] | '1' '0' | '1' '1' | '1' '2';
fragment YEAR: [0-9] [0-9] [0-9] [0-9];

fragment DIGIT: [0-9];
fragment FINALNOTATION: '.' | '?' | '!';
fragment TEXT: [a-zA-Z\u0080-\uFFFF-,_]+;


MULTIPLECHOICE: 'Multiple Choice:';

OPTION: 'Option:';
AST: '*';

OPERADOR: '+' | '-' | '*' | '/' | '=';


WORD: TEXT FINALNOTATION?;
NUMBER: DIGIT+;
REAL_NUMBER: DIGIT+ '.' DIGIT+;


WS: [ \t:]+ -> skip;
NEWLINE: [\r\n]+;