
package lab3Baranauskas;

import laborai.gui.MyException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

public class VaisiuGamyba
{
    private static final String ID = "TA";
    private static int serNr = 10000;
    
    private Vaisius[] vaisiai;
    private String[] raktai;
    private int kiekis = 0, idKiekis = 0;
    
    public static Vaisius[] gamintiVaisius(int kiekis)
    {
        Vaisius[] vaisiai = IntStream.range(0, kiekis)
                .mapToObj(i -> new Vaisius.Builder().buildRandom())
                .toArray(Vaisius[]::new);
        Collections.shuffle(Arrays.asList(vaisiai));
        return vaisiai;
    }
    
    public static String[] gamintiVaisID(int kiekis)
    {
        String[] raktai = IntStream.range(0, kiekis)
                .mapToObj(i -> ID + (serNr++))
                .toArray(String[]::new);
        Collections.shuffle(Arrays.asList(raktai));
        return raktai;
    }
    
    public Vaisius[] gamintiIrParduotiVaisius(int aibesDydis,
            int aibesImtis) throws MyException
    {
        if (aibesImtis > aibesDydis)
        {
            aibesImtis = aibesDydis;
        }
        vaisiai = gamintiVaisius(aibesDydis);
        raktai = gamintiVaisID(aibesDydis);
        this.kiekis = aibesImtis;
        return Arrays.copyOf(vaisiai, aibesImtis);
    }
    
    public Vaisius parduotiVaisiu()
    {
        if (vaisiai == null)
        {
            throw new MyException("Fruits not Generated");
        }
        if (kiekis < vaisiai.length)
        {
            return vaisiai[kiekis++];
        } else {
            throw new MyException("All Set Stored To Map");
        }
    }
    
    public String gautiIsBazesVaisiuID()
    {
        if (raktai == null)
        {
            throw new MyException("Fruit IDs Not Generated");
        }
        if (idKiekis >= raktai.length)
        {
            idKiekis = 0;
        }
        return raktai[idKiekis++];
    }
    
}
