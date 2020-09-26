import static spark.Spark.*;
import com.google.gson.Gson;
import exceptions.ApiException;
import models.*;
import dao.*;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import java.util.List;


public class App {
    public static void main(String[] args) {

        Sql2oDepartmentDao departmentsDao;
        Sql2oNewsDao newsDao;
        Sql2oUserDao usersDao;

        Connection conn;
        Gson gson = new Gson();

        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");

        departmentsDao = new Sql2oDepartmentDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        usersDao = new Sql2oUserDao(sql2o);

        conn = sql2o.open();

        get("/", "application/json", (req, res) ->
                "{\"message\":\"Welcome to the main page of ORGANISATIONAL API.\"}");

        //postman posts news object
        post("/news/new", "application/json", (req, res)->{
            News news = gson.fromJson(req.body(), News.class);
            newsDao.add(news);
            res.status(201);
            return gson.toJson(news);
        });

        //postman gets news array of objects
        get("/news", "application/json", (req, res) -> {
            System.out.println(newsDao.getAll());

            if(newsDao.getAll().size() > 0) {
                return gson.toJson(newsDao.getAll());
            }
            else{
                return "{\"message\":\"I'm sorry, but no news items are currently listed in the database.\"}";
            }
        });

        //postman gets news objects by their id
        get("/news/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int newsId = Integer.parseInt(req.params("id"));
            News newsToFind = newsDao.findById(newsId);
            if (newsToFind == null){
                throw new ApiException(404, String.format("No news item with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(newsToFind);
        });


    }
}