package cn.itcast.bos.service.base.impl;

import cn.itcast.bos.dao.base.AreaRepository;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-22 18:35
 **/
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;
    @Override
    public void areaImport(List<Area> areas) {
        areaRepository.save(areas);
    }

    @Override
    public Page<Area> findPageQuery(Specification<Area> specification, Pageable pageable) {
        return areaRepository.findAll(specification,pageable);
    }
}
