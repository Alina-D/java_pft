package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FtpHelper {

  private final ApplicationManager app;
  private FTPClient ftp;


  public FtpHelper(ApplicationManager app) {
    this.app = app;
    ftp = new FTPClient();
  }

  // локальный файл загружаемый, имя файла, имя резервной копии
  public void upload(File file, String target, String backup) throws IOException {
    // соединение с сервером
    ftp.connect(app.getProperty("ftp.host"));
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    // удаляем предыдущую резвную копию
    ftp.deleteFile(backup);
    // переименование удаленного файла
    ftp.rename(target, backup);
    // пассивный режим манипуляции данных
    ftp.enterLocalPassiveMode();
    // передается файл
    ftp.storeFile(target, new FileInputStream(file));
    ftp.disconnect();
  }

  public void restore(String backup, String target) throws IOException {
    ftp.connect(app.getProperty("ftp.host"));
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    ftp.deleteFile(target);
    // восстановление файла
    ftp.rename(target, backup);
    ftp.disconnect();
  }
}
