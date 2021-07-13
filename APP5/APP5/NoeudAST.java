package APP5;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class NoeudAST extends ElemAST {

  Terminal operation;
  ElemAST KidLeft;
  ElemAST KidRight;
  /** Constructeur pour l'initialisation d'attributs
   */
  public NoeudAST( ) { // avec arguments
    //
  }

  public NoeudAST(String s, ElemAST n1, ElemAST n2) {
    operation = new Terminal(s);
    KidLeft = n1;
    KidRight = n2;
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

}


