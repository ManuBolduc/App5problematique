package APP5;

/** @author Ahmed Khoumsi */

import java.io.IOException;
import java.io.OutputStreamWriter;

/** Classe representant une feuille d'AST
 */
public class NoeudAST extends ElemAST {

  // Attributs
  private ElemAST parent;
  private ElemAST gauche;
  private ElemAST droite;

  /** Constructeur pour l'initialisation d'attributs
   */
  public NoeudAST(String valeur,ElemAST gauche,ElemAST droite) { // avec arguments
    super.valeur = new Terminal(valeur);
    if (gauche != null)
        this.gauche= gauche;
    if (droite != null)
      this.droite= droite;

    //
  }

  public NoeudAST(String valeur){
    super.valeur = new Terminal(valeur);
  }

 
  /** Evaluation de noeud d'AST
   */
  public int EvalAST( ) {
     return 0;//
  }


  /** Lecture de noeud d'AST
   */
  public String LectAST( ) {
     return null;//
  }

  public void printTree(OutputStreamWriter out) throws IOException {
    if (droite != null) {
      droite.printTree(out, true, "");
    }
    printNodeValue(out);
    if (gauche != null) {
      gauche.printTree(out, false, "");
    }
  }

  public void printTree(OutputStreamWriter out, boolean isRight, String indent) throws IOException {
    if (droite != null) {
      droite.printTree(out, true, indent + (isRight ? "        " : " |      "));
    }
    out.write(indent);
    if (isRight) {
      out.write(" /");
    } else {
      out.write(" \\");
    }
    out.write("----- ");
    printNodeValue(out);
    if (gauche != null) {
      gauche.printTree(out, false, indent + (isRight ? " |      " : "        "));
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


