package org.quickstart.remoting.hessian.example.rpc.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import org.quickstart.remoting.hessian.example.rpc.bean.Person;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;

/**
 * 测试Hessian的序列号和反序列化
 */
public class Test1 {

	public static void main(String[] args) throws IOException {

		// 序列化
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Hessian2Output h2o = new Hessian2Output(os);

		h2o.startMessage();
		h2o.writeBoolean(true);
		h2o.writeInt(22);

		Person person = new Person();
		person.setAddress(new String[] { "Beijing", "TaiWan", "GuangZhou" });
		person.setBrithday(new Date());
		person.setGender(false);
		person.setHeight(168.5D);
		person.setId(300);
		person.setName("Jack");
		person.setPhone(188888888);
		person.setWeight(55.2F);
		h2o.writeObject(person);

		h2o.completeMessage();
		h2o.close();

		byte[] buffer = os.toByteArray();
		os.close();

		// 反序列化
		ByteArrayInputStream is = new ByteArrayInputStream(buffer);
		Hessian2Input h2i = new Hessian2Input(is);
		h2i.setSerializerFactory(SerializerFactory.createDefault());

		h2i.startMessage();
		System.out.println(h2i.readBoolean());
		System.out.println(h2i.readInt());
		System.out.println(h2i.readObject().toString());
		h2i.completeMessage();
		h2i.close();
		is.close();
	}
}
