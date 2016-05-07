




	import java.io.BufferedWriter;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.io.PrintWriter;
	import java.util.ArrayList;
	import java.util.Hashtable;
	import java.util.Iterator;

	//import Scanner.Parser.node;

	public class Asttree{
		//AstImplementation astimp=new AstImplementation();
		//Hashtable<String, String> st=new Hashtable<String,String>();
		int count =0;
		ArrayList<Newnode> al=new ArrayList<Newnode>();
		ArrayList<Statement> alst=new ArrayList<Statement>();
		//ArrayList<Declaration> aldec=new ArrayList<Declaration>();
		//ArrayList<Statement> tempstat=new ArrayList<Statement>();
		//ArrayList<Statement> tempelsstat=new ArrayList<Statement>();
		//ArrayList<Statement> tempwhile=new ArrayList<Statement>();
		//Hashtable<String,String> symboltable = new Hashtable<String,String>();
		//FileWriter astfile;
		//BufferedWriter //astb;
		/*
		AstImplementation asti=new AstImplementation();
		public void initializesymboltable(Hashtable<String,String> ht)
		{
			//System.out.println("In initialize");
			st=ht;
		}
		*/
		public void astImpl(node n) throws IOException
		{
			
			//astfile=new FileWriter(filename+".dot",true);
			//astb=new BufferedWriter(astfile);
			//PrintWriter pw=new PrintWriter(filename+".dot");
			//pw.write("");
			//pw.flush();
			//pw.close();
			//astb.write("graph mygraph{");
			//astb.newLine();
			try
			{
				//System.out.println("IN ASTTREE");
			//processDeclaration(n.child.get(1));
			////astb.write(n.child.get(3).name);
			processStatementSequence(n.child.get(3));
			//astb.write( count + "[label=\""+"PROGRAM"+"\"];");
			//astb.newLine()
			////astb.write(count+"<PROGRAM>");
			int tempcount=count; 
			//printDeclList(tempcount);
			printStatementList(tempcount);
			//print();
			//astb.write("}");
			//astb.close();
			Astprint astp=new Astprint();
			astp.print(al);
			/*
			for(String key:st.keySet())
			{
				System.out.println(key);
			}
			*/
			}
			catch(Exception e)
			{
				System.out.println(" Syntax error "+e);
			}
		}
		/*
		public void processDeclaration(node n) throws IOException
		{
			Declaration decl=new Declaration();
			decl.ident=n.child.get(1).name;
			
			////astb.write(n.child.get(3).child.get(0).name);
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
		*/
		/*
		public Type processType(node n)
		{
			//astb.write(n.child.get(0).name);
			Type type;
			type.type=n.child.get(0).name;
			return type;
		}
		*/
		public void processStatementSequence(node n)
		{
			
			
			////astb.write(n.child.get(0).name);
			
			alst.add(processStatement(n.child.get(0)));
			
			if(!n.child.get(2).child.get(0).name.equals("E"))
			{
				////astb.write("in process sseq ");
			processStatementSequence(n.child.get(2));
			}
		}
		public Statement processStatement(node n)
		{
			
			////astb.write("in process statement");
			Statement st=new Statement();
			
			if(n.child.get(0).name.equals("<ASSIGNMENT>"))
			{
				////astb.write("in process statement assignment");
				st.asgn=processAssignment(n.child.get(0));
				
			}
			else if(n.child.get(0).name.equals("<IFSTATEMENT>"))
			{
				////astb.write("in process statement if");
				////astb.write(n.child.get(0));
				st.ifst=processIfStatement(n.child.get(0));
			}
			else if(n.child.get(0).name.equals("<WHILESTATEMENT>"))
			{
				////astb.write("in process statement while");
				st.whilst=processWhileStatement(n.child.get(0));
			}
			else if(n.child.get(0).name.equals("<WRITEINT>"))
			{
				////astb.write("in process statement write");
				st.wi=processWriteInt(n.child.get(0));
			}
			return st;
		}
		public Assignment processAssignment(node n)
		{
			//Newnode newn=new Newnode();
			//al.add(newn);
			//newn.value=":=";
			//newn.category="OP";
			////astb.write("in process assignment method");
			Assignment asnt=new Assignment();
			String temp=n.child.get(0).name;
			String temp1;
			temp1=temp.substring(6,temp.length()-1);
			////astb.write(temp1);
			//Newnode nodel=new Newnode();
			//nodel.value=temp1;
			//nodel.type="IDENT";
			//newn.left=nodel;
			asnt.ident=temp1;
			asnt.asslf=processAssignmentLf(n.child.get(2));
			return asnt;
		}
		public AssignmentLf processAssignmentLf(node n)
		{
			////astb.write("in process assignment lf");
			AssignmentLf aslf=new AssignmentLf();
			aslf.exp=null;
			aslf.readint=null;
			if(n.child.get(0).name.equals("<EXPRESSION>"))
			{
				////astb.write("in process aslf exp");
				aslf.exp=processExpression(n.child.get(0));
			}
			else if(n.child.get(0).name.equals("READINT"))
			{
				////astb.write("in read int");
				aslf.readint=n.child.get(0).name;
				//Newnode nn=new Newnode();
				//nn.value=n.child.get(0).name;
				//nn.category="TERM";
				//newn.right=nn;
			}
			return aslf;
		}
		public Expression processExpression(node n)
		{
			
			////astb.write("in process expression");
			Expression exp= new Expression();
			exp.simpex=processSimpleExpression(n.child.get(0));
			////astb.write("-----------------------------------");
			////astb.write(n.child.get(1).child.get(0).name);
			
			if(!n.child.get(1).child.get(0).name.equals("E"))
			{
				////astb.write(n.child.get(1).child.get(0).name.equals("E"));
				////astb.write(1);
			////astb.write(n.child.get(1).child.get(0).name);
			exp.explf=processExpressionLf(n.child.get(1));
			}
			
			return exp;
		}
		public SimpleExpression processSimpleExpression(node n)
		{
			////astb.write("in process simple expression");
			SimpleExpression simpex = new SimpleExpression();
			////astb.write(n.child.get(0).name);
			simpex.term=processTerm(n.child.get(0));
			////astb.write("after term");
			////astb.write(n.child.get(1).child.get(0).name);
			if(!n.child.get(1).child.get(0).name.equals("E"))
			{
				////astb.write(n.child.get(1).child.get(0).name);
			simpex.simexplf=processSimpleExpressionLf(n.child.get(1));
			}
			return simpex;
		}
		public ExpressionLf processExpressionLf(node n)
		{
			
			////astb.write("in process expression lf");
			ExpressionLf explf=new ExpressionLf();
			explf.com=n.child.get(0).name;
			explf.exp=processExpression(n.child.get(1));
			return explf;
			
			
			
		}
		public Term processTerm(node n)
		{
			////astb.write("in process term");
			////astb.write("in term");
			Term ter=new Term();
			ter.fact=processFactor(n.child.get(0));
			
			if(!n.child.get(1).child.get(0).name.equals("E"))
			{
				////astb.write(n.child.get(1).child.get(0).name);
			ter.termlf=processTermLf(n.child.get(1));
			}
			return ter;
		}
		public SimpleExpressionLf processSimpleExpressionLf(node n)
		{
			////astb.write("in processSimpleExpressionLf");
			SimpleExpressionLf simpexplf=new SimpleExpressionLf();
			simpexplf.add=n.child.get(0).name;
			simpexplf.simpexp=processSimpleExpression(n.child.get(1));
			return simpexplf;
		}
		public Factor processFactor(node n)
		{
			
			////astb.write("in factor");
			Factor fact=new Factor();
			String temp=n.child.get(0).name;
			if(temp.startsWith("IDENT"))
			{
				////astb.write("in ident");
				fact.termType=AstImplementation.symboltable.get(temp.substring(6, temp.length()-1));
				//System.out.println(astimp.symboltable.get(temp.substring(6, temp.length()-1)));
				fact.termValue=temp.substring(6, temp.length()-1);
			}
			else if(temp.startsWith("NUM"))
			{
				////astb.write("in num");
				fact.termType="INT";
				fact.termValue=temp.substring(4,temp.length()-1);
			}
			else if(temp.startsWith("BOOL"))
			{
				////astb.write("in bool");
				fact.termType="BOOL";
				fact.termValue=temp.substring(8,temp.length()-1);
			}
			else if(temp.startsWith("LP"))
			{
				////astb.write("in lp");
				fact.exp=processExpression(n.child.get(1));
			}
			return fact;
		}
		public TermLf processTermLf(node n)
		{
			
			////astb.write("in termlf");
			TermLf terlf=new TermLf();
			terlf.mul=n.child.get(0).name;
			terlf.term=processTerm(n.child.get(1));
			return terlf;
			
		
		}
		public IfStatement processIfStatement(node n)
		{
			////astb.write("in process if ");
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
			

			////astb.write("in process if statement sequence");
			tempstat.add(processStatement(n.child.get(0)));
			if(!n.child.get(2).child.get(0).name.equals("E"))
				processIfStatementSequence(n.child.get(2),tempstat);
			return tempstat;
			
			
		}
		public ElseClause processElseClause(node n)
		{
			////astb.write("in process if elseclause");
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
			//ArrayList<Statement> tempelsstat=new ArrayList<Statement>();

			////astb.write("in process else statement sequence ");
			tempelsstat.add(processStatement(n.child.get(0)));
			if(!n.child.get(2).child.get(0).name.equals("E"))
				processElseStatementSequence(n.child.get(2),tempelsstat);
			return tempelsstat;
		}
		public WhileStatement processWhileStatement(node n)
		{
			////astb.write("in process while statement ");
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
			

			////astb.write("in process whilwstatementsequence ");
			tempwhile.add(processStatement(n.child.get(0)));
			if(!n.child.get(2).child.get(0).name.equals("E"))
				processWhileStatementSequence(n.child.get(2),tempwhile);
			return tempwhile;
		}
		public WriteInt processWriteInt(node n)
		{
			////astb.write("in process writeint ");
			WriteInt wint = new WriteInt();
			////astb.write(n.child.get(1).name);
			wint.exp=processExpression(n.child.get(1));
			return wint;
		}
		/*
		public void printDeclList(int val) throws IOException
		{
			int tempcount=val;
			count=count+1;
			//astb.write( count + "[label=\""+"DECLARATIONLIST"+"\"];");
			//astb.newLine();
			////astb.write(count+"DECLARATIONLIST");
			//astb.write(tempcount+" -- "+count);
			//astb.newLine();
			tempcount=count;
			Iterator<Declaration> itdec= aldec.iterator();
			while(itdec.hasNext())
			{
				Declaration temp=itdec.next();
				count=count+1;
				String name=temp.ident.substring(6,temp.ident.length()-1);
				//astb.write( count + "[label=\""+name+" : "+temp.identtype+"\"];");
				//astb.newLine();
				////astb.write(count+temp.ident +":"+temp.identtype);
				//astb.write(tempcount+" -- "+count);
				//astb.newLine();
				
			}
		}
		*/
		public void printStatementList(int val) throws IOException
		{
			int tempcount=val;
			++count;
			Newnode newn=new Newnode();
			newn.value="STATEMENTLIST";
			//astb.write( count + "[label=\""+"STATEMENTLIST"+"\"];");
			//astb.newLine();
			////astb.write(count+"STATEMENTLIST");
			//astb.write(tempcount+" -- "+count);
			//astb.newLine();
			tempcount=count;
			Iterator<Statement> it=alst.iterator();
			
			
			while(it.hasNext())
			{
				
				Statement temp=it.next();
				////astb.write(temp.asgn);
				if(temp.asgn!=null)
				{
					String type;
					count=count+1;
					Newnode nn=new Newnode();
					nn.value=":=";
					nn.category="ASGN";
					al.add(nn);
					//astb.write( count + "[label=\""+":="+"\"];");
					//astb.newLine();
					////astb.write(count+":=");
					//astb.write(tempcount+" -- "+count);
					//astb.newLine();
							//count + "[label=\""+treenode.name+"\"];"
					int tempcount1=count;
					count=count+1;
					Newnode nen=new Newnode();
					nen.value=temp.asgn.ident;
					nen.category="IDENT";
					nn.left=nen;
					//astb.write( count + "[label=\""+temp.asgn.ident+" : "+symboltable.get(temp.asgn.ident) +"\"];");
					//astb.newLine();
					////astb.write(count+temp.asgn.ident);
					//astb.write(tempcount1+ " -- "+count);
					//astb.newLine();
					////astb.write(temp.asgn.asslf.readint);
				
					nn.right=printAssignmentLf(temp.asgn.asslf,tempcount1);
					/*
					if(!type.equals(symboltable.get(temp.asgn.ident)))
					{
						//astb.write(tempcount1+"[fontcolor=Red, color=Red]");
						//astb.newLine();
					}
					*/
				}
				if(temp.wi!=null)
				{
					String type;
					count=count+1;
					//astb.write( count + "[label=\""+"WRITEINT"+"\"];");
					//astb.newLine();
					////astb.write(count+"writeint");
					//astb.write(tempcount+" -- "+count);
					//astb.newLine();
					Newnode nn=new Newnode();
					nn.value="WRITEINT";
					nn.category="WRITEINT";
					al.add(nn);
					int tempcount1=count;
					nn.right=printExpression(temp.wi.exp,tempcount1);
					/*if(!type.equals("INT"))
					{
						//astb.write(tempcount1+"[fontcolor=Red,color=Red]");
						//astb.newLine();
					}
					*/
				}
				if(temp.ifst!=null)
				{
				   
					al.add(printIfStatement(temp.ifst,tempcount));
				}
				if(temp.whilst!=null)
				{
					
					al.add(printWhileStatement(temp.whilst,tempcount));
				}
				
			}
		}
		public Newnode printAssignmentLf(AssignmentLf asnlf,int val) throws IOException
		{
			String type;
			////astb.write("in asgn lf");
			if(asnlf.exp!=null)
			{
			int tempcount=val;	
			return printExpression(asnlf.exp,tempcount);
			
			}
			else
			{
				int tempcount=val;
				count=count+1;
				Newnode nen=new Newnode();
				nen.value=asnlf.readint;
				nen.category="READINT";
				
				//astb.write( count + "[label=\""+asnlf.readint+" : "+"INT"+"\"];");
				//astb.newLine();
				////astb.write(count+asnlf.readint);
				//astb.write(tempcount+" -- "+count);
				//astb.newLine();
				/*if(type!="INT")
				{
					//astb.write(tempcount+"");
				}
				*/
				type="INT";
				return nen;
				
			}
			
		}
		public Newnode printExpression(Expression exp,int val) throws IOException
		{
			////astb.write(" in print expression");
			////astb.write(exp);
			if(exp.explf!=null)
			{
				String type1,type2;
				int tempcount=val;
				count=count+1;
				////astb.write(" in print expression.explf");
				String compare=exp.explf.com.substring(8,exp.explf.com.length()-1);
				Newnode nn=new Newnode();
				nn.value=compare;
				nn.category="OP";
				//astb.write( count + "[label=\""+compare+"\"];");
				//astb.newLine();
				////astb.write(count+exp.explf.com);
				//astb.write(tempcount+" -- "+count);
				//astb.newLine();
			    tempcount=count;
			    nn.left= printSimpleExpression(exp.simpex,tempcount);
			    nn.right=printExpression(exp.explf.exp,tempcount);
			    /*
			    if(!type1.equals(type2))
			    {
			    	String type="some";
			    	//astb.write(tempcount+"[fontcolor=Red, color=Red]");
			    	//astb.newLine();
			    	return type;
			    }
			    
			    else 
			    {
			    	return type1;
			    }
			    */
			    return nn;
			}
			else
			{
				//String type;
			int tempcount=val;
			
			return printSimpleExpression(exp.simpex,tempcount);
			//return type;
		
			}
		}	
		public Newnode printSimpleExpression(SimpleExpression simpex,int val) throws IOException
		{
			
			////astb.write(" in print simple expression");
			if(simpex.simexplf!=null)
			{
				String type1,type2;
				int tempcount=val;
				count+=1;
				////astb.write(" in print simpleexpression.simexplf");
				String add=simpex.simexplf.add.substring(9,simpex.simexplf.add.length()-1);
				Newnode nn=new Newnode();
				nn.value=add;
				nn.category="OP";
				
				//astb.write( count + "[label=\""+add+"\"];");
				//astb.newLine();
				////astb.write(count+simpex.simexplf.add);
				//astb.write(tempcount+" -- "+count);
				//astb.newLine();
				tempcount=count;
				nn.left=printTerm(simpex.term,tempcount);
				nn.right=printSimpleExpression(simpex.simexplf.simpexp,tempcount);
				/*
				if(!(type1.equals("INT")&&type2.equals("INT")))
				{
					String type="some";
					//astb.write(tempcount+"[fontcolor=Red,color=Red]");
					//astb.newLine();
					return type;
				}
				
				else
				{
					return type1;
				}
				*/
				return nn;
				
			}
			else
			{
			//String type;
			int tempcount=val;
			return printTerm(simpex.term,tempcount);
			//return type;
			}
		}
		public Newnode printTerm(Term ter,int val) throws IOException
		{
			////astb.write(" in print term");
			if(ter.termlf!=null)
			{
				String type1,type2;
				int tempcount=val;
				count+=1;
				////astb.write(" in print term.termlf");
				String mul=ter.termlf.mul.substring(15,ter.termlf.mul.length()-1);
				Newnode nn=new Newnode();
				nn.value=mul;
				nn.category="OP";
				//astb.write( count + "[label=\""+mul+"\"];");
				//astb.newLine();
				////astb.write(count+ter.termlf.mul);
				//astb.write(tempcount+" -- "+count);
				//astb.newLine();
				tempcount=count;
				nn.left=printFactor(ter.fact,tempcount);
				nn.right=printTerm(ter.termlf.term,tempcount);
				////astb.write(type1+" "+type2);
				/*
				if(!(type1.equals("INT")&&type2.equals("INT")))
				{
					String type="some";
					////astb.write("INSIDE");
					//astb.write(tempcount+"[fontcolor=Red,color=Red]");
					//astb.newLine();
					return type;
				}
				else
				{
					return type1;
				}
				*/
				return nn;
			}
			else
			{
				//String type;
			////astb.write(" out print term.termlf");
			int tempcount=val;
			 return printFactor(ter.fact,tempcount);
			//return type;
			}
		}
		public Newnode printFactor(Factor fact,int val) throws IOException
		{
			////astb.write(" in print factor");
			if(fact.exp==null)
			{
				int tempcount=val;
				
				////astb.write(" !in print fact.exp");
				count+=1;
				Newnode nn=new Newnode();
				nn.value=fact.termValue;
				//System.out.println(fact.termValue);
				//System.out.println(astimp.symboltable.get(fact.termValue));
				if(AstImplementation.symboltable.containsKey(fact.termValue))
				{
					//System.out.println("IN IDENT"+ fact.termValue);
					nn.category="IDENT";
				}
				else
				{
					//System.out.println("IN else"+fact.termValue);
					nn.category="TERM";
				}
				//astb.write( count + "[label=\""+fact.termValue+" : "+fact.termType+"\"];");
				//astb.newLine();
				////astb.write(count+fact.termType+fact.termValue);
				//astb.write(tempcount+" -- "+count);
				//astb.newLine();
				return nn;
			}
			else
			{
				//String type;
				////astb.write(" in print fact.exp");
				int tempcount=val;
				return printExpression(fact.exp,tempcount);
				//return type;
			}
		}
		public Newnode printIfStatement(IfStatement ifst,int val) throws IOException
		{
			String type;
			int tempcount=val;
			count=count+1;
			////astb.write(count+"statementlist");
			////astb.write(tempcount+"--"+count);
			//tempcount=count;
			//count=count+1;
			//astb.write( count + "[label=\""+"IF"+"\"];");
			//astb.newLine();
			////astb.write(count+"IF");
			//astb.write(tempcount+" -- "+count);
			//astb.newLine();
			Newnode nn=new Newnode();
			nn.value="IF";
			nn.category="IF";
			
			tempcount=count;
			nn.left=printExpression(ifst.exp,tempcount);
			/*
			if(type.equals("some"))
			{
				//astb.write(tempcount+"[fontcolor=Red, color=Red]");
				//astb.newLine();
			}
			*/
			nn.lists=printStatementList(ifst.stal,tempcount);
			if(ifst.elscls!=null)
			nn.right=printelseStatement(ifst.elscls,tempcount);
			return nn;
			
		}
		public ArrayList<Newnode> printStatementList(ArrayList<Statement> stal,int val) throws IOException
		{
			ArrayList<Newnode> alnn=new ArrayList<Newnode>();
			int tempcount=val;
			count+=1;
			//astb.write( count + "[label=\""+"STATEMENTLIST"+"\"];");
			//astb.newLine();
			////astb.write(count+"statementList");
			//astb.write(tempcount+" -- "+count);
			//astb.newLine();
			tempcount=count;
			Iterator<Statement> itif=stal.iterator();
			while(itif.hasNext())
			{
				
				Statement temp=itif.next();
				////astb.write(temp.asgn);
				if(temp.asgn!=null)
				{
					String type;
					count+=1;
					Newnode nn=new Newnode();
					nn.value=":=";
					nn.category="ASGN";
					alnn.add(nn);
					//astb.write( count + "[label=\""+":="+"\"];");
					//astb.newLine();
					////astb.write(count+":=");
					//astb.write(tempcount+" -- "+count);
					//astb.newLine();
					int tempcount1=count;
					count+=1;
					//String name=temp.asgn.ident.substring(6,temp.asgn.ident.length()-1);
					//astb.write( count + "[label=\""+temp.asgn.ident+" : "+ symboltable.get(temp.asgn.ident)+"\"];");
					//astb.newLine();
					Newnode nen=new Newnode();
					nen.value=temp.asgn.ident;
					nen.category="IDENT";
					nn.left=nen;
					////astb.write(count+temp.asgn.ident);
					//astb.write(tempcount1+" -- "+count);
					//astb.newLine();
					
					////astb.write(x);
					 
					nn.right=printAssignmentLf(temp.asgn.asslf,tempcount1);
					/*
					if(!type.equals((symboltable.get(temp.asgn.ident))))
					{
						//astb.write(tempcount1+"[fontcolor=Red,color=Red]");
						//astb.newLine();
					}
					*/
				}
				if(temp.wi!=null)
				{
					Newnode nn=new Newnode();
					nn.value="WRITEINT";
					nn.category="WRITEINT";
					alnn.add(nn);
					String type;
					count=count+1;
					//astb.write( count + "[label=\""+"WRITEINT"+"\"];");
					//astb.newLine();
					////astb.write(count+"writeint");
					//astb.write(tempcount+" -- "+count);
					//astb.newLine();
				
					int tempcount1=count;
					nn.right=printExpression(temp.wi.exp,tempcount1);
					/*
					if(!type.equals("INT"))
					{
						//astb.write(tempcount1+"[fontcolor=Red, color=Red]");
						//astb.newLine();
					}
					*/
				}
				if(temp.ifst!=null)
				{
					
					alnn.add(printIfStatement(temp.ifst,tempcount));
				}
				if(temp.whilst!=null)
				{
					
					alnn.add(printWhileStatement(temp.whilst,tempcount));
				}
				
				
			}
			return alnn;
			
		}
		public Newnode printelseStatement(ElseClause elscls,int val) throws IOException
		{
			int tempcount=val;
			count+=1;
			//astb.write( count + "[label=\""+"ELSE"+"\"];");
			//astb.newLine();
			////astb.write(count+"ELSE");
			//astb.write(tempcount+" -- "+count);
			//astb.newLine();
			Newnode nn= new Newnode();
			nn.value="ELSE";
			nn.category="ELSE";
			tempcount=count;
			nn.lists=printStatementList(elscls.elsstmt,tempcount);
			return nn;
		}
		public Newnode printWhileStatement(WhileStatement wlst,int val) throws IOException
		{
			String type;
			int tempcount=val;
			count+=1;
			//astb.write( count + "[label=\""+"WHILE"+"\"];");
			//astb.newLine();
			////astb.write(count+"WHILE");
			//astb.write(tempcount+" -- "+count);
			//astb.newLine();
			Newnode nn=new Newnode();
			nn.value="WHILE";
			nn.category="WHILE";
			
			tempcount=count;
			nn.right=printExpression(wlst.exp,tempcount);
			////astb.write(wlst.wlst.get(0).asgn.ident);
			/*
			if(type.equals("some"))
			{
				//astb.write(tempcount+"[fontcolor=Red,color=Red]");
				//astb.newLine();
			}
			*/
			nn.lists=printStatementList(wlst.wlst,tempcount);
			return nn;
		}
		/*
		public void print()
		{
			//ArrayList<Newnode> alnn=new ArrayList<Newnode>();
			//Asttree astr=new Asttree();
			//System.out.println(al.size());
			Newnode temp=new Newnode();
			Iterator<Newnode> it=al.iterator();
			ArrayList<Newnode> aln=new ArrayList<Newnode>();
		    temp=it.next();
		    temp=it.next();
		    temp=it.next();
		    temp=it.next();
		    Iterator<Newnode> itt=temp.lists.iterator();
		    while(itt.hasNext())
		    {
		    	temp=itt.next();
		    	System.out.println(temp.right.value);
		    }
			
		}
		*/

	


}
