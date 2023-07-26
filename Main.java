package ua.kiev.prog.case2;

import ua.kiev.prog.shared.Client;
import ua.kiev.prog.shared.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection())
        {
            try {
                try (Statement st = conn.createStatement()) {
                    st.execute("DROP TABLE IF EXISTS Clients");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            ClientDAOImpl2 dao = new ClientDAOImpl2(conn, "Clients");
            dao.createTable(Client.class);

            Client c = new Client("test", 1);
            dao.add(c);
            int id = c.getId();
            System.out.println(id);
            Client d = new Client("test1", 12);
            dao.add(d);
            int id2 = d.getId();
            System.out.println(id2);

            System.out.println("__________________________________________________");

            List<Client> list = dao.getAll(Client.class);
            for (Client cli : list)
                System.out.println(cli);

            System.out.println("__________________________________________________");

            List<Client> listOne = dao.getAll(Client.class, "name", "age");
            for (Client cli : listOne)
                System.out.println(cli);

            System.out.println("__________________________________________________");

            List<Client> listTwo = dao.getAll(Client.class, "age");
            for (Client cli : listTwo)
                System.out.println(cli);

            System.out.println("__________________________________________________");

            list.get(0).setAge(55);
            dao.update(list.get(0));
            dao.delete(list.get(0));
        }
    }
}
