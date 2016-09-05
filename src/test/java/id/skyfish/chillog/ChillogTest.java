package id.skyfish.chillog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by angga on 8/24/16.
 */
public class ChillogTest {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();

    @BeforeClass
    public static void setEnvironmentVariables() {
        Map<String, String> environmentVariables = new HashMap<>();
        environmentVariables.put("SERVICE_NAME", "fake-service-name");
        setEnv(environmentVariables);
    }

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
    }

    @After
    public void tearDownStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void testAlertShortMessage() {
        Chillog.alert("This is a short message");

        String errContent = err.toString();
        JSONObject errJson = JSON.parseObject(errContent);

        Assert.assertEquals("This is a short message", errJson.getString("short_message"));
        Assert.assertEquals(1, errJson.getIntValue("level"));
        Assert.assertEquals("fake-service-name", errJson.getString("service"));

        Assert.assertTrue(errJson.containsKey("host"));
        Assert.assertTrue(errJson.containsKey("timestamp"));
        Assert.assertTrue(errJson.containsKey("version"));
    }

    @Test
    public void testCriticalShortMessage() {
        Chillog.critical("This is a short message");

        String errContent = err.toString();
        JSONObject errJson = JSON.parseObject(errContent);

        Assert.assertEquals("This is a short message", errJson.getString("short_message"));
        Assert.assertEquals(2, errJson.getIntValue("level"));
        Assert.assertEquals("fake-service-name", errJson.getString("service"));

        Assert.assertTrue(errJson.containsKey("host"));
        Assert.assertTrue(errJson.containsKey("timestamp"));
        Assert.assertTrue(errJson.containsKey("version"));
    }

    @Test
    public void testErrorShortMessage() {
        Chillog.error("This is a short message");

        String errContent = err.toString();
        JSONObject errJson = JSON.parseObject(errContent);

        Assert.assertEquals("This is a short message", errJson.getString("short_message"));
        Assert.assertEquals(3, errJson.getIntValue("level"));
        Assert.assertEquals("fake-service-name", errJson.getString("service"));

        Assert.assertTrue(errJson.containsKey("host"));
        Assert.assertTrue(errJson.containsKey("timestamp"));
        Assert.assertTrue(errJson.containsKey("version"));
    }

    @Test
    public void testWarningShortMessage() {
        Chillog.warning("This is a short message");

        String outContent = out.toString();
        JSONObject ourJson = JSON.parseObject(outContent);

        Assert.assertEquals("This is a short message", ourJson.getString("short_message"));
        Assert.assertEquals(4, ourJson.getIntValue("level"));
        Assert.assertEquals("fake-service-name", ourJson.getString("service"));

        Assert.assertTrue(ourJson.containsKey("host"));
        Assert.assertTrue(ourJson.containsKey("timestamp"));
        Assert.assertTrue(ourJson.containsKey("version"));
    }

    @Test
    public void testNoticeShortMessage() {
        Chillog.notice("This is a short message");

        String outContent = out.toString();
        JSONObject outJson = JSON.parseObject(outContent);

        Assert.assertEquals("This is a short message", outJson.getString("short_message"));
        Assert.assertEquals(5, outJson.getIntValue("level"));
        Assert.assertEquals("fake-service-name", outJson.getString("service"));

        Assert.assertTrue(outJson.containsKey("host"));
        Assert.assertTrue(outJson.containsKey("timestamp"));
        Assert.assertTrue(outJson.containsKey("version"));
    }

    @Test
    public void testInfoShortMessage() {
        Chillog.info("This is a short message");

        String outContent = out.toString();
        JSONObject outJson = JSON.parseObject(outContent);

        Assert.assertEquals("This is a short message", outJson.getString("short_message"));
        Assert.assertEquals(6, outJson.getIntValue("level"));
        Assert.assertEquals("fake-service-name", outJson.getString("service"));

        Assert.assertTrue(outJson.containsKey("host"));
        Assert.assertTrue(outJson.containsKey("timestamp"));
        Assert.assertTrue(outJson.containsKey("version"));
    }

    @Test
    public void testDebugShortMessage() {
        Chillog.debug("This is a short message");

        String outContent = out.toString();
        JSONObject outJson = JSON.parseObject(outContent);

        Assert.assertEquals("This is a short message", outJson.getString("short_message"));
        Assert.assertEquals(7, outJson.getIntValue("level"));
        Assert.assertEquals("fake-service-name", outJson.getString("service"));

        Assert.assertTrue(outJson.containsKey("host"));
        Assert.assertTrue(outJson.containsKey("timestamp"));
        Assert.assertTrue(outJson.containsKey("version"));
    }

    @Test
    public void testAlertMessage() {
        Chillog.alert("This is a short message", "This supposed to be a long message",
                "key1", "value1", "key2", "value2");

        String errContent = err.toString();
        JSONObject errJson = JSON.parseObject(errContent);

        Assert.assertEquals(1, errJson.getIntValue("level"));
        Assert.assertEquals("fake-service-name", errJson.getString("service"));
        Assert.assertEquals("This is a short message", errJson.getString("short_message"));
        Assert.assertEquals("This supposed to be a long message", errJson.getString("full_message"));
        Assert.assertEquals("value1", errJson.getString("_key1"));
        Assert.assertEquals("value2", errJson.getString("_key2"));

        Assert.assertTrue(errJson.containsKey("host"));
        Assert.assertTrue(errJson.containsKey("timestamp"));
        Assert.assertTrue(errJson.containsKey("version"));
    }

    @Test
    public void testCriticalMessage() {
        Chillog.critical("This is a short message", "This supposed to be a long message",
                "key1", "value1", "key2", "value2");

        String errContent = err.toString();
        JSONObject errJson = JSON.parseObject(errContent);

        Assert.assertEquals(2, errJson.getIntValue("level"));
        Assert.assertEquals("fake-service-name", errJson.getString("service"));
        Assert.assertEquals("This is a short message", errJson.getString("short_message"));
        Assert.assertEquals("This supposed to be a long message", errJson.getString("full_message"));
        Assert.assertEquals("value1", errJson.getString("_key1"));
        Assert.assertEquals("value2", errJson.getString("_key2"));

        Assert.assertTrue(errJson.containsKey("host"));
        Assert.assertTrue(errJson.containsKey("timestamp"));
        Assert.assertTrue(errJson.containsKey("version"));
    }

    @Test
    public void testErrorMessage() {
        Chillog.error("This is a short message", "This supposed to be a long message",
                "key1", "value1", "key2", "value2");

        String errContent = err.toString();
        JSONObject errJson = JSON.parseObject(errContent);

        Assert.assertEquals(3, errJson.getIntValue("level"));
        Assert.assertEquals("fake-service-name", errJson.getString("service"));
        Assert.assertEquals("This is a short message", errJson.getString("short_message"));
        Assert.assertEquals("This supposed to be a long message", errJson.getString("full_message"));
        Assert.assertEquals("value1", errJson.getString("_key1"));
        Assert.assertEquals("value2", errJson.getString("_key2"));

        Assert.assertTrue(errJson.containsKey("host"));
        Assert.assertTrue(errJson.containsKey("timestamp"));
        Assert.assertTrue(errJson.containsKey("version"));
    }

    @Test
    public void testWarningMessage() {
        Chillog.warning("This is a short message", "This supposed to be a long message",
                "key1", "value1", "key2", "value2");

        String outContent = out.toString();
        JSONObject outJson = JSON.parseObject(outContent);

        Assert.assertEquals(4, outJson.getIntValue("level"));
        Assert.assertEquals("fake-service-name", outJson.getString("service"));
        Assert.assertEquals("This is a short message", outJson.getString("short_message"));
        Assert.assertEquals("This supposed to be a long message", outJson.getString("full_message"));
        Assert.assertEquals("value1", outJson.getString("_key1"));
        Assert.assertEquals("value2", outJson.getString("_key2"));

        Assert.assertTrue(outJson.containsKey("host"));
        Assert.assertTrue(outJson.containsKey("timestamp"));
        Assert.assertTrue(outJson.containsKey("version"));
    }

    @Test
    public void testNoticeMessage() {
        Chillog.notice("This is a short message", "This supposed to be a long message",
                "key1", "value1", "key2", "value2");

        String outContent = out.toString();
        JSONObject outJson = JSON.parseObject(outContent);

        Assert.assertEquals(5, outJson.getIntValue("level"));
        Assert.assertEquals("fake-service-name", outJson.getString("service"));
        Assert.assertEquals("This is a short message", outJson.getString("short_message"));
        Assert.assertEquals("This supposed to be a long message", outJson.getString("full_message"));
        Assert.assertEquals("value1", outJson.getString("_key1"));
        Assert.assertEquals("value2", outJson.getString("_key2"));

        Assert.assertTrue(outJson.containsKey("host"));
        Assert.assertTrue(outJson.containsKey("timestamp"));
        Assert.assertTrue(outJson.containsKey("version"));
    }

    @Test
    public void testInfoMessage() {
        Chillog.info("This is a short message", "This supposed to be a long message",
                "key1", "value1", "key2", "value2");

        String outContent = out.toString();
        JSONObject outJson = JSON.parseObject(outContent);

        Assert.assertEquals(6, outJson.getIntValue("level"));
        Assert.assertEquals("fake-service-name", outJson.getString("service"));
        Assert.assertEquals("This is a short message", outJson.getString("short_message"));
        Assert.assertEquals("This supposed to be a long message", outJson.getString("full_message"));
        Assert.assertEquals("value1", outJson.getString("_key1"));
        Assert.assertEquals("value2", outJson.getString("_key2"));

        Assert.assertTrue(outJson.containsKey("host"));
        Assert.assertTrue(outJson.containsKey("timestamp"));
        Assert.assertTrue(outJson.containsKey("version"));
    }

    @Test
    public void testDebugMessage() {
        Chillog.debug("This is a short message", "This supposed to be a long message",
                "key1", "value1", "key2", "value2");

        String outContent = out.toString();
        JSONObject outJson = JSON.parseObject(outContent);

        Assert.assertEquals(7, outJson.getIntValue("level"));
        Assert.assertEquals("fake-service-name", outJson.getString("service"));
        Assert.assertEquals("This is a short message", outJson.getString("short_message"));
        Assert.assertEquals("This supposed to be a long message", outJson.getString("full_message"));
        Assert.assertEquals("value1", outJson.getString("_key1"));
        Assert.assertEquals("value2", outJson.getString("_key2"));

        Assert.assertTrue(outJson.containsKey("host"));
        Assert.assertTrue(outJson.containsKey("timestamp"));
        Assert.assertTrue(outJson.containsKey("version"));
    }

    @Test
    public void testConvertToPairsNullParameter() {
        Map<String, Object> pairs = Chillog.convertToMap(null);

        Assert.assertEquals(0, pairs.size());
    }

    @Test
    public void testConvertToPairsNoContents() {
        Map<String, Object> pairs = Chillog.convertToMap(new String[] {});

        Assert.assertEquals(0, pairs.size());
    }

    @Test
    public void testConvertToPairsTwoContents() {
        Map<String, Object> pairs = Chillog.convertToMap(new String[]{"key", "value"});

        Assert.assertEquals(1, pairs.size());
        Assert.assertEquals("value", pairs.get("key"));
    }

    @Test
    public void testConvertToPairsOddContents() {
        Map<String, Object> pairs = Chillog.convertToMap(new String[]{"key1", "value1", "key2"});

        Assert.assertEquals(2, pairs.size());
        Assert.assertEquals("value1", pairs.get("key1"));
        Assert.assertEquals("", pairs.get("key2"));
    }

    @Test
    public void testGenerateJsonString() {
        String logJsonString = Chillog.generateJsonString(
                "A short message",
                null,
                System.currentTimeMillis(),
                Chillog.Level.ALERT,
                Chillog.EMPTY_PAIRS);

        JSONObject logJson = JSON.parseObject(logJsonString);
        Assert.assertTrue(logJson.containsKey("host"));
        Assert.assertTrue(logJson.containsKey("timestamp"));
        Assert.assertEquals(1, logJson.getIntValue("version"));
        Assert.assertEquals("fake-service-name", logJson.getString("service"));
        Assert.assertEquals(Chillog.Level.ALERT.getValue(), logJson.getIntValue("level"));
        Assert.assertEquals("A short message", logJson.getString("short_message"));

        // Make sure there are no excessive pair
        Assert.assertEquals(6, logJson.size());
    }

    @Test
    public void testGenerateJsonStringWithFullMessage() {
        String logJsonString = Chillog.generateJsonString(
                "A short message",
                "This supposed to be a really\nreally long.\n",
                System.currentTimeMillis(),
                Chillog.Level.ALERT,
                Chillog.EMPTY_PAIRS);

        JSONObject logJson = JSON.parseObject(logJsonString);
        Assert.assertTrue(logJson.containsKey("host"));
        Assert.assertTrue(logJson.containsKey("timestamp"));
        Assert.assertEquals(1, (int) logJson.getInteger("version"));
        Assert.assertEquals("fake-service-name", logJson.getString("service"));
        Assert.assertEquals(Chillog.Level.ALERT.getValue(), (int) logJson.getInteger("level"));
        Assert.assertEquals("A short message", logJson.getString("short_message"));
        Assert.assertEquals("This supposed to be a really\nreally long.\n", logJson.getString("full_message"));

        // Make sure there are no excessive pair
        Assert.assertEquals(7, logJson.size());
    }

    @Test
    public void testGenerateJsonStringWithAdditionalFields() {
        Map<String, Object> additionalFields = new HashMap<>();
        additionalFields.put("key1", "value1");
        additionalFields.put("key2", "value2");

        String logJsonString = Chillog.generateJsonString(
                "A short message",
                "This supposed to be a really\nreally long.\n",
                System.currentTimeMillis(),
                Chillog.Level.ALERT,
                additionalFields);

        JSONObject logJson = JSON.parseObject(logJsonString);
        Assert.assertTrue(logJson.containsKey("host"));
        Assert.assertTrue(logJson.containsKey("timestamp"));
        Assert.assertEquals(1, (int) logJson.getInteger("version"));
        Assert.assertEquals("fake-service-name", logJson.getString("service"));
        Assert.assertEquals(Chillog.Level.ALERT.getValue(), (int) logJson.getInteger("level"));
        Assert.assertEquals("A short message", logJson.getString("short_message"));
        Assert.assertEquals("This supposed to be a really\nreally long.\n", logJson.getString("full_message"));
        Assert.assertEquals("value1", logJson.getString("_key1"));
        Assert.assertEquals("value2", logJson.getString("_key2"));

        // Make sure there are no excessive pair
        Assert.assertEquals(9, logJson.size());
    }

    @Test
    public void testGenerateJsonStringWithAdditionalContainsNull() {
        Map<String, Object> additionalFields = new HashMap<>();
        additionalFields.put("key1", "value1");
        additionalFields.put("key2", null);

        String logJsonString = Chillog.generateJsonString(
                "A short message",
                "This supposed to be a really\nreally long.\n",
                System.currentTimeMillis(),
                Chillog.Level.ALERT,
                additionalFields);

        JSONObject logJson = JSON.parseObject(logJsonString);
        Assert.assertTrue(logJson.containsKey("host"));
        Assert.assertTrue(logJson.containsKey("timestamp"));
        Assert.assertEquals(1, (int) logJson.getInteger("version"));
        Assert.assertEquals("fake-service-name", logJson.getString("service"));
        Assert.assertEquals(Chillog.Level.ALERT.getValue(), (int) logJson.getInteger("level"));
        Assert.assertEquals("A short message", logJson.getString("short_message"));
        Assert.assertEquals("This supposed to be a really\nreally long.\n", logJson.getString("full_message"));
        Assert.assertEquals("value1", logJson.getString("_key1"));
        Assert.assertEquals("NULL", logJson.getString("_key2"));

        // Make sure there are no excessive pair
        Assert.assertEquals(9, logJson.size());
    }

    @Test
    public void testGenerateJsonStringWithAdditionalReservedField() {
        Map<String, Object> additionalFields = new HashMap<>();
        additionalFields.put("id", "value");

        String logJsonString = Chillog.generateJsonString(
                "A short message",
                "This supposed to be a really\nreally long.\n",
                System.currentTimeMillis(),
                Chillog.Level.ALERT,
                additionalFields);

        JSONObject logJson = JSON.parseObject(logJsonString);
        Assert.assertTrue(logJson.containsKey("host"));
        Assert.assertTrue(logJson.containsKey("timestamp"));
        Assert.assertFalse(logJson.containsKey("_id"));
        Assert.assertEquals(1, (int) logJson.getInteger("version"));
        Assert.assertEquals("fake-service-name", logJson.getString("service"));
        Assert.assertEquals(Chillog.Level.ALERT.getValue(), (int) logJson.getInteger("level"));
        Assert.assertEquals("A short message", logJson.getString("short_message"));
        Assert.assertEquals("This supposed to be a really\nreally long.\n", logJson.getString("full_message"));
        Assert.assertEquals("value", logJson.getString("__id"));

        // Make sure there are no excessive pair
        Assert.assertEquals(8, logJson.size());
    }

    /**
     * Sets a new environment variables. Taken from http://stackoverflow.com/a/7201825
     *
     * @param newEnvironmentVariables The new environment variables to be set.
     */
    private static void setEnv(Map<String, String> newEnvironmentVariables) {
        try
        {
            Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
            Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
            theEnvironmentField.setAccessible(true);
            Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
            env.putAll(newEnvironmentVariables);
            Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
            theCaseInsensitiveEnvironmentField.setAccessible(true);
            Map<String, String> cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
            cienv.putAll(newEnvironmentVariables);
        }
        catch (NoSuchFieldException e)
        {
            try {
                Class[] classes = Collections.class.getDeclaredClasses();
                Map<String, String> env = System.getenv();
                for(Class cl : classes) {
                    if("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
                        Field field = cl.getDeclaredField("m");
                        field.setAccessible(true);
                        Object obj = field.get(env);
                        Map<String, String> map = (Map<String, String>) obj;
                        map.clear();
                        map.putAll(newEnvironmentVariables);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
