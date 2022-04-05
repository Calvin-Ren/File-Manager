package Project_03;

import java.io.*;

public class file_manager {
    private String Path = "";
    private String Encoding = "UTF-8";

    // class file manager creation
    public file_manager() {
        File file = new File("");
        Path = file.getAbsolutePath() + File.separator;
    }

    // output the current path
    public final void cur_pos() {
        System.out.println("Current Path is :" + this.Path);
    }

    // the input file is not a file
    private final void file_denied(File file) {
        System.out.println("Reason:  " + file.getName() + "  is not a file!");
    }

    // the input filedir is not a file dir
    private final void filedir_denied(File filedir) {
        System.out.println("Reason:  " + filedir.getName() + "  is not a file dictionary!");
    }

    // the input path is can't be found
    private final void path_missing(File file) {
        System.out.println("Reason:  Can't find " + file.getAbsolutePath() + "  !");
    }

    // to create a file in the input path
    public boolean file_creation(String file_name) {
        boolean create_bool = false;
        File file = new File(this.Path + file_name);
        if (file.exists()) {
            System.out.println("Creation Failed!");
            System.out.println("Reason:  " + file_name + " already exits!");
        }
        else {
            try {
                file.createNewFile();
                create_bool = true;
                System.out.println("Creation Succeed!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return create_bool;
    }

    // to create a file dir in the input path
    public boolean filedir_creation(String filedir_name) {
        boolean create_bool = false;
        File filedir = new File(this.Path + filedir_name);
        if (filedir.exists()) {
            System.out.println("Creation Failed!");
            System.out.println("Reason:  " + filedir_name + " already exits!");
        }
        else {
            try {
                filedir.mkdirs();
                create_bool = true;
                System.out.println("Creation Succeed!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return create_bool;
    }

    public boolean delete(String file_name) {
        boolean delete_bool = false;
        File file = new File(this.Path + file_name);
        if (file.exists()) {
            if (file.isFile()) {
                delete_bool = delete_file(file.getAbsolutePath());
                if (delete_bool) {
                    System.out.println("Delete file" + "\"" + file_name + "\"" + "succeeded!");
                }
            }
            else {
                delete_bool = delete_filedir(file.getAbsolutePath());
                if (delete_bool) {
                    System.out.println("Delete dir" + "\"" + file_name + "\"" + "succeeded!");
                }
            }
        }
        else {
            System.out.println("Delete failed!");
            path_missing(file);
            return delete_bool;
        }
        return delete_bool;
    }

    // delete the file in the input path
    public boolean delete_file(String file_name) {
        boolean delete_bool = false;
        File file = new File(file_name);
        if (file.delete()) delete_bool = true;
        return delete_bool;
    }

    // delete the file dir in the input path
    public boolean delete_filedir(String filedir_name) {
        boolean delete_bool = false;
        if (!filedir_name.endsWith(File.separator)) {
            filedir_name = filedir_name + File.separator;
        }

        File dirFile = new File(filedir_name);
        File[] fileList = dirFile.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile()) {
                delete_bool = delete_file(fileList[i].getAbsolutePath());
                if (!delete_bool) break;
            }
            else {
                delete_bool = delete_filedir(fileList[i].getAbsolutePath());
                if (!delete_bool) break;
            }
        }
        if (dirFile.delete()) delete_bool = true;
        return delete_bool;
    }

    // change the current path
    public boolean change_dir(String filedir_name) {
        boolean change_bool = false;
        File filedir = new File(filedir_name);

        boolean absPath_bool = false;   // check abspath
        if (filedir.isAbsolute()) {
            absPath_bool = true;
        }
        else {
            filedir = new File(this.Path + filedir_name);
        }

        if (filedir.exists()) {         // file dictionary exists
            if (filedir.isDirectory()) {// input filedir is dictionary
                if (!filedir_name.endsWith(File.separator)) {
                    filedir_name = filedir_name + File.separator;
                }

                if (absPath_bool) {
                    this.Path = filedir_name;
                    change_bool = true;
                }
                else {
                    this.Path += filedir_name;
                    change_bool = true;
                }
            }
            else {                      // input filedir is not dictionary
                System.out.println("Failed to change dictionary!");
                filedir_denied(filedir);
            }
        }
        else {                          // file dictionary does not exists
            System.out.println("Failed to change dictionary!"); // report error
            file_denied(filedir);
        }

        if (absPath_bool) {
            System.out.println("Change succeeded!");            // change succeeded
        }

        // check if the path is
        return change_bool;
    }

    // list all the files' names in the current path
    public String[] file_list() {
        File filedir = new File(this.Path);
        String[] file_lst = {};

        if (!filedir.exists()) {  // filedir does not exists
            System.out.println("List failed!");
            file_denied(filedir);
            return file_lst;
        }
        if (filedir.isFile()) {
            System.out.println("List failed!");
            filedir_denied(filedir);
            return file_lst;
        }
        // current filedir is not dictionary
        System.out.println("Current File dictionary includes: ");
        File[] files = filedir.listFiles();
        for (File file:files) {
            if (file.isFile()) {
                System.out.println("[File]" + "   " + file.getName());
            }
            else {
                System.out.println("[Dictionary]" + "   " + file.getName());
            }
        }
        file_lst = filedir.list();
        return file_lst;
    }
    public boolean copy(String file_name) {
        boolean copy_bool = false;
        File file = new File(this.Path + file_name);

        if (!file.exists()) {
            System.out.println("Copy failed!");
            path_missing(file);
            return copy_bool;
        }

        String currPath = this.Path + file_name;
        String destPath = this.Path;


        if (file.isFile()) copy_bool = file_copy(currPath, destPath);
        else copy_bool = filedir_copy(currPath, destPath);

        if (copy_bool) {
            System.out.println("Copy succeeded！");
        }
        else {
            System.out.println("Copy failed！");
        }
        return copy_bool;
    }

    // copy the file from former path to target path
    public boolean file_copy(String former_path, String target_path) {
        boolean copy_bool = false;
        File file = new File(former_path);
        File copyFile = new File(target_path + file.getName());

        String fileFullName = file.getName();
        String filePriName = fileFullName.substring(0, fileFullName.lastIndexOf("."));
        String fileExtenName = fileFullName.substring(fileFullName.lastIndexOf("."));

        if (copyFile.exists()) {
            copyFile = new File(target_path + filePriName + "-copy" + fileExtenName);
        }

        FileInputStream fIn = null;
        BufferedInputStream bIn = null;
        FileOutputStream fOut = null;
        BufferedOutputStream bOut = null;

        try {
            fIn = new FileInputStream(file);
            bIn = new BufferedInputStream(fIn);
            fOut = new FileOutputStream(copyFile);
            bOut = new BufferedOutputStream(fOut);

            byte[] Bytes = new byte[1024];
            int length = 0;
            while((length = bIn.read(Bytes)) != -1) {
                bOut.write(Bytes, 0, length);
            }
            bOut.flush();

            copy_bool = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            bOut.close();
            fOut.close();
            bIn.close();
            fIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return copy_bool;
    }

    // copy the file dictionary from former path to target path
    public boolean filedir_copy(String former_path, String target_path) {
        boolean copy_bool = false;
        File dirFile = new File(former_path);
        File destDirFile = new File(target_path + dirFile.getName());

        if (destDirFile.exists()) {
            destDirFile = new File(target_path + dirFile.getName() + "-副本");
        }

        destDirFile.mkdirs();

        File temFile = null;
        String[] fileNameList = dirFile.list();
        for (String fileName:fileNameList) {
            temFile = new File(former_path + File.separator + fileName);

            if (temFile.isFile()) {
                if (!file_copy(former_path + File.separator + fileName, destDirFile.getAbsolutePath() + File.separator)) {
                    return copy_bool;
                }
            }
            else {
                if (!filedir_copy(former_path + File.separator + fileName, destDirFile.getAbsolutePath() + File.separator)) {
                    return copy_bool;
                }
            } // end else

        }	  // end while

        copy_bool = true;
        return copy_bool;
    }

    // encode the target file
    public boolean file_encode(String file_path) {
        boolean encode_bool = false;

        File file = new File(this.Path + file_path);
        if (!file.exists()) {
            System.out.println("Decode Failed!");
            path_missing(file);
            return encode_bool;
        }
        if (!file.isFile()) {
            System.out.println("Decode Failed!");
            file_denied(file);
            return encode_bool;
        }

        final byte key = 101; // code

        // create input/output stream target
        FileInputStream fileinput = null;
        BufferedReader bufferreader = null;
        FileOutputStream fileout = null;
        BufferedWriter bufferwrite = null;

        try {
            fileinput = new FileInputStream(file);
            bufferreader = new BufferedReader(new InputStreamReader(fileinput, this.Encoding));
            fileout = new FileOutputStream(this.Path + "Encryp-" + file_path);
            bufferwrite = new BufferedWriter(new OutputStreamWriter(fileout, this.Encoding));

            System.out.println("Encoding...");
            int c = 0;
            while ((c = bufferreader.read()) != -1) {
                bufferwrite.write(c ^key);
            }

            bufferwrite.flush();
            encode_bool = true;
            System.out.println("Encode Succeeded!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // close input & output stream
        try {
            bufferreader.close();
            bufferwrite.close();
            fileinput.close();
            fileout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encode_bool;
    }



    // decode the target file
    public boolean file_decode(String file_path) {
        boolean decode_bool = false;
        File encode_file = new File(this.Path + file_path);

        if (!encode_file.exists()) {
            System.out.println("Decode failed!");
            path_missing(encode_file);
            return decode_bool;
        }
        if (!encode_file.isFile()) {
            System.out.println("Decode failed!");
            path_missing(encode_file);
            return decode_bool;
        }

        final byte key = 101;
        // create input/output stream target
        FileInputStream fileinput = null;
        BufferedReader bufferreader = null;
        FileOutputStream fileout = null;
        BufferedWriter bufferwrite = null;

        try {
            fileinput = new FileInputStream(encode_file);
            bufferreader = new BufferedReader(new InputStreamReader(fileinput, this.Encoding));
            fileout = new FileOutputStream(this.Path + "Decryp-" + file_path);
            bufferwrite = new BufferedWriter(new OutputStreamWriter(fileout, this.Encoding));

            System.out.println("Decoding...");
            int c = 0;
            while ((c = bufferreader.read()) != -1) {
                bufferwrite.write(c ^key);
            }

            bufferwrite.flush();
            decode_bool = true;
            System.out.println("Decode Succeeded!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // close input & output stream
        try {
            bufferreader.close();
            bufferwrite.close();
            fileinput.close();
            fileout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decode_bool;
    }


}
