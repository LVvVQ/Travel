package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String checkCode = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(checkCode)){
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
            ObjectMapper mapper = new ObjectMapper();
            String result_json = mapper.writeValueAsString(resultInfo);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(result_json);
            return;
        }


        User user = new User();
        Map<String, String[]> loginFormMap = request.getParameterMap();
        try {
            BeanUtils.populate(user,loginFormMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        UserService userService = new UserServiceImpl();
        User u = userService.login(user);
        ResultInfo resultInfo = new ResultInfo();
        if (u != null){
            //用户存在
            if ("Y".equals(u.getStatus())){
                //已激活,登录成功
                resultInfo.setFlag(true);
                session.setAttribute("user",u);
            }else {
                //未激活
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("账号未激活,登录失败!");
            }
        }else {
            //用户不存在
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("登录失败,账号或密码错误!");
        }

        ObjectMapper mapper = new ObjectMapper();
        String result_json = mapper.writeValueAsString(resultInfo);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(result_json);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
