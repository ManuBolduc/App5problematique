package APP5;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;



/** @author Ahmed Khoumsi */

/** Cette classe effectue l'analyse syntaxique
 */
public class DescenteRecursive {

  ArrayList<Terminal> expression;
  int read_index;
/** Constructeur de DescenteRecursive :
      - recoit en argument le nom du fichier contenant l'expression a analyser
      - pour l'initalisation d'attribut(s)
 */
public DescenteRecursive(String in) {
  read_index = 0;
  expression = new ArrayList<>();
  Reader r = new Reader(in);
  AnalLex lexical = new AnalLex(r.toString());
  while(!lexical.finFichier) {
    expression.add(lexical.prochainTerminal());
  }
}
public DescenteRecursive(ArrayList<Terminal> liste ){
  read_index = 0;
  expression = liste;
}


/** AnalSynt() effectue l'analyse syntaxique et construit l'AST.
 *    Elle retourne une reference sur la racine de l'AST construit
 */
public ElemAST AnalSynt( ) {
   return E();
}

public ElemAST E() {
  ElemAST n1,n3;
  n1 = T();
  if (read_index < expression.size()){
  if (expression.get(read_index).chaine.equals("+")){
    read_index ++;
    ElemAST n2 = E();
    n3 = new NoeudAST("+", n1, n2);
    n1=n3;}
  else if (expression.get(read_index).chaine.equals("-")) {
    read_index ++;
    ElemAST n2 = E();
    n3 = new NoeudAST("-", n1, n2);
    n1=n3;
  }}
  return n1;
}

public ElemAST E_(){
  ElemAST n1,n3;
  n1 = new FeuilleAST(expression.get(read_index));
  read_index ++;
  if (read_index < expression.size()){
    if (expression.get(read_index).chaine.equals("+")){
      read_index ++;
      ElemAST n2 = E();
      n3 = new NoeudAST("+", n1, n2);
      n1=n3;}
    else if (expression.get(read_index).chaine.equals("-")) {
      read_index ++;
      ElemAST n2 = T();
      n3 = new NoeudAST("-", n1, n2);
      n1=n3;
    }}
  return n1;
}

public ElemAST T(){
  ElemAST n1,n3;
  n1 = Z();
  if (read_index < expression.size()){
    if (expression.get(read_index).chaine.equals("*")){
      read_index ++;
      ElemAST n2 = T();
      n3 = new NoeudAST("*", n1, n2);
      n1=n3;}
    else if (expression.get(read_index).chaine.equals("/")) {
      read_index ++;
      ElemAST n2 = T();
      n3 = new NoeudAST("/", n1, n2);
      n1=n3;
    }}
  return n1;
}

public ElemAST Z(){
  ElemAST n1 = new FeuilleAST(new Terminal("DEVRAIT PAS VOIR CA",typeTerminal.ERREUR));

  if (read_index < expression.size()){
    Terminal tmp = expression.get(read_index);
    if (tmp.type == typeTerminal.CHIFFRE || tmp.type == typeTerminal.VARIABLE  ){
      n1 = new FeuilleAST(tmp);

      read_index ++;
    }
    else if (tmp.type == typeTerminal.PARANTHESEOUVRANTE) {
      read_index ++;
      n1 = E();
      if (expression.get(read_index).type == typeTerminal.PARANTHESEFERMANTE){
        read_index++;
      }
      else {System.out.print("ERREUR DANS Z()");}
    }
  }
  return n1;
}



/** ErreurSynt() envoie un message d'erreur syntaxique
 */
public void ErreurSynt(String s) {
    //
}



  //Methode principale a lancer pour tester l'analyseur syntaxique 
  public static void main(String[] args) {
    //testVisualizeTree();

    ArrayList<Terminal> tmp = new ArrayList<>();

    String toWriteLect = "";
    String toWriteEval = "";


    System.out.println("Debut d'analyse syntaxique");
    if (args.length == 0){
      args = new String [2];
      args[0] = "APP5/input.txt";
      args[1] = "ResultatSyntaxique.txt";
    }
    DescenteRecursive dr = new DescenteRecursive(args[0]);
    try {
      ElemAST RacineAST = dr.AnalSynt();
      toWriteLect += "Lecture de l'AST trouve : "+ "\n" + RacineAST.LectAST() + "\n";
      System.out.println(toWriteLect);
      System.out.println("Lecture postfix : ");
      tmp = RacineAST.StartPostFix();
      System.out.println(printPostFix(tmp));
      System.out.println(RacineAST.EvalASTPostFix(tmp));
      toWriteEval += "Evaluation de l'AST trouve : " + RacineAST.EvalAST() + "\n";
      System.out.println(toWriteEval);
      //Writer w = new Writer(args[1],toWriteLect+toWriteEval); // Ecriture de toWrite
                                                              // dans fichier args[1]
    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
      System.exit(51);
    }
    System.out.println("Analyse syntaxique terminee");
  }

  private static boolean checkErreurParanthese(int ouvrante, int fermante){
    if(ouvrante!= fermante){
      int i = ouvrante - fermante;
      System.out.print("\nErreur , il manque : ");
      if (ouvrante < fermante){
        System.out.print(-i);
        System.out.print(" paranthese ouvrante");}
      else{
        System.out.print(i);
        System.out.print(" paranthese fermante");
      }
      return false;}
    else
      return true;
  }

  private static void testVisualizeTree(){
    ElemAST feuille1 = new FeuilleAST("8");
    ElemAST feuille2 = new FeuilleAST("9");
    ElemAST feuille3 = new FeuilleAST("10");
    ElemAST feuille4 = new FeuilleAST("11");


    ElemAST noeud2 = new NoeudAST("+",feuille3,feuille4);
    ElemAST noeud3 = new NoeudAST("+",noeud2,feuille2);
    ElemAST noeud1 = new NoeudAST("+",noeud2,noeud3);
    System.out.println(noeud1.EvalAST());
    System.out.println(noeud1.LectAST());
  }

  private static void testBothAnalyser(){
    String toWriteLect = "";
    String toWriteEval = "";

    ArrayList<Terminal> tmp = new ArrayList<>();

    AnalLex lexical = new AnalLex().runOnTextFile("APP5/input.txt","outpout.txt");

    if(checkErreurParanthese(lexical.countParantheseOuvrante,lexical.countParantheseFermante)){}

    else{
    DescenteRecursive dr = new DescenteRecursive(lexical.liste_terminaux);

    try {
      ElemAST RacineAST = dr.AnalSynt();
      toWriteLect += "Lecture de l'AST trouve : "+ "\n" + RacineAST.LectAST() + "\n";
      System.out.println(toWriteLect);
      tmp = RacineAST.StartPostFix();
      System.out.println(printPostFix(tmp));
      toWriteEval += "Evaluation de l'AST trouve : " + RacineAST.EvalAST() + "\n";
      System.out.println(toWriteEval);
      //Writer w = new Writer(args[1],toWriteLect+toWriteEval); // Ecriture de toWrite
      // dans fichier args[1]
    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
      System.exit(51);
    }
    System.out.println("Analyse syntaxique terminee");

  }}

  private static String printPostFix (ArrayList<Terminal> liste){
  String s= "";
  for (Terminal term : liste){
    s+=(term.toString()) +" ";
  }
  return s;
  }
}

