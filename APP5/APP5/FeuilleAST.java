package APP5;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class FeuilleAST extends ElemAST {

  Terminal valeur;


/**Constructeur pour l'initialisation d'attribut(s)
 * @param terminal
 */
  public FeuilleAST(Terminal terminal) {  // avec arguments
    valeur = terminal;
  }


  /** Evaluation de feuille d'AST
   */
  public int EvalAST( ) {
        return (Integer.parseInt(valeur.chaine));
  }


 /** Lecture de chaine de caracteres correspondant a la feuille d'AST
  */
  public String LectAST( ) {
    return null;//
  }

}
