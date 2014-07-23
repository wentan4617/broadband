package com.tm.broadband.mapper;


import java.util.List;

import com.tm.broadband.model.Ticket;
import com.tm.broadband.model.Page;

public interface TicketMapper {

/**
 * mapping tm_ticket, customer DAO component
 * 
 * @author StevenChen
 * 
  */

	/* SELECT AREA */

	List<Ticket> selectTicket(Ticket t);
	List<Ticket> selectTicketsByPage(Page<Ticket> page);
	int selectTicketsSum(Page<Ticket> page);

	/* // END SELECT AREA */
	/* =================================================================================== */
	/* INSERT AREA */

	void insertTicket(Ticket t);

	/* // END INSERT AREA */
	/* =================================================================================== */
	/* UPDATE AREA */

	void updateTicket(Ticket t);

	/* // END UPDATE AREA */
	/* =================================================================================== */
	/* DELETE AREA */

	void deleteTicketById(int id);

	/* // END DELETE AREA */

}
