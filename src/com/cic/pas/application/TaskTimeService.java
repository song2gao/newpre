/**  
* @Project: PreAcquisitionSystem3.0

* @Title: TaskTimeService.java

* @Package com.cic.pas.common.service

* @Description: TODO

* @author liuya7711@126.com

* @date 2014-3-1 上午11:07:57

* @Copyright: 2014 www.cic.cn

* @version V3.0  
*/
package com.cic.pas.application;
/** 
 * 版权所有(C)2013-2014 CIC 
 * 公司名称：北京中诚盛源技术发展有限公司
 * 版本:V3.0
 * 文件名：com.cic.pas.common.service.TaskTimeService.java 
 * 作者: 郑瀚超 ,陈阿强
 * 创建时间: 2014-3-1上午11:07:57 
 * 修改时间：2014-3-1上午11:07:57 
 */
/** 
 * @ClassName: TaskTimeService 
 * @Description: 任务计时服务
 * @author lenovo 
 * @date 2014-3-1 上午11:07:57 
 *  
 */
public class TaskTimeService {
	/** 
	 * @Title: time 
	 * @Description: TODO(任务计时) 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws 
	 */
	private void timeTask() {
		// TODO Auto-generated method stub

	}
	
	/** 
	 * @Title: updateTaskTime 
	 * @Description: TODO 调用 数据库访问服务存储任务起止时间
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws 
	 */
	private void updateTaskTime() {
		// TODO Auto-generated method stub
		new DBVisitService().update();
	}
}
