package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * @author #L
 * @date 2021/03/21
 */
public interface RouteDao {
    /**
     * 根据类别id查询总记录数
     * @param cid   类别id
     * @return      总记录数
     */
    public int getTotalCount(int cid);

    /**
     * 根据cid,currentPage,pageSize查询当前页的数据集合
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    public List<Route> findByPage(int cid, int start, int pageSize);
}
