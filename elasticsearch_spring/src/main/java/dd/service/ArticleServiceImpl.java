package dd.service;

import dd.dao.ArticleRepository;
import dd.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author ZhongChaoYuan
 * @create 2018-09-06 19:48
 **/
@Service
public class ArticleServiceImpl implements ArticleService{

   @Autowired
   private ArticleRepository articleRepository;

    public void save(Article article) {
        articleRepository.save(article);
    }

    public void delete(Article article) {
        articleRepository.delete(article);
    }

    public Iterable<Article> findAll() {
        return articleRepository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC,"id")));
    }

    public Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }
}
