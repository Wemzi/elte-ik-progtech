/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactlist;

import javax.swing.JOptionPane;

/**
 *
 * @author Dobreff András
 */
class ContactListController {
    
    private ContactListGUI gui;
    private ContactList contactList;

    public ContactListController(ContactListGUI gui, ContactList contactList) {
        this.gui = gui;
        this.contactList = contactList;
    }
    
    void addContact() {
        Contact newContact = new Contact();
        contactList.addContact(newContact);
        new EditContactWindow(gui,"Add Contact",newContact).setVisible(true);
        gui.updateList(contactList.getContactNames());
    }

    void deleteContact(String name) {
        Contact contact = contactList.getContactByName(name);
        int confirm = JOptionPane.showConfirmDialog(gui, "Do you wish to delete "+name+"?", "Delte Contact", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION){
            contactList.deleteContact(contact);
        }
        gui.updateList(contactList.getContactNames());
    }

    void editContact(String name) {
        Contact contact = contactList.getContactByName(name);
        new EditContactWindow(gui,"Edit Contact",contact).setVisible(true);
        gui.updateList(contactList.getContactNames());
    }
    
}
