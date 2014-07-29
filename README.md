RabbitMQ Routing with Maven and Eclipse
=======================================

Code based on the [RabbitMQ Routing](http://www.rabbitmq.com/tutorials/tutorial-four-java.html).

Minor modifications to make this more convenient to run from inside Eclipse with the help of m2e.

Changes from the original tutorial code:

  - pom.xml added to declare dependency on rabbitmq client library.
  - 'EmitLogDirect' runs in a loop and reads messages from System.in instead of commandline args.
  - Two classes 'ReceiveLogsAll' and 'ReceiveLogsError' instead of one, for receiving logs.
    This is just a convenience so they can be run without specifying any commandline args.

Importing into Eclipse
======================

You need M2E (maven tooling for Eclipse). Simply import the code in this project as an 
"Existing Maven Project" and it should be good to go.

Running
=======

Run any number of "EmitLogDirect", "ReceiveLogsAll" and "ReceiveLogsError" processes. 
Start a process by right click on the respective class and use "Run As >> 
Java Application". You can create additional Eclipse Console Views and pin one console 
to each process you start. Any messages you type in one of the "EmitLogDirect" console
will be broadcast. "RecieveLogsError" processes will only receive 'error' messages.
"ReceiveLogsAll" will receive "error", "info" and "warning" messages.
Besides valid 'error', 'info' and 'warning' severities, you can in fact also type
any arbitrary strings as 'severity'. Messages with invalid severities are sent,
but dropped by the broker because no one is listening to them.

Emit and Receive processes can be killed / started any time.