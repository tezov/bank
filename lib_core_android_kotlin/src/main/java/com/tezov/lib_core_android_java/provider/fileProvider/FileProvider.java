/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_android_java.provider.fileProvider;
//
//import static android.content.ContentResolver.SCHEME_CONTENT;
//import static android.content.ContentResolver.SCHEME_FILE;
//import static android.os.Environment.*;
//
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//
//import java.io.File;
//
//public class FileProvider extends androidx.core.content.FileProvider{
//    public static final String AUTHORITY = ".provider";
//    public static String getAuthority(String packageName){
//        return packageName + FileProvider.AUTHORITY;
//    }
//    public static boolean isAuthorityMe(String packageName, String authority){
//        return getAuthority(packageName).equals(authority);
//    }
//
//    public static Uri makeExportable(Uri uri){
//        if(SCHEME_FILE.equals(uri.getScheme())){
//            java.io.File file = new java.io.File(uri.getPath());
//            return uriFromFile(file);
//        }
//        else if(SCHEME_CONTENT.equals(uri.getScheme())){
//            return uri;
//        }
//        return null;
//    }
//    public static Uri uriFromFile(Context context, File file){
//        return getUriForFile(context, getAuthority(context.getPackageName()), file);
//    }
//    public static File fileFromUri(Uri uri){
//        if(VersionSDK.isSupEqualTo30_R()){
//            DebugException.start().log("version sdk >30, should not be possible").end();
//            return null;
//        }
//        String path = uri.getPath();
//        if(path.startsWith(Directory.PATH_SEPARATOR)){
//            path = path.substring(1);
//        }
//        int indexOfSep = path.indexOf(Directory.PATH_SEPARATOR);
//        if(indexOfSep == NULL_INDEX){
//            DebugException.start().log("invalid path").end();
//            return null;
//        }
//        String root = path.substring(0, indexOfSep);
//        boolean isRootValid = DIRECTORY_DOCUMENTS.equals(root) || DIRECTORY_PICTURES.equals(root)
//                || DIRECTORY_MOVIES.equals(root) || DIRECTORY_MUSIC.equals(root);
//        if(!isRootValid){
//            DebugException.start().log("invalid root").end();
//            return null;
//        }
//        path = StorageMedia.getPublicDirectory_before30_R("/") + path;
//        DebugLog.start().send(path).end();
//        return new File(path);
//    }
//
//    public static Uri permissionGrantRead(String packageName, Uri uri){
//        int flag = Intent.FLAG_GRANT_READ_URI_PERMISSION;
//        AppContext.get().grantUriPermission(packageName, uri, flag);
//        return uri;
//    }
//    public static Uri permissionRevokeRead(String packageName, Uri uri){
//        int flag = Intent.FLAG_GRANT_READ_URI_PERMISSION;
//        if(VersionSDK.isSupEqualTo26_OREO()){
//            AppContext.get().revokeUriPermission(packageName, uri, flag);
//        } else {
//            AppContext.get().revokeUriPermission(uri, flag);
//        }
//        return uri;
//    }
//    public static Uri permissionGrantWrite(String packageName, Uri uri){
//        int flag = Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
//        AppContext.get().grantUriPermission(packageName, uri, flag);
//        return uri;
//    }
//    public static Uri permissionRevokeWrite(String packageName, Uri uri){
//        int flag = Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
//        if(VersionSDK.isSupEqualTo26_OREO()){
//            AppContext.get().revokeUriPermission(packageName, uri, flag);
//        } else {
//            AppContext.get().revokeUriPermission(uri, flag);
//        }
//        return uri;
//    }
//    public static Uri permissionRevokeWriteAndRead(String packageName, Uri uri){
//        int flag = Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
//        if(VersionSDK.isSupEqualTo26_OREO()){
//            AppContext.get().revokeUriPermission(packageName, uri, flag);
//        } else {
//            AppContext.get().revokeUriPermission(uri, flag);
//        }
//        return uri;
//    }
//
//}
