package org.quickstart.t.io.showcase.server.handler;

import org.quickstart.t.io.showcase.common.ShowcasePacket;
import org.quickstart.t.io.showcase.common.Type;
import org.quickstart.t.io.showcase.common.intf.AbsShowcaseBsHandler;
import org.quickstart.t.io.showcase.common.packets.GroupMsgReqBody;
import org.quickstart.t.io.showcase.common.packets.GroupMsgRespBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 * @author tanyaowu
 * 2017年3月27日 下午9:51:28
 */
public class GroupMsgReqHandler extends AbsShowcaseBsHandler<GroupMsgReqBody> {
	private static Logger log = LoggerFactory.getLogger(GroupMsgReqHandler.class);

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
	public GroupMsgReqHandler() {
	}

	/**
	 * @return
	 * @author tanyaowu
	 */
	@Override
	public Class<GroupMsgReqBody> bodyClass() {
		return GroupMsgReqBody.class;
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
	public Object handler(ShowcasePacket packet, GroupMsgReqBody bsBody, ChannelContext channelContext) throws Exception {
		log.info("收到群聊请求消息:{}", Json.toJson(bsBody));
		GroupMsgRespBody groupMsgRespBody = new GroupMsgRespBody();
		groupMsgRespBody.setText(bsBody.getText());
		groupMsgRespBody.setToGroup(bsBody.getToGroup());

		ShowcasePacket respPacket = new ShowcasePacket();
		respPacket.setType(Type.GROUP_MSG_RESP);
		respPacket.setBody(Json.toJson(groupMsgRespBody).getBytes(ShowcasePacket.CHARSET));
		Aio.sendToGroup(channelContext.getGroupContext(), bsBody.getToGroup(), respPacket);

		return null;
	}
}
