学习笔记
GC日志解读
1、java -XX:+PrintGCDetails -XX:+PrintGCDateStamps  -Xloggc:gc.demo.log GCLogAnalysis
//JVM信息
OpenJDK 64-Bit Server VM (25.252-b09) for windows-amd64 JRE (1.8.0_252-Huawei_JDK_V100R001C00SPC192B002-b09), built on Apr 15 2020 20:00:00 by "Jenkins" with MS VC++ 16.2.5 (VS2019)
//物理内存16GB，11.8GB Free，Swap内存19GB，14GB Free
Memory: 4k page, physical 16654948k(11889732k free), swap 19145316k(14266212k free)
//命令行参数：初始堆内存166MB，最大对内存4GB（物理内存的1/4）,开启打印GC，开启打印GC时间戳，开启打印GC详细信息，开启压缩类指针，开启压缩普通类对象，Allocate large pages individually for better affinity？，默认使用并行GC
CommandLine flags: -XX:InitialHeapSize=266479168 -XX:MaxHeapSize=4263666688 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseParallelGC
//GC发生时间，Allocation Failure GC发生原因
//65536K->10743K(76288K)] 65536K->26218K(251392K), 0.0075743 secs 表示年轻代分配大小是76288K即76MB，发生GC时年轻代使用65536K即65MB，使用比例65536/76288=85.9%，GC之后年轻代从65MB减少到10MB
//总堆分配大小251MB，GC时使用65MB（与年轻代一样，首次只有Young区使用），GC后降低到26MB，有16MB对象从Young区提升到Old区
2020-10-28T22:05:31.527+0800: 0.171: [GC (Allocation Failure) [PSYoungGen: 65536K->10743K(76288K)] 65536K->26218K(251392K), 0.0075743 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2020-10-28T22:05:31.553+0800: 0.197: [GC (Allocation Failure) [PSYoungGen: 76279K->10737K(141824K)] 91754K->49311K(316928K), 0.0132129 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2020-10-28T22:05:31.623+0800: 0.267: [GC (Allocation Failure) [PSYoungGen: 141809K->10748K(141824K)] 180383K->91375K(316928K), 0.0173053 secs] [Times: user=0.00 sys=0.00, real=0.02 secs] 
2020-10-28T22:05:31.660+0800: 0.304: [GC (Allocation Failure) [PSYoungGen: 141820K->10745K(272896K)] 222447K->135463K(448000K), 0.0208732 secs] [Times: user=0.00 sys=0.05, real=0.02 secs] 
//Young区和总堆大小都在增大，Full GC时Young直接从10MB到0，Old区从124MB较少到122MB，减少较少，总堆从135MB减少到122MB，元数据区3MB不变
2020-10-28T22:05:31.681+0800: 0.325: [Full GC (Ergonomics) [PSYoungGen: 10745K->0K(272896K)] [ParOldGen: 124717K->122698K(254976K)] 135463K->122698K(527872K), [Metaspace: 2987K->2987K(1056768K)], 0.0196457 secs] [Times: user=0.01 sys=0.00, real=0.02 secs] 
2020-10-28T22:05:31.782+0800: 0.435: [GC (Allocation Failure) [PSYoungGen: 262144K->10751K(272896K)] 384842K->200674K(527872K), 0.0206791 secs] [Times: user=0.00 sys=0.06, real=0.03 secs] 
2020-10-28T22:05:31.812+0800: 0.456: [Full GC (Ergonomics) [PSYoungGen: 10751K->0K(272896K)] [ParOldGen: 189923K->176852K(352768K)] 200674K->176852K(625664K), [Metaspace: 2987K->2987K(1056768K)], 0.0226894 secs] [Times: user=0.06 sys=0.00, real=0.02 secs] 
2020-10-28T22:05:31.869+0800: 0.515: [GC (Allocation Failure) [PSYoungGen: 262144K->77986K(522240K)] 438996K->254839K(875008K), 0.0208681 secs] [Times: user=0.02 sys=0.06, real=0.02 secs] 
2020-10-28T22:05:32.005+0800: 0.661: [GC (Allocation Failure) [PSYoungGen: 521890K->96237K(543744K)] 698743K->359597K(896512K), 0.0461652 secs] [Times: user=0.03 sys=0.17, real=0.06 secs] 
2020-10-28T22:05:32.063+0800: 0.707: [Full GC (Ergonomics) [PSYoungGen: 96237K->0K(543744K)] [ParOldGen: 263359K->271997K(483328K)] 359597K->271997K(1027072K), [Metaspace: 2987K->2987K(1056768K)], 0.0352627 secs] [Times: user=0.06 sys=0.00, real=0.04 secs] 
2020-10-28T22:05:32.147+0800: 0.801: [GC (Allocation Failure) [PSYoungGen: 447488K->132111K(891392K)] 719485K->404109K(1374720K), 0.0340925 secs] [Times: user=0.03 sys=0.11, real=0.04 secs] 
2020-10-28T22:05:32.315+0800: 0.963: [GC (Allocation Failure) [PSYoungGen: 868367K->171516K(907776K)] 1140365K->524471K(1391104K), 0.0859063 secs] [Times: user=0.02 sys=0.20, real=0.09 secs] 
Heap
 PSYoungGen      total 907776K, used 668487K [0x000000076b400000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 736256K, 67% used [0x000000076b400000,0x0000000789952ac0,0x0000000798300000)
  from space 171520K, 99% used [0x0000000798300000,0x00000007a2a7f3d0,0x00000007a2a80000)
  to   space 231424K, 0% used [0x00000007b1e00000,0x00000007b1e00000,0x00000007c0000000)
 ParOldGen       total 483328K, used 352954K [0x00000006c1c00000, 0x00000006df400000, 0x000000076b400000)
  object space 483328K, 73% used [0x00000006c1c00000,0x00000006d74ae9a8,0x00000006df400000)
 Metaspace       used 2994K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 314K, capacity 386K, committed 512K, reserved 1048576K