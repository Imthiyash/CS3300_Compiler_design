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
   Hashtable<String, Integer> max_arguments = new Hashtable<String, Integer>();
   Hashtable<String, Pair<Integer, Integer>> temporaries = new Hashtable<String, Pair<Integer, Integer>>();
   Hashtable<String, Integer> loop_end = new Hashtable<String, Integer>();
   Hashtable<String, String> register_of = new Hashtable<String, String>();
   Hashtable<String, String> first_def = new Hashtable<String, String>();
   Hashtable<String, String> spill_of = new Hashtable<String, String>();
   Hashtable<String, Integer> spilled_count = new Hashtable<String, Integer>();
   Vector<Pair<String, Pair<Integer, Integer>>> live_intervals = new Vector<Pair<String, Pair<Integer, Integer>>>();
   Vector<String> registers = new Vector<String>();
   Vector<String> active = new Vector<String>();
   Vector<Integer> free_reg = new Vector<Integer>();
   Vector<String> labels_till = new Vector<String>();
   Vector<String> loops = new Vector<String>();
   boolean from_stmt_list = false;
   boolean from_call = false;
   boolean from_stmt = false;
   boolean use = false;
   String procedure_name = ".";
   String loop_label = ".";
   String spill_reg = "v0";
   int curr_arguments = 0;
   int spill_count = 0;
   int stack_index = 0;
   int line_count = 0;
   int proc_start = 0;
   int second_num = 0;
   int first_num = 0;
   int third_num = 0;
   int pass_num = 0;
   int index = 0;

   public void LinearScanRegisterAllocation(){
      // for(int i=0;i<live_intervals.size();i++){
      //    Pair<String, Pair<Integer, Integer>> p = live_intervals.get(i);
      //    System.out.println(p.getKey()+" " + p.getValue().getKey() + " " + p.getValue().getValue());
      // }
      for(int i=0;i<live_intervals.size();i++){
         ExpireOldIntervals(i);
         if(active.size() == 18){
            SpillAtInterval(i);
         }
         else{
            String reg = get_free_reg();
            String t = live_intervals.get(i).getKey();
            active.add(t);
            register_of.put(t,reg);
         }
      }
      for(int i=0;i<free_reg.size();i++){
         free_reg.set(i,0);
      }
   }

   public void ExpireOldIntervals(int i){
      int startpoint = live_intervals.get(i).getValue().getKey();
      for(int j = 0; j < active.size();j++){
         int endpoint = temporaries.get(active.get(j)).getValue();
         if(endpoint <= startpoint){
            String t = active.get(j);
            String reg = register_of.get(t);
            free_reg.set(registers.indexOf(reg),0);
            active.remove(j);
            j--;
         }
      }
   }

   public void SpillAtInterval(int i){
      int idx = 0;
      for(int j=0;j<active.size();j++){
         int epi = temporaries.get(active.get(idx)).getValue();
         int epj = temporaries.get(active.get(j)).getValue();
         if(epj > epi){
            idx = j;
         }
      }
      int epidx = temporaries.get(active.get(idx)).getValue();
      String t = live_intervals.get(i).getKey();
      int epi = temporaries.get(t).getValue();
      if(epidx > epi){
         register_of.put(t,register_of.get(active.get(idx)));
         register_of.remove(active.get(idx));
         active.remove(idx);
         active.add(t);
      }
      spill_count++;
   }

   public class Pair<K, V> {
      private K key;
      private V value;
      public Pair(K key, V value) {
         this.key = key;
         this.value = value;
      }
      public K getKey() {
         return key;
      }
      public V getValue() {
         return value;
      }
   }

   public String get_free_reg(){
      int i=0;
      while (i < free_reg.size()) {
         if(free_reg.get(i) == 0){
            free_reg.set(i,1);
            return registers.get(i);
         }
         i++;
      }
      return "None";
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
    * f0 -> "MAIN"
    * f1 -> StmtList()
    * f2 -> "END"
    * f3 -> ( Procedure() )*
    * f4 -> <EOF>
    */
   public R visit(Goal n) {
      R _ret=null;
      for(int i=0;i<=7;i++){
         String reg = "s" + String.valueOf(i);
         registers.add(reg);
         free_reg.add(0);
      }
      for(int i=0;i<=9;i++){
         String reg = "t" + String.valueOf(i);
         registers.add(reg);
         free_reg.add(0);
      }
      while(pass_num <= 3){
         procedure_name = "main";
         labels_till.clear();
         // System.out.println(register_of);
         stack_index = 0;
         line_count = 0;
         spill_count = 0;
         proc_start = 0;
         if(pass_num == 3){
            first_num = 0;
            second_num = 10;
            third_num = 0;
            if(max_arguments.containsKey("main")){
               third_num = max_arguments.get("main");
            }
            System.out.println("MAIN [0] ["+(10+spilled_count.get("main"))+"] [" + third_num + "]");
         }
         n.f0.accept(this);
         n.f1.accept(this);
         n.f2.accept(this);
         if(pass_num == 2){
            LinearScanRegisterAllocation();
         }
         if(pass_num == 2){
            spilled_count.put("main", spill_count);
         }
         if(pass_num == 3){
            System.out.println("END");
            if(spilled_count.get("main") == 0){
               System.out.println("// NOTSPILLED");
            }
            else{
               System.out.println("// SPILLED");
            }
         }
         n.f3.accept(this);
         n.f4.accept(this);
         pass_num++;
      }
      return _ret;
   }

   /**
    * f0 -> ( ( Label() )? Stmt() )*
    */
   public R visit(StmtList n) {
      from_stmt_list = true;
      R _ret=null;
      n.f0.accept(this);
      from_stmt_list = false;
      return _ret;
   }

   /**
    * f0 -> Label()
    * f1 -> "["
    * f2 -> IntegerLiteral()
    * f3 -> "]"
    * f4 -> StmtExp()
    */
   public R visit(Procedure n) {
      R _ret=null;
      live_intervals.clear();
      spill_count = 0;
      procedure_name = (n.f0.f0).toString();
      line_count++;
      proc_start = line_count;
      n.f0.accept(this);
      n.f1.accept(this);
      String temp1 = (String)n.f2.accept(this);
      first_num = Integer.valueOf(temp1);
      second_num = 0;
      third_num = 0;
      stack_index = 0;
      if(first_num > 4){
         second_num += (first_num - 4);
         stack_index = second_num;
      }
      if(max_arguments.containsKey(procedure_name)){
         second_num += 18;
         third_num += max_arguments.get(procedure_name);
      }
      else{
         second_num += 8;
      }
      if(pass_num == 2){
         for(int i=0;i<first_num;i++){
            String temps = procedure_name + "/TEMP_" + String.valueOf(i);
            if(temporaries.containsKey(temps)){
               Pair<String, Pair<Integer, Integer>> p = new Pair<String, Pair<Integer, Integer>>(temps,temporaries.get(temps));
               live_intervals.add(p);
            }
         }
      }
      if(pass_num == 3){
         second_num += spilled_count.get(procedure_name);
         System.out.println(procedure_name + " [" + first_num + "] [" + second_num + "] [" + third_num + "]");
         int i = 0;
         while(i<=7){
            System.out.println("\tASTORE SPILLEDARG " + stack_index + " s" + i);
            stack_index++;
            i++;
         }
         for(i=0;i<first_num;i++){
            String temps = procedure_name + "/TEMP_" + String.valueOf(i);
            if(register_of.containsKey(temps)){
               if(i < 4){
                  System.out.println("\tMOVE " + register_of.get(temps) + " a" + i);
               }
               else{
                  System.out.println("\tALOAD v1 SPILLEDARG " + (i-4));
                  System.out.println("\tMOVE " + register_of.get(temps) + " v1");
               }
            }
            else{
               if(temporaries.get(temps).getValue() != proc_start){
                  String s = "SPILLEDARG " + String.valueOf(stack_index);
                  spill_of.put(temps,s);
                  stack_index++;
                  if(i<4){
                     System.out.println("\tMOVE v0 a" + i);
                     System.out.println("\tASTORE " + s + " v0");
                  }
                  else{
                     System.out.println("\tALOAD v1 SPILLEDARG " + (i-4));
                     System.out.println("\tASTORE " + s + " v1");
                  }
               }
            }
         }
      }
      n.f3.accept(this);
      n.f4.accept(this);
      if(pass_num == 2){
         spilled_count.put(procedure_name, spill_count);
      }
      procedure_name = ".";
      return _ret;
   }

   /**
    * f0 -> NoOpStmt()
    *       | ErrorStmt()
    *       | CJumpStmt()
    *       | JumpStmt()
    *       | HStoreStmt()
    *       | HLoadStmt()
    *       | MoveStmt()
    *       | PrintStmt()
    */
   public R visit(Stmt n) {
      line_count++;
      from_stmt = true;
      R _ret=null;
      n.f0.accept(this);
      from_stmt = false;
      return _ret;
   }

   /**
    * f0 -> "NOOP"
    */
   public R visit(NoOpStmt n) {
      R _ret=null;
      n.f0.accept(this);
      if(pass_num == 3){
         System.out.println("\tNOOP");
      }
      return _ret;
   }

   /**
    * f0 -> "ERROR"
    */
   public R visit(ErrorStmt n) {
      R _ret=null;
      n.f0.accept(this);
      if(pass_num == 3){
         System.out.println("\tERROR");
      }
      return _ret;
   }

   /**
    * f0 -> "CJUMP"
    * f1 -> Temp()
    * f2 -> Label()
    */
   public R visit(CJumpStmt n) {
      R _ret=null;
      n.f0.accept(this);
      use = true;
      String temp1 = (String)n.f1.accept(this);
      use = false;
      String temp2 = (String)n.f2.accept(this);
      if(pass_num == 1){
         labels_till.add(procedure_name + "_" + temp2);
      }
      if(pass_num == 3){
         System.out.println("\tCJUMP " + temp1 + " " + procedure_name + "_" + temp2);
      }
      return _ret;
   }

   /**
    * f0 -> "JUMP"
    * f1 -> Label()
    */
   public R visit(JumpStmt n) {
      R _ret=null;
      n.f0.accept(this);
      use = true;
      String temp1 = (String)n.f1.accept(this);
      use = false;
      if(pass_num == 0){
         loop_end.put(procedure_name + "_" + temp1,line_count);
      }
      if(pass_num == 1){
         labels_till.add(procedure_name + "_" + temp1);
         if((procedure_name + "_" + temp1).equals(loop_label)){
            loop_label = ".";
         }
      }
      if(pass_num == 3){
         System.out.println("\tJUMP " + procedure_name + "_" + temp1);
      }
      return _ret;
   }

   /**
    * f0 -> "HSTORE"
    * f1 -> Temp()
    * f2 -> IntegerLiteral()
    * f3 -> Temp()
    */
   public R visit(HStoreStmt n) {
      R _ret=null;
      n.f0.accept(this);
      String temp1 = (String)n.f1.accept(this);
      String temp2 = (String)n.f2.accept(this);
      use = true;
      String temp3 = (String)n.f3.accept(this);
      use = false;
      if(pass_num == 3){
         if(registers.contains(temp1)){
            System.out.println("\tHSTORE " + temp1 + " " + temp2 + " " + temp3);
         }
         else{
            String reg = spill_reg;
            System.out.println("\tALOAD " + reg + " " + temp1);
            System.out.println("\tHSTORE " + reg + " " + temp2 + " " + temp3);
            if(spill_reg.equals("v0")) spill_reg = "v1";
            else spill_reg = "v0";
            System.out.println("\tASTORE " + temp1 + " " + reg);
         }
      }
      return _ret;
   }

   /**
    * f0 -> "HLOAD"
    * f1 -> Temp()
    * f2 -> Temp()
    * f3 -> IntegerLiteral()
    */
   public R visit(HLoadStmt n) {
      R _ret=null;
      n.f0.accept(this);
      String temp1 = (String)n.f1.accept(this);
      use = true;
      String temp2 = (String)n.f2.accept(this);
      use = false;
      String temp3 = (String)n.f3.accept(this);
      if(pass_num == 3){
         if(registers.contains(temp1)){
            System.out.println("\tHLOAD " + temp1 + " " + temp2 + " " + temp3);
         }
         else{
            String reg = spill_reg;
            System.out.println("\tHLOAD " + reg + " " + temp2 + " " + temp3);
            if(spill_reg.equals("v0")) spill_reg = "v1";
            else spill_reg = "v0";
            System.out.println("\tASTORE " + temp1 + " "+ reg);
         }
      }
      return _ret;
   }

   /**
    * f0 -> "MOVE"
    * f1 -> Temp()
    * f2 -> Exp()
    */
   public R visit(MoveStmt n) {
      R _ret=null;
      n.f0.accept(this);
      String temp1 = (String)n.f1.accept(this);
      use = true;
      String temp2 = (String)n.f2.accept(this);
      use = false;
      if(pass_num == 3){
         if(registers.contains(temp1)){
            System.out.println("\tMOVE " + temp1 + " " + temp2);
         }
         else{
            String reg = spill_reg;
            System.out.println("\tMOVE " + reg + " " + temp2);
            if(spill_reg.equals("v0")) spill_reg = "v1";
            else spill_reg = "v0";
            System.out.println("\tASTORE " + temp1 + " " + reg);
         }
      }
      return _ret;
   }

   /**
    * f0 -> "PRINT"
    * f1 -> SimpleExp()
    */
   public R visit(PrintStmt n) {
      R _ret=null;
      n.f0.accept(this);
      use = true;
      String temp1 = (String)n.f1.accept(this);
      use = false;
      if(pass_num == 3){
         System.out.println("\tPRINT " + temp1);
      }
      return _ret;
   }

   /**
    * f0 -> Call()
    *       | HAllocate()
    *       | BinOp()
    *       | SimpleExp()
    */
   public R visit(Exp n) {
      R _ret=null;
      String temp = (String)n.f0.accept(this);
      return (R)temp;
   }

   /**
    * f0 -> "BEGIN"
    * f1 -> StmtList()
    * f2 -> "RETURN"
    * f3 -> SimpleExp()
    * f4 -> "END"
    */
   public R visit(StmtExp n) {
      R _ret=null;
      n.f0.accept(this);
      if(pass_num == 1){
         for(int i=0;i<first_num;i++){
            String s = procedure_name + "/TEMP_" + String.valueOf(i);
            temporaries.put(s,(new Pair<Integer, Integer>(line_count, line_count)));
            first_def.put(s,".");
         }
      }
      n.f1.accept(this);
      n.f2.accept(this);
      use = true;
      String temp1 = (String)n.f3.accept(this);
      use = false;
      n.f4.accept(this);
      if(pass_num == 2){
         LinearScanRegisterAllocation();
      }
      if(pass_num == 3){
         System.out.println("\tMOVE v0 " + temp1);
         stack_index = 0;
         if(first_num > 4){
            stack_index = first_num - 4;
         }
         int i = 0;
         while(i<=7){
            System.out.println("\tALOAD s"+i+" SPILLEDARG " + stack_index);
            stack_index++;
            i++;
         }
         System.out.println("END");
         if(spilled_count.get(procedure_name) == 0){
            System.out.println("// NOTSPILLED");
         }
         else{
            System.out.println("// SPILLED");
         }
      }
      return _ret;
   }

   /**
    * f0 -> "CALL"
    * f1 -> SimpleExp()
    * f2 -> "("
    * f3 -> ( Temp() )*
    * f4 -> ")"
    */
   public R visit(Call n) {
      R _ret=null;
      int start = stack_index;
      if(pass_num == 3){
         int i = 0;
         while(i<=9){
            System.out.println("\tASTORE SPILLEDARG " + stack_index + " t" + i);
            stack_index++;
            i++;
         }
         stack_index -= 10;
      }
      n.f0.accept(this);
      String temp1 = n.f1.accept(this).toString();
      n.f2.accept(this);
      from_call = true;
      curr_arguments = 0;
      use = true;
      n.f3.accept(this);
      use = false;
      from_call = false;
      if(pass_num == 1){
         if((max_arguments.containsKey(procedure_name) && max_arguments.get(procedure_name) < curr_arguments) || (max_arguments.containsKey(procedure_name) == false)){
            max_arguments.put(procedure_name, curr_arguments);
         }
      }
      if(pass_num == 3){
         if(registers.contains(temp1)){
            System.out.println("\tCALL " + temp1);
         }
         else{
            use = true;
            String temps = n.f1.accept(this).toString();
            use = false;
            System.out.println("\tCALL " + temps);
         }
         int i = 0;
         while(i<=9){
            System.out.println("\tALOAD t" + i + " SPILLEDARG " + stack_index);
            stack_index++;
            i++;
         }
         stack_index -= 10;
      }
      n.f4.accept(this);
      return (R)"v0";
   }

   /**
    * f0 -> "HALLOCATE"
    * f1 -> SimpleExp()
    */
   public R visit(HAllocate n) {
      R _ret=null;
      n.f0.accept(this);
      use = true;
      String temp1 = (String)n.f1.accept(this);
      use = false;
      if(pass_num == 3){
         _ret = (R)("HALLOCATE " + temp1);
      }
      return _ret;
   }

   /**
    * f0 -> Operator()
    * f1 -> Temp()
    * f2 -> SimpleExp()
    */
   public R visit(BinOp n) {
      R _ret=null;
      String op = (String)n.f0.accept(this);
      use = true;
      String temp1 = (String)n.f1.accept(this);
      String temp2 = (String)n.f2.accept(this);
      use = false;
      if(pass_num == 3){
         _ret = (R)(op + " " + temp1 + " " + temp2);
      }
      return _ret;
   }

   /**
    * f0 -> "LE"
    *       | "NE"
    *       | "PLUS"
    *       | "MINUS"
    *       | "TIMES"
    *       | "DIV"
    */
   public R visit(Operator n) {
      R _ret=null;
      n.f0.accept(this);
      String op = ".";
      if(n.f0.which == 0)op = "LE";
      else if(n.f0.which == 1)op = "NE";
      else if(n.f0.which == 2)op = "PLUS";
      else if(n.f0.which == 3)op = "MINUS";
      else if(n.f0.which == 4)op = "TIMES";
      else if(n.f0.which == 5)op = "DIV";
      return (R)op;
   }

   /**
    * f0 -> Temp()
    *       | IntegerLiteral()
    *       | Label()
    */
   public R visit(SimpleExp n) {
      R _ret=null;
      String reg = (String)n.f0.accept(this);
      if(pass_num == 3){
         if(n.f0.which == 1){
            System.out.println("\tMOVE " + spill_reg + " " + reg);
            reg = spill_reg;
            if(spill_reg.equals("v0")) spill_reg = "v1";
            else spill_reg = "v0";
         }
      }
      return (R)reg;
   }

   /**
    * f0 -> "TEMP"
    * f1 -> IntegerLiteral()
    */
   public R visit(Temp n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      String temp1 = procedure_name + "/TEMP_" + (n.f1.f0.toString());
      if(pass_num == 1 && from_call){
         curr_arguments++;
      }
      if(pass_num == 1){
         if(temporaries.containsKey(temp1) == false){
            int a = line_count;
            if(Integer.valueOf(n.f1.f0.toString()) < first_num){
               a = proc_start;
            }
            temporaries.put(temp1,(new Pair<Integer,Integer>(a,line_count)));
            if(a == line_count){
               first_def.put(temp1, loop_label);
            }
            else{
               first_def.put(temp1, ".");
            }
         }
         else{
            if(loop_label == "." || first_def.get(temp1) != "."){
               temporaries.put(temp1,(new Pair<Integer,Integer>(temporaries.get(temp1).getKey(),line_count)));
            }
            else{
               // System.out.println(temp1 + " " + loop_label);
               temporaries.put(temp1,(new Pair<Integer,Integer>(temporaries.get(temp1).getKey(),loop_end.get(loop_label))));
            }
         }
      }
      if(pass_num == 2){
         Pair<String, Pair<Integer, Integer>> p = new Pair<String, Pair<Integer, Integer>>(temp1,temporaries.get(temp1));
         boolean a = true;
         for(int i=0;i<live_intervals.size();i++){
            if(live_intervals.get(i).getKey().equals(temp1)){
               a = false;
               break;
            }
         }
         if(a){
            live_intervals.add(p);
         }
      }
      String reg = "No_reg_yet";
      if(pass_num == 3){
         if(register_of.containsKey(temp1)){
            reg = register_of.get(temp1);
         }
         else{
            if(use){
               System.out.println("\tALOAD " + spill_reg + " " + spill_of.get(temp1));
               reg = spill_reg;
               if(spill_reg.equals("v0")) spill_reg = "v1";
               else spill_reg = "v0";
            }
            else{
               if(spill_of.containsKey(temp1)==false){
                  String s = "SPILLEDARG " + String.valueOf(stack_index);
                  spill_of.put(temp1,s);
                  stack_index++;
                  reg = s;
               }
               else{
                  reg = spill_of.get(temp1);
               }
            }
         }
      }
      if(pass_num == 3 && from_call){
         curr_arguments++;
         if(curr_arguments <= 4){
            System.out.println("\tMOVE a"+(curr_arguments-1) + " " + reg);
         }
         else{
            System.out.println("\tPASSARG " + (curr_arguments - 4) + " " + reg);
         }
      }
      return (R)reg;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public R visit(IntegerLiteral n) {
      R _ret=null;
      n.f0.accept(this);
      return (R)(n.f0.toString());
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(Label n) {
      R _ret=null;
      n.f0.accept(this);
      String label = (n.f0.toString());
      if(from_stmt_list && !from_stmt){
         label = procedure_name + "_" + label;
         if(pass_num == 1 && labels_till.contains(label) == false && loop_label == "."){
            loop_label = label;
         }
         if(pass_num == 3 ){
            System.out.println(label);
         }
      }
      return (R)label;
   }
}