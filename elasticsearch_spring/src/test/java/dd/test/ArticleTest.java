package dd.test;

import dd.domain.Article;
import dd.service.ArticleService;
import org.elasticsearch.client.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ZhongChaoYuan
 * @create 2018-09-06 20:17
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@SuppressWarnings("all")
public class ArticleTest {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private Client client;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Test
    public void createIndex(){
        //创建索引
        elasticsearchTemplate.createIndex(Article.class);
        //添加映射
        elasticsearchTemplate.putMapping(Article.class);
    }

    //测试保存操作
    @Test
    public void save(){
        Article article = new Article();
        article.setId(001);
        article.setTitle("窑子是瓜皮吗?????");
        article.setContent("你这不是废话吗?");

        articleService.save(article);
    }

    //测试删除操作
    @Test
    public void delete(){
        Article article = new Article();
        article.setId(001);

        articleService.delete(article);
    }

    //插入数据
    @Test
    public void saveMany(){
        for (int i = 0; i < 101; i++) {
            Article article = new Article();
            article.setId(i);
            article.setTitle(i+"窑子是瓜皮吗?????");
            article.setContent(i+"你这不是废话吗?");

            articleService.save(article);
        }
    }

    //排序
    @Test
    public void findAll(){
        Iterable<Article> articles =  articleService.findAll();
        for (Article article : articles) {
            System.out.println(article);
        }
    }

    //分页
    @Test
    public void pageQuety(){
        Pageable pageable = new PageRequest(0,10);
        Page<Article> articles =  articleService.findAll(pageable);
        for (Article article : articles) {
            System.out.println(article);
        }
    }
}
