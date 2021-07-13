package APP5;

/** @author Ahmed Khoumsi */

import java.io.*;

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


