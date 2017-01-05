# game-of-life_toroidal
An implementation of John Conway's classic cellular automata, The Game of Life, 
written in Java. Included with the repository are a handful of predefined game
states that exhbit interesting behavior. Of note, the b52bomber.start provides
a fascinating "game" of life.

This program takes a configuration file that defines characteristics of the game,
namely the ASCII representation of dead and live cells, as well as the manner in
which the game board is evaluated. "n"ormal mode wraps the board from left to
right only, while "t"oroidal mode will wrap both the left to the right and top
to the bottom.

