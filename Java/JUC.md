# JUC

## 用户线程和守护线程

```java
 public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 正在运行..."
                    + (Thread.currentThread().isDaemon() ? "守护线程" : "用户线程"));
            while (true) {

            }
        }, "t1");
        thread.setDaemon(true);
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() +"\t ---end主线程");
    }
```

## 锁

### 乐观锁和悲观锁

悲观锁：使用线程时一定认为有其他线程来竞争，主要用在写操作

乐观锁：认为在使用数据时不会有其它线程修改数据或资源，版本号机制Version；CAS算法；用在读操作

#### synchronized

作用与：

1. 实例方法
2. 代码块：对括号里的配置加锁；底层实现 monitorenter 和 monitorexit
3. 静态方法：对当前类加锁

---



1. 一个对象里面如果有多个synchronized方法，某一时刻，只要有一个线程去调用其中的一个synchronized方法，其他线程都只能等待
2. 普通方法与同步锁无关
3. 不同的对象，用的不是同一把锁，情况会有变化
4. ’
   1. 对于普通方法，锁的是当前实例的对象，通常是this
   2. 对于静态方法，锁的是Class
   3. 对于同步方法块，锁的是synchronized括号内的对象
   4. 当一个线程试图访问同步代码块时他首先必须得到锁，正常退出或抛出异常时必须释放锁。
5. 静态方法和普通方法之前不存在竞争条件



#### 公平锁和非公平锁

公平锁：线程先到先得

非公平锁：减少了CPU的空闲时间

### 可重入锁（递归锁）

#### 基础概念：

一个线程递归进入子方法；同一个线程拥有同一把锁

#### 种类：

隐式锁：synchronized关键字使用的锁，默认是可重入锁

显式锁：RrentrantLock

### 死锁

```java
public class DeadLockDemo {
    public static void main(String[] args) {
        Object objectA = new Object();
        Object objectB = new Object();

        new Thread(() -> {
            synchronized (objectA){
                System.out.println("成功获取A锁");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (objectB){
                    System.out.println("尝试获取B锁");
                }
            }
        },"A").start();

        new Thread(() -> {
            synchronized (objectB){
                System.out.println("成功获取B锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (objectA){
                    System.out.println("尝试获取A锁");
                }
            }
        },"B").start();
    }
}
```

### JMM内存模型

1. 实现线程和主内存的抽象关系
2. 屏蔽各个硬件和OS的差异

#### 特性：

1. 可见性：当一个线程修改了属性，其他线程可见；拷贝回线程本地变量，修改后写回主内存
2. 原子性：一个操作不能被打断，多线程下，操作不能被其他线程干扰
3. 有序性：防止JVM对指令进行重排



#### Happens-Before

包含可见性和有序性的约束

关键点：

1. 如果一个操作Happens-Before另一个操作，那么第一个操作的执行结果将对于第二个操作可见，而且第一个的操作顺序排在第二个操作之前
2. 两个操作之间存在Happens-Before关系，并不意味着一定要按照Happens-Before的原则制定的顺序来执行，如果重排序之后的执行结果与Happens-Before关系来执行的结果一致，那么这种重排序并不非法



#### 8条

1. 次序规则：一个线程内，按照代码顺序，写在前面的操作
2. 锁定规则：一个unlock操作先行发生于后面
3. volatile变量规则：前面的写操作对后面的读操作是可见的
4. 传递规则：如果A先于B，B先于C，那么A先于C
5. 线程启动规则：Thread对象的Start方法先发生于子线程的每一个动作
6. 线程中断规则：
   1. 对线程Interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生；
   2. 可以通过Thread.Interrupt()检测到是否发生中断
   3. 先调用interrupt()方法设置过中断标志位，才能检测到中断发送
7. 线程终止规则：线程中的所有操作都咸鱼发生于对此线程的终止检测，我们可以通过isAlive()等手段检测线程是否已终止运行
8. 对象终结规则：一个对象的初始化完成先行发生于他的finalize()方法的开始 

#### Volatile

特点：可见性、有序性;无法保证原子性

内存屏障：

​	内存屏障之前的所有写操作都要写回主内存

​	内存屏障之后的所有读操作都能获得内存屏障之前的所有写操作的结果（可见性）

粗分：读屏障和写屏障；写指令后插入写屏障，强制把写缓冲区的数据刷回到主内存中

细分：LoadLoad、StoreStore、LoadStore、StoreLoad



**读之后不重排，写之前不重排，写后读不重排**

#### 总结



![image-20230331200532735](https://oss.iseenu.icu:443/typora/2023/03/31/202303312005817.png)

![image-20230331200450046](https://oss.iseenu.icu:443/typora/2023/03/31/202303312004131.png)



![image-20230331200421471](https://oss.iseenu.icu:443/typora/2023/03/31/202303312004611.png)



## CAS

Compare And Swap:核心实现就是Unsafe类；比较更新的变量值V和预期值E，相等才会将V的值更新为N，若不相等就自旋



**缺点：**

1. 循环时间长开销大
2. ABA问题 



## LongAdder

无竞争下和AtomicLong性能相同

底层：Base+Cell数组

base变量：低并发，直接累加

cell数组：高并发，累加各项





![image-20230403161452036](https://oss.iseenu.icu:443/typora/2023/04/03/202304031614173.png)



## Thread Local

定义：线程局部变量



Thread包含Thread Local，TL包含Thread  Map



强引用：即使OOM都不回收

软引用：内存不够才回收

弱引用：只要有GC就被回收

虚引用：虚引用必会被回收



K是虚引用，V是强引用

引用链：Thread Ref -> Thread -> ThreaLocalMap -> Entry -> value

ThreadLocal里面使用了一个存在弱引用的map, map的类型是ThreadLocal.ThreadLocalMap. Map中的key为一个threadlocal实例。这个Map的确使用了弱引用，不过弱引用只是针对key。每个key都弱引用指向threadlocal。 当把threadlocal实例置为null以后，没有任何强引用指向threadlocal实例，所以threadlocal将会被gc回收。



**在ThreadLocal的`get(),set(),remove()`的时候都会清除线程ThreadLocalMap里所有key为null的value**。



## Java对象内存布局和对象头

在堆内存中的存储布局

#### 对象头

16字节

![image-20230409203813155](https://oss.iseenu.icu:443/typora/2023/04/09/202304092038336.png)

#### 实例数据

#### 对齐填充

不够八位，进行填充



## Synchronization

###  锁的升级过程

无锁->偏向锁->轻量级锁->重量级锁



影响性能



### 偏向锁

偏向自己的线程，Only One

![775136-20220111112403663-1390941402](https://oss.iseenu.icu:443/typora/2023/04/11/202304112010137.jpg)

图片引用：https://www.cnblogs.com/shiblog/p/15778619.html



### 轻锁

当关闭偏向锁功能或多线程竞争会导致偏向锁升级为轻量级锁



原理：线程如果自旋成功，那么下次自旋的最大次数会增加，因为JVM认为既然上次成功了，那么这一次也很大概率会成功。反之，如果很少会自旋成功，那么下次会减少自旋的次数甚至不自旋，避免CPU空转。



### 重锁





### JIT对锁的优化

#### 锁膨胀

锁膨胀是指 synchronized 从无锁升级到偏向锁，再到轻量级锁，最后到重量级锁的过程，它叫做锁膨胀也叫做锁升级

#### 锁消除

对于无意义的锁，编译器将会对其消除

```java
public String method() {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < 10; i++) {
        sb.append("i:" + i);
    }
    return sb.toString();
}
```



#### 锁粗化

对重复加同一个锁的复杂流程进行优化

```java
public class StringBufferTest {
 
    StringBuffer stringBuffer = new StringBuffer();
 
    public void append(){
        stringBuffer.append("a").append("b").append("c");
    }

```



### AQS 抽象队列同步器

AQS是一个CLH（双向FIFO队列）

![image-20230412205522776](https://oss.iseenu.icu:443/typora/2023/04/12/202304122102274.png)

和AQS相关的：

1. ReentrantLock
2. CountDownLatch
3. ReentrantReadWriteLock
4. Semaphore
5. ...



code:

```java
public abstract class AbstractQueuedSynchronizer extends AbstractOwnableSynchronizer implements java.io.Serializable {
  protected AbstractQueuedSynchronizer() { }
  
  private transient volatile Node head;  // 懒加载，只有在发生竞争的时候才会初始化；
  private transient volatile Node tail;  // 同样懒加载；
  private volatile int state;  // 自定义的锁状态，可以用来表示锁的个数，以实现互斥锁和共享锁；
}
```



![image-20230415193648298](https://oss.iseenu.icu:443/typora/2023/04/15/202304151936378.png)



```java
static final class Node {
  static final Node SHARED = new Node();  // 共享模式
  static final Node EXCLUSIVE = null;     // 互斥模式

  static final int CANCELLED =  1; // 表示线程取消获取锁
  static final int SIGNAL    = -1; // 表示后继节点需要被唤醒
  static final int CONDITION = -2; // 表示线程位于条件队列
  static final int PROPAGATE = -3; // 共享模式下节点的最终状态，确保在doReleaseShared的时候将共享状态继续传播下去

  /**
   * 节点状态（初始为0，使用CAS原则更新）
   * 互斥模式：0，SIGNAL，CANCELLED
   * 共享模式：0，SIGNAL，CANCELLED，PROPAGATE
   * 条件队列：CONDITION
   */
  volatile int waitStatus;
  
  volatile Node prev;     // 前继节点
  volatile Node next;     // 后继节点
  volatile Thread thread; // 取锁线程
  Node nextWaiter;        // 模式标识，取值：SHARED、EXCLUSIVE

  // Used by addWaiter，用于添加同队队列
  Node(Thread thread, Node mode) {   
    this.nextWaiter = mode;
    this.thread = thread;
  }

  // Used by Condition，同于添加条件队列
  Node(Thread thread, int waitStatus) { 
    this.waitStatus = waitStatus;
    this.thread = thread;
  }
}
```



### 运行逻辑

![image-20230415194049496](https://oss.iseenu.icu:443/typora/2023/04/15/202304151940556.png)

#### 源码层：

Node 中的waitState为1表示占用

![image-20230413224324458](https://oss.iseenu.icu:443/typora/2023/04/13/202304132249445.png)

默认非公平锁。True才代表是公平锁



公平锁和非公平锁的lock唯一区别就在于公平锁在获取同步状态时多了一个限制条件

---

![image-20230414212843473](https://oss.iseenu.icu:443/typora/2023/04/14/202304142130196.png)

tryAcquire为主要方法

addWaiter()

![image-20230414215759177](https://oss.iseenu.icu:443/typora/2023/04/14/202304142157421.png)



```
ReentrantLock 构造方法可以指定是否是公平锁
```

![image-20230415191916859](https://oss.iseenu.icu:443/typora/2023/04/15/202304151919954.png)

#### 入队

```java
private Node addWaiter(Node mode) {
  Node node = new Node(Thread.currentThread(), mode); // SHARED、EXCLUSIVE
  // Try the fast path of enq; backup to full enq on failure
  Node pred = tail;
  if (pred != null) {
    node.prev = pred;
    if (compareAndSetTail(pred, node)) {
      pred.next = node;
      return node;
    }
  }
  enq(node);
  return node;
}
```

```java
private Node enq(final Node node) {
  for (;;) {
    Node t = tail;
    if (t == null) { // Must initialize
      if (compareAndSetHead(new Node())) // 此时head和tail才初始化
        tail = head;
    } else {
      node.prev = t;
      if (compareAndSetTail(t, node)) {
        t.next = node;
        return t;
      }
    }
  }
}
```

### 独占模式

#### 1.应用

```java
public class Mutex implements Lock {
  private final Sync sync = new Sync();
  private static final int lock = 1;
  private static final int unlock = 0;

  @Override
  public void lock() {
    sync.acquire(lock);
  }

  @Override
  public boolean tryLock() {
    return sync.tryAcquire(lock);
  }

  @Override
  public void unlock() {
    sync.release(unlock);
  }

  private static class Sync extends AbstractQueuedSynchronizer {
    @Override
    protected boolean isHeldExclusively() {
      return getState() == lock;
    }

    @Override
    public boolean tryAcquire(int acquires) {
      if (compareAndSetState(unlock, lock)) {
        setExclusiveOwnerThread(Thread.currentThread());
        return true;
      }
      return false;
    }

    @Override
    protected boolean tryRelease(int releases) {
      if (getState() == unlock)
        throw new IllegalMonitorStateException();
      setExclusiveOwnerThread(null);
      setState(unlock);
      return true;
    }
  }
}
```

#### 2.获取锁

对于独占模式取锁而言有一共有四中方式，

- **tryAcquire：** 快速尝试取锁，成功时返回true；这是独占模式必须要重写的方法，其他方式获取锁时，也会先尝试快速获取锁；同时 `tryAcquire` 也就决定了，这个锁时公平锁/非公平锁，可重入锁/不重冲入锁等；（比如上面的实例就是不可重入非公平锁，具体分析以后还会详细讲解）
- **acquire：** 不响应中断，阻塞获取锁；
- **acquireInterruptibly：** 响应中断，阻塞获取锁；
- **tryAcquireNanos：** 响应中断，超时阻塞获取锁；

![image-20230415222720683](https://oss.iseenu.icu:443/typora/2023/04/15/202304152227844.png)

```java
public final void acquire(int arg) {
  if (!tryAcquire(arg) &&                             // 首先尝试快速获取锁
       acquireQueued(addWaiter(Node.EXCLUSIVE), arg)) // 失败后入队，然后阻塞获取
    selfInterrupt();                                  // 最后如果取锁的有中断，则重新设置中断
}
```

```java
final boolean acquireQueued(final Node node, int arg) {
  boolean failed = true;
  try {
    boolean interrupted = false;           // 只要取锁过程中有一次中断，返回时都要重新设置中断
    for (;;) {
      final Node p = node.predecessor();   // 一直阻塞到前继节点为头结点
      if (p == head && tryAcquire(arg)) {  // 获取同步状态
        setHead(node);                     // 设置头结点，此时头部不存在竞争，直接设置
        // next 主要起优化作用，并且在入队的时候next不是CAS设置
        // 也就是通过next不一定可以准确取到后继节点，所以在唤醒的时候不能依赖next，需要反向遍历
        p.next = null; // help GC          
        failed = false;
        return interrupted;
      }
      if (shouldParkAfterFailedAcquire(p, node) && // 判断并整理前继节点
        parkAndCheckInterrupt())                   // 当循环最多第二次的时候，必然阻塞
        interrupted = true;
    }
  } finally {
    if (failed)  // 异常时取消获取
      cancelAcquire(node);
  }
}
```

```java
private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
  int ws = pred.waitStatus;
  if (ws == Node.SIGNAL) return true;
  if (ws > 0) {  // 大于0说明，前继节点异常或者取消获取，直接跳过；
    do {
      node.prev = pred = pred.prev;  // 跳过pred并建立连接
    } while (pred.waitStatus > 0);
    pred.next = node;
  } else {
    compareAndSetWaitStatus(pred, ws, Node.SIGNAL);  // 标记后继节点需要唤醒
  }
  return false;
```

##### acquireInterruptibly 方法

![image-20230416164209852](https://oss.iseenu.icu:443/typora/2023/04/16/202304161805661.png)

```java
public final void acquireInterruptibly(int arg) throws InterruptedException {
  if (Thread.interrupted()) throw new InterruptedException();  // 中断退出
  if (!tryAcquire(arg))           // 获取同步状态
    doAcquireInterruptibly(arg);  // 中断获取
}
```

```java
private void doAcquireInterruptibly(int arg) throws InterruptedException {
  final Node node = addWaiter(Node.EXCLUSIVE);   // 加入队尾
  boolean failed = true;
  try {
    for (;;) {
      final Node p = node.predecessor();
      if (p == head && tryAcquire(arg)) {
        setHead(node);
        p.next = null; // help GC
        failed = false;
        return;
      }
      if (shouldParkAfterFailedAcquire(p, node) &&   // 判断并整理前继节点
        parkAndCheckInterrupt())                     // 等待
        throw new InterruptedException();
    }
  } finally {
    if (failed)
      cancelAcquire(node);
  }
}
```

#### 3. 释放锁

释放锁时，判断有后继节点需要唤醒，则唤醒后继节点，然后退出；有唤醒的后继节点重新设置头结点，并标记状态

```java
public final boolean release(int arg) {
  if (tryRelease(arg)) {   // 由用户重写，尝试释放
    Node h = head;
    if (h != null && h.waitStatus != 0)
      unparkSuccessor(h);  // 唤醒后继节点
    return true;
  }
  return false;
}	
```



### 取消等待

#### 1.队列尾部取消

tail节点指向倒数第二个Node，释放末尾的Thread

#### 2.队列中间取消

#### 3.队列中一片取消

和队列思想一样。释放所需要的Node，并且维护双向链表的结构

## 无锁-独占锁-偏向锁-邮戳锁

### ReentrantReadWriteLock

一个资源能够被多个读线程访问，或者被一个写线程访问，但是不能同时存在读写线程

![image-20230416174010962](https://oss.iseenu.icu:443/typora/2023/04/16/202304161805107.png)

锁饥饿

锁降级



先都后写可行。先写后读全部锁死
