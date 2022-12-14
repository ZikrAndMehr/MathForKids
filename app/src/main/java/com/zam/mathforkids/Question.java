package com.zam.mathforkids;

public class Question {

    private String s;
    private int max, min, a, b, c;
    private int[] random = new int[4];

    public Question(String s, int max, int min){
        this.s = s;
        this.max = max;
        this.min = min;
        a = getRandomNumber(false);
        setupSwitch();
        random[0] = getRandomNumber(true);
        random[1] = getRandomNumber(true);
        random[2] = getRandomNumber(true);
        random[3] = getRandomNumber(true);
    }

    private void setupSwitch(){
        switch (s){
            case "+":
                b = getRandomNumber(false);
                c = a + b;
                break;
            case "-":
                b = getRandomNumber(a-1);
                c = a - b;
                break;
            case "×":
                b = getRandomNumber(min/5);
                c = a * b;
                break;
            case "÷":
                b = getRandomNumber(a);
                while (a%b!=0){
                    b = getRandomNumber(a);
                }
                c = a / b;
                break;
        }
    }

    private int getRandomNumber(boolean check) {
        if (check){
            int x = 1, r = (int) (Math.random() * ((c+(c/2) - c/2) + 1)) + c/2;

            while (x!=0) {
                if (r==c){
                    r = (int) (Math.random() * ((c+(c/2) - c/2) + 1)) + c/2;
                }
                else {x=0;}
            }
            return r;
        }

        else {
            return (int) (Math.random() * ((max - min) + 1)) + min;
        }
    }
    private int getRandomNumber(int mx) {
        return (int) (Math.random() * (mx - 1) + 1) + 1;

    }

    public String getQuestion() {
        return String.valueOf(a)+s+String.valueOf(b)+"="+String.valueOf(c);
    }

    public String getA() {
        return String.valueOf(a);
    }

    public String getB() {
        return  String.valueOf(b);
    }

    public int getC() {
        return  c;
    }

    public int[] getRandom() {
        return random;
    }
}

