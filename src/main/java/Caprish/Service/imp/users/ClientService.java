package Caprish.Service.imp.users;

import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.users.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService
        extends UserBasicGenericService<Client>
         {

    @Autowired
    ClientRepository clientRepository;
    public ClientService(ClientRepository clientRepository) {
        super(clientRepository);
    }

    @Override
    protected void validateBeforeSave(Client client) {
    }

    @Override
    protected void postSave(Client original, Client saved) {
    }

    public List<Client> searchByPhone(Integer phone) {
        return clientRepository.findByPhone(phone);
    }


}
