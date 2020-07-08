package demo.lwq.es;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodsController {

    //每页数量
    private Integer PAGESIZE = 10;

    @Autowired
    private RestClient restClient;
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    // @Autowired
    // private TransportClient transportClient;

    @Autowired
    private GoodsRepository goodsRepository;

    /* The ElasticsearchRestTemplate is an implementation of the ElasticsearchOperations interface
     * using the High Level REST Client.
     */
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /* The ElasticsearchTemplate is an implementation of the ElasticsearchOperations interface
     * using the Transport Client.
     */
    //@Autowired
    // private ElasticsearchTemplate elasticsearchTemplate;  // 需要transport client

    // @GetMapping("transport/{index}")
    // public String transport(@PathVariable String index) throws IOException {
    //     IndicesExistsResponse inExistsResponse = transportClient.admin().indices()
    //             .exists(new IndicesExistsRequest(index)).actionGet();
    //     if (inExistsResponse.isExists()) {
    //         System.out.println("Index [" + index + "] is exist!");
    //     } else {
    //         System.out.println("Index [" + index + "] is not exist!");
    //     }
    //     return "index: " + index + " ; 存在?" + inExistsResponse.isExists();
    // }

    @GetMapping("ping")
    public String ping() throws IOException {
        return "ping: " + restHighLevelClient.ping(RequestOptions.DEFAULT);
    }

    @GetMapping("nodes")
    public String nodes() throws IOException {
        return "nodes: " + restClient.getNodes().stream().map(node -> node.toString()).collect(Collectors.joining(","));
    }

    @GetMapping("index")
    public Boolean createIndex(){
        return elasticsearchRestTemplate.indexOps(GoodsInfo.class).create();
        // return elasticsearchTemplate.createIndex(GoodsInfo.class);

    }

    @GetMapping("saveAndFind")
    public GoodsInfo saveAndFind() {
        GoodsInfo goodsInfo = new GoodsInfo(2L, "macbook", "mac笔记本");
        elasticsearchRestTemplate.save(goodsInfo);
        return elasticsearchRestTemplate.get("2", GoodsInfo.class);
    }

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

    //根据name搜索
    @GetMapping("search/{name}")
    public SearchHits<GoodsInfo> getList(String name) {
        //es搜索默认第一页页码是0
        // QueryBuilder queryBuilder = getEntitySearchQuery(pageNumber, PAGESIZE, query);
        CriteriaQuery query = new CriteriaQuery(Criteria.where("name").contains(name));
        SearchHits<GoodsInfo> goodsInfoSearchHits = elasticsearchRestTemplate.search(query, GoodsInfo.class);

        return goodsInfoSearchHits;
    }

    private QueryBuilder getEntitySearchQuery(int pageNumber, int pageSize, String searchContent) {
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(QueryBuilders.matchPhraseQuery("name", searchContent));
        // 设置分页
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        return new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build().getQuery();
    }

}