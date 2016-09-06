# java-chillog

Java-Chillog is a logging library based on GELF 2 logging format. Its main job are:

1. Automatically format message to conform with Chillog standard.
2. Send the formatted message to either STDOUT or STDERR (depending on which API is called).
 
 
## API

The API is based on syslog severity standard, with the notable exception of no "EMERGENCY" severity. So, the API 
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
    For example, executing `Chillog.alert("Logging test");` prints this line to STDERR:

    `{"short_message":"Logging test","level":1,"service":"ChillogTest","host":"chillog.local","version":1,"timestamp":1472787830594}`

2. `Chillog.alert(String shortMessage, String fullMessage, String... keyValuePairs)`

    Prints a Chillog-formatted log containing short message, full message, and possible additional information in 
    varargs format. For example: executing 
    `Chillog.alert("Logging test", "Longer description", "first var", "content", "second var", "content");` prints this 
    line to STDERR:

    `{"short_message":"Logging test","level":1,"full_message":"Longer description","service":"ChillogTest","host":"chillog.local","version":1,"timestamp":1473068800736,"_first var":"content","_second var":"content"}`
    
3. `Chillog.alert(String shortMessage, String fullMessage, Map<String, Object> keyValuePairs)`

    Prints a Chillog-formatted log containing short message, full message, and possible additional information in `Map`
    object. For example: executing
    `Chillog.alert("Logging test", "Longer description", Chillog.map("user_id", 1, "ip_address", InetAddress.getLocalHost()));` 
    prints this line to STDERR:
    
    `{"short_message":"Logging test","level":1,"full_message":"Longer description","service":"ChillogTest","host":"chillog.local","version":1,"timestamp":1473114792652,"_user_id":"1","_ip_address":"my.local/192.168.1.1"}`
    
    The caller should use this last flavor if the caller wants to log arbitrary `Object`. This library, then, will use
    `toString()` to print the content.
