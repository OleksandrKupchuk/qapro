package tests.L7;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TestData {


    public void initFolders() {
        try {
            Files.createDirectories(new File("hars").toPath());
            FileUtils.cleanDirectory(new File("hars"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DataProvider
    public Object[][] statusCodesProvider() {
        return new Object[][]{
//                {200},
//                {301},
//                {404},
                {500}
        };
    }

    public void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
