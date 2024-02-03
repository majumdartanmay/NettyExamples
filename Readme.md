# Introduction

This repository will audit my learnings of Netty. My referece to learn Netty is the [Netty In Action](https://www.manning.com/books/netty-in-action) book. I will simply summarize my learnings in each chapter along with some example

## Part 1  -> Netty Concepts and Architechture

Before we start with actual netty implementations, we will discuss the philosophy of netty. 

### Networking in Java

Earlier networking in java meant dealing directlly with the C socket libraries. Look up the example at [BeforeNettyExample](/BeforeNettyExample) to understand what I mean.

You would notice that to create that example, we have to create one thread for each socket. Now, creating a thread for every connection is expensive as the memory needs to be allocated on the stack. A program like this will not scale.

So what are the alternatives?

* Java NIO *

This provides us methods for non blocking input output operations.


* Selectors *

The architechture looks like this

<pre>
Socket -> Read/Write --
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;\\
Socket -> Read/Write&emsp;&emsp;---> Selectors -> Thread
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;\/
Socket -> Read/Write--

Using the event notification API, selectors understand which _non blocking_ socket is ready for IO
</pre>
