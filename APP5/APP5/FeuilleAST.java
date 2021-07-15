package APP5;

/** @author Ahmed Khoumsi */

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/** Classe representant une feuille d'AST
 */
public class FeuilleAST extends ElemAST {

  //Terminal valeur;


/**Constructeur pour l'initialisation d'attribut(s)
 * @param terminal
 */
  public FeuilleAST(Terminal terminal) {  // avec arguments
      super.valeur = terminal;
  }
  public FeuilleAST(String Valeur) {  // avec arguments
      super.valeur = new Terminal(Valeur);
  }


  /** Evaluation de feuille d'AST
   */
  public int EvalAST( ) {

      if (super.valeur.type!=typeTerminal.CHIFFRE ){
          ErreurEvalAST("Impossible devaluer une chaine de charactere");
          return 0;}
      else
          return (Integer.parseInt(super.valeur.chaine));
  }


 /** Lecture de chaine de caracteres correspondant a la feuille d'AST
  */
  public String LectAST( ) {
      return super.valeur.chaine;//
  }

  public ArrayList<Terminal> PostFix(){
      ArrayList<Terminal> tmp = new ArrayList<>();
      tmp.add(super.valeur);
      return tmp;
  }

  public ArrayList<Terminal>  StartPostFix(){return null;}


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
