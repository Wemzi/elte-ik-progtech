/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactlist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

/**
 *
 * @author Dobreff András
 */
public class ContactListGUI extends JFrame{
    
    private JButton editBtn;
    private JButton deleteBtn;
    private JButton addBtn;
    private JList contactsList;
    private ContactListController controller;
    
    public ContactListGUI() {
        setSize(400,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.editBtn = new JButton("Edit Contact");
        this.deleteBtn = new JButton("Delte Contact");
        this.addBtn = new JButton("Add Contact");
        this.contactsList = new JList();
        initComponents();
    }

    public void setController(ContactListController controller){
        this.controller = controller;
    }
    
    private void initComponents() {
        this.contactsList.setModel(new DefaultListModel());
        
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JList list = ContactListGUI.this.contactsList;
                int index  = list.getSelectedIndex();
                String name = (String) ((DefaultListModel) list.getModel()).getElementAt(index);
                controller.editContact(name);
            }
        });
        
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JList list = ContactListGUI.this.contactsList;
                int index  = list.getSelectedIndex();
                String name = (String) ((DefaultListModel) list.getModel()).getElementAt(index);
                controller.deleteContact(name);
            }
        });
        
        addBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.addContact();
            }
        });
        
        this.applyLayout();
    }
    
    private void applyLayout(){
        GroupLayout layout = new GroupLayout(getContentPane());
        this.setLayout(layout);
        
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(this.contactsList);
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll)
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addComponent(this.addBtn)
                        .addGap(10)
                        .addComponent(this.editBtn)
                        .addGap(10)
                        .addComponent(this.deleteBtn)
                )
                .addContainerGap()
        );
        
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup()
                        .addComponent(scroll)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(this.addBtn)
                                .addGap(10)
                                .addComponent(this.editBtn)
                                .addGap(10)
                                .addComponent(this.deleteBtn)
                                .addContainerGap()
                        )
                )
                .addContainerGap()
        );
        
        pack();
    }
    
    public void updateList(List<String> list){
        DefaultListModel model = new DefaultListModel();
        for(String element : list){
            model.addElement(element);
        }
        this.contactsList.setModel(model);
    }
}
