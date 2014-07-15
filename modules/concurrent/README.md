 ## java.util.concurrent

 ### Executor

 一种执行提交的Runnable tasks任务的对象，它提供了一种机制，可以对所提交的任务和这些任务如何执行进行解耦。通常情况下，Executor并不是严格要求是异步的，它有可能是被调用者所在的线程执行任务，一般来说，任务都会开启一个新线程来执行。

 Executor接口已经有一些默认的实现，如扩展接口ExecutorService，以及ThreadPoolExecutor类，以及Executors类，它提供了一些方便的工厂方法。

 ### ExecutorService

 对于Executor的扩展接口，该类可以产生Future对象，该对象可以跟踪异步执行任务的具体过程；当未使用时，建议关闭该对象，以释放资源；关于关闭动作，有2种，一种是立即组织处于等待执行状态的任务，以及尝试立即停止正在执行的任务，另外一种是在关闭之前允许以及在执行的任务执行完毕。

 执行提交的单个任务的方法是submit，该方法可以返回Future对象（该对象可以结束执行过程，或者等待任务完成）；当有一系列任务提交执行时，如果希望当有一个任务完成就返回，可以使用invokeAny，如果希望所有任务完成后再返回，就使用invokeAll。

 ### ThreadPoolExecutor

 在jdk并发包中的一个线程池的服务类，我们可以把实现了Runnable接口的任务放入其中执行，之后，有可能使用线程池中的线程执行，或者新建线程执行该任务，具体
 看配置策略。具体策略配置需要根据如下几个点以及实际场景进行决定。

  * corePoolSize
  * 最大线程数
  * 何种任务缓冲队列
  * 线程池满时拒绝处理handler的策略设定

  <pre>ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler)

不过多数情况下，程序员被建议使用Executors来获取线程池服务，而不是通过设定参数来直接new出对象来使用ThreadPoolExecutor.

 ### FutureTask

 查看jdk的说明文档，其适用的场景为异步计算情况下，可以开始或者结束该计算，也可以通过get方法异步获取计算结果，如果计算没有完成，那么get方法就会阻塞。

 另外，FutureTask还是对Runnable和Callable的一个封装，可以直接调用run方法执行任务，或者放入线程池中执行任务。即使多次执行run方法也会被视为执行一次任务调用。

 ### Semaphone

 ### Condition

 ### 拆分锁 CAS voliate AbstractQueuedSynchronizer

 ### Executors

 一个工具类，用来方便创建ThreadPoolExecutor。

  * newFixedThreadPool(int)

    创建固定大小的线程池，默认情况下，ThreadPoolExecutor中启动corePoolSize大小的线程后就一直运行，不会没有任务达到就停止运行线程；

    任务缓冲队列采用的是LinkedBlockingQueue，其大小为整数值的最大值，当运行的任务超出线程池的大小后，就会被放入该任务缓冲队列中等待空闲线程来执行，当放入的任务
    超出任务缓冲队列的最大值时，就报拒绝执行异常RejectedExecutionException。

  * newSingleThreadExecutor()

    创建大小只为1的线程池，固定线程池，使用时只有一个任务被执行，其他任务放入LinkedBlockingQueue中。

  * newCachedThreadPool()

    创建corePoolSize为0的线程池，最大线程数为整数的最大值，任务缓冲队列为SynchronousQueue，使用时，放入的任务会复用线程或者启用新线程执行，知道启动的线程数到达
    最大整数值时才会跑出RejectedExecutionException。

  * newScheduledThreadPool(int)

    创建指定大小的corePoolSize的线程池，最大线程数为整型的最大值。任务缓冲队列采用的是DelayedWordQueue。通常用于需要延迟执行的任务的场景，如在分布式环境下，
    异步操作需要超时回调的场景。
