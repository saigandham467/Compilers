

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

//import Scanner.Parser.node;

public class AstImplementation {
	int count =0;
	static int typecounter=0;
	ArrayList<Statement> alst=new ArrayList<Statement>();
	ArrayList<Declaration> aldec=new ArrayList<Declaration>();
	public static Hashtable<String,String> symboltable = new Hashtable<String,String>();
	FileWriter astfile;
	BufferedWriter astb;
	public void astImpl(node n,String filename) throws IOException
	{
		astfile=new FileWriter(filename+".ast.dot",true);
		astb=new BufferedWriter(astfile);
		PrintWriter pw=new PrintWriter(filename+".ast.dot");
		pw.write("");
		pw.flush();
		pw.close();
		astb.write("graph mygraph{");
		astb.newLine();
		try
		{
		processDeclaration(n.child.get(1));
		//astb.write(n.child.get(3).name);
		processStatementSequence(n.child.get(3));
		astb.write( count + "[label=\""+"PROGRAM"+"\"];");
		astb.newLine();
		int tempcount=count; 
		printDeclList(tempcount);
		printStatementList(tempcount);
		if(typecounter==1)
		{
			throw new RuntimeException("Type error in input file: Check type declaration in input file");
		}
		Astprint astp=new Astprint();
		astp.printdec(symboltable);
		astb.write("}");
		astb.close();
		}
		catch(Exception e)
		{
			System.out.println(" Syntax error "+e);
		}
	}
	public void processDeclaration(node n) throws IOException
	{
		Declaration decl=new Declaration();
		decl.ident=n.child.get(1).name;
		decl.identtype=n.child.get(3).child.get(0).name;
		String name=decl.ident.substring(6,decl.ident.length()-1);
		if(!symboltable.containsKey(name))
		{
		symboltable.put(name, decl.identtype);
		aldec.add(decl);
		}
		else
		{
			System.out.println(decl.ident+" is declared more than once, considering only first declaration");
		}
		if(!n.child.get(5).child.get(0).name.equals("E"))
			processDeclaration(n.child.get(5));
	}
	public void processStatementSequence(node n)
	{
		alst.add(processStatement(n.child.get(0)));
		if(!n.child.get(2).child.get(0).name.equals("E"))
		{
			processStatementSequence(n.child.get(2));
		}
	}
	public Statement processStatement(node n)
	{
		Statement st=new Statement();
		if(n.child.get(0).name.equals("<ASSIGNMENT>"))
		{
			st.asgn=processAssignment(n.child.get(0));
		}
		else if(n.child.get(0).name.equals("<IFSTATEMENT>"))
		{
			st.ifst=processIfStatement(n.child.get(0));
		}
		else if(n.child.get(0).name.equals("<WHILESTATEMENT>"))
		{
			
			st.whilst=processWhileStatement(n.child.get(0));
		}
		else if(n.child.get(0).name.equals("<WRITEINT>"))
		{
			st.wi=processWriteInt(n.child.get(0));
		}
		return st;
	}
	public Assignment processAssignment(node n)
	{
		Assignment asnt=new Assignment();
		String temp=n.child.get(0).name;
		String temp1;
		temp1=temp.substring(6,temp.length()-1);
		asnt.ident=temp1;
		asnt.asslf=processAssignmentLf(n.child.get(2));
		return asnt;
	}
	public AssignmentLf processAssignmentLf(node n)
	{
		AssignmentLf aslf=new AssignmentLf();
		aslf.exp=null;
		aslf.readint=null;
		if(n.child.get(0).name.equals("<EXPRESSION>"))
		{
			aslf.exp=processExpression(n.child.get(0));
		}
		else if(n.child.get(0).name.equals("READINT"))
		{
			aslf.readint=n.child.get(0).name;
		}
		return aslf;
	}
	public Expression processExpression(node n)
	{
		Expression exp= new Expression();
		exp.simpex=processSimpleExpression(n.child.get(0));
		if(!n.child.get(1).child.get(0).name.equals("E"))
		{
			exp.explf=processExpressionLf(n.child.get(1));
		}
		return exp;
	}
	public SimpleExpression processSimpleExpression(node n)
	{
		SimpleExpression simpex = new SimpleExpression();
		simpex.term=processTerm(n.child.get(0));
			if(!n.child.get(1).child.get(0).name.equals("E"))
		{
				simpex.simexplf=processSimpleExpressionLf(n.child.get(1));
		}
		return simpex;
	}
	public ExpressionLf processExpressionLf(node n)
	{
		ExpressionLf explf=new ExpressionLf();
		explf.com=n.child.get(0).name;
		explf.exp=processExpression(n.child.get(1));
		return explf;
	}
	public Term processTerm(node n)
	{
		Term ter=new Term();
		ter.fact=processFactor(n.child.get(0));
		if(!n.child.get(1).child.get(0).name.equals("E"))
		{
			ter.termlf=processTermLf(n.child.get(1));
		}
		return ter;
	}
	public SimpleExpressionLf processSimpleExpressionLf(node n)
	{
		SimpleExpressionLf simpexplf=new SimpleExpressionLf();
		simpexplf.add=n.child.get(0).name;
		simpexplf.simpexp=processSimpleExpression(n.child.get(1));
		return simpexplf;
	}
	public Factor processFactor(node n)
	{
		Factor fact=new Factor();
		String temp=n.child.get(0).name;
		if(temp.startsWith("IDENT"))
		{
			fact.termType=symboltable.get(temp.substring(6, temp.length()-1));
			fact.termValue=temp.substring(6, temp.length()-1);
		}
		else if(temp.startsWith("NUM"))
		{
			fact.termType="INT";
			fact.termValue=temp.substring(4,temp.length()-1);
		}
		else if(temp.startsWith("BOOL"))
		{
			fact.termType="BOOL";
			fact.termValue=temp.substring(8,temp.length()-1);
		}
		else if(temp.startsWith("LP"))
		{
			fact.exp=processExpression(n.child.get(1));
		}
		return fact;
	}
	public TermLf processTermLf(node n)
	{
		
		//astb.write("in termlf");
		TermLf terlf=new TermLf();
		terlf.mul=n.child.get(0).name;
		terlf.term=processTerm(n.child.get(1));
		return terlf;
		
	
	}
	public IfStatement processIfStatement(node n)
	{
		//astb.write("in process if ");
		IfStatement ifs=new IfStatement();
		ifs.exp=processExpression(n.child.get(1));
		if(!n.child.get(3).child.get(0).name.equals("E"))
		{
			ArrayList<Statement> tempstat=new ArrayList<Statement>();
		ifs.stal=processIfStatementSequence(n.child.get(3),tempstat);
		//tempstat.clear();
		}
		if(!n.child.get(4).child.get(0).name.equals("E"))
		{
		ifs.elscls=processElseClause(n.child.get(4));
		
		}
		return ifs;
	}
	public ArrayList<Statement> processIfStatementSequence(node n,ArrayList<Statement> tempstat)
	{
		tempstat.add(processStatement(n.child.get(0)));
		if(!n.child.get(2).child.get(0).name.equals("E"))
			processIfStatementSequence(n.child.get(2),tempstat);
		return tempstat;
	}
	public ElseClause processElseClause(node n)
	{
		//astb.write("in process if elseclause");
			ElseClause elscls=new ElseClause();
			if(!n.child.get(1).child.get(0).name.equals("E"))
			{
				ArrayList<Statement> tempelsstat=new ArrayList<Statement>();
			elscls.elsstmt=processElseStatementSequence(n.child.get(1),tempelsstat);
			//tempelsstat.clear();
			}
			return elscls;
			
	}
	public ArrayList<Statement> processElseStatementSequence(node n,ArrayList<Statement> tempelsstat)
	{
		tempelsstat.add(processStatement(n.child.get(0)));
		if(!n.child.get(2).child.get(0).name.equals("E"))
			processElseStatementSequence(n.child.get(2),tempelsstat);
		return tempelsstat;
	}
	public WhileStatement processWhileStatement(node n)
	{
		//astb.write("in process while statement ");
		WhileStatement wst=new WhileStatement();
		wst.exp=processExpression(n.child.get(1));
		if(!n.child.get(3).child.get(0).name.equals("E"))
		{
			ArrayList<Statement> tempwhile=new ArrayList<Statement>();
			wst.wlst=processWhileStatementSequence(n.child.get(3),tempwhile);
		//tempwhile.clear();
		}
		return wst;
	}
	public ArrayList<Statement> processWhileStatementSequence(node n, ArrayList<Statement> tempwhile)
	{
		

		//astb.write("in process whilwstatementsequence ");
		tempwhile.add(processStatement(n.child.get(0)));
		if(!n.child.get(2).child.get(0).name.equals("E"))
			processWhileStatementSequence(n.child.get(2),tempwhile);
		return tempwhile;
	}
	public WriteInt processWriteInt(node n)
	{
		//astb.write("in process writeint ");
		WriteInt wint = new WriteInt();
		//astb.write(n.child.get(1).name);
		wint.exp=processExpression(n.child.get(1));
		return wint;
	}
	public void printDeclList(int val) throws IOException
	{
		int tempcount=val;
		count=count+1;
		astb.write( count + "[label=\""+"DECLARATIONLIST"+"\"];");
		astb.newLine();
		//astb.write(count+"DECLARATIONLIST");
		astb.write(tempcount+" -- "+count);
		astb.newLine();
		tempcount=count;
		Iterator<Declaration> itdec= aldec.iterator();
		while(itdec.hasNext())
		{
			Declaration temp=itdec.next();
			count=count+1;
			String name=temp.ident.substring(6,temp.ident.length()-1);
			astb.write( count + "[label=\""+name+" : "+temp.identtype+"\"];");
			astb.newLine();
			//astb.write(count+temp.ident +":"+temp.identtype);
			astb.write(tempcount+" -- "+count);
			astb.newLine();
			
		}
	}
	public void printStatementList(int val) throws IOException
	{
		int tempcount=val;
		++count;
		astb.write( count + "[label=\""+"STATEMENTLIST"+"\"];");
		astb.newLine();
		//astb.write(count+"STATEMENTLIST");
		astb.write(tempcount+" -- "+count);
		astb.newLine();
		tempcount=count;
		Iterator<Statement> it=alst.iterator();
		while(it.hasNext())
		{
			
			Statement temp=it.next();
			//astb.write(temp.asgn);
			if(temp.asgn!=null)
			{
				String type;
				count=count+1;
				astb.write( count + "[label=\""+":="+"\"];");
				astb.newLine();
				//astb.write(count+":=");
				astb.write(tempcount+" -- "+count);
				astb.newLine();
				int tempcount1=count;
				count=count+1;
				astb.write( count + "[label=\""+temp.asgn.ident+" : "+symboltable.get(temp.asgn.ident) +"\"];");
				astb.newLine();
				astb.write(tempcount1+ " -- "+count);
				astb.newLine();
				type=printAssignmentLf(temp.asgn.asslf,tempcount1);
				if(!type.equals(symboltable.get(temp.asgn.ident)))
				{
					astb.write(tempcount1+"[fontcolor=Red, color=Red]");
					astb.newLine();
					typecounter=1;
				}
			}
			if(temp.wi!=null)
			{
				String type;
				count=count+1;
				astb.write( count + "[label=\""+"WRITEINT"+"\"];");
				astb.newLine();
				//astb.write(count+"writeint");
				astb.write(tempcount+" -- "+count);
				astb.newLine();
				int tempcount1=count;
				type=printExpression(temp.wi.exp,tempcount1);
				if(!type.equals("INT"))
				{
					astb.write(tempcount1+"[fontcolor=Red,color=Red]");
					astb.newLine();
					typecounter=1;
				}
			}
			if(temp.ifst!=null)
			{
			   
				printIfStatement(temp.ifst,tempcount);
			}
			if(temp.whilst!=null)
			{
				
				printWhileStatement(temp.whilst,tempcount);
			}
			
		}
	}
	public String printAssignmentLf(AssignmentLf asnlf,int val) throws IOException
	{
		String type;
		//astb.write("in asgn lf");
		if(asnlf.exp!=null)
		{
		int tempcount=val;	
		type=printExpression(asnlf.exp,tempcount);
		
		}
		else
		{
			int tempcount=val;
			count=count+1;
			astb.write( count + "[label=\""+asnlf.readint+" : "+"INT"+"\"];");
			astb.newLine();
			//astb.write(count+asnlf.readint);
			astb.write(tempcount+" -- "+count);
			astb.newLine();
			/*if(type!="INT")
			{
				astb.write(tempcount+"");
			}
			*/
			type="INT";
			
		}
		return type;
	}
	public String printExpression(Expression exp,int val) throws IOException
	{
		//astb.write(" in print expression");
		//astb.write(exp);
		if(exp.explf!=null)
		{
			String type1,type2;
			int tempcount=val;
			count=count+1;
			//astb.write(" in print expression.explf");
			String compare=exp.explf.com.substring(8,exp.explf.com.length()-1);
			astb.write( count + "[label=\""+compare+"\"];");
			astb.newLine();
			//astb.write(count+exp.explf.com);
			astb.write(tempcount+" -- "+count);
			astb.newLine();
		    tempcount=count;
		    type1= printSimpleExpression(exp.simpex,tempcount);
		    type2=printExpression(exp.explf.exp,tempcount);
		    if(!type1.equals(type2))
		    {
		    	String type="some";
		    	astb.write(tempcount+"[fontcolor=Red, color=Red]");
		    	astb.newLine();
		    	typecounter=1;
		    	return type;
		    }
		    else 
		    {
		    	return type1;
		    }
		}
		else
		{
			String type;
		int tempcount=val;
		
		type =printSimpleExpression(exp.simpex,tempcount);
		return type;
	
		}
	}	
	public String printSimpleExpression(SimpleExpression simpex,int val) throws IOException
	{
		
		//astb.write(" in print simple expression");
		if(simpex.simexplf!=null)
		{
			String type1,type2;
			int tempcount=val;
			count+=1;
			//astb.write(" in print simpleexpression.simexplf");
			String add=simpex.simexplf.add.substring(9,simpex.simexplf.add.length()-1);
			astb.write( count + "[label=\""+add+"\"];");
			astb.newLine();
			//astb.write(count+simpex.simexplf.add);
			astb.write(tempcount+" -- "+count);
			astb.newLine();
			tempcount=count;
			type1=printTerm(simpex.term,tempcount);
			type2=printSimpleExpression(simpex.simexplf.simpexp,tempcount);
			if(!(type1.equals("INT")&&type2.equals("INT")))
			{
				String type="some";
				astb.write(tempcount+"[fontcolor=Red,color=Red]");
				astb.newLine();
				typecounter=1;
				return type;
			}
			else
			{
				return type1;
			}
			
		}
		else
		{
		String type;
		int tempcount=val;
		type =printTerm(simpex.term,tempcount);
		return type;
		}
	}
	public String printTerm(Term ter,int val) throws IOException
	{
		//astb.write(" in print term");
		if(ter.termlf!=null)
		{
			String type1,type2;
			int tempcount=val;
			count+=1;
			//astb.write(" in print term.termlf");
			String mul=ter.termlf.mul.substring(15,ter.termlf.mul.length()-1);
			astb.write( count + "[label=\""+mul+"\"];");
			astb.newLine();
			//astb.write(count+ter.termlf.mul);
			astb.write(tempcount+" -- "+count);
			astb.newLine();
			tempcount=count;
			type1=printFactor(ter.fact,tempcount);
			type2=printTerm(ter.termlf.term,tempcount);
			//astb.write(type1+" "+type2);
			if(!(type1.equals("INT")&&type2.equals("INT")))
			{
				String type="some";
				//astb.write("INSIDE");
				astb.write(tempcount+"[fontcolor=Red,color=Red]");
				astb.newLine();
				typecounter=1;
				return type;
			}
			else
			{
				return type1;
			}
		}
		else
		{
			String type;
		//astb.write(" out print term.termlf");
		int tempcount=val;
		type=printFactor(ter.fact,tempcount);
		return type;
		}
	}
	public String printFactor(Factor fact,int val) throws IOException
	{
		if(fact.exp==null)
		{
			int tempcount=val;
			count+=1;
			astb.write( count + "[label=\""+fact.termValue+" : "+fact.termType+"\"];");
			astb.newLine();
			astb.write(tempcount+" -- "+count);
			astb.newLine();
			return fact.termType;
		}
		else
		{
			String type;
			//astb.write(" in print fact.exp");
			int tempcount=val;
			type=printExpression(fact.exp,tempcount);
			return type;
		}
	}
	public void printIfStatement(IfStatement ifst,int val) throws IOException
	{
		String type;
		int tempcount=val;
		count=count+1;
		
		astb.write( count + "[label=\""+"IF"+"\"];");
		astb.newLine();
		//astb.write(count+"IF");
		astb.write(tempcount+" -- "+count);
		astb.newLine();
		tempcount=count;
		type=printExpression(ifst.exp,tempcount);
		
		printStatementList(ifst.stal,tempcount);
		if(ifst.elscls!=null)
		printelseStatement(ifst.elscls,tempcount);
		
	}
	public void printStatementList(ArrayList<Statement> stal,int val) throws IOException
	{
		int tempcount=val;
		count+=1;
		astb.write( count + "[label=\""+"STATEMENTLIST"+"\"];");
		astb.newLine();
		//astb.write(count+"statementList");
		astb.write(tempcount+" -- "+count);
		astb.newLine();
		tempcount=count;
		Iterator<Statement> itif=stal.iterator();
		while(itif.hasNext())
		{
			
			Statement temp=itif.next();
			if(temp.asgn!=null)
			{
				String type;
				count+=1;
				astb.write( count + "[label=\""+":="+"\"];");
				astb.newLine();
				astb.write(tempcount+" -- "+count);
				astb.newLine();
				int tempcount1=count;
				count+=1;
				astb.write( count + "[label=\""+temp.asgn.ident+" : "+ symboltable.get(temp.asgn.ident)+"\"];");
				astb.newLine();
				astb.write(tempcount1+" -- "+count);
				astb.newLine();
				type=printAssignmentLf(temp.asgn.asslf,tempcount1);
				if(!type.equals((symboltable.get(temp.asgn.ident))))
				{
					astb.write(tempcount1+"[fontcolor=Red,color=Red]");
					astb.newLine();
					typecounter=1;
				}
			}
			if(temp.wi!=null)
			{
				String type;
				count=count+1;
				astb.write( count + "[label=\""+"WRITEINT"+"\"];");
				astb.newLine();
				astb.write(tempcount+" -- "+count);
				astb.newLine();
				int tempcount1=count;
				type=printExpression(temp.wi.exp,tempcount1);
				if(!type.equals("INT"))
				{
					astb.write(tempcount1+"[fontcolor=Red, color=Red]");
					astb.newLine();
					typecounter=1;
				}
			}
			if(temp.ifst!=null)
			{
				printIfStatement(temp.ifst,tempcount);
			}
			if(temp.whilst!=null)
			{
				printWhileStatement(temp.whilst,tempcount);
			}
		}
	}
	public void printelseStatement(ElseClause elscls,int val) throws IOException
	{
		int tempcount=val;
		count+=1;
		astb.write( count + "[label=\""+"ELSE"+"\"];");
		astb.newLine();
		astb.write(tempcount+" -- "+count);
		astb.newLine();
		tempcount=count;
		printStatementList(elscls.elsstmt,tempcount);
		
	}
	public void printWhileStatement(WhileStatement wlst,int val) throws IOException
	{
		String type;
		int tempcount=val;
		count+=1;
		astb.write( count + "[label=\""+"WHILE"+"\"];");
		astb.newLine();
		astb.write(tempcount+" -- "+count);
		astb.newLine();
		tempcount=count;
		type=printExpression(wlst.exp,tempcount);
		printStatementList(wlst.wlst,tempcount);
	}

}
