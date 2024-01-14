package com.younes.reskilingproject.bookManagement.service.InventoryService;

import com.younes.reskilingproject.bookManagement.entity.bookStore.Inventory;
import com.younes.reskilingproject.bookManagement.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImplInventoryService implements InventoryService{

    private InventoryRepository inventoryRepository;
    @Autowired
    public ImplInventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    @Override
    public List<Inventory> getAllEntries() {
        return inventoryRepository.findAll();
    }
    @Override
    public Inventory insertEntry(Inventory entry) {
        return inventoryRepository.save(entry);
    }

    @Override
    public Inventory updateEntry(long id, int quantity) {
        Inventory tempEntry = inventoryRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Entry by the id : " + id + "not found"));
        tempEntry.setQuantity(quantity);
        return inventoryRepository.save(tempEntry);
    }

    @Override
    public Inventory getEntry(long id) {
        return inventoryRepository.findById(id).orElse(null);
    }
    @Override
    public void deleteEntry(long id) {
        inventoryRepository.deleteById(id);
    }
}
