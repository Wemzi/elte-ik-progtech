/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactlist;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author Dobreff András
 */
class EditContactWindow extends JDialog{
    
    private Contact contact;
    
    private final JLabel name = new JLabel("Name:");
    private final JLabel phone = new JLabel("Phone:");
    private final JLabel address = new JLabel("Address:");
    private final JTextField name_field = new JTextField();
    private final JTextField phone_field = new JTextField();
    private final JTextField address_field = new JTextField();
    private final JButton Save = new JButton("Save");
    private final JButton Cancel = new JButton("Cancel");
    
    public EditContactWindow(Frame owner, String title, Contact contact) {
        super(owner,title,true);
        this.contact = contact;
        this.name_field.setText(contact.getName());
        this.phone_field.setText(contact.getPhone());
        this.address_field.setText(contact.getAddress());
        this.initComponents();
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        this.Save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditContactWindow.this.contact.setName(EditContactWindow.this.name_field.getText());
                EditContactWindow.this.contact.setPhone(EditContactWindow.this.phone_field.getText());
                EditContactWindow.this.contact.setAddress(EditContactWindow.this.address_field.getText());
                EditContactWindow.this.dispose();
            }
        });
        
        this.Cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditContactWindow.this.dispose();
            }
        });
        
        applyLayout();
    }

    private void applyLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        this.setLayout(layout);
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(this.name, 100, 100, 100)
                                .addGap(10)
                                .addComponent(this.name_field, 150, 150, 150)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(this.phone, 100, 100, 100)
                                .addGap(10)
                                .addComponent(this.phone_field, 150, 150, 150)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(this.address, 100, 100, 100)
                                .addGap(10)
                                .addComponent(this.address_field, 150, 150, 150)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(this.Save)
                                .addGap(10)
                                .addComponent(this.Cancel)
                        )
                )
                .addContainerGap()
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup()
                        .addComponent(this.name)
                        .addComponent(this.name_field)
                )
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addComponent(this.phone)
                        .addComponent(this.phone_field)
                )
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addComponent(this.address)
                        .addComponent(this.address_field)
                )
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addComponent(this.Save)
                        .addComponent(this.Cancel)
                )
                .addContainerGap()
        );
        this.setResizable(false);
        pack();
    }
    
    
    
}
