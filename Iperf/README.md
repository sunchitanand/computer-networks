# Iperfer
Network bandwidth measuring tool. This version of Iperf uses sockets to measure the bandwidth at client and server ends and also detects any data loss th
that might have occured during the communication process.

When operating in client mode, Iperfer will send TCP packets to a specific host for a specified time window and track how much data was sent during that time frame; it will calculate and display the bandwidth based on how much data was sent in the elapsed time. When operating in server mode, Iperfer will receive TCP packets and track how much data was received during the lifetime of a connection; it will calculate and display the bandwidth based on how much data was received and how much time elapsed between received the first and last byte of data

## How to Run
Connect the client and the server to the same network - wired or wireless. 

### Client Mode
To operate Iperfer in client mode, it should be invoked as follows:

```
java Iperfer -c -h <server hostname> -p <server port> -t <time>
```

* -c indicates this is the iperf client which should generate data.
* server hostname is the hostname or IP address of the iperf server which will consume data
* server port is the port on which the remote host is waiting to consume data
* time is the duration in seconds for which data should be generated

### Server Mode 
To operate Iperfer in server mode, it should be invoked as follows:
```
java Iperfer -s -p <listen port>
```

* -s indicates this is the iperf server which should consume data
* listen port is the port on which the host is waiting to consume data; the port should be in the range 1024 ≤ listen port ≤ 65535
* You can use the presence of the -s option to determine Iperfer should operate in client mode.
