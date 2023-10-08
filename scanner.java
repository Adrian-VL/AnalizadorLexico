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


        return tokens;
    }
}
