package com.bee32.plover.util;

import java.io.IOException;
import java.io.Writer;
import java.nio.CharBuffer;
import java.util.Locale;

import javax.free.BCharOut;
import javax.free.IIndentedOut;
import javax.free.ITextIndention;
import javax.free.IndentedOutImpl;

public class PrettyPrintStream
        implements Appendable, IIndentedOut {

    private BCharOut buf;
    private IndentedOutImpl out;

    public PrettyPrintStream() {
        buf = new BCharOut();
        out = new IndentedOutImpl(buf);
    }

    @Override
    public Appendable append(CharSequence csq)
            throws IOException {
        print(csq.toString());
        return this;
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end)
            throws IOException {
        String s = csq.subSequence(start, end).toString();
        print(s);
        return this;
    }

    @Override
    public Appendable append(char c)
            throws IOException {
        print(c);
        return this;
    }

    @Override
    public int enter() {
        return out.enter();
    }

    @Override
    public int leave() {
        return out.leave();
    }

    @Override
    public void print(boolean b) {
        out.print(b);
    }

    @Override
    public void print(char c) {
        out.print(c);
    }

    @Override
    public void print(char[] s) {
        out.print(s);
    }

    @Override
    public void print(double d) {
        out.print(d);
    }

    @Override
    public void print(float f) {
        out.print(f);
    }

    @Override
    public void print(int i) {
        out.print(i);
    }

    @Override
    public void print(long l) {
        out.print(l);
    }

    @Override
    public void print(Object... args) {
        out.print(args);
    }

    @Override
    public void print(Object obj) {
        out.print(obj);
    }

    @Override
    public void print(String s) {
        out.print(s);
    }

    @Override
    public void printf(Locale l, String format, Object... args) {
        out.printf(l, format, args);
    }

    @Override
    public void printf(String format, Object... args) {
        out.printf(format, args);
    }

    @Override
    public void println() {
        out.println();
    }

    @Override
    public void println(boolean x) {
        out.println(x);
    }

    @Override
    public void println(char x) {
        out.println(x);
    }

    @Override
    public void println(char[] x) {
        out.println(x);
    }

    @Override
    public void println(double x) {
        out.println(x);
    }

    @Override
    public void println(float x) {
        out.println(x);
    }

    @Override
    public void println(int x) {
        out.println(x);
    }

    @Override
    public void println(long x) {
        out.println(x);
    }

    @Override
    public void println(Object... args) {
        out.println(args);
    }

    @Override
    public void println(Object x) {
        out.println(x);
    }

    @Override
    public void println(String x) {
        out.println(x);
    }

    public Writer toWriter() {
        return out.toWriter();
    }

    @Override
    public void write(char[] chars, int off, int len)
            throws IOException {
        out.write(chars, off, len);
    }

    @Override
    public void write(char[] chars)
            throws IOException {
        out.write(chars);
    }

    @Override
    public void write(CharBuffer charBuffer)
            throws IOException {
        out.write(charBuffer);
    }

    @Override
    public void write(CharSequence chars, int off, int len)
            throws IOException {
        out.write(chars, off, len);
    }

    @Override
    public void write(int c)
            throws IOException {
        out.write(c);
    }

    @Override
    public void write(String string, int off, int len)
            throws IOException {
        out.write(string, off, len);
    }

    @Override
    public void write(String s)
            throws IOException {
        out.write(s);
    }

    @Override
    public void checkError(boolean arg0)
            throws IOException {
        out.checkError(arg0);
    }

    @Override
    public final void close() {
        out.close();
    }

    @Override
    public final void flush() {
        out.flush();
    }

    @Override
    public final void flush(boolean arg0) {
        out.flush(arg0);
    }

    @Override
    public ITextIndention getTextIndention() {
        return out.getTextIndention();
    }

    @Override
    public String toString() {
        return buf.toString();
    }

}
