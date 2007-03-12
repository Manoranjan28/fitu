package org.commonfarm.web;

import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.components.DefaultRichtexteditorConnector;


public class RichtexteditorConnector extends DefaultRichtexteditorConnector {
    public static final Log logger = LogFactory.getLog(RichtexteditorConnector.class);

    private ServletContext servletContext;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    protected String calculateActualServerPath(String actualServerPath,
            String type, String folderPath) throws Exception {
        String path = servletContext.getRealPath(actualServerPath);
        path = path.replace('\\', '/');
        makeDirIfNotExists(path);
        path = path.endsWith("/") ? path : path + "/";
        return path + type + folderPath;
    }

    protected Folder[] getFolders(String virtualFolderPath, String type)
            throws Exception {
        String path = calculateActualServerPath(getActualServerPath(), type,
                virtualFolderPath);
        makeDirIfNotExists(path);
        java.io.File f = new java.io.File(path);
        java.io.File[] children = f.listFiles(new FileFilter() {
            public boolean accept(java.io.File pathname) {
                if (!pathname.isFile()) {
                    return true;
                }
                return false;
            }
        });

        List tmpFolders = new ArrayList();
        for (int a = 0; a < children.length; a++) {
            tmpFolders.add(new Folder(children[a].getName()));
        }

        return (Folder[]) tmpFolders.toArray(new Folder[0]);
    }

    protected FoldersAndFiles getFoldersAndFiles(String virtualFolderPath,
            String type) throws Exception {
        String path = calculateActualServerPath(getActualServerPath(), type,
                virtualFolderPath);
        makeDirIfNotExists(path);
        java.io.File f = new java.io.File(path);
        java.io.File[] children = f.listFiles();

        List directories = new ArrayList();
        List files = new ArrayList();
        for (int a = 0; a < children.length; a++) {
            if (children[a].isDirectory()) {
                directories.add(new Folder(children[a].getName()));
            } else {
                try {
                    files.add(new File(children[a].getName(),
                            fileSizeInKBytes(children[a])));
                } catch (Exception e) {
                    logger.error("cannot deal with file " + children[a], e);
                }
            }
        }

        return new FoldersAndFiles((Folder[]) directories
                .toArray(new Folder[0]), (File[]) files.toArray(new File[0]));
    }

    protected FileUploadResult fileUpload(String virtualFolderPath,
            String type, String filename, String contentType,
            java.io.File newFile) {
        try {
            String tmpDir = calculateActualServerPath(getActualServerPath(),
                    type, virtualFolderPath);
            makeDirIfNotExists(tmpDir);
            String tmpFile = tmpDir + filename;
            if (makeFileIfNotExists(tmpFile)) {
                // already exists
                int a = 0;
                String ext = String.valueOf(a);
                tmpFile = calculateActualServerPath(getActualServerPath(),
                        type, virtualFolderPath)
                        + filename + ext;
                while (makeFileIfNotExists(tmpFile)) {
                    a = a + 1;
                    ext = String.valueOf(a);
                    if (a > 100) {
                        return FileUploadResult.invalidFile();
                    }
                }
                copyFile(newFile, new java.io.File(tmpFile));
                return FileUploadResult
                        .uploadCompleteWithFilenamChanged(filename + ext);
            } else {
                copyFile(newFile, new java.io.File(tmpFile));
                return FileUploadResult.uploadComplete();
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
            e.printStackTrace();
            return FileUploadResult.invalidFile();
        }
    }

    protected void unknownCommand(String command, String virtualFolderPath,
            String type, String filename, String contentType,
            java.io.File newFile) {
        throw new RuntimeException("unknown command " + command);
    }

    /**
     * 
     * @param path
     * @return true if file already exists, false otherwise.
     */
    protected boolean makeDirIfNotExists(String path) {
        java.io.File dir = new java.io.File(path);
        if (!dir.exists()) {
            logger.debug("make directory " + dir);
            boolean ok = dir.mkdirs();
            if (!ok) {
                throw new RuntimeException("cannot make directory " + dir);
            }
            return false;
        }
        return true;
    }

    protected boolean makeFileIfNotExists(String filePath) throws IOException {
        java.io.File f = new java.io.File(filePath);
        if (!f.exists()) {
            logger.debug("creating file " + filePath);
            boolean ok = f.createNewFile();
            if (!ok) {
                throw new RuntimeException("cannot create file " + filePath);
            }
            return false;
        }
        return true;
    }

}