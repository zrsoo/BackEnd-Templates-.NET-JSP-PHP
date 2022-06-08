package backend.controller;

import backend.domain.User;
import backend.model.DBManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginController extends HttpServlet{
    public LoginController()
    {
        super();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        DBManager dbManager = new DBManager();
        User user = dbManager.authenticate(username, password);

        RequestDispatcher requestDispatcher = null;

        if(user.getUsername() != null)
        {
            // TODO success.jsp and error.jsp
            requestDispatcher = request.getRequestDispatcher("/success.jsp");

            // Set session
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("user", user);

            // TODO check in all servlets whether Attribute "user" is set. -> else, return
        }
    }
}
