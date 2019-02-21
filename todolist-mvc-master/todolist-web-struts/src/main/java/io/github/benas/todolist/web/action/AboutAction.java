

package io.github.benas.todolist.web.action;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Action class to redirect to about page.
 *
 * 
 */
public class AboutAction extends ActionSupport {
    public static final String ACTIVE = "active";

    public String getAboutTabStyle() {
        return ACTIVE;
    }

}
