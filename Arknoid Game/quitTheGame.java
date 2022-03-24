import biuoop.DrawSurface;
import biuoop.GUI;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class quitTheGame implements Animation {
    private GUI gui;
    public quitTheGame(GUI gui) {
        this.gui = gui;
    }
    @Override
    public void doOneFrame(DrawSurface d) {
        Path path = FileSystems.getDefault().getPath("src/highscores.txt");
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
           ;
        } catch (IOException x) {
            ;
        }
        gui.close();
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
