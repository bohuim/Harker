import java.util.*;

public class PriorityQueueTester
{
	private static boolean debug = true;

	private static final int NUMBER_OF_ELEMENTS = 100;
	private static final int NUMBER_OF_OPERATIONS = 1000;

	public static void main(String[] args)
	{
		PriorityQueue<Integer> real = new PriorityQueue<Integer>();
		MyPriorityQueue<Integer> yours = new MyPriorityQueue<Integer>();

		if (!yours.isEmpty())
			throw new Error("isEmpty returned false initially");

		//test peek when empty
		if (yours.peek() != null)
			throw new Error("peek should return null when empty");

		System.out.println("---------- FILLING UP ----------");

		for (int i = 0; i < NUMBER_OF_ELEMENTS; i++)
		{
			debug("real:  " + real);
			debug("yours:  " + yours);

			Integer x = new Integer(random(NUMBER_OF_ELEMENTS));
			debug("Adding " + x);
			real.add(x);
			yours.add(x);
		}

		System.out.println("---------- TRYING RANDOM OPERATIONS ----------");

		for (int i = 0; i < NUMBER_OF_OPERATIONS; i++)
		{
			debug("real:  " + real);
			debug("yours:  " + yours);

			if (real.isEmpty())
			{
				if (!yours.isEmpty())
					throw new Error("isEmpty returned false instead of true");

				//add something else
				Integer x = new Integer(random(NUMBER_OF_ELEMENTS));
				debug("Adding " + x);
				boolean realValue = real.add(x);
				boolean yourValue = yours.add(x);
				if (realValue != yourValue)
					throw new Error("add returned " + yourValue + " and should return " + realValue);
			}
			else
			{
				if (yours.isEmpty())
					throw new Error("isEmpty returned true instead of false");

				int op = random(3);

				if (op == 0)
				{
					//add something else
					Integer x = new Integer(random(NUMBER_OF_ELEMENTS));
					debug("Adding " + x);
					boolean realValue = real.add(x);
					boolean yourValue = yours.add(x);
					if (realValue != yourValue)
						throw new Error("add returned " + yourValue + " and should return " + realValue);
				}
				else if (op == 1)
				{
					//test remove
					debug("Removing");
					Integer realMin = real.remove();
					Integer yourMin = yours.remove();
					if (!realMin.equals(yourMin))
						throw new Error("removeMin returned " + yourMin +
							" instead of " + realMin);
				}
				else
				{
					//test peek
					Integer realMin = real.peek();
					Integer yourMin = yours.peek();
					if (!realMin.equals(yourMin))
						throw new Error("peekMin returned " + yourMin +
							" instead of " + realMin);
				}
			}
		}

		System.out.println("---------- EMPTYING ----------");

		//empty it
		while (!real.isEmpty() && !yours.isEmpty())
		{
			debug("real:  " + real);
			debug("yours:  " + yours);
			debug("Removing");

			Integer realMin = real.remove();
			Integer yourMin = yours.remove();
			if (!realMin.equals(yourMin))
				throw new Error("removeMin returned " + yourMin +
					" instead of " + realMin);
		}

		if (real.isEmpty() != yours.isEmpty())
			throw new Error("isEmpty returned " + yours.isEmpty() +
				" instead of " + real.isEmpty());

		System.out.println("YOU WIN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}

	private static int random(int n)
	{
		return (int)(Math.random() * n);
	}

	private static void debug(String message)
	{
		if (debug)
			System.out.println(message);
	}
}