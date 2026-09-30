package dao;

import model.User;
import util.DBConnection;
import util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {


    // REGISTER USER
    public boolean registerUser(User user) {

        String sql =
                "INSERT INTO users " +
                "(first_name, middle_name, last_name, " +
                "email, phone, password_hash, " +
                "password_salt, role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    user.getFirstName()
            );

            ps.setString(
                    2,
                    user.getMiddleName()
            );

            ps.setString(
                    3,
                    user.getLastName()
            );

            ps.setString(
                    4,
                    user.getEmail()
            );

            ps.setString(
                    5,
                    user.getPhone()
            );

            ps.setString(
                    6,
                    user.getPasswordHash()
            );

            ps.setString(
                    7,
                    user.getPasswordSalt()
            );

            ps.setString(
                    8,
                    user.getRole()
            );


            int rows =
                    ps.executeUpdate();


            return rows > 0;


        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // LOGIN USER
    public User loginUser(
            String email,
            String password) {


        String sql =
                "SELECT * FROM users " +
                "WHERE email = ?";


        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(sql)
        ) {


            ps.setString(
                    1,
                    email
            );


            ResultSet rs =
                    ps.executeQuery();


            if (rs.next()) {

                String storedHash =
                        rs.getString(
                                "password_hash"
                        );


                String storedSalt =
                        rs.getString(
                                "password_salt"
                        );


                boolean correct =
                        PasswordUtil.verifyPassword(
                                password,
                                storedHash,
                                storedSalt
                        );


                if (correct) {

                    User user =
                            new User();


                    user.setUserId(
                            rs.getInt("user_id")
                    );


                    user.setFirstName(
                            rs.getString(
                                    "first_name"
                            )
                    );


                    user.setMiddleName(
                            rs.getString(
                                    "middle_name"
                            )
                    );


                    user.setLastName(
                            rs.getString(
                                    "last_name"
                            )
                    );


                    user.setEmail(
                            rs.getString(
                                    "email"
                            )
                    );


                    user.setPhone(
                            rs.getString(
                                    "phone"
                            )
                    );


                    user.setRole(
                            rs.getString(
                                    "role"
                            )
                    );


                    user.setAccountStatus(
                            rs.getString(
                                    "account_status"
                            )
                    );


                    return user;
                }
            }


        } catch (Exception e) {

            e.printStackTrace();
        }


        return null;
    }
}
