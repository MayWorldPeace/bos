package cn.itcast.bos.service.base.impl;

import cn.itcast.bos.dao.base.StandardRepository;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-17 18:07
 **/
@Service("standardService")
@Transactional
public class StandardServiceImpl implements StandardService {
    //注入Dao
    @Autowired
    private StandardRepository standardRepository;

    @Override
    @CacheEvict(value = "standard",allEntries = true)
    public void save(Standard standard) {
        standardRepository.save(standard);
    }

    @Override
    public void delete(int id) {
        standardRepository.delete(id);
    }

    @Override
    @Cacheable(value = "standard",key = "#pageable.pageNumber +'_'+ #pageable.pageSize")
    public Page<Standard> findPage(Pageable pageable) {
        Page<Standard> pageDate = standardRepository.findAll(pageable);
        return pageDate;
    }

    @Override
    @Cacheable("standard")
    public List<Standard> findAll() {
        return standardRepository.findAll();
    }
}
