
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

DOT   : '.' ;

STRING : '"' ~('\r' | '\n' | '"')* '"' ;

fragment ESC :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\') ;

WS  :   (' '|'\t'|'\r'|'\n')+ -> skip ;