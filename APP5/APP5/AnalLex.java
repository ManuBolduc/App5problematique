package APP5;

/** @author Ahmed Khoumsi */

/** Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

public boolean state;
public int readIndex;
public String i_text;
public String o_chaine;
public boolean finFichier;

/** Constructeur pour l'initialisation d'attribut(s)
 * @param s
 */
  public AnalLex(String s) {  // arguments possibles
    this.i_text = s;
    this.state = false;
    this.readIndex = 0;
    this.finFichier = false;
  }


/** resteTerminal() retourne :
      false  si tous les terminaux de l'expression arithmetique ont ete retournes
      true s'il reste encore au moins un terminal qui n'a pas ete retourne 
 */
  public boolean resteTerminal( ) {
    return !(readIndex == i_text.length()-1);
  }
  
  
/** prochainTerminal() retourne le prochain terminal
      Cette methode est une implementation d'un AEF
 */  
  public Terminal prochainTerminal( ) {
     System.out.println("entreProchainterminal");
     char c;
     this.o_chaine = "";
     while (!finFichier){
       System.out.println("loop");
       if (readIndex == i_text.length()){
         finFichier = true;
         c = '%';
       }
       else{
         c = i_text.charAt(this.readIndex);
       }

       readIndex++;
       if (!state) {
         if (c == '+') {
           System.out.println("we see + state 0");
           o_chaine += c;
           return new Terminal(o_chaine);
         } else if (c == '0' || c == '1') {
            System.out.println("we see number state 0");
            o_chaine += c;
            state = true;
         }
         else {
           finFichier = true;
           System.out.println("we see random state 0");
           ErreurLex("1315");
           return new Terminal("fin de fichier");
         }
       }
       else {
         if (c == '0' || c == '1'){
           System.out.println("we see number state 1");
           o_chaine += c;
         }
         else{
           System.out.println("symbol state 1");
           readIndex--;
           state = false;
           return new Terminal(o_chaine);
         }
       }
     }
     return new Terminal("tchoin");
  }

 
/** ErreurLex() envoie un message d'erreur lexicale
 */ 
  public void ErreurLex(String s) {
    System.out.println(s);
  }

  
  //Methode principale a lancer pour tester l'analyseur lexical
  public static void main(String[] args) {
    String toWrite = "";
    System.out.println("Debut d'analyse lexicale");
    if (args.length == 0){
    args = new String [2];
            args[0] = "APP5\\input.txt";
            args[1] = "output.txt";
    }
    Reader r = new Reader(args[0]);

    AnalLex lexical = new AnalLex(r.toString()); // Creation de l'analyseur lexical

    // Execution de l'analyseur lexical
    Terminal t = null;
    while(!lexical.finFichier){
      t = lexical.prochainTerminal();
      toWrite +=t.chaine + "\n" ;  // toWrite contient le resultat
    }				   //    d'analyse lexicale
    System.out.println(toWrite); 	// Ecriture de toWrite sur la console
    Writer w = new Writer(args[1],toWrite); // Ecriture de toWrite dans fichier args[1]
    System.out.println("Fin d'analyse lexicale");
  }
}
