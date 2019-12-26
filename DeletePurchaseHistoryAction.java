package com.internousdev.spring.action;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.spring.dao.PurchaseHistoryInfoDAO;
import com.internousdev.spring.dto.PurchaseHistoryInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class DeletePurchaseHistoryAction extends ActionSupport implements SessionAware{
	private List<PurchaseHistoryInfoDTO> purchaseHistoryInfoDTOList;
	private Map<String,Object>session;
	public String execute(){
		String tempLogined = String.valueOf(session.get("loginFlg"));
		int logined = "null".equals(tempLogined)? 0: Integer.parseInt(tempLogined);
		if(logined != 1){
			return"loginError";
		}
		String result = ERROR;
		PurchaseHistoryInfoDAO purchaseHistoryInfoDAO = new PurchaseHistoryInfoDAO();
		try {
			int count = purchaseHistoryInfoDAO.deletePurchaseHistory(String.valueOf(session.get("userId")));
			if(count > 0){
				purchaseHistoryInfoDTOList = purchaseHistoryInfoDAO.getPurchaseHistoryInfo(String.valueOf(session.get("userId")));
				result = SUCCESS;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	public List<PurchaseHistoryInfoDTO> getPurchaseHistoryInfoDTOList(){
		return purchaseHistoryInfoDTOList;
	}
	public void setPurchaseHistoryInfoDTOList(List<PurchaseHistoryInfoDTO>purchaseHistoryInfoDTOList){
		this.purchaseHistoryInfoDTOList = purchaseHistoryInfoDTOList;
	}
	public Map<String,Object> getSession(){
		return session;
	}
	public void setSession(Map<String,Object>session){
		this.session = session;
	}

}
