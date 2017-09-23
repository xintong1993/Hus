


package student_player;

import hus.HusBoardState;
import hus.HusPlayer;
import hus.HusMove;

import java.util.ArrayList;
import java.util.Random;

import student_player.mytools.MyTools;

/** A Hus player submitted by a student. */
public class StudentPlayer extends HusPlayer {
    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses
     * to associate you with your agent. The constructor should do nothing else.
     */
	boolean first=true;
    public StudentPlayer() {
        super("260523472");
    }

    /**
     * This is the primary method that you need to implement. The
     * ``board_state`` object contains the current state of the game, which your
     * agent can use to make decisions. See the class hus.RandomHusPlayer for
     * another example agent.
     */

    public HusMove chooseMove(HusBoardState board_state) {
        System.out.println("getTurnNumber " + board_state.getTurnNumber());
    	long start= System.currentTimeMillis();
    	long end = start+1996;
    	if (first) { end = (start + 30000-5);first=false;System.out.println("FIRST ");}
        // Get the contents of the pits so we can use it to make decisions.

        // Use code stored in ``mytools`` package.
        // Get the legal moves for the current board state.
        ArrayList<HusMove> moves = board_state.getLegalMoves();
        int move_size = moves.size();
        System.out.println("Xia move size: " + moves.size());
        //HusMove move = moves.get(0);

        /////////////////////////////////////// Minimax with a b
        int a = -10000;
        int b = 10000;
        int maximum = -10000;
        int res = 0;
        HusMove res_move = null;

        start=System.currentTimeMillis();
        double avr=(end-start+0.0)/move_size;
        for (HusMove m : moves) { // for each legal move for current board state
            HusBoardState cloned_bs = (HusBoardState) board_state.clone();
            cloned_bs.move(m); // get the new board under the move m
            
            	res = Minimax_ab(cloned_bs, a, b, start+=avr);
           

            if (res > maximum) {
                maximum = res;
                res_move = m;
            }
        }
        return res_move;
    }

    public int Minimax_ab(HusBoardState state, int a, int b, double end) {
        ArrayList<HusMove> next_moves = state.getLegalMoves();
        // If N is deep enough then
        if (System.currentTimeMillis()>end) {
            // Get the contents of the pits so we can use it to make decisions.
            int[][] pits = state.getPits();
            // Use ``player_id`` and ``opponent_id`` to get my pits and
            // opponentpits.
            int[] my_pits = pits[player_id];
            int sum = 0;
            for (int i = 0; i < my_pits.length; i++) {
                sum += my_pits[i];
            }
            return sum; // return the estimated score of this leaf
        } else {
            int alpha = a;
            int beta = b;
            long start=System.currentTimeMillis();
            double avr=(end-start)/next_moves.size();
            if (state.getTurnPlayer() != player_id) {
                // if N is a Min node then
                // For each successor Ni of N
                for (HusMove m : next_moves) {
                    HusBoardState cloned_state = (HusBoardState) state.clone();
                    cloned_state.move(m);
                    beta = (int) Math.min(beta, Minimax_ab(cloned_state, alpha, beta, start+=avr));
                    if (alpha >= beta) {
                        return alpha;
                    }
                }
                return beta;
            } else {
                for (HusMove m : next_moves) {
                    HusBoardState cloned_state = (HusBoardState) state.clone();
                    cloned_state.move(m);
                    alpha = (int) Math.max(alpha, Minimax_ab(cloned_state, alpha, beta, start+=avr));
                    if (alpha >= beta) {
                        return beta;
                    }
                }
                return alpha;
            }
        }

    }

}


