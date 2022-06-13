# IDEA远程一键部署SpringBoot到Docker

### 参考
[IDEA远程一键部署SpringBoot到Docker](https://blog.csdn.net/mrlin6688/article/details/110522807)

<big>**两点问题**</big>

* 必须在Dockerfile指定EXPOSE 8990暴露端口，否则即使做了端口映射也不成功，docker ps查看没有映射成功，当然访问不了了。
* docker-maven-plugin通过docker deployment运行不起作用，所以要用maven-antrun-plugin，
但是直接用mvn docker:* 貌似可行（没有试）

