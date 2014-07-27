package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.TicketComment;
import com.tm.broadband.model.Page;

public interface TicketCommentMapper {

/**
 * mapping tm_ticket_comment, customer DAO component
 * 
 * @author StevenChen
 * 
  */

	/* SELECT AREA */

	List<TicketComment> selectTicketComment(TicketComment tc);
	List<TicketComment> selectTicketCommentsByPage(Page<TicketComment> page);
	int selectTicketCommentsSum(Page<TicketComment> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertTicketComment(TicketComment tc);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateTicketComment(TicketComment tc);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteTicketCommentById(int id);
	void deleteTicketCommentByTicketId(int id);

	/* // END DELETE AREA */

}
