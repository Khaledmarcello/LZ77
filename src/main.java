public class main {
    public static void main(String args[]){
        LZ77 lz77=new LZ77();
        lz77.Compress("C:\\Users\\RCSC\\Documents\\GitHub\\LZ77\\test.txt", "compressed");
        lz77.Decompress("C:\\Users\\RCSC\\Documents\\GitHub\\LZ77\\compressed.txt", "decompressed");
    }
    }

