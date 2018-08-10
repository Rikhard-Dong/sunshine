package com.hfmes.sunshine.utils;

import java.io.*;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 13:09
 * <p>
 * 序列化工具
 */
public class SerializeUtils {
    private static final String encoding = "ISO-8859-1";

    public static String serializeObject2String(Object obj) {
        String restult = null;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
//            byte[] buff = baos.toByteArray();
//
//            BASE64Encoder encoder = new BASE64Encoder();
//            retsult = encoder.encode(buff);

            restult = baos.toString(encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return restult;
    }

    public static Object String2Object(String str) {
        Object object = null;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes(encoding));
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            object = ois.readObject();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return object;
    }

    /**
     * 反序列化对象
     *
     * @param bytes
     * @return
     */
    public static Object String2Object(byte[] bytes) {
        Object object = null;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             DataInputStream dis = new DataInputStream(bais);
             ObjectInputStream ois = new ObjectInputStream(dis)) {
            object = ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return object;
    }

    public static void serializeObject2File(Object obj, String filename) {
        File file = new File(filename);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
