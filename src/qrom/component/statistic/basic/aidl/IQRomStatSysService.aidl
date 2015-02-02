package qrom.component.statistic.basic.aidl;


interface IQRomStatSysService {

/**
* 退出统计
*@param Bundle reqData
*/
int destroyStat(in Bundle reqData);

/**
* 删除不合法数据
*@param Bundle reqData
*/
int deleteRomInvalidData(in Bundle reqData);

/**
* 获取版本信息
*/
String getQIMEI();

/**
* 获取tms版本信息
*/
String getTmsVersion();

/**
* 上报所有数据
*/
int reportAllStatDatas();

/**
* 初始化灯塔
*/
int initBeacon();

/**
* 统计用户数据
*@param Bundle reqData
*/
int triggerStatisticUserData(in Bundle reqData);

/**
* 同步数据到rom
*@param Bundle reqData
*/
int synStatisticData2Rom(int ver, in Bundle reqData);

/**
* 数据入库
*/
int saveStatCacheImmediatyly();

/**
* 获取指定类型数据
*@param int
*/
byte[] getDatas(int reqType);

/**
* 统计指定类型的数据
*@param  int reqType
*@param Bundle reqData
*/
int triggerDataByType(int reqType, in Bundle reqData);

}
