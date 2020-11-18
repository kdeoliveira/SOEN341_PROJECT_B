package util;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class ReadLine implements Closeable, Iterable<String[]> {
    private FileInputStream src;
    private StringBuilder lineArray;
    private Queue<String[]> queueOfLines;
    private int numberOfElemenets;

    public ReadLine(FileInputStream src, int n){
        this.src = src;
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

    public String[] oneline() throws IOException {
        this.lineArray.delete(0, this.lineArray.length());
        this.execute(src.read());
        String[] temp = this.lineArray.toString().strip().split(" ", this.numberOfElemenets);

        if(temp.length < 1)     return new String[0];
        queueOfLines.add(temp);
        return temp;
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



    public static void main(String[] args){

        try(ReadLine rl = new ReadLine("input.asm", 4)){

            for(String[] x : rl){
                System.out.println(Arrays.toString(x));
            }
            
            // for(String[] x : rl.allLines()){
            //     System.out.println(Arrays.toString(x));
            // }
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
