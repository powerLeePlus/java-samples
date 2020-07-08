package com.example.es.demo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

//@Component
/**
 * ElasticsearchRepository可以做Elasticsearch的相关增删改查，用法和普通的CRUDRepository是一样的，
 * 这样就能统一ElasticSearch和普通的JPA操作，获得和操作mysql一样的代码体验。
 * 但是同时可以看到ElasticsearchRepository的功能是比较少的，简单查询够用，但复杂查询就不够了。
 * ————————————————
 * 而ElasticsearchTemplate则提供了更多的方法来完成更多的功能，也包括分页之类的，
 * 他其实就是一个封装好的ElasticSearch Util功能类，通过直接连接client来完成数据的操作。所以它是和相应的es client实现有关的。
 * ————————————————
 * @author lwq
 * @date 2020/7/8 0008
 */
public interface GoodsRepository extends ElasticsearchRepository<GoodsInfo,Long> {
}