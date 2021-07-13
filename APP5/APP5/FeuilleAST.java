package APP5;

/** @author Ahmed Khoumsi */

import java.io.IOException;
import java.io.OutputStreamWriter;

/** Classe representant une feuille d'AST
 */
public class FeuilleAST extends ElemAST {

  // Attribut(s)


/**Constructeur pour l'initialisation d'attribut(s)
 */
  public FeuilleAST(String Valeur) {  // avec arguments
      super.valeur = new Terminal(Valeur);
  }


  /** Evaluation de feuille d'AST
   */
  public int EvalAST( ) {
    return 0;//
  }


 /** Lecture de chaine de caracteres correspondant a la feuille d'AST
  */
  public String LectAST( ) {
      return super.valeur.chaine;//
  }


  public void printNodeValue(OutputStreamWriter out) throws IOException {
        if (super.valeur == null) {
            out.write("<null>");
        } else {
            out.write(super.valeur.toString());
        }
        out.write('\n');
  }

  public void printTree(OutputStreamWriter out, boolean isRight, String indent) throws IOException {
      out.write(indent);
      if (isRight) {
          out.write(" /");
      } else {
          out.write(" \\");
      }
      out.write("----- ");
      printNodeValue(out);

  }
}
