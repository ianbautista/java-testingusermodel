package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest
{
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        // getting userIds
//        List<User> myList = userService.findAll();
//        for (User u : myList)
//        {
//            System.out.println(u.getUserid() + " " + u.getUsername());
//        }

//        4 test admin
//        7 test cinnamon
//        11 test barnbarn
//        13 test puttat
//        14 test misskitty
    }

    @After
    public void tearDown() throws Exception
    {

    }

    @Test(expected = ResourceNotFoundException.class)
    public void a_findUserByIdNotFound() {
        assertEquals("test admin",userService.findUserById(1000).getUsername());
    }

    @Test
    public void b_findUserById()
    {
        assertEquals("test barnbarn",userService.findUserById(11).getUsername());
    }

    @Test
    public void bb_findByInvalidNameContaining()
    {
        assertEquals(0, userService.findByNameContaining("moshed").size());
    }

    @Test
    public void c_findByNameContaining()
    {
        assertEquals(5,userService.findByNameContaining("test").size());
    }

    @Test
    public void d_findAll()
    {
        assertEquals(5, userService.findAll().size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ee_deleteInvalidName() {
        assertEquals("", userService.findByName("moshedtraversy").getUsername());
    }

    @Test
    public void e_delete()
    {
        userService.delete(14);
        assertEquals(4,userService.findAll().size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void d_findByInvalidName() {
        assertEquals("", userService.findByName("moshedtraversy").getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void f_findByNameNotFound() {
        assertEquals("", userService.findByName("test christian").getUsername());
    }

    @Test
    public void g_findByName()
    {
        assertEquals("test puttat", userService.findByName("test puttat").getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void h_saveInvalidId() {
        User newUser = new User();
        newUser.setPassword("test");
        newUser.setUsername("test user");
        newUser.setPrimaryemail("test@user.com");
        newUser.setUserid(10000);
        List<Role> roles = roleService.findAll();
        for(Role r : roles) {
            newUser.getRoles().add(new UserRoles(newUser, r));
        }
        var dataUser = userService.save(newUser);
        assertEquals("", dataUser.getUsername());
    }

    @Test
    public void i_save()
    {
        // role
        Role r1 = new Role("admin");
        r1.setRoleid(1);

        // new user
        String user4Name = "christian";
        User u4 = new User(user4Name,
                "godkinglordboss",
                "god@king.com");
        u4.getRoles().add(new UserRoles(u4, r1));

        User addUser =  userService.save(u4);
        assertNotNull(addUser);
        assertEquals(user4Name,addUser.getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void j_updateNotFound() {
        Role r2 = new Role("user");
        r2.setRoleid(2);
        String user5Name = "test barnbarn";
        User u5 = new User(user5Name,
                "godkinglordboss",
                "god@king.com");
        u5.setUserid(400);
        u5.getRoles()
                .add(new UserRoles(u5, r2));
        User addUser = userService.update(u5,400);
        assertNotNull(addUser);
        assertEquals(user5Name,addUser.getUsername());
    }


    @Test
    public void k_update()
    {
        String updateName = "testtest";

        User updateUser = new User();
        updateUser.setUsername("testtest");
        updateUser.setUserid(14);
        updateUser.setPrimaryemail("testtest@lambda.com");
        updateUser.setPassword("godkinglordboss");
        updateUser.getUseremails().add(new Useremail(updateUser, "god@king.com"));

        Role role = new Role("godking");
        role.setRoleid(1);
        updateUser.getRoles().add(new UserRoles(updateUser, role));

        User userDetails = userService.update(updateUser, 13);
        assertNotNull(userDetails);
        assertEquals(updateName, userService.findUserById(13).getUsername());
    }

    @Test
    public void y_delete()
    {
        userService.delete(7);
        assertEquals(4, userService.findAll().size());
    }


    @Test
    public void z_deleteAll()
    {
        userService.deleteAll();
        assertEquals(0, userService.findAll().size());
    }
}