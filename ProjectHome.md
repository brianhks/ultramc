# Ultra MemCached Java Client #
## Why use Ultra MemCached client ##
Lets face it, the reason you are using memcached is to make your application faster.  Why would you use a client that wasn't built for speed?  Here is a list of performance items built into Ultra MemCached (some of which are yet to be done)
  * Complete use of java.nio package for communication
  * Non blocking IO so effective timeouts can be used
  * Direct ByteBuffer pooling - used to read/write messages to channels
  * Socket pooling
  * Ability for custom Object encoders
  * (TODO) Direct copy between String and ByteBuffer for packing messages
  * Ability to pass "noreply" to storage requests
  * (TODO) Asynchronous calling option
  * (TODO) Operation object recycling

## Currently implemented calls (more coming): ##
  * set
  * get (single value)
  * delete
  * flush\_all

## Ease of Use ##
Every client library should be flexible and easy to use.  Ultra MemCached is no exception.  Every operation call creates an object that you can set options on before running.  Here are some examples:
```
//simple set
client.createSet("key", "My Value").run();

//set with no reply
client.createSet("key", "My Value").setReply(false).run();

//set with custom value transcoder and timeout
client.createSet("key", "My Value").setTimeout(500).setValueTranscoder(myEncoder).run();
```
I think you get the idea.  What about get operations:
```
String value = (String)client.createGet("key").run().getValue();
```