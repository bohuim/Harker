
public class Tester 
{
	private WordGameDisplay display;
	
	private String history;
	private String chart = "";
	private int rows;
	private int columns;
	private double[][] table;
	
	private double A;
	private double B;
	private double C;
	private double df;
	private double result;
	
	public Tester()
	{
		display = new WordGameDisplay();
	}
	
	public void test()
	{
		display.setText("# of Rows of Data?");
		rows = Integer.parseInt(display.getGuess());
		display.setText("# of Columns of Data?");
		columns = Integer.parseInt(display.getGuess());
		
		table = new double[rows+1][columns+1];
		
		for (int r=0; r<rows+1; r++)
		{
			for (int c=0; c<columns+1; c++)
			{
				if (r<rows)
				{
					if (c<columns)
					{
						display.setText("Row " + (r+1) + ", Column " + (c+1) + "?" + "\n" + chart);
						table[r][c]=Integer.parseInt(display.getGuess());
						table[r][columns] = 0 + table[r][columns] + table[r][c];
						table[rows][c]+=table[r][c];
					}
					chart = chart + table[r][c] + "  ";
				}
				else
				{
					chart = chart + table[r][c] + "  ";
					table[rows][columns]+=table[r][c];
				}
				
			}
			if (r<rows)
				chart += "\n";
		}
		
		for (int r=0; r<rows; r++)
		{
			for (int c=0; c<columns; c++)
				A = A + (table[r][c]*Math.log(table[r][c]));
		}
		
		
		for (int r=0; r<rows; r++)
			B = B + (table[r][columns]*Math.log(table[r][columns]));
		for (int c=0; c<columns; c++)
			B = B + (table[rows][c]*Math.log(table[rows][c]));

		for (int r=0; r<rows; r++)
			C += table[r][columns];
		C = C*Math.log(C);
		
		df = (rows-1)*(columns-1);
		
		result = 2*(A-B+C);
		
		history = "\n" + "A = " + A + 
				  "\n" + "B = " + B +
				  "\n" + "C = " + C +
				  "\n" + "df = " + df + 
				  "\n" + "f Test = " + result;
				  ;
		display.setText(chart + history);
		
		display.getGuess();
		chart = "";
		A = 0;
		B = 0;
		C = 0;
		df = 0;
		result = 0;
	}
}
