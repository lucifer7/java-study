package util.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 *         Date 2017-3-15
 *         Device Aurora R5
 */
public class BufferedInputFile {
    static String read(String fileName) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String s;
        StringBuffer sb = new StringBuffer();
        // TODO: 2017-4-3 tbc...
        return null;
    }
}
