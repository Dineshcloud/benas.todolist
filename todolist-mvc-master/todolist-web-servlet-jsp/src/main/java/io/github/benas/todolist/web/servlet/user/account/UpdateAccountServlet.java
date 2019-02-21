

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
import java.text.MessageFormat;
import java.util.ResourceBundle;

import static io.github.benas.todolist.web.util.Views.ACCOUNT_PAGE;

/**
 * 
 */

@WebServlet(name = "UpdateAccountServlet", urlPatterns = "/user/account/update.do")
public class UpdateAccountServlet extends HttpServlet {

    private UserService userService;

    private ResourceBundle resourceBundle;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletConfig.getServletContext());
        userService = applicationContext.getBean(UserService.class);
        resourceBundle = ResourceBundle.getBundle("todolist");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(TodoListUtils.SESSION_USER);

        if (isAlreadyUsed(email) && isDifferent(email, user.getEmail())) {
            request.setAttribute("error", MessageFormat.format(resourceBundle.getString("account.email.alreadyUsed"), email));
            request.setAttribute("user", user);
            request.getRequestDispatcher(ACCOUNT_PAGE).forward(request, response);
            return;
        }

        user.setName(name);
        user.setEmail(email);
        userService.update(user);
        request.setAttribute("updateProfileSuccessMessage", resourceBundle.getString("account.profile.update.success"));
        request.getRequestDispatcher("/user/account").forward(request, response);

    }

    private boolean isDifferent(String newEmail, String currentEmail) {
        return !newEmail.equals(currentEmail);
    }

    private boolean isAlreadyUsed(String email) {
        return userService.getUserByEmail(email) != null;
    }

}
