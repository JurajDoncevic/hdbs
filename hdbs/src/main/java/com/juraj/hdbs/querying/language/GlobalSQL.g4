grammar GlobalSQL;

/* Lexical rules */
SELECT  :   'SELECT';
FROM    :   'FROM';
JOIN    :   'JOIN';
WHERE   :   'WHERE';
DELETE  :   'DELETE';
UPDATE  :   'UPDATE';
VALUES  :   'VALUES';
SUM     :   'SUM';
COUNT   :   'COUNT';
AVG     :   'AVG';
MIN     :   'MIN';
MAX     :   'MAX';

SEMI    :   ';';
COLO    :   ',';
//WS  :   ( ' ' | '\t' | '\r' | '\n' )+ -> skip;
WS  :   [ \r\t\u000C\n]+ -> skip;

GT  :   '>';
GE  :   '>=';
LT  :   '<';
LE  :   '<=';
EQ  :   '=';
NE  :   '!=';

AND :   'AND';
OR  :   'OR';

LPAREN  :   '(';
RPAREN  :   ')';

COLID   :   ([a-zA-Z_][a-zA-Z_0-9]*'.'[a-zA-Z_][a-zA-Z_0-9]*'.'[a-zA-Z_][a-zA-Z_0-9]*);
TBLID   :   ([a-zA-Z_][a-zA-Z_0-9]*'.'[a-zA-Z_][a-zA-Z_0-9]*);
DBID    :   ([a-zA-Z_][a-zA-Z_0-9]*);
ALLCOLS :   '*';

INTEGER :   ('-'*[1-9]+[0-9]*|[0]);
DECIMAL :   ('-'*[1-9]+[0-9]*'.'[0-9]+|[0]'.'[0-9]+);
STRING  :   ('\''.+?'\'');

DBL_SLASH :   '//' .*? '\n' -> skip;
BLOCK_COMMENT    :   '/*' .*? '*/' -> skip;

/* Grammar rules */
number_value    :   INTEGER | DECIMAL
                ;

string_value    :   STRING
                ;

value   : number_value | string_value
        ;

number_comparison_operator  :   GT | GE | LT | LE | EQ | NE
                            ;

string_comparison_operator  :   NE | EQ
                            ;

comparison  :   COLID (number_comparison_operator number_value | string_comparison_operator string_value)
            ;

logical_operator  : AND | OR
                ;

select_clause   :   SELECT COLID (COLO COLID)*
                |   SELECT ALLCOLS
                ;

delete_clause   :   DELETE TBLID
                ;

update_clause   :   UPDATE TBLID LPAREN (COLID (COLO COLID)*) RPAREN VALUES LPAREN (value (COLO value)*) RPAREN
                ;

aggregate_clause   :   (SUM | AVG | MIN | MAX | COUNT) LPAREN COLID RPAREN
                    ;

from_clause :   FROM TBLID (COLO TBLID)*
            ;

join_clause :   JOIN LPAREN COLID COLO COLID RPAREN
            ;

where_clause    :   WHERE comparison (logical_operator comparison)*
                ;

select_query    :   select_clause from_clause (join_clause)* (SEMI | (where_clause SEMI))
                ;

delete_query    :   delete_clause from_clause (join_clause)* (SEMI | (where_clause SEMI))
                ;

update_query    :   update_clause from_clause (join_clause)* (SEMI | (where_clause SEMI))
                ;

aggregate_query :   aggregate_clause from_clause (join_clause)* (SEMI | (where_clause SEMI))
                ;