package gui;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Font;

import businessLogic.BLFacade;
import com.toedter.calendar.JCalendar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class PartidoaSortuGUI extends JFrame{

    private JTextField jokalariakField;
    private JTextField kuotaField;

    private static BLFacade appFacadeInterface;
    public static BLFacade getBusinessLogic(){
        return appFacadeInterface;
    }
    public static void setBussinessLogic (BLFacade afi){
        appFacadeInterface=afi;
    }

    public PartidoaSortuGUI() {
        BLFacade bLogic = LoginPantailaGUI.getBusinessLogic();
        final JPanel errorPanel = new JPanel();

        this.setTitle("PARTIDOA SORTU");
        this.setBounds(100, 100, 452, 385);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        this.setLocationRelativeTo(null);

        JLabel lblPartidoaSortu = new JLabel("PARTIDOA SORTU");
        lblPartidoaSortu.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblPartidoaSortu.setBounds(124, 10, 184, 44);
        this.getContentPane().add(lblPartidoaSortu);

        jokalariakField = new JTextField();
        jokalariakField.setBounds(172, 66, 234, 29);
        this.getContentPane().add(jokalariakField);
        jokalariakField.setColumns(10);

        JLabel lblJokalariak = new JLabel("PARTIDOA");
        lblJokalariak.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblJokalariak.setBounds(26, 73, 113, 22);
        this.getContentPane().add(lblJokalariak);

        JCalendar calendar = new JCalendar();
        calendar.setBounds(222, 167, 184, 153);
        this.getContentPane().add(calendar);

        JLabel lblData = new JLabel("DATA");
        lblData.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblData.setBounds(348, 121, 58, 22);
        this.getContentPane().add(lblData);

        JButton btnSortu = new JButton("SORTU");
        btnSortu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean error = false;
                String description = jokalariakField.getText();
                Date eventDate = calendar.getDate();
                if(description.contains("-")) { //Partidoak gidoia badu:
                    String[] partidoa = description.split("-");
                    long millis=System.currentTimeMillis();
                    java.util.Date currentDate = new java.util.Date(millis);
                    //Datak konparatu, sartutako partidoak gaurko data baino txikiagoa den data bat badu, errorea
                    if(currentDate.compareTo(eventDate) > 0) JOptionPane.showMessageDialog(errorPanel, "Berriz sartu data.", "Error", JOptionPane.ERROR_MESSAGE);
                    else {
                        if (!partidoa[0].trim().equalsIgnoreCase(partidoa[1].trim())) { //Partidoko taldeak desberdinak badira:
                            if(!bLogic.existsPartidoa(description)) { //Partidoa aurretik existitzen ez bada
                                bLogic.partidoaSortu(description, eventDate);
                                System.out.println(description + " : " + eventDate + " match stored.");
                            } else error = true; //Partidoa jadanik existitzen bada, errorea
                        } else error = true; //Taldeak berdinak badira, errorea
                    }
                } else error = true; //Gidoia ez badu errorea
                if (error) JOptionPane.showMessageDialog(errorPanel, "Berriz sartu partidua.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnSortu.setForeground(Color.BLACK);
        btnSortu.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnSortu.setBounds(26, 183, 129, 52);
        this.getContentPane().add(btnSortu);
        
        JButton btnAtzera = new JButton("ATZERA");
        btnAtzera.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
				MainLangileGUI a = new MainLangileGUI();
				a.setVisible(true);
				setVisible(false);
			}
        });
        btnAtzera.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnAtzera.setBounds(26, 291, 113, 29);
        getContentPane().add(btnAtzera);
    }

}
