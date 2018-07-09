package org.quickstart.remoting.hessian.example.rpc.client;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.quickstart.remoting.hessian.example.rpc.HelloHessian;
import org.quickstart.remoting.hessian.example.rpc.bean.Person;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * Hessian RPC
 */
public class HelloHessianClient {

	public static String urlName = "http://localhost:8080/hessian-server/HelloHessian";

	public static void main(String[] args) throws MalformedURLException {

		HessianProxyFactory factory = new HessianProxyFactory();
		// 开启方法重载
		factory.setOverloadEnabled(true);

		HelloHessian helloHession = (HelloHessian) factory.create(
				HelloHessian.class, urlName);

		// 调用方法
		System.out.println("call sayHello():" + helloHession.sayHello());
		System.out.println("call sayHello(\"Tom\"):"
				+ helloHession.sayHello("Tom"));
		System.out.println("call getPersons():");

		// 调用方法获取集合对象
		List<Person> persons = helloHession.getPersons();
		if (null != persons && persons.size() > 0) {
			for (Person p : persons) {
				System.out.println(p.toString());
			}
		} else {
			System.out.println("No person.");
		}

		// 通过参数调用方法获取对象
		int id = 2;
		System.out.println(String.format("call getPersonById(%d)", id));
		Person person = helloHession.getPersonById(id);
		if (null != person) {
			System.out.println(person.toString());
		} else {
			System.out.println("Id is " + id + " person not exist.");
		}

		// 上传文件
		String fileName = "upload.txt";
		String filePath = System.getProperty("user.dir") + "/temp/" + fileName;
		InputStream data = null;
		try {
			data = new BufferedInputStream(new FileInputStream(filePath));
			if (helloHession.uploadFile(fileName, data)) {
				System.out.println("Upload file " + filePath + " succeed.");
			} else {
				System.out.println("Upload file " + filePath + " failed.");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(data);
		}

		// 下载文件
		fileName = "download.txt";
		filePath = System.getProperty("user.dir") + "/temp/" + fileName;
		try {

			byte[] temp = helloHession.downloadFile(fileName);
			if (null != temp) {
				FileWriter output = new FileWriter(filePath);
				IOUtils.write(temp, output, "UTF-8");
				System.out.println("Download file " + filePath + " succeed.");
				output.close();
			} else {
				System.out.println("Download file " + filePath + " failed.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
