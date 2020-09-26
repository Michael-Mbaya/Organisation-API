package dao;
import models.User;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.sql2o.Sql2o;
import org.sql2o.Connection;

import static org.junit.Assert.*;

public class Sql2oUserDaoTest {

    private static Connection conn;
    private static Sql2oDepartmentDao departmentDao;
    private static Sql2oNewsDao newsDao;
    private static Sql2oUserDao userDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        departmentDao = new Sql2oDepartmentDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
//        departmentDao.clearAll();
//        newsDao.clearAll();
        userDao.clearAll();
        System.out.println("clearing database");
        conn.close();
    }



    @Test
    public void instantiatesCorrectly(){
        User testUser = setUpUser();
        assertEquals(true, testUser instanceof User);
    }

    @Test
    public void returnsTheRightId(){
        User testUser = setUpUser();
        assertEquals(1, testUser.getId());
    }

    @Test
    public void returnsCorrectSizeAfterInstantiation(){
        User testUser = setUpUser();
        assertEquals(1, userDao.getAllUsers().size());
    }

    @Test
    public void returnNothingWhenThereIsNoInstance(){
        assertEquals(0, userDao.getAllUsers().size());
    }


    @Test
    public void findByIdReturnsCorrectInstanceOfClass(){
        User testUsers = setUpUser();
        User testAltUser = setUpAltUser();
        assertEquals(testAltUser, userDao.findUserById(testAltUser.getId()));
    }

    @Test
    public void updatesCorrectlyUpdatesAllFieldsCorrectly(){
        User testUser = setUpUser();
        userDao.update(testUser.getId(), "Hampton", "Banker", "Teller", 20);
        User findUser = userDao.findUserById(testUser.getId());
        assertEquals("Hampton", findUser.getUserName());
        assertEquals("Banker", findUser.getUserCompanyPosition());
        assertEquals("Teller", findUser.getUserCompanyRole());
        assertEquals(20, findUser.getDepartmentId());
    }


    @Test
    public void deletesUserByIdCorrectly(){
        User testUser = setUpUser();
        userDao.deleteById(testUser.getId());
        assertEquals(0, userDao.getAllUsers().size());
    }

    @Test
    public void clearAll_clearsAllInstancesFromTheDatabaseCorrectly_true(){
        User testUser = setUpUser();
        User testAltUser = setUpAltUser();
        userDao.clearAll();
        assertEquals(0, userDao.getAllUsers().size());
    }


    public User setUpUser(){
        User testUser = new User("Millie", "Secretary", "Writer", 12);
        userDao.add(testUser);
        return testUser;
    }

    public User setUpAltUser(){
        User testAltUser = new User("Mildred", "Janitor", "Recording", 8);
        userDao.add(testAltUser);
        return testAltUser;
    }

}
