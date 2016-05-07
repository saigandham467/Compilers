

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalysis
{
	static String fname;
	public static void main(String[] args) throws IOException,FileNotFoundException
	{
		PrintWriter pw = new PrintWriter("adrs.txt");
		pw.write("");
		pw.flush();
		pw.close();
		ArrayList<String> AL=new ArrayList();
		ArrayList Al2;
	    String filename = args[0];
	    
		if(!filename.contains(".tl"))
		{
			throw new RuntimeException("input file does not end with .tl");
		}
		Al2=lexar(filename,AL);
		Al2.add("$");
		String file=filename.substring(0, filename.length()-3);	
		fname=file;
		Parser p=new Parser();
		p.Parser_method(Al2,file);
	}
	
	public static ArrayList lexar(String filename, ArrayList Al) throws IOException
	{
		StringBuffer sb = new StringBuffer(filename);
		String outputfilename=sb.substring(0,sb.length()-3);
		FileWriter tokData = new FileWriter(outputfilename+".tok",true);
		BufferedWriter tokDataB = new BufferedWriter(tokData);
		PrintWriter pw = new PrintWriter(outputfilename+".tok");
		pw.write("");
		pw.flush();
		pw.close();
		String temptok;
		Hashtable<String,String> symbol=new Hashtable<String, String>();
		symbol.put("(", "LP");
		symbol.put(")", "RP");
		symbol.put(":=", "ASGN");
		symbol.put(";", "SC");
		symbol.put("*", "MULTIPLICATIVE(*)");
		symbol.put("div", "MULTIPLICATIVE(div)");
		symbol.put("mod","MULTIPLICATIVE(mod)");
		symbol.put("+", "ADDITIVE(+)");
		symbol.put("-", "ADDITIVE(-)");
		symbol.put("=","COMPARE(=)");
		symbol.put("!=","COMPARE(!=)");
		symbol.put("<","COMPARE(<)");
		symbol.put("<=","COMPARE(<=)");
		symbol.put(">","COMPARE(>)");
		symbol.put(">=","COMPARE(>=)");
		Hashtable<String,String> bool=new Hashtable<String,String>();
		bool.put("false", "BOOLLIT(FALSE)");
		bool.put("true", "BOOLLIT(TRUE)");
		Hashtable<String,String> keywords=new Hashtable<String,String>();
		keywords.put("if", "IF");
		keywords.put("then", "THEN");
		keywords.put("else", "ELSE");
		keywords.put("begin", "BEGIN");
		keywords.put("end", "END");
		keywords.put("while", "WHILE");
		keywords.put("do", "DO");
		keywords.put("program", "PROGRAM");
		keywords.put("var", "VAR");
		keywords.put("as", "AS");
		keywords.put("int", "INT");
		keywords.put("bool", "BOOL");
		Hashtable<String,String> procedures=new Hashtable<String,String>();
		procedures.put("writeint", "WRITEINT");
		procedures.put("readint", "READINT");
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while((line=br.readLine())!=null)
			{
			StringTokenizer st=new StringTokenizer(line);
			int flag1=0;
			while(st.hasMoreTokens())
			{
				int flag =0;
				temptok=st.nextToken();
				if(temptok.equals("%"))
				{
					flag1=1;
				
				}
				if(flag1==1)
					continue;
				
			 if(symbol.containsKey(temptok))
			 {
				 tokDataB.write(symbol.get(temptok));
				 tokDataB.newLine();
				 Al.add(symbol.get(temptok));
				 flag =1;
			 }
			 else if(bool.containsKey(temptok))
			 {
				 tokDataB.write(bool.get(temptok));
				 tokDataB.newLine();
				 Al.add(bool.get(temptok));
				 flag =1;
			 }
			 else if(keywords.containsKey(temptok))
			 {
				 tokDataB.write(keywords.get(temptok));
				 tokDataB.newLine();
				 Al.add(keywords.get(temptok));
				 flag =1;
			 }
			 else if(procedures.containsKey(temptok))
			 {
				tokDataB.write(procedures.get(temptok));
				 tokDataB.newLine();
				 Al.add(procedures.get(temptok));
				 flag =1;
			 }
			 if(flag==0)
			 {
				 String number=temptok;
				 Pattern testPattern= Pattern.compile("[1-9][0-9]*|0");
				 Matcher teststring= testPattern.matcher(number);
			
				 if(teststring.matches())
				 {
				 try{
					 int x=Integer.parseInt(number);
				 }
				 catch(Exception e)
				 {
					 String temp="error :token "+number+" is not as per TL language specification";
					 tokDataB.write(temp);
					 tokDataB.newLine();
					 br.close();
					 tokDataB.close();
					 return Al;
				 }
				 String temp="NUM("+number+")";
				 tokDataB.write(temp);
				 tokDataB.newLine();
				 Al.add(temp);
				 flag=1;
			 }
			 Pattern testPattern2= Pattern.compile("[A-Z][A-Z0-9]*");
			 Matcher teststring2 = testPattern2.matcher(number);
			  if(teststring2.matches()&& flag==0)
			 {
				 String temp="IDENT("+number+")";
				 tokDataB.write(temp);
				 tokDataB.newLine();
				 Al.add(temp);
				 flag=1;
			 }
			 if(flag==0)
			 {
				String temp="error :token "+number+" is not as per TL language specification";
				tokDataB.write(temp);
				tokDataB.newLine();
				br.close();
				tokDataB.close();
				return Al;
			}
			 
		   }
		}
	}
			br.close();
			tokDataB.close();
			return Al;
  }

}
