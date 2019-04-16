package Copy;

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
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取信息小程序——基本功能简单实现版(到处都是BUG版)
 * 
 * 启动程序后，输入网址并选择需要获取的信息模式，
 * 随后会在D:/目录下创建一个CatchResult.txt文件，用于保存获取的信息。
 * 
 * @author Kamui
 *
 */

/*
 * 测试用URL：
 * （测试获取邮箱地址功能）
 * https://tieba.baidu.com/p/5605167486?red_tag=1303982220
 * （测试获取手机号功能）
 * http://www.18000000001.com/ 
 * （测试获取固定电话功能）
 * http://www.114chn.com/index.htm
 * （测试获取URL功能）
 * http://www.acfun.cn/
 * 
 */

public class CatchInfoProgram {

	// 0.主方法----------------------------------
	public static void main(String[] args) throws InterruptedException {
		/**启动一个子线程运行采集程序-start**/
		new Thread(new StartThread()).start();
		/**启动一个子线程运行采集程序-end**/

		/**主线程休眠300秒，模拟假设主线程在处理其它业务，例如不断检测硬盘容量是否超标，以报警等-start**/
		Thread.sleep(300000);
		/**主线程休眠300秒，模拟假设主线程在处理其它业务，例如不断检测硬盘容量是否超标，以报警等-end**/

	}

	public static class StartThread implements Runnable{

		@Override
		public void run() {
			try {
				System.out.println("程序启动，请输入网址（URL）：");
				Scanner sc = new Scanner(System.in);
				String webAdress = sc.nextLine().trim();
				System.out.println("请选择信息获取模式：（1-4）");
				System.out.println("1-获取邮箱地址信息");
				System.out.println("2-获取手机号码信息");
				System.out.println("3-获取固定电话信息");
				System.out.println("4-获取网址(URL)信息");
				int modeSelect = sc.nextInt();

				switch (modeSelect) {
					case 1:
						System.out.println("已选择获取邮箱地址信息。正在读取网站信息……");
						catchEmail(enterWebside(webAdress));
						break;

					case 2:
						System.out.println("已选择获取手机号码信息。正在读取网站信息……");
						catchMobilePhone(enterWebside(webAdress));
						break;

					case 3:
						System.out.println("已选择获取固定电话信息。正在读取网站信息……");
						catchPhoneNumber(enterWebside(webAdress));
						break;

					case 4:
						System.out.println("已选择获取网址(URL)信息。正在读取网站信息……");
						catchWebside(enterWebside(webAdress));
						break;

					default:
						System.out.println("错误：未知的输入！请输入1-4以内的数字。程序结束。");
						sc.close();
						System.exit(1);
				}
				System.out.println("信息获取完毕！程序即将结束，感谢使用。");
				sc.close();
				System.exit(1);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
//	// 1.启动方法
//	public static void start() {
//		try {
//			System.out.println("程序启动，请输入网址（URL）：");
//			Scanner sc = new Scanner(System.in);
//			String webAdress = sc.nextLine().trim();
//			System.out.println("请选择信息获取模式：（1-4）");
//			System.out.println("1-获取邮箱地址信息");
//			System.out.println("2-获取手机号码信息");
//			System.out.println("3-获取固定电话信息");
//			System.out.println("4-获取网址(URL)信息");
//			int modeSelect = sc.nextInt();
//
//			switch (modeSelect) {
//			case 1:
//				System.out.println("已选择获取邮箱地址信息。正在读取网站信息……");
//				catchEmail(enterWebside(webAdress));
//				break;
//
//			case 2:
//				System.out.println("已选择获取手机号码信息。正在读取网站信息……");
//				catchMobilePhone(enterWebside(webAdress));
//				break;
//
//			case 3:
//				System.out.println("已选择获取固定电话信息。正在读取网站信息……");
//				catchPhoneNumber(enterWebside(webAdress));
//				break;
//
//			case 4:
//				System.out.println("已选择获取网址(URL)信息。正在读取网站信息……");
//				catchWebside(enterWebside(webAdress));
//				break;
//
//			default:
//				System.out.println("错误：未知的输入！请输入1-4以内的数字。程序结束。");
//				sc.close();
//				System.exit(1);
//			}
//			System.out.println("信息获取完毕！程序即将结束，感谢使用。");
//			sc.close();
//			System.exit(1);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//
//		}
//	}

	// 2.读取网址信息方法----------------------------------
	public static BufferedReader enterWebside(String webAdress) {
		try {
			// 创建一个url对象,获取用户输入的地址。
			URL url = new URL(webAdress);
			// 打开连接
			URLConnection opcon = url.openConnection();
			// 设置连接网络超时时间 单位为毫秒
			opcon.setConnectTimeout(1000 * 10);
			// 通过流 操作读取指定网络地址中的所有信息
			BufferedReader bufr = new BufferedReader(new InputStreamReader(opcon.getInputStream()));
			return bufr;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("网络无法连接！程序终止！请确认输入网址格式是否正确。");
			System.exit(1);
		}
		return null;// 不会被执行
	}

	// 3.生成txt文件方法并保存获取结果方法(输入流,正则表达式)----------------------------------
	public static void OutputInfo(BufferedReader bufr, String regex ) throws IOException {
		Scanner sc = new Scanner(System.in);
		//判断文件是否已存在。为避免覆盖源文件所写。
		if (!new File("D:/CatchResult.txt").exists()) {
			System.out.println("信息保存文件创建成功！位于D:/CatchResult.txt");
		} else {
			System.out.println("检测到信息保存文已存在，是否要直接覆盖原文件？(0-不覆盖，1-覆盖)");
			String select = sc.nextLine();
			while (true) {
				if ("0".equals(select)) {
					System.out.println("已选择不覆盖源文件，程序即将结束，感谢使用！");
					System.exit(1);
				} else {
					if ("1".equals(select)) {
						System.out.println("已覆盖原文件！位于D:/CatchResult.txt");
						break;
					} else {
						System.out.println("错误：未知的输入！");
						System.out.println("检测到文件已存在，是否要直接覆盖原文件？(0-不覆盖，1-覆盖)");
						select = sc.nextLine();
					}
				}
			}
		}
		sc.close();
		
		// 创建输出流
		PrintWriter pw = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:/CatchResult.txt"), "utf-8")));

		String line = null;
		// 使用模式的compile()方法生成工厂模式对象
		Pattern p = Pattern.compile(regex);
		// 遍历输出
		while ((line = bufr.readLine()) != null) {
			Matcher m = p.matcher(line);// 正则校验
			while (m.find()) {
				pw.println(m.group().trim());
				break;
			}
		}
		pw.flush();// 刷新缓冲
		/**假设这里写代码不小心出了一个bug，出现了异常-start**/
		int a = 0;
		int b=1/a;
		/**假设这里代码不小心出了一个bug，出现了异常-end**/
		//1.因为出现了异常，所以下面的关闭流代码不会执行
		//2.这时候，由于出现异常子线程已经崩溃
		//3.但是这时候，主线程处理休眠状态（实际情况可能是在处理其它业务），所以虚拟机不会退出，因此虚拟机对D:/CatchResult.txt文件的资源不会释放，导致这个文件不能删除和修改
		pw.close();
	}

	// 4.1获取邮箱地址方法----------------------------------
	public static void catchEmail(BufferedReader bufr) throws IOException {
		// 匹配email的正则
		String regex = "[a-zA-Z0-9_-]+@\\w+\\.[a-z]+(\\.[a-z]+)?";
		OutputInfo(bufr, regex);
	}

	// 4.2获取手机号方法----------------------------------
	public static void catchMobilePhone(BufferedReader bufr) throws IOException {
		// 匹配手机号的正则
		String regex = "(\\+86|0086)?\\s?1\\d{10}";
		OutputInfo(bufr, regex);
	}

	// 4.3获取固定电话方法----------------------------------
	public static void catchPhoneNumber(BufferedReader bufr) throws IOException {
		// 匹配固定电话的正则
		String regex = "^[0][1-9]{2,3}-[0-9]{5,10}$";
		OutputInfo(bufr, regex);
	}

	// 4.4获取网址方法----------------------------------
	public static void catchWebside(BufferedReader bufr) throws IOException {
		// 匹配网址的正则
		String regex = "[http|https]+[:/]+[0-9A-Za-z:/[-]_#[?][=][.][&]]*";
		OutputInfo(bufr, regex);
	}

}