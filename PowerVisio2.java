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
    JPanel ButtonPano,DataPano,chartPanel;
   
    static JTextArea DataTextArea;
    
    static javax.swing.JScrollPane jScrollPane1;
    private PrintStream standardOut;
    
    
     public static final int CHAT_ROW_LIMIT = 5;
    
    
    CommunicationCOM temp= new CommunicationCOM();
    
    private XYDataset createDataset() {
    XYSeriesCollection dataset = new XYSeriesCollection();
    XYSeries series1 = new XYSeries("Object 1");
 
    series1.add(1.0, 2.0);
    series1.add(2.0, 3.0);
    series1.add(3.0, 2.5);
    series1.add(3.5, 2.8);
    series1.add(4.2, 6.0);

    dataset.addSeries(series1); 
 
    return dataset;
}
    
    private XYDataset createDatasetFromTextArea() {
    XYSeriesCollection dataset = new XYSeriesCollection();
    XYSeries series1 = new XYSeries("Object 1");
    int i=0;
    int NombreLignes = 0;
    String Record = "";
    
    NombreLignes = DataTextArea.getLineCount();
    Record = DataTextArea.getText();
    
    System.out.println("NombresLignes : " + NombreLignes);
    
    /*for(i=0;i<NombreLignes;i++)
    {
    series1.add(i,2 );
    }*/
    
    String[] textLigne = DataTextArea.getText().split("\n");
    
    System.out.println("TextLignes : " +textLigne.length);
    
    
   /* for(i = 0; i < textLigne.length; i++){
		System.out.println(textLigne[i]);
			}*/
    dataset.addSeries(series1); 
 
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
       
        
        
        setTitle("PowerVisio");
       // this.setResizable(false);
        this.setBounds(200,200,1100,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ButtonPano = new JPanel();
        Start = new JButton("Start");
        Stop = new JButton("Stop");
        Stop.setEnabled(false);
        Exit = new JButton("Exit");
        ButtonPano.setLayout(new GridLayout(3,1));
        ButtonPano.setPreferredSize(new Dimension(100,500));
        ButtonPano.setBackground(Color.red);
        ButtonPano.add(Start);
        ButtonPano.add(Stop);
        ButtonPano.add(Exit);
        
        EcouteurPW ec;
        ec = new EcouteurPW();
        
        Start.addActionListener(ec);
        Stop.addActionListener(ec);
        Exit.addActionListener(ec);
        
        JPanel chartPanel = createChartPanel();
        
        DataPano = new JPanel();
        DataTextArea = new JTextArea();
        jScrollPane1 = new JScrollPane(DataTextArea);
        jScrollPane1.setPreferredSize(new Dimension(200,450));
        DataPano.add(jScrollPane1);
        
        PrintStream printStream = new PrintStream(new CustomOutputStream(DataTextArea));
        
        standardOut = System.out;
        
        System.setOut(printStream);
        System.setErr(printStream);
        
        chartPanel = createChartPanel();
        
      
        getContentPane().add(ButtonPano,BorderLayout.WEST);
        getContentPane().add(DataPano,BorderLayout.CENTER);
        getContentPane().add(chartPanel, BorderLayout.EAST);
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
				System.out.println("Communication Started");
                                temp.start();
                                Stop.setEnabled(true);
                                Start.setEnabled(false);
			}
			if (arg0.getSource()==Stop)
			{
				// ButtonPano.setBackground(Color.RED);
				//JTA.setText(JPF.getPassword());
				System.out.println("Communication Stopped");
                            try {
                                temp.stop();
                                createDatasetFromTextArea();
                            } catch (IOException ex) {
                                Logger.getLogger(PowerVisio2.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                Stop.setEnabled(false);
                                Start.setEnabled(true);
			}
                        if (arg0.getSource()==Exit)
			{
				// ButtonPano.setBackground(Color.RED);
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
              new PowerVisio2().setVisible(true);
              //new PowerVisio().setVisible(true);
            }
        });
    }   
}
     
     
 
    
  

