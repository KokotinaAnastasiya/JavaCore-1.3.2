import java.io.*;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        File[] saves = {
                new File("D://Games/savegames/save1.dat"),
                new File("D://Games/savegames/save2.dat"),
                new File("D://Games/savegames/save3.dat"),
        };
        String[] savesNames = {"save1", "save2", "save3"};
        for (int i = 0; i < saves.length; i++) {
            try {
                if (saves[i].createNewFile())
                    System.out.println("Файл " + saves[i].getName() + " был создан");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        saveGame("D://Games/savegames/save1.dat", new GameProgress(90, 10, 3, 245.35));
        saveGame("D://Games/savegames/save2.dat", new GameProgress(80, 8, 4, 255.65));
        saveGame("D://Games/savegames/save3.dat", new GameProgress(70, 5, 5, 265.75));
        String pathZip = "D:/Games//savegames/zip.zip";
        String[] filesPath = {
                "D://Games/savegames/save1.dat",
                "D://Games/savegames/save2.dat",
                "D://Games/savegames/save3.dat",
        };
        zipFiles(pathZip, filesPath);
        for (File save : saves) {
            if (save.delete()) {
                System.out.println("Файл " + save.getName() + " был заархивирован и удалён");
            } else {
                System.out.println(save.getName() + " not deleted");
            }
        }
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFiles(String path, String[] filesPath) {
        for (String s : filesPath) {
            try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path));
                 FileInputStream fis = new FileInputStream(s)) {
                ZipEntry entry = new ZipEntry("packed_saves.txt");
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}