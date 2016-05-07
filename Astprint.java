

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class Astprint {
	int counter=0;
	static int regcounter=0;
	Newnode temp=new Newnode();
	Declaration tempdec=new Declaration();
	public static Hashtable<String,Integer> registertable = new Hashtable<String,Integer>();
    FileWriter fw;
    BufferedWriter bw;
	
    public  Astprint() throws IOException 
	{
		fw=new FileWriter("adrs.txt",true);
		 bw =new BufferedWriter(fw);
	}
	
	public void printdec(Hashtable<String,String> aldec) throws IOException
	{
		bw.write("B"+counter+":");
		bw.newLine();
		for(String key: aldec.keySet())
		{
			processdec(key,aldec.get(key));
		}
		bw.close();
		fw.close();
	}
	
	public void processdec(String temp1,String temp2) throws IOException
	{
		if(temp2.equals("INT"))
		{
			bw.write("loadI 0 => r_"+temp1);
			registertable.put("r_"+temp1,regcounter);
			++regcounter;
			bw.newLine();
		}
		else 
		{
			if(temp2.equals("TRUE"))
			{
				bw.write("loadI 1 => r_"+temp1);
				registertable.put("r_"+temp1,regcounter);
			    ++regcounter;
				bw.newLine();
			}
			else
			{
				bw.write("loadI 0 => r_"+temp1);
				registertable.put("r_"+temp1,regcounter);
			    ++regcounter;
				bw.newLine();
			}
		}
	}
	public void print(ArrayList<Newnode> al) throws IOException
	{
		Iterator<Newnode> it=al.iterator();
		while(it.hasNext())
		{
			temp=it.next();
			process(temp,1);
		}
		bw.write("exit");
		bw.newLine();
		bw.close();
		fw.close();
		Codegeneration cg=new Codegeneration();
		cg.codeprint(registertable,regcounter);
	}
	public void process(Newnode temp,int current) throws IOException
	{
		if(temp.value.equals(":="))
		{
			
			process(temp.right,2);
			//process(temp.left,1);
			//bw.write("mov r2 => r1");
			bw.write("i2i r2 => r_"+temp.left.value);
			bw.newLine();
		}
		if(temp.category.equals("TERM"))
		{
			if(temp.value.equals("TRUE"))
			{
				bw.write("loadI 1 => r"+current);
				bw.newLine();
			}
			else if(temp.value.equals("FALSE"))
			{
				bw.write("loadI 0 => r"+current);
				bw.newLine();
			}
			else
			{
				bw.write("loadI "+temp.value+" => r"+current);
				bw.newLine();
			}
			
		}
		if(temp.category.equals("IDENT"))
		{
			bw.write("i2i r_"+temp.value+" => r"+current);
			bw.newLine();
		}
		if(temp.category.equals("READINT"))
		{
			bw.write("readInt => r"+current);
			bw.newLine();
		}
		if(temp.category.equals("OP"))
		{
			process(temp.left,1);
			process(temp.right,2);
			if(temp.value.equals("+"))
			{
				bw.write("add r1 r2 => r"+current);
				bw.newLine();
			}
			else if(temp.value.equals("-"))
			{
				bw.write("sub r1 r2 => r"+current);
				bw.newLine();
			}
			else if(temp.value.equals("div"))
			{
				bw.write("div r1 r2 => r"+current);
				bw.newLine();
			}
			else if(temp.value.equals("*"))
			{
				bw.write("mult r1 r2 => r"+current);
				bw.newLine();
			}
			else if(temp.value.equals("mod"))
			{
				bw.write("mod r1 r2 => r"+current);
				bw.newLine();
			}
			else if(temp.value.equals("="))
			{
				bw.write("seq r1 r2 => r"+current);
				bw.newLine();
			}
			else if(temp.value.equals("<"))
			{
				bw.write("slt r1 r2 => r"+current);
				bw.newLine();
			}
			else if(temp.value.equals(">"))
			{
				bw.write("sgt r1 r2 => r"+current);
				bw.newLine();
			}
			else if(temp.value.equals(">="))
			{
				bw.write("sge r1 r2 => r"+current);
				bw.newLine();
			}
			else if(temp.value.equals("<="))
			{
				bw.write("sle r1 r2 => r"+current);
				bw.newLine();
			}
			else if(temp.value.equals("!="))
			{
				bw.write("sne r1 r2 => r"+current);
				bw.newLine();
			}
		}
		if(temp.value.equals("WRITEINT"))
		{
			
			if(temp.right.category.equals("IDENT"))
			{
				bw.write("writeint r_"+temp.right.value);
				bw.newLine();
			}
			else
			{
				process(temp.right,2);
				bw.write("writeint r2");
				bw.newLine();
			}
		}
		if(temp.value.equals("WHILE"))
		{
			++counter;
			int temp1=counter;
			if(temp1==1)
			{
				bw.write("jumpI -> B1");
				bw.newLine();
			}
			bw.write("B"+temp1+":");
			bw.newLine();
			process(temp.right,1);
			int temp2=temp1+1;
			int temp3=temp1+2;
			bw.write("cbr r1 -> B"+temp2+" B"+temp3);
			bw.newLine();
			bw.write("B"+temp2+":");
			bw.newLine();
			processstatementlist(temp.lists);
			bw.write("jumpI -> B"+temp1);
			bw.newLine();
			bw.write("B"+temp3+":");
			bw.newLine();
			counter=temp3;
		}
		if(temp.value.equals("IF"))
		{
			++counter;
			int temp1=counter;
			if(temp1==1)
			{
				bw.write("jumpI -> B1");
			}
			bw.write("B"+temp1+":");
			bw.newLine();
			process(temp.left,1);
			int temp2=temp1+1;
			int temp3=temp1+2;
			int temp4=temp1+3;
			bw.write("cbr r1 -> B"+temp2+" B"+temp3);
			bw.newLine();
			bw.write("B"+temp2+":");
			bw.newLine();
			processstatementlist(temp.lists);
			bw.write("jumpI -> B"+temp4);
			bw.newLine();
			bw.write("B"+temp3+":");
			bw.newLine();
			process(temp.right,2);
			bw.write("jumpI -> B"+temp4);
			bw.newLine();
			bw.write("B"+temp4+":");
			bw.newLine();
			counter=temp4;
		}
		if(temp.value.equals("ELSE"))
		{
			processstatementlist(temp.lists);
		}
	}
	public void processstatementlist(ArrayList<Newnode> list) throws IOException
	{
		Iterator<Newnode> it=list.iterator();
		while(it.hasNext())
		{
			temp=it.next();
			process(temp,1);
		}
	}
}
