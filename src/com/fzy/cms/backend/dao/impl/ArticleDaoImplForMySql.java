package com.fzy.cms.backend.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fzy.cms.backend.VO.PagerVO;
import com.fzy.cms.backend.dao.ArticleDao;
import com.fzy.cms.backend.mode1.Article;
import com.fzy.cms.backend.mode1.Channel;
//import com.fzy.cms.backend.vo.PagerVO;
import com.fzy.cms.utils.DBUtils;

public class ArticleDaoImplForMySql {
	public void addArticle(Article a){
		String sql = "insert into t_article (" +
				"title,source,content,author,keyword,intro,type,recommend,headline,topicId,createTime,updateTime,adminId) " +
				"values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sqlForChannel = "insert into t_channel_article (channelId,articleId) values (?,?)";
		Connection conn = DBUtils.getConn();
		PreparedStatement pstmt = null;
		PreparedStatement pstmtForChannel = null;
		//ResultSet rs = null;
		try {
			//插入文章信息
			pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, a.getTitle());
			pstmt.setString(2, a.getSource());
			pstmt.setString(3, a.getContent());
			pstmt.setString(4, a.getAuthor());
			pstmt.setString(5, a.getKeyword());
			pstmt.setString(6, a.getIntro());
			pstmt.setString(7, a.getType());
			pstmt.setBoolean(8, a.isRecommend());
			pstmt.setBoolean(9, a.isHeadline());
			pstmt.setInt(10, a.getTopicId());
			pstmt.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
			pstmt.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
			pstmt.setInt(13, a.getAdminId());
			pstmt.executeUpdate();
			
			//获得刚刚插入的记录的ID
			ResultSet newId = pstmt.getGeneratedKeys();
			if(newId.next()){
				a.setId(newId.getInt(1));
			}
			
			//插入文章与频道之间的关联信息
			Set<Channel> channels = a.getChannels();
			if(channels != null){
				for(Channel c:channels){
					pstmtForChannel = conn.prepareStatement(sqlForChannel);
					pstmtForChannel.setInt(1, c.getId());
					pstmtForChannel.setInt(2, a.getId());
					pstmtForChannel.executeUpdate();
				}
			}
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			DBUtils.rollback(conn);
		} finally{
			DBUtils.close(pstmtForChannel);
			DBUtils.close(pstmt);
			DBUtils.close(conn);
		}	
	}
	
	public void delArticle(String[] ids) {
		String sql = "delete from t_article where id = ?";
		String sqlForChannel = "delete from t_channel_article where articleId = ?";
		Connection conn = DBUtils.getConn();
		PreparedStatement pstmt = null;
		PreparedStatement pstmtForChannel = null;
		try {
			for (int i = 0; i < ids.length; i++) {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(ids[i]));
				pstmt.executeUpdate();
				
				pstmtForChannel = conn.prepareStatement(sqlForChannel);
				pstmtForChannel.setInt(1, Integer.parseInt(ids[i]));
				pstmtForChannel.executeUpdate();
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			DBUtils.rollback(conn);
		} finally {
			DBUtils.close(pstmtForChannel);
			DBUtils.close(pstmt);
			DBUtils.close(conn);
		}
	}

	public Article findArticleById(int id) {

		String sql = "select * from t_article where id = ?";
		Connection conn = DBUtils.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Article a = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				a = new Article();
				a.setId(rs.getInt("id"));
				a.setTitle(rs.getString("title"));
				a.setContent(rs.getString("content"));
				a.setSource(rs.getString("source"));
			}

			// conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			//DBUtils.rollback(conn);
		} finally {
			DBUtils.close(rs);
			DBUtils.close(pstmt);
			DBUtils.close(conn);
		}
		
		return a;
	}
	
	public void updateArticle(String title,String source,String content,int id){
		String sql = "update t_article set title=?,source=?,content=?,updateTime=? where id=?";
		Connection conn = DBUtils.getConn();
		PreparedStatement pstmt = null;
		//ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, source);
			pstmt.setString(3, content);
			pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			pstmt.setInt(5, id);
			
			pstmt.executeUpdate();
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			DBUtils.rollback(conn);
		} finally{
			//DBUtil.close(rs);
			DBUtils.close(pstmt);
			DBUtils.close(conn);
		}		
	}
	
	public PagerVO findArticles(String title,int offset,int pagesize){
		List articles = new ArrayList();
		
		String sql = "select * from t_article limit ?,?";
		if(title != null){
			sql = "select *from t_article where title like '%"+title+"%' limit ?,?";
		}
		String sqlTotal="select count(*) from t_article";
		if(title != null){
			sqlTotal = "select count(*) from t_article where title like '%"+title+"%'";
		}
		Connection conn = DBUtils.getConn();
		PreparedStatement pstmt = null;
		PreparedStatement pstmtTotal = null;
		ResultSet rs = null;
		ResultSet rsTotal = null;
		int total = 0;
		try {
			//查询总量的功能
			pstmtTotal = conn.prepareStatement(sqlTotal);
			rsTotal = pstmtTotal.executeQuery();
			if(rsTotal.next()){
				total = rsTotal.getInt(1);
			}
			
			//查询当页数据的功能
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, pagesize);
			
			rs = pstmt.executeQuery();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			while(rs.next()){
				Article a =new Article();
				
				a.setId(rs.getInt("id"));
				a.setTitle(rs.getString("title"));
				a.setContent(rs.getString("content"));
				a.setCreateTime(rs.getTimestamp("createtime"));
				System.out.print(rs.getString("title"));
				articles.add(a);
				
			}
			
			//conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			//DBUtils.rollback(conn);
		} finally{
			DBUtils.close(rsTotal);
			DBUtils.close(pstmtTotal);
			DBUtils.close(rs);
			DBUtils.close(pstmt);
			DBUtils.close(conn);
		}
		PagerVO pv = new PagerVO();
		pv.setDatas(articles);
		pv.setTotal(total);
		
		return pv;
	}
	
	public PagerVO findArticles(Channel c, int offset, int pagesize) {
		// TODO Auto-generated method stub
		return null;
	}
}
