package ObjTest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class CodingConvert {
	public static void main(String[] args) {
		try {
			BufferedReader br=
					new BufferedReader(new InputStreamReader(new FileInputStream("src/ObjTest/CatchInfoProgram.java"),
							"utf-8"));
			String line=null;
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("CatchInfoProgram.java"),"gbk"));
			while((line=br.readLine())!=null) {
				pw.println(line);
			}
			br.close();
			pw.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
