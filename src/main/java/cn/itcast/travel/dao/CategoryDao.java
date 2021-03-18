package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @author #L
 * @date 2021/03/18
 */
public interface CategoryDao {
    /**
     * 查询所有的分类
     * @return
     */
    public List<Category> findAllCategory();
}
