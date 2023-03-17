package fr.atlasworld.content.datagen;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.*;
public class ZipUtils {
    public static void zipFolderContent(String sourceFolderPath, String destinationZipPath) {
        try {
            FileOutputStream fos = new FileOutputStream(destinationZipPath);
            ZipOutputStream zos = new ZipOutputStream(fos);

            for (File f : new File(sourceFolderPath).listFiles()) {
                if (f.isDirectory()) {
                    addFolderToZip("", f.getPath(), zos);
                } else {
                    addFileToZip(f.getPath(), f.getName(), zos);
                }
            }

            zos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addFileToZip(String filePath, String entryName, ZipOutputStream zos) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        ZipEntry zipEntry = new ZipEntry(entryName.replace("\\", "/"));
        zos.putNextEntry(zipEntry);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) > 0) {
            zos.write(buffer, 0, length);
        }
        zos.closeEntry();
        fis.close();
    }

    private static void addFolderToZip(String parentPath, String folderPath, ZipOutputStream zos) throws IOException {
        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            if (!parentPath.equals("")) {
                parentPath += "/";
            }
            parentPath += folder.getName();
            for (String fileName : folder.list()) {
                addFolderToZip(parentPath, folderPath + "/" + fileName, zos);
            }
        } else {
            addFileToZip(folderPath, parentPath + "/" + folder.getName(), zos);
        }
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

