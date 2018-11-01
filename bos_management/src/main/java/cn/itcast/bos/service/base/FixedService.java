package cn.itcast.bos.service.base;

import cn.itcast.bos.domain.base.FixedArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-22 20:56
 **/
public interface FixedService {

    void fixedAdd(FixedArea fixedArea);

    Page<FixedArea> pageQuery(Specification<FixedArea> specification, Pageable pageable);

    void fixedArea_associationCourierToFixedArea(Integer takeTimeId, Integer courierId, FixedArea fixedArea);
}
