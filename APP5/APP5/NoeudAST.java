package APP5;

/** @author Ahmed Khoumsi */

import java.io.*;

/** Classe representant une feuille d'AST
 */
public class NoeudAST extends ElemAST {

  //Terminal operation;
  ElemAST KidLeft;
  ElemAST KidRight;
  /** Constructeur pour l'initialisation d'attributs
   */
  public NoeudAST(String valeur,ElemAST gauche,ElemAST droite) { // avec arguments
    super.valeur = new Terminal(valeur);
    if (gauche != null)
        this.KidLeft = gauche;
    if (droite != null)
      this.KidRight= droite;

    //
  }
/*
  public NoeudAST(String s, ElemAST n1, ElemAST n2) {
    operation = new Terminal(s);
    KidLeft = n1;
    KidRight = n2;
  }


  public NoeudAST(String valeur){
    super.valeur = new Terminal(valeur);
  }
*/
 
  /** Evaluation de noeud d'AST
   */
  public int EvalAST( ) {
     if(super.valeur.chaine.equals("+")){
      return (KidLeft.EvalAST() + KidRight.EvalAST());
    }
     else {
       ErreurEvalAST("Noeud non +");
       return 0;
     }
  }


  /** Lecture de noeud d'AST
   */
  public String LectAST( ) {
    try {
      OutputStream file = new FileOutputStream(new File("tree.txt"));
      OutputStreamWriter writer= new OutputStreamWriter(file);
      this.printTree(writer);
      writer.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }//
    return "Le tree a ete creer dans tree.txt";
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

}


