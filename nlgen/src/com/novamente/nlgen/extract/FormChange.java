/**
 * 
 */
package com.novamente.nlgen.extract;

import java.util.*;
import java.io.*;

/**
 * @author lianlian
 * 
 */
public class FormChange {

	public static Set<String> Need2Change = new HashSet<String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add("pos");
			add("noun_number");
			add("tense");
			add("gender");
		}
	};

	public static Vector<String> readFile(String fileName, String fileCode) {
		Vector<String> lines = new Vector<String>();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName), fileCode));
			String str;
			while ((str = in.readLine()) != null) {
				lines.add(str.trim());
			}
			in.close();
		} catch (IOException e) {
			e.getMessage();
		}
		return lines;
	}

	public static Vector<String> changeForm(Vector<String> original) {
		Vector<String> newForm = new Vector<String>();
		for (String temp : original) {
			if (!temp.equals("")) {
				if (Need2Change.contains(temp.substring(0, temp.indexOf('(')))) {
					temp = temp.replace('(', ' ');
					temp = temp.replace(')', ' ');
					temp = temp.replace(",", "");
					String[] tempArray = temp.split(" ");
					if (tempArray.length == 4) {
						/*
						 * System.err.println("BAD RELATION");
						 * System.err.println(temp);
						 */
						temp = tempArray[3] + "(" + tempArray[0] + tempArray[2]
								+ ")";
					} else {
						temp = tempArray[2] + "(" + tempArray[1] + ")";
					}
				} else if (temp.contains("-FLAG")) {
					temp = temp.replace("-FLAG", "");
					temp = temp.substring(0, temp.indexOf("(")).toLowerCase()
							+ temp.substring(temp.indexOf("("), temp
									.indexOf(",")) + ")";
				}
				newForm.add(temp);
			} else {
				newForm.add(temp);
			}
		}
		return newForm;
	}

	public static void WriteByline(Vector<String> line, String code,
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

	/*	*//**
	 * @param args
	 */
	public static void main(String[] args) {
		Vector<String> originalForm = FormChange.readFile(
				"testData//trivial-corpus-relation.txt", "GBK");
		Vector<String> newForm = FormChange.changeForm(originalForm);
		FormChange.WriteByline(newForm, "GBK",
				"testData//trivial-corpus-relation-1.txt");
		System.out.println("finish");
	}

}
