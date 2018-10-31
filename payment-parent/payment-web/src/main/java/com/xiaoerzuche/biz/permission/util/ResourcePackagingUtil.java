package com.xiaoerzuche.biz.permission.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.xiaoerzuche.biz.permission.enu.ResourceType;
import com.xiaoerzuche.biz.permission.mode.Resource;
import com.xiaoerzuche.biz.permission.vo.MenuVo;

public class ResourcePackagingUtil {
	private static final Logger logger = Logger.getLogger(ResourcePackagingUtil.class);

	/**
	 * 将扁平的资源组装成父子层级的关系
	 * @param resources
	 * @return
	 */
	public static List<MenuVo> packingMenu(List<Resource> resources){
		List<MenuVo> menus = new ArrayList<MenuVo>();
		Map<Integer, MenuVo> MenuVoMap = new HashMap<Integer, MenuVo>(10);
		for(Resource resource : resources){
			if(resource.getType() == ResourceType.MENUE.getCode()){
				MenuVo vo = new MenuVo();
				vo = MenuVo.createBy(resource);
				MenuVoMap.put(resource.getId(), vo);
				menus.add(vo);
			}else if(resource.getType() == ResourceType.LINK.getCode() || resource.getType() == ResourceType.BUTTON.getCode()){
				//因为在登陆的时候获取权限时已经排序好了，所以这里直接按照顺序来处理就OK咧by Nick C
				MenuVo pvo = MenuVoMap.get(resource.getPid());
				if(pvo == null){
					logger.warn("找不到父亲资源，pid="+resource.getPid()+", resource="+resource);
					continue;
				}
				MenuVo vo = MenuVo.createBy(resource);
				MenuVoMap.put(resource.getId(), vo);
				pvo.getChilds().add(vo);
			}else{
				logger.warn("未知的资源类型：resource:"+resource);
			}
		}
		return menus;
	}
}
