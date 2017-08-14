
grammar UseCaseDiagram;

useCaseDiagram
    : (diagramName)?
      
      actors DOT
      
      useCases DOT
      
      relations DOT
    ;

diagramName
    : DIAGRAM STRING 
    ;

element
    : actor     # elementActor
    | useCase   # elementUseCase
    ;

actor
    : STRING (INHERITANCE actor)?
    ;

actors
    : ACTORS actor (COMMA actor)*
    ;

useCase
    : STRING (INHERITANCE useCase)?
    ;

useCases
    : USE_CASES useCase (COMMA useCase)*
    ;

relation
    : element COMMUNICATION element # relationComm
    | useCase EXTENSION useCase     # relationExte
    | useCase INCLUSION useCase     # relationIncl
    ;

relations
    : RELATIONS relation (COMMA relation)*
    ;

/** Diagram Tokens **/

DIAGRAM       : 'Diagrama de Casos de Uso'     ;

ACTORS        : 'Atores'       ;

USE_CASES     : 'Casos de Uso' ;

RELATIONS     : 'Relacionamentos'     ;

INHERITANCE   : 'herda de'     ;

COMMUNICATION : 'se associa com'  ;

EXTENSION     : 'estende'       ;

INCLUSION     : 'inclui'      ;

/** Diagram Tokens **/

    
COMMA : ',' ;

QUOTE : '"' ;

DOT   : '.' ;

STRING :  '"' (ESC | ~["\\])* '"' ;

fragment ESC :   '\\' (["\\/bfnrt] | UNICODE) ;

fragment UNICODE : 'u' HEX HEX HEX HEX ;

fragment HEX : [0-9a-fA-F] ;

WS  :   [ \t\n\r]+ -> skip ;