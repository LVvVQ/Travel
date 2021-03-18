package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author #L
 * @date 2021/03/18
 */
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet{

    private CategoryService categoryService = new CategoryServiceImpl();

    public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Category> categoryList = categoryService.findAllCategory();
        writeValue(categoryList,response);

    }
}
