package cn.itcast.bos.dao.base;

import cn.itcast.bos.domain.base.TakeTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-25 21:15
 **/
public interface TakeTimeRepository extends JpaRepository<TakeTime,Integer>,JpaSpecificationExecutor<TakeTime> {

}
