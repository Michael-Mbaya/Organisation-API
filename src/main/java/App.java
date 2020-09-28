import static spark.Spark.*;
import com.google.gson.Gson;
import exceptions.ApiException;
import models.*;
import dao.*;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class App {
    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {

        port(getHerokuAssignedPort());
        staticFileLocation("/public");

        Sql2oDepartmentDao departmentsDao;
        Sql2oNewsDao newsDao;
        Sql2oUserDao usersDao;

        Connection conn;
        Gson gson = new Gson();


        //For h2
//        String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
//        Sql2o sql2o = new Sql2o(connectionString, "", "");
        //for postgresql
//        String connectionString = "jdbc:postgresql://localhost:5432/myorg";
//        Sql2o sql2o = new Sql2o(connectionString, "moringa", "moringa");
        //for herokupostgresql
        String connectionString = "jdbc:postgresql://ec2-3-218-112-22.compute-1.amazonaws.com:5432/d3lp89ash90jh";
        Sql2o sql2o = new Sql2o(connectionString, "ivmpkcooiunyxg", "bcc572d51ad47ac0fcefe5e5d9623027bb62b0f46d209d36271754a528bfb571");

        departmentsDao = new Sql2oDepartmentDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        usersDao = new Sql2oUserDao(sql2o);

        conn = sql2o.open();

        get("/", "application/json", (req, res) ->
                "{\"message\":\"Welcome to the main page of ORGANISATIONAL API.\"}");

        //news
        //postman posts new News object (Json Format)
        post("/news/new", "application/json", (req, res)->{
            News news = gson.fromJson(req.body(), News.class);
            newsDao.add(news);
            res.status(201);
            return gson.toJson(news);
        });

        //postman gets List of News objects
        get("/news", "application/json", (req, res) -> {
            System.out.println(newsDao.getAll());

            if(newsDao.getAll().size() > 0) {
                return gson.toJson(newsDao.getAll());
            }
            else{
                return "{\"message\":\"I'm sorry, but no news items are currently listed in the database.\"}";
            }
        });

        //postman gets News objects by their id (Json format)
        get("/news/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int newsId = Integer.parseInt(req.params("id"));
            News newsToFind = newsDao.findById(newsId);
            if (newsToFind == null){
                throw new ApiException(404, String.format("No news item with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(newsToFind);
        });

        //departments
        //postman posts new Department objects (Json format)
        post("/department/new", "application/json", (req, res)->{
            Department department = gson.fromJson(req.body(), Department.class);
            departmentsDao.add(department);
            res.status(201);
            return gson.toJson(department);
        });

        //postman gets List of Department Objects
        get("/departments", "application/json", (req, res) -> {
            System.out.println(departmentsDao.getAll());

            if(departmentsDao.getAll().size() > 0){
                return gson.toJson(departmentsDao.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no departments are currently listed in the database.\"}";
            }

        });

        //postman gets Department Objects by their id (Json Format)
        get("/department/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentsDao.findById(departmentId);
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(departmentToFind);
        });

        //get filtered news (in an array of objects) belonging to a department by the department Id
        get("/department/:id/news", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));

            Department departmentToFind = departmentsDao.findById(departmentId);
            List<News> departmentNews;

               if (departmentToFind == null) {
                    throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
                }

            departmentNews = newsDao.getAllNewsByDepartment(departmentId);
            res.type("application/json");
            return gson.toJson(departmentNews);
        });

        //get filtered Users (in an array of objects) belonging to a department by the department Id
        get("/department/:id/users", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));

            Department departmentToFind = departmentsDao.findById(departmentId);
            List<User> departmentUsers;

            if (departmentToFind == null) {
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }

            departmentUsers = usersDao.getAllUsersByDepartment(departmentId);
            res.type("application/json");
            return gson.toJson(departmentUsers);
        });

        //users
        //postman posts new User Object (Json Format)
        post("/user/new", "application/json", (req, res)->{
            User user = gson.fromJson(req.body(), User.class);
            usersDao.add(user);
            res.status(201);
            return gson.toJson(user);
        });

        //postman gets List of User Objects
        get("/users", "application/json", (req, res) -> {
            System.out.println(usersDao.getAllUsers());

            if(usersDao.getAllUsers().size() > 0){
                return gson.toJson(usersDao.getAllUsers());
            }

            else{
                return "{\"message\":\"I'm sorry, but no users are currently listed in the database.\"}";
            }
        });

        //postman gets User Objects by their id (Json Format)
        get("/user/:id", "application/json", (req, res) -> {
            int userId = Integer.parseInt(req.params("id"));
            User userToFind = usersDao.findUserById(userId);
            if (userToFind == null){
                throw new ApiException(404, String.format("No user with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(userToFind);
        });

        //FILTERS
        exception(ApiException.class, (exception, req, res) -> {
            ApiException err = exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });


        after((req, res) ->{
            res.type("application/json");
        });

    }
}