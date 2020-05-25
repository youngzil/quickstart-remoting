netty 提供了 OptionalSslHandler 支持一个问口同时支持 http和https, 但一般做法是开两个端口, 80 到http, 443 到 https,  nginx可配强制转到443



参考
https://www.jianshu.com/p/ed0177a9b2e3
https://github.com/netty/netty/blob/4.1/handler/src/main/java/io/netty/handler/ssl/OptionalSslHandler.java


