# 注意
在多模块项目下，IDEA直接启动Application一定要配置 Working directory为 `$ModuleFileDir$`，否则会是父模块根目录，由此访问jsp页面会是404

![](.README_images/4c08d011.png)

原因见：[SpringBoot 多模块项目访问不到 JSP 页面问题解决方式](https://blog.csdn.net/ZBylant/article/details/91042569)