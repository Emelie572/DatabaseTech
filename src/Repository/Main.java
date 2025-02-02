package Repository;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {

        Repository repository = new Repository();
        repository.getCustomers();


    }
}
