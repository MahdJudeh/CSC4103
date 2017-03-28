//Name: Judeh, Mahdi
//Project: PA-2 (Page Replacement Algorithms)
//File: PageReplacementAlgorithim
//Instructor: Feng Chen
//Class: cs4103-sp17
//LogonID: cs410352

/**
 *
 * @author Mahdi
 */
import java.io.*;
import java.util.*;

public class PageReplacementAlgorithim {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        Scanner in = new Scanner(new File(args[2]));
        List<String[]> fileInput = new ArrayList<String[]>();
        while (in.hasNextLine()) {
            String[] parseStringToArray = new String[2];
            parseStringToArray = (in.nextLine()).split(" ");
            fileInput.add(parseStringToArray);
        }
        if (args[0].equalsIgnoreCase("clock")) {
            CLOCK clock = new CLOCK(fileInput, Integer.parseInt(args[1]));
            clock.clockAlgorithim();
        } else if (args[0].equalsIgnoreCase("lru")) {
            LRU lru = new LRU(fileInput, Integer.parseInt(args[1]));
            lru.lruAlgorithim();
        }
        else{
            System.out.println("you outputted a non-implemented algorithim");
        }
    }

}
