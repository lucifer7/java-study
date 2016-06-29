package util.document;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by lucifer on 2016-6-29.
 * 使用 apache poi 读取 doc 文档，抓取图片并保存到本地
 */
public class Doc2Img {
    public static void getDocAndPicTable(String path, String picSavePath) throws Exception {
        File file = new File(path);
        FileInputStream in = new FileInputStream(file.getAbsolutePath());
        HWPFDocument doc = new HWPFDocument(in);

        PicturesTable pTable = doc.getPicturesTable();

        int titleLength = doc.getSummaryInformation().getTitle().length();
        int length = doc.characterLength();
        for (int i = titleLength; i < length - 1; i++) {
            Range range = new Range(i, i + 1, doc);
            CharacterRun cr = range.getCharacterRun(0);
            if (pTable.hasPicture(cr)) {
                savePicture(pTable, cr, picSavePath, file.getName());
            }
        }
    }

    //保存图片到本地
    private static void savePicture(PicturesTable pTable, CharacterRun cr, String picSavePath, String fileName) throws Exception {
        Picture picture = pTable.extractPicture(cr, false);

        String picName = fileName.replaceAll("\\..+", "") + "_" + System.currentTimeMillis() + "." + picture.suggestFileExtension();
        OutputStream out = new FileOutputStream(new File(picSavePath + File.separator + picName));
        picture.writeImageContent(out);

        System.out.println("Saving picture: " + picName);
    }

    public static void main(String[] args) {
        String path = "C:\\Users\\lucifer\\Desktop\\test.doc";
        String picSavePath = "d:\\images";

        try {
            getDocAndPicTable(path, picSavePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
