## 使用 add2line 定位问题

 -  add2line 工具的位置
      1. 在window系统下,so文件是 arm64, 使用的add2line 在 toolchains\aarch64-linux-android-4.9\prebuilt\windows-x86_64\bin 中
      2. add2line工具需要根据当前系统和 so文件支持的系统而定.
   
 -  add2line 命令
    1. `path/add2line -e so_path/xx.so 0000000000013174 0000000000012aa4 `
    2. 后面2个是 报错信息中的地址,  地址来源于crash 日志中 backtrace下的内容,只需要自己so文件中的地址
 ```
     2022-04-19 15:35:13.574 27548-27548/? A/DEBUG: backtrace:
     2022-04-19 15:35:13.574 27548-27548/? A/DEBUG:     #00 pc 0000000000013174  /data/app/com.ggg.mediafactory-DPxXf-9qEEarV2_6VRmH4w==/base.apk (offset 0x1015000) (VideoHandler::decode(_jobject*)+756)
     2022-04-19 15:35:13.574 27548-27548/? A/DEBUG:     #01 pc 0000000000012aa4  /data/app/com.ggg.mediafactory-DPxXf-9qEEarV2_6VRmH4w==/base.apk (offset 0x1015000) (videoCommandHandler(_JNIEnv*, _jobject*, _jobject*)+452)
 ```
    

## crash signal 说明

```
 SIGHUP 1  // 终端连接结束时发出(不管正常或非正常)
 SIGINT 2  // 程序终止(例如Ctrl-C)
 SIGQUIT 3 // 程序退出(Ctrl-\)
 SIGILL 4 // 执行了非法指令，或者试图执行数据段，堆栈溢出
 SIGTRAP 5 // 断点时产生，由debugger使用
 SIGABRT 6 // 调用abort函数生成的信号，表示程序异常
 SIGIOT 6 // 同上，更全，IO异常也会发出
 SIGBUS 7 // 非法地址，包括内存地址对齐出错，比如访问一个4字节的整数, 但其地址不是4的倍数
 SIGFPE 8 // 计算错误，比如除0、溢出
 SIGKILL 9 // 强制结束程序，具有最高优先级，本信号不能被阻塞、处理和忽略
 SIGUSR1 10 // 未使用，保留
 SIGSEGV 11 // 非法内存操作，与SIGBUS不同，他是对合法地址的非法访问，比如访问没有读权限的内存，向没有写权限的地址写数据
 SIGUSR2 12 // 未使用，保留
 SIGPIPE 13 // 管道破裂，通常在进程间通信产生
 SIGALRM 14 // 定时信号,
 SIGTERM 15 // 结束程序，类似温和的SIGKILL，可被阻塞和处理。通常程序如果终止不了，才会尝试SIGKILL
 SIGSTKFLT 16  // 协处理器堆栈错误
 SIGCHLD 17 // 子进程结束时, 父进程会收到这个信号。
 SIGCONT 18 // 让一个停止的进程继续执行
 SIGSTOP 19 // 停止进程,本信号不能被阻塞,处理或忽略
 SIGTSTP 20 // 停止进程,但该信号可以被处理和忽略
 SIGTTIN 21 // 当后台作业要从用户终端读数据时, 该作业中的所有进程会收到SIGTTIN信号
 SIGTTOU 22 // 类似于SIGTTIN, 但在写终端时收到
 SIGURG 23 // 有紧急数据或out-of-band数据到达socket时产生
 SIGXCPU 24 // 超过CPU时间资源限制时发出
 SIGXFSZ 25 // 当进程企图扩大文件以至于超过文件大小资源限制
 SIGVTALRM 26 // 虚拟时钟信号. 类似于SIGALRM, 但是计算的是该进程占用的CPU时间.
 SIGPROF 27 // 类似于SIGALRM/SIGVTALRM, 但包括该进程用的CPU时间以及系统调用的时间
 SIGWINCH 28 // 窗口大小改变时发出
 SIGIO 29 // 文件描述符准备就绪, 可以开始进行输入/输出操作
 SIGPOLL SIGIO // 同上，别称
 SIGPWR 30 // 电源异常
 SIGSYS 31 // 非法的系统调用

```


