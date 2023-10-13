//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class GJNoArguDepthFirst<R> implements GJNoArguVisitor<R> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
   Hashtable<String, String> new_var_name = new Hashtable<String, String>();
   Hashtable<String, Vector<String>> methods_of_class = new Hashtable<String, Vector<String>>();
   Hashtable<String, Hashtable<String, Vector<String>>> inherited_methods_of_class = new Hashtable<String, Hashtable<String, Vector<String>>>();
   Hashtable<String, Vector<String>> fields_of_class = new Hashtable<String, Vector<String>>();
   Hashtable<String, Vector<String>> inherited_fields_of_class = new Hashtable<String, Vector<String>>();
   Hashtable<String, Integer> method_num_args = new Hashtable<String, Integer>();
   Hashtable<String, String> var_type = new Hashtable<String, String>();
   Hashtable<String, String> method_type = new Hashtable<String, String>();
   Hashtable<String, String> extended_by = new Hashtable<String, String>();
   Hashtable<Integer, Vector<String>> arguments_of_level = new Hashtable<Integer, Vector<String>>();
   Vector<String> fields = new Vector<String>();
   Vector<String> methods = new Vector<String>();
   int num_args = 0;
   int pass_num = 1;
   int num_fields = 0;
   int count = 0;
   int L_count = 0;
   int from_fields = -1;
   int level = 0;
   String class_name = "";
   String method_name = "";
   String last_label = "";
   String type_name = "";
   boolean can_be_type = false;
   boolean debug = false;
   boolean is_field = false;
   boolean from_method = false;
   boolean alloc_expr = false;
   boolean from_alloc = false;

   public String new_temp(){
      // System.out.println(count);
      count++;
      return "TEMP " + String.valueOf(count);
   }

   public String new_label(){
      L_count++;
      return "L" + String.valueOf(L_count);
   }

   public R visit(NodeList n) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeListOptional n) {
      if ( n.present() ) {
         R _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public R visit(NodeOptional n) {
      if ( n.present() )
         return n.node.accept(this);
      else
         return null;
   }

   public R visit(NodeSequence n) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeToken n) { return null; }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
   public R visit(Goal n) {
      R _ret=null;
      while(pass_num <= 3){
         count = 0;
         L_count = 0;
         n.f0.accept(this);
         n.f1.accept(this);
         n.f2.accept(this);
         pass_num++;
      }
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> PrintStatement()
    * f15 -> "}"
    * f16 -> "}"
    */
   public R visit(MainClass n) {
      R _ret=null;
      n.f0.accept(this);
      if(pass_num == 3){
         System.out.println("MAIN");
      }
      n.f1.accept(this);
      class_name = n.f1.f0.toString();
      n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);
      n.f5.accept(this);
      n.f6.accept(this);
      n.f7.accept(this);
      n.f8.accept(this);
      n.f9.accept(this);
      n.f10.accept(this);
      n.f11.accept(this);
      n.f12.accept(this);
      n.f13.accept(this);
      n.f14.accept(this);
      n.f15.accept(this);
      n.f16.accept(this);
      if(pass_num == 3){
         System.out.println("END");
      }
      return _ret;
   }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
   public R visit(TypeDeclaration n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
   public R visit(ClassDeclaration n) {
      R _ret=null;
      class_name = n.f1.f0.toString();
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      is_field = true;
      fields.clear();
      n.f3.accept(this);
      is_field = false;
      if(pass_num == 1){
         Vector<String> temp = new Vector<String>(fields);
         fields_of_class.put(class_name, temp);
      }
      methods.clear();
      n.f4.accept(this);
      if(pass_num == 1){
         Vector<String> temp = new Vector<String>(methods);
         methods_of_class.put(class_name, temp);
      }
      n.f5.accept(this);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
   public R visit(ClassExtendsDeclaration n) {
      R _ret=null;
      n.f0.accept(this);
      String cls_1 = (n.f1.f0).toString();
      String cls_2 = (n.f3.f0).toString();
      if(pass_num == 1){
         extended_by.put(cls_1, cls_2);
      }
      if( pass_num == 2 ){
         Vector<String> v = new Vector<String> (methods_of_class.get(cls_2));
         if(inherited_methods_of_class.containsKey(cls_1) == false){
            Hashtable<String, Vector<String>> ht = new Hashtable<String, Vector<String>> ();
            inherited_methods_of_class.put(cls_1, ht);
         }
         String tempo = cls_1;
         while(extended_by.containsKey(tempo)){
            String ext_cls = extended_by.get(tempo);
            Vector<String> vec = new Vector<String> (methods_of_class.get(ext_cls));
            for(String s: methods_of_class.get(cls_1)){
               if(vec.contains(s)){
                  vec.remove(s);
               }
            }
            inherited_methods_of_class.get(cls_1).put(ext_cls, vec);
            tempo = ext_cls;
         }
      }
      if(pass_num == 2){
         String tempo = cls_1;
         Vector<String> f = new Vector<String>();
         while(extended_by.containsKey(tempo)){
            String ext_cls = extended_by.get(tempo);
            Vector<String> vec = fields_of_class.get(ext_cls);
            for(String s: vec){
               if(f.contains(s) == false){
                  f.add(s);
               }
            }
            tempo = ext_cls;
         }
         inherited_fields_of_class.put(cls_1, f);
      }
      n.f1.accept(this);
      class_name = (n.f1.f0).toString();
      n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);
      is_field = true;
      fields.clear();
      n.f5.accept(this);
      is_field = false;
      if(pass_num == 1){
         Vector<String> temp = new Vector<String>(fields);
         fields_of_class.put(class_name, temp);
      }
      methods.clear();
      n.f6.accept(this);
      if(pass_num == 1){
         Vector<String> temp = new Vector<String>(methods);
         methods_of_class.put(class_name, temp);
      }
      n.f7.accept(this);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public R visit(VarDeclaration n) {
      R _ret=null;
      n.f0.accept(this);
      String temp = type_name;
      n.f1.accept(this);
      String id_name = (n.f1.f0).toString();
      num_args++;
      if(pass_num == 1){
         if(is_field == true){
            fields.add(id_name);
            var_type.put((class_name + "_._" + id_name), temp);
         }
         else{
            String var_name = "TEMP " + String.valueOf(num_args);
            new_var_name.put((class_name + "_" + method_name + "_" + id_name), var_name);
            var_type.put((class_name + "_" + method_name + "_" + id_name), temp);
         }
      }
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
    */
   public R visit(MethodDeclaration n) {
      from_method = true;
      method_name = n.f2.f0.toString();
      int num = 0;
      if(pass_num == 3){
         num = 1 + method_num_args.get(class_name + "_" + method_name);
         System.out.println("\n" + class_name + "_" + method_name + "  [" + num + "]");
         System.out.println("BEGIN");
      }
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      String m_type = type_name;
      n.f2.accept(this);
      if(pass_num == 1){
         methods.add(method_name);
      }
      n.f3.accept(this);
      num_args = 0;
      n.f4.accept(this);
      if(pass_num == 1){
         method_type.put((class_name + "_" + method_name), m_type);
         method_num_args.put((class_name + "_" + method_name), num_args);
         new_var_name.put((class_name + "_" + method_name + "_this"),"TEMP 0");
      }
      n.f5.accept(this);
      n.f6.accept(this);
      n.f7.accept(this);
      count = num_args;
      // System.out.println(count + "here");
      num_args = 0;
      n.f8.accept(this);
      n.f9.accept(this);
      String expr = n.f10.accept(this).toString();
      if(pass_num == 3){
         System.out.println("RETURN " + expr);
      }
      n.f11.accept(this);
      n.f12.accept(this);
      if( pass_num == 3 ){
         System.out.println("END");
      }
      from_method = false;
      return _ret;
   }

   /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
   public R visit(FormalParameterList n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
   public R visit(FormalParameter n) {
      R _ret=null;
      n.f0.accept(this);
      String temp = type_name;
      n.f1.accept(this);
      num_args++;
      String id_name = (n.f1.f0).toString();
      if(pass_num == 1){
         String var_name = "TEMP " + String.valueOf(num_args);
         new_var_name.put((class_name + "_" + method_name + "_" + id_name), var_name);
         var_type.put((class_name + "_" + method_name + "_" + id_name), temp);
      }
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   public R visit(FormalParameterRest n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
   public R visit(Type n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
   public R visit(ArrayType n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      type_name = "int[]";
      return _ret;
   }

   /**
    * f0 -> "boolean"
    */
   public R visit(BooleanType n) {
      R _ret=null;
      n.f0.accept(this);
      type_name = "boolean";
      return _ret;
   }

   /**
    * f0 -> "int"
    */
   public R visit(IntegerType n) {
      R _ret=null;
      n.f0.accept(this);
      type_name = "int";
      return _ret;
   }

   /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | DoStatement()
    *       | PrintStatement()
    */
   public R visit(Statement n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
   public R visit(Block n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public R visit(AssignmentStatement n) {
      R _ret=null;
      from_fields = -1;
      alloc_expr = false;
      String temp1 = n.f0.accept(this).toString();
      int a = from_fields;
      n.f1.accept(this);
      String temp2 = n.f2.accept(this).toString();
      int b = from_fields;
      if( pass_num == 3 ){
         System.out.println("MOVE " + temp1 + " " + temp2);
         if(a != -1){
            System.out.println("HSTORE TEMP 0 " + from_fields + " " + temp1);
         }
         String id_name = n.f0.f0.toString();
         String LHS_type = var_type.get((class_name + "_" + method_name + "_" + id_name));
         if(LHS_type != type_name){
            from_alloc = false;
            if(a != -1){
               var_type.put((class_name + "_._" + id_name), type_name);
            }
            else{
               var_type.put((class_name + "_" + method_name + "_" + id_name), type_name);
            }
         }
      }
      n.f3.accept(this);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
   public R visit(ArrayAssignmentStatement n) {
      R _ret=null;
      String temp1 = n.f0.accept(this).toString();
      n.f1.accept(this);
      String temp2 = n.f2.accept(this).toString();
      n.f3.accept(this);
      n.f4.accept(this);
      String temp3 = n.f5.accept(this).toString();
      n.f6.accept(this);
      String t1 = new_temp();
      String t2 = new_temp();
      String t3 = new_temp();
      String t4 = new_temp();
      String t5 = new_temp();
      if(pass_num == 3){
         System.out.println("MOVE " + t2 + " 1");
         System.out.println("MOVE " + t3 + " PLUS " + t2 + " " + temp2);
         System.out.println("MOVE " + t5 + " " + 4);
         System.out.println("MOVE " + t4 + " TIMES " + t3 + " " + t5);
         System.out.println("MOVE " + t1 + " PLUS " + temp1 + " " + t4 );
         System.out.println("HSTORE " + t1 + " 0 " + temp3);
      }
      return _ret;
   }

   /**
    * f0 -> IfthenElseStatement()
    *       | IfthenStatement()
    */
   public R visit(IfStatement n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public R visit(IfthenStatement n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      String l1 = new_label();
      String temp1 = n.f2.accept(this).toString();
      if(pass_num == 3){
         System.out.println("CJUMP " + temp1 + " " + l1);
      }
      n.f3.accept(this);
      n.f4.accept(this);
      if(pass_num == 3){
         System.out.println(l1);
         System.out.println("NOOP");
      }
      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
   public R visit(IfthenElseStatement n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      String temp1 = n.f2.accept(this).toString();
      n.f3.accept(this);
      String l1 = new_label();
      String l2 = new_label();
      if( pass_num == 3 ){
         System.out.println("CJUMP " + temp1 + " " + l1);
      }
      n.f4.accept(this);
      if( pass_num == 3 ){
         System.out.println("JUMP " + l2);
         System.out.println(l1);
      }
      n.f5.accept(this);
      n.f6.accept(this);
      if( pass_num == 3 ){
         System.out.println(l2);
         System.out.println("NOOP");
      }
      return _ret;
   }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public R visit(WhileStatement n) {
      R _ret=null;
      String l1 = new_label();
      String l2 = new_label();
      if(pass_num == 3){
         System.out.println(l1);
      }
      n.f0.accept(this);
      n.f1.accept(this);
      String temp1 = n.f2.accept(this).toString();
      if(pass_num == 3){
         System.out.println("CJUMP " + temp1 + " " + l2);
      }
      n.f3.accept(this);
      n.f4.accept(this);
      if(pass_num == 3){
         System.out.println("JUMP " + l1);
         System.out.println(l2);
         System.out.println("NOOP");
      }
      return _ret;
   }

   /**
    * f0 -> "do"
    * f1 -> Statement()
    * f2 -> "while"
    * f3 -> "("
    * f4 -> Expression()
    * f5 -> ")"
    * f6 -> ";"
    */
   public R visit(DoStatement n) {
      R _ret=null;
      String l1 = new_label();
      String l2 = new_label();
      if(pass_num == 3){
         System.out.println(l1);
      }
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      n.f3.accept(this);
      String temp1 = n.f4.accept(this).toString();
      if(pass_num == 3){
         System.out.println("CJUMP " + temp1 + " " + l2);
         System.out.println("JUMP " + l1);
         System.out.println(l2);
         System.out.println("NOOP");
      }
      n.f5.accept(this);
      n.f6.accept(this);
      return _ret;
   }

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
   public R visit(PrintStatement n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      String temp1 = n.f2.accept(this).toString();
      n.f3.accept(this);
      n.f4.accept(this);
      if(pass_num == 3){
         System.out.println("PRINT " + temp1);
      }
      return _ret;
   }

   /**
    * f0 -> OrExpression()
    *       | AndExpression()
    *       | CompareExpression()
    *       | neqExpression()
    *       | AddExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | DivExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | PrimaryExpression()
    */
   public R visit(Expression n) {
      R _ret=null;
      String s = n.f0.accept(this).toString();
      return (R)s;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
   public R visit(AndExpression n) {
      R _ret=null;
      String temp1 = n.f0.accept(this).toString();
      String l1 = new_label();
      String t = new_temp();
      if( pass_num == 3 ){
         System.out.println("MOVE " + t + " 0");
         System.out.println("CJUMP " + temp1 + " " + l1);
      }
      n.f1.accept(this);
      String temp2 = n.f2.accept(this).toString();
      if( pass_num == 3 ){
         System.out.println("CJUMP " + temp2 + " " + l1);
         System.out.println("MOVE " + t + " " + temp2);
         System.out.println(l1);
      }
      type_name = "boolean";
      return (R)t;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "||"
    * f2 -> PrimaryExpression()
    */
   public R visit(OrExpression n) {
      R _ret=null;
      String temp1 = n.f0.accept(this).toString();
      String l1 = new_label();
      String l2 = new_label();
      String t = new_temp();
      if( pass_num == 3 ){
         System.out.println("MOVE " + t + " 0");
         System.out.println("CJUMP " + temp1 + " " + l1);
         System.out.println("MOVE " + t + " " + temp1);
         System.out.println("JUMP " + l2);
      }
      n.f1.accept(this);
      if( pass_num == 3 ){
         System.out.println(l1);
      }
      String temp2 = n.f2.accept(this).toString();
      if( pass_num == 3 ){
         System.out.println("CJUMP " + temp2 + " " + l2);
         System.out.println("MOVE " + t + " " + temp2);
         System.out.println(l2);
      }
      type_name = "boolean";
      return (R)t;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<="
    * f2 -> PrimaryExpression()
    */
   public R visit(CompareExpression n) {
      R _ret=null;
      String temp1 = n.f0.accept(this).toString();
      n.f1.accept(this);
      String temp2 = n.f2.accept(this).toString();
      String t = new_temp();
      if(pass_num == 3){
         System.out.println("MOVE " + t + " LE " + temp1 + " " + temp2);
      }
      last_label = new_label();
      type_name = "boolean";
      return (R)t;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "!="
    * f2 -> PrimaryExpression()
    */
   public R visit(neqExpression n) {
      R _ret=null;
      String temp1 = n.f0.accept(this).toString();
      n.f1.accept(this);
      String temp2 = n.f2.accept(this).toString();
      String t = new_temp();
      if(pass_num == 3){
         System.out.println("MOVE " + t + " NE " + temp1 + " " + temp2);
      }
      last_label = new_label();
      type_name = "boolean";
      return (R)t;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   public R visit(AddExpression n) {
      R _ret=null;
      String temp1 = n.f0.accept(this).toString();
      n.f1.accept(this);
      String temp2 = n.f2.accept(this).toString();
      String t = new_temp();
      if(pass_num == 3){
         System.out.println("MOVE " + t + " PLUS " + temp1 + " " + temp2);
      }
      type_name = "int";
      return (R)t;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   public R visit(MinusExpression n) {
      R _ret=null;
      String temp1 = n.f0.accept(this).toString();
      n.f1.accept(this);
      String temp2 = n.f2.accept(this).toString();
      String t = new_temp();
      if(pass_num == 3){
         System.out.println("MOVE " + t + " MINUS " + temp1 + " " + temp2);
      }
      type_name = "int";
      return (R)t;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   public R visit(TimesExpression n) {
      R _ret=null;
      String temp1 = n.f0.accept(this).toString();
      n.f1.accept(this);
      String temp2 = n.f2.accept(this).toString();
      String t = new_temp();
      if(pass_num == 3){
         System.out.println("MOVE " + t + " TIMES " + temp1 + " " + temp2);
      }
      type_name = "int";
      return (R)t;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "/"
    * f2 -> PrimaryExpression()
    */
   public R visit(DivExpression n) {
      R _ret=null;
      String temp1 = n.f0.accept(this).toString();
      n.f1.accept(this);
      String temp2 = n.f2.accept(this).toString();
      String t = new_temp();
      if(pass_num == 3){
         System.out.println("MOVE " + t + " DIV " + temp1 + " " + temp2);
      }
      type_name = "int";
      return (R)t;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
   public R visit(ArrayLookup n) {
      R _ret=null;
      String temp1 = n.f0.accept(this).toString();
      n.f1.accept(this);
      String temp2 = n.f2.accept(this).toString();
      int idx = 0;
      n.f3.accept(this);
      String t1 = new_temp();
      String t2 = new_temp();
      String t3 = new_temp();
      String t4 = new_temp();
      String t5 = new_temp();
      String t6 = new_temp();
      if(pass_num == 3){
         System.out.println("MOVE " + t2 + " 1");
         System.out.println("MOVE " + t3 + " PLUS " + t2 + " " + temp2);
         System.out.println("MOVE " + t5 + " " + 4);
         System.out.println("MOVE " + t4 + " TIMES " + t3 + " " + t5);
         System.out.println("MOVE " + t6 + " PLUS " + temp1 + " " + t4);
         System.out.println("HLOAD " + t1 + " " + t6 + " 0");
      }
      type_name = "int";
      return (R)t1;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
   public R visit(ArrayLength n) {
      R _ret=null;
      String temp1 = n.f0.accept(this).toString();
      n.f1.accept(this);
      n.f2.accept(this);
      String t1 = new_temp();
      String t2 = new_temp();
      String t3 = new_temp();
      if(pass_num == 3){
         System.out.println("MOVE " + t1 + " 0");
         System.out.println("MOVE " + t2 + " PLUS " + temp1 + " " + t1);
         System.out.println("HLOAD " + t3 + " " + t2 + " 0");
      }
      type_name = "int";
      return (R)t3;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
   public R visit(MessageSend n){
      level++;
      R _ret=null;
      String t = n.f0.accept(this).toString();
      String class_type = type_name;
      n.f1.accept(this);
      String id_name = n.f2.accept(this).toString();
      String new_t = new_temp();
      String temp1 = new_temp();
      String temp2 = new_temp();
      String temp3 = new_temp();
      if(pass_num == 3){
         // System.out.println(class_type + " " + id_name);
         int idx = -1;
         int prev = 0;
         Vector<String> v = methods_of_class.get(class_type);
         int ext_1 = 0;
         if(inherited_methods_of_class.containsKey(class_type)){
            Hashtable<String, Vector<String>> ht = inherited_methods_of_class.get(class_type);
            Enumeration<String> e = ht.keys();
            while (e.hasMoreElements()) {
               String key = e.nextElement();
               ext_1 += ht.get(key).size();
            }
         }
         ext_1 += v.size();
         ext_1 *= 4;
         for(int i=0;i<v.size();i++){
            ext_1 -= 4;
            if(v.get(i).equals(id_name)){
               idx = ext_1;
            }
            prev = v.size();
         }
         if(idx == -1 && extended_by.containsKey(class_type)){
            Hashtable<String, Vector<String>> ht = inherited_methods_of_class.get(class_type);
            Enumeration<String> e = ht.keys();
            while(e.hasMoreElements()){
               String key = e.nextElement();
               Vector<String> p = ht.get(key);
               for(int i=0;i<p.size();i++){
                  ext_1 -= 4;
                  if(p.get(i).equals(id_name)){
                     idx = ext_1;
                  }
               }
               prev += p.size();
            }
         }
         System.out.println("MOVE " + temp1 + " " + t);
         System.out.println("HLOAD " + temp2 + " " + temp1 + " 0");
         System.out.println("HLOAD " + temp3 + " " + temp2 + " " + idx);
      }
      n.f3.accept(this);
      if(arguments_of_level.containsKey(level)){
         arguments_of_level.get(level).clear();
      }
      n.f4.accept(this);
      if(pass_num == 3){
         System.out.print("MOVE " + new_t + " CALL " + temp3 + " ( " + temp1 + " ");
         if(arguments_of_level.containsKey(level)){
            Vector<String> arg_v = arguments_of_level.get(level);
            for(int i=0;i<arg_v.size();i++){
               System.out.print(" " + arg_v.get(i) + " ");
            }
         }
         System.out.println(")");
      }
      n.f5.accept(this);
      type_name = ".";
      if(pass_num == 3){
         String tempo = class_type;
         while(method_type.containsKey(tempo + "_" +id_name) == false){
            // System.out.println(tempo);
            if(extended_by.containsKey(tempo)){
               tempo = extended_by.get(tempo);
            }
            else{
               break;
            }
         }
         type_name = method_type.get((tempo + "_" + id_name));
      }
      last_label = new_label();
      level--;
      return (R)new_t;
   }

   /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
   public R visit(ExpressionList n) {
      R _ret=null;
      String temp1 = n.f0.accept(this).toString();
      if(pass_num == 3){
         if(arguments_of_level.containsKey(level) == false){
            Vector<String> v = new Vector<String>();
            arguments_of_level.put(level, v);
         }
         arguments_of_level.get(level).add(temp1);
      }
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Expression()
    */
   public R visit(ExpressionRest n) {
      R _ret=null;
      n.f0.accept(this);
      String temp1 = n.f1.accept(this).toString();
      if(pass_num == 3){
         arguments_of_level.get(level).add(temp1);
      }
      return _ret;
   }

   /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
    */
   public R visit(PrimaryExpression n) {
      R _ret=null;
      String t = n.f0.accept(this).toString();
      return (R)t;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public R visit(IntegerLiteral n) {
      R _ret=null;
      n.f0.accept(this);
      String t = new_temp();
      String num = n.f0.toString();
      if(pass_num == 3){
         System.out.println("MOVE " + t + " " + num);
      }
      type_name = "int";
      return (R)t;
   }

   /**
    * f0 -> "true"
    */
   public R visit(TrueLiteral n){
      R _ret=null;
      n.f0.accept(this);
      String t = new_temp();
      if(pass_num == 3){
         System.out.println("MOVE " + t + " 1");
      }
      type_name = "boolean";
      return (R)t;
   }

   /**
    * f0 -> "false"
    */
   public R visit(FalseLiteral n) {
      R _ret=null;
      n.f0.accept(this);
      String t = new_temp();
      if(pass_num == 3){
         System.out.println("MOVE " + t + " 0");
      }
      type_name = "boolean";
      return (R)t;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(Identifier n) {
      R _ret=null;
      n.f0.accept(this);
      String t = n.f0.toString();
      String var_name = t;
      type_name = t;
      if(pass_num == 3){
         if(from_method){
            String s = class_name + "_" + method_name + "_" + t;
            if(new_var_name.containsKey(s)){
               var_name = new_var_name.get(s);
               type_name = var_type.get(s);
            }
            else{
               Vector<String> v = fields_of_class.get(class_name);
               int sz = v.size();
               for(int i=0;i<v.size();i++){
                  if(v.get(i).equals(t)){
                     var_name = new_temp();
                     type_name = var_type.get(class_name + "_._" + t);
                     String new_t = new_temp();
                     int offset = 4 + i*4;
                     from_fields = offset;
                     System.out.println("HLOAD " + new_t + " TEMP 0 " + offset);
                     System.out.println("MOVE " + var_name + " " + new_t);
                     // new_var_name.put(s,new_t);
                     if(var_type.containsKey(s)){
                        var_type.put(s,type_name);
                     }
                     break;
                  }
               }
               if(var_name == t && extended_by.containsKey(class_name)){
                  Vector<String> p = inherited_fields_of_class.get(class_name);
                  for(int i=0;i<p.size();i++){
                     if(p.get(i).equals(t)){
                        var_name = new_temp();
                        String tempo = class_name;
                        while(!var_type.containsKey(tempo + "_._" + t)){
                           tempo = extended_by.get(tempo);
                        }
                        type_name = var_type.get(tempo + "_._" + t);
                        String new_t = new_temp();
                        int offset = 4 + i*4 + sz*4;
                        from_fields = offset;
                        System.out.println("HLOAD " + new_t + " TEMP 0 " + offset);
                        System.out.println("MOVE " + var_name + " " + new_t);
                        // new_var_name.put(s,new_t);
                        if(var_type.containsKey(s)){
                           var_type.put(s,type_name);
                        }
                        break;
                     }
                  }
               }
            }
         }
         else{
            var_name = t;
            type_name = t;
         }
      }
      // System.out.println(t + " " + type_name);
      return (R)var_name;
   }

   /**
    * f0 -> "this"
    */
   public R visit(ThisExpression n) {
      R _ret=null;
      n.f0.accept(this);
      // String t = new_temp();
      type_name = class_name;
      return (R)"TEMP 0";
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
   public R visit(ArrayAllocationExpression n) {
      alloc_expr = true;
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      String temp1 = n.f3.accept(this).toString();
      String temp2 = new_temp();
      String t1 = new_temp();
      String t2 = new_temp();
      String t3 = new_temp();
      String t4 = new_temp();
      String t5 = new_temp();
      String t6 = new_temp();
      String t7 = new_temp();
      String t8 = new_temp();
      String t9 = new_temp();
      String t10 = new_temp();
      String l1 = new_label();
      String l2 = new_label();
      if(pass_num == 3){
         System.out.println("MOVE " + t5 + " 1");
         System.out.println("MOVE " + t4 + " PLUS " + temp1 + " " + t5);
         System.out.println("MOVE " + t1 + " 4");
         System.out.println("MOVE " + t2 + " TIMES " + t4 + " " + t1);
         System.out.println("MOVE " + t3 + " HALLOCATE " + t2);
         System.out.println("MOVE " + t7 + " 4");
         System.out.println(l1);
         System.out.println("MOVE " + t8 + " LE " + t7 + " MINUS " + t2 + " 4");
         System.out.println("CJUMP " + t8 + " " + l2);
         System.out.println("MOVE " + t9 + " PLUS " + t3 + " " + t7);
         System.out.println("HSTORE "+ t9 + " 0 0");
         System.out.println("MOVE " + t10 + " PLUS " + t7 + " 4");
         System.out.println("MOVE " + t7 + " " + t10);
         System.out.println("JUMP " + l1);
         System.out.println(l2);
         System.out.println("NOOP");
         System.out.println("HSTORE " + t3 + " 0 " + temp1);
      }
      n.f4.accept(this);
      type_name = "int[]";
      return (R)t3;
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   public R visit(AllocationExpression n) {
      R _ret=null;
      n.f0.accept(this);
      String id_name = n.f1.accept(this).toString();
      String cls_name = n.f1.f0.toString();
      n.f2.accept(this);
      n.f3.accept(this);
      String t = new_temp(); //struct
      if(pass_num == 3){
         int ext_1 = 0, ext_2 = 0;
         if(inherited_methods_of_class.containsKey(cls_name)){
            Hashtable<String, Vector<String>> v = inherited_methods_of_class.get(cls_name);
            Enumeration<String> e = v.keys();
            while (e.hasMoreElements()) {
               String key = e.nextElement();
               ext_1 += v.get(key).size();
            }
         }
         if(inherited_fields_of_class.containsKey(cls_name)){
            ext_2 = inherited_fields_of_class.get(cls_name).size();
         }
         int num = 4 * (methods_of_class.get(cls_name).size() + ext_1);
         int f_num = 4 * (fields_of_class.get(cls_name).size() + ext_2) + 4;
         int size = f_num;
         String temp1 = new_temp();  //size
         String temp2 = new_temp(); 
         String temp2_2 = new_temp(); 
         String temp3 = new_temp();  //virtual table
         System.out.println("MOVE " + temp1 + " " + num);
         System.out.println("MOVE " + temp2 + " HALLOCATE " + temp1);
         System.out.println("MOVE " + temp3 + " " + temp2);
         Vector<String> v = methods_of_class.get(cls_name);
         for(String s : v){
            num -= 4;
            String var_name = new_temp();
            System.out.println("MOVE " + var_name + " " + cls_name + "_" + s);
            System.out.println("HSTORE " + temp3 + " " + num + " " + var_name);
         }
         if(ext_1 != 0){
            Hashtable<String, Vector<String>> ht = inherited_methods_of_class.get(cls_name);
            Enumeration<String> e = ht.keys();
            String temp = cls_name;
            while(e.hasMoreElements()){
               String key = e.nextElement();
               for(String s : ht.get(key)){
                  num -= 4;
                  String var_name = new_temp();
                  System.out.println("MOVE " + var_name + " " + key + "_" + s);
                  System.out.println("HSTORE " + temp3 + " " + num + " " + var_name);
               }
            }
         }
         System.out.println("MOVE " + temp1 + " " + size);
         System.out.println("MOVE " + temp2_2 + " HALLOCATE " + temp1);
         System.out.println("MOVE " + t + " " + temp2_2);
         System.out.println("HSTORE " + t + " 0 " + temp3);
         String t1 = new_temp();
         String t2 = new_temp();
         while(f_num != 4){
            String s = new_temp();
            f_num -= 4;
            System.out.println("MOVE " + t1 + " " + f_num );
            System.out.println("MOVE " + t2 + " PLUS " + t + " " + t1);
            System.out.println("HSTORE "+ t2 + " 0 0");
         }
      }
      type_name = cls_name;
      return (R)t;
   }

   /**
    * f0 -> "!"
    * f1 -> Expression()
    */
   public R visit(NotExpression n) {
      R _ret=null;
      n.f0.accept(this);
      String temp1 = n.f1.accept(this).toString();
      String temp2 = new_temp();
      String temp3 = new_temp();
      if(pass_num == 3){
         System.out.println("MOVE " + temp2 + " 1");
         System.out.println("MOVE " + temp3 + " MINUS " + temp2 + " " + temp1);
      }
      type_name = "boolean";
      return (R)temp3;
   }

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
   public R visit(BracketExpression n) {
      R _ret=null;
      n.f0.accept(this);
      String t = n.f1.accept(this).toString();
      n.f2.accept(this);
      return (R)t;
   }

   /**
    * f0 -> Identifier()
    * f1 -> ( IdentifierRest() )*
    */
   public R visit(IdentifierList n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Identifier()
    */
   public R visit(IdentifierRest n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      return _ret;
   }
}