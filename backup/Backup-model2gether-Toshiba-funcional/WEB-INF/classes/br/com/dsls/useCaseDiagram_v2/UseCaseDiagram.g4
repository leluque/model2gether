
grammar UseCaseDiagram;

useCaseDiagram
    : diagramName
      
      actors
      
      useCases
      
      relations
    ;

diagramName
    : DIAGRAM STRING
    ;

element
    : actor     # elementActor
    | useCase   # elementUseCase
    ;

actor
    : STRING
    ;

actors
    : ACTORS (actor (COMMA actor)* DOT)?
    ;

useCase
    : STRING
    ;

useCases
    : USE_CASES (useCase (COMMA useCase)* DOT)?
    ;

relation
    : element COMMUNICATION element # relationComm
    | useCase EXTENSION useCase     # relationExte
    | useCase INCLUSION useCase     # relationIncl
    | useCase INHERITANCE useCase   # relationInheUC
    | actor   INHERITANCE actor     # relationInheAct
    ;

relations
    : RELATIONS (relation (COMMA relation)* DOT)?
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

DOT   : '.' ;

STRING : '"' ~('\r' | '\n' | '"')* '"' ;

fragment ESC :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\') ;

WS  :   (' '|'\t'|'\r'|'\n')+ -> skip ;