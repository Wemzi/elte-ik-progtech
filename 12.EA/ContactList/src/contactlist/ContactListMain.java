/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactlist;

/**
 *
 * @author Dobreff András
 */
public class ContactListMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ContactList model = new ContactList();
        ContactListGUI gui = new ContactListGUI();
        ContactListController controller = new ContactListController(gui, model);
        gui.setController(controller);
        gui.setVisible(true);
    }
    
}
