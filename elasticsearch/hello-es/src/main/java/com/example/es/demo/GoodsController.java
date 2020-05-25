package com.example.es.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodsController {

    //每页数量
    private Integer PAGESIZE = 10;

    @Autowired
    private GoodsRepository goodsRepository;

    //http://localhost:8080/save
    // {"id":0,"name":"手机","description":"商品 手机"}
    @PostMapping("save")
    public String save(@RequestBody GoodsInfo goodsInfo) {
        goodsInfo.setId(System.currentTimeMillis());
        goodsRepository.save(goodsInfo);
        return "success";
    }

    //http://localhost:8888/delete?id=1525415333329
    @GetMapping("delete")
    public String delete(long id) {
        goodsRepository.deleteById(id);
        return "success";
    }

    //http://localhost:8888/update?id=1525417362754&name=修改&description=修改
    @GetMapping("update")
    public String update(long id, String name, String description) {
        GoodsInfo goodsInfo = new GoodsInfo(id,
                name, description);
        goodsRepository.save(goodsInfo);
        return "success";
    }

    //http://localhost:8080/getOne?id=1585206724867
    @GetMapping("getOne")
    public GoodsInfo getOne(long id) {
        Optional<GoodsInfo> goodsInfo = goodsRepository.findById(id);
        return goodsInfo.get();
    }

    @GetMapping("listAll")
    public List<GoodsInfo> listAll() {
        Iterable<GoodsInfo> goodsInfoIterable = goodsRepository.findAll();
        ArrayList<GoodsInfo> goodsInfos = new ArrayList<>();
        goodsInfoIterable.forEach(goodsInfo -> goodsInfos.add(goodsInfo));
        return goodsInfos;
    }

    //http://127.0.0.1:8080/getGoodsList?query=手机
    //http://localhost:8888/getGoodsList?query=商品&pageNumber=1
    //根据关键字"商品"去查询列表，name或者description包含的都查询
    @GetMapping("getGoodsList")
    public List<GoodsInfo> getList(Integer pageNumber, String query) {
        if (pageNumber == null) {
            pageNumber = 0;
        }
        //es搜索默认第一页页码是0
        SearchQuery searchQuery = getEntitySearchQuery(pageNumber, PAGESIZE, query);
        Page<GoodsInfo> goodsPage = goodsRepository.search(searchQuery);
        return goodsPage.getContent();
    }

    private SearchQuery getEntitySearchQuery(int pageNumber, int pageSize, String searchContent) {
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(QueryBuilders.matchPhraseQuery("name", searchContent));
        // 设置分页
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        return new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
    }

}