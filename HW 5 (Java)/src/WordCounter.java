import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Semaphore;

public class WordCounter {

    // The following are the ONLY variables we will modify for grading.
    // The rest of your code must run with no changes.
    public static final Path FOLDER_OF_TEXT_FILES = Paths.get("/Users/haksoo/Desktop/2020 Fall/CSE 216/HW 5 (Java)/src/input_text"); // path to the folder where input text files are located
    public static final Path WORD_COUNT_TABLE_FILE = Paths.get("/Users/haksoo/Desktop/2020 Fall/CSE 216/HW 5 (Java)/src/output_text/plainText.txt"); // path to the output plain-text (.txt) file
    public static final int NUMBER_OF_THREADS = 1;                // max. number of threads to spawn

    public static class ReadFileRunnable extends Thread {
        String filePath;
        TreeMap<String, Integer> wordsMap;
        Semaphore sem;

        public ReadFileRunnable(Semaphore sem, String filePath) {
            this.filePath = filePath;
            wordsMap = new TreeMap<String, Integer>();
            this.sem = sem;
        }

        public TreeMap<String,Integer> getWordsMap(){ return wordsMap; }

        public void run() {

            try {
                sem.acquire();
                String wordsInLine = new String(Files.readAllBytes(Paths.get(filePath)));
                wordsInLine = wordsInLine.replace("\r", " ");
                wordsInLine = wordsInLine.replace("\n", " ");
                wordsInLine = wordsInLine.replaceAll("[^a-zA-Z ]", "").toLowerCase().trim();
                String[] eachWords = wordsInLine.split("\\s+");

                if (! wordsInLine.equals("")) {
                    for (String eachWord : eachWords) {
                        if (wordsMap.get(eachWord) == null)
                            wordsMap.put(eachWord, 1);
                        else
                            wordsMap.put(eachWord, wordsMap.get(eachWord) + 1);
                    }
                }

            } catch (IOException | InterruptedException e) {
                System.out.println("Not succeeded ");
            }
            sem.release();

        }
    }

    public static void main(String... args) {
        if (NUMBER_OF_THREADS <= 0)
            throw new IllegalArgumentException("There must be at least one thread to spawn");
        Semaphore sem = new Semaphore(NUMBER_OF_THREADS);
        File Folder = FOLDER_OF_TEXT_FILES.toFile();
        File[] listOfFiles = Folder.listFiles();
        ArrayList<File> listOfTextFiles = new ArrayList<>();
        ArrayList<ReadFileRunnable> threadList = new ArrayList<>();
        int longestFileNamePlusOne = 0;
        int longestWordLenPlusOne = 0;


        try {
            for (File listOfFile : listOfFiles) {
                if (listOfFile.getName().contains(".txt"))
                    listOfTextFiles.add(listOfFile);
            }
        }
        catch(NullPointerException e){
            System.out.println("Path is not valid");
        }

        Collections.sort(listOfTextFiles);

            for (File listOfTextFile : listOfTextFiles) {
                    if (longestFileNamePlusOne < listOfTextFile.getName().length())
                        longestFileNamePlusOne = listOfTextFile.getName().length();
                    ReadFileRunnable a = new ReadFileRunnable(sem, listOfTextFile.getPath());
                    threadList.add(a);
                    a.start();
            }

        List<Map<String, Integer>> wordCounts =new ArrayList<>();

        for (ReadFileRunnable thread : threadList) {
            try {
                thread.join();
                wordCounts.add(thread.getWordsMap());
            } catch (Exception ex) {
                System.out.println("Not succeeded ");
            }
        }

        TreeMap<String, Integer> totalMap = new TreeMap<String, Integer>();

        for (Map<String, Integer> wordCount : wordCounts) {
            for (String word : wordCount.keySet()) {
                if (totalMap.get(word) == null) {
                    totalMap.put(word, wordCount.get(word));
                } else {
                    totalMap.replace(word, totalMap.get(word) + wordCount.get(word));
                }
                if (longestWordLenPlusOne < word.length()) {
                    longestWordLenPlusOne = word.length();
                }
            }
        }

        longestWordLenPlusOne++;
        longestFileNamePlusOne++;

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(WORD_COUNT_TABLE_FILE.toFile()));
            StringBuilder firstLine = new StringBuilder();
            firstLine.append(" ".repeat(Math.max(0, longestWordLenPlusOne)));
            for (File listOfTextFile : listOfTextFiles) {
                String fileNameWithoutText = listOfTextFile.getName().replace(".txt", "");
                firstLine.append(fileNameWithoutText);
                if (fileNameWithoutText.length() < longestFileNamePlusOne) {
                    for (int j = 0; j < longestFileNamePlusOne - fileNameWithoutText.length(); j++) {
                        firstLine.append(" ");
                    }
                }
            }
            if (! totalMap.isEmpty()) {
                firstLine.append("total");
                writer.write(firstLine.toString() + "\n");
            }

            for (String word : totalMap.keySet()) {
                StringBuilder temp = new StringBuilder();
                temp.append(word);
                if (word.length() < longestWordLenPlusOne) {
                    temp.append(" ".repeat(longestWordLenPlusOne - word.length()));
                }
                for (Map<String, Integer> wordCount : wordCounts) {
                    if (wordCount.get(word) == null) {
                        temp.append("0");
                        temp.append(" ".repeat(Math.max(0,longestFileNamePlusOne - 1)));
                    } else {
                        temp.append(wordCount.get(word).toString());
                        temp.append(" ".repeat(Math.max(0, longestFileNamePlusOne - wordCount.get(word).toString().length())));
                    }
                }
                temp.append(totalMap.get(word).toString()).append("\n");
                writer.write(temp.toString());
            }
            writer.close();
        }
        catch(Exception ex){
            System.out.println("Not succeeded ");
        }
        System.out.println("SUCCEDDED");

    }
}

