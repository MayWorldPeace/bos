package cn.itcast.bos.dao.base;

import cn.itcast.bos.domain.base.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**\
 * @author ZhongChaoYuan
 * @create 2018-08-17 18:39
 **/
public interface StandardRepository extends JpaRepository<Standard,Integer> {
    public List<Standard> findByName(String name);

    @Query(value = "from Standard where name=?",nativeQuery = false)
    public List<Standard> queryName(String name);
}
