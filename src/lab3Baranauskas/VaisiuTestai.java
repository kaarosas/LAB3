package lab3Baranauskas;

import laborai.studijosktu.MapKTUx;
import laborai.studijosktu.MapKTUOA;
import laborai.studijosktu.Ks;
import java.util.Locale;
import laborai.studijosktu.HashType;


public class VaisiuTestai {


    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        atvaizdzioTestas();
        greitaveikosTestas();
    }
    
    public static void atvaizdzioTestas()
    {
        Vaisius a1 = new Vaisius.Builder().buildRandom();
        Vaisius a2 = new Vaisius.Builder().buildRandom();
        Vaisius a3 = new Vaisius.Builder().buildRandom();
        Vaisius a4 = new Vaisius.Builder().buildRandom();
        Vaisius a5 = new Vaisius.Builder().buildRandom();
        Vaisius a6 = new Vaisius.Builder().buildRandom();
        Vaisius a7 = new Vaisius.Builder().buildRandom();
        
        String[] vaisID = {"ID184", "ID135", "ID167", "ID129", "ID114", "ID124", "ID141", "ID153"};
        int id = 0;
        MapKTUx<String, Vaisius> atvaizdis
                = new MapKTUx(new String(), new Vaisius(), HashType.DIVISION);
        
        Vaisius[] vaisius = {a1, a2, a3, a4, a5, a6, a7};
        for (Vaisius a : vaisius)
        {
            atvaizdis.put(vaisID[id++], a);
        }
        atvaizdis.println("Porų išsidėstymas atvaizdyje pagal raktus");
        Ks.oun("Ar egzistuoja pora atvaizdyje");
        Ks.oun(atvaizdis.contains(vaisID[6]));
        Ks.oun(atvaizdis.contains(vaisID[7]));
        Ks.oun("Pašalinamos poros iš atvaizdžio:" + vaisID[1] + " ir " + vaisID[7]);
        Ks.oun(atvaizdis.remove(vaisID[1]));
        Ks.oun(atvaizdis.remove(vaisID[7]));
        atvaizdis.println("Porų išsidėstymas tvaizdyje pagal raktus");
        Ks.oun("Atliekame porų paieška atvaizdyje:");
        Ks.oun(atvaizdis.get(vaisID[2]));
        Ks.oun(atvaizdis.get(vaisID[7]));
        Ks.oun("Išspausdiname atvaizdžio poras String eilute:");
        atvaizdis.println();
        
        boolean r = atvaizdis.containsValue(a4);
        Ks.oun("Ar egzistuoja tokia reikšmė: " + a4 + "\n" + r);
        Ks.oun("");
        
        atvaizdis.put(vaisID[1], a1);
        atvaizdis.println();
        Ks.oun("---------------------------------------------------");
        Ks.oun("Vidutinis grandinėlės ilgis: " + atvaizdis.averageChainSize());
        Ks.oun("");
        
        Ks.oun("Tuščių elementų kiekis: " + atvaizdis.numberOfEmpties());
        Ks.oun("");
        
        Ks.oun("Ikelia elementą su raktu jei dar nėra atvaizde: " + vaisID[7] +
                "\nGrąžinama reikšmė: " + atvaizdis.putIfAbsent(vaisID[7], a2));
        Ks.oun("");
        atvaizdis.println();
        Ks.oun("");
        
        Ks.oun("Elementas prieš pakeitimą: " + atvaizdis.get(vaisID[0]));
        Ks.oun("");
        Ks.oun("Ar pakeistas elementas nauju? " + atvaizdis.replace(vaisID[0], a1, a7));
        Ks.oun("");
        Ks.oun("Elementas po pakeitimo: " + atvaizdis.get(vaisID[0]));
        
        Ks.oun("---------------------------------------------------");
        
        MapKTUOA atvaizdis2 = new MapKTUOA(16);
        
        atvaizdis2.put(vaisID[0], a1);
        atvaizdis2.put(vaisID[1], a2);
        atvaizdis2.put(vaisID[2], a3);
        atvaizdis2.put(vaisID[3], a4);
        atvaizdis2.put(vaisID[4], a5);
        atvaizdis2.put(vaisID[5], a6);
        atvaizdis2.put(vaisID[6], a7);
        Ks.oun("Atvaizdžio elementų sąrašas:\n" + atvaizdis2.toString());        
        
        Ks.oun("Atvaizdžio elementų kiekis: " + atvaizdis2.size() + "\n");
        
        Ks.oun("Ar atvaizdys tuščias? " + atvaizdis2.isEmpty() + "\n");
        
        Ks.oun("Gauname elementą pagal raktą - " + vaisID[0] + " " + atvaizdis2.get(vaisID[0]) + "\n");
        
        Ks.oun("Išemame elementus iš atvaizdžio:\n" + vaisID[0] + " " + atvaizdis2.remove(vaisID[0]) + "\n" + vaisID[4] + " " + atvaizdis2.remove(vaisID[4]) + "\n \n" + atvaizdis2.toString());
        
        Ks.oun("Ar atvaizdyje yra ką tik išiimtas elementas su ID " + vaisID[4] + " - " + atvaizdis2.contains(vaisID[4]));
    
    }
    
    private static void greitaveikosTestas()
    {
        System.out.println("Greitaveikos tyrimas:\n");
        GreitaveikosTyrimas gt = new GreitaveikosTyrimas();
        
        new Thread(() -> gt.pradetiTyrima(),
                "Greitaveikos_tyrimo_gija").start();
        try {
            String result;
            while (!(result = gt.getResultsLogger().take())
                    .equals(GreitaveikosTyrimas.FINISH_COMMAND)) {                
                System.out.println(result);
                gt.getSemaphore().release();
            }           
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        gt.getSemaphore().release();
    }
    
}
