/**  
* @Project: PreAcquisitionSystem3.0

* @Title: DataBaseManageService.java

* @Package com.cic.pas.base.serviceInterface

* @Description: TODO

* @author liuya7711@126.com

* @date 2014-2-20 下午04:35:19

* @Copyright: 2014 www.cic.cn

* @version V3.0  
*/
package com.cic.pas.application.manage;

import com.cic.pas.application.DBVisitService;
import com.cic.pas.common.base.impl.PBaseClass;

/** 
 * 版权所有(C)2013-2014 CIC 
 * 公司名称：北京中诚盛源技术发展有限公司
 * 版本:V3.0
 * 文件名：com.cic.pas.base.serviceInterface.DataBaseManageService.java 
 * 作者: 刘亚 
 * 创建时间: 2014-2-20下午04:35:19 
 * 修改时间：2014-2-20下午04:35:19 
 */
/** 
 * @ClassName: DataBaseManageService 
 * @Description: TODO
 * @author lenovo 
 * @date 2014-2-20 下午04:35:19 
 *  
 */
public class PDBManageService extends PBaseClass{

	static{
		new DBVisitService();
	}
}
