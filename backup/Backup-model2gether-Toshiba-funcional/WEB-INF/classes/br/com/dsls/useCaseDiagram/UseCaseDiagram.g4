
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

DIAGRAM       : 'Use Case Diagram'     ;

ACTORS        : 'Actors'       ;

USE_CASES     : 'Use Cases' ;

RELATIONS     : 'Relationships'     ;

INHERITANCE   : 'inherits from'     ;

COMMUNICATION : 'associates with'  ;

EXTENSION     : 'extend'       ;

INCLUSION     : 'include'      ;

/** Diagram Tokens **/

    
COMMA : ',' ;

QUOTE : '"' ;

DOT   : '.' ;

STRING :  '"' (ESC | ~["\\])* '"' ;

fragment ESC :   '\\' (["\\/bfnrt] | UNICODE) ;

fragment UNICODE : 'u' HEX HEX HEX HEX ;

fragment HEX : [0-9a-fA-F] ;

WS  :   [ \t\n\r]+ -> skip ;