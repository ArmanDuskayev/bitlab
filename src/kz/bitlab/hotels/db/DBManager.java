package kz.bitlab.hotels.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DBManager {

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_users?serverTimezone=UTC&useUnicode=true", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean addUser(User user) {
        int rows = 0;

        try {

            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO users (email, password, full_name, picture) " +
                    "VALUES (?, ?, ?, ?)");

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFullName());
            statement.setString(4, user.getPicture());

            rows = statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static User getUserByEmail(String email) {
        User user = null;
        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("full_name"),
                        resultSet.getString("picture")
                );
            }

            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public static boolean updateUserPicture(User user) {
        int rows = 0;

        try {

            PreparedStatement statement = connection.prepareStatement("" +
                    "UPDATE users SET picture = ? WHERE id = ?");

            statement.setString(1, user.getPicture());
            statement.setLong(2, user.getId());

            rows = statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static boolean updateUserProfile(User user) {
        int rows = 0;

        try {

            PreparedStatement statement = connection.prepareStatement("" +
                    "UPDATE users SET full_name = ? WHERE id = ?");

            statement.setString(1, user.getFullName());
            statement.setLong(2, user.getId());

            rows = statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static boolean updateUserPassword(User user) {
        int rows = 0;

        try {

            PreparedStatement statement = connection.prepareStatement("" +
                    "UPDATE users SET password = ? WHERE id = ?");

            statement.setString(1, user.getPassword());
            statement.setLong(2, user.getId());

            rows = statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static boolean addHotel(Hotel hotel) {
        int rows = 0;

        try {

            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO hotels (id, name, author_id, stars, description, added_date, price) " +
                    "VALUES (NULL, ?, ?, ?, ?, NOW(), ?)");

            statement.setString(1, hotel.getName());
            statement.setLong(2, hotel.getAuthor().getId());
            statement.setInt(3, hotel.getStars());
            statement.setString(4, hotel.getDescription());
            statement.setInt(5, hotel.getPrice());

            rows = statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static ArrayList<Hotel> getAllHotels() {
        ArrayList<Hotel> hotels = new ArrayList<>();
        try {

            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT h.id, h.name, h.description, h.added_date, h.price, h.stars, h.author_id, u.full_name, u.picture " +
                    "FROM hotels h " +
                    "INNER JOIN users u ON u.id = h.author_id " +
                    "ORDER BY h.price ASC ");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                hotels.add(
                        new Hotel(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                new User(
                                        resultSet.getLong("author_id"),
                                        null, null,
                                        resultSet.getString("full_name"),
                                        resultSet.getString("picture")
                                ),
                                resultSet.getInt("stars"),
                                resultSet.getString("description"),
                                resultSet.getTimestamp("added_date"),
                                resultSet.getInt("price")
                        )
                );
            }

            statement.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

        return hotels;
    }

    public static Hotel getHotel(Long id) {
        Hotel hotel = null;
        try {

            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT h.id, h.name, h.description, h.added_date, h.price, h.stars, h.author_id, u.full_name, u.picture " +
                    "FROM hotels h " +
                    "INNER JOIN users u ON u.id = h.author_id " +
                    "WHERE h.id = ?");

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                hotel = new Hotel(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        new User(
                                resultSet.getLong("author_id"),
                                null, null,
                                resultSet.getString("full_name"),
                                resultSet.getString("picture")
                        ),
                        resultSet.getInt("stars"),
                        resultSet.getString("description"),
                        resultSet.getTimestamp("added_date"),
                        resultSet.getInt("price")
                );
            }

            statement.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

        return hotel;
    }

    public static boolean deleteHotel(Long id) {
        int rows = 0;
        try {

            PreparedStatement statement = connection.prepareStatement("DELETE FROM hotels WHERE id = ?");

            statement.setLong(1, id);

            rows = statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static boolean saveHotel(Hotel hotel) {
        int rows = 0;

        try {

            PreparedStatement statement = connection.prepareStatement("" +
                    "UPDATE hotels SET name = ?, stars = ?, description = ?, added_date = NOW(), price = ? WHERE id = ?");

            statement.setString(1, hotel.getName());
            statement.setInt(2, hotel.getStars());
            statement.setString(3, hotel.getDescription());
            statement.setInt(4, hotel.getPrice());
            statement.setLong(5, hotel.getId());

            rows = statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static boolean addComment(Comment comment) {
        int rows = 0;

        try {

            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO comments (hotel_id, user_id, comment, added_date) " +
                    "VALUES (?, ?, ?, NOW())");

            statement.setLong(1, comment.getHotel().getId());
            statement.setLong(2, comment.getUser().getId());
            statement.setString(3, comment.getComment());

            rows = statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static ArrayList<Comment> getAllComments(Long hotelId) {

        ArrayList<Comment> comments = new ArrayList<>();
        try {

            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT c.id, c.hotel_id, c.user_id, c.comment, c.added_date, u.full_name, u.picture " +
                    "FROM comments c " +
                    "INNER JOIN users u ON u.id = c.user_id " +
                    "WHERE c.hotel_id = ? " +
                    "ORDER BY c.added_date DESC ");

            statement.setLong(1, hotelId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                comments.add(
                        new Comment(
                                resultSet.getLong("id"),
                                new Hotel(
                                        resultSet.getLong("hotel_id"),
                                        null, null, 0, null, null, 0
                                ),
                                new User(
                                        resultSet.getLong("user_id"),
                                        null, null,
                                        resultSet.getString("full_name"),
                                        resultSet.getString("picture")
                                ),
                                resultSet.getString("comment"),
                                resultSet.getTimestamp("added_date")
                        )
                );

            }
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return comments;
    }

    public static boolean deleteComment(Long commentid) {
        int rows = 0;
        try {

            PreparedStatement statement = connection.prepareStatement("DELETE FROM comments WHERE id = ?");

            statement.setLong(1, commentid);

            rows = statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }
}
