package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

    static boolean isRoman = false;

    public static void main(String[] args) throws IOException {
        Tokenizer t = new Tokenizer();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            String inputString = in.readLine();
            ArrayList<Token> tokens = t.tokenize(inputString);
            testTokens(tokens);
            correctTokens(tokens);
            Token res = executeTokens(tokens);
            String resultString = isRoman ? res.getNumberRoman() : String.valueOf(res.getNumber());
            if (tokens.size() != 3) {
                throw new RuntimeException("Не соответствует требованиям. Результат(" + resultString + ")");
            }
            System.out.println(resultString);
        }
    }

    private static void correctTokens(ArrayList<Token> tokens) {
        removeAllSpaces(tokens);
        correctSign(tokens);
    }

    private static void correctSign(ArrayList<Token> tokens) {

        if (tokens.size() > 1) {
            Token firstToken = tokens.get(0);
            Token secondToken = tokens.get(1);
            if (!tokenIsNumber(firstToken) && tokenIsNumber(secondToken)) {
                tokens.remove(0);
                tokens.remove(0);
                if (firstToken.type == TokenType.SUB) {
                    tokens.add(0, new Token(String.valueOf(-secondToken.getNumber()), TokenType.NUMBER));
                } else if (firstToken.type == TokenType.ADD) {
                    tokens.add(0, new Token(String.valueOf(secondToken.getNumber()), TokenType.NUMBER));
                } else {
                    throw new RuntimeException("Неверный токен: " + firstToken.string);
                }
            }
        }

        if (tokens.size() > 3) {
            Token prevToken = null;
            for (int i = 0; i < tokens.size(); i++) {
                Token currToken = tokens.get(i);
                if (prevToken != null && !tokenIsNumber(prevToken) && !tokenIsNumber(currToken)) {
                    if (i < tokens.size() - 1) {
                        Token nextToken = tokens.get(i + 1);
                        if (!tokenIsNumber(nextToken)) {
                            throw new RuntimeException("Неверный токен: " + nextToken.string);
                        }
                        tokens.remove(i + 1);
                        tokens.remove(i);
                        if (currToken.type == TokenType.SUB) {
                            tokens.add(i, new Token(String.valueOf(-nextToken.getNumber()), TokenType.NUMBER));
                        } else if (currToken.type == TokenType.ADD) {
                            tokens.add(i, new Token(String.valueOf(nextToken.getNumber()), TokenType.NUMBER));
                        } else {
                            throw new RuntimeException("Неверный токен: " + currToken.string);
                        }
                        i--;
                    } else {
                        throw new RuntimeException("Неверный последний токен: " + currToken.string);
                    }
                }
                prevToken = currToken;
            }
        }
    }

    private static void removeAllSpaces(ArrayList<Token> tokens) {
        tokens.removeIf(t -> t.type == TokenType.SPACES);
    }

    private static Token executeTokens(ArrayList<Token> tokens) {
        int res = 0;
        Token prevToken = null;
        for (int i = 0; i < tokens.size(); i++) {
            Token currToken = tokens.get(i);
            if (tokenIsNumber(currToken)) {
                if (prevToken == null) {
                    res += currToken.getNumber();
                } else {
                    int number = currToken.getNumber();
                    switch (prevToken.type) {
                        case ADD:
                            res += number;
                            break;
                        case SUB:
                            res -= number;
                            break;
                        case MUL:
                            res *= number;
                            break;
                        case DIV:
                            res /= number;
                            break;
                        default:
                            throw new RuntimeException("Неверный токен: " + currToken.string);
                    }
                }
            }
            prevToken = currToken;
        }
        return new Token(String.valueOf(res), TokenType.NUMBER);
    }

    private static boolean tokenIsNumber(Token token) {
        return (token.type == TokenType.NUMBER || token.type == TokenType.NUMBER_ROMAN);
    }

    private static void testTokens(ArrayList<Token> tokens) {
        {
            boolean isNumber = false;
            boolean isNumberRoman = false;
            for (Token token : tokens) {
                if (token.type == TokenType.NUMBER) {
                    isNumber = true;
                } else if (token.type == TokenType.NUMBER_ROMAN) {
                    isNumberRoman = true;
                }
            }
            if (isNumber && isNumberRoman) {
                throw new RuntimeException("Калькулятор умеет работать только с арабскими или римскими цифрами одновременно.");
            }
            isRoman = isNumberRoman;
        }
    }
}
