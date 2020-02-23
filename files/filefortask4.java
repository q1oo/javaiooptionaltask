PACKAGE MY.AZAMAT; 
 
IMPORT JAVA.IO.*; 
IMPORT JAVA.UTIL.HASHMAP; 
IMPORT JAVA.UTIL.MAP; 
 
PUBLIC CLASS MAIN { 
 
    STATIC STRINGBUFFER TAB = NEW STRINGBUFFER(""); 
    STATIC FILE FILETOWRITE = NEW FILE("DATA/FILEFORWRITE.TXT"); 
 
    PUBLIC STATIC VOID SHOWINSIDE(FILE FILE) { 
        if (FILE.ISDIRECTORY()) { 
            WRITETOFILE(TAB + FILE.GETNAME()); 
            TAB.APPEND("    "); 
            FOR (FILE CURRENT : FILE.LISTFILES()) { 
                SHOWINSIDE(CURRENT); 
            } 
            WRITETOFILE(""); 
            TAB.DELETE(0,4); 
        } ELSE { 
            WRITETOFILE(TAB + FILE.GETNAME()); 
        } 
    } 
 
    PUBLIC STATIC VOID WRITETOFILE(STRING FILE) { 
        TRY (FILEWRITER FILEWRITER = NEW FILEWRITER(FILETOWRITE, TRUE); BUFFEREDWRITER OUT = NEW BUFFEREDWRITER(FILEWRITER)) { 
            if (FILE.EQUALS("")) { 
                OUT.NEWLINE(); 
            } ELSE { 
                OUT.WRITE(FILE); 
                OUT.NEWLINE(); 
            } 
        } CATCH (FILENOTFOUNDEXCEPTION e) { 
            E.PRINTSTACKTRACE(); 
        } CATCH (IOEXCEPTION e) { 
            E.PRINTSTACKTRACE(); 
        } 
    } 
 
    PUBLIC STATIC VOID MAIN(STRING[] ARGS) { 
        if (FILETOWRITE.EXISTS()) { 
            FILETOWRITE.DELETE(); 
        } 
        FILE FILEORDIR = NEW FILE(ARGS[0]); 
        if (FILEORDIR.EXISTS() && FILEORDIR.ISDIRECTORY()) { 
            //"D:\MUSIC\ROCK\КИНО\КИНО" 
            SHOWINSIDE(FILEORDIR); 
        } 
 
        if (FILEORDIR.EXISTS() && FILEORDIR.ISFILE()) {//"DATA\FILEFORREAD.TXT" 
            INT COUNTOFALLFOLDERS = 0; 
            INT COUNTOFALLFILES = 0; 
            INT CURRENTNUMBEROFFOLDER = 0; 
            INT COUNTOFFILESINCURRENTFOLDER = 0; 
            DOUBLE SUMOFLENGTHSOFFILENAMES = 0; 
            MAP<INTEGER, INTEGER> FILESINFOLDERS = NEW HASHMAP<>(); 
            TRY (FILEREADER FILEREADER = NEW FILEREADER(FILEORDIR); BUFFEREDREADER BUFFEREDREADER = NEW BUFFEREDREADER(FILEREADER)) { 
                STRING LINE = NULL; 
                WHILE ((LINE = BUFFEREDREADER.READLINE()) != NULL) { 
                    if (LINE.CONTAINS(".")) { 
                        COUNTOFFILESINCURRENTFOLDER++; 
                        COUNTOFALLFILES++; 
                        SUMOFLENGTHSOFFILENAMES += LINE.LENGTH(); 
                    } ELSE if (!LINE.ISEMPTY()) { 
                        COUNTOFALLFOLDERS++; 
                        CURRENTNUMBEROFFOLDER++; 
                    } ELSE if (LINE.ISEMPTY() && COUNTOFFILESINCURRENTFOLDER != 0){ 
                        FILESINFOLDERS.PUT(CURRENTNUMBEROFFOLDER, COUNTOFFILESINCURRENTFOLDER); 
                        COUNTOFFILESINCURRENTFOLDER = 0; 
                    } 
                } 
            } CATCH (FILENOTFOUNDEXCEPTION e) { 
                E.PRINTSTACKTRACE(); 
            } CATCH (IOEXCEPTION e) { 
                E.PRINTSTACKTRACE(); 
            } 
 
            SYSTEM.OUT.PRINTLN("COUNTOFALLFOLDERS = " + COUNTOFALLFOLDERS); 
            SYSTEM.OUT.PRINTLN("COUNTOFALLFILES = " + COUNTOFALLFILES); 
            SYSTEM.OUT.PRINTLN("AVGERAGE COUNT of FILES in FOLDERS = " + COUNTOFALLFILES/FILESINFOLDERS.SIZE()); 
            SYSTEM.OUT.PRINTLN("AVGERAGE LENGTH of FILENAMES = " + MATH.FLOOR(SUMOFLENGTHSOFFILENAMES/COUNTOFALLFILES*1E1)/1E1); 
        } 
    } 
} 
