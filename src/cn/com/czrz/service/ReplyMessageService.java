
package cn.com.czrz.service;

import cn.com.czrz.entity.ReplyMessage;

public class ReplyMessageService extends BaseService
{

    private static final long serialVersionUID = -9039743485737980299L;

    public void saveReplyMessage(ReplyMessage rm)
    {
        jdbc.saveEntity(rm);
    }
}
