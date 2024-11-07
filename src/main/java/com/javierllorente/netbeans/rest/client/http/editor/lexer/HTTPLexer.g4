lexer grammar HTTPLexer;
// https://github.com/JetBrains/http-request-in-editor-spec/blob/master/spec.md

fragment InputCharacter
    : ~ [\r\n]
    ;

fragment Alpha
    : [A-Za-z]
    ;

fragment Digit
    : [0-9]
    ;

fragment HexDigit
    : [0-9A-Fa-f]
    ;

fragment IdentifierCharacter
    : Alpha
    | Digit
    | Minus
    | Underscore
    ;

fragment WhiteSpace
    : [\t\f ]
    ;

fragment LineTail
    : InputCharacter* NewLine
    ;

fragment SegmentCharacter
    : ~ [\r\n/?#]
    ;

fragment QueryCharacter
    : ~ [\r\n#]
    ;

REQUIRED_WHITESPACE
    : WhiteSpace+
    ;

OPTIONAL_WHITESPACE
    : WhiteSpace*
    ;

IDENTIFIER
    : Alpha IdentifierCharacter*
    ;

REQUEST_SEPARATOR
    : Hashtag Hashtag Hashtag LineTail
    ;

LINE_COMMENT
    : LineComment -> channel(HIDDEN)
    ;

METHOD
    : 'GET'
    | 'HEAD'
    | 'POST'
    | 'PUT'
    | 'DELETE'
    | 'CONNECT'
    | 'PATCH'
    | 'OPTIONS'
    | 'TRACE' -> pushMode(INDENTED_PARTS)
    ;


HTTP_VERSION
    : 'HTTP/' Digit+ Dot Digit+
    ;

mode INDENTED_PARTS;

INDENTED_LINE_COMMENT
    : LineComment -> channel(HIDDEN), type(LINE_COMMENT)
    ;

INDENTED_HTTP_VERSION
    : 'HTTP/' Digit+ Dot Digit+ -> type(HTTP_VERSION)
    ;

NEWLINE_WITH_INDENT
    : NewLine WhiteSpace+
    ;

NEWLINE
    : NewLine
    ;

WS
    : WhiteSpace+
    ;

PART
    : ~ [ \t\n\r]
    ;

PART_END
    : . -> popMode, skip, more
    ;

ASTERISK
    : Star
    ;

QUESTION_MARK
    : QuestionMark
    ;

HASHTAG
    : Hashtag
    ;

SCHEME
    : 'https'
    | 'http'
    ;

SCHEME_SEPARATOR
    : '://'
    ;

COLON
    : Colon
    ;

DIGIT
    : Digit
    ;

IPV6ADDRESS
    : LBracket (Colon | HexDigit)* HexDigit+ RBracket
    ;

SLASH
    : Slash
    ;

SEGMENT
    : SegmentCharacter*
    ;

QUERY_CHAR
    : QueryCharacter
    ;

IN
    : LAngle
    ;

OUT
    : RAngle RAngle ExclamationMark?
    | RAngle RAngle?
    ;

MUSTACHE_START
    : '{{' -> pushMode(MUSTACHE)
    ;

SCRIPT_START
    : '{%' -> pushMode(SCRIPT)
    ;

ERROR
    : .
    ;

mode URI;

INDENT
    : NewLine
    ;

mode SCRIPT;
    
SCRIPT_END
    : '%}' -> popMode
    ;

SCRIPT_CONTENT
    : .
    ;

mode MUSTACHE;

MUSTACHE_END
    : '}}' -> popMode
    ;

MUSTACHE_CONTENT
    : ~ NewLine
    ;

MUSTACHE_ERROR
    : . -> type(ERROR)
    ;

fragment LineComment
    : '#' ~ [\r\n]*
    | '//' ~ [\r\n]*
    ;

fragment NewLine
    : '\r'? '\n'
    ;
/*
 ALPHA = %x41‑5A /  %x61‑7A ; A‑Z / a‑z
 */
fragment Alpha: [A-Za-z];

/*
 DIGIT = %x30‑39 ; 0-9
 */
fragment Digit: [0-9];

/*
 pct-encoded = "%"  HEXDIG  HEXDIG
 */
Pct_encoded: Percent HEXDIG HEXDIG;

/*
 HEXDIG = DIGIT /  "A" /  "B" /  "C" /  "D" /  "E" /  "F"
 */
fragment HEXDIG: Digit | 'A' | 'B' | 'C' | 'D' | 'E' | 'F';

fragment LColumn:'(';
fragment RColumn:')';
fragment SemiColon:';';
fragment Equals:'=';
fragment Period:',';

/*
 CRLF = CR  LF ; Internet standard newline
 */
CRLF: '\n';


fragment Minus :'-';
fragment Dot   : '.';
fragment Underscore: '_';
fragment Tilde : '~';
fragment QuestionMark :'?';
fragment Slash :'/';
fragment ExclamationMark: '!';
fragment Colon:':';
fragment At: '@';
fragment DollarSign:'$';
fragment Hashtag:'#';
fragment Ampersand:'&';
fragment Percent:'%';
fragment SQuote:'\'';
fragment Star:'*';
fragment Plus:'+';
fragment Caret:'^';
fragment BackQuote:'`';
fragment VBar:'|';
fragment Quote: '"';
fragment LBrace: '{';
fragment RBace: '}';
fragment BackSlash: '\\';
fragment LBracket: '[';
fragment RBracket: ']';
fragment LAngle: '<';
fragment RAngle: '>';

/*
 OWS = ( SP / HTAB ) ; optional whitespace
 */
OWS: SP | HTAB;

/*
 SP = %x20 ; space
 */
SP: ' ';

/*
 HTAB = %x09 ; horizontal tab
 */
HTAB: '\t';

VCHAR:
	ExclamationMark
	| Quote
	| Hashtag
	| DollarSign
	| Percent
	| Ampersand
	| SQuote
	| LColumn
	| RColumn
	| RColumn
	| Star
	| Plus
	| Period
	| Minus
	| Dot
	| Slash
	| Colon
	| SemiColon
	| '<'
	| Equals
	| '>'
	| QuestionMark
	| At
	| LBracket
	| BackSlash
	| Caret
	| Underscore
	| RBracket
	| BackQuote
	| LBrace
	| RBace
	| VBar
	| Tilde;

OBS_TEXT: '\u0080' ..'\u00ff';
