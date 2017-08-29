package util;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import java8.factory.Apple;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Assert;
import util.reflect.ReflectUtil;

import javax.annotation.Nullable;
import java.util.List;

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
 * <p>
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
    NOT_BLANK {
        @Override
        String apply(Pair pair) {
            Object key = pair.getKey();
            if (key instanceof CharSequence && StringUtils.isNotBlank((CharSequence) key)) {
                return null;
            } else {
                return "[" + key + "] is not valid string.";
            }
        }
    },
    DIGITS {           /* non-negative integer, like id */

        @Override
        String apply(Pair pair) {
            Object key = pair.getKey();
            if (key instanceof Number) {
                return null;
            } else if (ifStringMatches(key, "^\\d+$")) {
                return null;
            } else {
                return "[" + key + "] is not valid digits.";
            }
        }
    },
    SEPARATED_DIGITS {   /* digits separated by comma, space allowed, non-negative integer */

        @Override
        String apply(Pair pair) {
            Object key = pair.getKey();
            if (ifStringMatches(key, "^[\\d+\\s*,\\s*|\\d+]+$")) {
                return null;
            } else {
                return "[" + key + "] is not valid comma-separated digits.";
            }
        }
    },
    EMAIL {             /* email address, like xxx.yy@jjj.cc */

        @Override
        String apply(Pair pair) {
            Object key = pair.getKey();
            if (ifStringMatches(key, "^[\\w-_\\.+]+\\@([\\w]+\\.)+[\\w]{2,}$")) {
                return null;
            } else {
                return "[" + key + "] is not valid email address.";
            }
        }
    },
    SEPARATED_EMAILS {       /* email address separated by comma, allow a space after comma */

        @Override
        String apply(Pair pair) {
            Object key = pair.getKey();
            if (ifStringMatches(key, "^([\\w-_\\.+]+\\@([\\w]+\\.)+[\\w]{2,}\\,{0,1}\\s?)+$")) {
                return null;
            } else {
                return "[" + key + "] is not valid comma separated emails.";
            }
        }
    },
    ASCII_STRING {     /* string consists of ascii number */

        @Override
        String apply(Pair pair) {
            Object key = pair.getKey();
            if (key instanceof CharSequence && CharMatcher.ASCII.matchesAllOf((CharSequence) key)) {
                return null;
            } else {
                return "[" + key + "] is not valid string of ascii character.";
            }
        }
    },
    FILE_PATH_LINUX {     /* absolute path only, /xx/yy/ */

        @Override
        String apply(Pair pair) {
            Object key = pair.getKey();
            if (ifStringMatches(key, "^/([\\w|_|\\-]+/)+$")) {
                return null;
            } else {
                return "[" + key + "] is not valid file path on linux.";
            }
        }
    },
    CONST {     /* case sensitive */    /* how to check if value of two objects equal? especially for numerics ? */

        @Override
        String apply(Pair pair) {
            Object key = pair.getKey();
            Object param = pair.getValue();
            if (key.toString().equals(param.toString())) {
                return null;
            } else {
                return String.format("[%s] should equals %s.", key, param);
            }
        }
    },
    HOST_DOMAIN {     /* host for uploading file, restricted */

        @Override
        String apply(Pair pair) {
            Object key = pair.getKey();
            if (ifStringMatches(key, "^[\\w|\\-|_|\\.]+$")) {
                return null;
            } else {
                return "[" + key + "] is not valid host domain.";
            }
        }
    },
    LONGITUDE {
        @Override
        String apply(Pair pair) {
            Object key = pair.getKey().toString();
            if (ifStringMatches(key, "^[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")) {
                return null;
            } else {
                return "[" + key + "] is not valid longitude.";
            }
        }
    },
    LATITUDE {
        @Override
        String apply(Pair pair) {
            Object key = pair.getKey().toString();
            if (ifStringMatches(key, "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)$")) {
                return null;
            } else {
                return "[" + key + "] is not valid latitude.";
            }
        }
    },
    PHONE_US {
        @Override
        String apply(Pair pair) {
            Object key = pair.getKey().toString();
            if (ifStringMatches(key, "^(?:\\d{1,4}-)?\\d{3,4}[-\\s]?\\d{3,4}$")) {
                return null;
            } else {
                return "[" + key + "] is not valid us phone number.";
            }
        }
    };

    /* function function for each Checker rule
     * Returns string message as result,
     * Input a pair of property value and expected value(optional)*/
    //private Function<Pair, String> function;

    /*EntityChecker(Function<Pair, String> function) {
        this.function = function;
    }*/

    abstract String apply(Pair pair); /*{
        return this.function.apply(pair);
    }*/

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

                // 1. Get value of specified column
                Object property;
                try {
                    property = ReflectUtil.invokeGetter(target, column);
                } catch (IllegalArgumentException e) {
                    errors.add(column + " does not exist or invalid getter.");
                    continue;
                }

                // 2. Check if value is null
                /* Question: how to handle situation: nullable, but if not null, must match a pattern? */
                //if (param instanceof Boolean && (Boolean) param && null == property) {
                if (null == property) {
                    errors.add(column + " cannot be null.");
                    continue;
                }

                // 3. Apply the bind rule on this value
                String msg = rule.apply(Pair.of(property, param));
                if (null != msg) {
                    errors.add(column + msg);
                }
            }

            return errors;
        }
    }


    public static void main(String[] args) {
        Apple apple = new Apple("WHITE", 240);

        List<String> errors = new EntityChecker.CheckerBuilder()
                .bind(apple)
                .check("latitude", EntityChecker.LATITUDE)
                .check("longitude", EntityChecker.LONGITUDE)
                .validate();

        System.out.println(errors);
    }
}
