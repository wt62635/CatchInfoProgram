package ObjTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//使用说明：
/**
* 智能抓取——v1.0
* 
* 启动程序后，输入网址并选择需要获取的信息模式， 随后会在程序当前目录下创建一个CatchResult.txt文件，用于保存获取的信息。
* 如存在同名文件则可以选择覆盖或创建副本。
* 若未能成功连上网站，可选择重新连接或重新输入网址。
* 
* 备注：在输入网址时，
* 请勿直接输入域名→例如（www.acfun.cn）×；
* 需要输入完整URL→例如（http://www.acfun.cn）√；
* 
* @author Kamui
*
*/

/*
* 测试用URL： 
* （测试获取邮箱地址功能）
*  https://tieba.baidu.com/p/5605167486?red_tag=1303982220
* （测试获取手机号功能） 
* http://www.18000000001.com/ 
* （测试获取固定电话功能）
* http://www.ziyuan.ccoo.cn/yp/tel.html 
* （测试获取URL功能） 
* http://www.acfun.cn/
* 
*/

//0.程序启动主类----------------------------------

public class CatchInfoProgram2 {
	public static String webAdress ;
	public static String modeSelect ;
	public static long costTime ;
	public static Scanner sc0 = new Scanner(System.in);
	// 主方法
	public static void main(String[] args) {
		CatchInfoProgram2 cip2 = new CatchInfoProgram2();
		System.out.println("系统消息：程序已完成编译，成功启动！");
		System.out.println("系统消息：欢迎使用《智能抓取v1.0》程序！");
		System.out.println("提示：请输入完整网址（URL）：(例如：http://www.acfun.cn)");
		webAdress = sc0.nextLine().trim();
		System.out.println("\n提示：请选择信息获取模式：（1-4）");
		System.out.println("\n1-获取邮箱地址信息");
		System.out.println("\n2-获取手机号码信息");
		System.out.println("\n3-获取固定电话信息");
		System.out.println("\n4-获取网址(URL)信息");
		
		CatchInfo ci = new CatchInfo();
		try {
			new OutputInfo(cip2.choice(),ci.connect());
			System.out.println("系统消息：信息获取完毕！程序即将结束，感谢使用。");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("警告：程序出现未知异常，请与开发者联系，或检查程序源代码！");
		}finally {
			sc0.close();
			System.exit(1);
		}
	}

	// 选择方法
	private String choice() {
		modeSelect = sc0.nextLine();
		String regex = null;
		switch (modeSelect) {
		case "1":
			System.out.println("\n--已选择获取邮箱地址信息。正在读取网站信息……");
			// 匹配邮箱的正则
			regex = "[a-zA-Z0-9_-]+@\\w+\\.[a-z]+(\\.[a-z]+)?";
			modeSelect = "邮箱";
			break;

		case "2":
			System.out.println("\n--已选择获取手机号码信息。正在尝试连接网站……");
			// 匹配手机号的正则
			regex = "(\\+86|0086)?\\s?1\\d{10}";
			modeSelect = "手机号";
			break;

		case "3":
			System.out.println("\n--已选择获取固定电话信息。正在尝试连接网站……");
			// 匹配固定电话的正则
			regex = "(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +
					"(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";
			modeSelect = "固定电话";
			break;

		case "4":
			System.out.println("\n--已选择获取网址(URL)信息。正在尝试连接网站……");
			// 匹配网址的正则
			regex = "[http|https]+[:/]+[0-9A-Za-z:/[-]_#[?][=][.][&]]*";
			modeSelect = "网址（URL）";
			break;

		default:
			System.err.println("\n输入错误：未知的输入！请重新输入1-4以内的数字。");
			modeSelect = sc0.nextLine();
			choice();
		}
		return regex ;
	}
}

//1.选择取得信息----------------------------------

class CatchInfo {
	Scanner sc1 = new Scanner(System.in);
	// 获取信息
	protected BufferedReader connect() throws IOException {
		new CatchInfoProgram2();
		try {
			// 创建一个url对象,获取用户输入的地址。
			URL url = new URL(CatchInfoProgram2.webAdress);
			// 打开连接
			URLConnection opcon = url.openConnection();
			// 设置连接网络超时时间 单位为毫秒
			opcon.setConnectTimeout(1000 * 10);
			
			System.out.println("\n系统消息：连接成功，正在读取网站信息……");
			return read(opcon);
		} catch (Exception e) {
			System.err.println("\n网络异常：无法连接！请确认输入网址格式是否正确。");
			System.out.println("\n提示：请输入对应命令选择下一步：(1-重试   , 2-重新输入网址 , other-结束程序)");
			String nextstep = sc1.nextLine();
			
			if ("1".equals(nextstep)) {
				System.out.println("\n--正在重试……");
				connect();
				
			} else if("2".equals(nextstep)) {
				System.out.println("\n--请重新输入网址：");
				CatchInfoProgram2.webAdress = sc1.nextLine();
				System.out.println("\n--正在重试……");
				connect();
				
			} else {
				System.out.println("\n系统消息：正在结束程序……感谢使用！");
				System.exit(1);
			}
		}
			return connect();//递归执行
	}
	//创建读取流方法
	private BufferedReader read(URLConnection opcon) {
		// 通过流 操作读取指定网络地址中的所有信息
		BufferedReader bufr = null;
		try {
			bufr = new BufferedReader(new InputStreamReader(opcon.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("\n目标服务器异常：目标服务器访问出现异常！请重新启动程序尝试。");
			System.exit(1);
		}
		return bufr;
	}
}

//2.输出信息并创建文件----------------------------------

class OutputInfo {
	private String regex;
	private BufferedReader bufr;
	private String backName = "";
	Scanner sc2 = new Scanner(System.in);
	// 构造方法
	protected OutputInfo(String regex,BufferedReader bufr) {
		this.regex = regex;
		this.bufr = bufr;
		try {
			creatFile(bufr, regex);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("\n程序错误！文件创建失败！请重新启动程序。");
			System.exit(1);
		}
	}

	// setter&getter
	public BufferedReader getBufr() {
		return bufr;
	}

	public void setBufr(BufferedReader bufr) {
		this.bufr = bufr;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	// 创建文件
	private void creatFile(BufferedReader bufr, String regex) throws IOException {
		new File("./result").mkdir();//创建文件夹
		System.out.println("\n系统消息：正在程序当前目录下的“result”文件夹中创建信息保存文件“CatchResult.txt”……");
		confirmFile();
		// 创建输出流
		PrintWriter pw = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./result/CatchResult"+this.backName+".txt"), "utf-8")));
		new CatchInfoProgram2();
		
		
		String line = null;
		// 使用模式的compile()方法生成工厂模式对象
		Pattern p = Pattern.compile(regex);
		// 遍历输出
		long start = System.currentTimeMillis();
		while ((line = bufr.readLine()) != null) {
			Matcher m = p.matcher(line);// 正则校验
			while (m.find()) {
				pw.println(m.group().trim());
				break;
			}
		}
		long end = System.currentTimeMillis();
		
		pw.println("----------");
		pw.println("以上为全部获取结果。");
		pw.println("来源网站（URL）：" + CatchInfoProgram2.webAdress);
		pw.println("获取模式：" + CatchInfoProgram2.modeSelect);
		pw.println("获取耗时：" + ((double)(CatchInfoProgram2.costTime = end - start))/1000 + "秒");
		pw.flush();// 刷新缓冲
		bufr.close();
		pw.close();
	}

	// 确认文件是否存在方法
	private void confirmFile() {
		// 判断文件是否已存在。为避免覆盖源文件所写。
		if (!new File("./result/CatchResult.txt").exists()) {
			System.out.println("\n系统消息：信息保存文件创建成功！保存于程序当前目录/result/CatchResult.txt");
		} else {
			System.out.println("\n提示：检测到信息保存文件“CatchResult.txt”已存在，是否要直接覆盖原文件？(0-不覆盖，1-覆盖)");
			String select = sc2.nextLine();
			while (true) {
				if ("0".equals(select)) {
					System.out.println("\n--已选择不覆盖源文件，将创建同名副本+(创建时间)，并保存于程序当前目录/result。");
					Date date = new Date();
					SimpleDateFormat df = new SimpleDateFormat("yyMMdd_hh_mm_ss");
					this.backName = " 副本" + df.format(date);
					break;
				} else {
					if ("1".equals(select)) {
						System.out.println("\n--已覆盖原文件！保存于程序当前目录/result/CatchResult.txt");
						break;
					} else {
						System.err.println("\n错误：未知的输入！");
						System.out.println("\n系统消息：检测到文件已存在，是否要直接覆盖原文件？(0-不覆盖，1-覆盖)");
						select = sc2.nextLine();
					}
				}
			}
		}
	}
}
