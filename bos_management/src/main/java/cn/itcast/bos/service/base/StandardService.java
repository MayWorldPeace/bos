package cn.itcast.bos.service.base;

import cn.itcast.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-17 18:06
 **/
public interface StandardService {
    void save(Standard standard);

    Page<Standard> findPage(Pageable pageable);

    void delete(int id);

    List<Standard> findAll();
}

