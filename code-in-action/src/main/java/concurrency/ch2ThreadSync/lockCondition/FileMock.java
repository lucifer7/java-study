package concurrency.ch2ThreadSync.lockCondition;

/**
 * Created by lucifer on 2016-7-31.
 */
public class FileMock {
    private String content[];
    private int index;

    public FileMock(int size, int length) {
        content = new String[size];
        for (int i = 0; i < size; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < length; j++) {
                int indice = (int) (Math.random() * 255);
                sb.append((char) indice);
            }
            content[i] = sb.toString();
        }
        index = 0;
    }

    public boolean hasMoreLines() {
        return index < content.length;
    }

    public String getLine() {
        if (this.hasMoreLines()) {
            System.out.println("Mock: " + (content.length - index));
            return content[index++];
        }
        return null;
    }
}
