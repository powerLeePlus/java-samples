# 注意
在多模块项目下，IDEA直接启动Application一定要配置 Working directory为 `$ModuleFileDir$`，否则会是父模块根目录，由此访问jsp页面会是404

![](.README_images/4c08d011.png)

原因见：[SpringBoot 多模块项目访问不到 JSP 页面问题解决方式](https://blog.csdn.net/ZBylant/article/details/91042569)

## 三种返回数据的方式
- Model
- ModelAndView
- ModelMap

## springmvc controller return视图的三种方式的区别

### 1、return "index"
会被视图解析器解析和处理，

然后到指定的页面中，实际上这一个过程是一个请求转发的过程，它们仍然在同一Request中

体现在浏览器：浏览器地址栏不变

### 2、return "forward:index"
不会被视图解析器解析处理，

此时请求会转发到另一个Controller，是同一个Request。

体现在浏览器：浏览器地址栏不变

### 3、return "redirect:index"
不会被视图解析器解析处理，

此时请求会重定向到另一个Controller，是一次新得Request

体现在浏览器：浏览器地址栏变化

通常用于防止表单重复提交，

同时也可以通过RedirectAttributes（springmvc 3.1Version 之后）携带参数，还可以直接通过url params携带，如: `return "redirect:index?username=lwq"`

**注意** ：`forward:` 和 `redirect:` 后含不含`/`是有区别的，
例如：`return "redirect:index"`和`return "redirect:/index"` 是不同的。