package util.document;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 *
 * @author carl.yu
 * @since 2016/7/22
 */
public class CsvUtils {
    private CsvUtils() {
    }

    public interface CsvChecker {
        void check(CSVParser csvFileParser);
    }


    public static <T> List<T> parseCsv(Reader reader,
                                       Function<CSVRecord, T> converter,
                                       CsvChecker checker) {
        return parseCsv(null, reader, converter, checker);
    }

    /**
     * 方法说明：csv util读取文件工具
     *
     * @param format    csv format,具体参考Csv api
     * @param reader    具体reader
     * @param converter 具体转换规则
     * @param checker   csv header检测逻辑
     * @param <T>       返回类型
     * @return
     */
    public static <T> List<T> parseCsv(
            CSVFormat format,
            Reader reader,
            Function<CSVRecord, T> converter,
            CsvChecker checker) {
        Preconditions.checkNotNull(reader, "reader can't be null");
        if (format == null) format = CSVFormat.DEFAULT
                .withAllowMissingColumnNames()
                .withFirstRecordAsHeader()
                .withIgnoreEmptyLines();
        Preconditions.checkNotNull(converter, "convert can't be null");
        CSVParser csvFileParser = null;
        try {
            csvFileParser = new CSVParser(reader, format);
            checker.check(csvFileParser);
            List<CSVRecord> csvRecords = csvFileParser.getRecords();
            List<T> result = Lists.newArrayListWithCapacity(csvRecords.size());
            for (CSVRecord csvRecord : csvRecords) {
                result.add(converter.apply(csvRecord));
            }
            return result;
        } catch (IOException e) {
            Throwables.propagate(e);
        } finally {
            IOUtils.closeQuietly(csvFileParser);
            IOUtils.closeQuietly(reader);
        }
        return Collections.EMPTY_LIST;
    }

    public static class CollectionDef {
        private Long id;
        private String collectionId;
        private String collectionName;
        private Long configId;
        private String hotelCode;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCollectionId() {
            return collectionId;
        }

        public void setCollectionId(String collectionId) {
            this.collectionId = collectionId;
        }

        public String getCollectionName() {
            return collectionName;
        }

        public void setCollectionName(String collectionName) {
            this.collectionName = collectionName;
        }

        public Long getConfigId() {
            return configId;
        }

        public void setConfigId(Long configId) {
            this.configId = configId;
        }

        public String getHotelCode() {
            return hotelCode;
        }

        public void setHotelCode(String hotelCode) {
            this.hotelCode = hotelCode;
        }
    }

    public static void main(String[] args) throws Exception {
        String fileName = "C:\\Users\\carl.yu\\Desktop\\collection_file_template.csv";
        FileReader reader = new FileReader(fileName);
        List<CollectionDef> collectionDefList = CsvUtils.parseCsv(reader, new Function<CSVRecord, CollectionDef>() {
            @Override
            public CollectionDef apply(CSVRecord input) {
                CollectionDef def = new CollectionDef();
                long number = input.getRecordNumber();
                String collectionId = input.get("collection_id");
                //checkNotBlank(collectionId, String.format("row:%d collection_id is empty.", number));
                def.setCollectionId(collectionId);
                String collectionName = input.get("collection_name");
                //checkNotBlank(collectionName, String.format("row:%d column:collection_name is empty.", number));
                def.setCollectionName(collectionName);
                String ctyhocn = input.get("ctyhocn");
                //checkNotBlank(collectionName, String.format("row:%d column:ctyhocn is empty.", number));
                ctyhocn = ctyhocn.trim();
                //checkState(ctyhocn.length() == 7, String.format("row:%d column:ctyhocn length must be 7.", number));
                def.setHotelCode(ctyhocn.toUpperCase());
                return def;
            }
        }, new CsvChecker() {
            @Override
            public void check(CSVParser csvFileParser) {
                Map<String, Integer> headerMap = csvFileParser.getHeaderMap();
                //checkState(headerMap.containsKey("collection_id"), "csv header must contains collection_id");
                //checkState(headerMap.containsKey("collection_name"), "csv header must contains collection_name");
                //checkState(headerMap.containsKey("ctyhocn"), "csv header must contains ctyhocn");
            }
        });
        System.out.println(collectionDefList);
    }
}
