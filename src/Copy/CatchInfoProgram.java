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
 * ��ȡ��ϢС���򡪡��������ܼ�ʵ�ְ�(��������BUG��)
 * 
 * ���������������ַ��ѡ����Ҫ��ȡ����Ϣģʽ��
 * ������D:/Ŀ¼�´���һ��CatchResult.txt�ļ������ڱ����ȡ����Ϣ��
 * 
 * @author Kamui
 *
 */

/*
 * ������URL��
 * �����Ի�ȡ�����ַ���ܣ�
 * https://tieba.baidu.com/p/5605167486?red_tag=1303982220
 * �����Ի�ȡ�ֻ��Ź��ܣ�
 * http://www.18000000001.com/ 
 * �����Ի�ȡ�̶��绰���ܣ�
 * http://www.114chn.com/index.htm
 * �����Ի�ȡURL���ܣ�
 * http://www.acfun.cn/
 * 
 */

public class CatchInfoProgram {

	// 0.������----------------------------------
	public static void main(String[] args) throws InterruptedException {
		/**����һ�����߳����вɼ�����-start**/
		new Thread(new StartThread()).start();
		/**����һ�����߳����вɼ�����-end**/

		/**���߳�����300�룬ģ��������߳��ڴ�������ҵ�����粻�ϼ��Ӳ�������Ƿ񳬱꣬�Ա�����-start**/
		Thread.sleep(300000);
		/**���߳�����300�룬ģ��������߳��ڴ�������ҵ�����粻�ϼ��Ӳ�������Ƿ񳬱꣬�Ա�����-end**/

	}

	public static class StartThread implements Runnable{

		@Override
		public void run() {
			try {
				System.out.println("������������������ַ��URL����");
				Scanner sc = new Scanner(System.in);
				String webAdress = sc.nextLine().trim();
				System.out.println("��ѡ����Ϣ��ȡģʽ����1-4��");
				System.out.println("1-��ȡ�����ַ��Ϣ");
				System.out.println("2-��ȡ�ֻ�������Ϣ");
				System.out.println("3-��ȡ�̶��绰��Ϣ");
				System.out.println("4-��ȡ��ַ(URL)��Ϣ");
				int modeSelect = sc.nextInt();

				switch (modeSelect) {
					case 1:
						System.out.println("��ѡ���ȡ�����ַ��Ϣ�����ڶ�ȡ��վ��Ϣ����");
						catchEmail(enterWebside(webAdress));
						break;

					case 2:
						System.out.println("��ѡ���ȡ�ֻ�������Ϣ�����ڶ�ȡ��վ��Ϣ����");
						catchMobilePhone(enterWebside(webAdress));
						break;

					case 3:
						System.out.println("��ѡ���ȡ�̶��绰��Ϣ�����ڶ�ȡ��վ��Ϣ����");
						catchPhoneNumber(enterWebside(webAdress));
						break;

					case 4:
						System.out.println("��ѡ���ȡ��ַ(URL)��Ϣ�����ڶ�ȡ��վ��Ϣ����");
						catchWebside(enterWebside(webAdress));
						break;

					default:
						System.out.println("����δ֪�����룡������1-4���ڵ����֡����������");
						sc.close();
						System.exit(1);
				}
				System.out.println("��Ϣ��ȡ��ϣ����򼴽���������лʹ�á�");
				sc.close();
				System.exit(1);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
//	// 1.��������
//	public static void start() {
//		try {
//			System.out.println("������������������ַ��URL����");
//			Scanner sc = new Scanner(System.in);
//			String webAdress = sc.nextLine().trim();
//			System.out.println("��ѡ����Ϣ��ȡģʽ����1-4��");
//			System.out.println("1-��ȡ�����ַ��Ϣ");
//			System.out.println("2-��ȡ�ֻ�������Ϣ");
//			System.out.println("3-��ȡ�̶��绰��Ϣ");
//			System.out.println("4-��ȡ��ַ(URL)��Ϣ");
//			int modeSelect = sc.nextInt();
//
//			switch (modeSelect) {
//			case 1:
//				System.out.println("��ѡ���ȡ�����ַ��Ϣ�����ڶ�ȡ��վ��Ϣ����");
//				catchEmail(enterWebside(webAdress));
//				break;
//
//			case 2:
//				System.out.println("��ѡ���ȡ�ֻ�������Ϣ�����ڶ�ȡ��վ��Ϣ����");
//				catchMobilePhone(enterWebside(webAdress));
//				break;
//
//			case 3:
//				System.out.println("��ѡ���ȡ�̶��绰��Ϣ�����ڶ�ȡ��վ��Ϣ����");
//				catchPhoneNumber(enterWebside(webAdress));
//				break;
//
//			case 4:
//				System.out.println("��ѡ���ȡ��ַ(URL)��Ϣ�����ڶ�ȡ��վ��Ϣ����");
//				catchWebside(enterWebside(webAdress));
//				break;
//
//			default:
//				System.out.println("����δ֪�����룡������1-4���ڵ����֡����������");
//				sc.close();
//				System.exit(1);
//			}
//			System.out.println("��Ϣ��ȡ��ϣ����򼴽���������лʹ�á�");
//			sc.close();
//			System.exit(1);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//
//		}
//	}

	// 2.��ȡ��ַ��Ϣ����----------------------------------
	public static BufferedReader enterWebside(String webAdress) {
		try {
			// ����һ��url����,��ȡ�û�����ĵ�ַ��
			URL url = new URL(webAdress);
			// ������
			URLConnection opcon = url.openConnection();
			// �����������糬ʱʱ�� ��λΪ����
			opcon.setConnectTimeout(1000 * 10);
			// ͨ���� ������ȡָ�������ַ�е�������Ϣ
			BufferedReader bufr = new BufferedReader(new InputStreamReader(opcon.getInputStream()));
			return bufr;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("�����޷����ӣ�������ֹ����ȷ��������ַ��ʽ�Ƿ���ȷ��");
			System.exit(1);
		}
		return null;// ���ᱻִ��
	}

	// 3.����txt�ļ������������ȡ�������(������,������ʽ)----------------------------------
	public static void OutputInfo(BufferedReader bufr, String regex ) throws IOException {
		Scanner sc = new Scanner(System.in);
		//�ж��ļ��Ƿ��Ѵ��ڡ�Ϊ���⸲��Դ�ļ���д��
		if (!new File("D:/CatchResult.txt").exists()) {
			System.out.println("��Ϣ�����ļ������ɹ���λ��D:/CatchResult.txt");
		} else {
			System.out.println("��⵽��Ϣ�������Ѵ��ڣ��Ƿ�Ҫֱ�Ӹ���ԭ�ļ���(0-�����ǣ�1-����)");
			String select = sc.nextLine();
			while (true) {
				if ("0".equals(select)) {
					System.out.println("��ѡ�񲻸���Դ�ļ������򼴽���������лʹ�ã�");
					System.exit(1);
				} else {
					if ("1".equals(select)) {
						System.out.println("�Ѹ���ԭ�ļ���λ��D:/CatchResult.txt");
						break;
					} else {
						System.out.println("����δ֪�����룡");
						System.out.println("��⵽�ļ��Ѵ��ڣ��Ƿ�Ҫֱ�Ӹ���ԭ�ļ���(0-�����ǣ�1-����)");
						select = sc.nextLine();
					}
				}
			}
		}
		sc.close();
		
		// ���������
		PrintWriter pw = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:/CatchResult.txt"), "utf-8")));

		String line = null;
		// ʹ��ģʽ��compile()�������ɹ���ģʽ����
		Pattern p = Pattern.compile(regex);
		// �������
		while ((line = bufr.readLine()) != null) {
			Matcher m = p.matcher(line);// ����У��
			while (m.find()) {
				pw.println(m.group().trim());
				break;
			}
		}
		pw.flush();// ˢ�»���
		/**��������д���벻С�ĳ���һ��bug���������쳣-start**/
		int a = 0;
		int b=1/a;
		/**����������벻С�ĳ���һ��bug���������쳣-end**/
		//1.��Ϊ�������쳣����������Ĺر������벻��ִ��
		//2.��ʱ�����ڳ����쳣���߳��Ѿ�����
		//3.������ʱ�����̴߳�������״̬��ʵ������������ڴ�������ҵ�񣩣���������������˳�������������D:/CatchResult.txt�ļ�����Դ�����ͷţ���������ļ�����ɾ�����޸�
		pw.close();
	}

	// 4.1��ȡ�����ַ����----------------------------------
	public static void catchEmail(BufferedReader bufr) throws IOException {
		// ƥ��email������
		String regex = "[a-zA-Z0-9_-]+@\\w+\\.[a-z]+(\\.[a-z]+)?";
		OutputInfo(bufr, regex);
	}

	// 4.2��ȡ�ֻ��ŷ���----------------------------------
	public static void catchMobilePhone(BufferedReader bufr) throws IOException {
		// ƥ���ֻ��ŵ�����
		String regex = "(\\+86|0086)?\\s?1\\d{10}";
		OutputInfo(bufr, regex);
	}

	// 4.3��ȡ�̶��绰����----------------------------------
	public static void catchPhoneNumber(BufferedReader bufr) throws IOException {
		// ƥ��̶��绰������
		String regex = "^[0][1-9]{2,3}-[0-9]{5,10}$";
		OutputInfo(bufr, regex);
	}

	// 4.4��ȡ��ַ����----------------------------------
	public static void catchWebside(BufferedReader bufr) throws IOException {
		// ƥ����ַ������
		String regex = "[http|https]+[:/]+[0-9A-Za-z:/[-]_#[?][=][.][&]]*";
		OutputInfo(bufr, regex);
	}

}