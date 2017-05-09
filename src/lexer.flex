%%
%type Token
%class Lexer
%%

"while"			{ return new While(); }
":="			  { return new Assign(); }
[0-9]+			{ return new Number(Integer.parseInt(yytext())); }
"false"     { return new False(); }
"true"      { return new True(); }
[a-zA-Z]+		{ return new VarName(yytext()); }
"-"				  { return new Minus(); }
"+"		 		  { return new Plus(); }
"*"         { return new Times(); }
"/"         { return new Divide();}
";"				  { return new Semicolon(); }
"("				  { return new Lbr(); }
")"				  { return new Rbr(); }
"{"				  { return new Lcurl(); }
"}"				  { return new Rcurl(); }
"||"        { return new Or(); }
"&&"        { return new And(); }
[\ \t\b\f\r\n]+ { /* eat whitespace */ }
"//"[^\n]*      { /* one-line comment */ }
.               { throw new Error("Unexpected character ["+yytext()+"]"); }
