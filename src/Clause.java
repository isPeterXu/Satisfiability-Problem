import java.util.ArrayList;

public class Clause {
	
	ArrayList<Integer> literals;

	//constructor
	public Clause(){
		literals = new ArrayList<>();
	}
	
	public Clause(Clause c){
		literals = new ArrayList<>();
		for(int i = 0; i < c.getSize(); i++){
			literals.add(c.getLiteral(i));
		}
		
	}
	//add a literal to clause
	public void addLiteral(int literal){
		literals.add(literal);
	}
	//remove a literal from clause
	public void removeLiteral(int index){
		literals.remove(index);
	}
	//get the size of clause
	public int getSize(){
		return literals.size();
	}
	//get the i-th literal of clause
	public int getLiteral(int index){
		return literals.get(index);
	}
	//
	public String toString(){
		if(this.getSize() == 0){
			return "()";
		}
		String output = "(";
		for(int i = 0; i<literals.size(); i++){
			if(i < literals.size()-1){
				output = output + literals.get(i) + " v ";
			} else { output = output + literals.get(i) + ")";}
		}
		return output;
	}
}
