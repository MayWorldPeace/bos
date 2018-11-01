package dd.dao;

import dd.domain.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author ZhongChaoYuan
 * @create 2018-09-06 19:44
 **/
public interface ArticleRepository extends ElasticsearchRepository<Article,Integer> {

}
