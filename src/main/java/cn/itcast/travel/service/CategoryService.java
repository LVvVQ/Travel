package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @author #L
 * @date 2021/03/18
 */
public interface CategoryService {
    public List<Category> findAllCategory();
}
