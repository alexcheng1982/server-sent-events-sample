package sse.es;

import javax.servlet.http.HttpServletRequest;

/**
 * Servlet implementation class MovementServlet
 */
public class MovementServlet extends EventSourceServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected EventSource newEventSource(HttpServletRequest request,
			String clientId) {
		return new MovementEventSource(800, 600, 20);
	}

}
