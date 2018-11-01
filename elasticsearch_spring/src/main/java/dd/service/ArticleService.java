package dd.service;

import dd.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author ZhongChaoYuan
 * @create 2018-09-06 19:46
 **/
public interface ArticleService {
    public void save(Article article);

    void delete(Article article);

    Iterable<Article> findAll();

    Page<Article> findAll(Pageable pageable);
}
