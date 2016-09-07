# java-chillog

Java-Chillog is a logging library based on GELF 2 logging format. Its main job are:

1. Automatically format message to conform with Chillog standard.
2. Send the formatted message to either STDOUT or STDERR (depending on which API is called).
 
 
## API

### Main API

The Main API is based on syslog severity standard, with the notable exception of no "EMERGENCY" severity. So, the API 
consists of:

1. Alert
2. Critical
2. Error
3. Warning
4. Notice
5. Info
6. Debug

For each severity, there are 3 flavors. For example: For "alert" severity, there are 3 options:

1. `Chillog.alert(String shortMessage)`

    Prints a Chillog-formatted log containing short message.
    For example, executing `Chillog.alert("Logging test");` will print the line below to STDERR:

    `{"short_message":"Logging test","level":1,"service":"ChillogTest","host":"chillog.local","version":1,"timestamp":1472787830594}`

2. `Chillog.alert(String shortMessage, String fullMessage, String... keyValuePairs)`

    Prints a Chillog-formatted log containing short message, full message, and possible additional information in 
    varargs format. For example: executing `Chillog.alert("Logging test", "Longer description", "first var", "content", "second var", "content");`
    will print the line below to STDERR:

    `{"short_message":"Logging test","level":1,"full_message":"Longer description","service":"ChillogTest","host":"chillog.local","version":1,"timestamp":1473068800736,"_first var":"content","_second var":"content"}`
    
3. `Chillog.alert(String shortMessage, String fullMessage, Map<String, Object> keyValuePairs)`

    Prints a Chillog-formatted log containing short message, full message, and possible additional information in `Map`
    object. For example: executing `Chillog.alert("Logging test", "Longer description", Chillog.map("user_id", 1, "ip_address", InetAddress.getLocalHost()));` 
    will print the line below to STDERR:
    
    `{"short_message":"Logging test","level":1,"full_message":"Longer description","service":"ChillogTest","host":"chillog.local","version":1,"timestamp":1473114792652,"_user_id":"1","_ip_address":"my.local/192.168.1.1"}`
    
    The caller should use this last flavor if the caller wants to log arbitrary `Object`. This library, then, will use
    `toString()` to print the content.


### Helper API

There only one helper API:

1. `Chillog.map(Object... keyValuePairs)`

    Creates a `Map<String, Object>` from varargs. This method is intended to be paired with the 3rd flavor of logging 
    API to create Map object. 
    
    The format must interleaving `String` and `Object`.
    For example, executing `Chillog.map("key1", 1, "key2", "value2", "key3", new Something(3);`
    will produce `Map<String, Object>` that conceptually equivalent by executing:
    
    ```
    Map<String, Object> result = new HashMap<>();
    result.put("key1", 1);
    result.put("key2", "value2");
    result.put("key3", new Something(3);
    ```

## Test

To test, simply run `./test.sh`. The result will be JUnit unit test report and JaCoCo coverage report.

The unit test report is located at `./build/test-results/test/TEST-id.skyfish.chillog.ChillogTest.xml`

The coverage report is located at `./build/reports/jacoco/test/jacocoTestReport.xml`
