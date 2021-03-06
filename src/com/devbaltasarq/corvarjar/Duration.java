// CorvarJar (c) 2020 Baltasar MIT License <jbgarcia@uvigo.es>


package com.devbaltasarq.corvarjar;


import java.util.Locale;


/** Represents duration in minutes and seconds. */
public class Duration {
    public static final String FIELD = "time";
    public enum TimeUnit { Seconds, Minutes }

    /** Creates a new duration, given time in seconds.
      * @param secs The duration time, in seconds.
      */
    public Duration(int secs)
    {
        this.secs = secs;
    }

    /** Creates a new duration, given time in minutes and seconds.
      * @param min The duration time, in minutes.
      * @param secs The duration time, in seconds.
      */
    public Duration(int min, int secs)
    {
        this.secs = (min * 60) + secs;
    }

    /** @return the same duration in here, but in another object. */
    public Duration copy()
    {
        return new Duration( this.secs );
    }

    /** Creates a new duration, given time in minutes and seconds.
     * @param min The duration time, in minutes.
     */
    public Duration(float min)
    {
        this.secs = ( (int) ( 60.0 * min ) );
    }

    @Override
    public int hashCode()
    {
        return Integer.valueOf( this.secs ).hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        boolean toret = false;

        if ( o instanceof Duration ) {
            final Duration dot = (Duration) o;

            toret = ( this.getTimeInSeconds() == dot.getTimeInSeconds() );
        }

        return toret;
    }

    /** Sets this duration to a given number of seconds.
      * @param secs the new number of seconds.
      * @return itself.
      */
    public Duration set(int secs)
    {
        this.secs = secs;
        return this;
    }

    /** Adds a number of seconds to this duration.
      * @param secs the number of seconds to add.
      * @return itself.
      */
    public Duration add(int secs)
    {
        this.secs += secs;
        return this;
    }

    /** @return The whole time in seconds. */
    public int getTimeInSeconds()
    {
        return this.secs;
    }

    /** @return The time in seconds, once minutes discounted. */
    public int getSeconds()
    {
        return this.secs % 60;
    }

    /** @return The time in minutes. */
    public int getMinutes()
    {
        return this.secs / 60;
    }

    /** Parses the time as the user enters it.
     * @param mode 0 for seconds, 1 for minutes.
     * @param txt The text for the value in seconds or minutes.
     */
    public void parse(int mode, String txt) throws NumberFormatException
    {
        if ( mode < 0
          || mode >= TimeUnit.values().length )
        {
            mode = 0;
        }

        parse( TimeUnit.values()[ mode ], txt );
    }

    /** Parses the time as the user enters it.
     * @param mode Basically, 0 for seconds, 1 for minutes.
     * @param txt The text for the value in seconds or minutes.
     * @see TimeUnit
     */
    public void parse(TimeUnit mode, String txt) throws NumberFormatException
    {
        float timeValue = Float.parseFloat( txt );

        if ( mode == TimeUnit.Seconds ) {
            this.secs = (int) timeValue;
        }
        else
        if ( mode == TimeUnit.Minutes ) {
            this.secs = ( (int) (60 * timeValue) );
        }
    }

    /** @return the duration in the format 00:00, minutes and secs. */
    public String toChronoString()
    {
        return String.format (Locale.getDefault(),
                "%02d:%02d", this.getMinutes(), this.getSeconds() );
    }

    @Override
    public String toString()
    {
        int minutes = this.getMinutes();
        String toret = String.format( Locale.getDefault(), "%2d\"", this.getTimeInSeconds() );

        if ( minutes > 0 ) {
            toret = String.format( Locale.getDefault(), "%2d'%02d\"", this.getMinutes(), this.getSeconds() );
        }

        return toret;
    }

    private int secs;
}
