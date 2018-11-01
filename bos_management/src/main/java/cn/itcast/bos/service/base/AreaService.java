package cn.itcast.bos.service.base;

import cn.itcast.bos.domain.base.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-22 18:35
 **/
public interface AreaService {

    void areaImport(List<Area> areas);

    Page<Area> findPageQuery(Specification<Area> specification, Pageable pageable);
}
