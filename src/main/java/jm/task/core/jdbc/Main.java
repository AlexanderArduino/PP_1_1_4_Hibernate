package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl usi = new UserServiceImpl();

        usi.createUsersTable();
        usi.removeUserById(2);
//        usi.saveUser("Ivan", "Petrov", (byte)20);
//        usi.dropUsersTable();
        }
}
