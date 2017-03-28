//Name: Judeh, Mahdi
//Project: PA-2 (Page Replacement Algorithms)
//File: CLOCK
//Instructor: Feng Chen
//Class: cs4103-sp17
//LogonID: cs410352

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mahdi
 */
public class CLOCK {

    private List<String[]> pageSwaps;
    private List<float[]> pageFrames;
    private int pageFaults = 0;
    private int pageReferences = 0;
    private int swapTime = 0;
    private int writeTime = 0;
    private int cacheSize;

    public CLOCK(List<String[]> fileInput, int cacheSize) {
        pageSwaps = fileInput;
        this.cacheSize = cacheSize;
        pageFrames = new ArrayList<float[]>();
    }

    public void clockAlgorithim() {
        int currentHead = 0;
        boolean pageReplaced = false;
        for (int i = 0; i < pageSwaps.size(); i++) {
            boolean pageFound = false;
            float page = Float.parseFloat(pageSwaps.get(i)[1]);
            int currentIndex = 0;
            for (int j = 0; j < pageFrames.size(); j++) {
                if (pageFrames.get(j)[0] == page) {
                    pageFound = true;
                    currentIndex = j;
                }
            }

            //Adds page to memory if number of pages in memory is less than cache size
            if (pageFrames.size() < cacheSize) {
                if (!pageFound) {
                    if (pageSwaps.get(i)[0].equalsIgnoreCase("R")) {
                        float[] tempArray = {page, 0, 0};
                        pageFrames.add(tempArray);
                        pageFaults++;
                        swapTime += 5;
                    } else {
                        float[] tempArray = {page, 0, 1};
                        pageFrames.add(tempArray);
                        pageFaults++;
                        swapTime += 5;
                    }
                } else {
                    if (pageSwaps.get(i)[0].equalsIgnoreCase("W")) {
                        float[] tempArray = {page, 1, 1};
                        pageFrames.set(currentIndex, tempArray);
                        pageReferences++;
                    } else {
                        float[] tempArray = {page, 1, pageFrames.get(currentIndex)[2]};
                        pageFrames.set(currentIndex, tempArray);
                        pageReferences++;
                    }
                }
            } //Cache is full so now we do page replacement unless we find the page already
            else {
                //Page Was not found in full memory
                if (!pageFound) {
                    while (!pageReplaced) {
                        //If the current page we want to add needs to be written
                        //Ghetto solution for clock algorithim so I don't have to write a class for a circular linked list.
                        if (pageFrames.get(currentHead)[1] == 0) {
                            if (pageSwaps.get(i)[0].equalsIgnoreCase("W")) {
                                float[] tempArray = {page, 1, 1};
                                if (pageFrames.get(currentHead)[2] == 1) {
                                    writeTime += 10;
                                }
                                pageFaults++;
                                pageFrames.set(currentHead, tempArray);
                                swapTime += 5;
                                currentHead++;
                                if (currentHead >= cacheSize) {
                                    currentHead = 0;
                                }
                                pageReplaced = true;
//                                System.out.println("replaced");
                            }
                            else{
                                float[] tempArray = {page, 1, pageFrames.get(currentHead)[2]};
                                pageFaults++;
                                pageFrames.set(currentHead, tempArray);
                                swapTime += 5;
                                currentHead++;
                                if (currentHead >= cacheSize) {
                                    currentHead = 0;
                                }
                                pageReplaced = true;
//                                System.out.println("replaced");
                            }
                        } else {
                            float[] tempArray = {pageFrames.get(currentHead)[0], 0, pageFrames.get(currentHead)[2]};
                            pageFrames.set(currentHead, tempArray);
                            currentHead++;
                            if (currentHead >= cacheSize) {
                                currentHead = 0;
                            }
//                            System.out.println("not replaced");
                        }
                    }
                    pageReplaced = false;
                } //Page found in full memory
                else {
                    if (pageSwaps.get(i)[0].equalsIgnoreCase("W")) {
                        float[] tempArray = {page, 1, 1};
                        pageFrames.set(currentIndex, tempArray);
                        pageReferences++;
                    } else {
                        float[] tempArray = {page, 1, pageFrames.get(currentIndex)[2]};
                        pageFrames.set(currentIndex, tempArray);
                        pageReferences++;
                    }
                }
            }
        }
        System.out.printf(" # of page references = %d %n # of page misses = %d %n # of time units for page misses = %d %n # of time units for writing modified page out = %d %n", pageReferences, pageFaults, swapTime + writeTime, writeTime);
    }
}
