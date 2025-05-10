package Caprish.Service.interfaces.users;

import Caprish.Model.imp.users.Client;
import java.util.List;

public interface IClientService extends IUserBasicGenericService<Client> {
    List<Client> searchByPhone(Integer phone);
}
