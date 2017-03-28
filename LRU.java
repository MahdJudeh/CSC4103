//Name: Judeh, Mahdi
//Project: PA-2 (Page Replacement Algorithms)
//File: LRU
//Instructor: Feng Chen
//Class: cs4103-sp17
//LogonID: cs410352


import java.util.*;

public class LRU {

    private List<String[]> pageSwaps;
    private List<float[]> pageFrames;
    private int pageFaults = 0;
    private int pageReferences = 0;
    private int swapTime = 0;
    private int writeTime = 0;
    private int cacheSize;

    public LRU(List<String[]> fileInput, int cacheSize) {
        pageSwaps = fileInput;
        this.cacheSize = cacheSize;
        pageFrames = new ArrayList<float[]>();
    }

    public void lruAlgorithim() {
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
                        float[] tempArray = {page, System.currentTimeMillis(), 0};
                        pageFrames.add(tempArray);
                        pageFaults++;
                        swapTime += 5;
                    } else {
                        float[] tempArray = {page, System.currentTimeMillis(), 1};
                        pageFrames.add(tempArray);
                        pageFaults++;
                        swapTime += 5;
                    }
                } else {
                    if (pageSwaps.get(i)[0].equalsIgnoreCase("W")) {
                        float[] tempArray = {page, System.currentTimeMillis(), 1};
                        pageFrames.set(currentIndex, tempArray);
                        pageReferences++;
                    } else {
                        float[] tempArray = {page, System.currentTimeMillis(), pageFrames.get(currentIndex)[2]};
                        pageFrames.set(currentIndex, tempArray);
                        pageReferences++;
                    }
                }
            } //Cache is full so now we do page replacement unless we find the page already
            else {
                //Page Was not found in full memory
                if (!pageFound) {
                    float TempTime = pageFrames.get(0)[1];
                    for (int k = 0; k < pageFrames.size() - 1; k++) {
                        if (TempTime > pageFrames.get(k + 1)[1]) {
                            currentIndex = k + 1;
                            TempTime =  pageFrames.get(k + 1)[1];
                        }
                    }
                    //If the current page we want to add needs to be written
                    if (pageSwaps.get(i)[0].equalsIgnoreCase("W")) {
                        float[] tempArray = {page, System.currentTimeMillis(), 1};
                        if (pageFrames.get(currentIndex)[2] == 1) {
                            writeTime += 10;
                        }
                        pageFaults++;
                        pageFrames.set(currentIndex, tempArray);
                        swapTime += 5;
                    } else {
                        float[] tempArray = {page, System.currentTimeMillis(), 0};
                        if (pageFrames.get(currentIndex)[2] == 1) {
                            writeTime += 10;
                        }
                        pageFaults++;
                        pageFrames.set(currentIndex, tempArray);
                        swapTime += 5;
                    }
                } //Page found in full memory
                else {
                    if (pageSwaps.get(i)[0].equalsIgnoreCase("W")) {
                        float[] tempArray = {page, System.currentTimeMillis(), 1};
                        pageFrames.set(currentIndex, tempArray);
                        pageReferences++;
                    } else {
                        float[] tempArray = {page, System.currentTimeMillis(), pageFrames.get(currentIndex)[2]};
                        pageFrames.set(currentIndex, tempArray);
                        pageReferences++;
                    }
                }
            }
        }
        System.out.printf(" # of page references = %d %n # of page misses = %d %n # of time units for page misses = %d %n # of time units for writing modified page out = %d %n", pageReferences, pageFaults, swapTime + writeTime, writeTime);
    }
}
