package cn.itcast.bos.service.base.impl;

import cn.itcast.bos.dao.base.CourierRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-19 10:15
 **/
@Service("courierService")
@Transactional
public class CourierServiceImpl implements CourierService {

    @Autowired
    private CourierRepository courierRepository;

    @Override
    @RequiresPermissions("courier:add")
    public void save(Courier courier) {
        courierRepository.save(courier);
    }

    @Override
    public Page<Courier> findPage(Specification<Courier> specification, Pageable pageable) {
        return courierRepository.findAll(specification,pageable);
    }

    @Override
    public void del(String[] arr) {
        for (int i = 0;i<arr.length;i++){
            Integer id = Integer.parseInt(arr[i]);
            courierRepository.del(id);
        }
    }

    @Override
    public List<Courier> findNoAssociation() {
        Specification<Courier> specification = new Specification<Courier>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                Predicate fixedAreas = cb.isEmpty(root.get("fixedAreas").as(Set.class));
                return fixedAreas;
            }
        };
        return courierRepository.findAll(specification);
    }
}
