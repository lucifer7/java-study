package util.reflect;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang.IncompleteArgumentException;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * User: Tony
 * Date: 12-6-15
 */
public class SqlUtil {
    /**
     * Generate sql with the attributes in entity, separate by comma
     * @param fields              entity class type
     * @param usePlaceHolder    if true, use '?', otherwise, use field name
     * @return                  return a string connected by fields
     */
    public static String getEntityColumns(Field[] fields, boolean usePlaceHolder) {
        StringBuilder sb = new StringBuilder();

        for (Field f : fields) {
            if (usePlaceHolder) {
                sb.append("?,");
            } else {
                String columnName;
                /*if (f.isAnnotationPresent(Column.class) && StringUtils.isNotBlank(f.getAnnotation(Column.class).name())) {      // 1. Get column name from @Column annotation
                    Column column = f.getAnnotation(Column.class);
                    columnName = column.name();
                } else*/ {                                        // 2. Get column name from field name
                    columnName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, f.getName());
                }
                if (StringUtils.isNotBlank(columnName)) {
                    sb.append(columnName).append(",");
                } else {
                    throw new IncompleteArgumentException("Cannot mapping column name from field: " + f.getName());
                }
            }
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }

        return sb.toString();
    }

    /**
     * Set values of entity for prepared statement
     * Hidden error: JDK does not guarantee the fields are in order, try sort before use
     * @param ps            prepared statement
     * @param o             entity
     * @param filterId      if true, filter 'id' field
     * @throws SQLException
     */
    public static void insertValueSetter(PreparedStatement ps, Object o, boolean filterId) throws SQLException {
        int i = 1;
        for (Field f : o.getClass().getDeclaredFields()) {
            if ((!filterId || !f.getName().equalsIgnoreCase("id")) && !f.isSynthetic()) {     // filter synthetic fields
                if (f.getType().isEnum()) {         // For Enum type, convert to string
                    ps.setString(i++, ReflectUtil.invokeGetter(o, f.getName()).toString());
                } else {
                    ps.setObject(i++, ReflectUtil.invokeGetter(o, f.getName()));
                }
            }
        }
    }
}
