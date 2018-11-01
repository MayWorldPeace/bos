package cn.itcast.bos.web.action.base;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.Servlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhongChaoYuan
 * @create 2018-08-22 18:34
 **/
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AreaAction extends ActionSupport implements ModelDriven<Area> {

    @Autowired
    private AreaService areaService;
    private Area area = new Area();

    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    public Area getModel() {
        return area;
    }

    private File file;

    public void setFile(File file) {
        this.file = file;
    }

    //导入xls  xlsx
    @Action(value = "area_import")
    public void areaImport() throws Exception {
        List<Area> areas = new ArrayList<Area>();
        Workbook workbook = WorkbookFactory.create(new FileInputStream(file));
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            Area area = new Area();
            area.setId(row.getCell(0).getStringCellValue());
            area.setProvince(row.getCell(1).getStringCellValue());
            area.setCity(row.getCell(2).getStringCellValue());
            area.setDistrict(row.getCell(3).getStringCellValue());
            area.setPostcode(row.getCell(4).getStringCellValue());
            areas.add(area);
        }
        areaService.areaImport(areas);
        System.out.println("导入成功!!");
    }

    //条件查询
    private Integer page;
    private Integer rows;

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    @Action(value = "area_pageQuery")
    public void pageQuery() throws IOException {
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        Pageable pageable = new PageRequest(page-1,rows);
        Specification<Area> specification = new Specification<Area>() {
            @Override
            public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList();
                if (StringUtils.isNotBlank(area.getProvince())){
                    Predicate province = cb.like(root.get("province").as(String.class), "%"+area.getProvince()+"%");
                    list.add(province);
                }
                if (StringUtils.isNotBlank(area.getCity())){
                    Predicate city = cb.like(root.get("city").as(String.class), "%"+area.getCity()+"%");
                    list.add(city);
                }
                if (StringUtils.isNotBlank(area.getDistrict())){
                    Predicate district = cb.like(root.get("district").as(String.class), "%" + area.getDistrict() + "%");
                    list.add(district);
                }
                return cb.and(list.toArray(new Predicate[0]));
            }
        };
        Page<Area>page = areaService.findPageQuery(specification,pageable);
        Map<String,Object> map = new HashMap();
        map.put("total",page.getTotalElements());
        map.put("rows",page.getContent());

        String jsonString = JSON.toJSONString(map);

        ServletActionContext.getResponse().getWriter().print(jsonString);
        System.out.println(jsonString);
    }

}
