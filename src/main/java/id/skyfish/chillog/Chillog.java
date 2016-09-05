package id.skyfish.chillog;

import com.alibaba.fastjson.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Chillog main class.
 * <p>
 * This class contains static methods to log message that conforms with the Chillog Logging Format v1.
 * The log will be directed to STDOUT or STDERR.
 */
public class Chillog {

    /**
     * The severity level of the message.
     * <p>
     * This level based loosely on BSD Syslog (https://tools.ietf.org/html/rfc3164) with
     * notable exception of the lack of EMERGENCY level.
     */
    enum Level {
        ALERT(1),
        CRITICAL(2),
        ERROR(3),
        WARNING(4),
        NOTICE(5),
        INFORMATIONAL(6),
        DEBUG(7);

        private final int value;

        Level(int value) {
            this.value = value;
        }

        int getValue() {
            return value;
        }
    }

    /**
     * Null Object pattern to ease checking for empty pairs.
     */
    static final Map<String, Object> EMPTY_PAIRS = Collections.emptyMap();

    /**
     * Hostname of the machine where the application is running.
     */
    private static String hostname;

    /**
     * The service name identifier of the application.
     */
    private static String serviceName;

    static {
        setHostname();
        setServiceName();
    }

    /**
     * Prints a Chillog-formatted string to STDERR, with "action must be taken immediately" severity.
     *
     * @param shortMessage Short message to print.
     */
    public static void alert(String shortMessage) {
        alert(shortMessage, null, EMPTY_PAIRS);
    }

    /**
     * Prints a Chillog-formatted string to STDERR, with "critical conditions" severity.
     *
     * @param shortMessage Short message to print.
     */
    public static void critical(String shortMessage) {
        critical(shortMessage, null, EMPTY_PAIRS);
    }

    /**
     * Prints a Chillog-formatted string to STDERR, with "error conditions" severity.
     *
     * @param shortMessage Short message to print.
     */
    public static void error(String shortMessage) {
        error(shortMessage, null, EMPTY_PAIRS);
    }

    /**
     * Prints a Chillog-formatted string to STDOUT, with "warning conditions" severity.
     *
     * @param shortMessage Short message to print.
     */
    public static void warning(String shortMessage) {
        warning(shortMessage, null, EMPTY_PAIRS);
    }

    /**
     * Prints a Chillog-formatted string to STDOUT, with "normal but significant condition" severity.
     *
     * @param shortMessage Short message to print.
     */
    public static void notice(String shortMessage) {
        notice(shortMessage, null, EMPTY_PAIRS);
    }

    /**
     * Prints a Chillog-formatted string to STDOUT, with "informational messages" severity.
     *
     * @param shortMessage Short message to print.
     */
    public static void info(String shortMessage) {
        info(shortMessage, null, EMPTY_PAIRS);
    }

    /**
     * Prints a Chillog-formatted short message to STDOUT, with "debug-level messages" severity.
     *
     * @param shortMessage Short message to print.
     */
    public static void debug(String shortMessage) {
        debug(shortMessage, null, EMPTY_PAIRS);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Prints a Chillog-formatted message to STDERR, with "action must be taken immediately" severity.
     *
     * To help debugging/tracing, caller can use the keyValuePairs parameter. Input to this parameter must be formatted
     * as interleaving key and value. This method, then, will parse adjacent key and value as a pair. For example,
     * when caller wants to add information about HTTP Request ID and HTTP Referer, callee can format the parameter as
     * <code>"http_request_id", "a1b2c3d4", "http_referer", "https://www.google.com"</code>.
     *
     * @param shortMessage  Short message that describe the event.
     * @param fullMessage   More-detailed message.
     * @param keyValuePairs Varargs that will be used as additional information, formatted as a key-value pairs.
     */
    public static void alert(String shortMessage, String fullMessage, String... keyValuePairs) {
        Map<String, Object> additionalFields = convertToMap(keyValuePairs);
        alert(shortMessage, fullMessage, additionalFields);
    }

    /**
     * Prints a Chillog-formatted message to STDERR, with "critical conditions" severity.
     *
     * To help debugging/tracing, caller can use the keyValuePairs parameter. Input to this parameter must be formatted
     * as interleaving key and value. This method, then, will parse adjacent key and value as a pair. For example,
     * when caller wants to add information about HTTP Request ID and HTTP Referer, callee can format the parameter as
     * <code>"http_request_id", "a1b2c3d4", "http_referer", "https://www.google.com"</code>.
     *
     * @param shortMessage  Short message that describe the event.
     * @param fullMessage   More-detailed message.
     * @param keyValuePairs Varargs that will be used as additional information, formatted as a key-value pairs.
     */
    public static void critical(String shortMessage, String fullMessage, String... keyValuePairs) {
        Map<String, Object> additionalFields = convertToMap(keyValuePairs);
        critical(shortMessage, fullMessage, additionalFields);
    }

    /**
     * Prints a Chillog-formatted message to STDERR, with "error conditions" severity.
     *
     * To help debugging/tracing, caller can use the keyValuePairs parameter. Input to this parameter must be formatted
     * as interleaving key and value. This method, then, will parse adjacent key and value as a pair. For example,
     * when caller wants to add information about HTTP Request ID and HTTP Referer, callee can format the parameter as
     * <code>"http_request_id", "a1b2c3d4", "http_referer", "https://www.google.com"</code>.
     *
     * @param shortMessage  Short message that describe the event.
     * @param fullMessage   More-detailed message.
     * @param keyValuePairs Varargs that will be used as additional information, formatted as a key-value pairs.
     */
    public static void error(String shortMessage, String fullMessage, String... keyValuePairs) {
        Map<String, Object> additionalFields = convertToMap(keyValuePairs);
        error(shortMessage, fullMessage, additionalFields);
    }

    /**
     * Prints a Chillog-formatted message to STDOUT, with "warning conditions" severity.
     *
     * To help debugging/tracing, caller can use the keyValuePairs parameter. Input to this parameter must be formatted
     * as interleaving key and value. This method, then, will parse adjacent key and value as a pair. For example,
     * when caller wants to add information about HTTP Request ID and HTTP Referer, callee can format the parameter as
     * <code>"http_request_id", "a1b2c3d4", "http_referer", "https://www.google.com"</code>.
     *
     * @param shortMessage  Short message that describe the event.
     * @param fullMessage   More-detailed message.
     * @param keyValuePairs Varargs that will be used as additional information, formatted as a key-value pairs.
     */
    public static void warning(String shortMessage, String fullMessage, String... keyValuePairs) {
        Map<String, Object> additionalFields = convertToMap(keyValuePairs);
        warning(shortMessage, fullMessage, additionalFields);
    }

    /**
     * Prints a Chillog-formatted message to STDOUT, with "normal but significant condition" severity.
     *
     * To help debugging/tracing, caller can use the keyValuePairs parameter. Input to this parameter must be formatted
     * as interleaving key and value. This method, then, will parse adjacent key and value as a pair. For example,
     * when caller wants to add information about HTTP Request ID and HTTP Referer, callee can format the parameter as
     * <code>"http_request_id", "a1b2c3d4", "http_referer", "https://www.google.com"</code>.
     *
     * @param shortMessage  Short message that describe the event.
     * @param fullMessage   More-detailed message.
     * @param keyValuePairs Varargs that will be used as additional information, formatted as a key-value pairs.
     */
    public static void notice(String shortMessage, String fullMessage, String... keyValuePairs) {
        Map<String, Object> additionalFields = convertToMap(keyValuePairs);
        notice(shortMessage, fullMessage, additionalFields);
    }

    /**
     * Prints a Chillog-formatted message to STDOUT, with "informational messages" severity.
     *
     * To help debugging/tracing, caller can use the keyValuePairs parameter. Input to this parameter must be formatted
     * as interleaving key and value. This method, then, will parse adjacent key and value as a pair. For example,
     * when caller wants to add information about HTTP Request ID and HTTP Referer, callee can format the parameter as
     * <code>"http_request_id", "a1b2c3d4", "http_referer", "https://www.google.com"</code>.
     *
     * @param shortMessage  Short message that describe the event.
     * @param fullMessage   More-detailed message.
     * @param keyValuePairs Varargs that will be used as additional information, formatted as a key-value pairs.
     */
    public static void info(String shortMessage, String fullMessage, String... keyValuePairs) {
        Map<String, Object> additionalFields = convertToMap(keyValuePairs);
        info(shortMessage, fullMessage, additionalFields);
    }

    /**
     * Prints a Chillog-formatted message to STDOUT, with "debug-level messages" severity.
     *
     * To help debugging/tracing, caller can use the keyValuePairs parameter. Input to this parameter must be formatted
     * as interleaving key and value. This method, then, will parse adjacent key and value as a pair. For example,
     * when caller wants to add information about HTTP Request ID and HTTP Referer, callee can format the parameter as
     * <code>"http_request_id", "a1b2c3d4", "http_referer", "https://www.google.com"</code>.
     *
     * @param shortMessage  Short message that describe the event.
     * @param fullMessage   More-detailed message.
     * @param keyValuePairs Varargs that will be used as additional information, formatted as a key-value pairs.
     */
    public static void debug(String shortMessage, String fullMessage, String... keyValuePairs) {
        Map<String, Object> additionalFields = convertToMap(keyValuePairs);
        debug(shortMessage, fullMessage, additionalFields);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Prints a Chillog-formatted message to STDERR, with "action must be taken immediately" severity.
     * <p>
     * To help debugging/tracing, caller can use the additionalFields parameter. The contents of the map will be
     * printed along with the message.
     *
     * @param shortMessage     Short message that describe the event.
     * @param fullMessage      More-detailed message.
     * @param additionalFields Map of String to Object. The content of this variable will be printed alongside the
     *                         message. To get the string representation, object's <code>toString()</code> method
     *                         will be called in the implementation.
     */
    public static void alert(String shortMessage, String fullMessage, Map<String, Object> additionalFields) {
        log(shortMessage, fullMessage, getMillisTimestamp(), Level.ALERT, additionalFields);
    }

    /**
     * Prints a Chillog-formatted message to STDERR, with "critical conditions" severity.
     * <p>
     * To help debugging/tracing, caller can use the additionalFields parameter. The contents of the map will be
     * printed along with the message.
     *
     * @param shortMessage     Short message that describe the event.
     * @param fullMessage      More-detailed message.
     * @param additionalFields Map of String to Object. The content of this variable will be printed alongside the
     *                         message. To get the string representation, object's <code>toString()</code> method
     *                         will be called in the implementation.
     */
    public static void critical(String shortMessage, String fullMessage, Map<String, Object> additionalFields) {
        log(shortMessage, fullMessage, getMillisTimestamp(), Level.CRITICAL, additionalFields);
    }

    /**
     * Prints a Chillog-formatted message to STDERR, with "error conditions" severity.
     * <p>
     * To help debugging/tracing, caller can use the additionalFields parameter. The contents of the map will be
     * printed along with the message.
     *
     * @param shortMessage     Short message that describe the event.
     * @param fullMessage      More-detailed message.
     * @param additionalFields Map of String to Object. The content of this variable will be printed alongside the
     *                         message. To get the string representation, object's <code>toString()</code> method
     *                         will be called in the implementation.
     */
    public static void error(String shortMessage, String fullMessage, Map<String, Object> additionalFields) {
        log(shortMessage, fullMessage, getMillisTimestamp(), Level.ERROR, additionalFields);
    }

    /**
     * Prints a Chillog-formatted message to STDOUT, with "warning conditions" severity.
     * <p>
     * To help debugging/tracing, caller can use the additionalFields parameter. The contents of the map will be
     * printed along with the message.
     *
     * @param shortMessage     Short message that describe the event.
     * @param fullMessage      More-detailed message.
     * @param additionalFields Map of String to Object. The content of this variable will be printed alongside the
     *                         message. To get the string representation, object's <code>toString()</code> method
     *                         will be called in the implementation.
     */
    public static void warning(String shortMessage, String fullMessage, Map<String, Object> additionalFields) {
        log(shortMessage, fullMessage, getMillisTimestamp(), Level.WARNING, additionalFields);
    }

    /**
     * Prints a Chillog-formatted message to STDOUT, with "normal but significant condition" severity.
     * <p>
     * To help debugging/tracing, caller can use the additionalFields parameter. The contents of the map will be
     * printed along with the message.
     *
     * @param shortMessage     Short message that describe the event.
     * @param fullMessage      More-detailed message.
     * @param additionalFields Map of String to Object. The content of this variable will be printed alongside the
     *                         message. To get the string representation, object's <code>toString()</code> method
     *                         will be called in the implementation.
     */
    public static void notice(String shortMessage, String fullMessage, Map<String, Object> additionalFields) {
        log(shortMessage, fullMessage, getMillisTimestamp(), Level.NOTICE, additionalFields);
    }

    /**
     * Prints a Chillog-formatted message to STDOUT, with "informational messages" severity.
     * <p>
     * To help debugging/tracing, caller can use the additionalFields parameter. The contents of the map will be
     * printed along with the message.
     *
     * @param shortMessage     Short message that describe the event.
     * @param fullMessage      More-detailed message.
     * @param additionalFields Map of String to Object. The content of this variable will be printed alongside the
     *                         message. To get the string representation, object's <code>toString()</code> method
     *                         will be called in the implementation.
     */
    public static void info(String shortMessage, String fullMessage, Map<String, Object> additionalFields) {
        log(shortMessage, fullMessage, getMillisTimestamp(), Level.INFORMATIONAL, additionalFields);
    }

    /**
     * Prints a Chillog-formatted message to STDOUT, with "debug-level messages" severity.
     * <p>
     * To help debugging/tracing, caller can use the additionalFields parameter. The contents of the map will be
     * printed along with the message.
     *
     * @param shortMessage     Short message that describe the event.
     * @param fullMessage      More-detailed message.
     * @param additionalFields Map of String to Object. The content of this variable will be printed alongside the
     *                         message. To get the string representation, object's <code>toString()</code> method
     *                         will be called in the implementation.
     */
    public static void debug(String shortMessage, String fullMessage, Map<String, Object> additionalFields) {
        log(shortMessage, fullMessage, getMillisTimestamp(), Level.DEBUG, additionalFields);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Log message to either STDOUT (for message with WARNING or above severity) or STDERR (for message with ERROR or
     * below severity).
     *
     * @param shortMessage Short message about the event.
     * @param fullMessage More-detailed message about the event. Can be null.
     * @param timestamp Milliseconds passed since January 1st, 1970.
     * @param level The severity {@link Level} of the event.
     * @param additionalFields Additional information about the event.
     */
    static void log(String shortMessage, String fullMessage, long timestamp,
                    Level level, Map<String, Object> additionalFields) {
        String logMessageString = generateJsonString(shortMessage, fullMessage, timestamp, level, additionalFields);
        if (level.getValue() >= Level.WARNING.getValue()) {
            System.out.println(logMessageString);
        } else {
            System.err.println(logMessageString);
        }
    }

    /**
     * Generate log message that conforms with the Chillog Logging Format v1.
     *
     * @param shortMessage Short message about the event.
     * @param fullMessage More-detailed message about the event. Can be null.
     * @param timestamp Milliseconds passed since January 1st, 1970.
     * @param level The severity {@link Level} of the event.
     * @param additionalFields Additional information about the event.
     *
     * @return A valid JSON string that conforms with the Chillog Logging Format v1.
     */
    static String generateJsonString(String shortMessage, String fullMessage, long timestamp, Level level,
                                     Map<String, Object> additionalFields) {
        JSONObject logMessageJson = new JSONObject();

        /*
         * Required fields
         */

        logMessageJson.put("version", 1);
        logMessageJson.put("host", hostname);
        logMessageJson.put("service", serviceName);
        logMessageJson.put("short_message", shortMessage);
        logMessageJson.put("timestamp", timestamp);
        logMessageJson.put("level", level.getValue());


        /*
         * Optional fields
         */

        if (fullMessage != null) {
            logMessageJson.put("full_message", fullMessage);
        }

        if (additionalFields != EMPTY_PAIRS) {
            for (Map.Entry<String, Object> additionalField : additionalFields.entrySet()) {
                Object key = additionalField.getKey();
                Object value = additionalField.getValue();
                if (value != null) {
                    logMessageJson.put("_" + key, value.toString());
                } else {
                    logMessageJson.put("_" + key, "NULL");
                }
            }

            // Check for reserved field: _id
            Object content = logMessageJson.get("_id");
            if (content != null) {
                // Rename the field if found
                logMessageJson.put("__id", content);
                logMessageJson.remove("_id");
            }
        }

        return logMessageJson.toJSONString();
    }

    /**
     * Converts an array of {@link String} to a key-value pair contained in a {@link Map}.
     *
     * <p>The callee must make sure that the format of the input follows the pattern:
     *
     * <pre>
     * 0: [key1],
     * 1: [value1],
     * 2: [key2],
     * 3: [value2],
     * ...
     * n * 2: [key(n *2)],
     * n * 2 + 1: [value(n * 2 + 1)].
     * </pre>
     *
     * @param strings array that contains key-value pairs.
     * @return Map of string to string where each entry corresponds to key-value pair.
     */
    static Map<String, Object> convertToMap(String[] strings) {
        if (strings == null) {
            return EMPTY_PAIRS;
        }

        Map<String, Object> pairs = new HashMap<>();

        // Loop through each pairs.
        int pairCount = (int) Math.ceil((float) strings.length / 2);
        for (int i = 0; i < pairCount; i++) {
            int pairKeyIndex = i * 2;               // index of the key of the pair in the array.
            int pairValueIndex = pairKeyIndex + 1;  // index of the value of the pair in the array.

            // Check and prevent NPE
            if (pairValueIndex < strings.length) {
                pairs.put(strings[pairKeyIndex], strings[pairValueIndex]);
            } else {
                pairs.put(strings[pairKeyIndex], "");
            }
        }

        return pairs;
    }

    /**
     * Sets the hostname.
     * <p>
     * This method will set the hostname for logging purpose. The way it does this is by:
     * <p>
     * 1. Checks whether environment variable called HOSTNAME is set. If it is, the hostname
     *    will be set according to the value of this environment value.
     * 2. If hostname is still not set, this method will try to get the local hostname by
     *    calling <code>InetAddress.getLocalHost().getHostName()</code>, and then set the
     *    hostname according to the result.
     * 3. If the hostname is still not set (because previous two methods fail), the hostname
     *    will be set to empty string (i.e. "").
     */
    private static void setHostname() {
        hostname = System.getenv("HOSTNAME");

        if (hostname == null) {
            try {
                hostname = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException ignored) {
            }
        }

        if (hostname == null) {
            hostname = "";
        }
    }

    /**
     * Sets the service name.
     *
     * This method will set the service name for logging purpose. The value will be fetched
     * from environment variable called SERVICE_NAME. If no such environment variable found,
     * {@link RuntimeException} will be thrown.
     */
    private static void setServiceName() {
        serviceName = System.getenv("SERVICE_NAME");

        if (serviceName == null) {
            throw new RuntimeException("SERVICE_NAME is not set. Please set it in your environment variable.");
        };
    }

    /**
     * Gets how long milliseconds has passed since January 1st, 1970.
     *
     * @return A milliseconds value that represent the time passed since January 1st, 1970.
     */
    private static long getMillisTimestamp() {
        return System.currentTimeMillis();
    }
}
