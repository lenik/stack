package com.bee32.plover.disp.util;

public interface ITokenQueue {

    String INDEX = ""; // "<<index>>";

    /**
     * Get available token count of the rest.
     *
     * @return Count of the rest tokens.
     */
    int available();

    /**
     * Test if the queue is empty.
     *
     * @return <code>true</code> If the queue is empty.
     */
    boolean isEmpty();

    /**
     * Skip a number of tokens.
     *
     * @param n
     *            Number of tokens to skip.
     */
    void skip(int n);

    /**
     * Shift out the head token.
     *
     * A trailing slash ('/') should be translated into {@link #INDEX}.
     *
     * @return <code>null</code> If no more token.
     */
    String shift();

    /**
     * Shift out the head token as an int.
     *
     * If the head token isn't int, shift doesn't happen and <code>null</code> is returned.
     *
     * @return <code>null</code> If no more token, or the head token isn't an int number.
     */
    Integer shiftInt();

    /**
     * Shift out the head token as a long.
     *
     * If the head token isn't long, shift doesn't happen and <code>null</code> is returned.
     *
     * @return <code>null</code> If no more token, or the head token isn't a long number.
     */
    Long shiftLong();

    /**
     * Shift out the head token.
     *
     * @return <code>null</code> If no more token.
     */
    String peek();

    /**
     * Peek at the n-th token from the head.
     *
     * @return <code>null</code> If the token doesn't exist.
     */
    String peek(int offset);

    /**
     * Peek at the head token as integer.
     *
     * @return <code>null</code> If no more token, or the head token isn't a int number.
     */
    Integer peekInt();

    /**
     * Peek at the n-th token from the head as int.
     *
     * @return <code>null</code> If the token doesn't exist, or it's not a int integer.
     */
    Integer peekInt(int offset);

    /**
     * Peek at the head token as long.
     *
     * @return <code>null</code> If no more token, or the head token isn't a long number.
     */
    Long peekLong();

    /**
     * Peek at the n-th token from the head as long.
     *
     * @return <code>null</code> If the token doesn't exist, or it's not a long integer.
     */
    Long peekLong(int offset);

}
