package Caprish.Service.imp.sales;

import Caprish.Model.imp.sales.Item;
import Caprish.Repository.interfaces.sales.ItemRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;

@Service
public class ItemService extends MyObjectGenericService<Item, ItemRepository> {
    protected ItemService(ItemRepository childRepository) {
        super(childRepository);
    }



}
