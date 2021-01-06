import java.io.*;

public class LZ77 {
    public String ReadFromFile (String Path) throws IOException {
        String content = null;
        File file = new File(Path);
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }
    public void WriteToFile(String content,String name){
        BufferedWriter writer = null;
        String fileName = name+".txt" ;
        File logFile = new File(fileName);
        try{
            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {}

        }
    }
    public int Exist(String chars,String content){
        int len=content.length();
        int num=chars.length();
        for (int i = 0; i < len - num + 1; i++)
        {
            String compare = "";
            for (int j = i; j < num + i; j++)
            {
                compare += content.toCharArray()[j];
            }

            if (chars.equals(compare))
                return i;
        }
        return -1;

    }
    public void Compress(String path , String name)
    {
        String text = new String();
        String compressed = new String ("") ;

        try {
            text = ReadFromFile(path);
        } catch (IOException e) {}

        int len = text.length();
        String end = "";

        for (int i = 0; i<len; i++)
        {
            String curr =new String( "");
            curr += (text.toCharArray()[i]) ;
            int length = 0;
            int ptr = 0 ;
            String next = "";
            int start = i ;
            while (Exist(curr,end)!=-1)
            {
                length++;
                i++;
                ptr = start-end.indexOf(curr) ;
                if (i==len)
                    break ;
                curr += text.toCharArray()[i];
            }
            end += curr ;

            if (i==len)
                next += "NULL" ;
            else
            {
                next += text.toCharArray()[i];
            }
            String tag = new String("<" + ptr + "," + length + "," + next + ">");
            compressed+=tag;
        }
        WriteToFile(compressed , name);
    }
    public void Decompress(String path , String name)
    {
        String text = new String();
        String decompressed = new String ("") ;

        try {
            text = ReadFromFile(path);
        } catch (IOException e) {}

        int len = text.length();

        int count = 0 ;

        for (int i=0 ; i<len ; i++)
        {
            int ptr = 0 , length = 0 ;
            String next = "" ;
            String temp ="";
            i++ ;
            while (text.toCharArray()[i]!=',')
            {
                temp += text.toCharArray()[i];
                i++ ;
            }
            i++ ;
            ptr = Integer.parseInt(temp);
            temp = "" ;
            while (text.toCharArray()[i]!=',')
            {
                temp += text.toCharArray()[i];
                i++ ;
            }
            i++ ;
            length = Integer.parseInt(temp);

            if (i==len-5)
            {
                for (int k=i ; k<i+4 ; k++)
                {
                    next +=text.toCharArray()[k];
                }
                i=len-1 ;
            }
            else
            {
                next += text.toCharArray()[i];
              //  System.out.println(next);
                i++ ;
            }


            //System.out.println("<" + ptr + "," + length + "," + next + ">");
            if (ptr == 0)
            {
                decompressed += next ;
                count ++ ;
            }
            else
            {
                for (int j=0 ; j<length ; j++)
                {
                    decompressed+=decompressed.toCharArray()[count - ptr+j];
                    //count ++ ;
                }
                count += length ;

                if (!next.equals("NULL"))
                {
                    decompressed += next ;
                    count ++ ;
                }
                else
                    break ;
            }
        }
        WriteToFile(decompressed , name);
    }


}

