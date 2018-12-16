package lab3Baranauskas;

import com.sun.org.apache.xml.internal.serializer.Encodings;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import laborai.studijosktu.HashType;
import laborai.studijosktu.MapKTU;
import laborai.studijosktu.MapKTUx;
import java.util.HashSet;
import laborai.studijosktu.MapKTUOA;
import laborai.gui.MyException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

public class GreitaveikosTyrimas {
    
    public static final String FINISH_COMMAND = "finishCommand";
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("laborai.gui.messages");

    private final BlockingQueue resultsLogger = new SynchronousQueue();
    private final Semaphore semaphore = new Semaphore(-1);
    private final Timekeeper tk;

    private final String[] TYRIMU_VARDAI = {"add0.75", "add0.25", "rem0.75", "rem0.25", "get0.75", "get0.25", "MapCon", "MapCon", "MapRem", "HasRem"};
    private final int[] TIRIAMI_KIEKIAI = {10000, 20000, 40000, 100000};
    
    private final MapKTUx<String, Vaisius> vaisiuAtvaizdis
            = new MapKTUx(new String(), new Vaisius(), 10, 0.75f, HashType.DIVISION);
    private final MapKTUx<String, Vaisius> vaisiuAtvaizdis2
            = new MapKTUx(new String(), new Vaisius(), 10, 0.25f, HashType.DIVISION);
    private final MapKTU<String, String> greit1
            = new MapKTU();
    private final MapKTU<String, String> greit2
            = new MapKTU();
    private final MapKTU<String, String> greit3
            = new MapKTU();
    private final HashSet<String> greit4
            = new HashSet<>();
    private final Queue<String> chainsSizes = new LinkedList<>();
    
    public GreitaveikosTyrimas()
    {
        semaphore.release();
        tk = new Timekeeper(TIRIAMI_KIEKIAI, resultsLogger, semaphore);
    }
    
    public void pradetiTyrima()
    {
        try {
            SisteminisTyrimas();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public void SisteminisTyrimas() throws InterruptedException, IOException
    {
        try {
            chainsSizes.add(MESSAGES.getString("msg4"));
            chainsSizes.add("   kiekis      " + TYRIMU_VARDAI[0] + "    " + TYRIMU_VARDAI[1]);
            for (int k : TIRIAMI_KIEKIAI)
            {

                List<String> lines = Files.readAllLines(Paths.get("zodynas.txt"));
                
                String[] line = lines.toArray(new String[lines.size()]);
                
                for (int i = 0; i < line.length; i++)
                {
                    greit1.put(line[i], line[i]);
                    greit2.put(line[i], line[i]);
                    greit3.put(line[i], line[i]);
                    greit4.add(line[i]);
                }

                tk.startAfterPause();
                tk.start();
          
                for (String s : line)
                {
                    greit1.contains(s);
                }
                tk.finish(TYRIMU_VARDAI[6]);
                
                for (String s : line)
                {
                    greit2.contains(s);
                }
                tk.finish(TYRIMU_VARDAI[7]);
                
                for (String s : line)
                {
                    greit3.remove(s);
                }
                tk.finish(TYRIMU_VARDAI[8]);
                
                for (String s : line)
                {
                    greit4.remove(s);
                }
                tk.finish(TYRIMU_VARDAI[9]);
                             
                tk.seriesFinish();
            }
            
            StringBuilder sb = new StringBuilder();
            chainsSizes.stream().forEach(p -> sb.append(p).append(System.lineSeparator()));
            tk.logResult(sb.toString());
            tk.logResult(FINISH_COMMAND);          
        } catch (MyException e) {
            tk.logResult(e.getMessage());
        }
    }
    
    
    public BlockingQueue<String> getResultsLogger()
    {
        return resultsLogger;
    }
    
    public Semaphore getSemaphore()
    {
        return semaphore;
    }
    
}
