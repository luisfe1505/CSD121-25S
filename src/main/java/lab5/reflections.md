I didn’t have to modify main or TicTacToeGame when I added both Randy
and Omola because they both inherit from the same abstract Player class with its single method 
pickNextMove(Board). Each subclass like Randy with its Random logic or Omola with its win‑and‑block look‑ahead, 
just implements that contract,and in the game loop I call currentPlayer.pickNextMove(board) once, 
trusting on Java’s dynamic dispatch to choose the right behavior. That keeps the engine closed to modification 
but open to extension , lets me swap in any Player subclass safely  and maintains low coupling since the game 
only depends on the Player abstraction, not on any concrete implementation.