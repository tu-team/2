package com.novamente.nlgen;

import java.io.IOException;
import java.net.ServerSocket;

import com.novamente.nlgen.gen.NLGenerator;
import com.novamente.nlgen.gen.NLGenerator.Options;
import com.novamente.nlgen.util.OptionParser;

public class Server {
	private int listen_port;

	public Server()
	{
            listen_port = 5555;
            String serverPort = System.getProperty( "SERVER_PORT" );
            if ( serverPort != null ) {
                try {
                    listen_port = Integer.parseInt( serverPort );
                } catch ( NumberFormatException ex ) { } // ignore
            } // if
	}

	public static void main(String[] args) throws IOException
	{

		boolean listening = true;
		OptionParser optParser = new OptionParser(Options.class);
		Options opts = (Options) optParser.parse(args, true);
		System.out.println("[Server::Info] Calling NLGen with " + optParser.getPassedInOptions());

		String enCoding = "GBK";
		if(opts.enCoding!=null){
			enCoding=opts.enCoding;
		}
		String relExFileName = opts.relExFileName;
		String linksFileName = opts.linksFileName;	
		NLGenerator nlg = new NLGenerator();		
		nlg.setNlgeInfo(relExFileName, linksFileName, enCoding);
		
		Server s = new Server();
		ServerSocket listen_sock = null;
		
		try
		{
			listen_sock = new ServerSocket(s.listen_port);
		}
		catch (IOException e)
		{
			System.err.println("[Server::Error] Listen failed on port " + s.listen_port);
			System.exit(-1);
		}
		System.out.println("[Server::Info] NLGen Socket Listening on port " + s.listen_port);
		
		while(listening){			
			new ServerThread(listen_sock.accept(), nlg).start();
			System.out.println("[Server::Info] - Socket Accept");
		}
				
		listen_sock.close();

		
		
/*		
		while(true)
		{
			Socket out_sock = null;
			OutputStream outs = null;
			InputStream ins = null;
			try {
				out_sock = listen_sock.accept();
				ins = out_sock.getInputStream();
				outs = out_sock.getOutputStream();
			} catch (IOException e) {
				System.err.println("Error: Accept failed");
				continue;
			}

			System.err.println("Info: Socket accept");
			BufferedReader in = new BufferedReader(new InputStreamReader(ins));
			PrintWriter out = new PrintWriter(outs, true);
			//fabricio: requires a END to indicate the end of the relex input
			String relexInput = "";
			try {	
				String line = in.readLine();				
				if (line == null)
					continue;

				//System.out.println(line);
				while(!line.equals("END")){
          System.err.println("Line: "+line);
					relexInput += line+"\n";
					line = in.readLine();
				}
				System.err.println("Info: recv input: \"" + relexInput + "\"");


				
				String[] sens = nlg.generatorFromString(relexInput, enCoding
						relExFileName, linksFileName);
				if (sens != null) {
					for (String str : sens) {
						out.println(str);
						System.out.println(str);
					}
				} else {
			    System.err.println("cannot match the similar sentence from the corpus...");
					out.println("ERROR[1] - NLGen did not recognize the relex input as a sentence");
				}
				
				//out.println("END of relex input");

			}
			catch (IOException e)
			{
				System.err.println("Error: Processing input failed");
				continue;
			} catch (StringIndexOutOfBoundsException e) {
				//e.printStackTrace();
				System.err.println("Error: Relex "+relexInput+" not well formed. Exception: "+e.getMessage());
				out.println("ERROR[2] - Relex output not well formed");
			}
      finally{
  				System.err.println("Info: Closed printer");
          out.close();
      }
*/
      /*
			try
			{
				out_sock.close();
				System.err.println("Info: Closed socket");
			}
			catch (IOException e)
			{
				System.err.println("Error: Socket close failed");
				continue;
			}
      */
//		}		

		
		
	}
}
