package backend.controller;

import backend.model.DBManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DocumentController extends HttpServlet {
    public DocumentController()
    {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String documentId = request.getParameter("documentId");
        String documentName = request.getParameter("documentName");
        String documentContents = request.getParameter("documentContents");

        System.out.println(documentId);
        System.out.println(documentName);
        System.out.println(documentContents);

        DBManager dbManager = new DBManager();
        dbManager.addDocument(documentId, documentName, documentContents);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/success.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
