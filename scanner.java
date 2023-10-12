import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private static final Map<String, TipoToken> palabrasReservadas;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and",    TipoToken.AND);
        palabrasReservadas.put("else",   TipoToken.ELSE);
        palabrasReservadas.put("false",  TipoToken.FALSE);
        palabrasReservadas.put("for",    TipoToken.FOR);
        palabrasReservadas.put("fun",    TipoToken.FUN);
        palabrasReservadas.put("if",     TipoToken.IF);
        palabrasReservadas.put("null",   TipoToken.NULL);
        palabrasReservadas.put("or",     TipoToken.OR);
        palabrasReservadas.put("print",  TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("true",   TipoToken.TRUE);
        palabrasReservadas.put("var",    TipoToken.VAR);
        palabrasReservadas.put("while",  TipoToken.WHILE);
    }

    private final String source;

    private final List<Token> tokens = new ArrayList<>();
    
    public Scanner(String source){
        this.source = source + " ";
    }

    public List<Token> scan() throws Exception {
        int estado = 0;
        String lexema = "";
        char c;

        for(int i=0; i<source.length(); i++){
            c = source.charAt(i);

          
            switch (estado){
                case 0:
                    if(Character.isLetter(c)){
                        estado = 9;
                        lexema += c;
                    }
                    else if(Character.isDigit(c)){
                        estado = 11;
                        lexema += c;
                    }
                    else if( c =='>'){
                        estado = 1;
                        lexema += c;
                    }
                    else if(c == '<'){
                        estado = 2;
                        lexema += c;
                    }
                    else if(c == '0'){
                        estado = 7;
                        lexema += c;
                    }
                    else if(c == '!'){
                        estado = 10;
                        lexema += c;
                    }
                    else if(c =='"'){
                        estado = 16;
                        lexema += c;
                    }
                    // Verifica si el carácter es una barra diagonal (slash)
                    else if(c == '/'){
                      estado = 17;           // Establece el estado a 17
                      lexema += c;           // Añade el carácter al lexema
                   }
                   // Verifica si el carácter es un signo más
                   else if(c == '+'){
                     estado = 21;           // Establece el estado a 21
                     lexema += c;           // Añade el carácter al lexema
                   }
                   // Verifica si el carácter es un signo menos
                   else if(c == '-'){
                     estado = 22;           // Establece el estado a 22
                     lexema += c;           // Añade el carácter al lexema
                   }
                   // Verifica si el carácter es un asterisco
                   else if(c == '*'){
                     estado = 23;           // Establece el estado a 23
                     lexema += c;           // Añade el carácter al lexema
                   }
                   // Verifica si el carácter es una llave de apertura
                  else if(c == '{'){
                     estado = 24;           // Establece el estado a 24
                     lexema += c;           // Añade el carácter al lexema
                   }
                   // Verifica si el carácter es una llave de cierre
                  else if(c == '}'){
                     estado = 25;           // Establece el estado a 25
                     lexema += c;           // Añade el carácter al lexema
                   }
                   // Verifica si el carácter es un paréntesis de apertura
                 else if(c == '('){
                    estado = 26;           // Establece el estado a 26
                    lexema += c;           // Añade el carácter al lexema
                   }
                   // Verifica si el carácter es un paréntesis de cierre
                 else if(c == ')'){
                    estado = 27;           // Establece el estado a 27
                    lexema += c;           // Añade el carácter al lexema
                   }
                   // Verifica si el carácter es una coma
                 else if(c == ','){
                    estado = 28;           // Establece el estado a 28
                    lexema += c;           // Añade el carácter al lexema
                   }
                   // Verifica si el carácter es un punto
                 else if(c == '.'){
                    estado = 29;           // Establece el estado a 29
                    lexema += c;           // Añade el carácter al lexema
                   }
                   // Verifica si el carácter es un punto y coma
                 else if(c == ';'){
                    estado = 30;           // Establece el estado a 30
                    lexema += c;           // Añade el carácter al lexema
                   }
                   // Verifica si el carácter no es una letra o un dígito y no es ninguno de los caracteres listados
                 else if(!Character.isLetterOrDigit(c) && c!='\n' && c!=' ' && c!='\t' && c!='\r'){
                    estado = 31;           // Establece el estado a 31
                    i--;                   // Decrementa el valor de i
                    lexema = "";           // Limpia el valor del lexema
                   }
              break;
               case 1:
                    if(c == '='){
                        lexema += c;
                    Token t = new Token (TipoToken.GREATER_EQUAL, lexema);
                    tokens.add(t);
                    }
                    else{
                        Token t = new Token(TipoToken,GREATER, lexema);
                        tokens.add(t);
                        i--;
                    }
                estado = 0;
                lexema = "";
            break
                case 2:
                    if(c == '='){
                        lexema += c;
                Token t = new Token(TipoToken.LESS_EQUAL, lexema);
                tokens.add(t);
                    }
            else{
                Token t = new Token(TipoToken.LESS, lexema);
                tokens.add(t);
                i--;
            }
            estado = 0;
            lexema="";
                    break;
            case 7:
                if(c == '='){
                lexema += c;
                Token t = new Token(TipoToken.EQUAL_EQUAL, lexema);
                tokens.add(t);
            }
            else{
                Token t = new Token(TipoToken.EQUAL, lexema);
                tokens.add(t);
                i--;
            }
            estado = 0;
            lexema = "";
            break;
            case 9:
                if(Character.isLetter(c) || Character.idDigit(c)){
                estado = 9;
                lexema += c;
            }
            else{
                TipoToken tt = palabrasReservadas.get(lexema);
                if(tt == null){
                    Token t = new Token(TipoToken.IDENTIFIER, lexema);
                    tokens.add(t);
                }
                else{
                    Token t = new Token(tt, lexema);
                    tokens.add(t);
                }
                estado = 0;
                lexema = "";
                i--;
            }
        break;
                case 10:
                    if(c == '='){
                        lexema += c;
                        Token t = new Token(TipoToken.BANG_EQUAL, lexema);
                        tokens.add(t);
                    }
                    else{
                        Token t = new Token(TipoToken.BANG, lexema);
                        tokens.add(t);
                        i--;
                    }
                    estado = 0;
                    lexema = "";
                break;
                case 11:
                    if(Character.isDigit(c)){
                        estado = 11;
                        lexema += c;
                    }
                    else if(c == '.'){
                        estado = 12;
                        lexema += c;
                    }
                    else if(c == 'E'){
                        estado = 14;
                        lexema += c;
                    }
                    else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Integer.valueOf(lexema));
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                break;
                case 12:
                    if(Character.isDigit(c)){
                        estado = 13;
                        lexema += c;
                    }
                    else{
                        lexema = lexema.substring(0, lexema.length() - 1);
                        Token t = new Token(TipoToken.NUMBER, lexema, Integer.valueOf(lexema));
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                        i--;
                    }
                break;
                case 13:
                    if(Character.isDigit(c)){
                        estado = 13;
                        lexema += c;
                    }
                    else if(c == 'E'){
                        estado = 14;
                        lexema += c;
                    }
                    else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Float.parseFloat(lexema));
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                break;
                case 14:
                    if(Character.isDigit(c)){
                        estado = 15;
                        lexema += c;
                    }
                    else{
                        if(lexema.contains(".")){
                            lexema = lexema.substring(0, lexema.length() - 1);
                            Token t = new Token(TipoToken.NUMBER, lexema, Integer.valueOf(lexema));
                            tokens.add(t);
                        }
                        else{
                            lexema = lexema.substring(0, lexema.length() - 1);
                            Token t = new Token(TipoToken.NUMBER, lexema, Float.parseFloat(lexema));
                            tokens.add(t);
                        }
                        i--;
                        i--;
                        lexema = "";
                        estado = 0;
                    }
                break;
                case 15:
                    if(Character.isDigit(c)){
                        estado = 15;
                        lexema += c;
                    }
                    else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Double.parseDouble(lexema));
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                break;
                case 16:
                    if(c == '\n'){
                        Interprete.error(linea, "Salto de linea no permitido");
                        estado = 0;
                        do {
                            if(i != (source.length() - 1)){
                                i++;
                                c = source.charAt(i);
                            }
                            else{
                                Interprete.error(linea, "Se esperaba '\"'");
                                break;
                            }
                            if(c == '\n'){
                                Interprete.error(linea, "Salto de linea no permitido");
                            }
                        } while (c != '"');
                        estado = 0;
                        lexema = "";
                    }
                    else if(c != '"'){
                        estado = 16;
                        lexema += c;
                        if(i == (source.length() - 1)){
                            Interprete.error(linea, "Se esperaba '\"'");
                        }
                    }
                    else{
                        lexema += c;
                        Token t = new Token(TipoToken.STRING, lexema, lexema.substring(1, lexema.length() - 1));
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }
                break;
// Cuando el estado es 17
                case 17:
                    if(c == '/'){
                        estado = 20;           // Cambio al estado 20 si se encuentra otra barra diagonal (comentario de línea)
                        lexema += c;           // Añade el carácter al lexema
                    }
                    else if(c == '*'){
                        estado = 18;           // Cambio al estado 18 (inicio de comentario de bloque)
                        lexema = "";           // Limpia el lexema
                    }
                    else{
                        Token t = new Token(TipoToken.SLASH, lexema); // Crea un token para una sola barra diagonal
                        tokens.add(t);
                        i--;
                        lexema = "";
                        estado = 0;
                    }
                break;          
                // Tratando comentario de bloque
                case 18:
                    if(c != '*'){
                        estado = 18;           // Permanece en el estado 18 si no encuentra '*'
                        if(i == (source.length() - 1)){
                            Interprete.error(linea, "Se esperaba '*/'"); // Error si no se cierra el comentario
                        }
                    }
                    else{
                        estado = 19;           // Cambio al estado 19 si se encuentra '*'
                    }
                break;
                
                // Estado intermedio en comentario de bloque
                case 19:
                    if(c == '*'){
                        estado = 19;           // Permanece en el estado 19 si encuentra otro '*'
                    }
                    else if(c == '/'){
                        estado = 0;            // Final del comentario de bloque
                    }
                    else{
                        estado = 18;           // Regresa al estado 18 si no encuentra '/'
                        if(i == (source.length() - 1)){
                            Interprete.error(linea, "Se esperaba '*/'"); // Error si no se cierra el comentario
                        }
                    }
                break;
                
                // Tratando comentario de línea
                case 20:
                    if(c != '\n'){
                        estado = 20;           // Permanece en el estado 20 mientras no encuentre un salto de línea
                        lexema += c;           // Añade el carácter al lexema
                    }
                    else{
                        estado = 0;            // Final del comentario de línea
                        lexema = "";
                    }
                break;
                               // Caso para el signo más
                case 21:{
                    Token t = new Token(TipoToken.PLUS, lexema);   // Crea un token para el signo más
                    tokens.add(t);                                 // Añade el token a la lista de tokens
                    i--;                                           // Decrementa el índice
                    lexema = "";                                   // Limpia el lexema
                    estado = 0;                                    // Reinicia el estado
                break;}
                
                // Caso para el signo menos
                case 22:{
                    Token t = new Token(TipoToken.MINUS, lexema);  // Crea un token para el signo menos
                    tokens.add(t);                                 // ...
                    i--;                                           // ...
                    lexema = "";                                   // ...
                    estado = 0;                                    // ...
                break;}

                // Caso para el asterisco
                case 23:{
                    Token t = new Token(TipoToken.STAR, lexema);   // Crea un token para el asterisco
                    tokens.add(t);
                    i--;
                    lexema = "";
                    estado = 0;
                break;}

                // Caso para la llave de apertura
                case 24:{
                    Token t = new Token(TipoToken.LEFT_BRACE, lexema); // Crea un token para '{'
                    tokens.add(t);
                    i--;
                    lexema = "";
                    estado = 0;
                break;}

                // Caso para la llave de cierre
                case 25:{
                    Token t = new Token(TipoToken.RIGHT_BRACE, lexema); // Crea un token para '}'
                    tokens.add(t);
                    i--;
                    lexema = "";
                    estado = 0;
                break;}

                // Caso para el paréntesis de apertura
                case 26:{
                    Token t = new Token(TipoToken.LEFT_PAREN, lexema);  // Crea un token para '('
                    tokens.add(t);
                    i--;
                    lexema = "";
                    estado = 0;
                break;}

                // Caso para el paréntesis de cierre
                case 27:{
                    Token t = new Token(TipoToken.RIGHT_PAREN, lexema); // Crea un token para ')'
                    tokens.add(t);
                    i--;
                    lexema = "";
                    estado = 0;
                break;}

                // Caso para la coma
                case 28:{
                    Token t = new Token(TipoToken.COMMA, lexema);       // Crea un token para la coma
                    tokens.add(t);
                    i--;
                    lexema = "";
                    estado = 0;
                break;}

                // Caso para el punto
                case 29:{
                    Token t = new Token(TipoToken.DOT, lexema);         // Crea un token para el punto
                    tokens.add(t);
                    i--;
                    lexema = "";
                    estado = 0;
                break;}

                // Caso para el punto y coma
                case 30:{
                    Token t = new Token(TipoToken.SEMICOLON, lexema);   // Crea un token para el punto y coma
                    tokens.add(t);
                    i--;
                    lexema = "";
                    estado = 0;
                break;}

                // En caso de expresión no válida
                case 31:
                    Interprete.error(linea, "Expresion no valida");     // Muestra un mensaje de error para una expresión no válida
                    estado = 0;                                          // Reinicia el estado
                break;
            }
        }
        return tokens;
    }
}
