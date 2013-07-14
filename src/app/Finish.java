package app;

import java.sql.SQLException;
import java.util.Random;

public class Finish {
    
    private static Random random;
    private int pR;
    private Angle dpR;
    private int DpR;
    private int deltaDpR;
    private Angle deltadpR;
    private double k;
    private InputData id;
    private int x;
    private int y;
    private int h;
    private int F;
    private int G;
    private int Dt;
    private Angle dt;
    private int Dv;
    private Angle dv;
    private int deltaDv;
    private Angle deltadv;
    private Angle e;
    private double dxt;
    private int p;
    private Angle zR;
    private Angle z;
    private Angle rv;
    private Angle Iv;

    public int getpR() {
        return pR;
    }

    public Angle getdpR() {
        return dpR;
    }

    public int getDpR() {
        return DpR;
    }

    public int getDeltaDpR() {
        return deltaDpR;
    }

    public Angle getDeltadpR() {
        return deltadpR;
    }

    public double getK() {
        return k;
    }

    public InputData getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getH() {
        return h;
    }

    public int getF() {
        return F;
    }

    public int getG() {
        return G;
    }

    public int getDt() {
        return Dt;
    }

    public Angle getdt() {
        return dt;
    }

    public int getDv() {
        return Dv;
    }

    public Angle getdv() {
        return dv;
    }

    public int getDeltaDv() {
        return deltaDv;
    }

    public Angle getDeltadv() {
        return deltadv;
    }

    public Angle getE() {
        return e;
    }

    public double getDxt() {
        return dxt;
    }

    public int getP() {
        return p;
    }

    public Angle getzR() {
        return zR;
    }

    public Angle getZ() {
        return z;
    }

    public Angle getRv() {
        return rv;
    }
    
    public Angle getIv(){
        return Iv;
    }
    
    public Finish(){}
    
    public Finish(Angle d, int p, InputData data) throws SQLException{
        random = new Random();
        pR = p;
        dpR = d;
        id = data;
        DpR = id.getD(pR);
        deltaDpR = DpR - id.getDT();
        deltadpR = new Angle(dpR.get() - id.getDovT().get());
        k = ((int)(deltaDpR/(id.getDT()/1000.)))/10.;
        int DtR = random.nextInt(1900);
        Angle dtR = new Angle(random.nextInt(6000)/100);
        int Rx, Ry;
        Rx = id.getRX();
        Ry = id.getRY();
        x = Rx - (int) (DtR* Math.cos((360 + dtR.getDigr()) * Math.PI / 180.) / 5) * 5;
        y = Ry - (int) (DtR * Math.sin((360 + dtR.getDigr()) * Math.PI / 180.) / 5) * 5;
        h = id.getRH()+random.nextInt(10)-5;
        F = random.nextInt(38)*10+20;
        G = random.nextInt(28)*10+20;
        int VPx, VPy;
        VPx = id.getVPX();
        VPy = id.getVPY();
        Dt = (int)(Math.sqrt((VPx-x)*(VPx-x)+(VPy-y)*(VPy-y)));
        dt = new Angle(Math.acos((VPy-y)/Dt));//(Math.toDegrees(Math.atan2(VPx-x, VPy-y)))/6.
        deltaDv = (int)(Dt*k/100.);
        z = id.getZ(Dt);
        zR = id.getZ(DpR);
        Angle dz = new Angle(zR.get()-z.get());
        if(Dt>DpR)
            dz = new Angle(dz.get()*(-1.));
        deltadv = new Angle(deltadpR.get() + z.get());
        Dv = Dt + deltaDv;
        dv = new Angle(dt.get()+deltadv.get());
        e = new Angle((int)((h-id.getVPH())/(Dt/1000.))/100.);
        dxt = id.getDxt(Dv);
        this.p = id.getP(Dv);
        rv = new Angle(30+e.get());
        Iv = new Angle(F/6.*id.getKV());
    }
}

