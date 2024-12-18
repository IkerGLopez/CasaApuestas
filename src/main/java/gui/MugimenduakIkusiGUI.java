package gui;

import domain.Pertsona;
import domain.User;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import businessLogic.BLFacade;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MugimenduakIkusiGUI extends JFrame {
    private static Pertsona user;
    private DefaultListModel<String> model = new DefaultListModel<String>();
    private JList listMugimenduak;
    private JScrollPane paneMugimenduak;
    private static BLFacade businessLogic;

    public MugimenduakIkusiGUI() {
    	this.businessLogic = LoginPantailaGUI.getBusinessLogic();
        this.user = LoginPantailaGUI.getUser();
        this.setBounds(100, 100, 500, 424);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Zure mugimenduak");

        User user1 = (User) this.user;
        List<String> mugimenduak = user1.getMugimenduak();
        for (String m : mugimenduak) {
            model.addElement(m);
        }
        listMugimenduak = new JList(model);
        this.user = user1;

        paneMugimenduak = new JScrollPane(listMugimenduak);
        paneMugimenduak.setBounds(43, 72, 400, 149);
        paneMugimenduak.setViewportView(listMugimenduak);
        listMugimenduak.setLayoutOrientation(JList.VERTICAL);
        getContentPane().add(paneMugimenduak);


        JLabel lblZureMugimenduak = new JLabel("ZURE MUGIMENDUAK");
        lblZureMugimenduak.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblZureMugimenduak.setBounds(145, 23, 202, 33);
        getContentPane().add(lblZureMugimenduak);

        JButton btnAtzera = new JButton("ATZERA");
        btnAtzera.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainUserGUI a = new MainUserGUI();
                a.setVisible(true);
                setVisible(false);
            }
        });
        btnAtzera.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnAtzera.setBounds(196, 339, 90, 25);
        getContentPane().add(btnAtzera);
        
        JLabel lblZureDirua = new JLabel("ZURE DIRUA");
        lblZureDirua.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblZureDirua.setBounds(196, 234, 90, 33);
        getContentPane().add(lblZureDirua);
        
        JTextPane txtpnDiruKant = new JTextPane();
        
        StyledDocument doc = txtpnDiruKant.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        
        txtpnDiruKant.setBackground(UIManager.getColor("Button.background"));
        double zureDirua = user1.getDirua();
        double diruB = Math.round(zureDirua * 100.0) / 100.0;
        
        String dirua = String.valueOf(diruB);
        txtpnDiruKant.setFont(new Font("Tahoma", Font.BOLD, 14));
        txtpnDiruKant.setText(dirua + " \u20AC");
        txtpnDiruKant.setBounds(178, 264, 125, 38);
        getContentPane().add(txtpnDiruKant);
    }
}