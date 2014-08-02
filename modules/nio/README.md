## NIO

*not blocking io*

### Buffer

一块内存区域，连续的

是NIO读写数据的中转地


### Channel

用于向buffer中写数据；或者从buffer中读取数据；是buffer的唯一接口对象

支持异步操作

channel不是一根管子连接在输入出两端，而是输入端对应一根管子，输出端对应一根管子，可以理解为一个通道


### Selector

异步I/O核心api


### SelectionKey