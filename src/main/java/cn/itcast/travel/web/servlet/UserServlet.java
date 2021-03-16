package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author  #L
 * @date    2021/03/16
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private ResultInfo resultInfo = new ResultInfo();
    private UserService userService = new UserServiceImpl();

    /**
     * 用户注册
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (checkcodeIsNullOrFalse(request)){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
            sendResultJsonToResponse(resultInfo_to_json(resultInfo),response);
            return;
        }

        //将前端表单中的数据封装为user对象
        Map<String, String[]> formMap = request.getParameterMap();

        //校验用户&&将返回结果封装到resultInfo对象中
        boolean flag =  userService.register(formMap_to_User(formMap));
        if (flag){
            resultInfo.setFlag(true);
        }else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败!");
        }

        //将resultInfo对象转为json数据传回前端
        sendResultJsonToResponse(resultInfo_to_json(resultInfo),response);
    }

    /**
     * 激活码校验
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String code = request.getParameter("code");
        if (code != null){
            boolean flag = userService.active(code);
            String msg = null;
            if (flag){
                msg = "激活成功,请<a href = 'http://localhost:8080/travel/login.html'>登录</a>";
            }else {
                msg = "激活失败,请联系管理员";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }

    }

    /**
     * 用户登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //验证码错误响应错误信息
        if (checkcodeIsNullOrFalse(request)){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
            sendResultJsonToResponse(resultInfo_to_json(resultInfo),response);
            return;
        }

        //将表单数据封装为User对象
        Map<String, String[]> loginFormMap = request.getParameterMap();
        User u = userService.login(formMap_to_User(loginFormMap));

        //检查用户状态并响应消息
        if (u != null){
            //用户存在
            if ("Y".equals(u.getStatus())){
                //已激活,登录成功
                resultInfo.setFlag(true);
                request.getSession().setAttribute("user",u);
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
        sendResultJsonToResponse(resultInfo_to_json(resultInfo),response);
    }

    /**
     * 查找已登录用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void searchUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //响应回json格式的已登录用户信息
        Object user = request.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json; charset=UTF-8");
        mapper.writeValue(response.getOutputStream(),user);
    }

    /**
     * 退出账号
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("user");
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /**
     * 校验验证码是否为空或错误
     * @param request
     * @return 验证码为空或错误返回false,验证码正确返回true
     * @throws ServletException
     */
    public boolean checkcodeIsNullOrFalse(HttpServletRequest request) {
        //核对用户输入的验证码和生成的验证是否一致,并删去这次的验证码
        String checkcode = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        return checkcode_server == null || !checkcode_server.equalsIgnoreCase(checkcode);
    }

    /**
     * 将ResultInfo对象以json格式Response到前端
     * @param json
     * @param response
     * @throws IOException
     */
    public void sendResultJsonToResponse(String json,HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 将ResultInfo对象转为String
     * @param resultInfo
     * @return String类型的ResultInfo对象
     * @throws IOException
     */
    public String  resultInfo_to_json(ResultInfo resultInfo) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(resultInfo);
    }

    /**
     * 将表单中提交的参数转为User对象
     * @param formMap 一个包含表单提交参数的Map集合
     * @return Map转化后的User对象
     */
    public User formMap_to_User(Map<String, String[]> formMap){
        User user = new User();
        try {
            BeanUtils.populate(user,formMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return user;
    }
}