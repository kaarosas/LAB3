package lab3Baranauskas;

import laborai.studijosktu.KTUable;
import laborai.studijosktu.Ks;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Vaisius implements KTUable{

    private static final double minKaina = 0.10;
    private static final double maxKaina = 10.00;
    private String spalva = "";
    private String pavadinimas = "";
    private String barKodNum = "";
    private double kaina = 0.0;
    private double svoriskg = 0.0;
    private double svorisg = svoriskg * 1000;
    private int kiekis = 0;
    
    public Vaisius() { }
    
    public Vaisius(String spalva, String pav, String kod,
            double kaina, double svoris, int kiekis) 
    {
        this.spalva = spalva;
        this.pavadinimas = pav;
        this.barKodNum = kod;
        this.kaina = kaina;
        this.svoriskg = svoris;
        this.kiekis = kiekis;
        validate();
    }
    
    public Vaisius(String dataString)
    {
        this.parse(dataString);
    }

    public Vaisius(Builder builder) {
        this.spalva = builder.spalva;
        this.pavadinimas = builder.pavadinimas;
        this.barKodNum = builder.barKodNum;
        this.kaina = builder.kaina;
        this.svoriskg = builder.svoriskg;
        this.kiekis = builder.kiekis;
        validate();
    }
    

    @Override
    public Vaisius create(String dataString) {
       return new Vaisius(dataString);
    }

    @Override
    public String validate() {
        String klaidosTipas = "";
        if (kaina < minKaina || kaina > maxKaina)
        {
            klaidosTipas = "Kaina už leistinų ribų [" + minKaina
                    + ":" + maxKaina + "]";
        }
        return klaidosTipas;
    }

    @Override
    public void parse(String dataString) {
        try {
            Scanner ed = new Scanner(dataString);
            spalva = ed.next();
            pavadinimas = ed.next();
            barKodNum = ed.next();
            kaina = ed.nextDouble();
            svoriskg = ed.nextDouble();
            kiekis = ed.nextInt();
            validate();
        } catch (InputMismatchException e)
        {
            Ks.ern("Blogas duomenų formatas apie vaisiu -> " + dataString);
        } catch (NoSuchElementException e) {
            Ks.ern("Trūksta duomenų apie Vaisiu -> " + dataString);
        }
    }
    
    @Override
    public String toString() 
    {
        return pavadinimas + " | " + spalva + " | " + barKodNum + " | " + 
                String.format("%.2f", kaina) + " | " + 
                String.format("%.2f", svoriskg) + " | " + kiekis;
    }
    
    public String getSpalva() 
    {
        return spalva;
    }
    
    public String getPav()
    {
        return pavadinimas;
    }
    
    public String getBarKod()
    {
        return barKodNum;
    }
    
    public void setKaina(double kaina)
    {
        this.kaina = kaina;
    }
    
    public double getKaina()
    {
        return kaina;
    }
    
    public void setSvoris(double svoris)
    {
        this.svoriskg = svoris;
    }
    
    public double getSvoris()
    {
        return svoriskg;
    }
    
    public int getKiekis()
    {
        return kiekis;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Vaisius other = (Vaisius) obj;
        if (Objects.equals(this.barKodNum, this.pavadinimas))
        {
            return false;
        }
        if (Objects.equals(this.spalva, this.svoriskg))
        {
            return false;
        }
        if (this.kiekis != other.kiekis)
        {
            return false;
        }
        if (Double.doubleToLongBits(this.kaina) !=
                Double.doubleToLongBits(other.kaina))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode()         
    {
        int hash = 7;
        hash = 89 * hash + (this.pavadinimas != null ? this.pavadinimas.hashCode() : 0);
        hash = 89 * hash + (this.spalva != null ? this.spalva.hashCode() : 0);
        hash = 89 * hash + this.kiekis;
        hash = 89 * hash + (this.barKodNum != null ? this.barKodNum.hashCode() : 0);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.kaina)
                ^ (Double.doubleToLongBits(this.kaina) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.svoriskg)
                ^ (Double.doubleToLongBits(this.svoriskg) >>> 32));
        return hash;
    }
    

    
    public static Comparator<Vaisius> pagalPavadinima = (Vaisius a1, Vaisius a2) -> {
        return a1.pavadinimas.compareTo(a2.pavadinimas);
    };
    
    public static Comparator<Vaisius> pagalKaina = (Vaisius a1, Vaisius a2) -> {
        if (a1.kaina < a2.kaina) {
            return -1;
        }
        if (a1.kaina > a2.kaina) {
            return +1;
        }
        return 0;
    };
    
    public static Comparator<Vaisius> pagalSvoriKaina = (Vaisius a1, Vaisius a2) -> {
        if (a1.svoriskg > a2.svoriskg) {
            return +1;
        }
        if (a1.svoriskg < a2.svoriskg) {
            return -1;
        }
        if (a1.kaina > a2.kaina) {
            return +1;
        }
        if (a1.kaina < a2.kaina) {
            return -1;
        }
        return 0;
    };
    
    public static class Builder
    {
            private final static Random RANDOM = new Random(1949);
            private final static String[][] PavadinimaiSpalvos = {
                {"Žalia", "Obuolys", "Vynuogė", "Avokadas", "Kivis"},
                {"Oranžinė", "Apelsinas", "Persikas", "Nektarinas", "Mangas"},
                {"Geltona", "Ananasas", "Bananas", "Greipfrutas", "Citrina"},
                {"Raudona", "Obuolys", "Granatas", "Braškė", "Vyšnia"}             
            };

        private String spalva = "";
        private String pavadinimas = "";
        private String barKodNum = "";
        private double kaina = 0.0;
        private double svoriskg = 0.0;
        private double svorisg = svoriskg * 1000;
        private int kiekis = 0;

        public Vaisius build() 
        {
            return new Vaisius(this);
        }

        public Vaisius buildRandom() 
        {
            int spal = RANDOM.nextInt(PavadinimaiSpalvos.length);
            int pav = RANDOM.nextInt(PavadinimaiSpalvos[spal].length - 1) + 1;
            return new Vaisius(PavadinimaiSpalvos[spal][0],
                    PavadinimaiSpalvos[spal][pav],
                    "" + RANDOM.nextInt(99999),
                    0.10 + RANDOM.nextDouble() * 990,
                    RANDOM.nextDouble() * 1000,
                    RANDOM.nextInt(100));            
        }

        public Builder spalva(String spalva)
        {
            this.spalva = spalva;
            return this;
        }
        public Builder Pavadinimas(String pav)
        {
            this.pavadinimas = pav;
            return this;
        }
        public Builder BarKodNum(String BarKod)
        {
            this.barKodNum = BarKod;
            return this;
        }
        public Builder kaina(double kaina)
        {
            this.kaina = kaina;
            return this;
        }
        public Builder svoris(double svoris)
        {
            this.svoriskg = svoris;
            return this;
        }
        public Builder kiekis(int kiekis)
        {
            this.kiekis = kiekis;
            return this;
        }       
    }   
}
