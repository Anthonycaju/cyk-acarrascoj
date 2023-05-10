package es.ceu.gisi.modcomp.cyk_algorithm.algorithm;

import es.ceu.gisi.modcomp.cyk_algorithm.algorithm.exceptions.CYKAlgorithmException;
import es.ceu.gisi.modcomp.cyk_algorithm.algorithm.interfaces.CYKAlgorithmInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Esta clase contiene la implementación de la interfaz CYKAlgorithmInterface
 * que establece los métodos necesarios para el correcto funcionamiento del
 * proyecto de programación de la asignatura Modelos de Computación.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class CYKAlgorithm implements CYKAlgorithmInterface {
Set<Character> noTerminales = new HashSet<Character>();
  Set<Character> Terminales = new HashSet<Character>();
  char axioma ;
  Set<String> annadir = new HashSet<>();
 HashMap<Character, Character> ProduccionesTerminales = new HashMap<>();
  HashMap<Character, Set<String>> ProduccionesNoTerminales = new HashMap<>();
    @Override
    /**
     * Método que añade los elementos no terminales de la gramática.
     *
     * @param nonterminal Por ejemplo, 'S'
     * @throws CYKAlgorithmException Si el elemento no es una letra mayúscula.
     */
    public void addNonTerminal(char nonterminal) throws CYKAlgorithmException {
        char prueba = Character.toLowerCase(nonterminal);
        if(noTerminales.contains(nonterminal)){
            throw new CYKAlgorithmException("EL Símoblo no terminal ya esta presnete");
        }
        if(nonterminal != prueba && Character.isLetter(nonterminal)){
           noTerminales.add(nonterminal);
        }else {
            throw new CYKAlgorithmException("EL símobolo no es No terminal");
        }
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    /**
     * Método que añade los elementos terminales de la gramática.
     *
     * @param terminal Por ejemplo, 'a'
     * @throws CYKAlgorithmException Si el elemento no es una letra minúscula.
     */
    public void addTerminal(char terminal) throws CYKAlgorithmException {
        char prueba = Character.toLowerCase(terminal);
       
        if (Terminales.contains(terminal)){
           throw new CYKAlgorithmException("Elementos ya pertemece a la Gramatica");
        } else{
            if(terminal == prueba && Character.isLetter(terminal)){
                Terminales.add(terminal);
            }else{
                throw new CYKAlgorithmException("EL elemento no se adapta al perfil de terminal");
            }
        }
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    /**
     * Método que indica, de los elementos no terminales, cuál es el axioma de
     * la gramática.
     *
     * @param nonterminal Por ejemplo, 'S'
     * @throws CYKAlgorithmException Si el elemento insertado no forma parte del
     * conjunto de elementos no terminales.
     */
    public void setStartSymbol(char nonterminal) throws CYKAlgorithmException {
      if(axioma == nonterminal){
          throw new CYKAlgorithmException("El elemento ya esta definido como Axioma");
      } else{
          if(noTerminales.contains(nonterminal)){
              axioma = nonterminal;
          } else{
              throw new CYKAlgorithmException ("El elemento ha de ser NO Terminal");
          }
      }
        /*if (noTerminales.contains(nonterminal)){
            
        }else{
            char prueba = Character.toLowerCase(nonterminal);
        if(nonterminal != prueba && Character.isLetter(nonterminal)){
           noTerminales.add(nonterminal);
        }else {
            throw new CYKAlgorithmException("EL símobolo no es No terminal");
        }
        }*/
       // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    /**
     * Método utilizado para construir la gramática. Admite producciones en FNC,
     * es decir de tipo A::=BC o A::=a
     *
     * @param nonterminal A
     * @param production "BC" o "a"
     * @throws CYKAlgorithmException Si la producción no se ajusta a FNC o está
     * compuesta por elementos (terminales o no terminales) no definidos
     * previamente.
     */
    public void addProduction(char nonterminal, String production) throws CYKAlgorithmException {
        int elementos = production.length();
        if (elementos == 1) {
            char ter = production.charAt(0);
            if (noTerminales.contains(nonterminal) && Terminales.contains(ter)) {
                ProduccionesTerminales.put(nonterminal, ter);
            } else {
                throw new CYKAlgorithmException("Mal  terminal");
            }
        } else if (elementos == 2) {
            char no1 = production.charAt(0);
            char no2 = production.charAt(1);
            if (noTerminales.contains(nonterminal) && noTerminales.contains(no1) && noTerminales.contains(no2)) {
                if (ProduccionesNoTerminales.get(nonterminal) != null) {
                    Set<String> pro = new HashSet<>(ProduccionesNoTerminales.get(nonterminal));
                    if (pro.contains(production)) {
                        throw new CYKAlgorithmException("Mal noterminal rfepetido");
                    } else {
                        pro.add(production);
                        ProduccionesNoTerminales.put(nonterminal, pro);
                    }
                } else {
                    Set<String> si = new HashSet<>();
                    si.add(production);
                    ProduccionesNoTerminales.put(nonterminal, si);
                }
            } else {
                throw new CYKAlgorithmException("Mal noterminal");
            }
        } else {
            throw new CYKAlgorithmException("Mal");
        }
    }

    @Override
    /**
     * Método que indica si una palabra pertenece al lenguaje generado por la
     * gramática que se ha introducido.
     *
     * @param word La palabra a verificar, tiene que estar formada sólo por
     * elementos no terminales.
     * @return TRUE si la palabra pertenece, FALSE en caso contrario
     * @throws CYKAlgorithmException Si la palabra proporcionada no está formada
     * sólo por terminales, si está formada por terminales que no pertenecen al
     * conjunto de terminales definido para la gramática introducida, si la
     * gramática es vacía o si el autómata carece de axioma.
     */
    public boolean isDerived(String word) throws CYKAlgorithmException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    /**
     * Método que, para una palabra, devuelve un String que contiene todas las
     * celdas calculadas por el algoritmo (la visualización debe ser similar al
     * ejemplo proporcionado en el PDF que contiene el paso a paso del
     * algoritmo).
¡     *
     * @param word La palabra a verificar, tiene que estar formada sólo por
     * elementos no terminales.
     * @return Un String donde se vea la tabla calculada de manera completa,
     * todas las celdas que ha calculado el algoritmo.
     * @throws CYKAlgorithmException Si la palabra proporcionada no está formada
     * sólo por terminales, si está formada por terminales que no pertenecen al
     * conjunto de terminales definido para la gramática introducida, si la
     * gramática es vacía o si el autómata carece de axioma.
     */
    public String algorithmStateToString(String word) throws CYKAlgorithmException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    /**
     * Elimina todos los elementos que se han introducido hasta el momento en la
     * gramática (elementos terminales, no terminales, axioma y producciones),
     * dejando el algoritmo listo para volver a insertar una gramática nueva.
     */
    public void removeGrammar() {
        ProduccionesTerminales.clear();
        ProduccionesNoTerminales.clear();
        Terminales.clear();
        noTerminales.clear();
        axioma = '\0';
    }

    @Override
    /**
     * Devuelve un String que representa todas las producciones que han sido
     * agregadas a un elemento no terminal.
     *
     * @param nonterminal
     * @return Devuelve un String donde se indica, el elemento no terminal, el
     * símbolo de producción "::=" y las producciones agregadas separadas, única
     * y exclusivamente por una barra '|' (no incluya ningún espacio). Por
     * ejemplo, si se piden las producciones del elemento 'S', el String de
     * salida podría ser: "S::=AB|BC".
     */
    public String getProductions(char nonterminal) {
    /*  List lista = new ArrayList();
      Set<String> noTer = ProduccionesNoTerminales.get(nonterminal);
      StringBuilder sb = new StringBuilder();
      sb.append(nonterminal + "::=");
      for(String no: noTer){
          sb.append(no + "|");
      }
      return sb.toString();
        StringBuilder sb = new StringBuilder();
        if(noTerminales.contains(nonterminal)){
            Set<Character> getNoTer = ProduccionesNoTerminales.get(nonterminal);
            sb.append(nonterminal + "::=");
    for (Character noTer : getNoTer) {
        sb.append(noTer + "|");
    }
    return sb.toString();
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }*/
    List<String> producciones = new ArrayList<>();
    if (ProduccionesNoTerminales.containsKey(nonterminal)) {
        Set<String> produccionesNoTerminales = ProduccionesNoTerminales.get(nonterminal);
        StringBuilder sb = new StringBuilder();
        sb.append(nonterminal).append("::=");
        for (String produccion : produccionesNoTerminales) {
            sb.append(produccion).append("|");
        }
        String produccionesStr = sb.toString();
        produccionesStr = produccionesStr.substring(0, produccionesStr.length() - 1); // Elimina la última barra
        producciones.add(produccionesStr);
        return produccionesStr;
    } else 
     throw new UnsupportedOperationException("Not supported yet.");
    
}




    @Override
    /**
     * Devuelve un String con la gramática completa.
     *
     * @return Devuelve el agregado de hacer getProductions sobre todos los
     * elementos no terminales.
     */
    public String getGrammar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}