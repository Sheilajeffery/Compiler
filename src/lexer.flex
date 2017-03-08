%%
%type Token
%%

"while"			{ return new While(); }
":="			{ return new Assign(); }
[0-9]+			{ return new Number(Integer.parseInt(yytext())); }
[a-zA-Z]+		{ return new VarName(yytext()); }
"-"				{ return new Minus(); }
"+"		 		{ return new Plus(); }
";"				{ return new Semicolon(); }
"("				{ return new Lbr(); }
")"				{ return new Rbr(); }
"{"				{ return new Lcurl(); }
"}"				{ return new Rcurl(); }
[\ \t\b\f\r\n]+ { /* eat whitespace */ }
"//"[^\n]*      { /* one-line comment */ }
.               { throw new Error("Unexpected character ["+yytext()+"]"); }