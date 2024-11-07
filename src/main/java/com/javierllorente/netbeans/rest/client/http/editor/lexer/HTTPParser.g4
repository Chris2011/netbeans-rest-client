parser grammar HTTPParser;


options {
  tokenVocab = HTTPLexer;
}

requestFile
    : REQUEST_SEPARATOR* request requestWithSeparator* REQUEST_SEPARATOR*
    ;

requestWithSeparator
    : REQUEST_SEPARATOR+ request
    ;

request
    :  requestLine NEWLINE headers NEWLINE messageBody? responseHandler? responseRef?
    ;

requestLine
    : (METHOD REQUIRED_WHITESPACE)? requestTarget (REQUIRED_WHITESPACE HTTP_VERSION)?
    ;

requestTarget
    : (PART NEWLINE_WITH_INDENT?)+
    ;
    
headers
    : (headerField NEWLINE)*
    ;

headerField
    : IDENTIFIER COLON  headerValue
    ;

headerValue
    : (PART NEWLINE_WITH_INDENT?)+
    ;

