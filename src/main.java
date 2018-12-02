import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {
	
	static List<String> operators = Arrays.asList("V","^","-");

	public static void main(String[] args) throws IOException
	{
			
		String file = new String();
		Boolean test = false;
		
		if (args.length > 0)
		{
			file = new String(args[args.length-1]);
		}
		else
		{
			file = "uf20-01.cnf";
			test = true;
		}
		System.out.println("File Selected: " + file);
		
		//read the file and separate all the text
		BufferedReader r;
		try {
			r = new BufferedReader(new FileReader(file));
			
			ArrayList<String> list = new ArrayList();
			Formula equation = new Formula();
			int variabes;
			int clauses;			
			String line;
			
			while ((line = r.readLine()) != null) 
			{
				if(comment(line)){ }
				else if(problem(line))
				{
					String[] s = line.split(" ");
					variabes = Integer.parseInt(s[2]);
					clauses = Integer.parseInt(s[3]);
				}
				else
				{
					Clause c = new Clause();
					String[] tokens = line.split(" ");
					int literal;
					for(int i = 0; i < tokens.length; i++) {
						literal = Integer.parseInt(tokens[i].trim());
						if(literal != 0){
							c.addLiteral(literal);
						}
					}
					equation.addClause(c);
				}
			}
			r.close();

			list.removeAll(Arrays.asList("", null));
			System.out.println("Number of Clauses: " + equation.getSize());
			long startTime = System.currentTimeMillis();
			boolean satisfiable = equation.applyDPLL(equation);
			System.out.println("The Number of Recursive Calls: "+equation.getrCallSum());
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("Elapsed Time: " + totalTime + " ms");
			if(satisfiable){
				System.out.println("Result: SAT");
				equation.printSATSet();
			} else {
				System.out.println("Result: UNSAT");
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File not found");
			System.exit(0);
		}
	}
	

	private static boolean problem(String line) {
		// TODO Auto-generated method stub
		if(line.charAt(0) == 'p')
		{
			return true;
		}
		return false;
	}
	
	private static boolean comment(String line) {
		// TODO Auto-generated method stub
		if(line.charAt(0) == 'c')
		{
			return true;
		}
		return false;
	}
	
	
}


