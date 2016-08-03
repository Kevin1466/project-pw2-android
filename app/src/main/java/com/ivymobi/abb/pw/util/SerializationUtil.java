package com.ivymobi.abb.pw.util;

import android.content.Context;

import com.ivymobi.abb.pw.network.CachedFileEnum;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by renguangkai on 2016/7/22.
 */
public class SerializationUtil {
    /**
     * 序列化--保存对象
     * @param name
     * @param sobj
     */
    public static void saveObject(Context ctx, String name, Serializable sobj) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = ctx.openFileOutput(name, ctx.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(sobj);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 反序列化--获取对象
     * @param name
     * @param <T>
     * @return
     */
    public static  <T> T getObject(Context ctx, String name) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = ctx.openFileInput(name);
            ois = new ObjectInputStream(fis);
            return (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    //ois流关闭异常
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
