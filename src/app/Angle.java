package app;

public class Angle {

    private double ang;

    public Angle() {
        ang = 0;
    }

    public Angle(double a) {
        a %= 60;
        ang = ((int)(a*100.))/100.;
    }

    public Angle(Angle a) {
        ang = a.get();
    }

    public void set(double a) {
        a %= 60;
        ang = ((int)(a*100.))/100.;
    }

    public double get() {
        return ang;
    }

    public void setDigr(double dgr) {
        dgr %= 360;
        ang = dgr / 6.;
    }

    public double getDigr() {
        return ang * 6.;
    }

    public String toString() {
        String res="";
        int a1 = Math.abs((int)ang);
        res += (int)a1;
        int a2 = Math.abs((int)(ang*100.))-a1*100;
        if(a2<10)
            res += "-0"+a2;
        else
            res += "-"+a2;
        return res;
    }
    
    public String toZ(){
        String res="";
        if(ang<0)
            res = "-";
        else 
            res = "+";
        int a1 = Math.abs((int)ang);
        res += (int)a1;
        int a2 = Math.abs((int)(ang*100.))-a1*100;
        if(a2<10)
            res += "-0"+a2;
        else
            res += "-"+a2;
        return res;
    }
}
