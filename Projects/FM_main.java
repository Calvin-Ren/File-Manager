package Project_03;

import java.util.Scanner;

public class FM_main {
    public static final void menue() {
        System.out.println("----------------Menue:----------------");
        System.out.println("1.List files");
        System.out.println("2.Enter filedir");
        System.out.println("3.Create file");
        System.out.println("4.Create filedir");
        System.out.println("5.Delete file or filedir");
        System.out.println("6.Copy file or filedir");
        System.out.println("7.Encode file");
        System.out.println("8.Decode file");
        System.out.println("9.menue");
        System.out.println("0.exit()");
    }

    public static void main(String[] args) {
        file_manager fm = new file_manager();
        Scanner sc =new Scanner(System.in);

        System.out.println("---------The CMD File Manager---------");

        fm.cur_pos();
        menue();
        boolean flag = true;
        while(flag) {
            System.out.println("");
            System.out.print("Please enter the num you want to choose:");
            int iFuncIndex = 0;
            // input check
            while (true) {
                if (sc.hasNextInt()) {
                    iFuncIndex = sc.nextInt();
                    if ((0 <= iFuncIndex) && (iFuncIndex <= 11)) {
                        break;
                    }
                    else {
                        System.out.println("Input Error!");
                        System.out.println("Reason：input num is supposed in 0-9");
                        System.out.println("");
                        System.out.print("Please enter the num you want to choose");
                    }
                }
                else {
                    System.out.println("Input Error!");
                    System.out.println("Reason: input tpye incorrect!");
                    sc.next();
                    System.out.println("");
                    System.out.print("Please enter the num you want to choose");
                }
            }

            if (iFuncIndex != 0) { fm.cur_pos(); }

            // menue selection
            switch (iFuncIndex) {
                case 0: {
                    flag = false;
                    System.out.println("Exit succeeded! Thx for using");
                    break;
                }
                case 1:
                {
                    fm.file_list();
                    break;
                }
                case 2:
                {
                    System.out.print("Input the dictionary you want to enter:");
                    String sPath = sc.next();
                    fm.change_dir(sPath);
                    break;
                }
                case 3:
                {
                    System.out.print("Input the File name you want to create:");
                    String creatFileName = sc.next();
                    fm.file_creation(creatFileName);
                    break;
                }
                case 4:
                {
                    System.out.print("Input the File dictionary name you want to create:");
                    String creatDirectoryName = sc.next();
                    fm.filedir_creation(creatDirectoryName);
                    break;
                }
                case 5:
                {
                    System.out.print("Input the name of File or File dictionary you want to delete:");
                    String deleteFileName = sc.next();
                    fm.delete(deleteFileName);
                    break;
                }
                case 6:
                {
                    System.out.print("Input the name of File or File dictionary you want to copy:");
                    String fileName = sc.next();
                    fm.copy(fileName);
                    break;
                }
                case 7:
                {
                    System.out.print("Input the name of File you want to encode:");
                    String encrypFileName = sc.next();
                    fm.file_encode(encrypFileName);
                    break;
                }
                case 8:
                {
                    System.out.print("Input the name of File you want to decode:");
                    String decrypFileName = sc.next();
                    fm.file_decode(decrypFileName);
                    break;
                }
                case 9: {
                    menue();
                    break;
                }
            }
        }
        sc.close();
    }
}
