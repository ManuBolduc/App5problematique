package APP5;


/** @author Ahmed Khoumsi */

import java.io.*;
import java.util.ArrayList;

/** Classe representant une feuille d'AST
 */
public class NoeudAST extends ElemAST {

  private static final char[] possibiliteCharMinuscule = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
  private static final char[] possibiliteCharMajususcule = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

  private static final char[] possibiliteChiffre = {'1','2','3','4','5','6','7','8','9','0'};

  private static final char[] possibiliteOperateur = {'+','-','*','/'};

  //Terminal operation;
  public ElemAST KidLeft;
  public ElemAST KidRight;
  /** Constructeur pour l'initialisation d'attributs
   */
  public NoeudAST(String valeur,ElemAST gauche,ElemAST droite) { // avec arguments

    switch (valeur.charAt(0)) {
      case '+':
        super.valeur = new Terminal(valeur,typeTerminal.ADD);
        break;
      case '-':
        super.valeur = new Terminal(valeur,typeTerminal.SUB);
        break;
      case '*':
        super.valeur = new Terminal(valeur,typeTerminal.MULT);
        break;
      case '/':
        super.valeur = new Terminal(valeur,typeTerminal.DIV);
        break;
      default:
      System.out.println("Should not be printed, constructeur noeud AST");
    }

    if (gauche != null)
        this.KidLeft = gauche;
    if (droite != null)
      this.KidRight= droite;

    //
  }

  /** Evaluation de noeud d'AST
   */
  public int EvalAST( ) {


    if(super.valeur.chaine.equals("+")){
      return (KidLeft.EvalAST() + KidRight.EvalAST());
      }
     else if (super.valeur.chaine.equals("-")){
       return (KidLeft.EvalAST() - KidRight.EvalAST());
      }
     else if (super.valeur.chaine.equals("*")){
       return (KidLeft.EvalAST() * KidRight.EvalAST());
     }
     else if (super.valeur.chaine.equals("/")){
       return (KidLeft.EvalAST() / KidRight.EvalAST());
     }

     else {
       ErreurEvalAST("Noeud non operateur");
       return 0;
     }

  }

  public String EvalASTPostFix(ArrayList<Terminal> terminaux){
    try{
    for (int index =0;terminaux.size()>1;index++)
    {
      int tmp=0;
      if (terminaux.get(index).type== typeTerminal.ADD || terminaux.get(index).type== typeTerminal.SUB || terminaux.get(index).type== typeTerminal.MULT|| terminaux.get(index).type== typeTerminal.DIV){

        switch (terminaux.get(index).type){
          case ADD:
            tmp = Integer.parseInt(terminaux.get(index-2).chaine) + Integer.parseInt(terminaux.get(index-1).chaine);
            break;
          case SUB:
            tmp = Integer.parseInt(terminaux.get(index-2).chaine) - Integer.parseInt(terminaux.get(index-1).chaine);
            break;
          case MULT:
            tmp = Integer.parseInt(terminaux.get(index-2).chaine) * Integer.parseInt(terminaux.get(index-1).chaine);
            break;
          case DIV:
            tmp = Integer.parseInt(terminaux.get(index-2).chaine) / Integer.parseInt(terminaux.get(index-1).chaine);
            break;
          default:
            System.out.println("Devrait pas etre print, evaluation postfix de l'arbre");
        }

        terminaux.remove(index);
        terminaux.remove(index-1);
        terminaux.remove(index-2);
        terminaux.add(index-2,new Terminal(String.valueOf(tmp),typeTerminal.CHIFFRE));
        index-=2;

      }
      else{}
    }
    }
    catch (Exception e){
      System.out.println(" Une variable est presente dans larbre donc on ne peut pas levaluer");
    }
    return (terminaux.get(0).chaine);
  }

  public ArrayList<Terminal> StartPostFix(){
    return PostFix();
  }

  public ArrayList<Terminal> PostFix(){
    ArrayList<Terminal> tmp = new ArrayList<>();
    tmp.addAll(KidLeft.PostFix()) ;
    tmp.addAll(KidRight.PostFix());
    tmp.add(new Terminal(super.valeur.chaine,super.valeur.type));
    return tmp;
  }

  /** Lecture de noeud d'AST
   */
  public String LectAST( ) {
    try {
      OutputStream file = new FileOutputStream("tree.txt");
      OutputStreamWriter writer= new OutputStreamWriter(file);
      this.printTree(writer);
      writer.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }//
    return "(" + KidLeft.LectAST() + super.valeur.chaine + KidRight.LectAST() + ")";
  }

  public void printTree(OutputStreamWriter out) throws IOException {
    if (KidRight != null) {
      KidRight.printTree(out, true, "");
    }
    printNodeValue(out);
    if (KidLeft != null) {
      KidLeft.printTree(out, false, "");
    }
  }

  public void printTree(OutputStreamWriter out, boolean isRight, String indent) throws IOException {
    if (KidRight != null) {
      KidRight.printTree(out, true, indent + (isRight ? "        " : " |      "));
    }
    out.write(indent);
    if (isRight) {
      out.write(" /");
    } else {
      out.write(" \\");
    }
    out.write("----- ");
    printNodeValue(out);
    if (KidLeft != null) {
      KidLeft.printTree(out, false, indent + (isRight ? " |      " : "        "));
    }
  }

  public void printNodeValue(OutputStreamWriter out) throws IOException {
    if (super.valeur == null) {
      out.write("<null>");
    } else {
      out.write(super.valeur.toString());
    }
    out.write('\n');
  }

  public boolean contains(final char[] array, char key)
  {
    for (char i : array)
    {
      if (i==key)
        return true;
    }
    return false;
  }
}


