

package io.github.benas.todolist.web.servlet.user.account;

import io.github.benas.todolist.web.common.util.TodoListUtils;
import io.github.todolist.core.domain.User;
import io.github.todolist.core.service.api.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 
 */

@WebServlet(name = "DeleteAccountServlet", urlPatterns = "/user/account/delete.do")
public class DeleteAccountServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletConfig.getServletContext());
        userService = applicationContext.getBean(UserService.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(TodoListUtils.SESSION_USER);
        userService.remove(user);
        session.invalidate();
        request.getRequestDispatcher("/index").forward(request, response);
    }

}
