
package cn.com.czrz.service;

import cn.com.czrz.entity.WchatMessage;

public class WchatMessageService extends BaseService
{

    private static final long serialVersionUID = -5696529219108310922L;

    public void saveWchatMessage(WchatMessage wm)
    {
        jdbc.saveEntity(wm);
    }

}
