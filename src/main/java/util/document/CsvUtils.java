package util.document;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import util.reflect.ReflectUtil;
import util.reflect.SqlUtil;
import util.reflect.StringUtil;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;


/**
 * <B>系统名称：MSM</B><BR>
 * <B>模块名称：CSV Tools </B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：provide support for csv parser(using function) and csv list writer </B><BR>
 *
 * @author carl.yu
 * @since 2016/7/22
 */
public class CsvUtils {
    private static final Logger LOG = Logger.getLogger(CsvUtils.class);

    private static final String DEFAULT_SEPARATOR = ",";

    private CsvUtils() {
    }

    public interface CsvChecker {
        void check(CSVParser csvFileParser);
    }

    /**
     * Write list of lines(columns in array) to csv files
     * @param lines list of lines(columns in array)
     * @param file result file
     * @param separator separator to join the columns
     * @return true if success, false if exception
     */
    public static boolean writeLines2Csv(List<String[]> lines, File file, String separator) {
        if (StringUtils.isBlank(separator)) {
            separator = DEFAULT_SEPARATOR;
        }

        List<String> joinedLines = Lists.newArrayListWithCapacity(lines.size());
        for (String[] line : lines) {
            joinedLines.add(StringUtil.join(line, separator));
        }

        return saveFile(file, joinedLines);
    }

    /**
     * Write list of beans to csv file
     * @param list      bean list, ArrayList preferred
     * @param file      result file
     * @param clzType   bean type
     * @return          true if success, false if exception
     */
    public static boolean writeList2Csv(List list, File file, Class clzType) {
        // 1. Filter class fields
        Field[] fields = ReflectUtil.filterFields(clzType.getDeclaredFields(), true);

        // 2. Generate csv header and lines by reflection
        String header = SqlUtil.getEntityColumns(fields, false);
        String[] lines;
        try {
            lines = ReflectUtil.genLineFromEntities(list, fields);
        } catch (IllegalAccessException e) {
            return false;
        }

        // 3. Concat csv content
        List<String> content = Lists.newArrayListWithCapacity(lines.length + 1);
        content.add(header);
        Collections.addAll(content, lines);

        // 4. Write content to file
        return saveFile(file, content);
    }

    private static boolean saveFile(File file, List<String> content) {
        try {
            FileUtils.writeLines(file, content);
            return true;
        } catch (IOException e) {
            LOG.error(e);
            return false;
        }
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
}
