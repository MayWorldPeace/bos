package cn.itcast.bos.dao.base;

import cn.itcast.bos.domain.base.FixedArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-22 20:57
 **/
public interface FixedRepository extends JpaRepository<FixedArea,String>,JpaSpecificationExecutor<FixedArea> {

}
