package com.younes.reskilingproject.bookManagement.service.InventoryService;

import com.younes.reskilingproject.bookManagement.entity.bookStore.Inventory;

import java.util.List;

public interface InventoryService {
    public List<Inventory> getAllEntries();
    public Inventory insertEntry(Inventory entry);
    public Inventory updateEntry(long id, int quantity);
    public Inventory getEntry(long id);
    public void deleteEntry(long id);
}
