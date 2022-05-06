import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {
        String pathZip = "F:/Java/Games/savegames/newZip.zip";
        String pathFolderToZip = "F:/Java/Games/GunRunner/savegames";

        //openZip(pathZip, pathFolderToZip);

        ArrayList<GameProgress> arrayGameProgress = new ArrayList<>();
        GameProgress g1 = null;
        GameProgress g2 = null;
        GameProgress g3 = null;

        arrayGameProgress.add(g1);
        arrayGameProgress.add(g2);
        arrayGameProgress.add(g3);

        File folder = new File(pathFolderToZip);

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];

                arrayGameProgress.set(i, openProgress(files[i].getPath()));
            }
        } else {
            System.out.println(pathFolderToZip + " - не является директорией.");
        }

        g1 = arrayGameProgress.get(0);
        g2 = arrayGameProgress.get(1);
        g3 = arrayGameProgress.get(2);

        System.out.println(g1.toString());
        System.out.println(g2.toString());
        System.out.println(g3.toString());
        System.out.println("\nРабота программы завершена.");
    }

    public static void openZip(String path, String pathFolderToZip) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(path))) {

            ZipEntry entry;
            String name;
            while ((entry = zis.getNextEntry()) != null) {
                name = entry.getName();

                FileOutputStream fis = new FileOutputStream(pathFolderToZip + "/" + name);
                for (int i = zis.read(); i != -1; i = zis.read()) {
                    fis.write(i);
                }
                fis.flush(); //запись
                zis.closeEntry();
                fis.close();
            }


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String pathFile) {
        GameProgress gameProgress = null;

        try (FileInputStream fis = new FileInputStream(pathFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }
}
