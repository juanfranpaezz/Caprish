package Caprish.Service.imp.users;

import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.users.ClientRepository;
import Caprish.Service.interfaces.users.IClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService
        extends UserBasicGenericService<Client>
        implements IClientService {

    private final ClientRepository clientRepo;

    public ClientService(ClientRepository repo) {
        super(repo);
        this.clientRepo = repo;
    }

    @Override
    protected void validateBeforeSave(Client client) {
    }

    @Override
    protected void postSave(Client original, Client saved) {
    }

    @Override
    public List<Client> searchByPhone(Integer phone) {
        return clientRepo.findByPhone(phone);
    }


}
