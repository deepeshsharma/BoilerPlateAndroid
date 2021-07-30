package com.deep.app.network.cache;

import android.content.Context;

import com.deep.app.util.IOUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DiskLruCache implements ICache<String> {
    private static final int APP_VERSION = 1;
    private static final int VALUE_COUNT = 1;
    private static final int IO_BUFFER_SIZE = 8 * 1024;

    private com.jakewharton.disklrucache.DiskLruCache mDiskCache;

    public DiskLruCache(Context context, String uniqueName, int diskCacheSize) {
        try {
            final File diskCacheDir = getDiskCacheDir(context, uniqueName);
            mDiskCache = com.jakewharton.disklrucache.DiskLruCache.open(diskCacheDir, APP_VERSION, VALUE_COUNT, diskCacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeValueToFile(String value, com.jakewharton.disklrucache.DiskLruCache.Editor editor) throws IOException {
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(editor.newOutputStream(0), IO_BUFFER_SIZE);
            IOUtils.write(value, out);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    private File getDiskCacheDir(Context context, String uniqueName) {
        final String cachePath = context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    @Override
    public String getCachedData(String key) {
        String value = null;
        com.jakewharton.disklrucache.DiskLruCache.Snapshot snapshot = null;
        InputStream in = null;
        try {

            snapshot = mDiskCache.get(key);
            if (snapshot == null) {
                return null;
            }
            in = snapshot.getInputStream(0);
            if (in != null) {
                value = IOUtils.toString(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(snapshot, in);
        }
        return value;
    }

    @Override
    public void putCachedData(String key, String value) {
        com.jakewharton.disklrucache.DiskLruCache.Editor editor = null;
        try {
            editor = mDiskCache.edit(key);
            if (editor == null) {
                return;
            }

            writeValueToFile(value, editor);
            mDiskCache.flush();
            editor.commit();
        } catch (IOException e) {
            try {
                if (editor != null) {
                    editor.abort();
                }
            } catch (IOException ignored) {
            }
        }
    }

    public boolean containsKey(String key) {
        boolean contained = false;
        com.jakewharton.disklrucache.DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = mDiskCache.get(key);
            contained = snapshot != null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (snapshot != null) {
                snapshot.close();
            }
        }

        return contained;
    }

    public void clearCache() {
        try {
            mDiskCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getCacheFolder() {
        return mDiskCache.getDirectory();
    }
}
