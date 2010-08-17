/* Guessing Game
 * Written by: Logan Bell
 *
 * Background: My super awesome guessing game. It has two
 * parts - the number guesser and the word guesser.
 *  
 */

import java.util.*;
import java.io.*;

public class Game
/*
 * Name: Game
 * Description: This is the client class. This class is ran by default
 */
{
	
	public static void main(String[] args)
	/*
	 * Name: main
	 * Description: Static method to run the program
	 * Input: String args
	 * Output: void
	 */
	{
		GuessMachine the_game;
		the_game = new GuessMachine();
		the_game.run();
	}
	
}

class GuessMachine 
/*
 * Name: GuessMachine
 * 
 * Synopsis:
 * 	GuessMachine my_object = new GuessMachine();
 * 	my_object.run();
 * 
 * Description: 
 * 	The Guess Machine initiates the a Stack object and handles UI
 * 
 * Public Methods:
 * 	
 * 	run()
 * 	Purpose: Kicks off the UI and begins the game
 *
 */
{

	private Stack my_stack;   //Private ptr to a Stack object
	
	GuessMachine()
	/*
	 * Name: GuessMachine
	 * Description: Constructor for GuessMachine
	 * Input: void
	 * Output: void
	 */
	{
		this.my_stack = new Stack();
		add_numbers();
			
	}
	
	
	
	public void run()
	/*
	 * Name: run
	 * Description: Public method that runs a GuessMachine game
	 * Input: void
	 * Output: void
	 */
	{
		this.display_current_menu();
	}
	
	
	
	private void add_numbers()
	/*
	 * Name: add_numbers
	 * Description: Pushes the various guesses onto the stack object
	 * Input: void
	 * Output: void
	 */
	{
		this.my_stack.push(5);
		this.my_stack.push(4);
		this.my_stack.push(3);	
		this.my_stack.push("Dog","Cats and ____", "They bark", "The mailman doesn't like them");
	}
	
	
	
	private void display_current_menu()
	/*
	 * Name: display_current_menu
	 * Description: Manages the menu interface for the game
	 * Input: void
	 * Output: void
	 */
	{
		InputStreamReader isr 		= new InputStreamReader(System.in);
		BufferedReader br 			= new BufferedReader(isr);
		GuessType current_guesser 	= this.my_stack.pop();
		while (true)
		{
			System.out.print(current_guesser.display_guess_phrase());
			try {
				String line = br.readLine();
				
				if (current_guesser.compare(line) == true)
				{
					System.out.print(current_guesser.display());
				}
				else
				{
					System.out.println(current_guesser.winner_display());
					// Pop another one off the stack
					current_guesser = this.my_stack.pop();
				
					// Will be set to null if no more to pop off, exit gracefully
					if (current_guesser == null)
					{
						System.out.println("You guessed everything on my mind! GAME OVER\n\n");
						System.exit(0);
					}
				}
				
			} 
			catch (IOException ex_io)	
			{
				System.out.println(ex_io);
				System.exit(-1);
			} 
			catch (NumberFormatException ex_nf) 
			{
				System.out.println("Improper input, re-enter");
			}

		}
		
	}
	
}

abstract class GuessType
/*
 * Name: GuessType
 * 
 * Synopsis:
 * 	
 * 
 * Description: 
 * 	An abstract class for the various GuessType objects.
 * 
 * Public Methods:
 * 	
 * 	compare()
 * 	Purpose: An abstracted method that accepts a String and returns a boolean.
 * 
 *  winner_display()
 *  Purpose: An abstracted method that returns a String if someone wins
 *  
 *  display()
 *  Purpose: An abstracted method that returns a STring when someone doesn't win (Standard display)
 *  
 *  next()
 *  Purpose: Returns a next ptr
 *  
 *  next(String)
 *  Purpose: Sets a the internal next to point to a new GuessType object
 *  
 */
{
	/*Private data*/
	private GuessType next;
	
	/*Abstract Public Methods*/
	public abstract boolean compare(String line);
	public abstract String winner_display();
	public abstract String display();
	public abstract String display_guess_phrase();
	
	/*Public Inherited methods*/
	public GuessType next()
	/*
	 * Name: next
	 * Description: A getter for the next ptr
	 * Input: void
	 * Output: GuessType Object
	 */
	{
		return this.next;
	
	}
	
	
	
	public void next(GuessType new_next)
	/*
	 * Name: next
	 * Description: A getter for the next ptr
	 * Input: GuessType object
	 * Output: void
	 */
	{
		this.next = new_next;
	}
	
}



class Word extends GuessType
/*
 * Name: Word
 * 
 * Synopsis:
 * 	Word word_obj = new Word(0, word, hint1, hint2, hint3);
 * 	word_obj.compare(Guessed_String);
 * 
 * Description: 
 * 	The Word class manages the logic for guessing a Word.
 * 
 * Public Methods:
 * 	
 * 	Word(int, string, string, string)
 * 	Purpose: Constructor. Accepts for 4 argument. The first argument is to initiate the guesses data. 
 *           then 3 hints are supplied.
 *  
 *  compare()
 *  Purpose: Accepts a string to compare with the internal state. 
 *  
 *  display()
 *  Purpose: Displays to the user what happens if they guess incorrectly
 *  
 *  winner_display()
 *  Purpose: Displays to the user what happens if they win
 */
{
	private int guesses;
	private String word;
	private String[] hints;
	private String compare_result;
	
	
	Word (int guesses, String word, String hint1, String hint2, String hint3)
	/*
	 * Name: Word
	 * Description: Constructor for the Word class
	 * Input: Int, String, String, String
	 * Output: void
	 */
	{
		this.guesses = guesses;
		this.word = word;
		this.hints = new String[3];
		this.hints[0] = hint1;
		this.hints[1] = hint2;
		this.hints[2] = hint3;
	}
	
	
	
	public boolean compare(String line)
	/*
	 * Name: compare
	 * Description: Compares a string with the private word
	 * Input: String args
	 * Output: Boolean
	 */
	{	
		this.guesses++;	
		if (this.word.toLowerCase().equals(line))
		{
			return false;
		}
		
		// Grab a random hint
		Random generator = new Random(); 
		int random_num = generator.nextInt(3);
		String hint = hints[random_num];
		this.compare_result = "\n" + hint + "\n";
		return true;
	}
	
	
	
	public String display()
	/*
	 * Name: display
	 * Description: Returns the results of a guess
	 * Input: void
	 * Output: String Object
	 */
	{
		return this.compare_result;
	}
	
	
	public String display_guess_phrase()
	/*
	 * Name: display_guess_phrase
	 * Description: Returns a String object with the beginning guess game
	 * Input: void
	 * Output: String Object
	 */
	{
		return "I'm thinking of a word, can you guess it?\n";
	}
	
	
	
	public String winner_display()
	/*
	 * Name: winner_display
	 * Description: Displays the winner message.
	 * Input: void
	 * Output: String Object
	 */
	{
		return "You guessed it!!\nIt only took you " + this.guesses + " guesses\n";
	
	}
}

class Number extends GuessType
/*
 * Name: Number
 * 
 * Synopsis:
 * 	Number number_obj = new Number(0, number);
 * 	number_obj.compare(Guessed_String);
 * 
 * Description: 
 * 	The Number class manages the logic for guessing a Number.
 * 
 * Public Methods:
 * 	
 * 	Number(int, int)
 * 	Purpose: Constructor. Accepts 2 arguments. The first argument is to initiate the guesses data. 
 *           then a number for the internal state.
 *  
 *  compare()
 *  Purpose: Accepts a string to compare with the internal state. 
 *  
 *  display()
 *  Purpose: Displays to the user what happens if they guess incorrectly
 *  
 *  winner_display()
 *  Purpose: Displays to the user what happens if they win
 */
{
	
	private int num;
	private int guesses;
	private String compare_result;
	
	Number(int guesses, int num)
	/*
	 * Name: Number
	 * Description: Constructor
	 * Input: int, int
	 * Output: void
	 */
	{
		this.guesses = guesses;
		this.num = num;
	}
	
	
	
	public boolean compare (String line)
	/*
	 * Name: compare
	 * Description: Compares the inputed String with num
	 * Input: String args
	 * Output: Boolean
	 */
	{
		int num = Integer.parseInt(line);	 //Convert the String object into an integer
		
		this.compare_result 	= "";		//The result string that is returned
		boolean return_compare 	= false;	//To remember the state of the result
		
		if (num != this.num)
		{
			this.compare_result = "Sorry the number does not match!\n";			
			return_compare = true;
		}
		
		if (num < this.num)
		{
			this.compare_result += "Hint: The number is greater than what you entered\n";
			return_compare = true;
		}
		
		if (num > this.num)
		{
			this.compare_result += "Hint: The number is LESS than what you entered\n";
			return_compare = true;
		}
	
		this.guesses++;
		return return_compare;
			
	}


	
	public String display()
	/*
	 * Name: display
	 * Description: Returns a String object with the compare results
	 * Input: void
	 * Output: String Object
	 */
	{
		return this.compare_result;	
	}
	
	
	public String display_guess_phrase()
	/*
	 * Name: display_guess_phrase
	 * Description: Returns a String object with the beginning guess game
	 * Input: void
	 * Output: String Object
	 */
	{
		return "I'm thinking of a number, can you guess it?\n";
	}
	
	
	
	public String winner_display()
	/*
	 * Name: winner_display
	 * Description: Displays the winner message
	 * Input: void
	 * Output: String Object
	 */
	{
		return "You guessed it!!\nIt only took you " + guesses + " guesses!";
	
	}
	
}

class Stack 
/*
 * Name: Stack
 * 
 * Synopsis:
 * 	Stack stack_obj = new Stack();
 * 	stack_obj.push(int);
 *  stack_obj.push(string);
 *  
 *  GuesssType foo = stack.pop();
 * 
 * Description: 
 * 	Manages a collection of various GuessType objects. Using a Stack style ADT.
 * 
 * Public Methods:
 * 	
 * 	Stack()
 * 	Purpose: Constructor. 
 *           
 *  push()
 *  Purpose: Accepts either a string or int, two overloaded methods 
 *  
 *  pop()
 *  Purpose: Returns a GuessType object, either Number or Word.
 *  
 *  peek()
 *  Purpose: Returns a GuessType object if one exists.
 *
 *  isempty()
 *  Purpose: Verifies if the stack is empty
 */
{
	
	private GuessType head;
	
	Stack()
	/*
	 * Name: Stack
	 * Description: Constructor
	 * Input: void
	 * Output: void
	 */
	{
		this.head = null; 
	}
	
	
	
	public int push(int num)
	/*
	 * Name: push
	 * Description: Creates a Number object and places on stack
	 * Input: int
	 * Output: int
	 */
	{
		if (num > 0)
	    {
	        if (!this.isempty())
	        {
	            GuessType temp = this.head;
	            this.head = new Number(0, num);
	            this.head.next(temp);
	        }
	        else
	        {
	            this.head = new Number(0,num);
	            this.head.next(null);
	        }
	        return(1);
	    }
	    return(0);

		
	}
	
	
	
	public int push(String word, String hint1, String hint2, String hint3)
	/*
	 * Name: push
	 * Description: Creates a Word object and places it on the Stack
	 * Input: String, String, String, String
	 * Output: int
	 */
	{
		if (word.length() > 0)
		{
			if (!this.isempty())
			{
				GuessType temp = this.head;
				this.head = new Word(0, word, hint1, hint2, hint3);
            	this.head.next(temp);
			}
			else
			{
				this.head = new Word(0, word, hint1, hint2, hint3);
				this.head.next(null);
			}
			return(1);
		}
		return(0);
				
	}
	
	
	
	public GuessType pop()
	/*
	 * Name: pop
	 * Description: Pops a GuessType object off the stack
	 * Input: void
	 * Output: GuessType Object
	 */
	{
	    if(!this.isempty())
	    {
	    	GuessType temp = this.head;
	    	if ( this.head.next() != null)
	        {
	        	this.head = this.head.next();
	        }
	    	else
	    	{
	    		this.head = null;
	    	}
	    	return temp;
	      	      
	    }
	    return this.head;
		
	}
	
	
	
	public GuessType peek()
	/*
	 * Name: peek
	 * Description: Peeks to see what next GuessType Object exists
	 * Input: void
	 * Output: GuessType Object
	 */
	{
		return this.head;
	}
	
	
	
	public boolean isempty()
	/*
	 * Name: isempty
	 * Description: Returns a boolean if the stack is empty
	 * Input: void
	 * Output: Boolean
	 */
	{
		if (this.head == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
		
}
