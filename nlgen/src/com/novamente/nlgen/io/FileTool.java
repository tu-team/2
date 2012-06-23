/**
 * 
 */
package com.novamente.nlgen.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import java.util.Iterator;



/**
 * @author lianlian
 * 
 */
public abstract class FileTool<L> {

	// read file line by line, then store in the Vector...
	public static Vector<String> readLine(String filename, String code) {
		Vector<String> lines = new Vector<String>();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename), code));
			String str;
			while ((str = in.readLine()) != null) {
				if (!"".equals(str))
				//	lines.add(str.toLowerCase().trim());
				 lines.add(str.trim());
			}
			in.close();
		} catch (IOException e) {
			e.getMessage();
		}
		return lines;
	}
	
	
	// read file line by line, then store in the Set, i.e. ignore the same line...
	public static Set<String> readLine1(String filename, String code) {
		Set<String> lines = new HashSet<String>();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename), code));
			String str;
			while ((str = in.readLine()) != null) {
				if (!"".equals(str))
					lines.add(str.trim());
				// lines.add(str.trim());
			}
			in.close();
		} catch (IOException e) {
			e.getMessage();
		}
		return lines;
	}
	
	// read file line by line, and ignore the empty line according to the parameter "readNull"
	public static Vector<String> readLine(String filename, String code, boolean readNull) {
		Vector<String> lines = new Vector<String>();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename), code));
			String str;
			while ((str = in.readLine()) != null) {
				if(readNull){
					lines.add(str);
				}else{
					if(!str.equals(""))
						lines.add(str);
				}
			}
			in.close();
		} catch (IOException e) {
			e.getMessage();
		}
		return lines;
	}

	// write the data (stored in the Vector) to the file line by line
	public static<L> void WriteByline(Vector<L> line, String code,
			String filename) {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filename), code));
			PrintWriter pw = new PrintWriter(out);
			for (int i = 0; i < line.size(); i++) {
				pw.println(line.elementAt(i));
			}
			pw.close();
			out.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// write the matrix to the file
	public static<L> void WriteMatrix(Vector<Vector<L>> line, String code,
			String filename) {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filename), code));
			PrintWriter pw = new PrintWriter(out);
			for (int i = 0; i < line.size(); i++) {
				for(int j=0;j<line.elementAt(i).size(); j++)
					pw.print(line.elementAt(i).elementAt(j)+" ");
				pw.println("");
			}
			pw.close();
			out.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// write the data (stored in the HashMap) to the file
	public static void WriteByline(HashMap<String, Integer> map,
			String filename, String code) {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filename), code));
			PrintWriter pw = new PrintWriter(out);
			for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
				String temp = it.next();
				pw.println(temp + "    " + map.get(temp));
			}
			pw.close();
			out.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static class StringInteger{
		String str;
		Integer i;
		
		public StringInteger(String str, Integer i){
			this.str=str;
			this.i=i;
		}
	}
	
	
	
	public static void WriteByline1(Vector<StringInteger> vec,
			String filename, String code) {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filename), code));
			PrintWriter pw = new PrintWriter(out);
			for(int i=0; i<vec.size(); i++){
				String temp =vec.elementAt(i).str+"    "+vec.elementAt(i);
				pw.println(temp);
			}
			pw.close();
			out.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
