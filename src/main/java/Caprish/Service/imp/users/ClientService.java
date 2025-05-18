package Caprish.Service.imp.users;

import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.users.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService extends UserBasicGenericService<Client> {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        super(clientRepository);
        this.clientRepository = clientRepository;
    }

    public List<Client> searchByPhone(Integer phone) {

        return clientRepository.findByPhone(phone);
    }

}
