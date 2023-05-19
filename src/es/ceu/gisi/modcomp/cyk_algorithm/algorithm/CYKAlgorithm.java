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
    Set<Character> noTerminales = new HashSet<>();
    Set<Character> terminales = new HashSet<>();
    char axioma;
    HashMap<Character, Character> produccionesTerminales = new HashMap<>();
    HashMap<Character, Set<String>> produccionesNoTerminales = new HashMap<>();
    @Override
    /**
     * Método que añade los elementos no terminales de la gramática.
     *
     * @param nonterminal Por ejemplo, 'S'
     * @throws CYKAlgorithmException Si el elemento no es una letra mayúscula.
     */
    public void addNonTerminal(char nonterminal) throws CYKAlgorithmException {
        char prueba = Character.toLowerCase(nonterminal);
        if (noTerminales.contains(nonterminal)) {
            throw new CYKAlgorithmException("EL Símoblo no terminal ya esta presnete");
        }
        if (nonterminal != prueba && Character.isLetter(nonterminal)) {
            noTerminales.add(nonterminal);
        } else {
            throw new CYKAlgorithmException("EL símobolo no es No terminal");
        }
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

        if (terminales.contains(terminal)) {
            throw new CYKAlgorithmException("Elementos ya pertemece a la Gramatica");
        } else {
            if (terminal == prueba && Character.isLetter(terminal)) {
                terminales.add(terminal);
            } else {
                throw new CYKAlgorithmException("EL elemento no se adapta al perfil de terminal");
            }
        }
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
        if (axioma == nonterminal) {
            throw new CYKAlgorithmException("El elemento ya esta definido como Axioma");
        } else {
            if (noTerminales.contains(nonterminal)) {
                axioma = nonterminal;
            } else {
                throw new CYKAlgorithmException("El elemento ha de ser NO Terminal");
            }
        }
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
            if (noTerminales.contains(nonterminal) && terminales.contains(ter)) {
                produccionesTerminales.put(nonterminal, ter);
            } else {
                throw new CYKAlgorithmException("Error porducido por no haber insertado proevioamente o el terminal a añadir o el no terminal");
            }
        } else if (elementos == 2) {
            char no1 = production.charAt(0);
            char no2 = production.charAt(1);
            if (noTerminales.contains(nonterminal) && noTerminales.contains(no1) && noTerminales.contains(no2)) {
                if (produccionesNoTerminales.get(nonterminal) != null) {
                    Set<String> pro = new HashSet<>(produccionesNoTerminales.get(nonterminal));
                    if (pro.contains(production)) {
                        throw new CYKAlgorithmException("Error producido debido a que esa producción ya ha sido añadida");
                    } else {
                        pro.add(production);
                        produccionesNoTerminales.put(nonterminal, pro);
                    }
                } else {
                    Set<String> si = new HashSet<>();
                    si.add(production);
                    produccionesNoTerminales.put(nonterminal, si);
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
    try {
        return algoritmoDerived(word);
    } catch (CYKAlgorithmException ex) {
        throw new CYKAlgorithmException(ex.getMessage());
    }
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
        int tamanno = (word.length() - 1);
        StringBuilder result = new StringBuilder();
        String[][] tablero = new String[tamanno][tamanno];
        //comprobacion validez word
        if (noTerminales.isEmpty() || terminales.isEmpty() || axioma == '\0') {
            throw new CYKAlgorithmException("Gramática inválida");
        }
        for (int i = 0; i < word.length(); i++) {
            char terminal = word.charAt(i);
            if (!terminales.contains(terminal)) {
                throw new CYKAlgorithmException("La palabra contiene elementos no terminales inválidos");
            }
        }
        //Cabecera
        Set<Character> cabecera = new HashSet<>(produccionesTerminales.keySet());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tamanno; i++) {
            char prueba = word.charAt(i);
            for (Character cabe : cabecera) {
                if (produccionesTerminales.get(cabe).equals(prueba)) {
                    if (tablero[0][i] == null) {
                        tablero[0][i] = String.valueOf(cabe);
                    } else {
                        tablero[0][i] += String.valueOf(cabe);
                    }
                }
            }
        }
        //Resto de Filas 
        for (int i = 1; i < tamanno; i++) {
            for (int j = 0; j < tamanno - i; j++) {
                /*    String hola = "hola";
            tablero [i][j] = hola;*/
                int a = 0;
                int b = 1;
                while (a < i) {
                    if (tablero[i][j] == null) {
                        tablero[i][j] = this.Concat(tablero[a][j + a], tablero[a][j + b]);
                    } else {
                        tablero[i][j] += this.Concat(tablero[a][j + a], tablero[a][j + b]);
                    }
                    a++;
                }
            }
        }

        for (int i = 0; i < tamanno; i++) {
            for (int j = 0; j < tamanno - i; j++) {
                if (tablero[i][j] != null) {
                    result.append("[").append(tablero[i][j]).append("] ");
                } else {
                    result.append("[ ] ");
                }
            }
            result.append("\n");
        }
        return result.toString();
    }
   /**
    * Método que genera el producto cartesiano, es decir, todas las concatenaciones posibles, de todos los caracteres
    * de dos palabras y posteriormente las compara con los no Terminales que generar las producciones de las gramaticas para ver
    * si coinciden y si si coincide con alguno lo annade a esa celda.
    * 
    * @param uno primer conjunto de caracteres a concatenar
    * @param dos segundo conjutno de caracteress a concatenar
    * @return  d.toString(); es decir, un conjunto de caracteres, formado por los cabezales que generar como producción
    * alguna de las combinaxciones posibles entre los elementos de uno y dos.
    */
    public String Concat(String uno, String dos) {
        Set<Character> diver = new HashSet<>(produccionesNoTerminales.keySet());
        StringBuilder d = new StringBuilder();
        Set<Character> unoC = new HashSet<>();
        Set<Character> dosC = new HashSet<>();

        if (uno == null || dos == null) {
            return ""; // Retorna una cadena vacía si alguna de las cadenas es nula
        }
        for (int i = 0; i < uno.length(); i++) {
            char c = uno.charAt(i);
            unoC.add(c);
        }

        for (int i = 0; i < dos.length(); i++) {
            char c = dos.charAt(i);
            dosC.add(c);
        }
        for (Character c : unoC) {
            for (Character e : dosC) {
                for (Character dr : diver) {
                    Set<String> f = new HashSet<>(produccionesNoTerminales.get(dr));
                    String g = String.valueOf(c) + String.valueOf(e);
                    if (f.contains(g)) {
                        d.append(dr);
                    }
                }
            }
        }
        return d.toString();
    }

    @Override
    /**
     * Elimina todos los elementos que se han introducido hasta el momento en la
     * gramática (elementos terminales, no terminales, axioma y producciones),
     * dejando el algoritmo listo para volver a insertar una gramática nueva.
     */
    public void removeGrammar() {
        produccionesTerminales.clear();
        produccionesNoTerminales.clear();
        terminales.clear();
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
        if (produccionesNoTerminales.containsKey(nonterminal)) {
            Set<String> proNoTerminales = produccionesNoTerminales.get(nonterminal);
            StringBuilder sb = new StringBuilder();
            sb.append(nonterminal).append("::=");
            for (String produccion : proNoTerminales) {
                sb.append(produccion).append("|");
            }
            if (produccionesTerminales.containsKey(nonterminal)) {
                char ter = produccionesTerminales.get(nonterminal);
                sb.append(ter);
            }
            String produccionesSinterminal = sb.toString();
            if (!(produccionesTerminales.containsKey(nonterminal))) {
                produccionesSinterminal = produccionesSinterminal.substring(0, produccionesSinterminal.length() - 1);
            }
            return produccionesSinterminal;
        } else if (produccionesTerminales.containsKey(nonterminal)) {
            char ter = produccionesTerminales.get(nonterminal);
            StringBuilder b = new StringBuilder();
            b.append(nonterminal).append("::=").append(ter);
            return b.toString();
        }
        return "";

    }
    @Override
    /**
     * Devuelve un String con la gramática completa.
     *
     * @return Devuelve el agregado de hacer getProductions sobre todos los
     * elementos no terminales.
     */
    public String getGrammar() {
       StringBuilder sb = new StringBuilder();
       Set<Character>elementos = new HashSet<>(produccionesNoTerminales.keySet());
       Set<Character> elementosAnnadir = new HashSet<>(produccionesTerminales.keySet());
       for(Character annadir: elementosAnnadir){
           if(!(elementos.contains(annadir))){
               elementos.add(annadir);
           }
       }
       for(Character el: elementos){
           sb.append( getProductions(el)).append(";");
       }
       return sb.toString();
    }
    
    /**
     * Este método es una copia del método algorithmStateToString, tiene la misma lógica parámetros, lo que diverge es 
     * no solo el return que en este método se vuelve boolean; sino que además la forma propia del método que pasa de ser String
     * a ser boolean
     * @param word
     * @return True, si el axíoma esta presente en la celda de la priema columna última fila. En caso contrario devuelve un false
     * @throws CYKAlgorithmException 
     */
    public boolean algoritmoDerived(String word) throws CYKAlgorithmException {
        int tamanno = word.length();
        StringBuilder result = new StringBuilder();
        String[][] tablero = new String[tamanno][tamanno];
        StringBuilder bs = new StringBuilder();
        Set<String> combinaciones = new HashSet<>();
        Set<Character> key = new HashSet<>(produccionesNoTerminales.keySet());

        // Comprobación de validez de la Concat
        if (noTerminales.isEmpty() || terminales.isEmpty() || axioma == '\0') {
            throw new CYKAlgorithmException("Gramática inválida");
        }

        for (int i = 0; i < word.length(); i++) {
            char terminal = word.charAt(i);
            if (!terminales.contains(terminal)) {
                throw new CYKAlgorithmException("La palabra contiene elementos no terminales inválidos");
            }
        }

        // Cabecera
        Set<Character> cabecera = new HashSet<>(produccionesTerminales.keySet());

        for (int i = 0; i < tamanno; i++) {
            char prueba = word.charAt(i);
            for (Character cabe : cabecera) {
                if (produccionesTerminales.get(cabe).equals(prueba)) {
                    if (tablero[0][i] == null) {
                        tablero[0][i] = String.valueOf(cabe);
                    } else {
                        tablero[0][i] += String.valueOf(cabe);
                    }
                }
            }
        }

        // Resto de celdas
        for (int i = 1; i < tamanno; i++) {
            for (int j = 0; j < tamanno - i; j++) {
                /*    String hola = "hola";
            tablero [i][j] = hola;*/
                int a = 0;
                int b = 1;
                while (a < i) {
                    if (tablero[i][j] == null) {
                        tablero[i][j] = this.Concat(tablero[a][j + a], tablero[a][j + b]);
                    } else {
                        tablero[i][j] += this.Concat(tablero[a][j + a], tablero[a][j + b]);
                    }
                    a++;
                }
            }
        }
        // Construir el String con todas las celdas calculadas
        Set<Character> ax = new HashSet<>();
        int a = tamanno - 1;
        int b = 0;
        boolean verdadero = false;
        String h = tablero[a][b];
        for (int i = 0; i < h.length(); i++) {

            ax.add(h.charAt(i));
        }
        if (ax.contains(axioma)) {
            verdadero = true;
        }

        return verdadero;
    }

}
