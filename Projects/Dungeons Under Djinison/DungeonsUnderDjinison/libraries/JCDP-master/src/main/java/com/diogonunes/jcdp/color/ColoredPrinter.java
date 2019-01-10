package com.diogonunes.jcdp.color;

import com.diogonunes.jcdp.color.api.AbstractColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi.Attribute;
import com.diogonunes.jcdp.color.api.Ansi.BColor;
import com.diogonunes.jcdp.color.api.Ansi.FColor;
import com.diogonunes.jcdp.color.api.IColoredPrinter;
import com.diogonunes.jcdp.color.impl.UnixColoredPrinter;
import com.diogonunes.jcdp.color.impl.WindowsColoredPrinter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * If you want to create a Colored Printer this is the only class you should
 * use. This is your Colored Printers Factory and it abstracts the creation of a
 * Colored Printer and its real implementation. It offers two types of
 * constructors, one for static and other for dynamic printers. If you use the
 * static constructor you will use one implementation offered by the library. If
 * you use the dynamic constructor you must pass as argument an instance of any
 * class that implements {@link IColoredPrinter} interface.
 *
 * @author Diogo Nunes
 * @version 2.0
 */
public class ColoredPrinter implements IColoredPrinter, AutoCloseable {

    // object with printer's implementation
    private AbstractColoredPrinter _impl;

    // ===========================
    // CONSTRUCTORS and BUILDERS
    // ===========================

    /**
     * Constructor of dynamic printers.
     *
     * @param implementation of {@link IColoredPrinter}
     */
    public ColoredPrinter(AbstractColoredPrinter implementation) {
        setImpl(implementation);
    }

    /**
     * @param b Builder with the desired configurations for the new printers.
     */
    public ColoredPrinter(Builder b) {
        if (System.getProperty("os.name").toLowerCase().startsWith("win"))
            setImpl(new WindowsColoredPrinter.Builder(b._level, b._timestampFlag).withFormat(b._dateFormat)
                    .attribute(b._attribute).foreground(b._foregroundColor).background(b._backgroundColor).build());
        else
            setImpl(new UnixColoredPrinter.Builder(b._level, b._timestampFlag).withFormat(b._dateFormat)
                    .attribute(b._attribute).foreground(b._foregroundColor).background(b._backgroundColor).build());
    }

    /**
     * Builder pattern: allows the caller to specify the attributes that it
     * wants to change and keep the default values in the others.
     */
    public static class Builder {
        // required parameters
        private int _level;
        private boolean _timestampFlag;
        // optional parameters - initialized to default values
        private Attribute _attribute = Attribute.NONE;
        private FColor _foregroundColor = FColor.NONE;
        private BColor _backgroundColor = BColor.NONE;
        private DateFormat _dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        /**
         * The Colored Printer created, by default, has no format. It comes with
         * timestamping active and zero level of debug.
         *
         * @param level  specifies the maximum level of debug this printer can
         *               print.
         * @param tsFlag true, if you want a timestamp before each message.
         */
        public Builder(int level, boolean tsFlag) {
            _level = level;
            _timestampFlag = tsFlag;
        }

        /**
         * @param level specifies the maximum level of debug this printer can
         *              print.
         * @return the builder.
         */
        public Builder level(int level) {
            this._level = level;
            return this;
        }

        /**
         * @param flag true, if you want a timestamp before each message.
         * @return the builder.
         */
        public Builder timestamping(boolean flag) {
            this._timestampFlag = flag;
            return this;
        }

        /**
         * @param df the printing format of the timestamp.
         * @return the builder.
         */
        public Builder withFormat(DateFormat df) {
            this._dateFormat = df;
            return this;
        }

        /**
         * @param attr specifies the attribute component of the printing format.
         * @return the builder.
         */
        public Builder attribute(Attribute attr) {
            this._attribute = attr;
            return this;
        }

        /**
         * @param fg specifies the foreground color of the printing format.
         * @return the builder.
         */
        public Builder foreground(FColor fg) {
            this._foregroundColor = fg;
            return this;
        }

        /**
         * @param bg specifies the background color of the printing format.
         * @return the builder.
         */
        public Builder background(BColor bg) {
            this._backgroundColor = bg;
            return this;
        }

        /**
         * @return a new instance of a Colored Printer.
         */
        public ColoredPrinter build() {
            return new ColoredPrinter(this);
        }

    }

    // =====================
    // GET and SET METHODS
    // =====================

    private AbstractColoredPrinter getImpl() {
        return _impl;
    }

    private void setImpl(AbstractColoredPrinter impl) {
        _impl = impl;
    }

    // =======================================
    // INTERFACE METHODS call implementation
    // =======================================

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLevel() {
        return getImpl().getLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLevel(int level) {
        getImpl().setLevel(level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDateFormatted() {
        return getImpl().getDateFormatted();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getDate() {
        return new Date();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printTimestamp() {
        getImpl().printTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printErrorTimestamp() {
        getImpl().printTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(Object msg) {
        getImpl().print(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void println(Object msg) {
        getImpl().println(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void errorPrint(Object msg) {
        getImpl().errorPrint(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void errorPrintln(Object msg) {
        getImpl().errorPrintln(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrint(Object msg) {
        getImpl().debugPrint(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrint(Object msg, int level) {
        getImpl().debugPrint(msg, level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrintln(Object msg) {
        getImpl().debugPrintln(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrintln(Object msg, int level) {
        getImpl().debugPrintln(msg, level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getImpl().toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAttribute(Attribute attr) {
        getImpl().setAttribute(attr);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setForegroundColor(FColor foreground) {
        getImpl().setForegroundColor(foreground);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBackgroundColor(BColor background) {
        getImpl().setBackgroundColor(background);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        getImpl().clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateCode() {
        return getImpl().generateCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateCode(Attribute attr, FColor foreground, BColor background) {
        return getImpl().generateCode(attr, foreground, background);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(Object msg, Attribute attr, FColor fg, BColor bg) {
        getImpl().print(msg, attr, fg, bg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void println(Object msg, Attribute attr, FColor fg, BColor bg) {
        getImpl().println(msg, attr, fg, bg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void errorPrint(Object msg, Attribute attr, FColor fg, BColor bg) {
        getImpl().errorPrint(msg, attr, fg, bg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void errorPrintln(Object msg, Attribute attr, FColor fg, BColor bg) {
        getImpl().errorPrintln(msg, attr, fg, bg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrint(Object msg, Attribute attr, FColor fg, BColor bg) {
        getImpl().debugPrint(msg, attr, fg, bg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrint(Object msg, int level, Attribute attr, FColor fg, BColor bg) {
        getImpl().debugPrint(msg, level, attr, fg, bg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrintln(Object msg, Attribute attr, FColor fg, BColor bg) {
        getImpl().debugPrintln(msg, attr, fg, bg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debugPrintln(Object msg, int level, Attribute attr, FColor fg, BColor bg) {
        getImpl().debugPrintln(msg, level, attr, fg, bg);
    }
    
    /**
     * This method is called through the java.lang.AutoCloseable interface. It is called
     * when the garbage collector of the JVM determines that this object no longer has any
     * references being made to it. It is usually used for streams that must be closed in
     * order to assure that the data has made it's way through the stream.
     * <p>
     * This method removes the need to call {@linkplain #clear()}. Do not call this method.
     * 
     * @author CodeDojo
     */
    @Override
    public void close() {
        getImpl().clear();
    }
}
