/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package applications.rest;

import java.sql.*;
import java.util.*;
import spark.*;
import static spark.Spark.*;
import com.google.gson.Gson;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

public class App {

    public static void main(String[] args) {
        var db = new Database("krusty.sqlite");
        port(8888);
        get("/ping", (req, res) -> db.ping(req, res));
        post("/reset", (req, res) -> db.reset(req, res));
        get("/customers", (req, res) -> db.getCustomers(req, res));
        get("/ingredients", (req, res) -> db.getMaterials(req, res));
        get("/cookies", (req, res) -> db.getCookies(req, res));
        get("/recipes", (req, res) -> db.getRecipes(req, res));  
    }
}

class Database {

    /**
     * The database connection.
     */
    private Connection conn;

    /**
     * Creates the database interface object. Connection to the database is
     * performed later.
     */
    public Database(String filename) {
        openConnection(filename);
    }

    /**
     * Opens a connection to the database, using the specified filename (if we'd
     * used a traditional DBMS, such as PostgreSQL or MariaDB, we would have
     * specified username and password instead).
     */
    public boolean openConnection(String filename) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + filename);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Closes the connection to the database.
     */
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the connection to the database has been established
     * 
     * @return true if the connection has been established
     */
    public boolean isConnected() {
        return conn != null;
    }

    String ping(Request req, Response res) {
        var result = new String("\npong" + " " + res.status()) + "\n";
        res.status(200);
        return result;
    }

    
    String reset(Request req, Response res) {
        res.type("application/json");
        String[] statements = {
            "DELETE FROM cookies", "DELETE FROM pallets", "DELETE FROM orders",
            "DELETE FROM order_sizes", "DELETE FROM customers", "DELETE FROM materials", "DELETE FROM ingredients",
            
            
            "INSERT INTO customers (customer_id, customer_name, address)" +  "VALUES(1, 'Finkakor AB', 'Helsingborg')",
            "INSERT INTO customers (customer_id, customer_name, address)" +  "VALUES(2, 'Småbröd AB', 'Malmö')",
            "INSERT INTO customers (customer_id, customer_name, address)" +  "VALUES(3, 'Kaffebröd AB', 'Landskrona')",
            "INSERT INTO customers (customer_id, customer_name, address)" +  "VALUES(4, 'Bjudkakor AB', 'Ystad')",
            "INSERT INTO customers (customer_id, customer_name, address)" +  "VALUES(5, 'Kalaskakor AB', 'Trelleborg')",
            "INSERT INTO customers (customer_id, customer_name, address)" +  "VALUES(6, 'Partykakor AB', 'Kristianstad')",
            "INSERT INTO customers (customer_id, customer_name, address)" +  "VALUES(7, 'Gästkakor AB', 'Hässleholm')",
            "INSERT INTO customers (customer_id, customer_name, address)" +  "VALUES (8, 'Skånekakor AB', 'Perstorp')",

            "INSERT INTO cookies (cookie_id, cookie_name)" + "VALUES(1, 'Nut ring')",
            "INSERT INTO cookies (cookie_id, cookie_name)" + "VALUES(2, 'Nut cookie')",
            "INSERT INTO cookies (cookie_id, cookie_name)" + "VALUES(3, 'Amernis')",
            "INSERT INTO cookies (cookie_id, cookie_name)" + "VALUES(4, 'Tango')",
            "INSERT INTO cookies (cookie_id, cookie_name)" + "VALUES(5, 'Almond delight')",
            "INSERT INTO cookies (cookie_id, cookie_name)" + "VALUES(6, 'Berliner')",
             
            "INSERT INTO materials (material_id, material_name, material_amount, unit)" + "VALUES(1, 'Flour', 100000, 'g')",
            "INSERT INTO materials (material_id, material_name, material_amount, unit)" + "VALUES(2, 'Butter', 100000, 'g')",
            "INSERT INTO materials (material_id, material_name, material_amount, unit)" + "VALUES(3, 'Icing sugar', 100000, 'g')",
            "INSERT INTO materials (material_id, material_name, material_amount, unit)" + "VALUES(4, 'Roasted, chopped nuts', 100000, 'g')",
            "INSERT INTO materials (material_id, material_name, material_amount, unit)" + "VALUES(5, 'Fine-ground nuts', 100000, 'g')",
            "INSERT INTO materials (material_id, material_name, material_amount, unit)" + "VALUES(6, 'Ground, roasted nuts', 100000, 'g')",
            "INSERT INTO materials (material_id, material_name, material_amount, unit)" + "VALUES(7, 'Bread crumbs', 100000, 'g')",
            "INSERT INTO materials (material_id, material_name, material_amount, unit)" + "VALUES(8, 'Sugar', 100000, 'g')",
            "INSERT INTO materials (material_id, material_name, material_amount, unit)" + "VALUES(9, 'Egg whites', 100000, 'ml')",
            "INSERT INTO materials (material_id, material_name, material_amount, unit)" + "VALUES(10, 'Chocolate', 100000, 'g')",
            "INSERT INTO materials (material_id, material_name, material_amount, unit)" + "VALUES(11, 'Marzipan', 100000, 'g')",
            "INSERT INTO materials (material_id, material_name, material_amount, unit)" + "VALUES(12, 'Eggs', 100000, 'g')",
            "INSERT INTO materials (material_id, material_name, material_amount, unit)" + "VALUES(13, 'Potato starch', 100000, 'g')",
            "INSERT INTO materials (material_id, material_name, material_amount, unit)" + "VALUES(14, 'Wheat flour', 100000, 'g')",
            "INSERT INTO materials (material_id, material_name, material_amount, unit)" + "VALUES(15, 'Sodium bicarbonate', 100000, 'g')",
            "INSERT INTO materials (material_id, material_name, material_amount, unit)" + "VALUES(16, 'Vanilla', 100000, 'g')",
            "INSERT INTO materials (material_id, material_name, material_amount, unit)" + "VALUES(17, 'Chopped almonds', 100000, 'g')",
            "INSERT INTO materials (material_id, material_name, material_amount, unit)" + "VALUES(18, 'Cinnamon', 100000, 'g')",
            "INSERT INTO materials (material_id, material_name, material_amount, unit)" + "VALUES(19, 'Vanilla sugar', 100000, 'g')",

            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (1, 1, 450)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (1, 2, 450)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (1, 3, 190)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (1, 4, 225)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (2, 5, 750)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (2, 6, 625)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (2, 7, 125)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (2, 8, 375)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (2, 9, 350)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (2, 10, 50)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (3, 11, 750)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (3, 2, 250)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (3, 12, 250)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (3, 13, 25)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (3, 14, 25)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (4, 2, 200)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (4, 8, 250)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (4, 1, 300)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (4, 15, 4)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (4, 16, 2)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (5, 2, 400)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (5, 8, 270)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (5, 17, 279)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (5, 1, 400)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (5, 18, 10)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (6, 1, 350)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (6, 2, 250)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (6, 3, 100)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (6, 12, 50)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (6, 19, 5)",
            "INSERT INTO ingredients (cookie_id, material_id, ingredient_amount)" + "VALUES (6, 10, 50)" };
           
        try (var ps = conn.createStatement()) {
            for (String statement : statements) {
                ps.addBatch(statement);
            }
            var x = ps.executeBatch();
            res.status(200);
            return "\nOK\n";
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500);
            return "\nCould not insert values \n";
        }
    }

    public String getCustomers(Request req, Response res) {
        res.type("application/json");
        var query = "SELECT customer_name AS name, address\n" + "FROM customers\n";
        var params = new LinkedList<String>();
        try (var ps = conn.prepareStatement(query)) {
            var index = 0;
            for (var param : params) {
                ps.setString(++index, param);
            }
            var rs = ps.executeQuery();
            var result = JSONizer.toJSON(rs, "customers");
            res.status(200);
            res.body(result);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getMaterials(Request req, Response res) {
        res.type("application/json");
        var query = "SELECT material_name AS name, material_amount AS quantity, unit\n" + "FROM materials\n";
        var params = new LinkedList<String>();
        try (var ps = conn.prepareStatement(query)) {
            var index = 0;
            for (var param : params) {
                ps.setString(++index, param);
            }
            var rs = ps.executeQuery();
            var result = JSONizer.toJSON(rs, "ingredients");
            res.status(200);
            res.body(result);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getCookies(Request req, Response res) {
        res.type("application/json");
        var query = "SELECT cookie_name AS name\n" + "FROM cookies\n" + "ORDER BY cookie_name\n";
        var params = new LinkedList<String>();
        try (var ps = conn.prepareStatement(query)) {
            var index = 0;
            for (var param : params) {
                ps.setString(++index, param);
            }
            var rs = ps.executeQuery();
            var result = JSONizer.toJSON(rs, "cookies");
            res.status(200);
            res.body(result);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
      }

    public String getRecipes(Request req, Response res) {
        res.type("application/json");
        var query = "SELECT cookie_name AS cookie, material_name AS ingredient, ingredient_amount AS quantity, unit\n" 
                 + "FROM cookies\n" + "JOIN ingredients\n" + "USING (cookie_id)\n" + "JOIN materials\n" + "USING (material_id)";
        var params = new LinkedList<String>();
        try (var ps = conn.prepareStatement(query)) {
            var index = 0;
            for (var param : params) {
                ps.setString(++index, param);
            }
            var rs = ps.executeQuery();
            var result = JSONizer.toJSON(rs, "recipes");
            res.status(200);
            res.body(result);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }


}

/**
 * Auxiliary class for automatically translating a ResultSet to JSON
 */
class JSONizer {

    public static String toJSON(ResultSet rs, String name) throws SQLException {
        StringBuilder sb = new StringBuilder();
        ResultSetMetaData meta = rs.getMetaData();
        boolean first = true;
        sb.append("{\n");
        sb.append("  \"" + name + "\": [\n");
        while (rs.next()) {
            if (!first) {
                sb.append(",");
                sb.append("\n");
            }
            first = false;
            sb.append("    {");
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                String label = meta.getColumnLabel(i);
                String value = getValue(rs, i, meta.getColumnType(i));
                sb.append("\"" + label + "\": " + value);
                if (i < meta.getColumnCount()) {
                    sb.append(", ");
                }
            }
            sb.append("}");
        }
        sb.append("\n");
        sb.append("  ]\n");
        sb.append("}\n");
        return sb.toString();
    }

    private static String getValue(ResultSet rs, int i, int columnType) throws SQLException {
        switch (columnType) {
        case java.sql.Types.INTEGER:
            return String.valueOf(rs.getInt(i));
        case java.sql.Types.REAL:
        case java.sql.Types.DOUBLE:
        case java.sql.Types.FLOAT:
            return String.valueOf(rs.getDouble(i));
        default:
            return "\"" + rs.getString(i) + "\"";
        }
    }
}
