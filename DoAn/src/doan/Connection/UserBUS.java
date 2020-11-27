package doan.Connection;

import doan.Connection.UserDAO;
import doan.Connection.UserDTO;
import java.util.ArrayList;

public class UserBUS {

    public static ArrayList<UserDTO> dsuser;

    public UserBUS() {
    }

    public void docDSuser() {
        UserDAO data = new UserDAO();
        if (dsuser == null) {
            dsuser = new ArrayList<UserDTO>();
        }
        dsuser = data.docDSSP();
    }

    public UserDTO Tim(String id) {
        for (UserDTO user : dsuser) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
}
