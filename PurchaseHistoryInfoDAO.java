package com.internousdev.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.spring.dto.PurchaseHistoryInfoDTO;
import com.internousdev.spring.util.DBConnector;

public class PurchaseHistoryInfoDAO {

	public List<PurchaseHistoryInfoDTO> getPurchaseHistoryInfo(String userId) throws SQLException {
		List<PurchaseHistoryInfoDTO> dtoList = new ArrayList<PurchaseHistoryInfoDTO>();
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		// DBから購入履歴情報を取得
		String sql = "SELECT " + " hist.id, " + " hist.user_id, " + " hist.product_count, " + " hist.price,"
				+ " hist.product_count * hist.price AS sub_total, " + " dest.family_name, " + " dest.first_name, "
				+ " dest.user_address, "
				+ " prod.product_name, " + " prod.product_name_kana, " + " prod.image_file_path, "
				+ " prod.image_file_name, " + " prod.release_date, " + " prod.release_company "
				+ " FROM purchase_history_info hist " + " left join product_info prod "
				+ " on hist.product_id = prod.id " + " left join destination_info dest "
				+ " on hist.destination_id = dest.id " + " where hist.user_id =? " + " order by hist.regist_date desc ";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PurchaseHistoryInfoDTO dto = new PurchaseHistoryInfoDTO();
				dto.setId(rs.getInt("id"));
				dto.setUserId(rs.getString("user_id"));
				dto.setProductCount(rs.getInt("product_count"));
				dto.setPrice(rs.getInt("price"));
				dto.setSubTotal(rs.getInt("sub_total"));
				dto.setFamilyName(rs.getString("family_name"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setUserAddress(rs.getString("user_address"));
				dto.setProductName(rs.getString("product_name"));
				dto.setProductNameKana(rs.getString("product_name_kana"));
				dto.setImageFilePath(rs.getString("image_file_path"));
				dto.setImageFileName(rs.getString("image_file_name"));
				dto.setReleaseDate(rs.getDate("release_date"));
				dto.setReleaseCompany(rs.getString("release_company"));
				dtoList.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return dtoList;

	}

	public int deletePurchaseHistory(String user_id) throws SQLException {
		int result = 0;
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		String sql = "DELETE FROM " + "purchase_history_info " + "WHERE user_id =?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, user_id);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return result;
	}

	public int insertPurchaseHistoryInfo(String userId, int productId, int productCount, int price, int destinationId)
			throws SQLException {
		int result = 0;
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		String sql = "INSERT INTO purchase_history_info "
				+ "(user_id, product_id, product_Count, price, destination_id, regist_date, update_date)"
				+ "VALUES (?, ?, ?, ?, ?, now(), now())";
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, userId);
			preparedStatement.setInt(2, productId);
			preparedStatement.setInt(3, productCount);
			preparedStatement.setInt(4, price);
			preparedStatement.setInt(5, destinationId);

			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return result;
	}

}
