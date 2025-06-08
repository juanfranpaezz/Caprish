package Caprish.Service.imp.business;

import Caprish.Model.imp.business.Address;
import Caprish.Model.imp.business.Branch;
import Caprish.Repository.interfaces.business.BranchRepository;
import Caprish.Service.imp.MyObjectGenericService;
import Caprish.Service.others.GoogleGeocodingService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;


@Service
public class BranchService extends MyObjectGenericService<Branch, BranchRepository, BranchService> {


    private final GoogleGeocodingService geocodingService;

    protected BranchService(BranchRepository childRepository, GoogleGeocodingService geocodingService) {
        super(childRepository);
        this.geocodingService = geocodingService;
    }

    @Override
    protected void verifySpecificAttributes(Branch entity) {

        if (entity.getBusiness() == null || entity.getBusiness().getId() == null) {
            throw new IllegalArgumentException("La sucursal debe estar asociada a un negocio existente.");
        }

        if (entity.getBranch_type() == null) {
            throw new IllegalArgumentException("La sucursal debe tener un tipo definido.");
        }

        Address address = entity.getAddress();
        if (address == null ||
                address.getDireccion() == null || address.getDireccion().isBlank() ||
                address.getLocalidad() == null || address.getLocalidad().isBlank() ||
                address.getProvincia() == null || address.getProvincia().isBlank() ||
                address.getPais() == null || address.getPais().isBlank()) {
            throw new IllegalArgumentException("Todos los campos de dirección deben estar completos.");
        }
    }

    @Override
    public Branch save(Branch branch) {
        // Validar dirección antes de guardar
        if (branch.getAddress() == null) {
            throw new IllegalArgumentException("La dirección no puede estar vacía");
        }
        Address address= branch.getAddress();
        if(address.getDireccion()== null || address.getDireccion().isBlank()
                || address.getLocalidad()== null || address.getLocalidad().isBlank()
                || address.getProvincia()== null || address.getProvincia().isBlank()
                || address.getPais()== null || address.getPais().isBlank()) {
            throw new IllegalArgumentException("Todos los campos deben estar completos");
        }

        boolean direccionValida= geocodingService.validateAddress(address.getFullAddress());
        if (!direccionValida) {
            throw new IllegalArgumentException("La direccion no es valida segun Google Maps");
        }
        return super.save(branch);
    }

    public void changeAddress(Long id, String address) {
        if(address== null || address.isBlank()) {
            throw new IllegalArgumentException("la direccion no puede estar vacia");
        }
        boolean direccionValida = geocodingService.validateAddress(address);
        if (!direccionValida) {
            throw new IllegalArgumentException("la direccion no es valida segun Google Maps");
        }
        ((BranchService) AopContext.currentProxy()).updateField(id, "address", "address");
    }

    public void changeBranch_type(Long id, String branch_type) {
        ((BranchService) AopContext.currentProxy()).updateField(id, "branch_type", branch_type);
    }

    public boolean AddresValidation(String address){
        return geocodingService.validateAddress(address);
    }
}
