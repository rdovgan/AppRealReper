package app;

import java.util.Random;

public class ShellConsumption {

    public static final double HA = 10_000.0;
    public static final double REPER_RATE = 0.75;
    public static final int SIGHT_SETTING = 3;
    public static final int GUN_NUMBER = 6;
    public String targetName;
    public Random random = new Random();
    /**
     * Consumption is for whole target or for 1 hectare
     */
    public boolean isForTarget;
    /**
     * mission type: destroy or suppress
     */
    public String missionType;
    /**
     * 0 - open, 1 closed or have armor
     */
    public int targetType;
    public double norm;
    public int consumption;
    public int consForHa;
    public int consForOneGun;
    /**
     * Fan interval in meters (Viyalo)
     */
    public int fanInterval;
    /**
     * Angle setting: 1 or 2 (Ustanovka uglomira)
     */
    public int angleSetting;

    public static double getHA() {
        return HA;
    }

    public static double getREPER_RATE() {
        return REPER_RATE;
    }

    public static int getSIGHT_SETTING() {
        return SIGHT_SETTING;
    }

    public static int getGUN_NUMBER() {
        return GUN_NUMBER;
    }

    public String getTargetName() {
        return targetName;
    }

    public Random getRandom() {
        return random;
    }

    public boolean isIsForTarget() {
        return isForTarget;
    }

    public String getMissionType() {
        return missionType;
    }

    public int getTargetType() {
        return targetType;
    }

    public double getNorm() {
        return norm;
    }

    public int getConsumption() {
        return consumption;
    }

    public int getConsForHa() {
        return consForHa;
    }

    public int getConsForOneGun() {
        return consForOneGun;
    }

    public int getFanInterval() {
        return fanInterval;
    }

    public int getAngleSetting() {
        return angleSetting;
    }

    public static ShellConsumption[] getVARIANTS() {
        return VARIANTS;
    }

    public ShellConsumption(int front, int depth, int topoDistance) {

        if (topoDistance < 6000) {
            if (front < 150) {
                front = 150;
            }
            if (depth < 150) {
                depth = 150;
            }
        }
        if (topoDistance >= 6000 && topoDistance < 16000) {
            if (front < 200) {
                front = 200;
            }
            if (depth < 200) {
                depth = 200;
            }
        }

        double distanceRate = 1.0;

        if (topoDistance >= 11_000) {
            topoDistance -= 10_000;
            topoDistance /= 1000;
            distanceRate = (1.0 / 10.0) * topoDistance + 1.0;
        }

        this.norm = (random.nextInt(2) + 3) / 4.;

        int i = (int) random.nextInt(VARIANTS.length - 1);

        this.targetName = VARIANTS[i].targetName;
        this.isForTarget = VARIANTS[i].isForTarget;
        this.missionType = VARIANTS[i].missionType;
        this.targetType = VARIANTS[i].targetType;

        if (!isForTarget) {
            this.consForHa = VARIANTS[i].consForHa;
            this.consumption = ((int) Math.rint(((front * depth) / HA)
                    * this.consForHa
                    * distanceRate
                    * REPER_RATE
                    * this.norm));
        } else {
            this.consumption = (int) Math.rint(VARIANTS[i].consumption
                    * distanceRate * REPER_RATE * this.norm);
        }

        if (this.consumption > 600) {
            this.norm = 0.5;
            this.consumption = (int) Math.rint(this.consumption * this.norm);
        }
        angleSetting(front);
        consForOneGun();
    }

    private ShellConsumption(String targetName, boolean isForTarget, String misType,
            int targetType, double consumption) {
        this.targetName = targetName;
        this.isForTarget = isForTarget;
        this.missionType = misType;
        this.targetType = targetType;
        if (!isForTarget) {
            this.consForHa = (int) Math.ceil(consumption);
        } else {
            this.consumption = (int) Math.ceil(consumption);
            this.consForHa = 0;
        }
    }

    private void consForOneGun() {
        this.consForOneGun = (int) Math.ceil(this.consumption / (double) (GUN_NUMBER * SIGHT_SETTING * this.angleSetting));
    }

    private void angleSetting(int front) {
        this.fanInterval = (int) (front / 6.0);
        this.angleSetting = 1;
        if (this.fanInterval > 25 && this.targetType == 1) {
            this.angleSetting = 2;
        }
        if (this.fanInterval > 50 && this.targetType == 0) {
            this.angleSetting = 2;
        }
    }
    private static final ShellConsumption[] VARIANTS = {
        new ShellConsumption("Батарея причіпних гармат, відкрита", true, "подавлення", 0, 240 / 3),
        new ShellConsumption("Група РЛС, відкрита", true, "подавелення", 0, 200),
        new ShellConsumption("Жива сила, відкрита", false, "подавлення", 0, 40 / 3),
        new ShellConsumption("Командний пункт, відкритий", false, "подавлення", 0, 50 / 3),
        new ShellConsumption("Батарея причіпних гармат, укрита", true, "подавлення", 1, 240),
        new ShellConsumption("Група РЛС, броньована", true, "подавелення", 1, 200 * 3),
        new ShellConsumption("Жива сила, укрита", false, "подавлення", 1, 180),
        new ShellConsumption("Командний пункт, укритий", false, "подавлення", 1, 51),
        new ShellConsumption("Батарея причіпних гармат, укрита", true, "знищення", 1, 240 * 3),
        new ShellConsumption("Жива сила, укрита", false, "знищення", 1, 40 * 3),
        new ShellConsumption("Командний пункт, укритий", false, "знищення", 1, 50 * 3),
        new ShellConsumption("Батарея причіпних гармат, відкрита", true, "знищення", 0, 240),
        new ShellConsumption("Група РЛС, відкрита", true, "знищення", 0, 200 * 3),
        new ShellConsumption("Жива сила, відкрита", false, "знищення", 0, 40),
        new ShellConsumption("Командний пункт, відкритий", false, "знищення", 0, 50), //new ShellConsumption("ПТКР, відкрита", true, "знищення", 0, 300)
    };

    @Override
    public String toString() {
        String r = this.targetName + "\t";
        r += this.missionType + "\n";
        r += (this.isForTarget) ? "For target\n" : "For one hectar\n";
        r += "Norm " + this.norm + "\n";
        r += "Cons for ha " + this.consForHa + "\t";
        r += "Consumption " + this.consumption + "\n";
        r += "Consumption for Gun " + this.consForOneGun + "\n";
        r += "Angle setting " + this.angleSetting + "\n";
        r += "Fan interval " + this.fanInterval + "\n";
        return r;
    }
}
