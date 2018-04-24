package com.example.acer.watch.javabean;

import android.util.Log;

import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.net.*;
public class searchVideo {
    URL url;
    URLConnection conn;
    static BufferedReader read;
    //	该正则式子是用于过滤搜索界面的视频链接
//	该界面是搜索之后的选择电影界面
    static final String REGULAR_VIDEOITEM = "href=[\'\"][^http][\\w+-?\\.?/?]+[^list_][0-9]+\\.html[\'\"]";
    //	存放搜索页面视频链接和电影名字的集合
//	static List<String> videoUrlList;
//	static List<String> videoName;
//	存放bean的集合
    private static List<VideoNameAndURLBean> beanList = new ArrayList<>();
    //	正则式子（实现抓取搜索结果条目的连接）
    private static final String
            REGULAR_VIDEOURL = "(magnet|ftp|ed2k|thunder):(//|\\?)[^\\s^\">]*.?(rmvb|mp4|mkv|avi|torrent)?\"";
    //	ftp://[^\s">]*.(rmvb|mp4|mkv|avi)
//	正则式子，匹配电影名字
    private static final String
            REGULAR_VIDEONAME = "<title>.+</title>";

    //    该方法是总方法，执行所有方法，类似主方法
    public List<VideoNameAndURLBean> doAllMethod(String videoName) {
        try {
            searchVideo catc = new searchVideo();
            List<String> wangZhi = catc.setURL();
            List<List<String>> videoItem = catc.doPostSearch(videoName, wangZhi);
            List<String> videoList = catc.doAgainUrl(videoItem);
            List<String> videoHtmlContent = catc.getHtmlContent(videoList);
            List<VideoNameAndURLBean> allBeanList = getVideoURL(videoHtmlContent);
            return allBeanList;
        } catch (Exception e) {
           return null;
        }
    }

    //	该方法用于设置访问的网站集合
    private List<String> setURL() {
        List<String> list = new ArrayList<>();
//		飘花电影网搜索脚本，关键词：searchword
//		list.add("http://vod.cnzol.com/search.php");
//		电影天堂搜索脚本，关键词：keyword
        list.add("http://s.ygdy8.com/plus/so.php");
//		七七铺搜索脚本，关键词：searchword
//		list.add("http://www.qiqipu.com/search.asp");
//		迅雷铺搜索脚本，关键词：keyword
		list.add("http://www.xunleipu.com/plus/search.php");
        return list;
    }

    //	该方法用于一一打开以上网站连接，并获取网页内容
//	返回的是网页内容集合
    private List<String> getHtmlContent(List<String> urlList) {
//		存放网页内容的集合(存放的是 sb)
        List<String> htmlList = new ArrayList<>();
        htmlList.clear();
        for (String str : urlList) {
//		存放网页内容的StringBuilder
            StringBuilder sb = new StringBuilder();
            try {
                url = new URL(str);
                conn = url.openConnection();
                conn.setReadTimeout(5000);
                conn.setConnectTimeout(5000);
                read = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(),"gb2312"));
                String line;
                while ((line = read.readLine()) != null) {
                    sb.append(line + "\r\n");
                }
                htmlList.add(sb.toString());
                read.close();
            } catch (Exception e) {
                continue;
            }
        }
        return htmlList;
    }

    //	该方法用于获取该网页中的视频链接，并过滤是不是想要的链接
    private List<VideoNameAndURLBean> getVideoURL(List<String> htmlContent) {
        for (String str : htmlContent) {
            List<String> urlList = new ArrayList<>();
            List<String> videoNameList = new ArrayList<>();
            try {
//			匹配电影名字
//                Matcher matcherName = Pattern.compile
//                        (REGULAR_VIDEONAME).matcher(str);
//			匹配视频链接
                Matcher matcher = Pattern.compile
                        (REGULAR_VIDEOURL).matcher(str);
                while(matcher.find()){
                    urlList.add(matcher.group());
//					System.out.println(matcher.group());
                }
                if(urlList.size()!=0){
                    Matcher matcherName=Pattern.compile
                            (REGULAR_VIDEONAME).matcher(str);
                    while(matcherName.find()) {
                        videoNameList.add(matcherName.group());
//				System.out.println(matcherName.group());
                    }
                }
                if(urlList.size()!=0){
                    List<String> videoUrlList = filterString(urlList);
                    List<String> videoName = deleteTitle(filterString(videoNameList));
                    VideoNameAndURLBean bean = new VideoNameAndURLBean(videoName, videoUrlList);
                    beanList.add(bean);
                }else{
                    continue;
                }
            } catch (Exception e) {
                continue;
            }
        }
//		writer.close();
//		过滤集合
//		videoUrlList=filterString(urlList);
//		videoName=filterString(videoNameList);
//		VideoNameAndURLBean bean=new VideoNameAndURLBean(videoName,videoUrlList);
//		beanList.add(bean);
        return beanList;
    }

    //	该方法用于过滤重复的链接,还可以去除视频链接中的多余字符
//	比如：“ 这些符号
    private static List<String> filterString(List<String> list) {
        List<String> newList = new ArrayList<>();
        for (String str : list) {
            String url = str.substring(0, str.length() - 1);
            newList.add(url);
        }
        for (int i = 0; i < newList.size(); i++) {
            for (int j = newList.size() - 1; j > i; j--) {
                if (newList.get(j).equals(newList.get(i))) {
                    newList.remove(j);
                }
            }
        }
        return newList;
    }

    //	去除点电影名字中的<title></title>标签
    private static List<String> deleteTitle(List<String> videoName) {
        List<String> newVideoName = new ArrayList<>();
        for (String str : videoName) {
            String[] name = str.substring(7, str.length() - 8).split("_");
            newVideoName.add(name[0].toString());
        }
        return newVideoName;
    }

    //	根据用户输入进行搜索(参数是网址集合)
    private List<List<String>> doPostSearch(String videoName, List<String> wangZhi) throws Exception {
//		用于存放获取到的视频列表连接
        List<List<String>> video = new ArrayList<>();
        for (String wangZhiItem : wangZhi) {
            List<String> everyPageUrl=new ArrayList<>();
            URLConnection conn = new URL(wangZhiItem).openConnection();
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            PrintWriter writer = new PrintWriter(
                    new OutputStreamWriter(conn.getOutputStream()));
            writer.print("keyword");
            writer.print('=');
            writer.print(URLEncoder.encode(videoName, "gb2312"));
            writer.flush();
            writer.close();
//			获取返回的数据
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "gb2312"));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\r\n");
                Matcher matcher = Pattern
                        .compile(REGULAR_VIDEOITEM).matcher(line);
                while (matcher.find()) {
				System.out.println(matcher.group());
                    everyPageUrl.add(matcher.group());
                }
            }
            Log.d("everyPageUrl数组长度"," "+everyPageUrl.size());
//          过滤一下重复的链接
            for(int i=0;i<everyPageUrl.size();i++) {
                for(int j=everyPageUrl.size()-1;j>i;j--) {
                    if(everyPageUrl.get(j).equals(everyPageUrl.get(i))) {
                        everyPageUrl.remove(j);
                    }
                }
            }
            Log.d("过滤后everyPageUrl数组长度"," "+everyPageUrl.size());
            video.add(everyPageUrl);
            reader.close();
        }
        return video;
    }

    //	该方法用于重组上面的方法返回的数据
//	将获取的数据重新组合成一个可以打开的连接
    private List<String> doAgainUrl(List<List<String>> href) {
        List<String> videoList=new ArrayList<>();
        for(int j=0;j<href.size();j++) {
            List<String> onePageUrl=href.get(j);
            for(String str : onePageUrl) {
//			截取字符串，（去掉“href='和'”）
                String hr=str.substring(6,str.length()-1);
//			根据数组来重组URL（防止胡乱组合，导致网页打不开）
                String reallyUrl="";
                switch(j) {
                    case 0: reallyUrl="http://www.ygdy8.com";break;
                    case 1: reallyUrl="http://www.xunleipu.com";break;
                }
                String videoHref=reallyUrl+hr;
                videoList.add(videoHref);
            }
        }
        return videoList;
    }
}
//    public static void main(String[] args) throws Exception {
//        searchVideo catc=new searchVideo();
//        List<String> wangZhi=catc.setURL();
//        List<String> videoItem=catc.doPostSearch(wangZhi);
//        List<String> videoList=catc.doAgainUrl(videoItem);
//        for(String str : videoList) {
//            System.out.println(str);
//        }
//        List<String> videoHtmlContent=catc.getHtmlContent(videoList);
//        System.out.println("共有"+videoHtmlContent.size()+"个页面");
//        getVideoURL(videoHtmlContent);
//			测试获取指定位置的URL
//			VideoNameAndURLBean benak=beanList.get(1);
//			List<String> strList=benak.getVideoUrlList();
//			for(String str : strList) {
//				System.out.println(str);
//			}
//			获取Bean类的数据
//        for(VideoNameAndURLBean bean: beanList) {
//            List<String> videoNameList=bean.getVideoNameList();
//            List<String> videoDownURL=bean.getVideoUrlList();
//            if(videoDownURL.size()!=0&&videoNameList.size()!=0){
//                for(String str : videoNameList) {
//                    System.out.println(str);
//                }
//                for(String str : videoDownURL) {
//                    System.out.println(str);
//                }
//            }
//        }
//    }
//}
