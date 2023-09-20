%option noyywrap

%{
    #include <stdio.h>
    #include "P1.tab.h"
%}

%%
[ \t\n]                     ;
"//".*                      ;
"+"                         {return ADD;}
"-"                         {return SUB;}
"*"                         {return MUL;}
"/"                         {return DIV;}
"="                         {return EQUAL;}
"||"                        {return OR;}
"&&"                        {return AND;}
"%"                         {return MOD;}
"|"                         {return ABS;}
"<"                         {return LT;}
">"                         {return GT;}
"{"                         {return LEFT;}
"}"                         {return RIGHT;}
"("                         {return OPEN;}
")"                         {return CLOSE;}
"["                         {return LEFT_OF;}
"]"                         {return RIGHT_OF;}
"!"                         {return NOT;}
"."                         {return DOT;}
","                         {return COMMA;}
";"                         {return SEOL;}
"#"                         {return HASH;}
"if"                        {return IF;}
"else"                      {return ELSE;}
"do"                        {return DO;}
"while"                     {return WHILE;}
"for"                       {return FOR;}
"System.out.println"        {return PRINT;}
"length"                    {return LENGTH;}
"true"                      {return TRUE;}
"false"                     {return FALSE;}
"new"                       {return NEW;}
"this"                      {return THIS;}
"int"                       {return INT;}
"boolean"                   {return BOOLEAN;}
"String"                    {return STRING;}
"return"                    {return RETURN;}
"class"                     {return CLASS;}
"public"                    {return PUBLIC;}
"static"                    {return STATIC;}
"void"                      {return VOID;}
"main"                      {return MAIN;}
"extends"                   {return EXTENDS;}
"define"                    {return DEFINE;}
"exit"                      {return EXIT;}
[0-9]+                      {yylval.cval = strdup(yytext);return NUM;}
[a-zA-Z]([a-zA-Z0-9]|"_")*  {yylval.cval = strdup(yytext);return ID;}
.                           {}
%%
