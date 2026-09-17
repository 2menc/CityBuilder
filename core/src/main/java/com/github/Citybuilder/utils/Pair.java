package com.github.citybuilder.utils;

/**
 * a generic Pair
 * @param <X> the type of the first element
 * @param <Y> the type of the second element
 * implements {@link java.io.Serializable}
 */
public class Pair<X, Y> implements java.io.Serializable {

    /**
     * The first element
     */
    private final X x;

    /**
     * The second element
     */
    private final Y y;

    /**
     * constructor
     * @param x the first element
     * @param y the second element
     */
    public Pair(final X x, final Y y) {
        this.x = x;
        this.y = y;
    }

    /**
     * gets the first element
     * @return the first element
     */
    public X getX() {
		return x;
	}

    /**
     * gets the second element
     * @return the second element
     */
	public Y getY() {
		return y;
	}


}
