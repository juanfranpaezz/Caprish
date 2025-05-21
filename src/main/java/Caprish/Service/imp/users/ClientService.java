package Caprish.Service.imp.users;

import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.users.ClientRepository;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService
        extends UserGenericService<Client, ClientRepository, ClientService> {


    public ClientService(ClientRepository repo) {
        super(repo);
    }


    public Optional<Client> searchByPhone(Integer phone) {
        return repository.findByPhone(phone);
    }

}
