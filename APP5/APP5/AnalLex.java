package APP5;



/** @author Ahmed Khoumsi */

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;



/** Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

  //public boolean state;
  public LexicalState state;
  public int readIndex;
  public String i_text;
  public String o_chaine;
  public boolean finFichier;
  public ArrayList<Terminal> liste_terminaux;
  public int countParantheseOuvrante;
  public int countParantheseFermante;


  private static final char[] possibiliteCharMinuscule = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
  private static final char[] possibiliteCharMajususcule = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

  private static final char[] possibiliteChiffre = {'1','2','3','4','5','6','7','8','9','0'};

  private static final char[] possibiliteOperateur = {'+','-','*','/'};


  /** Constructeur pour l'initialisation d'un analyseur sans input text(s)
   *
   */
  public AnalLex(){
    this.state = LexicalState.initial;
    this.readIndex = 0;
    this.finFichier = false;
    this.liste_terminaux = new ArrayList<Terminal>();
    this.countParantheseOuvrante =0;
    this.countParantheseFermante=0;
  };

  /** Constructeur pour l'initialisation d'attribut(s)
 * @param s
 */
  public AnalLex(String s) {  // arguments possibles

    this.i_text = s;
    this.state = LexicalState.initial;
    this.readIndex = 0;
    this.finFichier = false;
    this.liste_terminaux = new ArrayList<Terminal>();
    this.countParantheseOuvrante =0;
    this.countParantheseFermante=0;
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
     //System.out.println("entreProchainterminal");
     char c;
     this.o_chaine = "";
     while (!finFichier){
       //System.out.println("loop");
       if (readIndex == i_text.length()){
         finFichier = true;
         c = '%';
         System.out.println("we reach max length");
       }

       else // read du caractère
       {
         c = i_text.charAt(this.readIndex);
       }

       readIndex++;

       if (state == LexicalState.initial) {
         if(c=='+'||c=='-'||c=='*'||c=='/') {
           o_chaine += c;
           switch (c){
             case '-':
               return new Terminal(o_chaine,typeTerminal.SUB);
             case '*':
               return new Terminal(o_chaine,typeTerminal.MULT);
             case '/':
               return new Terminal(o_chaine,typeTerminal.DIV);
             default:
               return new Terminal(o_chaine,typeTerminal.ADD); } } // Lors d'un operateur + - / * , on retourne seulement un Terminal et on continu

         else if (c=='(' || c==')'){
           o_chaine += c;
           if (c=='('){
             countParantheseOuvrante++;
             return new Terminal(o_chaine,typeTerminal.PARANTHESEOUVRANTE);}
           else {
             countParantheseFermante++;
             return new Terminal(o_chaine, typeTerminal.PARANTHESEFERMANTE);}

           } // ( ou )

         else if (contains(possibiliteChiffre,c) ) {
           System.out.println("we see a number in the initial state ");
           o_chaine += c;
           state = LexicalState.chiffre;
           System.out.println("changing state to chiffre ");
         } // contient un chiffre entre 0 et 9, on entre dans letat de detection des chiffres

         else if (contains(possibiliteCharMajususcule,c)){
           System.out.println("we see a Majuscule in the initial state ");
           o_chaine += c;
           state = LexicalState.variable;
           System.out.println("changing state to variable ");
         }//contient une lettre minuscule ou majuscule , on entre dans letat de detection des variables

         else if (contains(possibiliteCharMinuscule,c)){
           ErreurLex("1315");
           return new Terminal("Lettre minuscule detecte dans letat intiial",typeTerminal.ERREUR);
         } // lettre minuscule detecte sans Majuscule

         else if (c==' '){

         }
         else {
           finFichier = true;
           System.out.println("we see something random in the initial state ");
           ErreurLex("1315");
           return new Terminal("fin de fichier",typeTerminal.ERREUR);}// symbole non reconnu dans letat initial : fin du fichier
          }

       else if(state == LexicalState.chiffre) {
         if (contains(possibiliteChiffre,c)){
           System.out.println("we see number state chiffre");
           o_chaine += c;
         }
         else{
           System.out.println("symbol in state chiffre");
           readIndex--;
           state = LexicalState.initial;
           return new Terminal(o_chaine,typeTerminal.CHIFFRE);
         }
       }

       else if(state == LexicalState.variable){
         if(contains(possibiliteCharMinuscule,c)|| contains(possibiliteCharMajususcule,c)){
           System.out.println("we see lettre minuscule ou majuscule dans le  state variable");
           o_chaine += c;
         }
         else if (contains(possibiliteOperateur,c)){
           System.out.println("operateur in state variable, switch pour etat initial");
           readIndex--;
           state = LexicalState.initial;
           return new Terminal(o_chaine,typeTerminal.VARIABLE);
         }

         else if (c == '_'){
           System.out.println("we see _ dans le  state variable, changement pour letat underscore");
           o_chaine += c;
           state = LexicalState.underscore;
         }
         else if(c == '(' || c== ')'){
           readIndex--;
           state = LexicalState.initial;
           return new Terminal(o_chaine,typeTerminal.VARIABLE);
         }

         else{
           finFichier = true;
           o_chaine += c;
           ErreurLex("1315");
           return new Terminal("Erreur au caractere :" + readIndex + "\n Caractere non recconu dans letat variable : " + o_chaine,typeTerminal.ERREUR);
         }

       }

       else if (state == LexicalState.underscore){
         if (contains(possibiliteCharMinuscule,c) || contains(possibiliteCharMajususcule,c)){
           System.out.println("we see char dans le  state underscore, changement pour letat variable");
           o_chaine += c;
           state = LexicalState.variable;
         }
         else if (c=='_'){
           o_chaine += c;
           ErreurLex("1315");
           return new Terminal("Erreur au caractere :" + readIndex + "\n 2 underscore de suite: " + o_chaine,typeTerminal.ERREUR);
         }
         else if (contains(possibiliteOperateur,c)){
           o_chaine += c;
           ErreurLex("1315");
           return new Terminal("Erreur au caractere :" + readIndex + "\n la variable se termine par un underscore: " + o_chaine,typeTerminal.ERREUR);
         }


         else {
           ErreurLex("1315");
           return new Terminal("Erreur au caractere :" + readIndex + "\n caractere non supporte: " + o_chaine,typeTerminal.ERREUR);
         }


       }

       else {
         readIndex--;
         state = LexicalState.initial;
         ErreurLex("1315");
         return new Terminal("Erreur etat non connu",typeTerminal.ERREUR);} // si pour x raison on est dans un etat inconnu, on envoie une erreur et on retourne a letat initial
     }
     return new Terminal("CECI NE DEVRAIT PAS PRINT PUISQUON EST SUPPOSER RETURN QQCHOSE(ERREUR OU AUTRE) ",typeTerminal.ERREUR);
  }



/** ErreurLex() envoie un message d'erreur lexicale
 */ 
  public void ErreurLex(String s) {
    System.out.println(s);
  }


  public AnalLex runOnTextFile(String inputpath,@Nullable String outputpath)
  {
    String toWrite = "";
    System.out.println("Debut d'analyse lexicale");
    Reader r = new Reader(inputpath);
    AnalLex lexical = new AnalLex(r.toString()); // Creation de l'analyseur lexical

    Terminal t = null;
    while(!lexical.finFichier){
      t = lexical.prochainTerminal();
      lexical.liste_terminaux.add(t); // ajout des UL en ordre dans un tableau de terminaux
      toWrite +=t.chaine + "\n" ;
    }

    if (outputpath != null){
      Writer w = new Writer(outputpath,toWrite); // Ecriture de toWrite dans fichier specifier en argument
    }

    System.out.println("Fin d'analyse lexicale");
    return lexical;

  }

  private boolean contains(final char[] array, char key)
  {
    for (char i : array)
    {
      if (i==key)
        return true;
    }
    return false;
  }

  //Methode principale a lancer pour tester l'analyseur lexical
  public static void main(String[] args) {
    AnalLex lexical = new AnalLex().runOnTextFile("APP5/input.txt","output.txt");

    System.out.println("\n\nListe de Terminaux");
    for(Terminal terminal : lexical.liste_terminaux)
      System.out.print(" "+ terminal.toString());


  }
}
