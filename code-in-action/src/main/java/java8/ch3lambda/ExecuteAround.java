package java8.ch3lambda;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Usage: <b> Execute Around Pattern: extract process from handling resources </b>
 *
 * @author lucifer
 *         Date 2017-4-11
 *         Device Aurora R5
 */
public class ExecuteAround {
    public static void main(String[] args) throws IOException {
        String result = processFileLimited();
        System.out.println(result);

        System.out.println(" Surrounding Execution by lambda: ");

        String oneLine = processFile((BufferedReader b) -> b.readLine());
        System.out.println(oneLine);

        String twoLine = processFile((BufferedReader b) -> b.readLine() + b.readLine());
        System.out.println(twoLine);
    }

    static String processFileLimited() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream("/temp/file.txt"), Charset.forName("UTF8")))) {
            return br.readLine();
        }
    }

    static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("/temp/file.txt"))) {
            return p.process(br);
        }
    }

    @FunctionalInterface
    interface BufferedReaderProcessor {
        String process(BufferedReader b) throws IOException;
    }
}
