package cn.itcast.bos.service.base.impl;

import cn.itcast.bos.dao.base.CourierRepository;
import cn.itcast.bos.dao.base.FixedRepository;
import cn.itcast.bos.dao.base.TakeTimeRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.FixedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-22 20:57
 **/
@Service
@Transactional
public class FixedServiceImpl implements FixedService {

    @Autowired
    FixedRepository fixedRepository;
    @Override
    public void fixedAdd(FixedArea fixedArea) {
        fixedRepository.save(fixedArea);
    }

    @Override
    public Page<FixedArea> pageQuery(Specification<FixedArea> specification, Pageable pageable) {
        return fixedRepository.findAll(specification,pageable);
    }

    @Autowired
    CourierRepository courierRepository;
    @Autowired
    TakeTimeRepository takeTimeRepository;
    @Override
    public void fixedArea_associationCourierToFixedArea(Integer takeTimeId, Integer courierId, FixedArea fixedArea) {
        FixedArea fixedArea1 = fixedRepository.findOne(fixedArea.getId());
        Courier courier = courierRepository.findOne(courierId);
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);

        fixedArea1.getCouriers().add(courier);

        courier.setTakeTime(takeTime);

    }
}
