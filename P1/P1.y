%{

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
extern int yylex();
extern int yyparse();
extern FILE* yyin;
void yyerror(const char* s);

struct Node{
    char* Key;
    char** argv;
    char* Value;
    int argc;
    int type_of_macro;
    struct Node* next;
};

struct Node* head;

char* s_add(char* s1, char* s2, char* s3, char* s4, char* s5, char* s6, char* s7, char* s8){
    char* s = (char*)calloc(strlen(s1) + strlen(s2) + strlen(s3) + strlen(s4) + strlen(s5) + strlen(s6) + strlen(s7) + strlen(s8)+8, sizeof(char));
    strcat(s,s1);strcat(s,s2);
    strcat(s,s3);strcat(s,s4);
    strcat(s,s5);strcat(s,s6);
    strcat(s,s7);strcat(s,s8);
    return s;
}

struct Node* search_by_name(char* s){
    struct Node* temp = head;
    while(temp != NULL){
        if(strcmp(temp -> Key,s) == 0){
            return temp;
        }
        temp = temp -> next;
    }
    return NULL;
}

void add_macro(char* s,char* parameters,char* val,int t){
    struct Node* f = search_by_name(s);
    if( f != NULL){
        printf("// Failed to parse macrojava code.");
        exit(0);
    }
    struct Node* temp = (struct Node*)malloc(sizeof(struct Node));
    temp -> Key = s;
    temp -> argv = (char**)calloc(100,sizeof(char*));
    char* str = strtok(parameters,",\n");
    int i = 0;
    while(str != NULL){
        temp -> argv[i++] = str;
        str = strtok(NULL,", \n\t");
    }
    temp -> Value = (char*)calloc(strlen(val)+1,sizeof(char));
    temp -> Value = val;
    temp -> argc = i;
    temp -> type_of_macro = t;
    temp -> next = NULL;
    if(head == NULL){
        head = temp;
        head->next = NULL;
    }
    else{
        temp->next = head;
        head = temp;
    }
}

struct Node* search_macro(char* s, char* parameters){
    struct Node* temp = head;
    char* strings = (char*)calloc(strlen(parameters)+1,sizeof(char));
    strcpy(strings,parameters);
    char* str = strtok(strings,",\n");
    int i = 0;
    while(str != NULL){
        i++;
        str = strtok(NULL,",\n\t");
    }
    while(temp != NULL){
        if(strcmp(temp -> Key, s) == 0 && temp -> argc == i){
            return temp;
        }
        temp = temp -> next;
    }
    return NULL;
}

char* replaceWord(char* s, char* to_rep, char* rep_with){
    char* result;
	int i, cnt = 0;
    if(!to_rep||!s)return NULL;
    if(!rep_with)rep_with="";
	int rep_withlen = strlen(rep_with);
	int to_replen = strlen(to_rep);
	for (i = 0; s[i] != '\0'; i++) {
		if (strstr(&s[i], to_rep) == &s[i]) {
			cnt++;
			i += to_replen - 1;
		}
	}
	result = (char*)malloc(i + cnt * (rep_withlen - to_replen) + 1);
	i = 0;
	while (*s) {
		if (strstr(s, to_rep) == s) {
			strcpy(&result[i], rep_with);
			i += rep_withlen;
			s += to_replen;
		}
		else{
			result[i++] = *s++;
        }
	}
	result[i] = '\0';
	return result;
}

char* get_macro(char* s,char* parameters,int t){
    struct Node* temp = search_macro(s,parameters);
    struct Node* temp2 = search_by_name(s);
    if(temp == NULL){
        if(temp2!=NULL){
            printf("// Failed to parse macrojava code.");
            exit(0);
        }
        if( t == 1){
            printf("// Failed to parse macrojava code.");
        }
        else{
            printf("// Failed to parse macrojava code.");
        }
        exit(0);
    }
    if(temp -> type_of_macro != t){
        if(t == 1){
            printf("// Failed to parse macrojava code.");
        }
        else{
            printf("// Failed to parse macrojava code.");
        }
        exit(0);
    }
    char* ans = temp -> Value;
    char* str = strtok(parameters,",\n");
    for(int i = 0;i < temp -> argc;i++){
        ans = replaceWord(ans,temp -> argv[i],str);
        str = strtok(NULL,", \n\t\0");
    }
    return ans;
}

%}

%union{
    int ival;
    char* cval;
}

%type<cval> goal methoddec methoddecstar macrodef macrodefexpr macrodefstar macrodefstmt stmt stmtstar idstar idstar2 type typedec typedecstar tidseolstar mainclass tidstar tidstar2 expression prim_expr exprstar2 exprstar3 num

%token<cval> ID NUM
%token ADD SUB MUL DIV MAX ABS MOD
%token AND OR LT GT EQUAL NOT
%token DOT COMMA HASH
%token SEOL EXIT INT
%token LEFT RIGHT OPEN CLOSE LEFT_OF RIGHT_OF
%token QUIT PRINT 
%token BOOLEAN STRING
%token IF ELSE DO WHILE FOR 
%token LENGTH TRUE FALSE
%token CLASS PUBLIC STATIC VOID MAIN NEW THIS 
%token EXTENDS DEFINE RETURN
%nonassoc nothing
%left ADD SUB MUL DIV MAX ABS MOD AND OR LT GT NOT DOT LEFT_OF RIGHT_OF

%start program

%%

program : goal                                        { printf("%s",$1);exit(0);}
;

goal : macrodefstar mainclass typedecstar             { $$ = s_add($1,$2,$3,"","","","",""); }
;

macrodefstar : macrodef macrodefstar                  { $$ = ""; }
    |                                                 { $$ = ""; }
;

macrodef: macrodefexpr                                { $$ = ""; }
    | macrodefstmt                                    { $$ = ""; }
;

macrodefexpr : HASH DEFINE ID OPEN idstar CLOSE OPEN expression CLOSE { $$ = "";add_macro($3,$5,$8,0); }
;

idstar : ID idstar2                                   { $$ = s_add($1,$2,"","","","","",""); }
    |                                                 { $$ = ""; }
;

idstar2 : COMMA ID idstar2                            { $$ = s_add(",",$2,$3,"","","","",""); }
    |                                                 { $$ = ""; }
;

expression : prim_expr AND prim_expr                  { $$ = s_add($1," && ",$3,"","","","",""); }
    | prim_expr OR prim_expr                          { $$ = s_add($1," || ",$3,"","","","",""); }
    | prim_expr NOT EQUAL prim_expr                   { $$ = s_add($1," != ",$4,"","","","",""); }
    | prim_expr LT EQUAL prim_expr                    { $$ = s_add($1," <= ",$4,"","","","",""); }
    | prim_expr ADD prim_expr                         { $$ = s_add($1," + ",$3,"","","","",""); }
    | prim_expr SUB prim_expr                         { $$ = s_add($1," - ",$3,"","","","",""); }
    | prim_expr MUL prim_expr                         { $$ = s_add($1," * ",$3,"","","","",""); }
    | prim_expr DIV prim_expr                         { $$ = s_add($1," / ",$3,"","","","",""); }
    | prim_expr DOT LENGTH                            { $$ = s_add($1," .length","","","","","",""); }
    | prim_expr LEFT_OF prim_expr RIGHT_OF            { $$ = s_add($1," [ ",$3," ] ","","","",""); }
    | prim_expr                                       { $$ = $1; }
    | prim_expr DOT ID OPEN exprstar2 CLOSE           { $$ = s_add($1," . ",$3," ( ",$5," ) ","",""); }
    | ID OPEN exprstar2 CLOSE                         { $$ = get_macro($1,$3,0); }
;

exprstar2 : expression exprstar3                      { $$ = s_add("(",$1,")",$2,"","","",""); }
    |                                                 { $$ = ""; }
;

exprstar3 : COMMA expression exprstar3                { $$ = s_add(",(",$2,")",$3,"","","",""); }
    |                                                 { $$ = ""; }
;

prim_expr : num                                      { $$ = s_add($1," ","","","","","",""); }
    | TRUE                                            { $$ = "true "; }
    | FALSE                                           { $$ = "false "; }
    | ID                                              { $$ = s_add($1," ","","","","","",""); }
    | THIS                                            { $$ = "this "; }
    | NEW ID OPEN CLOSE                               { $$ = s_add("new ",$2,"()","","","","",""); }
    | NEW INT LEFT_OF expression RIGHT_OF             { $$ = s_add("new int[",$4,"]","","","","",""); }
    | NOT expression                                  { $$ = s_add("!",$2,"","","","","",""); }
    | OPEN expression CLOSE                           { $$ = s_add("(",$2,")","","","","",""); }
;

num : NUM { $$ = $1; }
    | SUB NUM { $$ = s_add("-",$2,"","","","","",""); }

macrodefstmt : HASH DEFINE ID OPEN idstar CLOSE LEFT stmtstar RIGHT { $$ = "";add_macro($3,$5,$8,1); }
;

stmt : LEFT stmtstar RIGHT                            { $$ = s_add(" { ",$2," } ","\n","","","",""); }
    | PRINT OPEN expression CLOSE SEOL                { $$ = s_add(" System.out.println( ",$3," ) "," ;\n ","","","",""); }
    | ID EQUAL expression SEOL                        { $$ = s_add($1," = ",$3," ;\n ","","","","");}
    | ID LEFT_OF expression RIGHT_OF EQUAL expression SEOL { $$ = s_add(" ",$1," [ ",$3," ]= ",$6," ;\n ",""); }
    | IF OPEN expression CLOSE stmt                   { $$ = s_add(" if( ",$3,"){\n ",$5," } ","","",""); }
    | IF OPEN expression CLOSE stmt ELSE stmt         { $$ = s_add(" if (",$3,"){\n ",$5," }\n"," else{ ",$7," }"); }
    | DO stmt WHILE OPEN expression CLOSE SEOL        { $$ = s_add(" do{ ",$2," }while( ",$5," )",";\n","",""); }
    | WHILE OPEN expression CLOSE stmt                { $$ = s_add(" while( ",$3," )\n ",$5," ","","",""); }
    | ID OPEN exprstar2 CLOSE SEOL                    { $$ = get_macro($1,$3,1); }
;

mainclass : CLASS ID LEFT PUBLIC STATIC VOID MAIN OPEN STRING LEFT_OF RIGHT_OF ID CLOSE LEFT PRINT OPEN expression CLOSE SEOL RIGHT RIGHT 
{
    $$ = s_add("class ",$2,"{\n\tpublic static void main(String[] ",$12,"){\n\t\tSystem.out.println(",$17,");\n\t}\n}\n",""); 
}
;

typedecstar : typedec typedecstar                     { $$ = s_add($1,$2,"","","","","",""); }
    |                                                 { $$ = ""; }
;

typedec: CLASS ID LEFT tidseolstar methoddecstar RIGHT { $$ = s_add("class ",$2,"{\n",$4,$5,"}","",""); }
    | CLASS ID EXTENDS ID LEFT tidseolstar methoddecstar RIGHT { $$ = s_add("class  ",$2," extends  ",$4,"{",$6,$7,"}"); }
;

tidseolstar : tidseolstar type ID SEOL                { $$ = s_add($1,$2,$3,";\n","","","",""); }
    |                                                 { $$ = ""; }
;

stmtstar : stmt stmtstar                              { $$ = s_add($1,$2,"","","","","",""); }
    |                                                 { $$ = ""; }
;

methoddecstar : methoddec methoddecstar               { $$ = s_add($1,$2,"","","","","",""); }
    |                                                 { $$ = ""; }
;

methoddec : PUBLIC type ID OPEN tidstar CLOSE LEFT tidseolstar stmtstar RETURN expression SEOL RIGHT
{
    char* temp = s_add("\npublic ",$2,$3,"(",$5,")","","");
    $$ = s_add(temp,"{\n",$8,$9,"return ",$11,";\n}\n","");
}
;

tidstar : type ID tidstar2                            { $$ = s_add($1,$2,$3,"","","","",""); }
    |                                                 { $$ = ""; }
; 

tidstar2 : COMMA type ID tidstar2                     { $$ = s_add(",",$2,$3,$4,"","","",""); }
    |                                                 { $$ = ""; }
;

type : INT LEFT_OF RIGHT_OF                           { $$ = "int[] "; }
    | BOOLEAN                                         { $$ = "boolean "; }
    | INT                                             { $$ = "int "; }
    | ID                                              { $$ = s_add($1," ","","","","","",""); }
;

%%

int main(){
    yyin = stdin;
    do{
        yyparse();
    }while(!feof(yyin));
    return 0;
}

void yyerror(const char* s){
    printf("// Failed to parse macrojava code.");
    exit(0);
}
