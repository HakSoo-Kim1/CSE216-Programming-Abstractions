/**
 * WordCounter.java
 * @author JeongYoon Lee
 * @version java 8
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * A Class read txt files from {@link WordCounter1#FOLDER_OF_TEXT_FILES} and write txt file in {@link WordCounter1#WORD_COUNT_TABLE_FILE} about the total number of these words.
 * We can set max number of threads to spawn through {@link WordCounter1#NUMBER_OF_THREADS}
 *
 */
public class WordCounter1 {
    // The following are the ONLY variables we will modify for grading.
    // The rest of your code must run with no changes.
    public static final Path FOLDER_OF_TEXT_FILES  = Paths.get("/Users/haksoo/Desktop/2020 Fall/CSE 216/HW 5 (Java)/src/input_text"); // path to the folder where input text files are located
    public static final Path WORD_COUNT_TABLE_FILE = Paths.get("/Users/haksoo/Desktop/2020 Fall/CSE 216/HW 5 (Java)/src/output_text/plainText.txt"); // path to the output plain-text (.txt) file
    public static final int  NUMBER_OF_THREADS     = 2;                // max. number of threads to spawn

    private ArrayList<HashMap<String,Integer>> list = new ArrayList();

    /**
     * A Class implements {@link Callable} to get return value after doing thread.
     * It will run {@link WordCounter1#read(Path)} to get Hashmap that has {word, each count number}.
     *
     */
    public class RunThread implements Callable{
        private Path filePath;
        public RunThread(Path filePath){
            this.filePath = filePath;
        }

        @Override
        public Object call() {
            HashMap m  = null;
            m = read(filePath);

            /*Print Current Thread*/
            //String threadName = Thread.currentThread().getName();
            //System.out.println(threadName);
            return m;
        }
    }

    /**
     *
     * @param s String in a single line consisting of lowercase letters and " " from reading each file.
     * @return HashMap which contains information about word frequency.
     */
    public HashMap frequency_check(String s){

        String[] splited = s.split("\\s+");
        HashMap<String,Integer> order = new HashMap<String,Integer>();
        for(int i=0; i< splited.length;i++){
            if(order.containsKey(splited[i]) ==false){
                order.put(splited[i],1);
            }
            else{
                int n = order.get(splited[i]);
                order.replace(splited[i],n+1);
            }
        }
        return order;
    }

    /**
     * It makes ArrayList<String> which has words from all files in {@link WordCounter1#FOLDER_OF_TEXT_FILES} without overlapping.
     *
     * @param all List with HashMap which contains {word , frequency of that word}
     * @return ar that has all words for all files in {@link WordCounter1#FOLDER_OF_TEXT_FILES} without overlapping.
     */
    public ArrayList<String> total(List<HashMap<String, Integer>> all){
        ArrayList<String> ar = new ArrayList<>();
        for(int i=0;i<all.size();i++){
            HashMap<String,Integer> h = all.get(i);
            for(String s : h.keySet()){
                if(!ar.contains(s)){
                    ar.add(s);
                }
            }
        }
        Collections.sort(ar);
        return ar;
    }

    /**
     * Matching the word and frequency from all files.
     *
     * @param total_num Which has information about word and frequency from each file.
     * @param total Which has information about all words from all files in {@link WordCounter1#FOLDER_OF_TEXT_FILES} without overlapping.
     * @return a Which has information about all words from all files and frequency about every file.
     */
    public ArrayList<Integer> total_n(HashMap<String, Integer> total_num, ArrayList<String> total){
        ArrayList<Integer> a = new ArrayList<>();

        for(int i =0; i<total.size() ; i++){
            if(total_num.containsKey(total.get(i))){
                a.add(total_num.get(total.get(i)));
            }
            else{
                a.add(0);
            }
        }
        return a;
    }

    /**
     * Find the longest Integer among all words in all files from {@link WordCounter1#FOLDER_OF_TEXT_FILES}.
     *
     * @param s Which has information about all words from all files in {@link WordCounter1#FOLDER_OF_TEXT_FILES} wothout overlapping
     * @return longest Return longest word's lengths.
     */
    public int longest(ArrayList<String> s){
        int longest = 0;
        for(int i=0; i<s.size();i++){
            if(longest < s.get(i).length()){
                longest = s.get(i).length();
            }
        }
        return longest;
    }

    /**
     * This will read each file and turned to a String that only contains lowercase letters and " ".
     * For Windows OS, "\n" is treated as "\r\n", and for Mac OS, "\n" is treated as "\n". So I turned both cases to " ".
     *
     * @param filePath Which has each file's path.
     * @return m Which is HashMap that has information about words and every word's frequency from each file.
     * @throws IOException When there's error while reading Files.
     */
    public HashMap read(Path filePath) {
        Path file_list = filePath;
        String ab = null;
        try {
            ab = new String(Files.readAllBytes(file_list));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ab = ab.toLowerCase();
        ab = ab.replaceAll("[^a-zA-Z\\s]", ""); //Keep " "
        ab = ab.replace("\r\n", " "); //For Windows
        ab = ab.replaceAll("[\\n]", " "); //For Mac
        HashMap m = frequency_check(ab);
        return m;
    }

    /**
     * It will make formal form of String from all files to use for the final txt file.
     *
     * @param file_list Which has paths from all files in the dir.
     * @return final_string Which is String to use for write the final txt file.
     * @throws NullPointerException If {@link WordCounter1#list} is null.
     */
    public String making(List<Path> file_list){
        WordCounter1 r = new WordCounter1();
        ArrayList<ArrayList<Integer>> print_list = new ArrayList<ArrayList<Integer>>();
        ArrayList<String> word_list = r.total(list);
        if(list == null){
            throw new NullPointerException("list is null");
        }

        assert(file_list.size() == list.size());
        for(int i=0; i<file_list.size();i++){
            HashMap<String,Integer> h = (HashMap<String, Integer>) list.get(i);
            ArrayList<Integer> total_num = r.total_n(h , word_list);
            print_list.add(total_num);
        }

        ArrayList<Integer> total_print = new ArrayList<Integer>();
        for(int i=0; i<word_list.size() ; i++){
            int sum = 0;
            for(int j = 0; j< file_list.size();j++){
                sum = sum+ print_list.get(j).get(i);
            }
            total_print.add(sum);
        }
        print_list.add(total_print);


        int longest = r.longest(word_list)+1;
        String final_string = String.format("%-" + longest + "s"," ");
        String filename = "";

        int max_filename_length = 0;
        for(int i =0; i< file_list.size();i++){
            filename = String.valueOf(file_list.get(i).getFileName());
            filename = filename.substring(0,filename.length()-4);
            max_filename_length = Math.max(filename.length()-1 , max_filename_length);
        }
        max_filename_length = max_filename_length +2;
        for(int j =0; j< file_list.size();j++){
            filename = String.valueOf(file_list.get(j).getFileName());
            filename = filename.substring(0,filename.length()-4);
            String filename1 = String.format("%-" + Math.max(longest, max_filename_length) + "s",filename);

            final_string = final_string.concat(filename1);
        }
        String total_string = String.format("%-" + longest + "s","total");
        final_string = final_string.concat(total_string);
        final_string = final_string.concat("\n");


        for(int i=0; i<word_list.size(); i++){
            String word = String.format("%-" + longest +"s",word_list.get(i));

            for(int j =0; j< print_list.size();j++){
                String add_space = String.format("%-" + Math.max(longest, max_filename_length) + "s",print_list.get(j).get(i));
                word = word.concat(add_space);
            }
            word = word.concat("\n");
            final_string = final_string.concat(word);
        }
        return final_string;
    }

    /**
     * This makes ThreadPool to make {@link WordCounter1#NUMBER_OF_THREADS} threads.
     *
     * @param file_list @param file_list Which has paths from all files in the dir.
     * @throws IllegalArgumentException If {@link WordCounter1#NUMBER_OF_THREADS} < 0.
     */
    public void makeThread(List<Path> file_list) {
        ExecutorService maxThreadPool;
        if(NUMBER_OF_THREADS < 0){
            throw new IllegalArgumentException("NUMBER_OF_THREADS cannot have negative number.");
        }
        maxThreadPool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        Future<?>[] f = new Future[file_list.size()];
        for(int i = 0 ; i<file_list.size(); i++){
            f[i] = maxThreadPool.submit(new RunThread(file_list.get(i)));
        }
        for(int i = 0 ; i<file_list.size(); i++){
            HashMap m = null;
            try {
                m = (HashMap) f[i].get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            list.add(m);
        }
        maxThreadPool.shutdown();
    }

    public void setList(ArrayList<HashMap<String,Integer>> list){
        this.list = list;
    }

    public static void main(String[] args) throws IOException,  Exception {
        long start = System.nanoTime();
        List<Path> file_list = Files.list(FOLDER_OF_TEXT_FILES).filter(Files :: isRegularFile).filter(file -> file.toString().endsWith(".txt")).collect(Collectors.toList());
        if(file_list.size() == 0){
            throw new IOException("There is no readable txt file in this folder.");
        }
        WordCounter1 r = new WordCounter1();

        /*If NUMBER_OF_THREADS is 0 or 1, the program would run just with main thread.*/
        if(NUMBER_OF_THREADS == 0 || NUMBER_OF_THREADS == 1){
            ArrayList<HashMap<String,Integer>> list_1 = new ArrayList<>();
            HashMap m = null;
            for(int i = 0 ; i<file_list.size(); i++){
                m = r.read(file_list.get(i));
                list_1.add(m);
            }
            r.setList(list_1);

        }
        else{
            r.makeThread(file_list);
        }

        /*Print Current Thread*/
        //String threadName = Thread.currentThread().getName();
        //System.out.println(threadName);

        String final_string = r.making(file_list);

        try{
            BufferedWriter wr = new BufferedWriter(new FileWriter(WORD_COUNT_TABLE_FILE.toFile()));
            wr.write(final_string);
            wr.close();
        }
        catch(Exception e){
            e.getStackTrace();
        }

        long end = System.nanoTime();
        System.out.printf("time: %6d ms%n", end - start);
    }

}