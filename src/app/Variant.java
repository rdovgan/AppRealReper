package app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class Variant {

    private Strilba st;
    private int chance;
    private static Random random;
    private int n;
    private int variant;
    private List<String[]> list;
    private Finish f;
    private String path = "Варіанти/";
    private Angle fDov;
    private int lastP;
    private Angle lastRv;
    private Angle lastDov;

    public Strilba getSt() {
        return st;
    }

    public int getChance() {
        return chance;
    }

    public static Random getRandom() {
        return random;
    }

    public int getN() {
        return n;
    }

    public int getVariant() {
        return variant;
    }

    public List<String[]> getList() {
        return list;
    }

    public Finish getF() {
        return f;
    }

    public ShellConsumption getS() {
        return s;
    }

    public String getBR() {
        return BR;
    }
    private ShellConsumption s;

    public String getPath() {
        return path;
    }
    final String BR = "________________________________________________________";

    public Variant() throws SQLException {
        random = new Random();
        st = new Strilba();
        chance = random.nextInt(4) + 1;
        n = 0;
        variant = 0;
        list = new ArrayList<String[]>();
        fDov = new Angle(0);
        lastP = 0;
        lastRv = new Angle(0);
        lastDov = new Angle(0);
    }

    public Variant(int var) throws SQLException {
        random = new Random();
        st = new Strilba();
        chance = random.nextInt(4) + 1;
        n = 0;
        variant = var;
        list = new ArrayList<String[]>();
        fDov = new Angle();
        lastP = 0;
        lastRv = new Angle(0);
        lastDov = new Angle(0);
    }

    public Variant(int var, int c) throws SQLException {
        random = new Random();
        st = new Strilba();
        chance = c;
        n = 0;
        variant = var;
        list = new ArrayList<String[]>();
        fDov = new Angle();
        lastP = 0;
        lastRv = new Angle(0);
        lastDov = new Angle(0);
    }

    public char reverse(char c) {
        if (c == '+') {
            return '-';
        }
        if (c == '-') {
            return '+';
        }
        return '?';
    }

    public void doAction() throws SQLException, IOException {
        Spost sp1, sp2;
        char c;
        if (random.nextInt(2) == 1) {
            c = '+';
        } else {
            c = '-';
        }
        sp1 = new Spost(1, c);
        c = reverse(c);
        n++;//1st
        out(n, sp1);
        sp2 = new Spost(2, c);
        st.doCorrect(sp1, 200);
        n++;//2nd
        out(n, sp2);
        st.doCorrect(sp2, 100);
        switch (chance) {
            case 1:
                case1();
                break;
            case 2:
                case2();
                break;
            case 3:
                case3();
                break;
            case 4:
                case4();
                break;
        }
        fDov = new Angle(st.getDov().get());
        f = new Finish(st.getDov(), st.getP(), st.getID());
        s = new ShellConsumption(f.getF(), f.getG(), f.getDt());
    }

    public void writeDocs() throws IOException {
        outToDocx("" + variant);
        outToDocxU("" + variant);
    }

    public void setVariant(int v) {
        variant = v;
    }

    public Spost oneFromTwo(Spost sp1, Spost sp2) {
        char dov;
        int pod = 0;
        char p;
        if (sp1.dov == 'l') {
            pod -= sp1.pod;
        } else {
            pod += sp1.pod;
        }
        if (sp2.dov == 'l') {
            pod -= sp2.pod;
        } else {
            pod += sp1.pod;
        }
        if (pod < 0) {
            dov = 'l';
        } else {
            dov = 'r';
        }
        if (sp1.p == sp2.p) {
            p = sp1.p;
        } else if (sp1.p == '?') {
            p = sp2.p;
        } else if (sp2.p == '?') {
            p = sp1.p;
        } else {
            p = '?';
        }
        Spost sp = new Spost(dov, pod, p);
        return sp;
    }

    public void case1() {
        Spost sp1, sp2;
        char c;
        if (random.nextInt(2) == 1) {
            c = '+';
        } else {
            c = '-';
        }
        sp1 = new Spost(3, c);
        c = reverse(c);
        sp2 = new Spost(4, c);
        n++;//3rd
        out(n, sp1, sp2);
        Spost sp12 = oneFromTwo(sp1, sp2);
        st.doCorrect(sp12, 0);
        if (random.nextInt(2) == 1) {
            c = '+';
        } else {
            c = '-';
        }
        sp1 = new Spost(5, c);
        c = reverse(c);
        sp2 = new Spost(6, c);
        sp12 = oneFromTwo(sp1, sp2);
        n++;//4th
        out(n, sp1, sp2);
        st.doCorrect(sp12, 0);
        n++;
        out(n);
    }

    public void case2() {
        Spost sp1, sp2;
        char c;
        if (random.nextInt(2) == 1) {
            c = '+';
        } else {
            c = '-';
        }
        sp1 = new Spost(3, c);
        sp2 = new Spost(4, c);
        n++;//3rd
        out(n, sp1, sp2);
        Spost sp12 = oneFromTwo(sp1, sp2);
        st.doCorrect(sp12, 100);
        c = reverse(c);
        sp1 = new Spost(5, c);
        sp2 = new Spost(6, c);
        n++;//4th
        out(n, sp1, sp2);
        sp12 = oneFromTwo(sp1, sp2);
        st.doCorrect(sp12, 50);
        n++;
        out(n);
    }

    public void case3() {
        Spost sp1, sp2;
        char c;
        if (random.nextInt(2) == 1) {
            c = '+';
        } else {
            c = '-';
        }
        sp1 = new Spost(3, c);
        c = reverse(c);
        sp2 = new Spost(4, c);
        n++;//3rd
        out(n, sp1, sp2);
        Spost sp12 = oneFromTwo(sp1, sp2);
        st.doCorrect(sp12, 0);
        if (random.nextInt(2) == 1) {
            c = '+';
        } else {
            c = '-';
        }
        sp1 = new Spost(5, c);
        sp2 = new Spost(6, c);
        n++;//4th
        out(n, sp1, sp2);
        sp12 = oneFromTwo(sp1, sp2);
        st.doCorrect(sp12, -1);
        n++;
        out(n);
    }

    public void case4() {
        Spost sp1, sp2;
        char c;
        if (random.nextInt(2) == 1) {
            c = '+';
        } else {
            c = '-';
        }
        sp1 = new Spost(3, c);
        sp2 = new Spost(4, c);
        n++;//3rd
        out(n, sp1, sp2);
        Spost sp12 = oneFromTwo(sp1, sp2);
        st.doCorrect(sp12, 100);
        sp1 = new Spost(3, c);
        sp2 = new Spost(4, c);
        n++;//4th
        out(n, sp1, sp2);
        sp12 = oneFromTwo(sp1, sp2);
        st.doCorrect(sp12, 0);
        sp1 = new Spost(3, c);
        sp2 = new Spost(4, c);
        n++;//5th
        out(n, sp1, sp2);
        sp12 = oneFromTwo(sp1, sp2);
        st.doCorrect(sp12, 100);
        c = reverse(c);
        sp1 = new Spost(3, c);
        sp2 = new Spost(4, c);
        n++;//6th
        out(n, sp1, sp2);
        sp12 = oneFromTwo(sp1, sp2);
        st.doCorrect(sp12, -2);
        n++;
        out(n);
    }

    public void out(int numb) {
        String[] com = new String[6];
        com[0] = " " + n;
        com[1] = "Стій, записати. Репер " + st.getID().getRN() + "-й";
        com[2] = st.getP() + "";
        com[3] = st.getRv().toString();
        com[4] = st.getPDov().toZ() + "    \nОН" + st.getDov().toZ();
        com[5] = "";
        list.add(numb - 1, com);
    }

    public void out(int numb, Spost sp) {
        String[] com = new String[6];
        com[0] = " " + n;
        if (n == 1) {
            com[1] = "Віяло скупчене, 3-й, 1 снар. Вогонь";
            com[4] = "ОН" + st.getDov().toZ();
        } else {
            com[1] = "Вогонь";
            if (st.getDov().get() != lastDov.get()) {
                com[4] = st.getPDov().toZ();
                lastDov = new Angle(st.getDov().get());
            } else {
                com[4] = " ";
            }
        }
        if (st.getP() != lastP) {
            com[2] = st.getP() + "";
            lastP = st.getP();
        } else {
            com[2] = " ";
        }
        if (st.getRv().get() != lastRv.get()) {
            com[3] = st.getRv().toString();
            lastRv = new Angle(st.getRv().get());
        } else {
            com[3] = " ";
        }
        com[5] = sp.toString();
        list.add(numb - 1, com);
    }

    public void out(int numb, Spost sp1, Spost sp2) {
        String[] com = new String[6];
        com[0] = " " + n;
        if (n == 3) {
            com[1] = "2 снаряда. Вогонь";
        } else {
            com[1] = "Вогонь";
        }
        if (lastP != st.getP()) {

            com[2] = st.getP() + "";
            lastP = st.getP();
        } else {
            com[2] = " ";
        }
        if (lastRv.get() != st.getRv().get()) {

            com[3] = st.getRv().toString();
            lastRv = new Angle(st.getRv().get());
        } else {
            com[3] = " ";
        }
        if (lastDov.get() != st.getDov().get()) {
            com[4] = st.getPDov().toZ();
            lastDov = new Angle(st.getDov().get());
        } else {
            com[4] = " ";
        }
        com[5] = sp1.toString() + ", " + sp2.toString();
        list.add(numb - 1, com);
    }

    public InputData getID() {
        return st.getID();
    }

    public void outToDocx(String filename) throws IOException {
        InputData i = st.getID();
        XWPFDocument d = new XWPFDocument();
        XWPFParagraph para1 = d.createParagraph();
        para1.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = para1.createRun();
        run.setBold(true);
        run.setFontSize(14);
        run.setText("Варіант №" + variant);
        run = write(d, "122 мм Г Д-30");

        run = write(d, "ВП:   X = " + i.getVPX() + "\tY = " + i.getVPY() + "\th = " + i.getVPH()
                + "\tOH = " + i.getON());
        run = write(d, "КСП:  X = " + i.getKSPX() + "\tY = " + i.getKSPY());
        run = write(d, "\t\tПоправки:");
        run = write(d, i.getP1() + "\t\t" + i.getP2() + "\t\t" + i.getP3());
        run = write(d, z(i.getD1()) + "\t\t" + z(i.getD2()) + "\t\t" + z(i.getD3()));
        run = write(d, i.getS1().toZ() + "\t\t" + i.getS2().toZ() + "\t\t" + i.getS3().toZ());
        run = write(d, "Пристріляти дійсний репер №" + i.getRN() + ", снар. ОФ-462Ж, "
                + "пар. " + i.getRPar() + ", підрив. РГМ-2");
        run = write(d, "ДкR = " + i.getDK() + "\tAR(КСП) = " + i.getAR() + "\thR = " + i.getRH());
        run = write(d, "ВІДПОВІДЬ: ");
        String r = "справа";
        if (i.getRozm() == 'l') {
            r = "зліва";
        }
        run = write(d, "ДтR = " + i.getDT() + "\tdтR = " + i.getDovT().toZ() + "\tER = " + i.getE() + "\tДвR = " + i.getDV() + "\tВП: "
                + r + "\tЗаряд: " + i.zarToStr());
        run = write(d, "ΔXтис = " + i.getDXT()
                + "\tКв = " + i.getKV() + "\tКк = " + i.getKK()
                + "\tПЗ = " + i.getPZ() + "\tВд = " + (int) (i.getVD()));
        XWPFTable tab = d.createTable();
        XWPFTableRow row = tab.getRow(0);
        row.getCell(0).setText("  N\t");
        row.addNewTableCell().setText("   \t\tКоманда\t");
        row.addNewTableCell().setText("   П\t");
        row.addNewTableCell().setText("   Рв\t");
        row.addNewTableCell().setText("   Дов  \t");
        row.addNewTableCell().setText("   Спостереження\t");
        for (int j = 0; j < list.size(); j++) {
            row = row(tab, list.get(j));
        }
        run = write(d, "dпR = ОН" + f.getdpR().toZ() + "\tПвR = " + f.getpR() + "\tДпR = "
                + f.getDpR() + "\t∆ДпR = " + z(f.getDeltaDpR()) + "\t∆dпR = " + f.getDeltadpR()
                + "\tKc = " + z(f.getK()));
        String tar = "";
        if (s.missionType == "подавлення") {
            tar = "Подавити ";
        } else {
            tar = "Знищити ";
        }
        run = write(d, tar + "ціль №101 " + s.targetName);
        run = write(d, "Витрата снарядів: Норма");
        run = write(d, "Xц = " + f.getX() + "\tYц = " + f.getY() + "\thц = " + f.getH()
                + "\tФц = " + f.getF() + "\tГц = " + f.getG());
        run = write(d, BR);
        run = write(d, "Дтц = " + f.getDt() + "\tdтц = ОН" + f.getdt().toZ()
                + "\tEц = " + f.getE().toZ() + "\tДвц = " + f.getDv() + "\t∆Xтис. = "
                + (int) (f.getDxt()) + "\tП = " + f.getP());
        run = write(d, "Zр = " + f.getzR().toZ() + "\tZц = " + f.getZ().toZ() + "\tРв = "
                + f.getRv() + "\tdвц = " + f.getdv().toZ());
        XWPFTable rez = d.createTable();
        XWPFTableRow row2 = rez.getRow(0);
        row2.getCell(0).setText("    Команда\t");
        row2.addNewTableCell().setText("   П\t");
        row2.addNewTableCell().setText("   Рв\t");
        row2.addNewTableCell().setText("   Дов\t");
        row2 = rez.createRow();
        row2.getCell(0).setText("  Віяло = " + f.getIv() + ". По " + s.consForOneGun + " снар. Біглий вогонь.");
        row2.getCell(1).setText("  " + f.getP() + "  ");
        row2.getCell(2).setText("  " + f.getRv() + "  ");
        row2.getCell(3).setText("  " + f.getdv().toZ() + "  ");
        d.write(new FileOutputStream(path + filename + "Розв.docx"));
    }

    public void outToDocxU(String filename) throws IOException {
        InputData i = st.getID();
        XWPFDocument d = new XWPFDocument();
        XWPFParagraph para1 = d.createParagraph();
        para1.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = para1.createRun();
        run.setBold(true);
        run.setFontSize(14);
        run.setText("Варіант №" + variant);
        run = write(d, "ВП:   X = " + i.getVPX() + "\tY = " + i.getVPY() + "\th = " + i.getVPH()
                + "\tOH = " + i.getON());
        run = write(d, "КСП:  X = " + i.getKSPX() + "\tY = " + i.getKSPY());
        run = write(d, "\t\tПоправки:");
        run = write(d, i.getP1() + "\t\t" + i.getP2() + "\t\t" + i.getP3());
        run = write(d, z(i.getD1()) + "\t\t" + z(i.getD2()) + "\t\t" + z(i.getD3()));
        run = write(d, i.getS1().toZ() + "\t\t" + i.getS2().toZ() + "\t\t" + i.getS3().toZ());
        run = write(d, "Пристріляти дійсний репер №" + i.getRN() + ", снар. ОФ-462Ж, "
                + "пар. " + i.getRPar() + ", підрив. РГМ-2");
        run = write(d, "ДкR = " + i.getDK() + "\tAR(КСП) = " + i.getAR() + "\thR = " + i.getRH());
        run = write(d, "");
        XWPFTable tab = d.createTable();
        XWPFTableRow row = tab.getRow(0);
        row.getCell(0).setText("  N\t");
        row.addNewTableCell().setText("   \t\tКоманда\t");
        row.addNewTableCell().setText("   П\t");
        row.addNewTableCell().setText("   Рв\t");
        row.addNewTableCell().setText("   Дов  \t");
        row.addNewTableCell().setText("   Спостереження\t");
        for (int j = 0; j < list.size() - 1; j++) {
            row = rowU(tab, list.get(j));
        }
        run = write(d, "Перенесення вогню");
        String tar = "";
        if (s.missionType == "подавлення") {
            tar = "Подавити ";
        } else {
            tar = "Знищити ";
        }
        run = write(d, tar + "ціль №101 " + s.targetName + ". Витрата снарядів: Норма");
        run = write(d, "");
        run = write(d, "Xц = " + f.getX() + "\tYц = " + f.getY() + "\thц = " + f.getH()
                + "\tФц = " + f.getF() + "\tГц = " + f.getG());

        d.write(new FileOutputStream(path + filename + ".docx"));
    }

    public String z(double a) {
        if (a >= 0) {
            return "+" + a;
        } else {
            return a + "";
        }
    }

    public String z(int a) {
        if (a >= 0) {
            return "+" + a;
        } else {
            return a + "";
        }
    }

    public XWPFRun write(XWPFDocument doc, String text) {
        XWPFParagraph par = doc.createParagraph();
        XWPFRun run = par.createRun();
        par.setSpacingAfter(2);
        par.setSpacingBefore(2);
        run.setFontSize(12);
        String[] list = text.split("\t");
        run.setText(list[0]);
        for (int i = 1; i < list.length; i++) {
            run.getCTR().addNewTab();
            run.setText(list[i]);
        }
        return run;
    }

    public XWPFTableRow row(XWPFTable tab, String[] s) {
        XWPFTableRow row = tab.createRow();
        row.getCell(0).setText("  " + s[0] + "  ");
        row.getCell(1).setText("  " + s[1] + "  ");
        row.getCell(2).setText("  " + s[2] + "  ");
        row.getCell(3).setText("  " + s[3] + "  ");
        row.getCell(4).setText("  " + s[4] + "  ");
        row.getCell(5).setText("  " + s[5] + "  ");
        return row;
    }

    public XWPFTableRow row(XWPFRun run, XWPFTable tab, String[] s) {
        XWPFTableRow row = tab.createRow();
        row.getCell(0).setText("  " + s[0] + "  ");
        row.getCell(1).setText("  " + s[1] + "  ");
        row.getCell(2).setText("  " + s[2] + "  ");
        row.getCell(3).setText("  " + s[3] + "  ");
        row.getCell(4).setText("  " + s[4] + "  ");
        run.addBreak(BreakType.TEXT_WRAPPING);
        row.getCell(4).setText("  ОН" + st.getDov().toZ() + "  ");
        row.getCell(5).setText("  " + s[5] + "  ");
        return row;
    }

    public XWPFTableRow rowU(XWPFTable tab, String[] s) {
        XWPFTableRow row = tab.createRow();
        row.getCell(0).setText("  " + s[0] + "  ");
        row.getCell(1).setText("  ");
        row.getCell(2).setText("  ");
        row.getCell(3).setText("  ");
        row.getCell(4).setText("  ");
        row.getCell(5).setText("  " + s[5] + "  ");
        return row;
    }
}