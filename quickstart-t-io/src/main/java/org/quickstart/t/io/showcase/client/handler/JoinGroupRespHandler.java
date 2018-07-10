package org.quickstart.t.io.showcase.client.handler;

import org.quickstart.t.io.showcase.common.ShowcasePacket;
import org.quickstart.t.io.showcase.common.intf.AbsShowcaseBsHandler;
import org.quickstart.t.io.showcase.common.packets.JoinGroupRespBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 * @author tanyaowu
 * 2017年3月27日 下午9:51:28
 */
public class JoinGroupRespHandler extends AbsShowcaseBsHandler<JoinGroupRespBody> {
	private static Logger log = LoggerFactory.getLogger(JoinGroupRespHandler.class);

	/**
	 * @param args
	 * @author tanyaowu
	 */
	public static void main(String[] args) {

	}

	/**
	 *
	 * @author tanyaowu
	 */
	public JoinGroupRespHandler() {
	}

	/**
	 * @return
	 * @author tanyaowu
	 */
	@Override
	public Class<JoinGroupRespBody> bodyClass() {
		return JoinGroupRespBody.class;
	}

	/**
	 * @param packet
	 * @param bsBody
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author tanyaowu
	 */
	@Override
	public Object handler(ShowcasePacket packet, JoinGroupRespBody bsBody, ChannelContext channelContext) throws Exception {
		System.out.println("收到进群响应消息:" + Json.toJson(bsBody));

		if (JoinGroupRespBody.Code.SUCCESS.equals(bsBody.getCode())) {
			Aio.bindGroup(channelContext, bsBody.getGroup());
			System.out.println("进入群组[" + bsBody.getGroup() + "]成功");
		}

		return null;
	}
}
