/*package com.yaoyao.sha;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.yaoyao.utils.FastDFSClient;

public class TestFastDFS {

	@Test
	public void testUploadFile() throws Exception {
		// 1.向工程添加jar包
		// 2.创建一个配置文件，配置tracker服务器地址
		// 3.加载配置文件
		ClientGlobal.init("G:\\workspace\\shop\\shop-manager-web\\src\\main\\resources\\resource\\client.conf");
		// 4.创建一个TrackerClient对象
		TrackerClient trackerClient = new TrackerClient();
		// 5.使用TrackerClient对象获得trackerserver对象。
		TrackerServer trackerServer = trackerClient.getConnection();
		// 6.创建一个StorageServer的引用，我们用null就可以
		StorageServer storageServer = null;
		// 7.创建一个StorageClient对象。trackerserver、StorageServer两个参数
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		// 8.使用StorageClient对象上传文件
		NameValuePair[] metaList = new NameValuePair[3];
		metaList[0] = new NameValuePair("fileName", "2");
		metaList[1] = new NameValuePair("createTime", "2018-03-03 16:01:00");
		metaList[2] = new NameValuePair("createUser", "yaoyao");
		String[] upload_files = storageClient
				.upload_file("F:\\QQ\\2478412512\\Image\\Thumbnails\\[$@~U7KY{483WHQK2GKG)5M.jpg", "jpg", metaList);
		for (String file : upload_files) {
			System.out.println(file);
		}
	}

	@Test
	public void testFastDFSClient() throws Exception {
		FastDFSClient fastDFSClient = new FastDFSClient(
				"G:\\workspace\\shop\\shop-manager-web\\src\\main\\resources\\resource\\client.conf");
		String imgPath = fastDFSClient.uploadFile("F:\\QQ\\2478412512\\Image\\Thumbnails\\[$@~U7KY{483WHQK2GKG)5M.jpg");
		System.out.println(imgPath);
	}
}
*/