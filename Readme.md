Scrabble 

Author- Sehaj Punit Singh

**How to run the Solver.Jar**

> Solver.Jar can be run from the command line with "java -jar Solver.jar sowpods.txt" where 
sowpods.txt is dictionary file. The board file for the program are stored in the resources
folder known as table.txt, where customized boards can be pasted. 


**How to Run GUI-Scrabble.jar**

> This file can be run from the command line with "java -jar GUI-Scrabble.jar sowpods.txt" The 
customized board can be copied and pasted in the board.txt in the resources folder. 

**How to Play the Game:** 

1. There are three colors of tiles on the board Red, Blue and Grey. Blue tiles are the word Multiplier with 
value inside them, Grey tiles are letter multiplier with their value insdie them and red tile are neither
word mult or letter mult. 

2. The first move has to be made by the human player and the computer player takes its after 
the human player. Human player clicks on the tile in the tray first and the color or selected
tile will change from red to grey, then click the tile on the board where tile has to be placed. 

3. The tiles must be placed from left to right in order for ex: if i want to put "Gone" on the board 
the very first letter placed on the board must be "G" then "o" then "n" and "e" in the last. If i put
"one" first and "g" to its left then it will not work. The letters must be strictly placed left to right. 
The tiles must also be placed from top up to bottom down, vertically, as they were placed left to right. 

4. If the human player swaps the tiles if a valid words can't be made from 
the given letter choice then the computer takes its turn automatically itself. To swap the tiles
first click swap button then click of button to swap the tiles.

4. If the wrong tiles are placed on the board and something needs to be changed then click the "clear" button. 

5. Click Pass button if you don't want to take your turn and pass it to the computer but don't do that on veryfirst move 
   
6. Human can not trade tiles at very first move because computer will not take first turn.

**Possible bugs:** 

The wild card may show up in the human tray but it is not handled in the gui program 
so it may create errors

sometimes computer takes it turn but does not show on board because the algorith returns same word as last time so there is nothing to update
on board. this happens once a while.  Its after computer takes vertical move and most likey I have messed up refrences to the objects 
around line 245 of main in the gui. 

The program will not handle if someone has won or not. 

There may be other errors that I have not come across but they might exist.


