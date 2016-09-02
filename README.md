# java-chillog

Java-Chillog is a logging library based on GELF 2 logging format. Its main job are:

1. Automatically format message to conform with Chillog standard.
2. Send the formatted message to either STDOUT or STDERR (depending on which API is called).
 
 
## API

The API is based on syslog severity standard, with the notable exception of no "EMERGENCY" severity. So, the API consists of:

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

2. `Chillog.alert(String shortMessage, String fullMessage, String... keyValuePairs)`

    Prints a Chillog-formatted log containing short message, full message, and possible additional information in varargs format.
    
3. `Chillog.alert(String shortMessage, String fullMessage, Map<String, String> keyValuePairs)`

   Prints a Chillog-formatted log containing short message, full message, and possible additional information in `Map` object.
