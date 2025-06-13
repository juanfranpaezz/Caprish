package Caprish.Service.imp.users;

import Caprish.Exception.ClientException;
import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.users.ClientRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ClientService
        extends MyObjectGenericService<Client, ClientRepository, ClientService> {


    public ClientService(ClientRepository repo) {
        super(repo);
    }

    @Override
    protected void verifySpecificAttributes(Client entity) {

    }


    public Client searchByPhone(Integer phone) {
        Optional<Client> client = repository.findByPhone(phone);
        if (client.isEmpty() || client.get().getId() == null) {
            throw new ClientException("No existe ningun cliente con ese numero de telefono.");
        }else{
            return client.get();
        }
    }

    public Client findByCredentialId(Long credentialId) {
        return repository.findByCredential(credentialId);
    }


}
