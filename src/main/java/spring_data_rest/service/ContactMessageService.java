package spring_data_rest.service;

import spring_data_rest.entity.ContactMessage;
import java.util.List;

public interface ContactMessageService {
    ContactMessage create(ContactMessage input);
    ContactMessage get(Integer id);
    List<ContactMessage> getAll();
    void delete(Integer id);
}