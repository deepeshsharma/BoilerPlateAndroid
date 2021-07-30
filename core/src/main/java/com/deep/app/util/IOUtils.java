package com.deep.app.util;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

public final class IOUtils {
    public static final int EOF = -1;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 8;

    private IOUtils() {
    }

    public static String toString(final InputStream input) throws IOException {
        return toString(input, Charset.defaultCharset());
    }

    public static String toString(final InputStream input, final Charset encoding) throws IOException {
        final StringBuilderWriter sw = new StringBuilderWriter();
        copy(input, sw, encoding);
        return sw.toString();
    }

    public static void copy(final InputStream input, final Writer output, final Charset inputEncoding) throws IOException {
        final InputStreamReader in = new InputStreamReader(input);
        copy(in, output);
    }

    public static int copy(final Reader input, final Writer output) throws IOException {
        final long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    public static void copy(InputStream input, FileOutputStream output) {
        try {
            try {
                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE]; // or other buffer size
                int read;

                while ((read = input.read(buffer)) != -1) {
                    output.write(buffer, 0, read);
                }
                output.flush();
            } finally {
                output.close();
            }
        } catch (Exception e) {
            e.printStackTrace(); // handle exception, define IOException and others
        }
    }

    public static long copyLarge(final Reader input, final Writer output) throws IOException {
        return copyLarge(input, output, new char[DEFAULT_BUFFER_SIZE]);
    }

    public static long copyLarge(final Reader input, final Writer output, final char[] buffer) throws IOException {
        long count = 0;
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static void closeQuietly(final Reader input) {
        closeQuietly((Closeable) input);
    }

    public static void closeQuietly(final Writer output) {
        closeQuietly((Closeable) output);
    }

    public static void closeQuietly(final InputStream input) {
        closeQuietly((Closeable) input);
    }

    public static void closeQuietly(final OutputStream output) {
        closeQuietly((Closeable) output);
    }

    public static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }

    public static void closeQuietly(final Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (final Closeable closeable : closeables) {
            closeQuietly(closeable);
        }
    }

    public static void write(final byte[] data, final OutputStream output) throws IOException {
        if (data != null) {
            output.write(data);
        }
    }

    public static void writeChunked(final byte[] data, final OutputStream output) throws IOException {
        if (data != null) {
            int bytes = data.length;
            int offset = 0;
            while (bytes > 0) {
                int chunk = Math.min(bytes, DEFAULT_BUFFER_SIZE);
                output.write(data, offset, chunk);
                bytes -= chunk;
                offset += chunk;
            }
        }
    }

    public static void write(final String data, final OutputStream output) throws IOException {
        write(data, output, Charset.defaultCharset());
    }

    public static void write(final String data, final OutputStream output, final Charset encoding) throws IOException {
        if (data != null) {
            output.write(data.getBytes(encoding));
        }
    }
}
