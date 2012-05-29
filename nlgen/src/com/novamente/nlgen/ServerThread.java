package com.novamente.nlgen;

import java.io.*;
import java.net.Socket;

import com.novamente.nlgen.gen.NLGenerator;

public class ServerThread extends Thread {

	private Socket socket = null;
	private NLGenerator nlg;

    public ServerThread(Socket socket, NLGenerator nlg) {
    	super("ServerThread");
		this.socket = socket;
		this.nlg = nlg;
    }
    
    public void run() {
		
    	OutputStream outs = null;
		InputStream ins = null;
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			ins = socket.getInputStream();
			outs = socket.getOutputStream();
		} catch (IOException e) {
			System.err.println("[ServerThread::Error] Geting input and output stream from the socket failed");
		}
		
		try{
			in = new BufferedReader(new InputStreamReader(ins));
			out = new PrintWriter(outs, true);
			
			/*
			 * Error code:
			 * 1: relex well formed, but didn't match any sentence
			 * 2: relex not well formed (parser error)
			 */
			String relexInput = "";
			String line;
			while ((line = in.readLine()) != null) {
				if(line.equals("END")){//complete relex input. Must process it now
					//break;
					System.out.println("[ServerThread::Info] -- NLGen Server received text: "+relexInput);

					String[] sens = null;
					try{
						sens = nlg.generatorFromString(relexInput);
					}catch(StringIndexOutOfBoundsException e){
						System.err.println("[ServerThread::Error] - Relex "+relexInput+" not well formed. Exception: "+e.getMessage());
						out.println("ERROR[2] Relex output not well formed.");						
					}
					if (sens != null && sens.length > 0) {
						//assuming that the ranking puts the best candidate in the first position
						out.println(sens[0]);
						System.out.println("[ServerThread::Info -- NLGen found sentence: "+sens[0]);
						if(sens.length > 1){
							System.out.println("[ServerThread::Info -- NLGen found "+sens.length+" sentences, but only the first one was considered.");
						}
					} else {
						System.err.println("[ServerThread::Error] cannot match the similar sentence from the corpus.");
						out.println("ERROR[1] NLGen did not recognize the relex input as a sentence.");
					}
					relexInput = "";
				}else{
					System.out.println("[ServerThread::Info] - Received line: "+line);
					relexInput += line + "\n";
				}
			}
			in.close();
			out.close();
			socket.close();
    	}catch(IOException e){
    		e.printStackTrace();
    		System.err.println("[ServerThread::Error] processing socket input failel. Message: "+e.getMessage());    		
    	}
    }


	
}
