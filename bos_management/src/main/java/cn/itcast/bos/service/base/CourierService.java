package cn.itcast.bos.service.base;

import cn.itcast.bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-19 10:15
 **/
public interface CourierService {

    void save(Courier courier);

    Page<Courier> findPage(Specification<Courier>specification,Pageable pageable);

    void del(String[] arr);

    List<Courier> findNoAssociation();
}
