package cn.itcast.bos.service.base;

import cn.itcast.bos.domain.base.TakeTime;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-25 21:14
 **/
public interface TakeTimeService {

    List<TakeTime> findAll();
}
