## NIO

*not blocking io*

### Buffer

一块内存区域，连续的，是NIO读写数据的中转地；Buffer不仅仅只是定义了读写的内存区域，而且还包括这块区域的元数据信息，包括大小，指针
以及其他操作。Buffer是个接口，实现该接口的有java中的主要基本数据类型，除了Boolean类型，另外，ByteBuffer是常用的一种Buffer。


### Channel

用于向buffer中写数据；或者从buffer中读取数据；是buffer的唯一接口对象；老的IO操作是基于流的，stream，而且是单向的，而channel
是双向的，一个channel上既可以读也可以写。

channel可以理解为连接服务端和客户端的一根管子，所有数据都通过该管道在服务端和客户端之间流通。channel可以分为两大类：SelectableChannel和FileChannel。


### Selector

异步非阻塞I/O核心api，通常被称为多路复用器。其主要功能是对在其上面注册的channel进行轮询，对于已经就绪的channel进行调度执行；如果某个channel上面有新的
TCP连接接入，或者读和写事件发生，那么此channel就处于就绪状态。

Selector上面可以同时轮询多个channel。


### SelectionKey

标志channel上操作的某个状态，通过它可以获取多路复用器Selector上面的该状态位上的所有就绪channel集合;一个SelectionKey对象就代表着在Selector上注册的一个SelectableChannel。

Selector负责维护在其上面注册的SelectionKey集合树；通过key()方法可以返回在其上面注册的所有的channel对应的key集合；通过selectedKeys()方法返回所有的
selected-key 集合，这个集合中对应的每个channel都已经准备就绪进行I/O操作。cancelled-key指的是那些已经结束了的key集合，但是其对应的channel并没有在Selector上面立即注销，
而是在下次Selector的selection操作时会被注销；cancelled-key集合不能被直接访问。

在Selector的selection操作期间，会删除掉cancelled-key，所有的key集合自身是无法直接修改的。


### Selection

这是Selector的核心操作，通过select(), select(long), 和 selectNow()三个方法，操作过程包括三步：

1. 在cancelled-key集合中的每个key会被删除，同时其对应的channel会被注销，当然是下次执行selection的时候，这一步让cancelled-key集合变为空
2. The underlying operating system is queried for an update as to the readiness of each remaining channel to perform any of the operations identified by its key's interest set as of the moment that the selection operation began. For a channel that is ready for at least one such operation, one of the following two actions is performed:

  If the channel's key is not already in the selected-key set then it is added to that set and its ready-operation set is modified to identify exactly those operations for which the channel is now reported to be ready. Any readiness information previously recorded in the ready set is discarded.

  Otherwise the channel's key is already in the selected-key set, so its ready-operation set is modified to identify any new operations for which the channel is reported to be ready. Any readiness information previously recorded in the ready set is preserved; in other words, the ready set returned by the underlying system is bitwise-disjoined into the key's current ready set.

  If all of the keys in the key set at the start of this step have empty interest sets then neither the selected-key set nor any of the keys' ready-operation sets will be updated.
3. If any keys were added to the cancelled-key set while step (2) was in progress then they are processed as in step (1).

  Whether or not a selection operation blocks to wait for one or more channels to become ready, and if so for how long, is the only essential difference between the three selection methods.


### Future

代表IO操作的结果，通过该类可以获取操作结果，可以设置同步等待，异步获取，或者超时时间设置。