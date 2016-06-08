/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visucom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author frq08515
 */
public class PowerVisio2 extends  JFrame{
    
    JButton Start,Stop,Exit;
    JPanel pano,pano2,chartPanel;
   
    private JTextArea DataTextArea;
    
    private javax.swing.JScrollPane jScrollPane1;
    private PrintStream standardOut;
    
    
     public static final int CHAT_ROW_LIMIT = 5;
    
    
    CommunicationCOM temp= new CommunicationCOM();
    
    private XYDataset createDataset() {
    XYSeriesCollection dataset = new XYSeriesCollection();
    XYSeries series1 = new XYSeries("Object 1");
    XYSeries series2 = new XYSeries("Object 2");
    XYSeries series3 = new XYSeries("Object 3");
 
    series1.add(1.0, 2.0);
    series1.add(2.0, 3.0);
    series1.add(3.0, 2.5);
    series1.add(3.5, 2.8);
    series1.add(4.2, 6.0);
 
    series2.add(2.0, 1.0);
    series2.add(2.5, 2.4);
    series2.add(3.2, 1.2);
    series2.add(3.9, 2.8);
    series2.add(4.6, 3.0);
 
    series3.add(1.2, 4.0);
    series3.add(2.5, 4.4);
    series3.add(3.8, 4.2);
    series3.add(4.3, 3.8);
    series3.add(4.5, 4.0);
 
    dataset.addSeries(series1);
    dataset.addSeries(series2);
    dataset.addSeries(series3);
 
    return dataset;
}
      
    private JPanel createChartPanel() {
    String chartTitle = "Objects Movement Chart";
    String xAxisLabel = "X";
    String yAxisLabel = "Y";
    
   // plot.setBackgroundPaint(Color.DARK_GRAY);
 
    XYDataset dataset = createDataset();
 
    JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,
            xAxisLabel, yAxisLabel, dataset);
 
    return new ChartPanel(chart);
}
    public PowerVisio2()
    {
       
        DataTextArea = new JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane(DataTextArea);
       
        PrintStream printStream = new PrintStream(new CustomOutputStream(DataTextArea));
        
        standardOut = System.out;
        
        System.setOut(printStream);
        System.setErr(printStream);
        
        setTitle("PowerVisio");
       // this.setResizable(false);
        this.setBounds(200,200,1100,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        pano = new JPanel();
        pano2 = new JPanel();
        Start = new JButton("Start");
        
        Stop = new JButton("Stop");
        Stop.setEnabled(false);
        
        Exit = new JButton("Exit");
        
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(200, 200));
        topPanel.setBackground(Color.WHITE);

        /*final */JTextArea chatArea = new JTextArea();
        /*final */JScrollPane scrollPane = new JScrollPane(chatArea);

        final JPanel mainPanel = new JPanel(new BorderLayout(5,5));
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
        
        DataTextArea.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLineCount();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLineCount();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLineCount();
            }

            private void updateLineCount() {
                int lineCount = chatArea.getLineCount();
                if (lineCount <= CHAT_ROW_LIMIT) {
                    chatArea.setRows(lineCount);
                    mainPanel.revalidate();
                }
            }
        });
        
        JPanel chartPanel = createChartPanel();
        
        DataTextArea.setBackground(Color.WHITE);
        DataTextArea.setColumns(10);
        DataTextArea.setRows(10);
        jScrollPane1.setViewportView(DataTextArea);
        
        pano.setLayout(new GridLayout(3,1));
        pano.setPreferredSize(new Dimension(100,500));
        pano.setBackground(Color.red);
        pano.add(Start);
        pano.add(Stop);
        pano.add(Exit);
        
        pano2.setPreferredSize(new Dimension(1000,500));
        pano2.setBackground(Color.blue);
       
        chartPanel = createChartPanel();
        
        pano2.setLayout(new GridLayout(1,3));
        
    
        pano2.add(DataTextArea);
        pano2.add(jScrollPane1);
       
        pano2.add(jScrollPane1);
        pano2.add(DataTextArea);
        
        
        pano2.add(chartPanel);
        
        
        EcouteurPW ec;
        ec = new EcouteurPW();
        
        Start.addActionListener(ec);
        Stop.addActionListener(ec);
        Exit.addActionListener(ec);
        
        getContentPane().add(pano,BorderLayout.WEST);
        getContentPane().add(pano2,BorderLayout.EAST);
        //getContentPane().add(mainPanel);
    }
    
     /*public static void main(String args[]) { 
         PowerVisio2 MainWindow;
         
         System.out.println("Program PowerVisio Started");
         
         MainWindow = new PowerVisio2();
         MainWindow.setVisible(true);
         
     }*/
     
     public class EcouteurPW implements ActionListener
	{
                @Override
		public void actionPerformed(ActionEvent arg0)
		{	
			
			if (arg0.getSource()==Start)
			{
				//pano.setBackground(Color.RED);
				System.out.println("Communication Started");
                                temp.start();
                                Stop.setEnabled(true);
                                Start.setEnabled(false);
			}
			if (arg0.getSource()==Stop)
			{
				// pano.setBackground(Color.RED);
				//JTA.setText(JPF.getPassword());
				System.out.println("Communication Stopped");
                            try {
                                temp.stop();
                            } catch (IOException ex) {
                                Logger.getLogger(PowerVisio2.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                Stop.setEnabled(false);
                                Start.setEnabled(true);
			}
                        if (arg0.getSource()==Exit)
			{
				// pano.setBackground(Color.RED);
				//JTA.setText(JPF.getPassword());
				System.out.println("Program Exit");
                                System.exit(0);
			}
			
		
		}
		
		
		
	}  
     
   public static void main(String args[]) { 
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
      /*  try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PowerVisio2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PowerVisio2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PowerVisio2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PowerVisio2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //private PrintStream standardOut;
        
      
        /* Create and display the form */
        
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               //new PowerVisio2().setVisible(true);
              new PowerVisio().setVisible(true);
            }
        });
    }   
}
     
     
 
    
  

