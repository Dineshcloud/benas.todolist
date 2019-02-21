

package io.github.benas.todolist.web.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import io.github.benas.todolist.web.common.util.TodoListUtils;
import io.github.todolist.core.domain.User;

import java.util.Map;

/**
 * Custom interceptor to ensure that access to protected resources is allowed to only logged users.
 *
 * 
 */
public class LoginInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {

        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get(TodoListUtils.SESSION_USER);

        if (user != null) {
            return invocation.invoke();
        } else {
            return Action.LOGIN;
        }

    }

}
