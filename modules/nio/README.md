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

标志channel上操作的某个状态，通过它可以获取多路复用器Selector上面的该状态位上的所有就绪channel集合。

### Future

代表IO操作的结果，通过该类可以获取操作结果，可以设置同步等待，异步获取，或者超时时间设置。
