

package io.github.benas.todolist.web.servlet.todo;

import io.github.todolist.core.domain.Priority;
import io.github.todolist.core.domain.Todo;
import io.github.todolist.core.service.api.TodoService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.ResourceBundle;

import static io.github.benas.todolist.web.util.Views.ERROR_PAGE;
import static io.github.benas.todolist.web.util.Views.UPDATE_TODO_PAGE;

/**
 * Servlet that controls todo update.
 *
 * 
 */

@WebServlet(name = "UpdateTodoServlet", urlPatterns = {"/todos/update", "/todos/update.do"})
public class UpdateTodoServlet extends HttpServlet {

    private TodoService todoService;

    private ResourceBundle resourceBundle;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletConfig.getServletContext());
        todoService = applicationContext.getBean(TodoService.class);
        resourceBundle = ResourceBundle.getBundle("todolist");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String id = request.getParameter("todoId");
        try {
            long todoId = Long.parseLong(id);
            Todo todo = todoService.getTodoById(todoId); // FIXME security : may provide an id for a todo of another user!
            request.setAttribute("todo", todo);
            request.getRequestDispatcher(UPDATE_TODO_PAGE).forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", MessageFormat.format(resourceBundle.getString("no.such.todo"), id));
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long todoId = Long.parseLong(request.getParameter("todoId"));
        String title = request.getParameter("title");
        String dueDate = request.getParameter("dueDate");
        String priority = request.getParameter("priority");
        String status = request.getParameter("status");

        Todo todo = todoService.getTodoById(todoId);
        todo.setTitle(title);
        todo.setDueDate(new Date(dueDate));
        todo.setDone(Boolean.valueOf(status));
        todo.setPriority(Priority.valueOf(priority));
        todoService.update(todo);
        request.getRequestDispatcher("/todos").forward(request, response);

    }

}
