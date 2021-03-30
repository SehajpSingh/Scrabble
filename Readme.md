Scrabble 

Author- Sehaj Punit Singh

How to run the Solver.Jar

Solver.Jar can be run from the command line with "java -jar Solver.jar sowpods.txt" where 
sowpods.txt is dictionary file. The board file for the program are stored in the resources
folder known as table.txt, where customized boards can be pasted. 


How to Run GUI-Scrabble.jar

This file can be run from the command line with "java -jar GUI-Scrabble.jar sowpods.txt" The 
customized board can be copied and pasted in the board.txt in the resources folder. 

How to Play the Game: 

There are three colors of tiles on the board Red, Blue and Grey. Blue tiles are the word Multiplier with 
value inside them, Grey tiles are letter multiplier with their value insdie them and red tile are neither
word mult or letter mult. 

The first move has to be made by the human player and the computer player takes its after 
the human player. Human player clicks on the tile in the tray first and the color or selcted
tile will change from red to grey, then click the tile on the board where tile has to be placed. 

The tiles must be placed from left to right in order for ex: if i want to put "Gone" on the board 
the very first letter placed on the board must be "G" then "o" then "n" and "e" in the last. If i put
"one" first and "g" to its left then it will not work. The letters must be strictly placed left to right. 
The tiles must also be placed from top up to bottom down, vertically, as they were placed left to right. 

If the human player swaps the tiles if a valid words can't be made from 
the given letter choice then the computer takes its turn automatically itself. To swap the tiles
first click swap button then click of button to swap the tiles. 

If the wrong tiles are placed on the board and something needs to be changed then click the "clear" button. 



Possible bugs: 

The score calculation for computer is accurate but for human it does not count the 
word mulipliers or letter multipliers.

The wild card may show up in the human tray but it is not handled in the gui program 
so it may create errors 

Human can not trade tiles at very first because computer will not take first turn.

There may be other errors that I have not come across but they might exist.

