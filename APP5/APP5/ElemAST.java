package APP5;

/** @author Ahmed Khoumsi */

import java.io.IOException;
import java.io.OutputStreamWriter;

/** Classe Abstraite dont heriteront les classes FeuilleAST et NoeudAST
 */
public abstract class ElemAST {

  protected Terminal valeur;

  /** Evaluation d'AST
   */
  public abstract int EvalAST();


  /** Lecture d'AST
   */
  public abstract String LectAST();


/** ErreurEvalAST() envoie un message d'erreur lors de la construction d'AST
 */  
  public void ErreurEvalAST(String s) {	
    // 
  }

  public void printTree(OutputStreamWriter out, boolean isRight, String indent) throws IOException{};

  public void printNodeValue(OutputStreamWriter out) throws IOException{};

  public void printTree(OutputStreamWriter out) throws IOException{};

  @Override
  public String toString() {
    return  valeur.toString();
  }
}
