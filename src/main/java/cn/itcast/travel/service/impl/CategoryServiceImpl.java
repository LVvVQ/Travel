package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author #L
 * @date 2021/03/18
 */
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryImpl();

    @Override
    public List<Category> findAllCategory() {
        //获取jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        //使用sortedset排序查询redis中category中的数据
        Set<String> categorySet = jedis.zrange("category", 0, -1);
        List<Category> categoryList = null;

        if (categorySet==null || categorySet.size() == 0){
            //如果为空,则需要从数据库中查询数据
            categoryList = categoryDao.findAllCategory();
            //将查询到的数据存储到Redis中category的key
            for (Category category : categoryList) {
                jedis.zadd("category",category.getCid(),category.getCname());
            }
        } else {
            //如果不为空,将set中的数据存储到List中
            categoryList = new ArrayList<Category>();
            for (String name : categorySet) {
                Category category = new Category();
                category.setCname(name);
                categoryList.add(category);
            }
        }

        return categoryList;
    }
}
