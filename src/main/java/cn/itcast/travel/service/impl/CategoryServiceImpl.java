package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;

import java.util.List;

/**
 * @author #L
 * @date 2021/03/18
 */
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryImpl();

    @Override
    public List<Category> findAllCategory() {
        return categoryDao.findAllCategory();
    }
}
