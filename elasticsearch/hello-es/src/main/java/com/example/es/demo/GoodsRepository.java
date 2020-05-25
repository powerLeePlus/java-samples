package com.example.es.demo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

//@Component
public interface GoodsRepository extends ElasticsearchRepository<GoodsInfo,Long> {
}