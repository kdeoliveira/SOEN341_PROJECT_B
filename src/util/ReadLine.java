package util;

import java.io.*;
import java.util.*;

public class ReadLine implements Closeable, Iterable<String[]> {
    private FileInputStream src;
    private StringBuilder lineArray;
    private Queue<String[]> queueOfLines;
    private int numberOfElemenets;
    private String regex = " ";
    
    public ReadLine(FileInputStream src, int n){
        this.src = src;
        this.lineArray = new StringBuilder();
        this.queueOfLines = new LinkedList<>();
        this.numberOfElemenets = n;
    }

    public ReadLine(File src, int n) throws FileNotFoundException {
        this.src = new FileInputStream(src);
        this.lineArray = new StringBuilder();
        this.queueOfLines = new LinkedList<>();
        this.numberOfElemenets = n;
    }

    public ReadLine(String src, int n) throws IOException {

        try {
            this.src = new FileInputStream(src);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.lineArray = new StringBuilder();
        this.queueOfLines = new LinkedList<>();
        this.numberOfElemenets = n;
    }

    public ReadLine(String src, int n, String regex){
        try {
            this.src = new FileInputStream(src);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.lineArray = new StringBuilder();
        this.queueOfLines = new LinkedList<>();
        this.numberOfElemenets = n;
        this.regex = regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String[] oneline() throws IOException {
        this.lineArray.delete(0, this.lineArray.length());
        this.execute(src.read());
        // String[] temp = this.lineArray.toString().replaceAll("\\s+", " ").stripTrailing().split(this.regex, this.numberOfElemenets);
        List<String> str = new ArrayList<>();
        int ch = this.lineArray.indexOf(";");
        if(ch == -1)
            str.addAll(Arrays.asList(this.lineArray.toString().replaceAll("\\s+", " ").stripTrailing().split(this.regex, this.numberOfElemenets)));
        else{
            str.addAll(Arrays.asList(this.lineArray.substring(0, ch).replaceAll("\\s+", " ").stripTrailing().split(this.regex, this.numberOfElemenets)));
            str.add(this.lineArray.substring(ch, this.lineArray.length()).strip());
        }

        if(str.isEmpty())     return new String[0];
        queueOfLines.add(str.toArray(new String[0]));
        return str.toArray(new String[0]);
    }

    public boolean isEmpty() throws IOException {
        return this.src.available() == 0;
    }


    private void execute(int ch) throws IOException {
        if((char) ch == '\n')   return;

        this.lineArray.append((char) ch);

        if(this.src.available() == 0)   return;
        
        this.execute(this.src.read());
    }

    public Queue<String[]> allLines(){
        
        while(this.iterator().hasNext())
            this.iterator().next();
        return queueOfLines;
    }

    public void close() throws IOException {
        this.src.close();
    }

    public Iterator<String[]> iterator(){
        return new RLIterator(this);
    }

    public class RLIterator implements Iterator<String[]>{
        private ReadLine rl;

        public RLIterator(ReadLine rl){
            this.rl = rl;
        }
        public boolean hasNext(){
            try {
                return rl.src.available() > 0;
            } catch (IOException e) {
                throw new NoSuchElementException();
            }
        }

        public String[] next(){
            if(!this.hasNext()) throw new NoSuchElementException();
            try {
                return rl.oneline();
            } catch (IOException e) {
                throw new NoSuchElementException();
            }
        }
    }
}
