package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class InputData {

    private int dalt;
    private Angle dt;
    private Angle aon;
    private int rx;
    private int ry;
    private int rh;
    private int vpx;
    private int vpy;
    private int vph;
    private int kspx;
    private int kspy;
    private int dalk;
    private Angle ar;
    private int rn;
    private String par;
    private int p1;
    private int p2;
    private int p3;
    private int d1;
    private int d2;
    private int d3;
    private Angle s1;
    private Angle s2;
    private Angle s3;
    private int deltaD;
    private Angle deltad;
    private int Dv;
    private Angle dv;
    private static Random random;
    private double dxt;
    private int p;
    private Angle e;
    private double kv;
    private Angle kk;
    private char rozm;
    private Angle pz;
    private double vd;
    private char zar;
    private Angle rv;
    private Connection connection;
    private Statement statement;

    public InputData() throws SQLException {
        random = new Random();
        openCon();
        //Дт і dт
        dalt = random.nextInt(5000) + 5000;
        dt = new Angle(random.nextInt(6) - 3 + random.nextInt(100) / 100.);
        //Аон
        aon = new Angle(random.nextInt(60));
        if ((aon.get() % 15 > 5) && (aon.get() % 15 < 10)) {
            aon.set(aon.get() + 7);
        }
        //R (x,y,h,n)
        rx = random.nextInt(7000) * 10 + 30000;
        ry = random.nextInt(7000) * 10 + 30000;
        rh = random.nextInt(150) + 80;
        rn = random.nextInt(20);
        //пар.
        par = random.nextInt(40) + 1 + "-" + random.nextInt(100) + "-" + random.nextInt(100);
        //ВП (x,y,h)
        vpx = rx - (int) (dalt * Math.cos((360 + dt.getDigr()) * Math.PI / 180.) / 5) * 5;
        vpy = ry - (int) (dalt * Math.sin((360 + dt.getDigr()) * Math.PI / 180.) / 5) * 5;
        vph = random.nextInt(140) - 70 + rh;
        //Дк, АR
        dalk = random.nextInt(2000) + 2000;
        ar = new Angle(aon.get() + random.nextInt(600) / 100. - 3);
        //КСП (x,y)
        kspx = rx - (int) (dalk * Math.cos((360 + ar.getDigr()) * Math.PI / 180.) / 5) * 5;
        kspy = ry - (int) (dalk * Math.sin((360 + ar.getDigr()) * Math.PI / 180.) / 5) * 5;
        //p - дальність
        //d - попроавки на дальність
        //s - поправки на зміщення
        p1 = (int) (dalt / 1000 - random.nextInt(2) - 1) * 1000;
        p3 = (int) (dalt / 1000 + random.nextInt(2) + 2) * 1000;
        p2 = (int) ((p1 + p3) / 2000) * 1000;
        d1 = random.nextInt(10) * 5 + 100;
        d2 = d1 + random.nextInt(10) * 5 + 20;
        d3 = d2 + random.nextInt(10) * 5 + 20;
        s1 = new Angle(-1. * (random.nextInt(10) / 100.));
        s2 = new Angle(s1.get() - (random.nextInt(4) + 4) / 100.);
        s3 = new Angle(s2.get() - (random.nextInt(4) + 4) / 100.);
        //ΔД, Δd
        int daln1, daln2;
        int pd1, pd2;
        Angle pa1, pa2;
        double k, b;
        if (dalt < p2) {
            daln1 = p1;
            daln2 = p2;
            pd1 = d1;
            pd2 = d2;
            pa1 = new Angle(s1);
            pa2 = new Angle(s2);
        } else {
            daln1 = p2;
            daln2 = p3;
            pd1 = d2;
            pd2 = d3;
            pa1 = new Angle(s2);
            pa2 = new Angle(s3);
        }
        k = (daln1 - daln2) / (pd1 - pd2);
        b = daln2 - pd2 * k;
        deltaD = (int) ((dalt - b) / k);
        k = (daln1 - daln2) / (pa1.get() - pa2.get());
        b = daln2 - pa2.get() * k;
        deltad = new Angle((dalt - b) / k);
        //Дв, dв
        Dv = dalt + deltaD;
        dv = new Angle(dt.get() + deltad.get());
        zar = getZar(Dv);
        dxt = getDxt(Dv);
        p = getP(Dv);
        kv = Math.round(10.0 * dalk / dalt) / 10.0;
        pz = new Angle(Math.round(10.0 * (ar.get() - (aon.get() + dt.get()))) / 10.0);
        kk = new Angle((double) (pz.get() / (dalt / 100.0)));
        e = new Angle(Math.round((rh - vph) / (dalt / 1000.)) / 100.);
        vd = getVd(Dv);
        rv = getRv();
        if (ry - kspy != 0) {
            k = (rx - kspx) / (ry - kspy);
        } else {
            k = 1;
            System.err.println("/ by 0 in finding 'k'");
        }
        b = kspx - kspy * k;
        double tx;
        tx = (vpy - b) / k;
        if (tx < vpx) {
            rozm = 'r';
        } else {
            rozm = 'l';
        }
        //closeCon();
    }

    public int getLong(int x1, int y1, int x2, int y2) {
        int dx = x1 - x2;
        int dy = y1 - y2;
        int lng = (int) Math.sqrt(dx * dx + dy * dy);
        return lng;
    }

    public Angle getAngle(int x1, int y1, int x2, int y2) {
        int dx = x1 - x2;
        int dy = y1 - y2;
        Angle angle = new Angle(Math.atan(dy / dx));
        return angle;
    }

    public char getZar(int daln) {
        int d = daln + 2000;
        char rez;
        if (d > 12800) {
            rez = 'p';
        } else if (d > 11600) {
            rez = 'z';
        } else if (d > 10000) {
            rez = '1';
        } else if (d > 8400) {
            rez = '2';
        } else if (d > 6400) {
            rez = '3';
        } else {
            rez = '4';
        }
        return rez;

    }

    public double getDxt(int daln) throws SQLException {
        int d1, d2;
        d1 = ((int) (daln / 200)) * 200;
        d2 = d1 + 200;
        double dxt1, dxt2;
        dxt1 = dxt2 = 0;
        String query;
        query = "SELECT dx FROM strilba WHERE ZAR= '" + zar + "' and dal = " + d1;
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            dxt1 = resultSet.getDouble(1);
        }
        query = "SELECT dx FROM strilba WHERE ZAR= '" + zar + "' and dal = " + d2;
        resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            dxt2 = resultSet.getDouble(1);
        }
        double res = (daln - d1) * (dxt2 - dxt1) / 200;
        return res + dxt1;
    }

    public int getP(int daln) throws SQLException {
        int d1, d2;
        d1 = ((int) (daln / 200)) * 200;
        d2 = d1 + 200;
        double p1, p2;
        p1 = p2 = 0;
        String query;
        query = "SELECT p FROM strilba WHERE ZAR= '" + zar + "' and dal = " + d1;
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            p1 = resultSet.getDouble(1);
        }
        query = "SELECT p FROM strilba WHERE ZAR= '" + zar + "' and dal = " + d2;
        resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            p2 = resultSet.getDouble(1);
        }
        double res = (daln - d1) * (p2 - p1) / 200;
        return (int) (res + p1);
    }

    public double getVd(int daln) throws SQLException {
        int daln1, daln2;
        daln1 = ((int) (daln / 200)) * 200;
        daln2 = daln1 + 200;
        double vd1, vd2;
        vd1 = vd2 = 0;
        String query;
        query = "SELECT vd FROM strilba WHERE ZAR= '" + zar + "' and dal = " + daln1;
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            vd1 = resultSet.getDouble(1);
        }
        query = "SELECT vd FROM strilba WHERE ZAR= '" + zar + "' and dal = " + daln2;
        resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            vd2 = resultSet.getDouble(1);
        }
        double res = (daln - daln1) * (vd2 - vd1) / 200;
        return res + vd1;
    }

    public Angle getRv() throws SQLException {

        int z = 0;
        char h;
        if (rh - vph > 0) {
            h = '+';
        } else {
            h = '-';
        }
        String query;
        query = "SELECT z FROM popr WHERE ZAR= '" + zar + "' and p = " + p + "and e = " + e + "and h = '" + h + "'";
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            z = resultSet.getInt(1);
        }
        Angle rv = new Angle(e.get() + 30 + z / 100.);
        return rv;
    }

    public Angle getA() throws SQLException {
        int z = 0;
        char h;
        if (rh - vph > 0) {
            h = '+';
        } else {
            h = '-';
        }
        String query;
        query = "SELECT z FROM popr WHERE ZAR= '" + zar + "' and p = " + p + "and e = " + e + "and h = '" + h + "'";
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            z = resultSet.getInt(1);
        }
        Angle a = new Angle(z / 100.);
        return a;
    }

    public int getD(int p) throws SQLException {
        int d = 0;
        String query;
        query = "SELECT dal,p FROM strilba WHERE zar = '" + zar + "' AND p < " + p;
        int max = 0;
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            int tempD = resultSet.getInt(1);
            int tempP = resultSet.getInt(2);
            if (tempP > max) {
                max = tempP;
                d = tempD;
            }
        }
        d = p * d / max;
        return d;
    }

    public Angle getZ(int d) throws SQLException {
        String query;
        double a = 0;
        query = "SELECT z,dal FROM strilba WHERE zar = '" + zar + "' AND dal < " + d;
        double max = 0;
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            double z = resultSet.getDouble(1);
            int tempD = resultSet.getInt(2);
            if (z > max) {
                max = z;
                a = tempD;
            }
        }
        Angle z = new Angle(d * max / a / 100.);
        return z;
    }

    public double getDXT() {
        return dxt;
    }

    public int getPr() {
        return p;
    }

    public Angle getKK() {
        return kk;
    }

    public double getKV() {
        return kv;
    }

    public int getDV() {
        return Dv;
    }

    public Angle getDVA() {
        return dv;
    }

    public Angle getRV() {
        return rv;
    }

    public char getRozm() {
        return rozm;
    }

    public double getVD() {
        return vd;
    }

    public int getRN() {
        return rn;
    }

    public int getDT() {
        return dalt;
    }

    public Angle getDovT() {
        return dt;
    }

    public int getRX() {
        return rx;
    }

    public int getRY() {
        return ry;
    }

    public int getRH() {
        return rh;
    }

    public int getVPX() {
        return vpx;
    }

    public int getVPY() {
        return vpy;
    }

    public int getVPH() {
        return vph;
    }

    public Angle getON() {
        return aon;
    }

    public int getKSPX() {
        return kspx;
    }

    public int getKSPY() {
        return kspy;
    }

    public int getD1() {
        return d1;
    }

    public int getD2() {
        return d2;
    }

    public int getD3() {
        return d3;
    }

    public int getP1() {
        return p1;
    }

    public int getP2() {
        return p2;
    }

    public int getP3() {
        return p3;
    }

    public Angle getS1() {
        return s1;
    }

    public Angle getS2() {
        return s2;
    }

    public Angle getS3() {
        return s3;
    }

    public String getRPar() {
        return par;
    }

    public int getDK() {
        return dalk;
    }

    public Angle getAR() {
        return ar;
    }

    public Angle getE() {
        return e;
    }

    public String zarToStr() {
        switch (zar) {
            case '1':
                return "1-й";
            case '2':
                return "2-й";
            case '3':
                return "3-й";
            case '4':
                return "4-й";
            case 'p':
                return "повний";
            case 'z':
                return "зменшений";
        }
        return "";
    }

    public Angle getPZ() {
        return pz;
    }

    public void openCon() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            System.exit(1);
        }
        connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:hsqldb:file:db", "SA", "");
        } catch (SQLException e) {
            System.exit(1);
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeCon() throws SQLException {

        statement.execute("SHUTDOWN");
        connection.close();
    }

    public String toString() {
        return "Дт = " + dalt + "\tdт = ОН" + dt.toZ() + "\nAон = " + aon + "\tZar = " + zar + " \tΔХтис = " + dxt + "\nRx = " + rx + "\tRy = " + ry
                + "\tRh = " + rh + "\nВПx = " + vpx + "\tВПy = " + vpy + "\tВПh = " + vph
                + "\nКСПx = " + kspx + "\tКСПy = " + kspy + "\nДк = " + dalk + "\tAR = "
                + ar + "\tN = " + rn + "\tpar = " + par + "\n\t" + p1 + "\t" + p2 + "\t" + p3
                + "\n\t" + d1 + "\t" + d2 + "\t" + d3 + "\n\t" + s1.toZ() + "\t" + s2.toZ() + "\t" + s3.toZ()
                + "\nΔД = " + deltaD + "\tΔd = " + deltad.toZ() + "\nДв = " + Dv + "\tdВ = " + dv.toZ() + "\nП = " + p + "\tПЗ = " + pz
                + "\nKв = " + kv + "\tКк = " + kk + "\tε = " + e + "\tВд = " + vd + "\nРв = " + rv;
    }
}
