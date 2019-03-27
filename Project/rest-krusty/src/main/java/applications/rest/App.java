/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package applications.rest;

import java.sql.*;
import java.util.*;
import spark.*;
import static spark.Spark.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

public class App {

    public static void main(String[] args) {
        var db = new Database("krusty.sqlite");
        port(8888);
        post("/reset", (req, res) -> db.reset(req, res));
        get("/customers", (req, res) -> db.getCustomers(req, res));
        get("/ingredients", (req, res) -> db.getMaterials(req, res));
        get("/cookies", (req, res) -> db.getCookies(req, res));
        get("/recipes", (req, res) -> db.getRecipes(req, res)); 
        post("/pallets", (req, res) -> db.postPallet(req, res));
        get("/pallets", (req, res) -> db.getPallets(req, res)); 
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

    String reset(Request req, Response res) {
        res.type("application/json");
        String[] statements = {
            "DELETE FROM cookies", "DELETE FROM pallets", "DELETE FROM orders",
            "DELETE FROM order_sizes", "DELETE FROM customers", "DELETE FROM materials", "DELETE FROM ingredients",
            
            "INSERT INTO customers (customer_name, address)" +  "VALUES('Finkakor AB', 'Helsingborg')",
            "INSERT INTO customers (customer_name, address)" +  "VALUES('Småbröd AB', 'Malmö')",
            "INSERT INTO customers (customer_name, address)" +  "VALUES('Kaffebröd AB', 'Landskrona')",
            "INSERT INTO customers (customer_name, address)" +  "VALUES('Bjudkakor AB', 'Ystad')",
            "INSERT INTO customers (customer_name, address)" +  "VALUES('Kalaskakor AB', 'Trelleborg')",
            "INSERT INTO customers (customer_name, address)" +  "VALUES('Partykakor AB', 'Kristianstad')",
            "INSERT INTO customers (customer_name, address)" +  "VALUES('Gästkakor AB', 'Hässleholm')",
            "INSERT INTO customers (customer_name, address)" +  "VALUES ('Skånekakor AB', 'Perstorp')",

            "INSERT INTO cookies (cookie_name)" + "VALUES('Nut ring')",
            "INSERT INTO cookies (cookie_name)" + "VALUES('Nut cookie')",
            "INSERT INTO cookies (cookie_name)" + "VALUES('Amneris')",
            "INSERT INTO cookies (cookie_name)" + "VALUES('Tango')",
            "INSERT INTO cookies (cookie_name)" + "VALUES('Almond delight')",
            "INSERT INTO cookies (cookie_name)" + "VALUES('Berliner')",
             
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

            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Nut ring', 1, 450)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Nut ring', 2, 450)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Nut ring', 3, 190)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Nut ring', 4, 225)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Nut cookie', 5, 750)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Nut cookie', 6, 625)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Nut cookie', 7, 125)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Nut cookie', 8, 375)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Nut cookie', 9, 350)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Nut cookie', 10, 50)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Amneris', 11, 750)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Amneris', 2, 250)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Amneris', 12, 250)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Amneris', 13, 25)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Amneris', 14, 25)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Tango', 2, 200)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Tango', 8, 250)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Tango', 1, 300)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Tango', 15, 4)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Tango', 16, 2)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Almond delight', 2, 400)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Almond delight', 8, 270)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Almond delight', 17, 279)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Almond delight', 1, 400)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Almond delight', 18, 10)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Berliner', 1, 350)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Berliner', 2, 250)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Berliner', 3, 100)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Berliner', 12, 50)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Berliner', 19, 5)",
            "INSERT INTO ingredients (cookie_name, material_id, ingredient_amount)" + "VALUES ('Berliner', 10, 50)" };
           
        try (var ps = conn.createStatement()) { 
            for (String statement : statements) {
               ps.addBatch(statement);
            }
            ps.executeBatch();
            res.status(200);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson("status: " +  "ok");
        } catch (SQLException e) {
            e.printStackTrace();
            res.status(500);
            return "";
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
            res.status(500);
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
            res.status(500);
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
            res.status(500);
        }
        return "";
      }

    public String getRecipes(Request req, Response res) {
        res.type("application/json");
        var query = "SELECT cookie_name AS cookie, material_name AS ingredient, ingredient_amount AS quantity, unit\n" 
                 + "FROM cookies\n" + "JOIN ingredients\n" + "USING (cookie_name)\n" + "JOIN materials\n" + "USING (material_id)"
                 + "ORDER BY cookie, ingredient";
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
            res.status(500);
        }
        return "";
    }

    String postPallet(Request req, Response res) {
        res.type("application/json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        var statement = "INSERT \n" + "INTO pallets(cookie_name, production_date)\n"
                + "VALUES  (?, CURRENT_DATE);";
        try (var ps = conn.prepareStatement(statement)) {
            conn.createStatement().execute("PRAGMA foreign_keys = ON");
            ps.setString(1, req.queryParams("cookie"));
            if (ps.executeUpdate() != 1) {
                res.status(400);
                return gson.toJson("status:  " + "not enough ingredients"); // todo!!!!! 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return gson.toJson("status:  " + "no such cookie");
        }

        var query = "SELECT pallet_id\n" + "FROM pallets\n" + "WHERE rowid = last_insert_rowid()";
        try (var ps = conn.prepareStatement(query)) {
            var rs = ps.executeQuery();
            if (rs.next()) {
                var id = rs.getString("pallet_id");
                res.status(201);
                return gson.toJson("status: " + "ok" + " " + "id: " + "" + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        res.status(418);
        return "Error";
    }

    public String getPallets(Request req, Response res) {
        res.type("application/json");
        var query = "SELECT pallet_id AS id, cookie_name AS cookie, production_date AS ProductionDate, blocked, customer_id as customer\n" 
                 + "FROM pallets\n" + "LEFT JOIN orders\n" + "USING (order_nbr);"; 
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
            res.status(500);
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
