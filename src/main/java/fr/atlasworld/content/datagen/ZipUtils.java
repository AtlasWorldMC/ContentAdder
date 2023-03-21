package fr.atlasworld.content.datagen;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.zip.*;
public class ZipUtils {
    //Big thanks to Malfrador#0923 (https://github.com/Malfrador) for the working zip code!
    public static void generateZip(String srcFolder, String out) {
        try {
            System.out.println(srcFolder);
            File file = new File(srcFolder);
            FileOutputStream fileOutputStream = new FileOutputStream(out);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            File[] arrayOfFile = file.listFiles();
            if (arrayOfFile == null)
                return;
            System.out.println(Arrays.toString(arrayOfFile));
            for (File file1 : arrayOfFile)
                zipFile(file1, file1.getName(), zipOutputStream);
            zipOutputStream.close();
            fileOutputStream.close();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    //Big thanks to Malfrador#0923 (https://github.com/Malfrador) for the working zip code!
    private static void zipFile(File paramFile, String paramString, ZipOutputStream paramZipOutputStream) throws IOException {
        if (paramFile.isHidden())
            return;
        if (paramFile.isDirectory()) {
            if (paramString.endsWith("/")) {
                paramZipOutputStream.putNextEntry(new ZipEntry(paramString));
            } else {
                paramZipOutputStream.putNextEntry(new ZipEntry(paramString + "/"));
            }
            paramZipOutputStream.closeEntry();
            File[] arrayOfFile = paramFile.listFiles();
            if (arrayOfFile != null)
                for (File file : arrayOfFile) {
                    zipFile(file, paramString + "/" + file.getName(), paramZipOutputStream);
                }
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(paramFile);
        ZipEntry zipEntry = new ZipEntry(paramString);
        paramZipOutputStream.putNextEntry(zipEntry);
        byte[] arrayOfByte = new byte[1024];
        int i;
        while ((i = fileInputStream.read(arrayOfByte)) >= 0)
            paramZipOutputStream.write(arrayOfByte, 0, i);
        fileInputStream.close();
    }

    public static byte[] getZipHash(String filePath) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
            md.update(fileBytes);
            return md.digest();
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

