import java.util.ArrayList;

public class Equation {
	
	private ArrayList<Clause> clauses;
	private ArrayList<Integer> SATSet; //a set of SAT
	//constructor
	public Equation(){
		this.clauses = new ArrayList<Clause>();
		this.SATSet = new ArrayList<Integer>();
	}
	public Equation(Equation eq){
		clauses = new ArrayList<Clause>();
		this.SATSet = new ArrayList<Integer>();
		for(Clause c : eq.getClauses()) {
			Clause copy = new Clause(c);
		    clauses.add(copy);
		}

	}
	//add a literal to SATset
	public void addToSet(int literal){
		SATSet.add(literal);
	}
	//get the i-th clause
	public Clause getClause(int i){
		return clauses.get(i);
	}

	public void addClause(Clause clause){
		clauses.add(clause);		
	}
	
	public ArrayList<Clause> getClauses(){
		return clauses;
	}

	public int getSize(){
		return clauses.size();
	}

	public boolean hasEmptyClause(){
		// method to find if any empty clauses exist
		for(int index = 0; index < clauses.size(); index++){
			Clause clause = clauses.get(index);
			if(clause.getSize() == 0){
				return true;
			}
		}
		return false;
	}

	public int hasSingle(){
		// find if any clause has a single literal and return 0 if none exist
		for(int i = 0; i < clauses.size(); i++){
			Clause clause = clauses.get(i);
			if(clause.getSize() == 1){
				return clause.getLiteral(0);
			}
		}
		return 0;
	}

	public String toString(){
		String output = "{";
		if(clauses.size() == 0) return "{}";
		for(int index = 0; index < clauses.size(); index++){
			if(index < clauses.size()-1){
				output = output + clauses.get(index) + "^";
			} else { output = output + clauses.get(index) + "}";}
		}
		return output;
	}

	// Method to print out the set of SAT
	public void printSATSet(){
		String output = "";
		String temp;
		int printLn = 0;
		if(SATSet.size() == 0) output = "{}";
		for(int i = 0; i < SATSet.size(); i++){
			if (printLn == 10){
				output = output + "\n";
				printLn = 0;
			}
			int literal = SATSet.get(i);
			if(literal > 0){
				temp = literal + "=T";
			} else {temp = -1*literal + "=F";}
			if(i < SATSet.size()-1){
				output = output + temp + ",	";
			} else { output = output + temp;}
			printLn++;
		}
		System.out.println("Assignment:" + "\n" + output);
	}

	//Implementation based on DPLL
	public boolean applyDPLL(Equation eq){

		// if equation has no clauses, return true
		if(eq.getSize() == 0){return true;}
		// if an equation has an empty clause, return false
		if(eq.hasEmptyClause()){return false;}

		// if equation contains a clause with one literal
		int unitClause = eq.hasSingle();
		if(unitClause != 0){
			Equation eqWthUnitClause = new Equation(eq);
			eqWthUnitClause.simplify(unitClause);
			return applyDPLL(eqWthUnitClause);
		}

		/**
		 * Select a literal from a shortest clause to branch on.
		 */
		int selected = eq.selectFromShortestClause();
		Equation complementedEquation = new Equation(eq);
		Equation uncomplementedEquation = new Equation(eq);
		complementedEquation.simplify(selected);
		uncomplementedEquation.simplify(-1*selected);
		if(applyDPLL(complementedEquation) == true){
			addToSet(selected);
			return true;}
		else {
			addToSet(-1*selected);
			return applyDPLL(uncomplementedEquation);
		}
	}

	public void simplify(int literal){
		/**
		 * method to simplify an equation based on a selected literal
		 * if the clause contains that literal, then that clause is removed
		 * if the clause contains the literal in complemented form, remove it from the clause
		 */
		for(int i = 0; i < clauses.size(); i++){
			Clause clause = clauses.get(i);
			for(int j = 0; j < clause.getSize(); j++){
				if(clause.getLiteral(j) == literal){
					clauses.remove(i);
					i--;
				}  else if (clause.getLiteral(j) == (-1 * literal)){
					clause.removeLiteral(j);
				}
			}
		}
	}
	
	public int selectFromShortestClause(){
		/**
		 * method to return the first literal from shortest.
		 */
		int minimum = 99;
		int returnValue = 0;
		ArrayList<Integer> literals = new ArrayList<Integer>();
		for(int index = 0; index< clauses.size(); index++){
			Clause clause = clauses.get(index);
			if(clause.getSize() < minimum){
				minimum = clause.getSize();
				returnValue = clause.getLiteral(0);
			}

		}			
		return returnValue;
	}
}
