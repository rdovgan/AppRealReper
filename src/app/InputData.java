package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class InputData {

    private int Dt;
    private Angle dt;
    private Angle Aon;
    private int Rx;
    private int Ry;
    private int Rh;
    private int VPx;
    private int VPy;
    private int VPh;
    private int KSPx;
    private int KSPy;
    private int Dk;
    private Angle AR;
    private int Rn;
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
    private String rozm;
    private Angle pz;
    private double vd;
    private char zar;

    public InputData() {
        random = new Random();
        //Дт і dт
        Dt = random.nextInt(5000) + 5000;
        dt = new Angle(random.nextInt(6) - 3 + random.nextInt(100) / 100.);
        //Аон
        Aon = new Angle(random.nextInt(60));
        if (Aon.get() % 15 > 5) {
            Aon.set(Aon.get() + 7);
        }
        //R (x,y,h,n)
        Rx = random.nextInt(10000) * 10;
        Ry = random.nextInt(10000) * 10;
        Rh = random.nextInt(150) + 80;
        Rn = random.nextInt(20);
        //пар.
        par = random.nextInt(100) + "-" + random.nextInt(100) + "-" + random.nextInt(100);
        //ВП (x,y,h)
        VPx = Rx - (int) (Dt * Math.cos(360 - dt.getDigr()));
        VPy = Ry - (int) (Dt * Math.sin(360 - dt.getDigr()));
        VPh = random.nextInt(140) - 70 +Rh;
        //Дк, АR
        Dk = random.nextInt(1000) + 2000;
        AR = new Angle(Aon.get() + random.nextInt(600) / 100. - 3);
        //КСП (x,y)
        KSPx = Rx - (int) (Dk * Math.cos(360 - AR.getDigr()));
        KSPy = Ry - (int) (Dk * Math.sin(360 - AR.getDigr()));
        //p - дальність
        //d - попроавки на дальність
        //s - поправки на зміщення
        p1 = (int) (Dt / 1000 - random.nextInt(2) - 1) * 1000;
        p3 = (int) (Dt / 1000 + random.nextInt(2) + 2) * 1000;
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
        if (Dt < p2) {
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
        deltaD = (int) ((Dt - b) / k);
        k = (daln1 - daln2) / (pa1.get() - pa2.get());
        b = daln2 - pa2.get() * k;
        deltad = new Angle((Dt - b) / k);
        //Дв, dв
        Dv = Dt + deltaD;
        dv = new Angle(dt.get() + deltad.get());
        zar = getZar(Dv);
        dxt = getDxt(Dv);
        p = getP(Dv);
        kv = Math.round(10.0*Dk/Dt)/10.0;
        pz = new Angle(Math.round(10.0*(AR.get()-(Aon.get()+dt.get())))/10.0);
        kk = new Angle((double)(pz.get()/(Dt/100.0)));
        e = new Angle(Math.round((Rh-VPh)/(Dt/1000.))/100.);
        vd = getVd(Dv);
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

    public double getDxt(int daln) {
        int d1, d2;
        d1 = ((int) (daln / 200)) * 200;
        d2 = d1 + 200;
        double dxt1, dxt2;
        dxt1 = dxt2 = 0;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            System.exit(1);
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:hsqldb:file:db", "SA", "");
        } catch (SQLException e) {
            System.exit(1);
        }
        try {
            Statement statement = connection.createStatement();
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
            query = "SHUTDOWN";
            statement.execute(query);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        double res = (daln - d1) * (dxt2 - dxt1) / 200;
        return res + dxt1;
    }

    public int getP(int daln) {
        int d1, d2;
        d1 = ((int) (daln / 200)) * 200;
        d2 = d1 + 200;
        double p1, p2;
        p1 = p2 = 0;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            System.exit(1);
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:hsqldb:file:db", "SA", "");
        } catch (SQLException e) {
            System.exit(1);
        }
        try {
            Statement statement = connection.createStatement();
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
            query = "SHUTDOWN";
            statement.execute(query);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        double res = (daln - d1) * (p2 - p1) / 200;
        return (int)(res + p1);
    }
    
    public double getVd(int daln) {
        int d1, d2;
        d1 = ((int) (daln / 200)) * 200;
        d2 = d1 + 200;
        double vd1, vd2;
        vd1 = vd2 = 0;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            System.exit(1);
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:hsqldb:file:db", "SA", "");
        } catch (SQLException e) {
            System.exit(1);
        }
        try {
            Statement statement = connection.createStatement();
            String query;
            query = "SELECT vd FROM strilba WHERE ZAR= '" + zar + "' and dal = " + d1;
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                vd1 = resultSet.getDouble(1);
            }
            query = "SELECT vd FROM strilba WHERE ZAR= '" + zar + "' and dal = " + d2;
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                vd2 = resultSet.getDouble(1);
            }
            query = "SHUTDOWN";
            statement.execute(query);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        double res = (daln - d1) * (vd2 - vd1) / 200;
        return res + vd1;
    }

    public String toString() {
        return "Дт = " + Dt + "\tdт = ОН" + dt.toZ() + "\nAон = " + Aon + "\tZar = " + zar + " \tΔХтис = " + dxt + "\nRx = " + Rx + "\tRy = " + Ry
                + "\tRh = " + Rh + "\nВПx = " + VPx + "\tВПy = " + VPy + "\tВПh = " + VPh
                + "\nКСПx = " + KSPx + "\tКСПy = " + KSPy + "\nДк = " + Dk + "\tAR = "
                + AR + "\tN = " + Rn + "\tpar = " + par + "\n\t" + p1 + "\t" + p2 + "\t" + p3
                + "\n\t" + d1 + "\t" + d2 + "\t" + d3 + "\n\t" + s1.toZ() + "\t" + s2.toZ() + "\t" + s3.toZ()
                + "\nΔД = " + deltaD + "\tΔd = " + deltad.toZ() + "\nДв = " + Dv + "\tdВ = " + dv.toZ()+"\nП = "+p+"\tПЗ = "+pz
                + "\nKв = "+kv+"\tКк = "+kk+"\tε = "+e+"\tВд = "+vd;
    }
}
