

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class Parser {
	int ifcount=0;
	int count=0;
	ArrayList lexar;
	String token;
	Iterator<String> it;
	public void Parser_method(ArrayList arg, String filename)throws IOException
	{
		lexar=arg;
		it=lexar.iterator();
		node program_method;
		node nodenode;
		nodenode=Program_method();
		FileWriter tokData = new FileWriter("dot"+".dot",true);
		BufferedWriter tokDataB = new BufferedWriter(tokData);
		AstImplementation astimp=new AstImplementation(); 
		astimp.astImpl(nodenode,filename);
		/*
		if(AstImplementation.typecounter==1)
		{
			throw new RuntimeException("Type error in input file");
		}
		*/
		Asttree astr=new Asttree();
		astr.astImpl(nodenode);
	}
	public node Program_method()
	{
		node treenode=new node("<PROGRAM>");
		ArrayList<node> tempchild=new ArrayList<node>();
		token = it.next();
		if(token.equals("PROGRAM"))
		{
			node treenodepr=new node("PROGRAM");
			tempchild.add(treenodepr);
			token=it.next();
			node temp=Declaration_method();
			tempchild.add(temp);
			
			if(token.equals("BEGIN"))
			{
				node treenodebe=new node("BEGIN");
				tempchild.add(treenodebe);
				
				token=it.next();
				temp=StatementSequence_method();
				tempchild.add(temp);
				
				if(token .equals("END"))
				{
					node treenodeend=new node("END");
					tempchild.add(treenodeend);
					
					token=it.next();
						if(token.equals("$"))
						{ 
							System.out.println("Sucessfully done");
						}
				}
			}
		}
		
		treenode.child=tempchild;
		return treenode;
		
		
	
	}
	public node Declaration_method()
	{
		node Decnode=new node("<DECLARATION>");
		ArrayList<node> decchild =new ArrayList<node>();
        if(token.equals("VAR"))
		{
			node decvar=new node("VAR");
			decchild.add(decvar);
			
			token=it.next();
			
			if(token.startsWith("IDENT"))
			{
				node treenodeident=new node(token);
				decchild.add(treenodeident);
				
				token=it.next();
				if(token.equals("AS"))
				{
					node treenodeas=new node("AS");
					decchild.add(treenodeas);
					
					token=it.next();
					node temptype=Type_method();
					decchild.add(temptype);
					
					if(token.equals("SC"))
					{
						node treenodesc=new node("SC");
						decchild.add(treenodesc);
						
						token=it.next();
						node tempdec=Declaration_method();
						decchild.add(tempdec);
						
					}
				}
			}
		}
		else if(token.equals("BEGIN"))
		{
			node treenodeif=new node("E");
			decchild.add(treenodeif);
			
		}
        Decnode.child=decchild;
		return Decnode;
	}
	public node Type_method()
	{
		node typenode=new node("<TYPE>");
		ArrayList<node> typechild=new ArrayList<node>();
		if(token.equals("INT"))
		{
			node typeint=new node("INT");
			typechild.add(typeint);
			
			token=it.next();
			
		}
		else if(token.equals("BOOL"))
		{
			node typeint=new node("BOOL");
			typechild.add(typeint);
			
			token=it.next();
			
		}
		typenode.child=typechild;
		return typenode;
	}
	public node StatementSequence_method()
	{
		node statementseqnode=new node("<STATEMENTSEQUENCE>");
		ArrayList<node> stseqchild=new ArrayList<node>();
		
		if(token.startsWith("IDENT"))
		{
			
			node tempstat=Statement_method();
			stseqchild.add(tempstat);
			
			if(token.equals("SC"))
			{
				node stseqsc=new node("SC");
				stseqchild.add(stseqsc);
				
				token=it.next();
				node tempstatse =StatementSequence_method();
				stseqchild.add(tempstatse);
				
			}
		}
		else if(token.equals("IF"))
		{
			
			node tempst=Statement_method();
			stseqchild.add(tempst);
			
			if(token.equals("SC"))
			{
				node statementseqsc=new node("SC");
				stseqchild.add(statementseqsc);
				statementseqsc=statementseqnode;
				token=it.next();
				node tempss=StatementSequence_method();
				stseqchild.add(tempss);
				
			}
			
		}
		else if(token.equals("WHILE"))
		{
			node tempw=Statement_method();
			stseqchild.add(tempw);
			
			if(token.equals("SC"))
			{
				node statementseqsc=new node("SC");
				stseqchild.add(statementseqsc);
				
				token=it.next();
				node tempss=StatementSequence_method();
				stseqchild.add(tempss);
				
			}
		}
		else if(token.equals("WRITEINT"))
		{
			node tempwr=Statement_method();
			stseqchild.add(tempwr);
			
			if(token.equals("SC"))
			{
				
				node statementseqsc=new node("SC");
				stseqchild.add(statementseqsc);
				
				token=it.next();
				node tempss=StatementSequence_method();
				stseqchild.add(tempss);
				
			}
		}
		else if(token.equals("END"))
		{
			node stseqE=new node("E");
			stseqchild.add(stseqE);
			
		}
		else if(token.equals("ELSE"))
		{
			node stseqE=new node("E");
			stseqchild.add(stseqE);
			
		}
		statementseqnode.child=stseqchild;
		return statementseqnode;
		
	}
	public node Statement_method()
	{
		node statementnode =new node("<STATEMENT>");
		ArrayList<node> statchild=new ArrayList<node>();
		
	 if(token.startsWith("IDENT"))
	 {
		 
		 node tempass=Assignment_method();
		 statchild.add( tempass);
		
	 }
	 else if(token.equals("IF"))
	 {
		
		 node tempif=IfStatement_method();
		 statchild.add(tempif);
		
	 }
	 else if(token.equals("WHILE"))
	 {
		 node tempw=WhileStatement_method();
		 statchild.add(tempw);
		
	 }
	 else if(token.equals("WRITEINT"))
	 {
		 node tempw=WriteInt_method();
		 statchild.add(tempw);
		
	 }
	 statementnode.child=statchild;
	 return statementnode;
	}
	public node Assignment_method()
	{
		node assignmentnode = new node("<ASSIGNMENT>");
		ArrayList<node> asschild=new ArrayList<node>();
		if(token.startsWith("IDENT"))
		{
			
			node assident=new node(token);
			asschild.add(assident);
			
			token=it.next();
			if(token.equals("ASGN"))
			{
				
				node assass=new node("ASGN");
				asschild.add(assass);
				
				token=it.next();
				node asslf=AssignmentLf_method();
				asschild.add(asslf);
				
			}
		}
		assignmentnode.child=asschild;
		return assignmentnode;
	}
	public node AssignmentLf_method()
	{
		node assLfnode =new node("<ASSIGNMENTLF>");
		ArrayList<node> asslfchild=new ArrayList<node>();
		if(token.startsWith("IDENT"))
		{
			node tempe=Expression_method();
			asslfchild.add(tempe);
			
		}
		else if(token.equals("READINT"))
		{
			
			node asslfreadint=new node("READINT");
			asslfchild.add(asslfreadint);
			
			token=it.next();
		}
		else if(token.startsWith("NUM"))
		{
			node tempe=Expression_method();
			asslfchild.add(tempe);
			
		}
		else if(token.startsWith("BOOLLIT"))
		{
			node tempe=Expression_method();
			asslfchild.add(tempe);
			
		}
		else if(token.equals("LP"))
		{
			node tempe=Expression_method();
			asslfchild.add(tempe);
			
		}
		assLfnode.child=asslfchild;
		
		return assLfnode;
	}
	public node IfStatement_method()
	{
		ifcount=ifcount+1;
		node ifstatnode=new node("<IFSTATEMENT>");
		ArrayList<node> ifstatchild=new ArrayList<node>();
		
		if(token.equals("IF"))
		{
			
			node ifstatif=new node("IF");
			ifstatchild.add(ifstatif);
			
			token=it.next();
			
			node tempe=Expression_method();
			ifstatchild.add(tempe);
			
			if(token.equals("THEN"))
			{
				node ifstatthen=new node("THEN");
				ifstatchild.add(ifstatthen);
				token=it.next();
				ifstatchild.add(StatementSequence_method());
				//System.out.println("out of statement sequence");
				ifstatchild.add(ElseClause_method());
				//System.out.println("out of else clause method");
				if(token.equals("END"))
				{
					//System.out.println(token);
					node ifstatend=new node("END");
					ifstatchild.add(ifstatend);
					token=it.next();
				}
			}
		}
		//System.out.println(ifcount);
		//System.out.println(ifstatchild.size());
		ifstatnode.child=ifstatchild;
		return ifstatnode;
	}
	public node ElseClause_method()
	{
		node elseclausenode=new node("<ELSECLAUSE>");
		ArrayList<node> elseclausechild=new ArrayList<node>();
		if(token.equals("ELSE"))
		{
			node elsenode=new node("ELSE");
			elseclausechild.add(elsenode);
			token=it.next();
			elseclausechild.add(StatementSequence_method());
			
		}
		else if(token.equals("END"))
		{
			node elsenode=new node("E");
			elseclausechild.add(elsenode);
			
		}
		elseclausenode.child=elseclausechild;
		return elseclausenode;
		
	}
	public node WhileStatement_method()
	{
		node whilestatnode=new node("<WHILESTATEMENT>");
		ArrayList<node> whilestatchild=new ArrayList<node>();
		if(token.equals("WHILE"))
		{
			node whilestatwhile=new node("WHILE");
			whilestatchild.add(whilestatwhile);
			token=it.next();
			whilestatchild.add(Expression_method());
			if(token.equals("DO"))
			{
				node whilestatdo=new node("DO");
				whilestatchild.add(whilestatdo);
				token=it.next();
				whilestatchild.add(StatementSequence_method());
				if(token.equals("END"))
				{
					node whilestat=new node("END");
					whilestatchild.add(whilestat);
					token=it.next();
				}
			}
		}
		whilestatnode.child=whilestatchild;
		return whilestatnode;
		
	}
	public node WriteInt_method()
	{
		node writeintnode=new node("<WRITEINT>");
		ArrayList<node> writeintchild=new ArrayList<node>();
		////System.out.println("in writeint method");
		if(token.equals("WRITEINT"))
		{
			node writeintwrite=new node("writeint");
			writeintchild.add(writeintwrite);
			token=it.next();
			writeintchild.add(Expression_method());
		}
		writeintnode.child=writeintchild;
		return writeintnode;
	}
	public node Expression_method()
	{
		node expnode=new node("<EXPRESSION>");
		ArrayList<node> expchild=new ArrayList<node>();
		if(token.startsWith("IDENT("))
		{
			//System.out.println("expression method ident");
			expchild.add(SimpleExpression_method());
			expchild.add(ExpressionLf_method());
		}
		else if(token.startsWith("NUM"))
		{
			expchild.add(SimpleExpression_method());
			expchild.add(ExpressionLf_method());
		}
		else if(token.startsWith("BOOLLIT"))
		{
			expchild.add(SimpleExpression_method());
			expchild.add(ExpressionLf_method());
		}
		else if(token.equals("LP"))
		{
			expchild.add(SimpleExpression_method());
			expchild.add(ExpressionLf_method());
		}
		expnode.child=expchild;
		return expnode;
	}
	public node ExpressionLf_method()
	{
		node explfnode=new node("<EXPRESSIONLF>");
		ArrayList<node> explfchild=new ArrayList<node>();
		if(token.startsWith("COMPARE"))
		{
			//System.out.println(token);
			node explfcompare=new node(token);
			explfchild.add(explfcompare);
			token=it.next();
			explfchild.add(Expression_method());
		}
		else if(token.equals("SC"))
		{
			node explfsc=new node("E");
			explfchild.add(explfsc);
			
		}
		else if(token.equals("DO"))
		{
			node explfdo=new node("E");
			explfchild.add(explfdo);
		}
		else if(token.equals("THEN"))
		{
			node explfthen=new node("E");
			explfchild.add(explfthen);
		}
		else if(token.equals("RP"))
		{
			node explfrp=new node("E");
			explfchild.add(explfrp);
		}
		explfnode.child=explfchild;
		return explfnode;
	}
	public node SimpleExpression_method()
	{
		node simpexpnode=new node("<SIMPLEEXPRESSION>");
		ArrayList<node> simpexpchild=new ArrayList<node>();
		
		if(token.startsWith("IDENT"))
		{

			simpexpchild.add(Term_method());
			simpexpchild.add(SimpleExpressionLf_method());
		}
		else if (token.startsWith("NUM"))
		{
			
			simpexpchild.add(Term_method());
			simpexpchild.add(SimpleExpressionLf_method());
		}
		else if (token.startsWith("BOOLLIT"))
		{
			simpexpchild.add(Term_method());
			simpexpchild.add(SimpleExpressionLf_method());
		}
		else if (token.equals("LP"))
		{
			simpexpchild.add(Term_method());
			simpexpchild.add(SimpleExpressionLf_method());
		}
		simpexpnode.child=simpexpchild;
		return simpexpnode;
	}
	public node SimpleExpressionLf_method()
	{
		node simpexplfnode=new node("<SIMPLEEXPRESSIONLF>");
		ArrayList<node> simpexplfchild=new ArrayList<node>();
		if(token.startsWith("ADDITIVE"))
		{
			
			node simpexpadd=new node(token);
			simpexplfchild.add(simpexpadd);
			token=it.next();
			simpexplfchild.add(SimpleExpression_method());
		}
		else if(token.startsWith("COMPARE"))
		{
			node simpexplfcmp=new node("E");
			simpexplfchild.add(simpexplfcmp);
			
		}
		else if(token.equals("SC"))
		{
			node simpexplfsc=new node("E");
			simpexplfchild.add(simpexplfsc);
			
		}
		else if(token.equals("THEN"))
		{
			node simpexplfthen=new node("E");
			simpexplfchild.add(simpexplfthen);
			
		}
		else if(token.equals("DO"))
		{
			node simpexplfdo=new node("E");
			simpexplfchild.add(simpexplfdo);
			
		}
		
		else if(token.equals("RP"))
		{
			node simpexplfrp=new node("E");
			simpexplfchild.add(simpexplfrp);
			
		}
		simpexplfnode.child=simpexplfchild;
		return simpexplfnode;
	}
	public node Term_method()
	{
		node termnode=new node("<TERM>");
		ArrayList<node> termchild=new ArrayList<node>();
		if(token.startsWith("IDENT"))
		{
			
			termchild.add(Fact_method());
			termchild.add(TermLf_method());
		}
		else if(token.startsWith("NUM"))
		{
			termchild.add(Fact_method());
			termchild.add(TermLf_method());
		}
		else if(token.startsWith("BOOLLIT"))
		{
			termchild.add(Fact_method());
			termchild.add(TermLf_method());
		}
		else if(token.equals("LP"))
		{
			termchild.add(Fact_method());
			termchild.add(TermLf_method());
		}
		termnode.child=termchild;
		return termnode;
	}
	public node TermLf_method()
	{
		node termlfnode=new node("<TERMLF>");
		ArrayList<node> termlfchild=new ArrayList<node>();
		if(token.startsWith("MULTIPLICATIVE"))
		{
			node termlfmul=new node(token);
			termlfchild.add(termlfmul);
			token=it.next();
			termlfchild.add(Term_method());
		}
		else if(token.startsWith("ADDITIVE"))
		{
			node termlfadd=new node("E");
			termlfchild.add(termlfadd);
			
		}
		else if(token.startsWith("COMPARE"))
		{
			node termlfcmp=new node("E");
			termlfchild.add(termlfcmp);
			
		}
		else if(token.equals("SC"))
		{
			node termlfsc=new node("E");
			termlfchild.add(termlfsc);
			
		}
		else if(token.equals("THEN"))
		{
			node termlfthen=new node("E");
			termlfchild.add(termlfthen);
			
		}
		else if(token.equals("DO"))
		{
			node termlfdo=new node("E");
			termlfchild.add(termlfdo);
			
		}
		else if(token.equals("RP"))
		{
			node termlfrp=new node("E");
			termlfchild.add(termlfrp);
			
		}
		termlfnode.child=termlfchild;
		return termlfnode;
	}
	public node Fact_method()
	{
		node factnode=new node("<FACTOR>");
		ArrayList<node> factchild=new ArrayList<node>();
		if(token.startsWith("IDENT"))
		{
			
			node factident=new node(token);
			factchild.add(factident);
			token=it.next();
		}
		else if(token.startsWith("NUM"))
		{
			
			node factnum=new node(token);
			factchild.add(factnum);
			token=it.next();
		}
		else if(token.startsWith("BOOLLIT"))
		{
			node factbool=new node(token);
			factchild.add(factbool);
			token=it.next();
		}
		else if(token.equals("LP"))
		{
			node factlp=new node("LP");
			factchild.add(factlp);
			token=it.next();
			factchild.add(Expression_method());
			if(token.equals("RP"))
			{
				node factrp=new node("RP");
				factchild.add(factrp);
				token=it.next();
			}
		}
		factnode.child=factchild;
		return factnode;
	}
	/*
	public class node
	{
		String name;
		ArrayList<node> child;
		
		int number;
		public node(String name)
		{
			this.name=name;
			//this.parent=null;
			this.child=null;
			
		}
		
	}
	*/
	
	
/*	
	public void printtree(node treenode, int parentcounter )
	{
		try
		{
		count = count+1;
		int tempCount = count;
		System.out.println( count + "[label=\""+treenode.name+"\"];");
		if(parentcounter>0)
		{
			System.out.println(parentcounter+" -- "+ count);
		}
		if(treenode.name.startsWith("<"))
		{
			
			
			ArrayList<node> child=treenode.child;
			Iterator<node> it=child.iterator();
			
			while(it.hasNext())
			{
				node temp=it.next();
				printtree(temp,tempCount);
			}
			
		}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	*/
	
	
}
