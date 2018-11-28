package com.taotao.util;

import org.csource.fastdfs.*;

public class UploadUtil {

	/***
     * FastDFS图片上传工具类
	 * @param trackerserver		
	 * 				tracker服务端链接的socket链接IP和端口，格式 tracker_server=192.168.174.130:22122
	 * 				这里的trackerserver是完整的文件，供客户端代码读取解析，文件格式如上
	 * 
	 * @param buffer	文件的字节数组
	 * @param subfix	上传的文件后缀
	 * @return
	 */
	public static String[] upload(String trackerserver, byte[] buffer, String subfix) {
		try {
			ClientGlobal.init(trackerserver);
			TrackerClient tracker = new TrackerClient();
			TrackerServer trackerServer = tracker.getConnection();
			StorageServer storageServer = null;
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
			String[] fileinfos= storageClient.upload_file(buffer, subfix, null);
			return fileinfos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String[] upload(String trackerserver, String vfile) {
		try {
			ClientGlobal.init(trackerserver);
			TrackerClient tracker = new TrackerClient();
			TrackerServer trackerServer = tracker.getConnection();
			StorageServer storageServer = null;
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
			String[] fileinfos= storageClient.upload_file(vfile, null, null);
			return fileinfos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String path = System.getProperty("user.dir")+"/src/main/resources/";
		System.out.println(path);
		String vfile = path+"2.jpg";
		String[] fileinfos = upload(path+"tracker.conf", vfile);
		for (String string : fileinfos) {
			System.out.println(string);
		}

		String weblik = "http://fastimg.taotao.com/"+fileinfos[0]+"/"+fileinfos[1];
		System.out.println(weblik);
	}

}

