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

    public int changePhone(Long id, Integer phone) {
        return ((ClientService) AopContext.currentProxy()).updateField(id, "phone", phone);
    }


    public void changeTax(Long id, String tax) {
        ((ClientService) AopContext.currentProxy()).updateField(id, "tax", tax);
    }

    @Override
    protected Class<Client> getEntityClass() {
        return Client.class;
    }
}
