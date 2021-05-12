package com.company;

import java.util.ArrayList;

public class Tokenizer {
    private ArrayList<Token> tokens = new ArrayList<>();
    private char[] chars;
    private int index;

    public ArrayList<Token> tokenize(String program) {
        chars = program.toCharArray();
        tokens.clear();
        index = 0;
        return startTokenize();
    }

    private ArrayList<Token> startTokenize() {
        ArrayList<Token> res = new ArrayList<>();
        while (true) {
            Token token = getNextToken(index);
            if (token == null) {
                break;
            }
            res.add(token);
            index += token.string.length();
        }
        return res;
    }

    private Token getNextToken(int index) {
        if (index >= chars.length || index < 0) {
            return null;
        }
        StringBuilder tokenString = new StringBuilder();
        TokenType tokenType = null;
        for (int i = index; i < chars.length; i++) {
            char chr = chars[i];
            TokenType type = getTokenType(chr);
            if (tokenType == null) {
                tokenType = type;
                tokenString.append(chr);
                continue;
            }
            if (tokenType != type) {
                return new Token(tokenString.toString(), tokenType);
            }
            tokenString.append(chr);
        }
        if (tokenType == null) {
            return null;
        }
        return new Token(tokenString.toString(), tokenType);
    }

    private TokenType getTokenType(char chr) {
        if (chr >= '0' && chr <= '9') {
            return TokenType.NUMBER;
        }
        if ("IVXLCDM".indexOf(chr) != -1) {
            return TokenType.NUMBER_ROMAN;
        }
        switch (chr) {
            case '+':
                return TokenType.ADD;
            case '-':
                return TokenType.SUB;
            case '*':
                return TokenType.MUL;
            case '/':
                return TokenType.DIV;
            case ' ':
                return TokenType.SPACES;
        }
        throw new RuntimeException("Неверный символ: '" + chr + "'");
    }
}
