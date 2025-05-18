package Caprish.Service.imp.users;

import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.users.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService
        extends UserGenericService<Client, ClientRepository> {


    public ClientService(ClientRepository repo) {
        super(repo);
    }

    @Override
    protected void validateBeforeSave(Client client) {
    }

    @Override
    protected void postSave(Client original, Client saved) {
    }

    public List<Client> searchByPhone(Integer phone) {
        return repository.findByPhone(phone);
    }

    public void changePhone(Long id, Integer phone) {
        Client user = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));
        user.setPhone(phone);
        repository.save(user);
    }

    public void changeTax(Long id, String tax) {
        Client user = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));
        user.setTax(tax);
        repository.save(user);
    }

    @Override
    protected Class<Client> getEntityClass() {
        return Client.class;
    }
}
