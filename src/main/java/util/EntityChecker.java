package util;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Assert;
import util.reflect.ReflectUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

/**
 * Usage: <b> A checker for entity instance </b>
 * <pre>
 * Note:
 * 1. each rule is an enum with it's validating function
 * 2. provide a builder to apply rules to each property with method chain
 * 3. provide a bind() to pass the instance to be checked
 * 4. invoke function() to check each rule, return a list of errors or empty_list(if nothing's wrong)
 * 5. all rules are implicitly NOT NULL
 * 6. Some rules are too strict or not strict enough, update the regex pattern if you need
 *
 * TO improve:
 * 1. bind a list of entity
 * 2. implement nullable & if not null must match logic
 * </pre>
 *
 * Example:
 * <pre>
 *     List<String> errors = new EntityChecker.CheckerBuilder()
                                .bind(targetObject)
                                .check("serverPort", EntityChecker.CONST, 22L)
                                .check("protocol", EntityChecker.CONST, "sftp")
                                .check("channelFileName", EntityChecker.ASCII_STRING)
                                .validate();
 * </pre>
 *
 * @author Jingyi.Yang
 *         Date 2016/10/25
 **/
public enum EntityChecker {
    NOT_BLANK(new Function<Pair, String>() {
        @Nullable
        @Override
        public String apply(@Nullable Pair input) {
            Object key = input.getKey();
            if (key instanceof CharSequence && StringUtils.isNotBlank((CharSequence) key)) {
                return null;
            } else {
                return "[" + key + "] is not valid string.";
            }
        }
    }),
    DIGITS(new Function<Pair, String>() {       /* non-negative integer, like id */
        @Nullable
        @Override
        public String apply(@Nullable Pair input) {
            Object key = input.getKey();
            if (key instanceof Number) {
                return null;
            } else if (ifStringMatches(key, "^\\d+$")) {
                    return null;
            } else {
                return "[" + key + "] is not valid digits.";
            }
        }
    }),
    SEPARATED_DIGITS(new Function<Pair, String>() { /* digits separated by comma, space allowed, non-negative integer */
        @Nullable
        @Override
        public String apply(@Nullable Pair input) {
            Object key = input.getKey();
            if (ifStringMatches(key, "^[\\d+\\s*,\\s*|\\d+]+$")) {
                    return null;
            } else {
                return "[" + key + "] is not valid comma-separated digits.";
            }
        }
    }),
    EMAIL(new Function<Pair, String>() {            /* email address, like xxx.yy@jjj.cc */
        @Nullable
        @Override
        public String apply(@Nullable Pair input) {
            Object key = input.getKey();
            if (ifStringMatches(key, "^[\\w-_\\.+]+\\@([\\w]+\\.)+[\\w]{2,}$")) {
                return null;
            } else {
                return "[" + key + "] is not valid email address.";
            }
        }

    }),
    SEPARATED_EMAILS(new Function<Pair, String>() {     /* email address separated by comma, allow a space after comma */
        @Nullable
        @Override
        public String apply(@Nullable Pair input) {
            Object key = input.getKey();
            if (ifStringMatches(key, "^([\\w-_\\.+]+\\@([\\w]+\\.)+[\\w]{2,}\\,{0,1}\\s?)+$")) {
                return null;
            } else {
                return "[" + key + "] is not valid comma separated emails.";
            }
        }
    }),
    ASCII_STRING(new Function<Pair, String>() {     /* string consists of ascii number */
        @Nullable
        @Override
        public String apply(@Nullable Pair input) {
            Object key = input.getKey();
            if (key instanceof CharSequence && CharMatcher.ASCII.matchesAllOf((CharSequence) key)) {
                return null;
            } else {
                return "[" + key + "] is not valid string of ascii character.";
            }
        }
    }),
    FILE_PATH_LINUX(new Function<Pair, String>() {  /* absolute path only, /xx/yy/ */
        @Nullable
        @Override
        public String apply(@Nullable Pair input) {
            Object key = input.getKey();
            if (ifStringMatches(key, "^/([\\w|_|\\-]+/)+$")) {
                return null;
            } else {
                return "[" + key + "] is not valid file path on linux.";
            }
        }
    }),
    CONST(new Function<Pair, String>() {    /* case sensitive */    /* how to check if value of two objects equal? especially for numerics ? */
        @Nullable
        @Override
        public String apply(@Nullable Pair input) {
            Object key = input.getKey();
            Object param = input.getValue();
            if (key.toString().equals(param.toString())) {
                return null;
            } else {
                return String.format("[%s] should equals %s.", key, param);
            }
        }
    }),
    HOST_DOMAIN(new Function<Pair, String>() {  /* host for uploading file, restricted */
        @Nullable
        @Override
        public String apply(@Nullable Pair input) {
            Object key = input.getKey();
            if (ifStringMatches(key, "^[\\w|\\-|_|\\.]+$")) {
                return null;
            } else {
                return "[" + key + "] is not valid host domain.";
            }
        }
    }),
    LONGITUDE(new Function<Pair, String>() {
        @Nullable
        @Override
        public String apply(@Nullable Pair input) {
            Object key = input.getKey().toString();
            if (ifStringMatches(key, "^[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")) {
                return null;
            } else {
                return "[" + key + "] is not valid longitude.";
            }
        }
    }),
    LATITUDE(new Function<Pair, String>() {
        @Nullable
        @Override
        public String apply(@Nullable Pair input) {
            Object key = input.getKey().toString();
            if (ifStringMatches(key, "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)$")) {
                return null;
            } else {
                return "[" + key + "] is not valid latitude.";
            }
        }
    }),
    PHONE_US(new Function<Pair, String>() {
        @Nullable
        @Override
        public String apply(@Nullable Pair input) {
            Object key = input.getKey().toString();
            if (ifStringMatches(key, "^(?:\\d{1,4}-)?\\d{3,4}[-\\s]?\\d{3,4}$")) {
                return null;
            } else {
                return "[" + key + "] is not valid us phone number.";
            }
        }
    });

    /* function function for each Checker rule
     * Returns string message as result,
     * Input a pair of property value and expected value(optional)*/
    private Function<Pair, String> function;

    EntityChecker(Function<Pair, String> function) {
        this.function = function;
    }

    protected String apply(Pair pair) {
        return this.function.apply(pair);
    }

    private static boolean ifStringMatches(Object key, String pattern) {
        if (key instanceof CharSequence) {
            String digit = String.valueOf(key);
            if (digit.matches(pattern)) {
                return true;
            }
        }
        return false;
    }

    public static class CheckerBuilder {
        private Object target;
        List<Triple<String, EntityChecker, Object>> checkers;

        public CheckerBuilder bind(Object target) {
            Assert.assertNotNull("Target object cannot be null.", target);
            this.target = target;
            this.checkers = Lists.newArrayList();
            return this;
        }

        public CheckerBuilder check(String property, EntityChecker checker) {
            return check(property, checker, null);
        }
        public CheckerBuilder check(String property, EntityChecker checker, @Nullable Object param) {
            //Assert.hasText(property, "Property for check cannot be null or blank.");
            Assert.assertNotNull("Checker cannot be null.", checker);
            checkers.add(Triple.of(property, checker, param));
            return this;
        }

        public List<String> validate() {
            List<String> errors = Lists.newArrayList();

            for (Triple<String, EntityChecker, Object> checker : checkers) {
                String column = checker.getLeft();
                EntityChecker rule = checker.getMiddle();
                Object param = checker.getRight();

                Object property = ReflectUtil.invokeGetter(target, column);
                /* Question: how to handle situation: nullable, but if not null, must match a pattern? */
                //if (param instanceof Boolean && (Boolean) param && null == property) {
                if (null == property) {
                    errors.add(column + " cannot be null.");
                    continue;
                }
                String msg = rule.apply(Pair.of(property, param));
                if (null != msg) {
                    errors.add(column + msg);
                }
            }

            return errors;
        }

    }
}
