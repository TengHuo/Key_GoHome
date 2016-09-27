package FLOYD;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;



public class Station {
	private int[][] data = new int[15][15];        //用于存储两站之间通行的最短时长
	//private String[][] number = new String[15][15]; //用于存储与data对应的车次
	private final int MAX = 999999;                //设置一个不可能到达的最大权值
    
    
    private Set<SingleStation> stations;        //建立数组存放所有车票信息
    
    private Set<SingleStation> heb_sy = new HashSet<>();
    private Set<SingleStation> heb_bj = new HashSet<>();
    private Set<SingleStation> heb_jn = new HashSet<>();
    private Set<SingleStation> heb_sh = new HashSet<>();
    private Set<SingleStation> heb_gz = new HashSet<>();
    private Set<SingleStation> heb_ty = new HashSet<>();
    private Set<SingleStation> heb_zz = new HashSet<>();
    private Set<SingleStation> heb_wh = new HashSet<>();
    private Set<SingleStation> heb_nc = new HashSet<>();
    private Set<SingleStation> heb_xa = new HashSet<>();
    private Set<SingleStation> heb_lz = new HashSet<>();
    private Set<SingleStation> heb_wlmq = new HashSet<>();
    private Set<SingleStation> heb_cd = new HashSet<>();
    private Set<SingleStation> heb_nn = new HashSet<>();
    
    private Set<SingleStation> sy_bj = new HashSet<>();
    private Set<SingleStation> sy_jn = new HashSet<>();
    private Set<SingleStation> sy_sh = new HashSet<>();
    private Set<SingleStation> sy_gz = new HashSet<>();
    private Set<SingleStation> sy_ty = new HashSet<>();
    private Set<SingleStation> sy_zz = new HashSet<>();
    private Set<SingleStation> sy_wh = new HashSet<>();
    private Set<SingleStation> sy_nc = new HashSet<>();
    private Set<SingleStation> sy_xa = new HashSet<>();
    private Set<SingleStation> sy_lz = new HashSet<>();
    private Set<SingleStation> sy_wlmq = new HashSet<>();
    private Set<SingleStation> sy_cd = new HashSet<>();
    private Set<SingleStation> sy_nn = new HashSet<>();
    
    private Set<SingleStation> bj_jn = new HashSet<>();
    private Set<SingleStation> bj_sh = new HashSet<>();
    private Set<SingleStation> bj_gz = new HashSet<>();
    private Set<SingleStation> bj_ty = new HashSet<>();
    private Set<SingleStation> bj_zz = new HashSet<>();
    private Set<SingleStation> bj_wh = new HashSet<>();
    private Set<SingleStation> bj_nc = new HashSet<>();
    private Set<SingleStation> bj_xa = new HashSet<>();
    private Set<SingleStation> bj_lz = new HashSet<>();
    private Set<SingleStation> bj_wlmq = new HashSet<>();
    private Set<SingleStation> bj_cd = new HashSet<>();
    private Set<SingleStation> bj_nn = new HashSet<>();

    private Set<SingleStation> jn_sh = new HashSet<>();
    private Set<SingleStation> jn_gz = new HashSet<>();
    private Set<SingleStation> jn_ty = new HashSet<>();
    private Set<SingleStation> jn_zz = new HashSet<>();
    private Set<SingleStation> jn_wh = new HashSet<>();
    private Set<SingleStation> jn_nc = new HashSet<>();
    private Set<SingleStation> jn_xa = new HashSet<>();
    private Set<SingleStation> jn_lz = new HashSet<>();
    private Set<SingleStation> jn_wlmq = new HashSet<>();
    private Set<SingleStation> jn_cd = new HashSet<>();
    private Set<SingleStation> jn_nn = new HashSet<>();
    
    private Set<SingleStation> sh_gz = new HashSet<>();
    private Set<SingleStation> sh_ty = new HashSet<>();
    private Set<SingleStation> sh_zz = new HashSet<>();
    private Set<SingleStation> sh_wh = new HashSet<>();
    private Set<SingleStation> sh_nc = new HashSet<>();
    private Set<SingleStation> sh_xa = new HashSet<>();
    private Set<SingleStation> sh_lz = new HashSet<>();
    private Set<SingleStation> sh_wlmq = new HashSet<>();
    private Set<SingleStation> sh_cd = new HashSet<>();
    private Set<SingleStation> sh_nn = new HashSet<>();
    
    private Set<SingleStation> gz_ty = new HashSet<>();
    private Set<SingleStation> gz_zz = new HashSet<>();
    private Set<SingleStation> gz_wh = new HashSet<>();
    private Set<SingleStation> gz_nc = new HashSet<>();
    private Set<SingleStation> gz_xa = new HashSet<>();
    private Set<SingleStation> gz_lz = new HashSet<>();
    private Set<SingleStation> gz_wlmq = new HashSet<>();
    private Set<SingleStation> gz_cd = new HashSet<>();
    private Set<SingleStation> gz_nn = new HashSet<>();

    private Set<SingleStation> ty_zz = new HashSet<>();
    private Set<SingleStation> ty_wh = new HashSet<>();
    private Set<SingleStation> ty_nc = new HashSet<>();
    private Set<SingleStation> ty_xa = new HashSet<>();
    private Set<SingleStation> ty_lz = new HashSet<>();
    private Set<SingleStation> ty_wlmq = new HashSet<>();
    private Set<SingleStation> ty_cd = new HashSet<>();
    private Set<SingleStation> ty_nn = new HashSet<>();
    
    private Set<SingleStation> zz_wh = new HashSet<>();
    private Set<SingleStation> zz_nc = new HashSet<>();
    private Set<SingleStation> zz_xa = new HashSet<>();
    private Set<SingleStation> zz_lz = new HashSet<>();
    private Set<SingleStation> zz_wlmq = new HashSet<>();
    private Set<SingleStation> zz_cd = new HashSet<>();
    private Set<SingleStation> zz_nn = new HashSet<>();
    
    private Set<SingleStation> wh_nc = new HashSet<>();
    private Set<SingleStation> wh_xa = new HashSet<>();
    private Set<SingleStation> wh_lz = new HashSet<>();
    private Set<SingleStation> wh_wlmq = new HashSet<>();
    private Set<SingleStation> wh_cd = new HashSet<>();
    private Set<SingleStation> wh_nn = new HashSet<>();
    
    private Set<SingleStation> nc_xa = new HashSet<>();
    private Set<SingleStation> nc_lz = new HashSet<>();
    private Set<SingleStation> nc_wlmq = new HashSet<>();
    private Set<SingleStation> nc_cd = new HashSet<>();
    private Set<SingleStation> nc_nn = new HashSet<>();
    
    private Set<SingleStation> xa_lz = new HashSet<>();
    private Set<SingleStation> xa_wlmq = new HashSet<>();
    private Set<SingleStation> xa_cd = new HashSet<>();
    private Set<SingleStation> xa_nn = new HashSet<>();
    
    private Set<SingleStation> lz_wlmq = new HashSet<>();
    private Set<SingleStation> lz_cd = new HashSet<>();
    private Set<SingleStation> lz_nn = new HashSet<>();
    
    private Set<SingleStation> wlmq_cd = new HashSet<>();
    private Set<SingleStation> wlmq_nn = new HashSet<>();
    
    private Set<SingleStation> cd_nn = new HashSet<>();
    
    public Station() {   
    	for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) { //初始化路径矩阵到达自身的长度为0，其余均为无穷大
				if (i == j) {
					data[i][j] = 0;
				}else {
					data[i][j] = MAX;
				}
			}
		}
    	group_station(parseJson());
	};
	
	public int[][] getData() {
		return data;
	}
	
	private Set<SingleStation> parseJson() 
	{		
		stations = new HashSet<>();
		String s,num,depart_name,arrive_name,depart_time,arrive_time;
        int ticket_amt;
        double ticket_price; 
		
		//JSONTokener jsonTokener = new JSONTokener(json); 
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader("src/FLOYD/results.json"));
        
        try {  
        	while ((s = br.readLine()) != null) {    		
        		//JSONObject dataJson = new JSONObject(s);// 创建一个包含原始json串的json对象  
        		JSONArray jarray = new JSONArray(s);// 找到features的json
        		for (int i = 0; i < jarray.length(); i++) 
        		{
        			JSONObject stationObject = jarray.getJSONObject(i);
        			num = stationObject.getString("num");  
        			ticket_amt = stationObject.getInt("ticket_amt");  
        			depart_name = stationObject.getString("depart_name");
        			arrive_name = stationObject.getString("arrive_name");
        			depart_time = stationObject.getString("depart_time");
        			arrive_time = stationObject.getString("arrive_time");
        			ticket_price = stationObject.getDouble("ticket_price");
        			SingleStation station = new SingleStation(num, depart_name, arrive_name, depart_time, arrive_time, ticket_amt, ticket_price);
        			stations.add(station);
				} 
        	}
        } catch (JSONException | IOException e) {  
        	e.printStackTrace();  
        }  
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return stations;
	}
	
	private void setData(int i, int j, int value)
	{
		if (data[i][j] > value) { //用较小的时长覆盖原本较长的时长
			data[i][j] = value;
			data[j][i] = data[i][j]; //往返长度相同
		}
	}
	
	private void group_station(Set<SingleStation> g_stations){  //根据类别将车站放入指定set中
		for (SingleStation m_station : g_stations) 
		{
			switch (m_station.getKey()) 
			{
				//from heb
				case "heb_sy": case "sy_heb": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					heb_sy.add(m_station);
					setData(0, 1, m_station.getMTime()); //维护heb_sy的最短时间数值
					break;
				}
				case "heb_bj": case "bj_heb": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					heb_bj.add(m_station);
					setData(0, 2, m_station.getMTime());     //维护heb_sy的最短时间数值
					break;
				}
				case "heb_jn": case "jn_heb": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					heb_jn.add(m_station);
					setData(0, 3, m_station.getMTime());
					break;
				}
				case "heb_sh": case "sh_heb": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					heb_sh.add(m_station);
					setData(0, 4, m_station.getMTime());
					break;
				}
				case "heb_gz": case "gz_heb": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					heb_gz.add(m_station);
					setData(0, 5, m_station.getMTime());
					break;
				}
				case "heb_ty": case "ty_heb": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					heb_ty.add(m_station);
					setData(0, 6, m_station.getMTime());
					break;
				}
				case "heb_zz": case "zz_heb": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					heb_zz.add(m_station);
					setData(0, 7, m_station.getMTime());
					break;
				}
				case "heb_wh": case "wh_heb": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					heb_wh.add(m_station);
					setData(0, 8, m_station.getMTime());
					break;
				}
				case "heb_nc": case "nc_heb": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					heb_nc.add(m_station);
					setData(0, 9, m_station.getMTime());
					break;
				}
				case "heb_xa": case "xa_heb": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					heb_xa.add(m_station);
					setData(0, 10, m_station.getMTime());
					break;
				}
				case "heb_lz": case "lz_heb": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					heb_lz.add(m_station);
					setData(0, 11, m_station.getMTime());
					break;
				}
				case "heb_wlmq": case "wlmq_heb": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					heb_wlmq.add(m_station);
					setData(0, 12, m_station.getMTime());
					break;
				}
				case "heb_cd": case "cd_heb": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					heb_cd.add(m_station);
					setData(0, 13, m_station.getMTime());
					break;
				}
				case "heb_nn": case "nn_heb": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					heb_nn.add(m_station);
					setData(0, 14, m_station.getMTime());
					break;
				}
				
				//from sy
				case "sy_bj": case "bj_sy": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sy_bj.add(m_station);
					setData(1, 2, m_station.getMTime());
					break;
				}
				case "sy_jn": case "jn_sy": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sy_jn.add(m_station);
					setData(1, 3, m_station.getMTime());

					break;
				}
				case "sy_sh": case "sh_sy": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sy_sh.add(m_station);
					setData(1, 4, m_station.getMTime());
					break;
				}
				case "sy_gz": case "gz_sy": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sy_gz.add(m_station);
					setData(1, 5, m_station.getMTime());
					break;
				}
				case "sy_ty": case "ty_sy": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sy_ty.add(m_station);
					setData(1, 6, m_station.getMTime());
					break;
				}
				case "sy_zz": case "zz_sy": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sy_zz.add(m_station);
					setData(1, 7, m_station.getMTime());
					break;
				}
				case "sy_wh": case "wh_sy": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sy_wh.add(m_station);
					setData(1, 8, m_station.getMTime());
					break;
				}
				case "sy_nc": case "nc_sy": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sy_nc.add(m_station);
					setData(1, 9, m_station.getMTime());
					break;
				}
				case "sy_xa": case "xa_sy": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sy_xa.add(m_station);
					setData(1, 10, m_station.getMTime());
					break;
				}
				case "sy_lz": case "lz_sy": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sy_lz.add(m_station);
					setData(1, 11, m_station.getMTime());
					break;
				}
				case "sy_wlmq": case "wlmq_sy": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sy_wlmq.add(m_station);
					setData(1, 12, m_station.getMTime());
					break;
				}
				case "sy_cd": case "cd_sy": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sy_cd.add(m_station);
					setData(1, 13, m_station.getMTime());
					break;
				}
				case "sy_nn": case "nn_sy": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sy_nn.add(m_station);
					setData(1, 14, m_station.getMTime());
					break;
				}
				
				//from bj
				case "bj_jn": case "jn_bj": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					bj_jn.add(m_station);
					setData(2, 3, m_station.getMTime());
					break;
				}
				case "bj_sh": case "sh_bj": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					bj_sh.add(m_station);
					setData(2, 4, m_station.getMTime());
					break;
				}
				case "bj_gz": case "gz_bj": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					bj_gz.add(m_station);
					setData(2, 5, m_station.getMTime());
					break;
				}
				case "bj_ty": case "ty_bj": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					bj_ty.add(m_station);
					setData(2, 6, m_station.getMTime());
					break;
				}
				case "bj_zz": case "zz_bj": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					bj_zz.add(m_station);
					setData(2, 7, m_station.getMTime());
					break;
				}
				case "bj_wh": case "wh_bj": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					bj_wh.add(m_station);
					setData(2, 8, m_station.getMTime());
					break;
				}
				case "bj_nc": case "nc_bj": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					bj_nc.add(m_station);
					setData(2, 9, m_station.getMTime());
					break;
				}
				case "bj_xa": case "xa_bj": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					bj_xa.add(m_station);
					setData(2, 10, m_station.getMTime());
					break;
				}
				case "bj_lz": case "lz_bj": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					bj_lz.add(m_station);
					setData(2, 11, m_station.getMTime());
					break;
				}
				case "bj_wlmq": case "wlmq_bj": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					bj_wlmq.add(m_station);
					setData(2, 12, m_station.getMTime());
					break;
				}
				case "bj_cd": case "cd_bj": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					bj_cd.add(m_station);
					setData(2, 13, m_station.getMTime());
					break;
				}
				case "bj_nn": case "nn_bj": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					bj_nn.add(m_station);
					setData(2, 14, m_station.getMTime());
					break;
				}
				
				//from jn
				case "jn_sh": case "sh_jn": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					jn_sh.add(m_station);
					setData(3, 4, m_station.getMTime());
					break;
				}
				case "jn_gz": case "gz_jn": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					jn_gz.add(m_station);
					setData(3, 5, m_station.getMTime());
					break;
				}
				case "jn_ty": case "ty_jn": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					jn_ty.add(m_station);
					setData(3, 6, m_station.getMTime());
					break;
				}
				case "jn_zz": case "zz_jn": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					jn_zz.add(m_station);
					setData(3, 7, m_station.getMTime());
					break;
				}
				case "jn_wh": case "wh_jn": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					jn_wh.add(m_station);
					setData(3, 8, m_station.getMTime());
					break;
				}
				case "jn_nc": case "nc_jn": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					jn_nc.add(m_station);
					setData(3, 9, m_station.getMTime());
					break;
				}
				case "jn_xa": case "xa_jn": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					jn_xa.add(m_station);
					setData(3, 10, m_station.getMTime());
					break;
				}
				case "jn_lz": case "lz_jn": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					jn_lz.add(m_station);
					setData(3, 11, m_station.getMTime());
					break;
				}
				case "jn_wlmq": case "wlmq_jn": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					jn_wlmq.add(m_station);
					setData(3, 12, m_station.getMTime());
					break;
				}
				case "jn_cd": case "cd_jn": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					jn_cd.add(m_station);
					setData(3, 13, m_station.getMTime());
					break;
				}
				case "jn_nn": case "nn_jn": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					jn_nn.add(m_station);
					setData(3, 14, m_station.getMTime());
					break;
				}
				
				//from sh
				case "sh_gz": case "gz_sh": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sh_gz.add(m_station);
					setData(4, 5, m_station.getMTime());
					break;
				}
				case "sh_ty": case "ty_sh": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sh_ty.add(m_station);
					setData(4, 6, m_station.getMTime());
					break;
				}
				case "sh_zz": case "zz_sh": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sh_zz.add(m_station);
					setData(4, 7, m_station.getMTime());
					break;
				}
				case "sh_wh": case "wh_sh": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sh_wh.add(m_station);
					setData(4, 8, m_station.getMTime());
					break;
				}
				case "sh_nc": case "nc_sh": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sh_nc.add(m_station);
					setData(4, 9, m_station.getMTime());
					break;
				}
				case "sh_xa": case "xa_sh": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sh_xa.add(m_station);
					setData(4, 10, m_station.getMTime());
					break;
				}
				case "sh_lz": case "lz_sh": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sh_lz.add(m_station);
					setData(4, 11, m_station.getMTime());
					break;
				}
				case "sh_wlmq": case "wlmq_sh": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sh_wlmq.add(m_station);
					setData(4, 12, m_station.getMTime());
					break;
				}
				case "sh_cd": case "cd_sh": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sh_cd.add(m_station);
					setData(4, 13, m_station.getMTime());
					break;
				}
				case "sh_nn": case "nn_sh": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					sh_nn.add(m_station);
					setData(4, 14, m_station.getMTime());
					break;
				}
				
				//from gz
				case "gz_ty": case "ty_gz": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					gz_ty.add(m_station);
					setData(5, 6, m_station.getMTime());
					break;
				}
				case "gz_zz": case "zz_gz": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					gz_zz.add(m_station);
					setData(5, 7, m_station.getMTime());
					break;
				}
				case "gz_wh": case "wh_gz": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					gz_wh.add(m_station);
					setData(5, 8, m_station.getMTime());
					break;
				}
				case "gz_nc": case "nc_gz": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					gz_nc.add(m_station);
					setData(5, 9, m_station.getMTime());
					break;
				}
				case "gz_xa": case "xa_gz": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					gz_xa.add(m_station);
					setData(5, 10, m_station.getMTime());
					break;
				}
				case "gz_lz": case "lz_gz": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					gz_lz.add(m_station);
					setData(5, 11, m_station.getMTime());
					break;
				}
				case "gz_wlmq": case "wlmq_gz": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					gz_wlmq.add(m_station);
					setData(5, 12, m_station.getMTime());
					break;
				}
				case "gz_cd": case "cd_gz": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					gz_cd.add(m_station);
					setData(5, 13, m_station.getMTime());
					break;
				}
				case "gz_nn": case "nn_gz": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					gz_nn.add(m_station);
					setData(5, 14, m_station.getMTime());
					break;
				}
				
				//from ty
				case "ty_zz": case "zz_ty": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					ty_zz.add(m_station);
					setData(6, 7, m_station.getMTime());
					break;
				}
				case "ty_wh": case "wh_ty": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					ty_wh.add(m_station);
					setData(6, 8, m_station.getMTime());
					break;
				}
				case "ty_nc": case "nc_ty": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					ty_nc.add(m_station);
					setData(6, 9, m_station.getMTime());
					break;
				}
				case "ty_xa": case "xa_ty": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					ty_xa.add(m_station);
					setData(6, 10, m_station.getMTime());
					break;
				}
				case "ty_lz": case "lz_ty": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					ty_lz.add(m_station);
					setData(6, 11, m_station.getMTime());
					break;
				}
				case "ty_wlmq": case "wlmq_ty": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					ty_wlmq.add(m_station);
					setData(6, 12, m_station.getMTime());
					break;
				}
				case "ty_cd": case "cd_ty": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					ty_cd.add(m_station);
					setData(6, 13, m_station.getMTime());
					break;
				}
				case "ty_nn": case "nn_ty": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					ty_nn.add(m_station);
					setData(6, 14, m_station.getMTime());
					break;
				}
				
				// from zz
				case "zz_wh": case "wh_zz": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					zz_wh.add(m_station);
					setData(7, 8, m_station.getMTime());
					break;
				}
				case "zz_nc": case "nc_zz": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					zz_nc.add(m_station);
					setData(7, 9, m_station.getMTime());
					break;
				}
				case "zz_xa": case "xa_zz": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					zz_xa.add(m_station);
					setData(7, 10, m_station.getMTime());
					break;
				}
				case "zz_lz": case "lz_zz": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					zz_lz.add(m_station);
					setData(7, 11, m_station.getMTime());
					break;
				}
				case "zz_wlmq": case "wlmq_zz": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					zz_wlmq.add(m_station);
					setData(7, 12, m_station.getMTime());
					break;
				}
				case "zz_cd": case "cd_zz": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					zz_cd.add(m_station);
					setData(7, 13, m_station.getMTime());
					break;
				}
				case "zz_nn": case "nn_zz": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					zz_nn.add(m_station);
					setData(7, 14, m_station.getMTime());
					break;
				}
				
				// from wh
				case "wh_nc": case "nc_wh": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					wh_nc.add(m_station);
					setData(8, 9, m_station.getMTime());
					break;
				}
				case "wh_xa": case "xa_wh": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					wh_xa.add(m_station);
					setData(8, 10, m_station.getMTime());
					break;
				}
				case "wh_lz": case "lz_wh": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					wh_lz.add(m_station);
					setData(8, 11, m_station.getMTime());
					break;
				}
				case "wh_wlmq": case "wlmq_wh": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					wh_wlmq.add(m_station);
					setData(8, 12, m_station.getMTime());
					break;
				}
				case "wh_cd": case "cd_wh": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					wh_cd.add(m_station);
					setData(8, 13, m_station.getMTime());
					break;
				}
				case "wh_nn": case "nn_wh": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					wh_nn.add(m_station);
					setData(8, 14, m_station.getMTime());
					break;
				}
				
				//from nc
				case "nc_xa": case "xa_nc": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					nc_xa.add(m_station);
					setData(9, 10, m_station.getMTime());
					break;
				}
				case "nc_lz": case "lz_nc": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					nc_lz.add(m_station);
					setData(9, 11, m_station.getMTime());
					break;
				}
				case "nc_wlmq": case "wlmq_nc": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					nc_wlmq.add(m_station);
					setData(9, 12, m_station.getMTime());
					break;
				}
				case "nc_cd": case "cd_nc": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					nc_cd.add(m_station);
					setData(9, 13, m_station.getMTime());
					break;
				}
				case "nc_nn": case "nn_nc": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					nc_nn.add(m_station);
					setData(9, 14, m_station.getMTime());
					break;
				}
				
				//from xa
				case "xa_lz": case "lz_xa": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					xa_lz.add(m_station);
					setData(10, 11, m_station.getMTime());
					break;
				}
				case "xa_wlmq": case "wlmq_xa": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					xa_wlmq.add(m_station);
					setData(10, 12, m_station.getMTime());
					break;
				}
				case "xa_cd": case "cd_xa": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					xa_cd.add(m_station);
					setData(10, 13, m_station.getMTime());
					break;
				}
				case "xa_nn": case "nn_xa": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					xa_nn.add(m_station);
					setData(10, 14, m_station.getMTime());
					break;
				}
				
				//from lz
				case "lz_wlmq": case "wlmq_lz": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					lz_wlmq.add(m_station);
					setData(11, 12, m_station.getMTime());
					break;
				}
				case "lz_cd": case "cd_lz": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					lz_cd.add(m_station);
					setData(11, 13, m_station.getMTime());
					break;
				}
				case "lz_nn": case "nn_lz": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					lz_nn.add(m_station);
					setData(11, 14, m_station.getMTime());
					break;
				}
				
				//from wlmq
				case "wlmq_cd": case "cd_wlmq": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					wlmq_cd.add(m_station);
					setData(12, 13, m_station.getMTime());
					break;
				}
				case "wlmq_nn": case "nn_wlmq": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					wlmq_nn.add(m_station);
					setData(12, 14, m_station.getMTime());
					break;
				}
				
				//from cd
				case "cd_nn": case "nn_cd": //矩阵中需要来回长度相同，这里将往返划为一类
				{
					cd_nn.add(m_station);
					setData(13, 14, m_station.getMTime());
					break;
				}
				
				case "NA":
				{
					//System.out.println("Nothing...");
					break;
				}
			}
		}
	}
	
	class SingleStation                       //内部类用于暂时存放从json解析出来的票据信息
	{
		private String num;
		private String depart_name;
		private String arrive_name;
		private String depart_time;
		private String arrive_time;
	    private int ticket_amt;
	    private double ticket_price;
	    private int time = 0;
	    private String key = "NA";
	    
	    public SingleStation(String num,String depart_name,String arrive_name,String depart_time,
				String arrive_time,int ticket_amt,double ticket_price)
		{
	        this.num = num;
	        this.depart_name = depart_name;
	        this.arrive_name = arrive_name;
	        this.depart_time = depart_time;
	        this.arrive_time = arrive_time;
	        this.ticket_amt = ticket_amt;
	        this.ticket_price = ticket_price;
	        cal_time(depart_time,arrive_time);
	        grouping();
		}
	    
	    public void setNum(String num) {
			this.num = num;
		}
	    
	    public void setDepart_name(String depart_name) {
			this.depart_name = depart_name;
		}
	    
	    public void setArrive_name(String arrive_name) {
			this.arrive_name = arrive_name;
		}
	    
	    public void setDepart_time(String depart_time) {
			this.depart_time = depart_time;
		}
	    
	    public void setArrive_time(String arrive_time) {
			this.arrive_time = arrive_time;
		}
	    
	    public void setTicket_amt(int ticket_amt) {
			this.ticket_amt = ticket_amt;
		}
	    
	    public void setTicket_price(double ticket_price) {
			this.ticket_price = ticket_price;
		}
	    
		public String getNum() {
			return num;
		}

		public String getDepart_name() {
			return depart_name;
		}

		public String getArrive_name() {
			return arrive_name;
		}

		public String getDepart_time() {
			return depart_time;
		}

		public String getArrive_time() {
			return arrive_time;
		}

		public int getTicket_amt() {
			return ticket_amt;
		}

		public double getTicket_price() {
			return ticket_price;
		};
		
		public int getMTime() {
			if (time < 0) {
				time = -time;
			}
			return time;
		}
		
		public String getKey() {
			return key;
		}
		
		private void cal_time(String depart_time, String arrive_time) {
			DateFormat df = new SimpleDateFormat("HH:mm");   
			try  
			{   
			    Date d1 = df.parse(arrive_time);   
			    Date d2 = df.parse(depart_time);   
			    long diff = d1.getTime() - d2.getTime();   
			    //int hours = (int) diff / (60 * 60);
			    if (diff < 0)
			    {
			    	diff = -diff;
			    }
			    int minutes = (int) diff / (60 * 100 * 10);
			    time = minutes;
			}   
			catch (Exception e)   
			{   
				return;
			}  
		}
		
		private void grouping() {             //根据车票的始发－到达站进行key值分配
			boolean beg_flag = true,end_flag = true;
			int i = 0;
			
			String[] heb = {"哈尔滨","哈尔滨西","齐齐哈尔","牡丹江","佳木斯"}; // 0
		    String[] sy = {"山海关","沈阳北","沈阳","沈阳西","长春","苏家屯"}; // 1
		    String[] bj = {"丰台西","北京","丰台","北京西","北京南","石家庄","天津"};//2
		    String[] jn = {"济南","济南西","济西","青岛","青岛北"};//3
		    String[] sh = {"徐州","徐州北","徐州东","南京","南京东","南京南","无锡","常州","南翔","上海南","上海","杭州东","杭州南","上海虹桥","合肥南"};//4
		    String[] gz = {"株洲北","株洲","衡阳北","怀化","衡阳","长沙南","江村","广州南","广州","广州东","深圳北"};//5
		    String[] ty = {"太原","大同"};//6
		    String[] zz = {"郑州","郑州东","郑州南","郑州北","莆田西","商丘","洛阳龙门"};//7
		    String[] wh = {"武汉北","武昌","汉口","武汉","襄阳北"};//8
		    String[] nc = {"鹰潭","福州南","福州","上饶","南昌","南昌西","向塘西"};//9
		    String[] xa = {"西安","西安西","新丰镇","西安北"};//10
		    String[] lz = {"兰州西"};//11
		    String[] wlmq = {"乌鲁木齐"};//12
		    String[] cd = {"成都","成都东","成都北","达州","贵阳","贵阳北","贵阳东","贵阳南","重庆北"};//13
		    String[] nn = {"南宁东","南宁","柳州","柳州南","防城港"};//14
			
				if (beg_flag) {
					for (i = 0; i < heb.length; i++) {
						if (heb[i].equals(depart_name)) {
							key = "heb_";
							beg_flag = false;
							break;
						}
					}
				}
				if (beg_flag) {
					for (i = 0; i < sy.length; i++) {
						if (sy[i].equals(depart_name)) {
							key = "sy_";
							beg_flag = false;
							break;
						}
					}
				}
				if (beg_flag) {
					for (i = 0; i < bj.length; i++) {
						if (bj[i].equals(depart_name)) {
							key = "bj_";
							beg_flag = false;
							break;
						}
					}
				}
				if (beg_flag) {
					for (i = 0; i < jn.length; i++) {
						if (jn[i].equals(depart_name)) {
							key = "jn_";
							beg_flag = false;
							break;
						}
					}
				}
				if (beg_flag) {
					for (i = 0; i < sh.length; i++) {
						if (sh[i].equals(depart_name)) {
							key = "sh_";
							beg_flag = false;
							break;
						}
					}
				}
				if (beg_flag) {
					for (i = 0; i < gz.length; i++) {
						if (gz[i].equals(depart_name)) {
							key = "gz_";
							beg_flag = false;
							break;
						}
					}
				}
				if (beg_flag) {
					for (i = 0; i < ty.length; i++) {
						if (ty[i].equals(depart_name)) {
							key = "ty_";
							beg_flag = false;
							break;
						}
					}
				}
				if (beg_flag) {
					for (i = 0; i < zz.length; i++) {
						if (zz[i].equals(depart_name)) {
							key = "zz_";
							beg_flag = false;
							break;
						}
					}
				}
				if (beg_flag) {
					for (i = 0; i < wh.length; i++) {
						if (wh[i].equals(depart_name)) {
							key = "wh_";
							beg_flag = false;
							break;
						}
					}
				}
				if (beg_flag) {
					for (i = 0; i < nc.length; i++) {
						if (nc[i].equals(depart_name)) {
							key = "nc_";
							beg_flag = false;
							break;
						}
					}
				}
				if (beg_flag) {
					for (i = 0; i < xa.length; i++) {
						if (xa[i].equals(depart_name)) {
							key = "xa_";
							beg_flag = false;
							break;
						}
					}
				}
				if (beg_flag) {
					for (i = 0; i < lz.length; i++) {
						if (lz[i].equals(depart_name)) {
							key = "lz_";
							beg_flag = false;
							break;
						}
					}
				}
				if (beg_flag) {
					for (i = 0; i < wlmq.length; i++) {
						if (wlmq[i].equals(depart_name)) {
							key = "wlmq_";
							beg_flag = false;
							break;
						}
					}
				}
				if (beg_flag) {
					for (i = 0; i < cd.length; i++) {
						if (cd[i].equals(depart_name)) {
							key = "cd_";
							beg_flag = false;
							break;
						}
					}
				}
				if (beg_flag) {
					for (i = 0; i < nn.length; i++) {
						if (nn[i].equals(depart_name)) {
							key = "nn_";
							beg_flag = false;
							break;
						}
					}
				}
				
				if (end_flag) {
					for (i = 0; i < heb.length; i++) {
						if (heb[i].equals(arrive_name)) {
							key = key + "heb";
							end_flag = false;
							break;
						}
					}
				}
				if (end_flag) {
					for (i = 0; i < sy.length; i++) {
						if (sy[i].equals(arrive_name)) {
							key = key + "sy";
							end_flag = false;
							break;
						}
					}
				}
				if (end_flag) {
					for (i = 0; i < bj.length; i++) {
						if (bj[i].equals(arrive_name)) {
							key = key + "bj";
							end_flag = false;
							break;
						}
					}
				}
				if (end_flag) {
					for (i = 0; i < jn.length; i++) {
						if (jn[i].equals(arrive_name)) {
							key = key + "jn";
							end_flag = false;
							break;
						}
					}
				}
				if (end_flag) {
					for (i = 0; i < sh.length; i++) {
						if (sh[i].equals(arrive_name)) {
							key = key + "sh";
							end_flag = false;
							break;
						}
					}
				}
				if (end_flag) {
					for (i = 0; i < gz.length; i++) {
						if (gz[i].equals(arrive_name)) {
							key = key + "gz";
							end_flag = false;
							break;
						}
					}
				}
				if (end_flag) {
					for (i = 0; i < ty.length; i++) {
						if (ty[i].equals(arrive_name)) {
							key = key + "ty";
							end_flag = false;
							break;
						}
					}
				}
				if (end_flag) {
					for (i = 0; i < zz.length; i++) {
						if (zz[i].equals(arrive_name)) {
							key = key + "zz";
							end_flag = false;
							break;
						}
					}
				}
				if (end_flag) {
					for (i = 0; i < wh.length; i++) {
						if (wh[i].equals(arrive_name)) {
							key = key + "wh";
							end_flag = false;
							break;
						}
					}
				}
				if (end_flag) {
					for (i = 0; i < nc.length; i++) {
						if (nc[i].equals(arrive_name)) {
							key = key + "nc";
							end_flag = false;
							break;
						}
					}
				}
				if (end_flag) {
					for (i = 0; i < xa.length; i++) {
						if (xa[i].equals(arrive_name)) {
							key = key + "xa";
							end_flag = false;
							break;
						}
					}
				}
				if (end_flag) {
					for (i = 0; i < lz.length; i++) {
						if (lz[i].equals(arrive_name)) {
							key = key + "lz";
							end_flag = false;
							break;
						}
					}
				}
				if (end_flag) {
					for (i = 0; i < wlmq.length; i++) {
						if (wlmq[i].equals(arrive_name)) {
							key = key + "wlmq";
							end_flag = false;
							break;
						}
					}
				}
				if (end_flag) {
					for (i = 0; i < cd.length; i++) {
						if (cd[i].equals(arrive_name)) {
							key = key + "cd";
							end_flag = false;
							break;
						}
					}
				}
				if (end_flag) {
					for (i = 0; i < nn.length; i++) {
						if (nn[i].equals(arrive_name)) {
							key = key + "nn";
							end_flag = false;
							break;
						}
					}
				}
		}
	}
}
