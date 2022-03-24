/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactlist;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dobreff András
 */
class ContactList {
    
    private List<Contact> contacts = new ArrayList<>();

    List<String> getContactNames() {
        List<String> result = new ArrayList<>();
        for(Contact contact : contacts){
            result.add(contact.getName());
        }
        
        return result;
    }

    Contact getContactByName(String name) {
        for(Contact contact : contacts){
            if(contact.getName().equals(name)){
                return contact;
            }
        }
        return null;
    }

    void deleteContact(Contact contact) {
        contacts.remove(contact);
    }

    void addContact(Contact newContact) {
        contacts.add(newContact);
    }
    
}
