package lk.ijse.gdse.ormcw.bo.custom.impl;

import lk.ijse.gdse.ormcw.bo.custom.UserBO;
import lk.ijse.gdse.ormcw.dao.DAOFactory;
import lk.ijse.gdse.ormcw.dao.custom.UserDAO;
import lk.ijse.gdse.ormcw.dto.UserDTO;
import lk.ijse.gdse.ormcw.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public String getUser(String name) throws Exception {
        String user = userDAO.getUser(name);
        return user;
    }

    @Override
    public boolean save(UserDTO userDTO) throws IOException, SQLException {
        String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());
        return userDAO.save(new User(userDTO.getId(), userDTO.getUserName(), hashedPassword, userDTO.getRole()));
    }

    @Override
    public String getNextId() throws SQLException, IOException {
       return userDAO.getNextId();
    }

    @Override
    public List<UserDTO> getAll() throws SQLException, IOException {
        List<User> users = userDAO.getAll();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUserName(user.getUserName());
            userDTO.setPassword(user.getPassword());
            userDTO.setRole(user.getRole());
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    @Override
    public boolean update(UserDTO userDTO) throws IOException, SQLException {
        String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());
        return userDAO.update(new User(userDTO.getId(), userDTO.getUserName(), hashedPassword, userDTO.getRole()));
    }
}
