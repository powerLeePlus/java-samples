## 说明
使用spring-boot-starter-data-elasticsearch，通过RestHighLevelClient、ElasticsearchRepository、ElasticsearchRestTemplate操作es
## 版本
- spring-data-elasticsearch3.2.0 （对应springboot2.2.0，对应spring5.2.0 ）以下（不含）， 没有ElasticsearchRestTemplate。
- 这个版本
- 所以这个版本以下要使用ElasticsearchTemplate或ElasticsearchReporitory只能使用Transport 和 Node client连接（原因见《备注》）
- 这个版本以下要使用rest Client连接，只能用es原生API（RestHighLevelClient、RestClient）

## 备注
- ElasticsearchRestTemplate是基于RestHighLevelClient实现的，而ElasticsearchTemplate是基于Transport 和 Node client实现的
- 使用RestHighLevelClient连接，不能用于实例化ElasticsearchTemplate（可以通过其构造函数Client参数看出）
- 可以同时建立transport client 和 rest client 连接
- rest high level client使用 rest client 模块API构建