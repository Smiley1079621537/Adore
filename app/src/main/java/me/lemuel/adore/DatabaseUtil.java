package me.lemuel.adore;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.umeng.socialize.utils.ContextUtil.getPackageName;

/**
 * author : lemuel
 * time   : 2017/03/23
 */
public class DatabaseUtil {

    private DataBaseHelper helper;
    private Context context;
    private PackageInfo packInfo;


    public DatabaseUtil(Context context) {
        this.context = context;
        helper = new DataBaseHelper(context);
        getAppInfo();

    }

    private void getAppInfo() {
        // 获取packageManager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean copyRawDBToApkDb(Context context, int copyRawDbResId, String apkDbPath,
                                           String dbName, boolean refresh) throws IOException {
        boolean b = false;
        File f = new File(apkDbPath);
        if (!f.exists()) {
            f.mkdirs();
        }
        File dbFile = new File(apkDbPath + dbName);
        b = isDbFileExists(dbFile, refresh);
        if (!b) {
            InputStream is = context.getResources().openRawResource(copyRawDbResId);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                int size;
                byte[] buffer = new byte[1024 * 2];
                OutputStream fos = new FileOutputStream(apkDbPath + entry.getName());
                BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length);
                while ((size = zis.read(buffer, 0, buffer.length)) != -1) {
                    bos.write(buffer, 0, size);
                }
                bos.flush();
                bos.close();
            }
            zis.close();
            is.close();
        }
        return !b;
    }

    /**
     * 检查DB文件是否存在
     *
     * @param f       文件名
     * @param refresh 是否覆盖之前的db文件
     * @return
     */
    private static boolean isDbFileExists(File f, boolean refresh) {
        boolean b = false;
        if (f.exists()) {
            if (refresh) {
                f.delete();
                b = false;
            } else {
                b = true;
            }
        }
        return b;
    }
}
