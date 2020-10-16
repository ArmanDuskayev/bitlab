package kz.bitlab.hotels.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                    "INSERT INTO users (email, password, full_name, picture, city_id) " +
                    "VALUES (?, ?, ?, ?, ?)");

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFullName());
            statement.setString(4, user.getPicture());
            statement.setLong(5, user.getCity().getId());

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

            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT u.id, u.email, u.password, u.full_name, u.picture, u.city_id, c.name AS cityName, c.country_id, co.name AS countryName, co.code " +
                    "FROM users u " +
                    "INNER JOIN cities c ON c.id = u.city_id " +
                    "INNER JOIN countries co ON c.country_id = co_id " +
                    "WHERE u.email = ?");
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("full_name"),
                        resultSet.getString("picture"),
                        new City(
                                resultSet.getLong("city_id"),
                                resultSet.getString("cityName"),
                                new Country(
                                        resultSet.getLong("country_id"),
                                        resultSet.getString("countryName"),
                                        resultSet.getString("code")
                                )
                        )
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
                                        resultSet.getString("picture"),
                                        null
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
                                resultSet.getString("picture"),
                                null
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
                    "UPDATE hotels SET name = ?, stars = ?, description = ?, added_date = NOW(), price = ? " +
                    "WHERE id = ?");

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
                    "INSERT INTO comments (hotel_id, user_id, comment, added_date, parent_id) " +
                    "VALUES (?, ?, ?, NOW(), ?)");

            statement.setLong(1, comment.getHotel().getId());
            statement.setLong(2, comment.getUser().getId());
            statement.setString(3, comment.getComment());
            statement.setLong(4, comment.getParentId());

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
                    "SELECT c.id, c.hotel_id, c.user_id, c.comment, c.added_date, c.parent_id, u.full_name, u.picture " +
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
                                        resultSet.getString("picture"),
                                        null
                                ),
                                resultSet.getString("comment"),
                                resultSet.getTimestamp("added_date"),
                                resultSet.getLong("parent_id")
                        )
                );

            }
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return comments;
    }

    public static boolean deleteComment(Long commentId) {
        int rows = 0;
        try {

            PreparedStatement statement = connection.prepareStatement("DELETE FROM comments WHERE id = ?");

            statement.setLong(1, commentId);

            rows = statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows > 0;
    }

    public static ArrayList<Country> getAllCountries() {

        ArrayList<Country> countries = new ArrayList<>();

        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM countries");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                countries.add(new Country(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("code")
                ));
            }

            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return countries;
    }

    public static ArrayList<City> getCitiesByCountryId (Long countryId) {

        ArrayList<City> cities = new ArrayList<>();

        try {

            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT c.id, c.name, c.country_id, co.name AS countryName, co.code " +
                    "FROM cities c " +
                    "INNER JOIN countries co ON co.id = c.country_id " +
                    "WHERE c.country_id = ? ");
            statement.setLong(1, countryId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                cities.add(new City(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        new Country(
                                resultSet.getLong("country_id"),
                                resultSet.getString("countryName"),
                                resultSet.getString("code")
                        )
                ));
            }

            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cities;
    }

    public static City getCityById (Long cityId) {

        City city = null;

        try {

            PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT c.id, c.name, c.country_id, co.name AS countryName, co.code " +
                    "FROM cities c " +
                    "INNER JOIN countries co ON co.id = c.country_id " +
                    "WHERE c.id = ? ");
            statement.setLong(1, cityId);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("test point 1 + " + cityId);

            if (resultSet.next()) {
                city = new City(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        new Country(
                                resultSet.getLong("country_id"),
                                resultSet.getString("countryName"),
                                resultSet.getString("code")
                        )
                );
                System.out.println("test point 2");
            }

            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return city;
    }

}
