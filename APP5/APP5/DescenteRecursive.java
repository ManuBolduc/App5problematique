package APP5;
import java.util.ArrayList;



/** @author Ahmed Khoumsi */

/** Cette classe effectue l'analyse syntaxique
 */
public class DescenteRecursive {

  ArrayList<Terminal> expression;
  int read_index;
  boolean flag1 = false;
/** Constructeur de DescenteRecursive :
      - recoit en argument le nom du fichier contenant l'expression a analyser
      - pour l'initalisation d'attribut(s)
 */
public DescenteRecursive(String in) {
  read_index = 0;
  expression = new ArrayList<>();
  Reader r = new Reader(in);
  AnalLex lexical = new AnalLex().runOnTextFile(in,"output.txt");
  expression= lexical.liste_terminaux;
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
    if (expression.get(read_index).chaine.equals("+") || expression.get(read_index).chaine.equals("-")){
      String op = expression.get(read_index).chaine;
      read_index ++;
      ElemAST n2 = E();
      n3 = new NoeudAST(op, n1, n2);
      n1=n3;
    }
    else if(expression.get(read_index).type == typeTerminal.VARIABLE || expression.get(read_index).type == typeTerminal.CHIFFRE){
      n1.valeur.chaine = "Erreur: operande suivie d'un autre operande\n";
    }
    else if(expression.get(read_index).type == typeTerminal.PARANTHESEFERMANTE || expression.get(read_index).type == typeTerminal.PARANTHESEOUVRANTE){
      n1.valeur.chaine = "Erreur: operande suivie d'une parenthese\n";
    }
  }
  return n1;
}

public ElemAST T(){
  ElemAST n1,n3;
  n1 = Z();
  if (read_index < expression.size()){
    if (expression.get(read_index).chaine.equals("*") || expression.get(read_index).chaine.equals("/")){
      String op = expression.get(read_index).chaine;
      read_index ++;
      ElemAST n2 = T();
      n3 = new NoeudAST(op, n1, n2);
      n1=n3;
    }
    else if(expression.get(read_index).type == typeTerminal.VARIABLE || expression.get(read_index).type == typeTerminal.CHIFFRE){
      n1.valeur.chaine = "Erreur: operande suivie  d'un autre operande\n";
    }
    else if(expression.get(read_index).type == typeTerminal.PARANTHESEFERMANTE || expression.get(read_index).type == typeTerminal.PARANTHESEOUVRANTE){
      n1.valeur.chaine = "Erreur: operande suivie d'une parenthese\n";
    }
  }
  return n1;
}

public ElemAST Z(){
  //ElemAST n1 = new FeuilleAST(new Terminal("ERREUR a l'unite lexical : "+String.valueOf(read_index) ,typeTerminal.ERREUR));
  ElemAST n1 = new FeuilleAST(new Terminal("ERREUR fichier ne finit pas sur un operande ou une parenthese fermante\n"));
  if (read_index < expression.size()){
    Terminal tmp = expression.get(read_index);
    if (tmp.type == typeTerminal.CHIFFRE || tmp.type == typeTerminal.VARIABLE  ){
      n1 = new FeuilleAST(tmp);

      read_index ++;
    }
    else if (tmp.type == typeTerminal.PARANTHESEOUVRANTE) {
      read_index ++;
      n1 = E();
      if (read_index < expression.size()) {
        if (expression.get(read_index).type == typeTerminal.PARANTHESEFERMANTE) {
          read_index++;
        }
      }
      else {n1.valeur.chaine = "Erreur: parenthese ouvrante sans parenthese fermante\n";}
    }
    else if (read_index == 0){
      n1.valeur.chaine = "Erreur: debute par un operateur ou une parenthese fermante\n";
    }
    else if (read_index < expression.size()-1){
      n1.valeur.chaine = "Erreur: parenthese fermante ou operateur a la suite d'un operateur ou d'une parenthese ouvrante\n";
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
    if (args.length == 0) {
      args = new String[2];
      args[0] = "APP5/input.txt";
      args[1] = "output.txt";
    }
    System.out.print(testBothAnalyser(args[0],args[1]));
//    DescenteRecursive dr = new DescenteRecursive(args[0]);
//    try {
//      ElemAST RacineAST = dr.AnalSynt();
//      toWriteLect += "Lecture de l'AST trouve : "+ "\n" + RacineAST.LectAST() + "\n";
//      System.out.println(toWriteLect);
//      System.out.println("Lecture postfix : ");
//      tmp = RacineAST.StartPostFix();
//      System.out.println(printPostFix(tmp));
//      //System.out.println(RacineAST.EvalASTPostFix(tmp));
//      toWriteEval += "Evaluation de l'AST trouve : " + RacineAST.EvalAST() + "\n";
//      System.out.println(toWriteEval);
//      //Writer w = new Writer(args[1],toWriteLect+toWriteEval); // Ecriture de toWrite
//                                                              // dans fichier args[1]
//    } catch (Exception e) {
//      System.out.println(e);
//      e.printStackTrace();
//      System.exit(51);
//    }
//    System.out.println("Analyse syntaxique terminee");
//  }
  }
  private static boolean checkErreurParanthese(int ouvrante, int fermante){
    if(ouvrante== fermante)
      return false;
    else
      return true;
  }

  private static String parseErreurParanthese(int ouvrante, int fermante){
    int i = ouvrante - fermante;
    String tmp="";
    tmp+="\nErreur , il manque : ";
    if (ouvrante < fermante){
      tmp+=String.valueOf(-i);
      tmp+=" paranthese ouvrante";}
    else{
      tmp+=String.valueOf(i);
      tmp+=" paranthese ouvrante";}
    return tmp;
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

  private static String testBothAnalyser(String input, String output){
    String toWriteLect = "";
    String toWriteEval = "";
    String ErreurRetour="";

    ArrayList<Terminal> tmp = new ArrayList<>();

    AnalLex lexical = new AnalLex().runOnTextFile(input,output);

    for(Terminal terminaux: lexical.liste_terminaux){
      if (terminaux.type==typeTerminal.ERREUR){
        ErreurRetour+="Erreur dans l'analyse lexical: "+terminaux.chaine ;
        return ErreurRetour;
      }
    }

    if(checkErreurParanthese(lexical.countParantheseOuvrante,lexical.countParantheseFermante)){
      ErreurRetour+= parseErreurParanthese(lexical.countParantheseOuvrante,lexical.countParantheseFermante);
      return ErreurRetour;
    }
    else{

      DescenteRecursive dr = new DescenteRecursive(lexical.liste_terminaux);


    try {
      ElemAST RacineAST = dr.AnalSynt();
      toWriteLect += "Lecture de l'AST trouve : "+ "\n" + RacineAST.LectAST() + "\n";
      System.out.println(toWriteLect);
      System.out.println("Evaluation de l'AST trouve : ") ;

      tmp = RacineAST.StartPostFix();
      for(Terminal terminaux : tmp)
      {
        if(terminaux.type==typeTerminal.ERREUR){
          throw new Exception(terminaux.chaine);
        }
      }
      toWriteEval += RacineAST.EvalAST() + "\n";

      System.out.println(toWriteEval);

      System.out.println("Analyse Postfix : ");

      System.out.println(printPostFix(tmp));


    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
      System.exit(51);
    }
    System.out.println("Analyse syntaxique terminee");

  }
  return ErreurRetour;
}

  private static String printPostFix (ArrayList<Terminal> liste){
  String s= "";
  for (Terminal term : liste){
    s+=(term.toString()) +" ";
  }
  return s;
  }
}



