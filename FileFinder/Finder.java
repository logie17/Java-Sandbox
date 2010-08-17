 /* Written by: Logan Bell
 *
 * Background: My super awesome totally rad directory/keyword manager
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Finder
/*
 * Name: Finder
 * Description: This is the client class. This class is ran by default
 */
{
   public static void main(String [] args )
    /*
    * Name: main
    * Description: Static method to run the program
    * Input: String args
    * Output: void
    */
   {
       DirectoryFinder df_obj = new DirectoryFinder();
       df_obj.run();

   }
}



class DirectoryFinder
/*
 * Name: DirectoryFinder
 *
 * Synopsis:
 *  DirectoryFinder my_object = new DirectoryFinder();
 *  my_object.run();
 *
 * Description:
 *  The DirectoryFinder manages the UI is the heart of the program
 *
 * Public Methods:
 *
 *  run()
 *  Purpose: Kicks off the UI
 *
 */
{
   private BST tree;
   DirectoryFinder()
   /*
   * Name: DirectoryFinder
   * Description: The constructor
   * Input: void
   * Output: void
   */
   {
       this.tree = new BST();

   }



   public void run()
   /*
   * Name: run
   * Description: Runs the UI for the pprogram
   * Input: void
   * Output: void
   */
   {
       InputStreamReader isr       = new InputStreamReader(System.in);
       BufferedReader br           = new BufferedReader(isr);
       while (true)
       {
           System.out.println("What would you like to do?");
           System.out.println("1 - Search for Keyword");
           System.out.println("2 - Add a keyword");
           System.out.println("3 - Remove a keyword");
           System.out.println("4 - Display All");
           System.out.println("5 - Exit");
           try {
               String line = br.readLine();
               int num = Integer.parseInt(line);

               if (num == 1)
               {
                   System.out.println("Enter a keyword");
                   String keyword = br.readLine();

                   String directory = this.tree.find_directory(keyword);
                   System.out.println(directory);

               }
               else if (num == 2)
               {
                   System.out.println("What keyword would you like to add?");
                   String keyword = br.readLine();
                   System.out.println("What directory would you like to add?");
                   String directory = br.readLine();
                   System.out.println(this.tree.add(keyword, directory));
               }
               else if (num == 3)
               {
                   System.out.println("What keyword would you like to remove?");
                   String keyword = br.readLine();
                   System.out.println(this.tree.remove_keyword(keyword));
               }
               else if (num == 4)
               {
                   System.out.println(this.tree.display_in_order());
               }
               else if (num == 5)
               {
                   System.out.println("Goodbye!");
                   System.exit(0);
               }
               else
               {
                   System.out.println("Invalid choice!");
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



class BST
/*
 * Name: BST
 *
 * Synopsis:
 *  BST my_object = new BST();
 *  my_object.add("My Keyword", "My Directory");
 *
 * Description:
 *  The BST class manages BSTNodes. It manages the binary search tree and
 *  offers public methods to manage it.
 *
 * Public Methods:
 *
 *  add()
 *  Purpose: Adds a new BSTNode to the binary search tree
 *
 *  display_in_order()
 *  Purpose: Displays the elements in keyword search order
 *
 *  find_directory()
 *  Purpose: Finds a supplied directory by keyword. Returns a string
 *
 *  remove_keyword()
 *  Purpose: Removes a particular keyword/directory
 */
{
   private BSTNode root;

   BST()
   /*
   * Name: BST
   * Description: The constructor
   * Input: void
   * Output: void
   */
   {
       this.root = null;       //Root node is set to NULL
   }



   public String add(String keyword, String directory)
   /*
   * Name: add
   * Description: Addes a BSTNode to the binary search tree
   * Input: String (keyword), String (directory)
   * Output: String (Success Message)
   */
   {
       BSTNode current = this.root;

       if (current == null)
       {
           this.root = new BSTNode(keyword, directory);
           return "Successfully added ";
       }
       else
       {
           while (current != null)
           {
               if(current.compare(keyword) < 0)
               {
                   if(current.left_node() == null)
                   {
                       current.left_node(new BSTNode(keyword, directory));
                       return "Successfully added";
                   }
                   current = (BSTNode) current.left_node();
               }
               else
               {
                   if(current.right_node() == null)
                   {
                       current.right_node(new BSTNode(keyword, directory) );
                       return "Successfully added";
                   }
                   current = (BSTNode) current.right_node();
               }

           }
       }
       return "there was a problem";
   }



   public String display_in_order()
   /*
   * Name: display_in_order
   * Description: A public wrapper method to display_in_order_rec. Returns a string of
   *              in-ordered elements by keyword.
   * Input: void
   * Output: String
   */
   {
       String return_string = display_in_order_rec(this.root,"");
       return return_string;
   }



   private String display_in_order_rec(Node root, String return_string)
   /*
   * Name: display_in_order_rec
   * Description: A recursive private method to build a string of inordered elements
   * Input: Node, String
   * Output: String
   */
   {
       String rs = "";
       if (root != null)
       {
           if (root.left_node() != null)
           {
               rs += display_in_order_rec(root.left_node(), return_string);
           }
           rs += root.display();
           if (root.right_node() != null)
           {
               rs += display_in_order_rec(root.right_node(), return_string);
           }

       }
       return rs;
   }



   public String find_directory(String keyword)
   /*
   * Name: find_directory
   * Description: Finds a directory by a keyword
   * Input: String
   * Output: String
   */
   {
       Node current = this.root;
       if(current == null)
       {
           return "Unable to find keyword";
       }
       else
       {
           while(current != null)
           {
               if(current.compare(keyword) == 0)
               {
                   return "The directory is " + current.display_directory();
               }
               else if(current.compare(keyword) < 0 )
               {
                   current = current.left_node();
               }
               else if(current.compare(keyword) > 0)
               {
                   current = current.right_node();
               }
           }
           return "Unable to find keyword";
       }
   }



   public String remove_keyword(String keyword)
   /*
   * Name: remove_keyword
   * Description: Removes a keyword from a BST Tree
   * Input: String
   * Output: String (Success message)
   */
   {
       BSTNode current = this.root;
       BSTNode previous = null;
       boolean found = false;

       while (current != null)
       {
           if(current.compare(keyword) < 0)
           {
               previous = current;
               current = (BSTNode) current.left_node();
           }
           else if (current.compare(keyword) > 0)
           {
               previous = current;
               current = (BSTNode) current.right_node();
           }
           else if (current.compare(keyword) == 0)
           {
               found = true;
               break;
           }
       }

       if (found == true)
       {
           if(current.right_node() == null && current.left_node() == null)
           {
               if (previous != null && previous.compare(keyword) < 0)
               {
                   previous.remove_left_leaf();
                   return "Removed Successfully!";
               }
               else if (previous != null && previous.compare(keyword) > 0)
               {
                   previous.remove_right_leaf();
                   return "Removed Successfully!";
               }
               else
               {
                   this.root = null;
                   return "Removed Successfully!";
               }


           }
           else if (current.right_node() != null && current.left_node() == null)
           {
               Node temp =  current.right_node();
               previous.remove_right_leaf();
               previous.right_node(temp);
               return "Removed Successfully!";
           }
           else if (current.right_node() == null && current.left_node() != null)
           {
               Node temp = current.left_node();
               previous.remove_left_leaf();
               previous.left_node(temp);
               return "Removed Successfully!";
           }
           else if (current.right_node() != null && current.left_node() != null)
           {
               Node subtree = current.right_node();
               if (subtree.left_node() == null)
               {
                   current.set_data(subtree);
                   current.right_node(subtree.right_node());
                   subtree = null;
                   return "Removed Successfully!";
               }
               else
               {
                   Node sub_current = subtree;
                   Node sub_previous = null;

                   while (sub_current.left_node() != null)
                   {
                       sub_previous = sub_current;
                       sub_current = sub_current.left_node();
                   }
                   current.set_data(sub_current);
                   sub_previous.right_node(sub_current.right_node());
                   sub_current = null;
                   return "Removed Successfully!";

               }
           }
       }
       return "It appears that keyword does not exist?";
   }

}



abstract class Node
/*
 * Name: Node
 *
 * Synopsis:
 *  See BSTNode - This is an abstract class and should not be initialized
 *
 * Description:
 *  This is an abstract class for Nodes. Right now it only has one derived class.
 *  but could offer a framework for more Tree Nodes.
 *
 * Public Methods:
 *
 *  abstract compare()
 *  Purpose: The derived class should return a value based on how a node compares to a String
 *
 *  abstract display()
 *  Purpose: Displays data content for a node
 *
 *  left_node()
 *  Purpose: Returns lef_node leaf
 *
 *  right_node()
 *  Purpose: Returns right_node leaf
 *
 *  right_node(Node)
 *  Purpose: Sets right node leaf
 *
 *  left_node(Node)
 *  Purpose: Sets left node leaf
 *
 *  display_directory(Node)
 *  Purpose: Displays the directory data member
 *
 *  remove_left_leaf()
 *  Purpose: Sets left leaf to null
 *
 *  remove_right_leaf()
 *  Purpose: Sets right leaf to null
 *
 *  set_data(Node)
 *  Purpose: A copy method to copy data from one Node to another
 */
{
   protected String keyword;
   protected String directory;
   protected Node left;
   protected Node right;

   public abstract int compare(String keyword);
   public abstract String display();

   public Node left_node()
   /*
   * Name: left_node
   * Description: returns this.left
   * Input: void
   * Output: Node
   */
   {
       return this.left;
   }



   public void left_node(Node new_node)
   /*
   * Name: left_node
   * Description: Addes a new node to left
   * Input: Node
   * Output: Void
   */
   {
       this.left = new_node;
   }



   public Node right_node()
   /*
   * Name: right_node
   * Description: Returns this.right
   * Input: Void
   * Output: Node
   */
   {
       return this.right;
   }



   public void right_node(Node new_node)
   /*
   * Name: right_node
   * Description: Adds a new node to right
   * Input: Node
   * Output: Void
   */
   {
       this.right = new_node;
   }



   public String display_directory()
   /*
   * Name: display_directory
   * Description: Returns this.directory
   * Input: Void
   * Output: String
   */
   {
       return this.directory;
   }



   public void remove_left_leaf()
   /*
   * Name: remove_left_leaf
   * Description: Sets this.left to null
   * Input: Void
   * Output: Void
   */
   {
       this.left = null;
   }



   public void remove_right_leaf()
   /*
   * Name: remove_right_leaft
   * Description: Sets this.right to null
   * Input: Void
   * Output: Void
   */
   {
       this.right = null;
   }



   public void set_data(Node copy_from_node)
   /*
   * Name: set_data
   * Description: Copies node data to another
   * Input: Node
   * Output: Void
   */
   {
       this.keyword = copy_from_node.keyword;
       this.directory = copy_from_node.directory;
   }
}



class BSTNode extends Node
/*
 * Name: BSTNode
 *
 * Synopsis:
 *  BSTNode my_bstnode = new BSTNode("Keyword1", "Directory");
 *
 * Description:
 *  This is a BSTNode node class. It inheritants much of it's functionality from Node
 *
 * Public Methods:
 *
 *  compare()
 *  Purpose: Returns a 1, -1, or 0. It compares a keyword to determine if it should be
 *           placed higher or lower in a Tree.
 *
 *
 *  display()
 *  Purpose: Returns a string of the Node data content.
 *
 */
{
   protected BSTNode left;
   protected BSTNode right;

   BSTNode(String keyword, String directory)
   /*
   * Name: BSTNode
   * Description: The constructor
   * Input: Void
   * Output: Void
   */
   {
       this.left = (BSTNode) null;             //Explicitly set left to BSTNode
       this.right = (BSTNode) null;            //Explicitly set right to BSTNode
       this.keyword = keyword;
       this.directory = directory;
   }



   public int compare(String keyword)
   /*
   * Name: compare
   * Description: Returns a 1, -1, or 0. compares current keyword to suppplied keyword
   * Input: String
   * Output: Int
   */
   {
       if(this.keyword.compareToIgnoreCase(keyword) == 0)
       {
           return 0;
       }
       else if (this.keyword.compareToIgnoreCase(keyword) < 0)
       {
           return 1;
       }
       else
       {
           return -1;
       }
   }



   public String display()
   /*
   * Name: display
   * Description: Displays content of the node
   * Input: Void
   * Output: String
   */
   {
       return "Keyword: " + this.keyword + "\nDirectory: " + this.directory + "\n";
   }



}
