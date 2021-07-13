package APP5;

/** @author Ahmed Khoumsi */

/** Cette classe identifie les terminaux reconnus et retournes par
 *  l'analyseur lexical
 */
public class Terminal {
  public String chaine;











/** Un ou deux constructeurs (ou plus, si vous voulez)
  *   pour l'initalisation d'attributs
 * @param o_chaine
 */	
  public Terminal(String o_chaine) {   // arguments possibles
     chaine = o_chaine;
  }

    @Override
    public String toString() {
        return chaine ;
    }
}


