package backend.controller;

import backend.domain.Document;
import backend.domain.Movie;
import backend.domain.User;
import backend.model.DBManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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

        DBManager dbManager = new DBManager();
        dbManager.addDocument(documentId, documentName, documentContents);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/success.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null && action.equals("getDocuments")) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            response.setContentType("application/json");

            DBManager dbManager = new DBManager();
            ArrayList<Document> documentList = dbManager.getDocumentsByUser(userId);
            ArrayList<Movie> movieList = dbManager.getMoviesByUser(userId);

//        JSONArray jsonDocuments = new JSONArray();
//
//        documentList.forEach(document -> {
//            JSONObject obj = new JSONObject();
//            obj.put("id", document.getId());
//            obj.put("name", document.getName());
//            obj.put("contents", document.getContents());
//            jsonDocuments.add(obj);
//        });

            JSONArray jsonObjects = new JSONArray();

            int minNumber = Math.min(documentList.size(), movieList.size());

            for (int i = 0; i < minNumber; ++i) {
                JSONObject objDocument = new JSONObject();
                JSONObject objMovie = new JSONObject();

                objDocument.put("id", documentList.get(i).getId());
                objDocument.put("name", documentList.get(i).getName());
                objDocument.put("contents", documentList.get(i).getContents());

                objMovie.put("id", movieList.get(i).getId());
                objMovie.put("title", movieList.get(i).getTitle());
                objMovie.put("duration", movieList.get(i).getDuration());

                jsonObjects.add(objDocument);
                jsonObjects.add(objMovie);
            }

            for (int i = minNumber; i < documentList.size(); ++i) {

                JSONObject objDocument = new JSONObject();
                objDocument.put("id", documentList.get(i).getId());
                objDocument.put("name", documentList.get(i).getName());
                objDocument.put("contents", documentList.get(i).getContents());
                jsonObjects.add(objDocument);
            }

            for (int i = minNumber; i < movieList.size(); ++i) {
                JSONObject objMovie = new JSONObject();

                objMovie.put("id", movieList.get(i).getId());
                objMovie.put("title", movieList.get(i).getTitle());
                objMovie.put("duration", movieList.get(i).getDuration());

                jsonObjects.add(objMovie);
            }

            PrintWriter out = new PrintWriter(response.getOutputStream());
            out.println(jsonObjects.toJSONString());
            out.flush();
        }
        else if(action != null && action.equals("getMostPopularDocument"))
        {
            DBManager dbManager = new DBManager();
            ArrayList<Document> documentList = dbManager.getAllDocuments();
            ArrayList<User> userList = dbManager.getAllUsers();

            AtomicReference<Document> mostPopularDocument = new AtomicReference<>();
            AtomicLong maxAppearances = new AtomicLong(-1);

            documentList.forEach(document -> {
//                long nrAppearances = userList
//                        .stream()
//                        .map(user -> Arrays.stream(user.getDocumentList().split(" "))
//                        .toList()
//                        .contains(String.valueOf(document.getId())))
//                        .count();
                long nrAppearances = userList
                        .stream()
                            .map(User::getDocumentList)
                                    .filter(list -> list.contains(String.valueOf(document.getId())))
                                            .count();

                System.out.println("Nr appearances: " + nrAppearances);
                System.out.println("Current Document: " + document.getName());

                if(nrAppearances > maxAppearances.get()) {
                    maxAppearances.set(nrAppearances);
                    mostPopularDocument.set(document);

                    System.out.println("Max appearances: " + maxAppearances.get());
                    System.out.println("Most popular document: " + mostPopularDocument.get().getName());
                }
            });

            JSONObject jsonDocument = new JSONObject();
            jsonDocument.put("id", mostPopularDocument.get().getId());
            jsonDocument.put("name", mostPopularDocument.get().getName());
            jsonDocument.put("contents", mostPopularDocument.get().getContents());

            PrintWriter out = new PrintWriter(response.getOutputStream());
            out.println(jsonDocument.toJSONString());
            out.flush();
        }
    }
}
