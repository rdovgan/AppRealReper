package app;

import java.util.Random;

public class Spost {

    char dov;
    int pod;
    char p;
    static Random random;

    public Spost() {
        random = new Random();
        int znak = random.nextInt(2);
        if (znak == 1) {
            dov = 'r';
        } else {
            dov = 'l';
        }
        pod = random.nextInt(20) + 15;
        znak = random.nextInt(2);
        if (znak == 1) {
            p = '+';
        } else {
            p = '-';
        }
    }

    public Spost(int p1, int p2) {
        random = new Random();
        int znak = random.nextInt(2);
        if (znak == 1) {
            dov = 'r';
        } else {
            dov = 'l';
        }
        pod = random.nextInt(p2 - p1) + p1;
        znak = random.nextInt(2);
        if (znak == 1) {
            p = '+';
        } else {
            p = '-';
        }
    }

    public Spost(int n) {
        if (n == 0) {
            dov = ' ';
            pod = 0;
            p = '?';
        } else {
            random = new Random();
            int znak = random.nextInt(2);
            if (znak == 1) {
                dov = 'r';
            } else {
                dov = 'l';
            }
            pod = random.nextInt(20 / n) + (int) (15 / n);
            znak = random.nextInt(2);
            if (znak == 1) {
                p = '+';
            } else {
                p = '-';
            }
        }
    }

    public char getDov(){
        return dov;
    }
    
    public int getPod(){
        return pod;
    }
    
    public char getP(){
        return p;
    }
    
    public String toString() {
        String res = "";
        if(p=='?')
            return "?";
        if (dov == 'r') {
            res = "П ";
        } else {
            res = "Л ";
        }
        res += pod + ", " + p;
        return res;
    }
}
