package com.bee32.plover.util;

import java.io.IOException;
import java.io.Writer;
import java.nio.CharBuffer;
import java.util.Locale;

import javax.free.BCharOut;
import javax.free.IndentedOutImpl;

public class PrettyPrintStream
        implements Appendable {

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

    public int enter() {
        return out.enter();
    }

    public int leave() {
        return out.leave();
    }

    public void print(boolean b) {
        out.print(b);
    }

    public void print(char c) {
        out.print(c);
    }

    public void print(char[] s) {
        out.print(s);
    }

    public void print(double d) {
        out.print(d);
    }

    public void print(float f) {
        out.print(f);
    }

    public void print(int i) {
        out.print(i);
    }

    public void print(long l) {
        out.print(l);
    }

    public void print(Object... args) {
        out.print(args);
    }

    public void print(Object obj) {
        out.print(obj);
    }

    public void print(String s) {
        out.print(s);
    }

    public void printf(Locale l, String format, Object... args) {
        out.printf(l, format, args);
    }

    public void printf(String format, Object... args) {
        out.printf(format, args);
    }

    public void println() {
        out.println();
    }

    public void println(boolean x) {
        out.println(x);
    }

    public void println(char x) {
        out.println(x);
    }

    public void println(char[] x) {
        out.println(x);
    }

    public void println(double x) {
        out.println(x);
    }

    public void println(float x) {
        out.println(x);
    }

    public void println(int x) {
        out.println(x);
    }

    public void println(long x) {
        out.println(x);
    }

    public void println(Object... args) {
        out.println(args);
    }

    public void println(Object x) {
        out.println(x);
    }

    public void println(String x) {
        out.println(x);
    }

    public Writer toWriter() {
        return out.toWriter();
    }

    public void write(char[] chars, int off, int len)
            throws IOException {
        out.write(chars, off, len);
    }

    public void write(char[] chars)
            throws IOException {
        out.write(chars);
    }

    public void write(CharBuffer charBuffer)
            throws IOException {
        out.write(charBuffer);
    }

    public void write(CharSequence chars, int off, int len)
            throws IOException {
        out.write(chars, off, len);
    }

    public void write(int c)
            throws IOException {
        out.write(c);
    }

    public void write(String string, int off, int len)
            throws IOException {
        out.write(string, off, len);
    }

    public void write(String s)
            throws IOException {
        out.write(s);
    }

    public String toString() {
        return buf.toString();
    }

}
