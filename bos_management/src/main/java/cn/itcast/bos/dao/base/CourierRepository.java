package cn.itcast.bos.dao.base;

import cn.itcast.bos.domain.base.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-19 10:16
 **/
public interface CourierRepository extends JpaRepository<Courier,Integer> ,
        JpaSpecificationExecutor<Courier> {

    @Query("update Courier set deltag = '1' where id = ?")
    @Modifying
    public void del(Integer id);
}
