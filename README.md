# server
Simple socket server with ACK and no heartbeats.
* Mock Server accepts ANYTHING and sends first 5 bytes+ACK as acknowledgement. For example, if <0x02>00001!CONTAINER-1<0x03> is sent, 00001ACK will be the ACK
* If client connects and does not send anything for 3 minutes, client will be disconnected
* STX and ETX are required as start-of-stream and end-of-stream markers
* Logs the receipt time of each message from each client (IP:Port), logs will be mixed up in case of multiple clients
