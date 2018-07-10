package org.quickstart.remoting.hessian.example.rpc;

import java.io.InputStream;
import java.util.List;

import org.quickstart.remoting.hessian.example.rpc.bean.Person;

public interface HelloHessian {

	public String sayHello();

	public String sayHello(String name);

	public List<Person> getPersons();

	public Person getPersonById(int id);

	public boolean uploadFile(String fileName, InputStream data);

	public byte[] downloadFile(String fileName);
}
