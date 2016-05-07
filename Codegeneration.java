

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class Codegeneration {
	FileInputStream fis;
	BufferedReader br;
	FileWriter fw;
	BufferedWriter bw;
	String filename;
	Cfg cfg;
	public Codegeneration() throws IOException
	{
	  br = new BufferedReader(new FileReader("adrs.txt"));
	  cfg=new Cfg();
	  filename=LexicalAnalysis.fname;
	  PrintWriter pw=new PrintWriter(filename+".s");
	  pw.write("");
	  pw.flush();
	  pw.close();
	  fw=new FileWriter(filename+".s");
	  bw=new BufferedWriter(fw);
	  
	}
	 
	
	public void codeprint(Hashtable<String,Integer> regtable,int regcount) throws IOException
	{
		regtable.put("r1",regcount);
		++regcount;
		regtable.put("r2", regcount);
		++regcount;
		regtable.put("r3", regcount);
		bw.write("	.data\nnewline:	.asciiz "+ "\"\\n\""+"\n	.text\n	.globl main\nmain:\n	li $fp, 0x7ffffffc"); 
		bw.newLine();
		String line;
		while((line=br.readLine())!=null)
		 {
			StringTokenizer st = new StringTokenizer(line);
			while(st.hasMoreTokens())
			{
				String temp=st.nextToken();
				if(temp.startsWith("B"))
				{
					bw.write(temp);
					bw.newLine();
				}
				if(temp.startsWith("loadI"))
				{
					
					bw.write("li $t0, "+st.nextToken());
					bw.newLine();
					String temp1=st.nextToken();
				    temp1=st.nextToken();
				    bw.write("sw $t0, "+(regtable.get(temp1))*-4+"($fp)");
				    bw.newLine();
				}
				if(temp.equals("readInt"))
				{ 
					
					String temp1=st.nextToken();
					temp1=st.nextToken();
					bw.write("li $v0, 5");
					bw.newLine();
					bw.write("syscall");
					bw.newLine();
					bw.write("add $t0, $v0, $zero");
					bw.newLine();
					bw.write("sw $t0, "+(regtable.get(temp1))*-4+"($fp)");
					bw.newLine();
				}
				if(temp.equals("i2i"))
				{
					String temp1=st.nextToken();
					bw.write("lw $t1, "+(regtable.get(temp1))*-4+"($fp)");
					bw.newLine();
					bw.write("add $t0, $t1, $zero");
					bw.newLine();
					temp1=st.nextToken();
					temp1=st.nextToken();
					bw.write("sw $t0, "+(regtable.get(temp1)*-4+"($fp)"));
					bw.newLine();
					
				}
				if(temp.equals("jumpI"))
				{
					String temp1=st.nextToken();
					temp1=st.nextToken();
					bw.write("j "+temp1);
					bw.newLine();
				}
				if(temp.equals("sge"))
				{
					String temp1=st.nextToken();
					bw.write("lw $t1, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					temp1=st.nextToken();
					bw.write("lw $t2, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					bw.write("sge $t0, $t1,$t2");
					bw.newLine();
					temp1=st.nextToken();
					temp1=st.nextToken();
					bw.write("sw $t0, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					
				}
				if(temp.equals("seq"))
				{
					
					String temp1=st.nextToken();
					bw.write("lw $t1, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					temp1=st.nextToken();
					bw.write("lw $t2, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					bw.write("seq $t0, $t1,$t2");
					bw.newLine();
					temp1=st.nextToken();
					temp1=st.nextToken();
					bw.write("sw $t0, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					
				}
				if(temp.equals("slt"))
				{
					String temp1=st.nextToken();
					bw.write("lw $t1, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					temp1=st.nextToken();
					bw.write("lw $t2, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					bw.write("slt $t0, $t1,$t2");
					bw.newLine();
					temp1=st.nextToken();
					temp1=st.nextToken();
					bw.write("sw $t0, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					
				}
				if(temp.equals("sgt"))
				{
					String temp1=st.nextToken();
					bw.write("lw $t1, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					temp1=st.nextToken();
					bw.write("lw $t2, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					bw.write("sgt $t0, $t1,$t2");
					bw.newLine();
					temp1=st.nextToken();
					temp1=st.nextToken();
					bw.write("sw $t0, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
				}
				if(temp.equals("sle"))
				{
					String temp1=st.nextToken();
					bw.write("lw $t1, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					temp1=st.nextToken();
					bw.write("lw $t2, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					bw.write("sle $t0, $t1,$t2");
					bw.newLine();
					temp1=st.nextToken();
					temp1=st.nextToken();
					bw.write("sw $t0, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
				}
				if(temp.equals("sne"))
				{
					String temp1=st.nextToken();
					bw.write("lw $t1, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					temp1=st.nextToken();
					bw.write("lw $t2, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					bw.write("sne $t0, $t1,$t2");
					bw.newLine();
					temp1=st.nextToken();
					temp1=st.nextToken();
					bw.write("sw $t0, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
				}
				if(temp.equals("add"))
				{
					String temp1=st.nextToken();
					bw.write("lw $t1, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					temp1=st.nextToken();
					bw.write("lw $t2, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					bw.write("add $t0, $t1, $t2");
					bw.newLine();
					temp1=st.nextToken();
					temp1=st.nextToken();
					bw.write("sw $t0, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
				}
				if(temp.equals("sub"))
				{
					
					String temp1=st.nextToken();
					bw.write("lw $t1, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					temp1=st.nextToken();
					bw.write("lw $t2, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					bw.write("sub $t0, $t1, $t2");
					bw.newLine();
					temp1=st.nextToken();
					temp1=st.nextToken();
					bw.write("sw $t0, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
				}
				if(temp.equals("div"))
				{
					String temp1=st.nextToken();
					bw.write("lw $t1, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					temp1=st.nextToken();
					bw.write("lw $t2, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					bw.write("div $t0, $t1, $t2");
					bw.newLine();
					temp1=st.nextToken();
					temp1=st.nextToken();
					bw.write("sw $t0, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
				}
				if(temp.equals("mult"))
				{
					String temp1=st.nextToken();
					bw.write("lw $t1, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					temp1=st.nextToken();
					bw.write("lw $t2, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					bw.write("mul $t0, $t1, $t2");
					bw.newLine();
					temp1=st.nextToken();
					temp1=st.nextToken();
					bw.write("sw $t0, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
				}
				if(temp.equals("mod"))
				{
					String temp1=st.nextToken();
					bw.write("lw $t1, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					temp1=st.nextToken();
					bw.write("lw $t2, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					bw.write("rem $t0, $t1, $t2");
					bw.newLine();
					temp1=st.nextToken();
					temp1=st.nextToken();
					bw.write("sw $t0, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
				}
				if(temp.equals("writeint"))
				{
					String temp1=st.nextToken();
					bw.write("li $v0, 1");
					bw.newLine();
					bw.write("lw $t1, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					bw.write("add $a0, $t1, $zero");
					bw.newLine();
					bw.write("syscall");
					bw.newLine();
					bw.write("li $v0, 4");
					bw.newLine();
					bw.write("la $a0, newline");
					bw.newLine();
					bw.write("syscall");
					bw.newLine();
				}
				if(temp.equals("cbr"))
				{
					String temp1=st.nextToken();
					bw.write("lw $t0, "+regtable.get(temp1)*-4+"($fp)");
					bw.newLine();
					temp1=st.nextToken();
					temp1=st.nextToken();
					bw.write("bne $t0, $zero, "+temp1);
					bw.newLine();
					temp1=st.nextToken();
					bw.write("L11:");
					bw.newLine();
					bw.write("j "+temp1);
					bw.newLine();
					
				}
				if(temp.equals("exit"))
				{
					bw.write("li $v0, 10");
					bw.newLine();
					bw.write("syscall");
					bw.newLine();
					
				}
			}
		}
		bw.close();
		fw.close();
		cfg.printcfg();
	}
}
