

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
public class Cfg {
	BufferedReader br;
	FileWriter fw;
	BufferedWriter bw;
	String filename;
	public Cfg() throws IOException 
	{
		try {
			br=new BufferedReader(new FileReader("adrs.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		filename=LexicalAnalysis.fname;
		PrintWriter pw=new PrintWriter(filename+".3A.cfg.dot");
		pw.write("");
		pw.flush();
		pw.close();
		fw = new FileWriter(filename+".3A.cfg.dot");
		bw=new BufferedWriter(fw);
	}
	public void printcfg() throws IOException 
	{
		String parentnode="null";
		String line;
		bw.write("digraph graphviz {");
		bw.newLine();
		bw.write("   node [shape = none];");
		bw.newLine();
		bw.write("   edge [tailport = s];");
		bw.newLine();
		bw.write("   entry");
		bw.newLine();
		bw.write("   subgraph cluster {");
		bw.newLine();
		bw.write("   color=\"/x11/white\"");
		bw.newLine();
		while((line=br.readLine())!=null)
		{
			StringTokenizer st=new StringTokenizer(line);
			while(st.hasMoreTokens())
			{
			String temp=st.nextToken();
			if(temp.startsWith("B"))
			{
				parentnode=temp.substring(0,2);
				bw.write("\n"+temp.substring(0,2)+" [label=<<table border=\"0\"><tr><td border=\"1\" colspan=\"3\">"+temp.substring(0,2)+"</td></tr>");
			}
			else if(temp.equals("=>"))
			{
				bw.write("<td align=\"left\">=&gt; ");
				String temp1=st.nextToken();
				bw.write(temp1+"</td></tr>");
			}
			else if(temp.startsWith("cbr"))
			{
				String temp1=st.nextToken();
				bw.write("<tr><td align=\"left\">cbr</td><td align=\"left\">"+temp1+"</td>" );
				temp1=st.nextToken();
				temp1=st.nextToken();
				bw.write("<td align=\"left\">-&gt; "+temp1);
				String temp2=st.nextToken();
				bw.write(","+temp1+"</td></tr></table>>,fillcolor=\"x11/white\",shape=box]");
				bw.write("\n"+parentnode+" -> "+temp1);
				bw.newLine();
				bw.write("\n"+parentnode+" -> "+temp2);
			}
			else if(temp.startsWith("jumpI"))
			{
				String temp1=st.nextToken();
				temp1=st.nextToken();
				bw.write("<tr><td align=\"left\">jumpI</td><td align=\"left\">-&gt; "+temp1+"</td></tr></table>>,fillcolor=\"/x11/white\",shape=box]");
				bw.write("\n"+parentnode+" -> "+temp1);
			}
			else if(temp.equals("loadI")||temp.equals("readInt")||temp.equals("i2i")||temp.equals("mult")||temp.equals("sle")||temp.equals("add")||temp.equals("div")||temp.equals("sub")||temp.equals("mod")||temp.equals("sge")||temp.equals("slt")||temp.equals("sgt")||temp.equals("sne")||temp.equals("seq"))
			{
				bw.write("<tr><td align=\"left\">"+temp+"</td>");
			}
			else if(temp.equals("exit"))
			{
				bw.write("</table>>,fillcolor=\"x11/white\",shape=box]");
				bw.newLine();
				bw.write("}\nentry -> B0\n"+parentnode+" -> exit\n}");
			}
			else if(temp.equals("writeint"))
			{
				String temp1=st.nextToken();
				bw.write("<tr><td align=\"left\">writeint</td><td align=\"left\">"+temp1+"</td></tr>");
			}
			else
			{
				bw.write("<td align=\"left\">"+temp+"</td>");
			}
			}
		}
		bw.close();
		fw.close();
	}

}
