package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * @author #L
 * @date 2021/03/21
 */
public interface RouteService {
    /**
     * 根据类别进行分页查询
     * @param cid           类别id
     * @param currentPage   当前页数
     * @param pageSize      每页展示条目数
     * @return              线路PageBean
     */
    public PageBean<Route> pageQuery(int cid,int currentPage,int pageSize);

}
