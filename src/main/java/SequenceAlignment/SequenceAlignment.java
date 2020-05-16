package SequenceAlignment;

/**
 *  SequenceAlignment is:
 *      Given two strings X and Y of m and n characters over an alphabet,
 *      and a cost matrix for matching each character to another character
 *      of the alphabet, what is an alignment of X with Y such that the alignment
 *      results in minimum cost as described by matrix?
 *
 *      The following must be true in the optimal solution. Either the last characters are
 *      matched or not. That is,
 *      either
 *          x_m and y_n are matched,
 *      or
 *          x_m is unmatched <i>or</i> y_n is unmatched.
 *
 *      This suggests a DP solution, where we consider the subproblem of matching fro i=[1:n]
 *      and j=[1:m]. Either align (i,j) then look up optimal way to match substrings X[1:i-1]
 *      and Y[1:j-1], or leave i unmatched (X[1:i-1],Y[1:j]) or leave j unmatched (X[1:i], Y[1:j-1]).
 *
 *      For each of the three decisions we pay the appropriate cost, then minimize over all three.
 *
 */
public class SequenceAlignment {

    // TODO. Get strings X,Y, and cost matrix for alphabeta matching.
}
