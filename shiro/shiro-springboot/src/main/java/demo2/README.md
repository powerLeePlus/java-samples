# demo1
- 演示了登录认证和授权
- 用户名密码，权限保存在mysql中

认证授权相关数据库表如下：
![认证授权表](../../../../../.README_images/authentication_authorization_tables.jpg)

- 用了三种方式实现的授权
- 加了缓存
  - 使用shiro中默认EhCache实现缓存
  - 使用redis作为缓存
 
- session管理，并用redis保存session

- rememberMe功能
  
  - subject.isAuthenticated() 表示用户进行了身份验证登录的，即使用 Subject.login 进行了登录；
  
  - subject.isRemembered()：表示用户是通过记住我登录的，此时可能并不是真正的你（如你的朋友使用你的电脑，或者你的cookie 被窃取）在访问的；
  
  两者二选一，即 subject.isAuthenticated()==true，则subject.isRemembered()==false；反之一样。
 
 **建议：**
 
 - 访问一般网页：如个人在主页之类的，我们使用user 拦截器即可，user 拦截器只要用户登录(isRemembered() || isAuthenticated())过即可访问成功；
 
 - 访问特殊网页：如我的订单，提交订单页面，我们使用authc 拦截器即可，authc 拦截器会判断用户是否是通过Subject.login（isAuthenticated()==true
 ）登录的，如果是才放行，否则会跳转到登录页面叫你重新登录。

