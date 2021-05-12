package com.company;

public class Token {
    public String string;
    public TokenType type;

    public Token(String tokenString, TokenType tokenType) {
        string = tokenString;
        type = tokenType;
    }


    @Override
    public String toString() {
        return "Token{" +
                "string='" + string + '\'' +
                ", type=" + type +
                '}';
    }

    public int getNumber() {
        if (type == TokenType.NUMBER) {
            return Integer.parseInt(string);
        }
        if (type == TokenType.NUMBER_ROMAN) {
            return parseRoman(string);
        }
        throw new RuntimeException("Неверный токен: " + string);
    }

    private int parseRoman(String str) {
        int res = 0;
        int prev = 0;
        char prevChar = ' ';
        for (char c : str.toCharArray()) {
            int curr = romanToNumber(c);
            if (prev < curr) {
                if ((prev == 1 || prev % 10 == 0) && prev != 0 && isNear(c, prevChar)) {
                    res -= prev;
                    res += curr - prev;
                } else {
                    res += curr;
                }
            } else {
                res += curr;
            }
            prev = curr;
            prevChar = c;
        }
        return res;
    }

    private boolean isNear(char c, char prevChar) {
        switch (c) {
            case 'V':
            case 'X':
                return prevChar == 'I';
            case 'L':
            case 'C':
                return prevChar == 'X';
            case 'D':
            case 'M':
                return prevChar == 'C';
        }
        return false;
    }

    private int romanToNumber(char c) {
        switch (c) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
        }
        throw new RuntimeException("Неверный римский токен: " + c);
    }

    public String getNumberRoman() {
        int num = getNumber();
        if (num == 0) {
            return "0";
        }
        boolean isNegative = num < 0;
        num = Math.abs(num);
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (num >= 3000) {
                num -= 3000;
                sb.append("MMM");
            } else if (num >= 2000) {
                num -= 2000;
                sb.append("MM");
            } else if (num >= 1000) {
                num -= 1000;
                sb.append('M');
            } else if (num >= 900) {
                num -= 900;
                sb.append("CM");
            } else if (num >= 800) {
                num -= 800;
                sb.append("DCCC");
            } else if (num >= 700) {
                num -= 700;
                sb.append("DCC");
            } else if (num >= 600) {
                num -= 600;
                sb.append("DC");
            } else if (num >= 500) {
                num -= 500;
                sb.append('D');
            } else if (num >= 400) {
                num -= 400;
                sb.append("CD");
            } else if (num >= 300) {
                num -= 300;
                sb.append("CCC");
            } else if (num >= 200) {
                num -= 200;
                sb.append("CC");
            } else if (num >= 100) {
                num -= 100;
                sb.append('C');
            } else if (num >= 90) {
                num -= 90;
                sb.append("XC");
            } else if (num >= 80) {
                num -= 80;
                sb.append("LXXX");
            } else if (num >= 70) {
                num -= 70;
                sb.append("LXX");
            } else if (num >= 60) {
                num -= 60;
                sb.append("LX");
            } else if (num >= 50) {
                num -= 50;
                sb.append('L');
            } else if (num >= 40) {
                num -= 40;
                sb.append("XL");
            } else if (num >= 30) {
                num -= 30;
                sb.append("XXX");
            } else if (num >= 20) {
                num -= 20;
                sb.append("XX");
            } else if (num >= 10) {
                num -= 10;
                sb.append('X');
            } else if (num >= 9) {
                num -= 9;
                sb.append("IX");
            } else if (num >= 8) {
                num -= 8;
                sb.append("VIII");
            } else if (num >= 7) {
                num -= 7;
                sb.append("VII");
            } else if (num >= 6) {
                num -= 6;
                sb.append("VI");
            } else if (num >= 5) {
                num -= 5;
                sb.append('V');
            } else if (num >= 4) {
                num -= 4;
                sb.append("IV");
            } else if (num >= 3) {
                num -= 3;
                sb.append("III");
            } else if (num >= 2) {
                num -= 2;
                sb.append("II");
            } else if (num >= 1) {
                num -= 1;
                sb.append('I');
            } else {
                break;
            }
        }
        if (isNegative) {
            sb.insert(0, '-');
        }
        return sb.toString();
    }
}
