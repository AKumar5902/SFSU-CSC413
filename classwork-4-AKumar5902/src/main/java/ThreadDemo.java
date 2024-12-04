public class ThreadDemo extends Thread {
    final char charToPrint;

    final int times;

    public ThreadDemo(char charToPrint, int times) {
        super();
        this.charToPrint = charToPrint;
        this.times = times;
    }

    public static void main(String[] args) {
        var threadA = new ThreadDemo('a', 1000);
        var threadB= new ThreadDemo('b', 1000);

        threadA.start();
        threadB.start();

    }

    @Override
    public void run(){
        for(int i =0; i< times; i++){
            System.out.print(charToPrint);
        }
    }
}
