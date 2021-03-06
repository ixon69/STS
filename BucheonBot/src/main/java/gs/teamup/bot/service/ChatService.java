package gs.teamup.bot.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import gs.teamup.bot.jdbc.client.dao.ClientDAO;
import gs.teamup.bot.jdbc.client.model.Client;
import gs.teamup.bot.jdbc.customer.dao.CustomerDAO;
import gs.teamup.bot.jdbc.itcuser.model.ItcUser;
import gs.teamup.bot.pojo.edge.Button;
import gs.teamup.bot.pojo.edge.ChatMessage;
import gs.teamup.bot.pojo.edge.ExtraV2;
import gs.teamup.bot.pojo.event.TeamupEventChat;
import gs.teamup.bot.template.teamup.EdgeTemplate;
import gs.teamup.bot.jdbc.common.StringUtil;

@Component
@EnableScheduling
public class ChatService {
	private static final Log log = LogFactory.getLog(ChatService.class);
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module-Client.xml");
	ApplicationContext context1 = new ClassPathXmlApplicationContext("Spring-Module.xml");
	ClientDAO clientDAO = (ClientDAO) this.context.getBean("clientDAO");
	CustomerDAO customerDAO = (CustomerDAO) this.context1.getBean("customerDAO");
	@Autowired
	private EdgeTemplate edgeTemplate;
	@Value("${project.version}")
	private String version;
	private String infoCmt = "???????????????.\n?????????????????????(?????????) ?????? ???????????? ??????????????? ???????????? ????????????.\n\n"
			+ "(?????? ?????? ??????) ?????????\n"
			+ "(?????? ?????? ??????) ???5\n"
			+ "(?????? ?????? ??????) ??????  ??????\n"
	        + "(?????? ?????? ??????) 2379";

    public void doWelcome(TeamupEventChat eventChat) { // ????????? ?????? ??? ??????
        this.edgeTemplate.say(eventChat.getRoom(), this.infoCmt);
    }
	
	public void doChat(ChatMessage chatMessage, int room) throws UnsupportedEncodingException {
		String content = chatMessage.getContent().trim();
		if ((content.equals("/?")) || (content.equals("/help")) || (content.equals("/?????????")) || (content.equals("?"))
				|| (content.equals("help")) || (content.equals("?????????"))) {
			this.edgeTemplate.say(room, this.infoCmt);
		} else if (content.equals("ver")) {
			this.edgeTemplate.say(room, this.version);
		}
		
		else if (StringUtil.isNumeric(content)) {
			StringBuffer msg = new StringBuffer();
			List<String> plist = this.customerDAO.getPhoneName(content);
			
			if (plist.size() == 0) {
	        	this.edgeTemplate.say(room, "??????????????? ????????????. ???????????? ?????? ????????? ?????????.\n(?????? ?????? ??????) ?????????\n(?????? ?????? ??????) ???5\n(?????? ?????? ??????) ??????  ??????\n(?????? ?????? ??????) 2379");
	        } else {
	        	for(Object obj : plist) {
	        		msg.append((String)obj+"\n");
	        	}
	        	this.edgeTemplate.say(room, msg.toString());
	        }
		}

		else {
			StringBuffer msg = new StringBuffer();
			List<String> plist = this.customerDAO.getPhoneNumber(content);
			
			if (plist.size() == 0) {
	        	this.edgeTemplate.say(room, "??????????????? ????????????. ???????????? ?????? ????????? ?????????.\n(?????? ?????? ??????) ?????????\n(?????? ?????? ??????) ???5\n(?????? ?????? ??????) ??????  ??????\n(?????? ?????? ??????) 2379");
	        } else {
	        	for(Object obj : plist) {
	        		msg.append((String)obj+"\n");
	        	}
	        	this.edgeTemplate.say(room, msg.toString());
	        }
		}

/*		else if (content.startsWith("/????????????"))
	    {
	      if ((content.equals("/????????????")) || (content.equals("/????????????/")))
	      {
	        this.edgeTemplate.say(room, this.infoCmt);
	      }
	      else
	      {
	    	  String msg=this.customerDAO.getDeptDoctorCode(content);
	        
	        String infoMsg = "==???????????? ????????? ?????????????????????.==\n\n";
	        
	       
	        if (msg.equals("[]")) {
	          this.edgeTemplate.say(room, "??????????????? ????????????. ???????????? ?????? ????????? ?????????.");
	        } else {
	        String deptcode="https://www.cmcseoul.or.kr/page/department/A/";
	          this.edgeTemplate.say(room, infoMsg +deptcode+msg+"/3");
	        }
	      }
	    }
	    
	    else if (content.startsWith("/??????????????????"))
	    {
	      if ((content.equals("/??????????????????")) || (content.equals("/??????????????????/")))
	      {
	        this.edgeTemplate.say(room, this.infoCmt);
	      }
	      else
	      {
	    	  String msg=this.customerDAO.getDeptDoctorCode(content);
	        
	        String infoMsg = "==???????????? ????????? ???????????????????????????.==\n\n";
	        
	       
	        if (msg.equals("[]")) {
	          this.edgeTemplate.say(room, "??????????????? ????????????. ???????????? ?????? ????????? ?????????.");
	        } else {
	        String deptcode="https://www.cmcseoul.or.kr/page/department/A/";
	          this.edgeTemplate.say(room, infoMsg +deptcode+msg+"/2");
	        }
	      }
	    }
	    
		else if (content.startsWith("/??????")) {
			if ((content.equals("/??????")) || (content.equals("/??????/"))) {
				this.edgeTemplate.say(room, this.infoCmt);
			} else {
				String msg = this.customerDAO.getSvrHealth(content);

				String infoMsg = "==???????????? ==\n\n";

				if (msg.equals("[]")) {
					this.edgeTemplate.say(room, "????????? ????????????. AP????????? ??????????????????.");
				} else {
					String deptcode = "??????????????? ??????????????????. \n\n";
					this.edgeTemplate.say(room, infoMsg + deptcode + msg);
				}
			}
		}

		else if (content.startsWith("/??????")) {
			if ((content.equals("/??????")) || (content.equals("/??????/"))) {
				this.edgeTemplate.say(room, this.infoCmt);
			} else {
				List<Client> clients = this.clientDAO.findTelbyDeptname(content);

				String infoMsg = "==???????????? ?????????????????????.==\n\n";

				String clmsg = clients.toString().substring(1).replaceFirst("]", "").replace(", ", "");
				if (clmsg.equals("[]")) {
					this.edgeTemplate.say(room, "??????????????? ????????????. ???????????? ?????? ????????? ?????????.");
				} else {
					this.edgeTemplate.say(room, infoMsg + clmsg);
				}
			}
		} else if (content.startsWith("/??????")) {
			String[] UserValues = content.split("/");
			String instStr = UserValues[1];
			System.out.println(UserValues[1]);
			String HosStr = UserValues[2];
			System.out.println(UserValues[2]);
			if ((content.equals("/??????")) || (content.equals("/??????/"))) {
				this.edgeTemplate.say(room, this.infoCmt);
			} else {
				String infoMsg = "==???????????? ?????????????????????.==\n\n";
				String baselink = null;
				if (HosStr.startsWith("??????")) {
					baselink = "https://m.cmcseoul.or.kr/";
				} else if (HosStr.startsWith("?????????")) {
					baselink = "https://m.cmcsungmo.or.kr/";
				} else if (HosStr.startsWith("?????????")) {
					baselink = "https://m.cmcujb.or.kr/";
				} else if (HosStr.startsWith("?????????")) {
					baselink = "https://m.cmcvincent.or.kr/";
				} else if (HosStr.startsWith("?????????")) {
					baselink = "https://m.cmcbaoro.or.kr/";
				} else if (HosStr.startsWith("??????")) {
					baselink = "https://m.cmcbucheon.or.kr/";
				}
				String infoLink = baselink + "hospitalguide.map.sp";
				this.edgeTemplate.say(room, infoMsg + infoLink);
			}
		} else if (content.startsWith("/itc")) {
			if ((content.equals("/itc")) || (content.equals("/itc/"))) {
				this.edgeTemplate.say(room, this.infoCmt);
			} else {
				List<ItcUser> itu = this.clientDAO.findUserbyPost(content);
				String infoMsg = "==???????????? ??????ITC ???????????????.==\n\n";

				String clmsg = itu.toString().substring(1).replaceFirst("]", "").replace(", ", "");
				if (clmsg.equals("[]")) {
					this.edgeTemplate.say(room, "??????????????? ????????????. ???????????? ?????? ????????? ?????????.");
				} else {
					this.edgeTemplate.say(room, infoMsg + clmsg);
				}
			}
		} else if (content.startsWith("/??????")) {
			String[] UserValues = content.split("/");
			String instStr = UserValues[1];
			System.out.println(UserValues[1]);
			String DeptStr = UserValues[2];
			System.out.println(UserValues[2]);
			if ((content.equals("/??????")) || (content.equals("/??????/"))) {
				this.edgeTemplate.say(room, this.infoCmt);
			} else {
				String infoMsg = "==???????????? ??????????????????.==\n\n";

				String infoLink = "https://m.cmcseoul.or.kr/page/hospitalguide/lookaround?placeNm=" + DeptStr;
				this.edgeTemplate.say(room, infoMsg + infoLink);
			}
		} else if (content.startsWith("/??????")) {
			String[] UserValues = content.split("/");
			String instStr = UserValues[1];
			System.out.println(UserValues[1]);
			String Pid = UserValues[2];
			System.out.println(UserValues[2]);
			try {
				String url = "http://erp001.cmcnu.or.kr/cmcnu/webapps/mi/rp_humtrafactmngtweb/.live?submit_id=DRRPB00101&ep_interface=&business_id=mi&emplno="
						+ Pid + "&instcd=012&cbflag=Y";

				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc = dBuilder.parse(url);

				doc.getDocumentElement().normalize();
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

				NodeList nList = doc.getElementsByTagName("empllist");
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == 1) {
						String viewfairnmdd = "";
						String viewbaptnm = "";
						Element eElement = (Element) nNode;
						String tmpfairnmdd = getTagValue("fairnmdd", eElement);
						String tmpbaptnm = getTagValue("baptnm", eElement);
						if ((tmpfairnmdd == "-") || (tmpbaptnm == "-")) {
							viewbaptnm = "-";
							viewfairnmdd = "-";
						} else {
							viewfairnmdd = tmpfairnmdd.substring(0, 2) + "???" + tmpfairnmdd.substring(2, 4) + "???";
							viewbaptnm = tmpbaptnm;
						}
						this.edgeTemplate.say(room, "???????????? ????????? ???????????????.\n######################\n??????:" +

								getTagValue("name", eElement) + '\n' + "??????:" + getTagValue("emplno", eElement) + '\n'
								+ "??????:" + getTagValue("unitnm", eElement) + '\n' + "????????????:"
								+ getTagValue("hosinseqno", eElement) + '\n' + "?????????:"
								+ getTagValue("mpphonno", eElement) + '\n' + "?????????:" + viewbaptnm + '\n' + "??????:"
								+ viewfairnmdd + '\n');
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (content.startsWith("/??????")) {
			String[] UserValues = content.split("/");
			String instStr = UserValues[1];
			System.out.println(UserValues[1]);
			String usernm = UserValues[2];
			System.out.println(UserValues[2]);
			try {
				// String url =
				// "http://erp001.cmcnu.or.kr/cmcnu/webapps/mi/rp_humtrafactmngtweb/.live?submit_id=DRRPB00101&ep_interface=&business_id=mi&emplno=&instcd=012&cbflag=Y&name="
				// + usernm;
				// 20190208
				String url = "http://erp001.cmcnu.or.kr/cmcnu/webapps/mi/rp_humtrafactmngtweb/.live?submit_id=DRRPB00166&ep_interface=&business_id=mi&emplno=&instcd=012&cbflag=Y&name="
						+ usernm;
				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc = dBuilder.parse(url);

				doc.getDocumentElement().normalize();
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

				NodeList nList = doc.getElementsByTagName("empllist");
				System.out.println("????????? ????????? ??? : " + nList.getLength());
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == 1) {
						String viewfairnmdd = "";
						String viewbaptnm = "";
						Element eElement = (Element) nNode;
						String tmpfairnmdd = getTagValue("fairnmdd", eElement);
						String tmpbaptnm = getTagValue("baptnm", eElement);
						if ((tmpfairnmdd == "-") || (tmpbaptnm == "-")) {
							viewbaptnm = "-";
							viewfairnmdd = "-";
						} else {
							viewfairnmdd = tmpfairnmdd.substring(0, 2) + "???" + tmpfairnmdd.substring(2, 4) + "???";
							viewbaptnm = tmpbaptnm;
						}
						this.edgeTemplate.say(room, "???????????? ????????? ???????????????.\n######################\n??????:" +

								getTagValue("name", eElement) + '\n' + "??????:" + getTagValue("emplno", eElement) + '\n'
								+ "??????:" + getTagValue("unitnm", eElement) + '\n' + "????????????:"
								+ getTagValue("hosinseqno", eElement) + '\n' + "?????????:"
								+ getTagValue("mpphonno", eElement) + '\n' + "?????????:" + viewbaptnm + '\n' + "??????:"
								+ viewfairnmdd + '\n');
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (content.startsWith("/??????")) {
			String tmupidx = "/??????/" + chatMessage.getUser().toString();
			String userid = this.clientDAO.findPidByTmupidx(tmupidx);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

			Calendar c1 = Calendar.getInstance();

			String strToday = sdf.format(c1.getTime());
			try {
				String url = "http://erp001.cmcnu.or.kr/cmcnu/webapps/mi/rp_dligclaznsmngtweb/.live?submit_id=DRRPD30001&ep_interface=&business_id=mi&emplno="
						+ userid + "&instcd=012&dutym=" + strToday;

				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc = dBuilder.parse(url);

				doc.getDocumentElement().normalize();
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

				NodeList nList = doc.getElementsByTagName("offcntlist");
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == 1) {
						Element eElement = (Element) nNode;
						System.out.println("######################");
						this.edgeTemplate.say(room, "???????????? ?????? ???????????????.\n######################\n????????????:" +

								getTagValue("genryearno", eElement) + '\n' + "????????????:"
								+ getTagValue("useyearno", eElement) + '\n' + "????????????:"
								+ getTagValue("spreyearno", eElement) + "??? ?????????. " + '\n');
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (content.startsWith("/??????")) {
			String[] UserValues = content.split("/");
			String instStr = UserValues[1];
			System.out.println(UserValues[1]);
			String deptNm = UserValues[2];
			System.out.println(UserValues[2]);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Calendar c1 = Calendar.getInstance();
			String strToday = sdf.format(c1.getTime());
			try {
				String url = "http://emr012.cmcnu.or.kr/cmcnu/.live?submit_id=DRMMO30009&business_id=mr&instcd=012&workdd="
						+ strToday + "&orddeptnm=" + deptNm;

				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc = dBuilder.parse(url);

				doc.getDocumentElement().normalize();
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
				System.out.println("url :" + url);

				NodeList nList = doc.getElementsByTagName("chatbotworkdrinfo");
				System.out.println("????????? ????????? ??? : " + nList.getLength());
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == 1) {
						Element eElement = (Element) nNode;
						System.out.println("######################");
						this.edgeTemplate.say(room,
								"???????????? ?????? ???????????????.\n######################\n????????????:" + getTagValue("partcdnm", eElement)
										+ '\n' + "??????????????????:" + getTagValue("partfromtotm", eElement) + '\n' + "??????1:"
										+ getTagValue("drnm1", eElement) + '\n' + "??????2:"
										+ getTagValue("drnm2", eElement) + '\n' + "??????3:"
										+ getTagValue("drnm3", eElement) + '\n' + "??????:" + getTagValue("drnm4", eElement)
										+ '\n');
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (content.startsWith("/????????????")) {
			String[] UserValues = content.split("/");
			String instStr = UserValues[1];
			System.out.println(UserValues[1]);
			String Pid = UserValues[2];
			System.out.println(UserValues[2]);
			try {
				String url = "http://emr012.cmcnu.or.kr/cmcnu/.live?submit_id=DRPMH00002&business_id=pm&instcd=012&pid="
						+ Pid;

				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc = dBuilder.parse(url);

				doc.getDocumentElement().normalize();
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

				NodeList nList = doc.getElementsByTagName("mychart_rsrv_o");
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == 1) {

						Element eElement = (Element) nNode;

						this.edgeTemplate.say(room, "???????????? ?????????????????????.\n######################\n??????:" +

								getTagValue("gubun", eElement) + '\n' + "?????????:" + getTagValue("rsrvdt", eElement) + '\n'
								+ "??????:" + getTagValue("orddeptnm", eElement) + '\n' + "?????????:"
								+ getTagValue("orddrnm", eElement) + '\n');
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// pacs
		else if ((content.startsWith("/??????")) || (content.startsWith("/pacs")) || (content.startsWith("/PACS"))) {
			pacsCheck(room, content);
		}

		else if (content.startsWith("/??????")) {
			String[] UserValues = content.split("/");
			String instStr = UserValues[1];
			System.out.println(UserValues[1]);
			String userNm = UserValues[2];
			System.out.println(UserValues[2]);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Calendar c1 = Calendar.getInstance();
			String strToday = sdf.format(c1.getTime());
			//http://emr012.cmcnu.or.kr/cmcnu/.live?submit_id=DRPMC02100&&ex_interface=MOB%7C012&business_id=pm&srchcondflag=1&instcd=012&srchcond
			try {
				String url = "http://emr012.cmcnu.or.kr/cmcnu/.live?submit_id=DRPMC02100&&ex_interface=MOB%7C012&business_id=pm&srchcondflag=1&instcd=012&srchcond="
						+ userNm;
				
				// String url = "http://emr012.cmcnu.or.kr/cmcnu/.live?submit_id=DRPMC02100&ep_interface=MOB|012&business_id=pm&srchcondflag=1&instcd=012&srchcond="
				
				

				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc = dBuilder.parse(url);

				doc.getDocumentElement().normalize();
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
				System.out.println("url :" + url);

				NodeList nList = doc.getElementsByTagName("inhosppatlist");
				System.out.println("????????? ????????? ??? : " + nList.getLength());
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == 1) {
						Element eElement = (Element) nNode;
						System.out.println("######################");
						this.edgeTemplate.say(room,
								"???????????? ?????? ???????????????.\n######################\n??????:" + getTagValue("hngnm", eElement) + '\n'
										+ "??????:" + getTagValue("sexnm", eElement) + '\n' + "??????:"
										+ getTagValue("age", eElement) + '\n' + "?????????:"
										+ getTagValue("orddeptnm", eElement) + '\n' + "??????:"
										+ getTagValue("roomnm", eElement) + '\n' + '\n');
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
*/
	}

	private void pacsCheck(int room, String content) {
		String pacsrltmsg = getPacsSvrHealth(content);
		this.edgeTemplate.say(room, pacsrltmsg);
	}

	public String getPacsSvrHealth(String content) {
		String[] UserValues = content.split("/");
		String instStr = UserValues[1];
		System.out.println(UserValues[1]);
		String HosStr = UserValues[2];
		System.out.println(UserValues[2]);

		String baselink = null;
		if (HosStr.startsWith("??????")) {
			baselink = "http://m012.cmcnu.or.kr:8080/xwado/wadoserver.svc/";

			// baselink =
			// "http://m012.cmcnu.or.kr:8080/xWADO20/PushWADO?requestType=WADO&contentType=image/jpeg&studyUID=1.2.410.200003.1037.1.0.14802403.20180306.141500.1806406419.1&seriesUID=1.3.46.670589.26.200078.2.20180306.142815.117490&&objectUID=1.3.46.670589.26.200078.4.20180306.144126.117490.0&dicom=\\160.1.73.35\LTN07\20180306\CR\14802403_20180306_1806406419_CR\14802403_20180306_142815_117490_00000_CR.dcm";
		} else if (HosStr.startsWith("?????????")) {
			baselink = "http://m011.cmcnu.or.kr:8080/xwadodata/wadoserver.svc/";
		} else if (HosStr.startsWith("?????????")) {
			baselink = "http://m013.cmcnu.or.kr:8080/xwadodata/wadoserver.svc/";
		} else if (HosStr.startsWith("?????????")) {
			baselink = "http://m017.cmcnu.or.kr:8080/xwadodata/wadoserver.svc/";
		} else if (HosStr.startsWith("?????????")) {
			baselink = "http://m015.cmcnu.or.kr:8080/xwadodata/wadoserver.svc/";
		}

		String pacsrltmsg = "";

		try {
			// URL url = new
			// URL("http://emr015.cmcnu.or.kr/cmcnu/.live?submit_id=DRMOB01001&business_id=mb&ex_interface=MOB%7C015&instcd=015&imgetype=IMG&imgekey=104943525");
			URL url = new URL(baselink);
			InputStream openStream = url.openStream();
			InputStreamReader isr1 = new InputStreamReader(openStream, "UTF-8");
			BufferedReader bis1 = new BufferedReader(isr1);
			System.out.println("-------------------------------------------------------");
			System.out.println("??????????????? : ");
			while (true) {
				String str = bis1.readLine(); // ????????? ?????????
				if (str == null) {
					break;
				}
				System.out.println(str);

				if (str.contains("xWADO")) {
					pacsrltmsg = HosStr + " PACS ????????? ????????????????????????." + '\n' + str;
				} else {
					pacsrltmsg = "??????????????? ????????? ?????????";
				}

			}

			bis1.close();
			isr1.close();

		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			pacsrltmsg = "??????????????? ????????? ?????????";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			pacsrltmsg = "??????????????? ????????? ?????????";
		}
		return pacsrltmsg;
	}

	public String getPacsSvrHealthAll() {
		String svresult = "";
		String result = "";
		String msg011 = this.getPacsSvrHealth("/PACS/?????????");
		String msg012 = this.getPacsSvrHealth("/PACS/??????");
		String msg013 = this.getPacsSvrHealth("/PACS/?????????");
		String msg015 = this.getPacsSvrHealth("/PACS/?????????");
		String msg017 = this.getPacsSvrHealth("/PACS/?????????");

		if (!(msg011.contains("????????????"))) {
			svresult = svresult + "?????????,";
		}
		if (!(msg012.contains("????????????"))) {
			svresult = svresult + "??????,";
		}
		if (!(msg013.contains("????????????"))) {
			svresult = svresult + "?????????,";
		}
		if (!(msg015.contains("????????????"))) {
			svresult = svresult + "?????????,";
		}
		if (!(msg017.contains("????????????"))) {
			svresult = svresult + "?????????";
			System.out.println("svr 017 : " + svresult);

		}

		if (svresult.contentEquals("")) {
			System.out.println("svr cont : " + svresult);
			result = "?????? PACS ????????? ????????????????????????.";
		} else {
			result = svresult + " PACS ????????? ?????????????????????";
		}

		return result;

	}

	private static String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = nlList.item(0);
		if (nValue == null) {
			return "-";
		}
		return nValue.getNodeValue();
	}
}
