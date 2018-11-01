package cn.itcast.bos.dao.base;

import cn.itcast.bos.domain.base.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-22 18:36
 **/
public interface AreaRepository extends JpaRepository<Area, String>, JpaSpecificationExecutor {

    Area findByProvinceAndCityAndDistrict(String province, String city, String district);
}
