package visucom;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CommunicationCOM implements Runnable{
	 static   Thread myThread=null;
	 static   BufferedReader br;
	 static   BufferedWriter wr;
	 static   InputStreamReader isr;
	 static   OutputStreamWriter osw;
	 static   java.io.RandomAccessFile port;
         static   boolean stop = false;
 
	    public  CommunicationCOM(){ /**Constructeur*/
			  
	    }
 
            /** Function Start du thread CommunicationCom */
            
		public void start(){
                    myThread=new Thread(this);
			try {
			   port=new java.io.RandomAccessFile("COM5","r"); /* lecture seulement */
			}
			catch (Exception e) {
			System.out.println("start "+e.toString());
			}
                stop = false;        
	        myThread.start(); 
		}
                
                public void stop() throws IOException{
			/*try {
			   port=new java.io.RandomAccessFile("COM5","r"); 
			}
			catch (Exception e) {
			System.out.println("start "+e.toString());
			} */
	        stop = true;
                port.close();

		}
 
                /** Function RUN du thread CommunicationCom 
                 Demarrée automatiquement dés que le thread.start est executé */
		public void run() {
			  System.out.println("lecture COM...");
                          String st = null;
			  while (!stop)
                          {
				try
                                {
					st=port.readLine();
				} 
                                catch (IOException e) 
                                    {System.out.println(e.getMessage());}
				if (!stop) 
                                {
                                    System.out.println(st);
                                }
			  }
                          myThread = null;
		 }
	}
