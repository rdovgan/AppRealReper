package app;

import java.sql.SQLException;

public class Strilba {

    private InputData id;
    private Spost sp;
    private int p;
    private Angle pDov;
    private Angle rv;
    private Angle dov;

    public Strilba() throws SQLException {
        id = new InputData();
        sp = new Spost();
        p = id.getPr();
        rv = id.getRV();
        dov = id.getDVA();
        pDov = new Angle(0);
    }

    public void setSp(Spost sp) {
        this.sp = sp;
    }

    public InputData getID() {
        return id;
    }

    public int getP() {
        return p;
    }

    public Angle getRv() {
        return rv;
    }

    public Angle getDov() {
        return dov;
    }

    public Angle getPDov(){
        return pDov;
    }
    
    public void doCorrect(Spost sp, int d) {
        int dp;
        if (d == -1) {
            dp = (int) (id.getVD() / id.getDXT());
        } else 
        if(d==-2){dp = (int)((100-id.getVD())/id.getDXT());}
        else{
            dp = (int) (d / id.getDXT());
        }
        if (sp.getP() == '+') {
            dp = -dp;
        }
        p += dp;
        double ddov1 = (int) (d * id.getKK().get());
        if (id.getRozm() == 'r') {
            if (sp.getP() == '-') {
                ddov1 = -ddov1;
            }
        } else if (sp.getP() == '+') {
            ddov1 = -ddov1;
        }
        double ddov2 = sp.pod * id.getKV();
        if (sp.getDov() == 'r') {
            ddov2 = -ddov2;
        }
        pDov = new Angle((ddov1 + ddov2) / 100.);
        dov = new Angle(dov.get() + (ddov1 + ddov2) / 100.);
    }

    @Override
    public String toString() {
        return "\nП = " + p + "\tРв = " + rv + "\tДов = ОН" + dov.toZ();
    }
}
