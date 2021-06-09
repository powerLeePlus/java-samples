# springboot 异步请求和异步处理

## WEB异步
1. Callable<?>
2. DeferredResult<?>
3. WebAsyncTask<?>
## service异步
1. @Async + 返回void
2. @Async + 返回AsyncResult<?>（Future<?>）
3. @Async + 返回CompletableFuture<?>

### 比较
| | Callable | WebAsyncTask | DeferredResult | Async |
| :---- | :---- | :---- | :---- | :---- |
| 针对问题点 | 异步请求处理 | 异步请求处理 | 异步请求处理 | 异步方法 |
| 目标 | 释放Servlet容器线程 | 释放Servlet容器线程 | 释放Servlet容器线程 | 服务线程内多线程执行 |
| 拦截器 | CallableProcessingInterceptor | | DeferredResultProcessingInterceptor |
| 超时拦截器 | TimeoutCallableProcessingInterceptor | | TimeoutDeferredResultProcessingInterceptor | 
| 区别 | | 与Callable比较：可以为每个请求自定义超时时间、执行线程池| 与Callable和WebAsyncTask的比较：Callable和WebAsyncTask由spring自行set返回值及返回；而DeferredResult是在自定义线程执行，然后手动DeferredResult.setResult()触发请求返回 ||




