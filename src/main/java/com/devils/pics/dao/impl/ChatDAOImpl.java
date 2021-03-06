package com.devils.pics.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.devils.pics.dao.ChatDAO;
import com.devils.pics.domain.Chat;

@Repository
public class ChatDAOImpl implements ChatDAO {
	
	@Autowired
	private SqlSession sqlSession;
	private final String ns = "ChatMapper.";
	
	/* 채팅 추가하기 */
	public int addChat(Chat chat) throws Exception {
		return sqlSession.insert(ns+"addChat", chat);
	}
	
	/* 채팅 아이디로 해당되는 대화를 삭제함 */
	public int deleteChat(String chatId) throws Exception {
		return sqlSession.delete(ns+"deleteChat", chatId);
	}
	
	/* 읽음 처리(고객은 sender=1을 읽음 / 업체는 sender=0을 읽음) */
	public int setAlreadyRead(Map map) throws Exception {
		return sqlSession.update(ns+"setAlreadyRead", map);
	}
	
	/* 읽지 않은 메세지를 가져옴(고객은 sender=1을 가져옴 / 업체는 sender=0을 가져옴) */
	public List<Chat> getNotYetRead(Map map) throws Exception {
		return sqlSession.selectList(ns+"getNotYetRead", map);
	}
	
	/* 업체의 comId, stuId, custId, 안 읽은 채팅 개수, 날짜를 가져옴 */
	public List<Map> getCountOfUnreadComChat(String comId) throws Exception {
		return sqlSession.selectList(ns+"getCountOfUnreadComChat", comId);
	}
	
	/* 고객의 stuId, custId, 안 읽은 채팅 개수, 날짜를 가져옴 */
	public List<Map> getCountOfUnreadCustChat(String custId) throws Exception {
		return sqlSession.selectList(ns+"getCountOfUnreadCustChat", custId);
	}
	
	/* 채팅 아이디로 대화를 가져옴 */
	public Chat getChatByChatId(String chatId) throws Exception {
		return sqlSession.selectOne(ns+"getChatByChatId", chatId);
	}

	/* 해당되는 스튜디오, 고객의 대화를 가져옴 */
	public List<Chat> getPrevAllChat(Map map) throws Exception{
		return sqlSession.selectList(ns+"getPrevAllChat", map);
	}
	
	/* 해당 스튜디오, 고객의 가장 최근 채팅 가져오기  */
	public Chat getMostRecentChat(Map map) throws Exception {
		return sqlSession.selectOne(ns+"getMostRecentChat", map);
	}
	
	/* 업체의 스튜디오 및 고객별 최근 대화  */
	@Override
	public List<Map<String, String>> getRecentComChat(String comId) throws Exception {
		return sqlSession.selectList(ns+"getRecentComChat", comId);
	}
	
	/* 업체의 스튜디오 및 고객별 최근 대화를 중복 없이 가져옴(스튜디오 이름순) */
	@Override
	public List<Map<String, String>> getRecentComChatNoRpeat(String comId) throws Exception {
		return sqlSession.selectList(ns+"getRecentComChatNoRpeat", comId);
	}
	
	/* 스튜디오의 고객별 최근 대화  */
	@Override
	public List<Map<String, String>> getRecentStuChat(String stuId) throws Exception {
		return sqlSession.selectList(ns+"getRecentStuChat", stuId);
	}
	
	/* 고객의 스튜디오별 최근 대화  */
	@Override
	public List<Map<String, String>> getRecentCustChat(String custId) throws Exception{
		return sqlSession.selectList(ns+"getRecentCustChat", custId);
	}
	
	/* 스튜디오 아이디와 고객 이름으로 검색한, 업체의 스튜디오별/고객별 최근 대화   */
	@Override
	public List<Map<String, String>> getRecentChatByStuIdAndCustName(Map map) throws Exception {
		return sqlSession.selectList(ns+"getRecentChatByStuIdAndCustName", map);
	}

	/* 고객 이름으로 검색한, 업체의 스튜디오별/고객별 최근 대화  */
	@Override
	public List<Map<String, String>> getRecentChatByCustName(Map map) throws Exception {
		return sqlSession.selectList(ns+"getRecentChatByCustName", map);
	}
	
	/* 스튜디오 이름으로 검색한, 고객의 스튜디오별 최근 대화  */
	@Override
	public List<Map<String, String>> getRecentChatByStuName(Map map) throws Exception {
		return sqlSession.selectList(ns+"getRecentChatByStuName", map);
	}

	/* 고객 기본 정보(아이디, 이름, 프로필 사진) 가져오기 */
	@Override
	public Map<String, String> getCustDefaultInfo(String custId) throws Exception {
		return sqlSession.selectOne(ns+"getCustDefaultInfo", custId);
	}

	
	/* 스튜디오 기본 정보(스튜디오 아이디, 스튜디오 이름, 회사 아이디, 회사 이름, 회사 프로필 ) 가져오기*/
	@Override
	public Map<String, String> getStuDefaultInfo(String stuId) throws Exception {
		return sqlSession.selectOne(ns+"getStuDefaultInfo", stuId);
	}
	
}
