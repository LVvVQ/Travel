package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import java.util.List;

/**
 * @author #L
 * @date 2021/03/21
 */
public class RouteServiceImpl implements RouteService {
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize) {
        RouteDao routeDao = new RouteDaoImpl();
        PageBean<Route> pageBean = new PageBean<Route>();

        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);

        int totalCount = routeDao.getTotalCount(cid);
        pageBean.setTotalCount(totalCount);

        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pageBean.setTotalPage(totalPage);

        int start = (currentPage - 1) * pageSize;
        List<Route> routeList = routeDao.findByPage(cid,start,pageSize);
        pageBean.setList(routeList);

        return pageBean;
    }
}
